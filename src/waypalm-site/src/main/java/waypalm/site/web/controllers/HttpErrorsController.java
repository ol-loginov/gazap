package waypalm.site.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
@RequestMapping("/errors")
public class HttpErrorsController extends BaseController {
    private ModelAndView genericHttp(Locale locale, String code) {
        return responseBuilder(locale).view("errors/http." + code);
    }

    @RequestMapping({"/404", "/404.*"})
    public ModelAndView for404(Locale locale) {
        return genericHttp(locale, "404");
    }

    @RequestMapping({"/500", "/500.*"})
    public ModelAndView for500(Locale locale) {
        return genericHttp(locale, "500");
    }
}
