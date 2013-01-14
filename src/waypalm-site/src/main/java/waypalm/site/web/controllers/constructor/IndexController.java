package waypalm.site.web.controllers.constructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
@RequestMapping("/constructor")
public class IndexController extends BaseController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showWelcome(Locale locale) {
        return responseBuilder(locale).view("constructor/index");
    }

    @RequestMapping(value = "/surface", method = RequestMethod.POST)
    public ModelAndView newSurface(Locale locale) {

        return responseBuilder(locale).view("constructor/surface");
    }

    @RequestMapping(value = "/world", method = RequestMethod.POST)
    public ModelAndView newWorld(Locale locale) {
        return responseBuilder(locale).view("constructor/world");
    }
}
