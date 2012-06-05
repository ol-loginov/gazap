package gazap.site.web.mvc;

import gazap.site.web.model.ApiAnswerType;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationRequest {
    private static final String FAILURE = "AuthenticationRequest.AUTH_FAILURE";
    private static final String RESPONSE_TYPE = "AuthenticationRequest.RESPONSE_TYPE";
    private static final String RESPONSE = "AuthenticationRequest.RESPONSE";

    @SuppressWarnings("unchecked")
    private static <T> T removeAttribute(HttpServletRequest request, String key) {
        try {
            return (T) request.getAttribute(key);
        } finally {
            request.removeAttribute(key);
        }
    }

    private static <T> void addAttribute(HttpServletRequest request, String key, T value, boolean forceSet) {
        if (request.getAttribute(key) != null && !forceSet) {
            return;
        }
        request.setAttribute(key, value);
    }

    public AuthenticationRequest withError(HttpServletRequest request, AuthenticationException exception) {
        addAttribute(request, FAILURE, exception, false);
        return this;
    }

    public AuthenticationException retrieveError(HttpServletRequest request) {
        return removeAttribute(request, FAILURE);
    }

    public AuthenticationRequest withAnswer(HttpServletRequest request, ApiAnswerType answerType) {
        addAttribute(request, RESPONSE_TYPE, answerType, false);
        return this;
    }

    public ApiAnswerType retrieveAnswer(HttpServletRequest request) {
        return removeAttribute(request, RESPONSE_TYPE);
    }

    public AuthenticationRequest withResponse(HttpServletRequest request, AuthenticationResponse authenticationResponse) {
        addAttribute(request, RESPONSE, authenticationResponse, false);
        return this;
    }

    public AuthenticationResponse retrieveResponse(HttpServletRequest request) {
        return removeAttribute(request, RESPONSE);
    }
}
