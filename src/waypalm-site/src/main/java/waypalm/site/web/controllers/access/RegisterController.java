package waypalm.site.web.controllers.access;

import waypalm.site.web.model.ApiAnswer;
import waypalm.site.services.UserService;
import waypalm.site.web.controllers.BaseController;
import waypalm.site.web.controllers.ResponseBuilder;
import waypalm.site.web.views.access.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class RegisterController extends BaseController {
    @Autowired
    protected UserService userService;

    @RequestMapping(value = "/auth/register.ajax", method = RequestMethod.GET)
    public ModelAndView showFormAjax(Locale locale) {
        Register response = new Register();
        response.setForm(new RegisterForm());
        return responseBuilder(locale).view("access/register.ajax", response);
    }

    @RequestMapping(value = "/auth/register.ajax", method = RequestMethod.POST)
    public ModelAndView submitForm(Locale locale, @Valid RegisterForm form, BindingResult formBinding) {
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
