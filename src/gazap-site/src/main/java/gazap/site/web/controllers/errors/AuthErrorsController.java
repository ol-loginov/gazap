package gazap.site.web.controllers.errors;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.model.ServiceError;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.mvc.AuthenticationRequest;
import gazap.site.web.views.access.AuthStatus;
import gazap.site.web.views.errors.AuthError;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;

@Controller
public class AuthErrorsController extends BaseController {
    public static final String ROUTE = "/errors/auth";

    private final AuthenticationRequest authenticationRequest = new AuthenticationRequest();

    @RequestMapping(ROUTE + ".html")
    @ViewName(response = AuthError.class, name = "errors/auth")
    public AuthError errorHtml(HttpServletRequest request) {
        AuthError response = new AuthError();

        AuthenticationException error = authenticationRequest.retrieveError(request);
        if (error instanceof BadCredentialsException) {
            response.setErrorCode(ServiceError.AUTH_BAD_CREDENTIALS.code());
            response.getSuggestions().addAll(Arrays.asList("repeat", "restore", "register"));
        }
        return response;
    }

    @RequestMapping(ROUTE + ".json")
    public ModelAndView errorJson(Locale locale, HttpServletRequest request) {
        AuthStatus status = new AuthStatus();
        status.setSuccess(false);

        AuthenticationException error = authenticationRequest.retrieveError(request);
        if (error instanceof BadCredentialsException) {
            status.setCode(ServiceError.AUTH_BAD_CREDENTIALS.code());
            status.setMessage(format.getMessage(locale, status.getCode()));
        }
        return json(status);
    }
}
