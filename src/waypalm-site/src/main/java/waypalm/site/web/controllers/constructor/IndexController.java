package waypalm.site.web.controllers.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.domain.entity.Surface;
import waypalm.site.model.constructor.CreateSurfaceForm;
import waypalm.site.services.WorldService;
import waypalm.site.validation.ValidationGroups;
import waypalm.site.web.controllers.BaseController;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/constructor")
public class IndexController extends BaseController {
    @Autowired
    protected WorldService worldService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showWelcome(Locale locale) {
        return responseBuilder(locale).view("constructor/index");
    }

    @RequestMapping(value = "/surface", method = RequestMethod.GET)
    public ModelAndView getPlaneSurfaceWizard(Locale locale) {
        return responseBuilder(locale).view("constructor/surface");
    }

    @RequestMapping(value = "/surface", method = RequestMethod.POST)
    public ModelAndView createPlaneSurface(Locale locale, @Valid CreateSurfaceForm form, BindingResult formBinding) {
        if (form.getType() != null) {
            switch (form.getType()) {
                case GEOID:
                    validator.validate(form, formBinding, ValidationGroups.GeoidGeometry.class);
                    break;
                case PLANE:
                    validator.validate(form, formBinding, ValidationGroups.PlainGeometry.class);
                    break;
            }
        }
        if (formBinding.hasErrors()) {
            throw new IllegalArgumentException("invalid form");
        }

        form.setTitle(format.getMessage(locale, "constructor.defaultSurfaceName"));
        Surface surface = worldService.createSurface(auth.loadCurrentProfile(), form);
        return responseBuilder(locale).redirect("/constructor/s/" + surface.getId());
    }
}
