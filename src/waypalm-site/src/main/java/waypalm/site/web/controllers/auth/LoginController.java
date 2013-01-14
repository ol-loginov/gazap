package waypalm.site.web.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.services.UserAccess;
import waypalm.site.web.controllers.BaseController;
import waypalm.site.web.mvc.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping("/auth")
public class LoginController extends BaseController {
    private final static String FORM_LOGIN_FILTER = "/j_spring_security_check";

    @Autowired
    protected UserAccess userAccess;
    @Autowired
    protected AuthenticationFailureHandler authenticationFailureHandler;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getLoginPage(Locale locale) {
        return responseBuilder(locale).view("auth/login")
                .addObject("authProviders", userAccess.getAvailableSocialProviders());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView proceedFormLogin(Locale locale, HttpServletRequest request) throws IOException, ServletException {
        return responseBuilder(locale).forward(FORM_LOGIN_FILTER);
    }

    @RequestMapping("/error")
    public ModelAndView getLoginError(Locale locale, HttpServletRequest request) {
        AuthenticationException authError = authenticationFailureHandler.getAuthenticationException(request);
        return responseBuilder(locale).view("auth/login")
                .addObject("authProviders", userAccess.getAvailableSocialProviders())
                .addObject("authError", authError != null)
                .addObject("authErrorBadCredentials", authError instanceof BadCredentialsException)
                .addObject("authErrorUsernameNotFound", authError instanceof UsernameNotFoundException);
    }
}
