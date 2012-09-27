package waypalm.site.web.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
public class MyAvatarsController extends BaseController {
    @RequestMapping("/my/avatars")
    public ModelAndView showIndex(Locale locale) {
        return responseBuilder(locale).view("user/my-avatars");
    }
}
