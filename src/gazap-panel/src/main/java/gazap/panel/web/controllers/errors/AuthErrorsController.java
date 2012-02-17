package gazap.panel.web.controllers.errors;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.panel.web.views.errors.ErrorPage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AuthErrorsController {
    public static final String ROUTE = "/errors/auth";

    @RequestMapping({ROUTE, ROUTE + ".*"})
    @ViewName(response = ErrorPage.class, name = "errors/auth")
    public ErrorPage forAuth() {
        return new ErrorPage();
    }

    public static void respond(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        AuthErrorsCache.store(request, exception);
        request.getRequestDispatcher(ROUTE).forward(request, response);
    }
}
