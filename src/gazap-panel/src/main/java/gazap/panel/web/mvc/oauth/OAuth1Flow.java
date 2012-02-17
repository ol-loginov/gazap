package gazap.panel.web.mvc.oauth;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.oauth1.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OAuth1Flow extends OAuthFlow<OAuth1ConnectionFactory<?>> {
    private static final String SESSION_REQUEST_TOKEN = "__oauth_flow1_request_token__";

    public OAuth1Flow(boolean initiationRequired, OAuth1ConnectionFactory<?> connectionFactory) {
        super(initiationRequired, connectionFactory);
    }

    @Override
    public void initiateAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OAuth1Operations operations = getConnectionFactory().getOAuthOperations();

        OAuthToken requestToken = operations.fetchRequestToken(getCallbackUrl(), null);
        storeCachedRequestToken(request, requestToken);

        OAuth1Parameters oauthParameters = OAuth1Parameters.NONE;
        if (operations.getVersion() == OAuth1Version.CORE_10) {
            oauthParameters = new OAuth1Parameters();
            oauthParameters.setCallbackUrl(getCallbackUrl());
        }
        response.sendRedirect(operations.buildAuthenticateUrl(requestToken.getValue(), oauthParameters));
    }

    @Override
    public Connection<?> attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
        OAuthToken requestToken = extractCachedRequestToken(request);
        if (requestToken == null) {
            throw new AuthenticationException("lost request token");
        }

        String token = request.getParameter("oauth_token");
        String verifier = request.getParameter("oauth_verifier");

        OAuth1Operations operations = getConnectionFactory().getOAuthOperations();
        OAuthToken accessToken = operations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, verifier), null);
        return getConnectionFactory().createConnection(accessToken);
    }

    private void storeCachedRequestToken(HttpServletRequest request, OAuthToken token) {
        request.getSession().setAttribute(SESSION_REQUEST_TOKEN, token);
    }

    private OAuthToken extractCachedRequestToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        try {
            return (OAuthToken) session.getAttribute(SESSION_REQUEST_TOKEN);
        } finally {
            session.removeAttribute(SESSION_REQUEST_TOKEN);
        }
    }
}
