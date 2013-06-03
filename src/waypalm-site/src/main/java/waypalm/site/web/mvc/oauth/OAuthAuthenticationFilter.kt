package waypalm.site.web.mvc.oauth

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.social.connect.ConnectionFactoryLocator
import javax.inject.Inject
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices
import java.util.HashSet
import org.springframework.social.connect.Connection
import javax.servlet.http.HttpServletRequest
import org.springframework.social.connect.support.OAuth1ConnectionFactory
import org.springframework.util.StringUtils
import org.springframework.social.connect.support.OAuth2ConnectionFactory
import org.springframework.social.support.URIBuilder
import javax.security.sasl.AuthenticationException
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication

public class OAuthAuthenticationFilter [Inject](
        var connectionFactoryLocator: ConnectionFactoryLocator
)
: AbstractAuthenticationProcessingFilter(OAuthAuthenticationFilter.FILTER_ROUTE)
{
    class object {
        val FILTER_ROUTE = "/j_spring_oauth_security_check"
        val OAUTH_PARAM_PROVIDER = "oauth_provider"
    }

    val returnToUrlParameters = HashSet<String>();
    var overrideSecureUrl: String ? = null;

    public fun  setReturnToUrlParameters(returnToUrlParameters: Set<String>) {
        this.returnToUrlParameters.clear();
        this.returnToUrlParameters.addAll(returnToUrlParameters);
    }

    public override fun afterPropertiesSet() {
        super.afterPropertiesSet();
        if (returnToUrlParameters.isEmpty() && getRememberMeServices() is AbstractRememberMeServices) {
            var returnToUrlParameters = HashSet<String>();
            returnToUrlParameters.add((getRememberMeServices() as AbstractRememberMeServices).getParameter()!!);
            setReturnToUrlParameters(returnToUrlParameters)
        }
    }

    fun selectProviderName(request: HttpServletRequest): String {
        var name = request.getParameter(OAUTH_PARAM_PROVIDER);
        if (name == null) {
            val nameFromRequest = retrieveProviderName(request);
            if(nameFromRequest == null)
                throw IllegalStateException("OAuth provider is unknown");
            return nameFromRequest
        }
        return name!!
    }

    /**
     * OAuth Authentication flow: See http://dev.twitter.com/pages/auth
     */
    public override fun attemptAuthentication(requestParam: HttpServletRequest?, responseParam: HttpServletResponse?): Authentication? {
        var request = requestParam!!
        var response = responseParam!!

        val providerName = selectProviderName(request)
        var flow = selectFlow(providerName, request);
        if (flow.initiationRequired) {
            storeProviderName(request, providerName);
            flow.initiateAuthentication(request, response);
            return null;
        }

        var connection: Connection<*> = flow.attemptAuthentication(request)!!;
        return getAuthenticationManager()!!.authenticate(OAuthAuthenticationToken(connection = connection));
    }

    private fun selectFlow(provider: String, request: HttpServletRequest): OAuthFlow<*> {
        val connectionFactory = connectionFactoryLocator.getConnectionFactory(provider);

        if (StringUtils.hasText(request.getParameter("oauth_token"))) {
            return OAuth1Flow(false, connectionFactory as OAuth1ConnectionFactory<*>, callbackUrl(request));
        } else if (StringUtils.hasText(request.getParameter("code"))) {
            return OAuth2Flow(false, connectionFactory as OAuth2ConnectionFactory<*>, callbackUrl(request));
        } else if (connectionFactory is OAuth2ConnectionFactory<*>) {
            return OAuth2Flow(true, connectionFactory as OAuth2ConnectionFactory<*>, callbackUrl(request));
        } else if (connectionFactory is OAuth1ConnectionFactory<*>) {
            return OAuth1Flow(true, connectionFactory as OAuth1ConnectionFactory<*>, callbackUrl(request));
        } else {
            throw AuthenticationException("failed to select operation");
        }
    }

    private fun storeProviderName(request: HttpServletRequest, providerName: String) {
        request.getSession(true)!!.setAttribute(OAUTH_PARAM_PROVIDER, providerName);
    }

    private fun retrieveProviderName(request: HttpServletRequest): String? {
        val session = request.getSession(false);
        if (session == null) {
            return null;
        }
        try {
            return session.getAttribute(OAUTH_PARAM_PROVIDER) as String;
        } finally {
            session.removeAttribute(OAUTH_PARAM_PROVIDER);
        }
    }

    private fun callbackUrl(request: HttpServletRequest): String {
        val base = URIBuilder.fromUri(callbackUrlBase(request))!!;
        for (paramName  :String  in returnToUrlParameters) {
            if (request.getParameterMap()!!.containsKey(paramName)) {
                base.queryParam(paramName, request.getParameter(paramName));
            }
        }
        return base.build().toString();
    }

    private fun callbackUrlBase(request: HttpServletRequest): String {
        if (StringUtils.hasText(overrideSecureUrl)) {
            return overrideSecureUrl + FILTER_ROUTE;
        }
        return request.getRequestURL().toString();
    }
}