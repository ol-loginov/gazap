package waypalm.site.web.mvc.oauth

import org.springframework.social.connect.Connection
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class OAuthFlow<TConnectionFactory>(
        val initiationRequired: Boolean,
        val connectionFactory: TConnectionFactory,
        val callbackUrl: String? = null
) {
    public abstract fun attemptAuthentication(request: HttpServletRequest): Connection<*>?;

    public abstract fun initiateAuthentication(request: HttpServletRequest, response: HttpServletResponse);
}