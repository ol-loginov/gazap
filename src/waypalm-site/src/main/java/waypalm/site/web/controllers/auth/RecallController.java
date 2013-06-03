package waypalm.site.web.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.model.user.RestoreForm;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
@RequestMapping("/auth/recall")
public class RecallController extends BaseController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showForm(Locale locale) {
        return responseBuilder(locale).view("auth/recall");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView submitForm(Locale locale, RestoreForm form) {
        return responseBuilder(locale).view("auth/recall")
                .addObject("form", form);
    }
}
