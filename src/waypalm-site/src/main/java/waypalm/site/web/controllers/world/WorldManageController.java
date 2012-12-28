package waypalm.site.web.controllers.world;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.SurfaceGeometry;
import waypalm.domain.entity.SurfaceOrientation;
import waypalm.domain.entity.World;
import waypalm.site.exceptions.ObjectNotFoundException;
import waypalm.site.model.world.SurfaceCreateForm;
import waypalm.site.services.WorldService;
import waypalm.site.validation.ValidationGroups;
import waypalm.site.web.controllers.BaseController;
import waypalm.site.model.EntityApiAnswer;
import waypalm.common.web.controllers.Response;

import javax.validation.Valid;
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
                .addObject("world", world)
                .addObject("surfaceList", worldRepository.listSurfaceBelongsToWorld(world));
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

    @RequestMapping(value = "/{worldSlug}/surfaces/create", method = RequestMethod.GET)
    public ModelAndView createWorldSurface(Locale locale, @PathVariable String worldSlug) {
        World world = worldService.findWorldBySlug(worldSlug);
        return responseBuilder(locale).view("world/manage-world-surfaces-create")
                .addObject("world", world)
                .addObject("form", new SurfaceCreateForm())
                .addObject("surfaceGeometryList", SurfaceGeometry.values())
                .addObject("surfaceOrientationList", SurfaceOrientation.values());
    }

    @RequestMapping(value = "/{worldSlug}/surfaces/create", method = RequestMethod.POST)
    public ModelAndView createWorldSurface(Locale locale, @PathVariable String worldSlug, @Valid SurfaceCreateForm form, BindingResult formBinding) throws ObjectNotFoundException {
        World world = requireEntity(worldService.findWorldBySlug(worldSlug), worldSlug);
        EntityApiAnswer<Surface> answer = new EntityApiAnswer<>();

        Response response = responseBuilder(locale);
        if (!formBinding.hasErrors()) {
            switch (form.getGeometry()) {
                case PLANE:
                    validator.validate(form, formBinding, ValidationGroups.PlainGeometry.class);
                    break;
                default:
                    throw new IllegalArgumentException("surface kind is invalid");
            }
        }

        if (formBinding.hasErrors()) {
            answer.setSuccess(false);
            return response.json(answer);
        }

        Surface surface = worldService.createSurface(world, requireProfile(), form);
        answer.setEntity(surface);
        answer.setSuccess(true);
        return responseBuilder(locale).json(answer);
    }
}
