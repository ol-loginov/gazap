package waypalm.site.web.controllers.landing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.domain.dao.WorldRepository;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
public class CatalogWorldController extends BaseController {
    @Autowired
    WorldRepository worldRepository;

    @RequestMapping("/worlds")
    public ModelAndView worlds(Locale locale) {
        return responseBuilder(locale).view("worlds")
                .addObject("worldList", worldRepository.listWorld());
    }
}
