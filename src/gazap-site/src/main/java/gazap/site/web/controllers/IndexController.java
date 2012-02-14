package gazap.site.web.controllers;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.web.views.WelcomePage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {
    @RequestMapping({"/index", "/index.*"})
    @ViewName(response = WelcomePage.class, name = "index")
    public WelcomePage showCatalog() {
        return new WelcomePage();
    }
}
