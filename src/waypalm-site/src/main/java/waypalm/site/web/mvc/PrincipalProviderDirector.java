package waypalm.site.web.mvc;

import waypalm.site.web.mvc.oauth.OAuthAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;

@Service("principalProvider")
public class PrincipalProviderDirector extends PrincipalProvider implements UserDetailsService, AuthenticationUserDetailsService {
    private PrincipalProviderOAuth oauthProvider;
    private PrincipalProviderOpenID openidProvider;

    @Autowired
    protected void setOauthProvider(PrincipalProviderOAuth oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    @Autowired
    protected void setOpenidProvider(PrincipalProviderOpenID openidProvider) {
        this.openidProvider = openidProvider;
    }

    @Override
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
