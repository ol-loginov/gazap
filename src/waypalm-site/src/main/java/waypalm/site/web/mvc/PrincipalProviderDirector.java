package waypalm.site.web.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import waypalm.site.web.mvc.oauth.OAuthAuthenticationToken;

public class PrincipalProviderDirector extends PrincipalProvider implements UserDetailsService, AuthenticationUserDetailsService {
    private AuthenticationUserDetailsService<OAuthAuthenticationToken> oauthProvider;
    private AuthenticationUserDetailsService<OpenIDAuthenticationToken> openidProvider;

    @Autowired
    @Qualifier("principalProviderOAuth")
    protected void setOauthProvider(AuthenticationUserDetailsService<OAuthAuthenticationToken> oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    @Autowired
    @Qualifier("principalProviderOpenID")
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
}
