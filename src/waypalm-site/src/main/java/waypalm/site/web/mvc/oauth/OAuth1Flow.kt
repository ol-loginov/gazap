package waypalm.site.web.mvc.oauth

import org.springframework.social.connect.support.OAuth1ConnectionFactory
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.social.oauth1.OAuth1Parameters
import org.springframework.social.oauth1.OAuth1Version
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.connect.Connection
import javax.security.sasl.AuthenticationException
import org.springframework.social.oauth1.OAuthToken

public class OAuth1Flow(
        initiationRequired: Boolean,
        connectionFactory: OAuth1ConnectionFactory<*>,
        callbackUrl: String
)
: OAuthFlow<OAuth1ConnectionFactory<*>>(initiationRequired, connectionFactory, callbackUrl)
{
    class object {
        var SESSION_REQUEST_TOKEN: String = "__oauth_flow1_request_token__";
    }

    public override fun initiateAuthentication(request: HttpServletRequest, response: HttpServletResponse) {
        val operations = connectionFactory.getOAuthOperations()!!;

        val requestToken = operations.fetchRequestToken(callbackUrl, null)!!;
        storeCachedRequestToken(request, requestToken);

        var oauthParameters = OAuth1Parameters.NONE;
        if (operations.getVersion() == OAuth1Version.CORE_10) {
            oauthParameters = OAuth1Parameters();
            oauthParameters.setCallbackUrl(callbackUrl);
        }
        response.sendRedirect(operations.buildAuthenticateUrl(requestToken.getValue(), oauthParameters));
    }

    public override fun attemptAuthentication(request: HttpServletRequest): Connection<*>? {
        val requestToken = extractCachedRequestToken(request);
        if (requestToken == null) {
            throw AuthenticationException("lost request token");
        }

        val token = request.getParameter("oauth_token");
        val verifier = request.getParameter("oauth_verifier");

        val operations = connectionFactory.getOAuthOperations()!!;
        val accessToken = operations.exchangeForAccessToken(AuthorizedRequestToken(requestToken, verifier), null);
        return connectionFactory.createConnection(accessToken);
    }

    private fun storeCachedRequestToken(request: HttpServletRequest, token: OAuthToken) {
        request.getSession()!!.setAttribute(SESSION_REQUEST_TOKEN, token);
    }

    private fun extractCachedRequestToken(request: HttpServletRequest): OAuthToken? {
        val session = request.getSession(false);
        if (session == null) {
            return null;
        }
        try {
            return session.getAttribute(SESSION_REQUEST_TOKEN) as OAuthToken;
        } finally {
            session.removeAttribute(SESSION_REQUEST_TOKEN);
        }
    }

}