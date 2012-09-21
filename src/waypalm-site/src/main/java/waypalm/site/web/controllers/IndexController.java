package waypalm.site.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class IndexController extends BaseController {
    @RequestMapping({"/index", "/index.*"})
    public ModelAndView welcome(Locale locale) {
        return responseBuilder(locale).view("index");
    }

    @RequestMapping(value = "/brandMenu/account", params = "_target=bar")
    public ModelAndView accountMenu(Locale locale) {
        return responseBuilder(locale).view("brandMenu-account.bar");
    }

    @RequestMapping(value = "/brandMenu/site", params = "_target=bar")
    public ModelAndView siteMenu(Locale locale) {
        return responseBuilder(locale).view("brandMenu-site.bar");
    }
}
