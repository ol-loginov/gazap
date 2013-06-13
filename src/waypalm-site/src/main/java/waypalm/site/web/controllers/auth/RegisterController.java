package waypalm.site.web.controllers.auth;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.model.user.RegisterForm;
import waypalm.site.services.UserService;
import waypalm.site.web.controllers.BaseController;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import java.util.Locale;

@Named
@RequestMapping("/auth/register")
public class RegisterController extends BaseController {
    @Inject
    protected UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showRegistrationForm() {
        return view("auth/register");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView proceedRegistration(Locale locale, @Valid RegisterForm form, BindingResult formBinding) {
        if (formBinding.hasErrors()) {
            return view("auth/register")
                    .addObject("form", form)
                    .addObject("formErrors", getValidationErrors(locale, formBinding));
        }
        userService.createUser(form.getUsername(), form.getPassword());
        return forward("/auth");
    }
}
