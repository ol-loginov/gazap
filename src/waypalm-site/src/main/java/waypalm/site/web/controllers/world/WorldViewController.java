package waypalm.site.web.controllers.world;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import waypalm.common.util.ObjectUtil;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.World;
import waypalm.site.exceptions.ObjectNotFoundException;
import waypalm.site.web.controllers.BaseController;

import java.util.Locale;

@Controller
@RequestMapping("/w")
public class WorldViewController extends BaseController {
    @Autowired
    WorldRepository worldRepository;

    @RequestMapping("/{worldSlug}")
    public ModelAndView viewWorld(Locale locale, @PathVariable String worldSlug) throws ObjectNotFoundException {
        World world = loadWorldBySlug(worldSlug);
        if (world == null) {
            throw new ObjectNotFoundException(World.class, worldSlug);
        }
        return responseBuilder(locale).view("world/world-view")
                .addObject("world", world)
                .addObject("worldPublishing", worldRepository.getWorldPublishing(world));
    }

    private World loadWorldBySlug(String worldSlug) {
        if (!StringUtils.hasText(worldSlug)) {
            return null;
        }
        if (ObjectUtil.isInteger(worldSlug)) {
            return worldRepository.getWorld(Integer.parseInt(worldSlug));
        } else {
            return worldRepository.findWorldByAlias(worldSlug);
        }
    }
}
