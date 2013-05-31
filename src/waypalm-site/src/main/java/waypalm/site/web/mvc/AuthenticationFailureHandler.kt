package waypalm.site.web.mvc

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.WebAttributes

public class AuthenticationFailureHandler: SimpleUrlAuthenticationFailureHandler() {
    public fun getAuthenticationException(request: HttpServletRequest): AuthenticationException? {
        if (isUseForward())
            return request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) as AuthenticationException?
        return request.getSession(false)?.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) as AuthenticationException?
    }
}