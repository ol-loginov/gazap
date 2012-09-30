package waypalm.site.web.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
public class MySettingsController extends BaseController {
    @RequestMapping("/my/settings")
    public ModelAndView settingsPage(Locale locale) {
        return responseBuilder(locale).view("user/my-settings");
    }

    @RequestMapping(value = "/my/settings", method = RequestMethod.GET, params = "_target=tab")
    public ModelAndView settingsTab(Locale locale) {
        return responseBuilder(locale).view("user/_my-settings-home");
    }

    @RequestMapping(value = "/my/socials", method = RequestMethod.GET, params = "_target=tab")
    public ModelAndView socialTab(Locale locale) {
        return responseBuilder(locale).view("user/_my-settings-socials");
    }
}
