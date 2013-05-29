package waypalm.site.web.controllers.constructor;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.model.AnswerEntity;
import waypalm.site.model.constructor.CreateAreaForm;
import waypalm.site.web.controllers.BaseController2;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/tools/area")
public class ToolsAreaController extends BaseController2 {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAreaList() {
        return view("tools/area-list");
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView showAreaWizard() {
        return view("tools/area-new");
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, params = "_response=json")
    public ModelAndView createArea(Locale locale, @Valid CreateAreaForm areaForm, BindingResult areaFormBinding) {
        AnswerEntity response = new AnswerEntity();
        if (areaFormBinding.hasErrors()) {
            response.setErrors(getValidationErrors(locale, areaFormBinding));
        }
        return json(response);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, params = "_intent=preview")
    public ModelAndView createAreaPreview(CreateAreaForm areaForm) {
        return view("tools/area-new.preview")
                .addObject("form", areaForm);
    }
}
