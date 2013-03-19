package waypalm.site.web.controllers.constructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
@RequestMapping("/tools/area")
public class ToolsAreaController extends BaseController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAreaList(Locale locale) {
        return responseBuilder(locale).view("tools/area-list");
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView showAreaWizard(Locale locale) {
        return responseBuilder(locale).view("tools/area-new");
    }
}
