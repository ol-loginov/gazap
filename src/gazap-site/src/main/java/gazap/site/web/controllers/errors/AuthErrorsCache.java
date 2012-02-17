package gazap.site.web.controllers.errors;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

public class AuthErrorsCache {
    public static final String KEY = "AUTH_FAILURE";

    public static void store(HttpServletRequest request, AuthenticationException exception) {
        request.setAttribute(KEY, exception);
    }

    public static AuthenticationException remove(HttpServletRequest request) {
        try {
            return (AuthenticationException) request.getAttribute(KEY);
        } finally {
            request.removeAttribute(KEY);
        }
    }
}
