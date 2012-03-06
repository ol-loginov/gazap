package gazap.site.web.controllers.access;

import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.access.AuthStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StatusController extends BaseController {
    public final static String ROUTE = "/auth/status";

    @RequestMapping(ROUTE + ".json")
    public ModelAndView statusJson() {
        return json(new AuthStatus());
    }
}
