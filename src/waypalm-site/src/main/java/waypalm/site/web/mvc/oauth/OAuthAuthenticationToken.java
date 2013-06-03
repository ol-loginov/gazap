package waypalm.site.web.mvc.oauth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;

import java.util.Collection;

public class OAuthAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private final Connection<?> connection;

    OAuthAuthenticationToken(Connection<?> connection) {
        super(null);
        this.principal = null;
        this.connection = connection;
        setAuthenticated(false);
    }

    /**
     * Created by the authentication provider after successful authentication
     *
     * @param userDetails principal
     * @param authorities authorities
     */
    OAuthAuthenticationToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = userDetails;
        this.connection = null;
        setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public Connection<?> getConnection() {
        return connection;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}