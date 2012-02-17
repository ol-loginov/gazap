package gazap.site.web.controllers.access;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.access.Register;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController extends BaseController {
    @RequestMapping(value = "/auth/register", method = RequestMethod.GET)
    @ViewName(name = "access/register", response = Register.class)
    public Register showForm() {
        Register response = new Register();
        response.setForm(new RegisterForm());
        return response;
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    @ViewName(name = "access/register", response = Register.class)
    public Register submitForm(RegisterForm form) {
        Register response = new Register();
        response.setForm(form);
        return response;
    }
}
