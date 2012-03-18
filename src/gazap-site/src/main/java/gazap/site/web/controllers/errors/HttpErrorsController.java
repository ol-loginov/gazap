package gazap.site.web.controllers.errors;

import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.errors.HttpError;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Locale;

@Controller
@RequestMapping("/errors")
public class HttpErrorsController extends BaseController {
    private ModelAndView genericHttp(Locale locale, String code, String... suggestions) {
        HttpError page = new HttpError();
        page.setHttp(code);
        page.getSuggestions().addAll(Arrays.asList(suggestions));
        return responseBuilder(locale).view("errors/http", page);
    }

    @RequestMapping({"/404", "/404.*"})
    public ModelAndView for404(Locale locale) {
        return genericHttp(locale, "404",
                "search",
                "email");
    }

    @RequestMapping({"/500", "/500.*"})
    public ModelAndView for500(Locale locale) {
        return genericHttp(locale, "500",
                "come-n-try",
                "email");
    }
}
