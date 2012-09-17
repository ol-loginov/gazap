package waypalm.site.web.controllers.access;

import waypalm.site.services.UserAccess;
import waypalm.site.web.controllers.BaseController;
import waypalm.site.web.model.ApiAnswerType;
import waypalm.site.web.mvc.AuthenticationRequestHelper;
import waypalm.site.web.mvc.AuthenticationResponse;
import waypalm.site.web.views.access.LoginMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

@Controller
public class LoginController extends BaseController {
    private final static String FORM_LOGIN_FILTER = "/j_spring_security_check";

    public final static String LOGIN_ROUTE = "/auth";
    public final static String LOGIN_ROUTE_AJAX = LOGIN_ROUTE + ".ajax";

    @Autowired
    protected UserAccess userAccess;

    private AuthenticationRequestHelper authenticationRequestHelper = new AuthenticationRequestHelper();

    @RequestMapping(value = LOGIN_ROUTE_AJAX, method = RequestMethod.GET)
    public ModelAndView getLoginPage(Locale locale) {
        LoginMethods dialog = new LoginMethods();
        dialog.getAuthProviders().addAll(userAccess.getAvailableSocialProviders());
        return responseBuilder(locale).view("access/login.ajax", dialog);
    }

    @RequestMapping(value = LOGIN_ROUTE_AJAX, method = RequestMethod.POST)
    public ModelAndView proceedFormLogin(Locale locale, HttpServletRequest request) throws IOException, ServletException {
        authenticationRequestHelper
                .useAnswer(request, ApiAnswerType.JSON)
                .useResponse(request, AuthenticationResponse.STATUS);
        return responseBuilder(locale).forward(FORM_LOGIN_FILTER);
    }
}
