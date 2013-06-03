package waypalm.site.web.mvc.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OAuthAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String FILTER_ROUTE = "/j_spring_oauth_security_check";
    private static final String OAUTH_PARAM_PROVIDER = "oauth_provider";

    private Set<String> returnToUrlParameters = Collections.emptySet();

    public OAuthAuthenticationFilter() {
        super(FILTER_ROUTE);
    }

    @Inject
    ConnectionFactoryLocator connectionFactoryLocator;
    @Value("${setup.social.connect.overrideSecureUrl}")
    String overrideSecureUrl;

    public void setReturnToUrlParameters(Set<String> returnToUrlParameters) {
        Assert.notNull(returnToUrlParameters, "returnToUrlParameters cannot be null");
        this.returnToUrlParameters = returnToUrlParameters;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        if (returnToUrlParameters.isEmpty() && getRememberMeServices() instanceof AbstractRememberMeServices) {
            returnToUrlParameters = new HashSet<>();
            returnToUrlParameters.add(((AbstractRememberMeServices) getRememberMeServices()).getParameter());
        }
    }

    /**
     * OAuth Authentication flow: See http://dev.twitter.com/pages/auth
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String providerName = request.getParameter(OAUTH_PARAM_PROVIDER);
        if (providerName == null) {
            providerName = retrieveProviderName(request);
        }

        if (providerName == null) {
            throw new IllegalStateException("OAuth provider is unknown");
        }

        OAuthFlow flow = selectFlow(providerName, request);
        if (flow == null) {
            throw new AuthenticationException("unknown OAuth flow");
        }

        flow.setCallbackUrl(callbackUrl(request));

        if (flow.isInitiationRequired()) {
            storeProviderName(request, providerName);
            flow.initiateAuthentication(request, response);
            return null;
        }

        Connection<?> connection = flow.attemptAuthentication(request);
        OAuthAuthenticationToken token = new OAuthAuthenticationToken(connection);
        return getAuthenticationManager().authenticate(token);
    }

    private OAuthFlow selectFlow(String provider, HttpServletRequest request) throws IOException {
        ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(provider);

        if (StringUtils.hasText(request.getParameter("oauth_token"))) {
            return new OAuth1Flow(false, (OAuth1ConnectionFactory<?>) connectionFactory);
        } else if (StringUtils.hasText(request.getParameter("code"))) {
            return new OAuth2Flow(false, (OAuth2ConnectionFactory<?>) connectionFactory);
        } else if (connectionFactory instanceof OAuth2ConnectionFactory) {
            return new OAuth2Flow(true, (OAuth2ConnectionFactory) connectionFactory);
        } else if (connectionFactory instanceof OAuth1ConnectionFactory) {
            return new OAuth1Flow(true, (OAuth1ConnectionFactory<?>) connectionFactory);
        } else {
            throw new AuthenticationException("failed to select operation");
        }
    }

    private void storeProviderName(HttpServletRequest request, String providerName) {
        request.getSession(true).setAttribute(OAUTH_PARAM_PROVIDER, providerName);
    }

    private String retrieveProviderName(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        try {
            return (String) session.getAttribute(OAUTH_PARAM_PROVIDER);
        } finally {
            session.removeAttribute(OAUTH_PARAM_PROVIDER);
        }
    }

    private String callbackUrl(HttpServletRequest request) {
        URIBuilder base = URIBuilder.fromUri(callbackUrlBase(request));
        if (returnToUrlParameters != null) {
            for (String paramName : returnToUrlParameters) {
                if (request.getParameterMap().containsKey(paramName)) {
                    base.queryParam(paramName, request.getParameter(paramName));
                }
            }
        }
        return base.build().toString();
    }

    private String callbackUrlBase(HttpServletRequest request) {
        if (StringUtils.hasText(overrideSecureUrl)) {
            return overrideSecureUrl + FILTER_ROUTE;
        }
        return request.getRequestURL().toString();
    }
}