package waypalm.site.web.controllers.landing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.domain.dao.WorldRepository;
import waypalm.site.services.WorldService;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
public class IndexController extends BaseController {
    @Autowired
    WorldService worldService;

    @RequestMapping({"/index", "/index.*"})
    public ModelAndView welcome(Locale locale) {
        ModelAndView view = responseBuilder(locale).view("index");
        if (auth.isAuthorized()) {
            view.addObject("favouriteWorlds", worldService.getFavouriteWorlds(auth.getCurrentProfile()));
        }
        return view;
    }
}
