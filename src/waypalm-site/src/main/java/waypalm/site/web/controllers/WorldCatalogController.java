package waypalm.site.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class WorldCatalogController extends BaseController {
    @RequestMapping("/worlds")
    public ModelAndView worlds(Locale locale) {
        return responseBuilder(locale).view("worlds");
    }
}
