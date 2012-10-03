package waypalm.site.web.controllers.map;

import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.Geometry;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.World;
import waypalm.site.model.viewer.SurfaceTitle;
import waypalm.site.model.world.MapCreateForm;
import waypalm.site.services.WorldService;
import waypalm.site.web.controllers.BaseController;
import waypalm.site.web.controllers.ResponseBuilder;
import waypalm.site.web.model.ApiAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class MapCreateController extends BaseController {
    @Autowired
    protected WorldService worldService;
    @Autowired
    protected WorldRepository worldRepository;

    @RequestMapping(value = "/map/create", method = RequestMethod.GET)
    public ModelAndView getForm(Locale locale) {
        return responseBuilder(locale).view("map/create");
    }

    @RequestMapping(value = "/w/{worldAlias}/map/create", method = RequestMethod.POST, params = {"_response=json"})
    public ModelAndView create(Locale locale, @PathVariable String worldAlias, @Valid MapCreateForm form, BindingResult binding) {
        if (Geometry.Geoid.CLASS.equals(form.getGeometryClass())) {
            ValidationUtils.invokeValidator(validator, form, binding, Geometry.Geoid.class);
        } else if (Geometry.Plain.CLASS.equals(form.getGeometryClass())) {
            ValidationUtils.invokeValidator(validator, form, binding, Geometry.Plain.class);
        }

        World world = worldRepository.findWorldByAlias(worldAlias);

        ResponseBuilder response = responseBuilder(locale);
        MapCreateFormResult answer = new MapCreateFormResult();
        if (binding.hasErrors()) {
            response.validationErrors(answer, binding);
        } else {
            Surface surface = worldService.createSurface(world, securityHelper.getCurrentUser(), form);
            answer.setEntity(viewer.surfaceTitle(surface));
            answer.setSuccess(true);
        }
        return response.json(answer);
    }

    public static class MapCreateFormResult extends ApiAnswer {
        private SurfaceTitle entity;

        public SurfaceTitle getEntity() {
            return entity;
        }

        public void setEntity(SurfaceTitle entity) {
            this.entity = entity;
        }
    }
}
