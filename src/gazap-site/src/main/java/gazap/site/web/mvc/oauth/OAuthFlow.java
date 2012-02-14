package gazap.site.web.mvc.oauth;

import org.springframework.social.connect.Connection;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract class OAuthFlow<TConnectionFactory> {
    private final boolean initiationRequired;
    private final TConnectionFactory connectionFactory;

    private String callbackUrl;

    public OAuthFlow(boolean initiationRequired, TConnectionFactory connectionFactory) {
        this.initiationRequired = initiationRequired;
        this.connectionFactory = connectionFactory;
    }

    public abstract Connection<?> attemptAuthentication(HttpServletRequest request) throws AuthenticationException;

    public abstract void initiateAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public final boolean isInitiationRequired() {
        return initiationRequired;
    }

    public TConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
