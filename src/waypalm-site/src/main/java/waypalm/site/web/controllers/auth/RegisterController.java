package waypalm.site.web.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.common.web.controllers.Response;
import waypalm.site.model.user.RegisterForm;
import waypalm.site.services.UserService;
import waypalm.site.web.controllers.BaseController;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/auth/register")
public class RegisterController extends BaseController {
    @Autowired
    protected UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showRegistrationForm(Locale locale) {
        return responseBuilder(locale).view("auth/register");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView proceedRegistration(Locale locale, @Valid RegisterForm form, BindingResult formBinding) {
        Response response = responseBuilder(locale);
        if (formBinding.hasErrors()) {
            return response.view("auth/register")
                    .addObject("form", form)
                    .addObject("formErrors", response.getValidationErrors(formBinding));
        }
        userService.createUser(form.getUsername(), form.getPassword());
        return response.forward("/auth");
    }
}
