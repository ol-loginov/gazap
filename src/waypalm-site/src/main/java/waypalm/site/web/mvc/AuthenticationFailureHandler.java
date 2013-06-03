package waypalm.site.web.mvc;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public AuthenticationException getAuthenticationException(HttpServletRequest request) {
        if (isUseForward()) {
            return (AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        } else {
            HttpSession session = request.getSession(false);
            if (session != null) {
                return (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            }
        }
        return null;
    }
}
