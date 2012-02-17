package gazap.site.web.controllers.access;

import com.iserv2.commons.mvc.views.Content;
import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.services.UserAccess;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.access.LoginMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController extends BaseController {
    public static final String FORM_LOGIN_ROUTE = "/auth";

    @Autowired
    protected UserAccess userAccess;

    @RequestMapping(value = FORM_LOGIN_ROUTE, method = RequestMethod.GET)
    @ViewName(name = "access/login", response = LoginMethods.class)
    public Content getLoginPage() {
        LoginMethods dialog = new LoginMethods();
        dialog.getAuthProviders().addAll(userAccess.getAvailableSocialProviders());
        return dialog;
    }
}
