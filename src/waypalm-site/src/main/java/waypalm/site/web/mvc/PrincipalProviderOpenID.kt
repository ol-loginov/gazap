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

OpenIDAuthentication
public class PrincipalProviderOpenID()
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

        val socialLink = userRepository!!.findSocialConnection(provider, userName, email);
        if (socialLink == null) {
            val socialKey = ConnectionKey(provider, userName)
            val socialProfile = SocialProfile()
            socialProfile.setUrl(token.getIdentityUrl());
            socialProfile.setEmail(email);
            return userService.createSocialConnection(auth!!.loadCurrentProfile(), socialKey, socialProfile)!!.profile;
        }
        return socialLink.profile;
    }

    fun takeFirstAttribute(attributes: List<OpenIDAttribute>?, name: String): String? {
        var attr = attributes!!
                .find{name.equals(it.getName()) && it.getCount() > 1}
        return attr?.getValues()!!.get(0)
    }
}


Retention(RetentionPolicy.RUNTIME)
Qualifier
public annotation class OpenIDAuthentication
