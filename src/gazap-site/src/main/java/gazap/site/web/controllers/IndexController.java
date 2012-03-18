package gazap.site.web.controllers;

import gazap.site.web.views.Welcome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class IndexController extends BaseController {
    @RequestMapping({"/index", "/index.*"})
    public ModelAndView welcome(Locale locale) {
        return responseBuilder(locale).view("index", new Welcome());
    }
}
