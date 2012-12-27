package waypalm.site.web.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.services.UserAccess;
import waypalm.site.web.controllers.BaseController;

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

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getLoginPage(Locale locale, HttpServletRequest request) {
        AuthenticationException exception = (AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        return responseBuilder(locale).view("access/login")
                .addObject("authProviders", userAccess.getAvailableSocialProviders())
                .addObject("authError", exception);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView proceedFormLogin(Locale locale, HttpServletRequest request) throws IOException, ServletException {
        return responseBuilder(locale).forward(FORM_LOGIN_FILTER);
    }
}
