package gazap.panel.web.mvc;

import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserSocialLink;
import gazap.panel.model.ServiceErrorException;
import gazap.panel.web.mvc.oauth.OAuthAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.stereotype.Service;

@Service("principalProvider.OAuth")
public class PrincipalProviderOAuth extends PrincipalProvider implements AuthenticationUserDetailsService<OAuthAuthenticationToken> {
    @Override
    public UserDetails loadUserDetails(OAuthAuthenticationToken token) {
        try {
            return createPrincipal(loadUser(token.getConnection()));
        } catch (ServiceErrorException e) {
            throw new UsernameNotFoundException("internal error", e);
        }
    }

    private UserProfile loadUser(Connection<?> connection) throws ServiceErrorException {
        ConnectionKey key = connection.getKey();
        UserSocialLink socialLink = userProfileDao.findSocialConnection(key.getProviderId(), key.getProviderUserId(), null);
        return socialLink == null ? null : socialLink.getUser();
    }
}
