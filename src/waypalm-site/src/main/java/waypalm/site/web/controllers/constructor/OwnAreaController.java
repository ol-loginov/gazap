package waypalm.site.web.controllers.constructor;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.model.AnswerEntity;
import waypalm.site.model.constructor.CreateAreaForm;
import waypalm.site.services.AreaManager;
import waypalm.site.web.controllers.BaseController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/own/area")
public class OwnAreaController extends BaseController {
    @Inject
    AreaManager areaManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAreaList() {
        return view("own/area-list");
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView showAreaWizard() {
        return view("own/area-new");
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, params = "_response=json")
    public ModelAndView createArea(Locale locale, @Valid CreateAreaForm areaForm, BindingResult areaFormBinding) {
        AnswerEntity response = new AnswerEntity();
        if (areaFormBinding.hasErrors()) {
            response.setErrors(getValidationErrors(locale, areaFormBinding));
            return json(response);
        }

        areaManager.create(areaForm);

        return json(response);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, params = "_intent=preview")
    public ModelAndView createAreaPreview(CreateAreaForm areaForm) {
        return view("own/area-new.preview")
                .addObject("form", areaForm);
    }
}
