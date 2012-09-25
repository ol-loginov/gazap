package waypalm.site.web.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
@RequestMapping("/i/worlds")
public class WorldCatalogController extends BaseController {
    @RequestMapping("")
    public ModelAndView showIndex(Locale locale) {
        return responseBuilder(locale).view("user/worlds");
    }
}
