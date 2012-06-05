package gazap.site.web.controllers.world;

import gazap.domain.entity.World;
import gazap.site.web.model.ApiAnswer;
import gazap.site.model.viewer.WorldTitle;
import gazap.site.services.WorldService;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.controllers.ResponseBuilder;
import gazap.site.web.views.world.WorldCreatePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class WorldCreateController extends BaseController {
    @Autowired
    protected WorldService worldService;

    @RequestMapping(value = "/world/create.ajax", method = RequestMethod.GET)
    public ModelAndView formAjax(Locale locale) {
        WorldCreatePage page = new WorldCreatePage();
        page.setForm(new WorldCreateForm());
        return responseBuilder(locale).view("world/create.ajax", page);
    }

    @RequestMapping(value = "/world/create.ajax", method = RequestMethod.POST)
    public ModelAndView submitAjax(Locale locale, @Valid WorldCreateForm form, BindingResult formBinding) {
        ResponseBuilder responseBuilder = responseBuilder(locale);
        CreateFormApiAnswer answer = new CreateFormApiAnswer();
        if (formBinding.hasErrors()) {
            responseBuilder.validationErrors(answer, formBinding);
        } else {
            World world = worldService.createWorld(securityHelper.getCurrentUser(), form);
            answer.setWorld(viewer.worldTitle(world));
            answer.setSuccess(true);
        }
        return responseBuilder.json(answer);
    }

    public static class CreateFormApiAnswer extends ApiAnswer {
        private WorldTitle world;

        public WorldTitle getWorld() {
            return world;
        }

        public void setWorld(WorldTitle world) {
            this.world = world;
        }
    }
}
