package waypalm.site.web.controllers.auth

import waypalm.site.web.controllers.ControllerBase
import waypalm.site.web.controllers.ControllerServices
import org.springframework.web.bind.annotation.RequestMapping
import javax.inject.Named
import waypalm.site.services.UserService
import javax.inject.Inject
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.RequestMethod
import javax.validation.Valid
import org.springframework.validation.BindingResult
import waypalm.site.model.user.RegisterForm
import java.util.Locale

Named
RequestMapping(array("/auth/register"))
public class RegisterController [Inject](
        var userService: UserService,
        override var services: ControllerServices
): ControllerBase
{
    RequestMapping(method = array(RequestMethod.GET))
    public fun showRegistrationForm(): ModelAndView {
        return view("auth/register");
    }

    RequestMapping(method = array(RequestMethod.POST))
    public fun proceedRegistration(locale: Locale, Valid form: RegisterForm, formBinding: BindingResult): ModelAndView {
        if (formBinding.hasErrors()) {
            return view("auth/register")
                    .addObject("form", form)
                    .addObject("formErrors", getValidationErrors(locale, formBinding));
        }
        userService.createUser(form.getUsername(), form.getPassword());
        return forward("/auth");
    }

}