package waypalm.site.web.mvc

import waypalm.site.web.mvc.oauth.OAuthAuthenticationToken
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UserDetails

public class PrincipalProviderDirector(
        Autowired Qualifier("principalProviderOAuth")
        oauthProvider: AuthenticationUserDetailsService<OAuthAuthenticationToken>,
        Autowired Qualifier("principalProviderOpenID")
        openidProvider: AuthenticationUserDetailsService<OpenIDAuthenticationToken>
): PrincipalProvider(null, null, null)
, UserDetailsService
, AuthenticationUserDetailsService
{
    var oauthProvider: AuthenticationUserDetailsService<OAuthAuthenticationToken> = oauthProvider;
    var openidProvider: AuthenticationUserDetailsService<OpenIDAuthenticationToken> = openidProvider;

    public override fun loadUserByUsername(username: String?): UserDetails {
        throw UnsupportedOperationException()
    }
}