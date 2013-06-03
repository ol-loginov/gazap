package waypalm.site.web.controllers.landing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.domain.dao.WorldRepository;
import waypalm.site.web.controllers.BaseController;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;

@Named
@RequestMapping
public class CatalogWorldController extends BaseController {
    @Inject
    WorldRepository worldRepository;

    @RequestMapping("/worlds")
    public ModelAndView worlds(Locale locale) {
        return responseBuilder(locale).view("worlds")
                .addObject("worldList", worldRepository.listWorld());
    }
}
