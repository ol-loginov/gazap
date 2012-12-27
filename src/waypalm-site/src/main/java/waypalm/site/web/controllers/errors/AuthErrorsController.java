package waypalm.site.web.controllers.errors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.web.controllers.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@RequestMapping("auth/error")
public class AuthErrorsController extends BaseController {
    @RequestMapping
    public ModelAndView getErrorPage(Locale locale, HttpServletRequest request) {
        return responseBuilder(locale).view("errors/auth");
    }
}
