package gazap.site.web.controllers.access;

import com.iserv2.commons.mvc.views.Content;
import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.model.ApiAnswer;
import gazap.site.services.UserService;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.controllers.ResponseBuilder;
import gazap.site.web.views.access.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class RegisterController extends BaseController {
    @Autowired
    protected UserService userService;

    @RequestMapping(value = "/auth/register.ajax", method = RequestMethod.GET)
    @ViewName(name = "access/register.ajax", response = Register.class)
    public Register showFormAjax() {
        Register response = new Register();
        response.setForm(new RegisterForm());
        return response;
    }

    @RequestMapping(value = "/auth/register.ajax", method = RequestMethod.POST)
    public Content submitForm(Locale locale, @Valid RegisterForm form, BindingResult formBinding) {
        ResponseBuilder responseBuilder = responseBuilder(locale);
        ApiAnswerWithLoginUrl response = new ApiAnswerWithLoginUrl();
        if (formBinding.hasErrors()) {
            responseBuilder.validationErrors(response, formBinding);
        } else {
            userService.createUser(form.getUsername(), form.getPassword());
            response.setSuccess(true);
            response.setLoginUrl(LoginController.LOGIN_ROUTE_AJAX);
        }
        return responseBuilder.json(response);
    }

    public static class ApiAnswerWithLoginUrl extends ApiAnswer {
        private String loginUrl;

        public String getLoginUrl() {
            return loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }
    }
}
