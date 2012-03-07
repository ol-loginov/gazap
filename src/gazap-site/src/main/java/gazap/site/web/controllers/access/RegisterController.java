package gazap.site.web.controllers.access;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.model.ApiAnswer;
import gazap.site.model.ServiceError;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.access.Register;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class RegisterController extends BaseController {
    @RequestMapping(value = "/auth/register.ajax", method = RequestMethod.GET)
    @ViewName(name = "access/register.ajax", response = Register.class)
    public Register showFormAjax() {
        Register response = new Register();
        response.setForm(new RegisterForm());
        return response;
    }

    @RequestMapping(value = "/auth/register.ajax", method = RequestMethod.POST)
    public ModelAndView submitForm(Locale locale, @Valid RegisterForm form, BindingResult formBinding) {
        ApiAnswer response = new ApiAnswer();
        if (formBinding.hasErrors()) {
            response.setSuccess(false);
            response.setCode(ServiceError.VALIDATION_FAILED.code());
            response.setMessage(format.getMessage(locale, response.getCode()));
            response.setErrorList(storeErrors(formBinding.getAllErrors(), locale));
        }
        return json(response);
    }
}
