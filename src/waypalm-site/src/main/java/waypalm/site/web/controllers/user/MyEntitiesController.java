package waypalm.site.web.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.domain.entity.World;
import waypalm.site.model.world.WorldCreateForm;
import waypalm.site.services.WorldService;
import waypalm.site.web.controllers.BaseController;
import waypalm.site.web.controllers.EntityApiAnswer;
import waypalm.site.web.controllers.ResponseBuilder;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/my")
public class MyEntitiesController extends BaseController {
    @Autowired
    WorldService worldService;

    @RequestMapping("/avatars")
    public ModelAndView myAvatars(Locale locale) {
        return responseBuilder(locale).view("user/my-avatars");
    }

    @RequestMapping("/worlds")
    public ModelAndView myWorlds(Locale locale) {
        return responseBuilder(locale).view("user/my-worlds");
    }

    @RequestMapping(value = "/new/world", method = RequestMethod.GET)
    public ModelAndView createWorldForm(Locale locale) {
        return responseBuilder(locale).view("world/create-world")
                .addObject("form", new WorldCreateForm());
    }

    @RequestMapping(value = "/new/world", method = RequestMethod.POST, params = "_response=json")
    public ModelAndView createWorld(Locale locale, @Valid WorldCreateForm form, BindingResult formBinding) {
        ResponseBuilder responseBuilder = responseBuilder(locale);
        EntityApiAnswer<World> response = new EntityApiAnswer<>();
        if (formBinding.hasErrors()) {
            responseBuilder.validationErrors(response, formBinding);
        } else {
            response.setEntity(worldService.createWorld(requireProfile(), form));
            response.setSuccess(true);
        }
        return responseBuilder.json(response);
    }

}
