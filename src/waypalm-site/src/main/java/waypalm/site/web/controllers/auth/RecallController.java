package waypalm.site.web.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.model.user.RestoreForm;
import waypalm.site.web.controllers.BaseController2;

@Controller
@RequestMapping("/auth/recall")
public class RecallController extends BaseController2 {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showForm() {
        return view("auth/recall");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView submitForm(RestoreForm form) {
        return view("auth/recall")
                .addObject("form", form);
    }
}
