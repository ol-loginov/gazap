package waypalm.site.web.mvc.oauth

import org.springframework.social.connect.support.OAuth2ConnectionFactory
import javax.servlet.http.HttpServletRequest
import org.springframework.social.connect.Connection
import javax.servlet.http.HttpServletResponse
import org.springframework.social.oauth2.OAuth2Parameters
import org.springframework.social.oauth2.GrantType

public class OAuth2Flow(
        initiationRequired: Boolean,
        factory: OAuth2ConnectionFactory<*>,
        callbackUrl: String
)
: OAuthFlow<OAuth2ConnectionFactory<*>>(initiationRequired, factory, callbackUrl)
{
    public override fun attemptAuthentication(request: HttpServletRequest): Connection<*>? {
        val code = request.getParameter("code");
        val accessGrant = connectionFactory.getOAuthOperations()!!.exchangeForAccess(code, callbackUrl, null);
        return connectionFactory.createConnection(accessGrant);
    }

    public override fun initiateAuthentication(request: HttpServletRequest, response: HttpServletResponse) {
        var operations = connectionFactory.getOAuthOperations();
        val parameters = OAuth2Parameters();
        parameters.setRedirectUri(callbackUrl);
        response.sendRedirect(operations!!.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, parameters));
    }
}