package waypalm.site.web.controllers.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.services.UserAccess;
import waypalm.site.web.controllers.BaseController;
import waypalm.site.web.mvc.AuthenticationFailureHandler;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Named
@RequestMapping("/auth")
public class LoginController extends BaseController {
    private final static String FORM_LOGIN_FILTER = "/j_spring_security_check";

    @Inject
    protected UserAccess userAccess;
    @Inject
    protected AuthenticationFailureHandler authenticationFailureHandler;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getLoginPage() {
        return view("auth/login")
                .addObject("authProviders", userAccess.getAvailableSocialProviders());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView proceedFormLogin() throws IOException, ServletException {
        return forward(FORM_LOGIN_FILTER);
    }

    @RequestMapping("/error")
    public ModelAndView getLoginError(HttpServletRequest request) {
        AuthenticationException authError = authenticationFailureHandler.getAuthenticationException(request);
        return view("auth/login")
                .addObject("authProviders", userAccess.getAvailableSocialProviders())
                .addObject("authError", authError != null)
                .addObject("authErrorBadCredentials", authError instanceof BadCredentialsException)
                .addObject("authErrorUsernameNotFound", authError instanceof UsernameNotFoundException);
    }
}
