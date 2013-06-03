package waypalm.site.web.mvc.oauth;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2Flow extends OAuthFlow<OAuth2ConnectionFactory<?>> {
    public OAuth2Flow(boolean initiationRequired, OAuth2ConnectionFactory<?> factory) {
        super(initiationRequired, factory);
    }

    @Override
    public void initiateAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OAuth2Operations operations = getConnectionFactory().getOAuthOperations();
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(getCallbackUrl());
        response.sendRedirect(operations.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, parameters));
    }

    @Override
    public Connection<?> attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
        String code = request.getParameter("code");
        AccessGrant accessGrant = getConnectionFactory().getOAuthOperations().exchangeForAccess(code, getCallbackUrl(), null);
        return getConnectionFactory().createConnection(accessGrant);
    }
}
