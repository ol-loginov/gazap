package gazap.panel.web.controllers.access;

import com.iserv2.commons.mvc.views.Content;
import com.iserv2.commons.mvc.views.ViewName;
import gazap.panel.web.controllers.BaseController;
import gazap.panel.web.views.access.LoginDialog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController extends BaseController {
    public static final String FORM_LOGIN_ROUTE = "/auth";

    @RequestMapping(value = FORM_LOGIN_ROUTE, method = RequestMethod.GET)
    @ViewName(name = "access/login", response = LoginDialog.class)
    public Content getLoginPage(HttpServletRequest request) {
        return new LoginDialog();
    }
}
