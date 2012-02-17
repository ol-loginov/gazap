package gazap.panel.web.controllers;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.panel.web.views.Welcome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {
    @RequestMapping({"/index", "/index.*"})
    @ViewName(response = Welcome.class, name = "index")
    public Welcome welcome() {
        return new Welcome();
    }
}
