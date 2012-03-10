package gazap.site.web.controllers.access;

import com.iserv2.commons.mvc.views.ForwardContent;
import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.model.ApiAnswerType;
import gazap.site.services.UserAccess;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.mvc.AuthenticationRequest;
import gazap.site.web.mvc.AuthenticationResponse;
import gazap.site.web.views.access.LoginMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    private AuthenticationRequest authenticationRequest = new AuthenticationRequest();

    @RequestMapping(value = LOGIN_ROUTE_AJAX, method = RequestMethod.GET)
    @ViewName(name = "access/login.ajax", response = LoginMethods.class)
    public LoginMethods getLoginPage() {
        LoginMethods dialog = new LoginMethods();
        dialog.getAuthProviders().addAll(userAccess.getAvailableSocialProviders());
        return dialog;
    }

    @RequestMapping(value = LOGIN_ROUTE_AJAX, method = RequestMethod.POST)
    public ForwardContent proceedFormLogin(Locale locale, HttpServletRequest request) throws IOException, ServletException {
        authenticationRequest
                .withAnswer(request, ApiAnswerType.JSON)
                .withResponse(request, AuthenticationResponse.STATUS);
        return contentFactory(locale).forward(FORM_LOGIN_FILTER);
    }
}
