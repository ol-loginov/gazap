package gazap.site.web.controllers.access;

import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.access.Restore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class RestoreController extends BaseController {
    @RequestMapping(value = "/auth/restore", method = RequestMethod.GET)
    public ModelAndView showForm(Locale locale) {
        Restore response = new Restore();
        response.setForm(new RestoreForm());
        return responseBuilder(locale).view("access/restore", response);
    }

    @RequestMapping(value = "/auth/restore", method = RequestMethod.POST)
    public ModelAndView submitForm(Locale locale, RestoreForm form) {
        Restore response = new Restore();
        response.setForm(form);
        return responseBuilder(locale).view("access/restore", response);
    }
}
