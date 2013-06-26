package waypalm.site.web.mvc;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import waypalm.site.web.mvc.oauth.OAuthAuthenticationToken;

import javax.inject.Inject;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PrincipalProviderDirector extends PrincipalProvider implements UserDetailsService, AuthenticationUserDetailsService {
    private AuthenticationUserDetailsService<OAuthAuthenticationToken> oauthProvider;
    private AuthenticationUserDetailsService<OpenIDAuthenticationToken> openidProvider;

    @Inject
    @PrincipalProviderDirector.OAuth
    protected void setOauthProvider(AuthenticationUserDetailsService<OAuthAuthenticationToken> oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    @Inject
    @PrincipalProviderDirector.OpenID
    protected void setOpenidProvider(AuthenticationUserDetailsService<OpenIDAuthenticationToken> openidProvider) {
        this.openidProvider = openidProvider;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return createPrincipal(userRepository.findProfileByEmail(userName));
    }

    @Override
    public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
        if (token instanceof OAuthAuthenticationToken) {
            return oauthProvider.loadUserDetails((OAuthAuthenticationToken) token);
        }
        if (token instanceof OpenIDAuthenticationToken) {
            return openidProvider.loadUserDetails((OpenIDAuthenticationToken) token);
        }
        return null;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Qualifier
    public static @interface OpenID {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Qualifier
    public static @interface OAuth {
    }
}
