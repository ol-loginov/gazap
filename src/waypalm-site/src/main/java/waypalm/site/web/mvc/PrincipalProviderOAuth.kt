package waypalm.site.web.mvc

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.transaction.annotation.Transactional
import org.springframework.social.connect.Connection
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.twitter.api.Twitter
import waypalm.site.web.mvc.oauth.OAuthAuthenticationToken
import waypalm.domain.entity.Profile
import waypalm.common.web.model.SocialProfile
import waypalm.common.util.ifEmpty
import waypalm.common.util.ifNull
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier
import waypalm.domain.dao.UserRepository
import waypalm.site.services.UserService
import waypalm.site.services.UserAccess
import javax.inject.Inject
import javax.inject.Named

Named OAuth
public class PrincipalProviderOAuth [Inject](
        var userService: UserService,
        var userRepository: UserRepository,
        var auth: UserAccess
)
: PrincipalProvider()
, AuthenticationUserDetailsService<OAuthAuthenticationToken>
{
    Transactional
    public override fun loadUserDetails(token: OAuthAuthenticationToken?): UserDetails? {
        return createPrincipal(loadUser(token!!.getConnection()!!))
    }

    fun loadUser(connection: Connection<*>): Profile {
        val connectionKey = connection.getKey()!!
        val socialLink = userRepository.findSocialConnection(connectionKey.getProviderId(), connectionKey.getProviderUserId(), null)
        if(socialLink == null){
            return userService.createSocialConnection(auth.loadCurrentProfile(), connectionKey, wrap(connection))!!.profile
        }
        return socialLink.profile
    }

    fun wrap(connection: Connection<*>): SocialProfile {
        val api = connection.getApi()
        when(api ){
            is Facebook -> return initialize(SocialProfile(), api as Facebook)
            is Twitter -> return initialize(SocialProfile(), api as Twitter)
            else -> return SocialProfile()
        }
    }

    fun initialize(data: SocialProfile, api: Facebook): SocialProfile {
        val p = api.userOperations()!!.getUserProfile()!!;
        data.nick = p.getUsername().ifEmpty("fb${p.getId()}")
        data.email = p.getEmail().ifNull("")
        data.displayName = p.getName().ifNull("")
        data.url = p.getLink().ifNull("")
        return data
    }
    fun initialize(data: SocialProfile, api: Twitter): SocialProfile {
        val p = api.userOperations()!!.getUserProfile()!!;
        data.nick = p.getScreenName().ifEmpty("fb${p.getId()}")
        data.displayName = p.getName().ifNull("")
        data.url = p.getUrl().ifNull("")
        return data
    }
}

Retention(RetentionPolicy.RUNTIME)
Qualifier
public annotation class OAuth
