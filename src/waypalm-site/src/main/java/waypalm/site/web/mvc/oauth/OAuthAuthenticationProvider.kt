package waypalm.site.web.mvc.oauth

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import javax.inject.Inject
import org.springframework.security.core.Authentication
import waypalm.site.web.mvc.AuthDirector

public class OAuthAuthenticationProvider [Inject] (
        AuthDirector var userDetailsService: AuthenticationUserDetailsService<OAuthAuthenticationToken>
)
: AuthenticationProvider
{
    public override fun authenticate(authentication: Authentication?): Authentication? {
        if (authentication == null || !supports(authentication.getClass())) {
            return null;
        }

        if (authentication is OAuthAuthenticationToken) {
            val response = authentication as OAuthAuthenticationToken;
            val userDetails = userDetailsService.loadUserDetails(response)!!;
            return OAuthAuthenticationToken(userDetails, userDetails.getAuthorities())
        }

        return null;
    }

    public override fun supports(authentication: Class<*>?): Boolean {
        return authentication != null && javaClass<OAuthAuthenticationToken>().isAssignableFrom(authentication);
    }
}