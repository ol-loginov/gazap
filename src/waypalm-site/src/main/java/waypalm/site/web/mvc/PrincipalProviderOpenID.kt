package waypalm.site.web.mvc

import javax.inject.Qualifier
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.transaction.annotation.Transactional
import waypalm.domain.entity.Profile
import java.net.URL
import java.net.MalformedURLException
import waypalm.common.web.model.SocialProfile
import org.springframework.social.connect.ConnectionKey
import org.springframework.security.openid.OpenIDAttribute
import waypalm.common.util.ifNull
import java.util.Locale
import com.iserv2.commons.lang.IservHashUtil
import javax.inject.Inject
import waypalm.site.services.UserAccess
import waypalm.domain.dao.UserRepository
import waypalm.site.services.UserService
import javax.inject.Named

Named OpenID
public class PrincipalProviderOpenID [Inject](
        var userService: UserService,
        var userRepository: UserRepository,
        var auth: UserAccess
)
: PrincipalProvider()
, AuthenticationUserDetailsService<OpenIDAuthenticationToken>
{
    Transactional
    public override fun loadUserDetails(token: OpenIDAuthenticationToken?): UserDetails? {
        return createPrincipal(loadUser(token!!))
    }

    fun loadUser(token: OpenIDAuthenticationToken): Profile {
        var url: URL;
        try {
            url = URL(token.getIdentityUrl());
        } catch (e: MalformedURLException) {
            throw IllegalArgumentException("wrong identity url");
        }

        val provider = createOpenIDProvider(url);
        val userName = createOpenIDUserName(url);
        val email = takeFirstAttribute(token.getAttributes(), "email").ifNull(takeFirstAttribute(token.getAttributes(), "axEmail").ifNull(""));

        val socialLink = userRepository.findSocialConnection(provider, userName, email);
        if (socialLink == null) {
            return userService.createSocialConnection(auth.loadCurrentProfile()
                    , ConnectionKey(provider, userName)
                    , SocialProfile(url = token.getIdentityUrl()!!, email = email)
            )!!.profile;
        }
        return socialLink.profile;
    }

    fun takeFirstAttribute(attributes: List<OpenIDAttribute>?, name: String): String? {
        var attr = attributes!!
                .find{ name.equals(it.getName()) && it.getCount() > 1 }
        return attr?.getValues()!!.get(0)
    }

    fun createOpenIDProvider(url: URL): String {
        return url.getHost()!!.toLowerCase(Locale.ENGLISH);
    }

    fun createOpenIDUserName(url: URL): String {
        return IservHashUtil.md5(url.toString())!!;
    }
}

Retention(RetentionPolicy.RUNTIME)
Qualifier
public annotation class OpenID
