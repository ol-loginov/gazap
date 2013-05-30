package waypalm.site.web.mvc

import waypalm.site.web.mvc.oauth.OAuthAuthenticationToken
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.authentication.AbstractAuthenticationToken
import javax.inject.Inject
import org.springframework.transaction.annotation.Transactional

public class PrincipalProviderDirector(): PrincipalProvider()
, UserDetailsService
, AuthenticationUserDetailsService<AbstractAuthenticationToken>
{
    Inject OAuthAuthentication
            var oauthProvider: AuthenticationUserDetailsService<OAuthAuthenticationToken>? = null
    Inject OpenIDAuthentication
            var openidProvider: AuthenticationUserDetailsService<OpenIDAuthenticationToken>? = null

    Transactional
    public override fun loadUserByUsername(username: String?): UserDetails {
        return createPrincipal(userRepository!!.findProfileByEmail(username))!!
    }

    public override fun loadUserDetails(token: AbstractAuthenticationToken?): UserDetails? {
        when(token){
            is OAuthAuthenticationToken -> return oauthProvider!!.loadUserDetails(token as OAuthAuthenticationToken)
            is OpenIDAuthenticationToken -> return openidProvider!!.loadUserDetails(token as OpenIDAuthenticationToken)
            else -> return null
        }
    }
}