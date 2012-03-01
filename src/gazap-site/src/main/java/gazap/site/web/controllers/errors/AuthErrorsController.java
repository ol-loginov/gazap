package gazap.site.web.controllers.errors;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.model.ServiceError;
import gazap.site.web.views.errors.AuthError;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class AuthErrorsController {
    public static final String ROUTE = "/errors/auth";

    @RequestMapping({ROUTE, ROUTE + ".*"})
    @ViewName(response = AuthError.class, name = "errors/auth")
    public AuthError forAuth(HttpServletRequest request) {
        AuthError response = new AuthError();

        AuthenticationException error = AuthErrorsCache.remove(request);
        if (error instanceof BadCredentialsException) {
            response.setErrorCode(ServiceError.AUTH_BAD_CREDENTIALS.code());
            response.getSuggestions().addAll(Arrays.asList("repeat", "restore", "register"));
        }
        return response;
    }

    public static void respond(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        AuthErrorsCache.store(request, exception);
        request.getRequestDispatcher(ROUTE).forward(request, response);
    }
}
