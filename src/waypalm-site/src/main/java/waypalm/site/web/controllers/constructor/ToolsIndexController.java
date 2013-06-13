package waypalm.site.web.controllers.constructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.web.controllers.BaseController;

@Controller
@RequestMapping("/tools")
public class ToolsIndexController extends BaseController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showWelcome() {
        return view("tools/index");
    }
}
