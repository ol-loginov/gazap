package waypalm.site.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.services.WorldService;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
public class LandingController extends BaseController {
    @Autowired
    WorldService worldService;

    @RequestMapping({"/index", "/index.*"})
    public ModelAndView welcome(Locale locale) {
        return responseBuilder(locale).view("index");
    }
}