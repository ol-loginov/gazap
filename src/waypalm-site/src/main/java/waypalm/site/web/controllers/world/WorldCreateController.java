package waypalm.site.web.controllers.world;

import waypalm.domain.entity.World;
import waypalm.site.model.viewer.WorldTitle;
import waypalm.site.services.WorldService;
import waypalm.site.web.controllers.BaseController;
import waypalm.site.web.controllers.ResponseBuilder;
import waypalm.site.web.model.ApiAnswer;
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
        return responseBuilder(locale).view("world/create.ajax")
                .addObject("form", new WorldCreateForm());
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
