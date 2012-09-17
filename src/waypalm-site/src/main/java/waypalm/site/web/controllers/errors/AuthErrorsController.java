package waypalm.site.web.controllers.errors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.model.ServiceError;
import waypalm.site.web.controllers.BaseController;
import waypalm.site.web.model.ApiAnswer;
import waypalm.site.web.mvc.AuthenticationRequestHelper;
import waypalm.site.web.views.errors.AuthError;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;

@Controller
public class AuthErrorsController extends BaseController {
    public static final String ROUTE = "/errors/auth";

    private final AuthenticationRequestHelper authenticationRequestHelper = new AuthenticationRequestHelper();

    @RequestMapping(ROUTE + ".html")
    public ModelAndView errorHtml(Locale locale, HttpServletRequest request) {
        AuthError response = new AuthError();

        AuthenticationException error = authenticationRequestHelper.getError(request);
        if (error instanceof BadCredentialsException) {
            response.setErrorCode(ServiceError.AUTH_BAD_CREDENTIALS.code());
            response.getSuggestions().addAll(Arrays.asList("repeat", "restore", "register"));
        }

        return responseBuilder(locale).view("errors/auth", response);
    }

    @RequestMapping(ROUTE + ".json")
    public ModelAndView errorJson(Locale locale, HttpServletRequest request) {
        ApiAnswer status = new ApiAnswer();
        status.setSuccess(false);

        AuthenticationException error = authenticationRequestHelper.getError(request);
        if (error instanceof BadCredentialsException) {
            status.setCode(ServiceError.AUTH_BAD_CREDENTIALS.code());
        } else {
            status.setCode(ServiceError.AUTH_FAILED.code());
        }

        if (status.getCode() != null && status.getMessage() == null) {
            status.setMessage(format.getMessage(locale, status.getCode()));
        }
        return responseBuilder(locale).json(status);
    }
}
