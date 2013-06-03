package waypalm.site.web.controllers.landing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
public class CatalogAvatarController extends BaseController {
    @RequestMapping("/avatars")
    public ModelAndView avatars(Locale locale) {
        return responseBuilder(locale).view("avatars");
    }
}
