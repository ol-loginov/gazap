package waypalm.site.web.mvc.oauth;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class OAuthAuthenticationProvider implements AuthenticationProvider, InitializingBean {
    private AuthenticationUserDetailsService<OAuthAuthenticationToken> userDetailsService;

    public void setUserDetailsService(AuthenticationUserDetailsService<OAuthAuthenticationToken> userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "The userDetailsService must be set");
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        if (authentication instanceof OAuthAuthenticationToken) {
            OAuthAuthenticationToken response = (OAuthAuthenticationToken) authentication;
            UserDetails userDetails = userDetailsService.loadUserDetails(response);
            return createSuccessfulAuthentication(userDetails);
        }

        return null;
    }

    protected Authentication createSuccessfulAuthentication(UserDetails userDetails) {
        return new OAuthAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    public boolean supports(Class<?> authentication) {
        return OAuthAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
