package waypalm.site.web.mvc;

import waypalm.site.web.model.ApiAnswerType;
import waypalm.site.web.controllers.user.StatusController;
import waypalm.site.web.controllers.errors.AuthErrorsController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Service("authenticationHandler")
public class AuthenticationHandler implements AuthenticationFailureHandler, AuthenticationSuccessHandler {
    private final AuthenticationRequestHelper authenticationRequest = new AuthenticationRequestHelper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        authenticationRequest.setError(request, exception);
        forward(request, response, AuthErrorsController.ROUTE);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String baseUrl) throws IOException, ServletException {
        request.getRequestDispatcher(typedRoute(baseUrl, authenticationRequest.retrieveAnswer(request))).forward(request, response);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthenticationResponse responseView = authenticationRequest.retrieveResponse(request);
        switch (responseView) {
            case STATUS:
                forward(request, response, StatusController.ROUTE);
                break;
            default:
                throw new IllegalStateException("not implemented response for view " + responseView);
        }
    }

    private String typedRoute(String baseUrl, ApiAnswerType answerType) {
        return answerType == null
                ? baseUrl
                : String.format("%s.%s", baseUrl, answerType.toString().toLowerCase(Locale.ENGLISH));
    }
}
