package waypalm.site.web.controllers.auth

import org.springframework.web.bind.annotation.RequestMapping
import javax.inject.Named
import javax.inject.Inject
import waypalm.site.web.controllers.ControllerServices
import waypalm.site.web.controllers.ControllerBase
import waypalm.site.web.controllers.ModelAndView
import org.springframework.web.bind.annotation.RequestMethod
import waypalm.site.services.UserAccess
import waypalm.site.web.mvc.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException

Named
RequestMapping(array("/auth"))
public class LoginController [Inject](
        override var services: ControllerServices,
        var userAccess: UserAccess,
        var authenticationFailureHandler: AuthenticationFailureHandler
): ControllerBase
{
    var formLoginFilter: String = "/j_spring_security_check"

    RequestMapping(method = array(RequestMethod.GET))
    public fun getLoginPage(): ModelAndView {
        return view("auth/login")
                .addObject("authProviders", userAccess.getAvailableSocialProviders());
    }

    RequestMapping(method = array(RequestMethod.POST))
    public fun proceedFormLogin(): ModelAndView {
        return forward(formLoginFilter);
    }

    RequestMapping(array("/error"))
    public fun getLoginError(request: HttpServletRequest): ModelAndView {
        var authError = authenticationFailureHandler.getAuthenticationException(request);
        return view("auth/login")
                .addObject("authProviders", userAccess.getAvailableSocialProviders())
                .addObject("authError", authError != null)
                .addObject("authErrorBadCredentials", authError is BadCredentialsException)
                .addObject("authErrorUsernameNotFound", authError is UsernameNotFoundException);
    }
}