package waypalm.site.web.controllers.world;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.World;
import waypalm.site.services.WorldService;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
@RequestMapping("/manage/w")
public class WorldManageController extends BaseController {
    @Autowired
    WorldRepository worldRepository;
    @Autowired
    WorldService worldService;

    @RequestMapping("/{worldSlug}/surfaces")
    public ModelAndView manageSurfaces(Locale locale, @PathVariable String worldSlug) {
        World world = worldService.findWorldBySlug(worldSlug);
        return responseBuilder(locale).view("world/manage-world-surfaces")
                .addObject("world", world);
    }

    @RequestMapping("/{worldSlug}/details")
    public ModelAndView manageDetails(Locale locale, @PathVariable String worldSlug) {
        World world = worldService.findWorldBySlug(worldSlug);
        return responseBuilder(locale).view("world/manage-world-details")
                .addObject("world", world);
    }

    @RequestMapping("/{worldSlug}/publishing")
    public ModelAndView managePublishing(Locale locale, @PathVariable String worldSlug) {
        World world = worldService.findWorldBySlug(worldSlug);
        return responseBuilder(locale).view("world/manage-world-publishing")
                .addObject("world", world);
    }
}
