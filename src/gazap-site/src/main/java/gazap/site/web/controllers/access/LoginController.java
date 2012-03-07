package gazap.site.web.controllers.access;

import com.iserv2.commons.mvc.views.Content;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController extends BaseController {
    @Autowired
    protected UserAccess userAccess;

    private AuthenticationRequest authenticationRequest = new AuthenticationRequest();

    @RequestMapping(value = "/auth.ajax", method = RequestMethod.GET)
    @ViewName(name = "access/login.ajax", response = LoginMethods.class)
    public Content getLoginPage() {
        LoginMethods dialog = new LoginMethods();
        dialog.getAuthProviders().addAll(userAccess.getAvailableSocialProviders());
        return dialog;
    }

    @RequestMapping(value = "/auth.ajax", method = RequestMethod.POST)
    public void proceedFormLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        authenticationRequest.storeAnswer(request, ApiAnswerType.JSON);
        authenticationRequest.storeResponse(request, AuthenticationResponse.STATUS);
        request.getRequestDispatcher("/j_spring_security_check").forward(request, response);
    }
}
