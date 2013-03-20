package waypalm.site.web.controllers.constructor;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import waypalm.site.model.AnswerEntity;
import waypalm.site.model.constructor.CreateAreaForm;
import waypalm.site.web.controllers.BaseController;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/tools/area")
public class ToolsAreaController extends BaseController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAreaList(Locale locale) {
        return responseBuilder(locale).view("tools/area-list");
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView showAreaWizard(Locale locale) {
        return responseBuilder(locale).view("tools/area-new");
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, params = "_response=json")
    public ModelAndView createArea(Locale locale, @Valid CreateAreaForm areaForm, BindingResult areaFormBinding) {
        AnswerEntity response = new AnswerEntity();
        if (areaFormBinding.hasErrors()) {
            response.setErrors(responseBuilder(locale).getValidationErrors(areaFormBinding));
        }
        return responseBuilder(locale).json(response);
    }
}
