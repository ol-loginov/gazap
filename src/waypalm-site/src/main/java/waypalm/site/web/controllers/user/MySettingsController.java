package waypalm.site.web.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
public class MySettingsController extends BaseController {
    @RequestMapping("/my/publicity")
    public ModelAndView viewPublicity(Locale locale) {
        return responseBuilder(locale).view("user/settings-publicity");
    }

    @RequestMapping("/my/socials")
    public ModelAndView viewSocials(Locale locale) {
        return responseBuilder(locale).view("user/settings-socials");
    }
}
