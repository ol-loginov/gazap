package waypalm.site.web.controllers.constructor

import waypalm.site.web.controllers.ControllerBase
import waypalm.site.web.controllers.ControllerServices
import javax.inject.Inject
import javax.inject.Named
import org.springframework.web.bind.annotation.RequestMapping
import waypalm.site.web.controllers.ModelAndView
import org.springframework.web.bind.annotation.RequestMethod
import java.util.Locale
import org.springframework.validation.BindingResult
import waypalm.site.model.constructor.CreateAreaForm
import javax.validation.Valid
import waypalm.site.model.AnswerEntity

Named
RequestMapping(array("/tools/area"))
public class ToolsAreaController [Inject](
        override var services: ControllerServices
): ControllerBase
{
    RequestMapping(method = array(RequestMethod.GET))
    public fun showAreaList(): ModelAndView {
        return view("tools/area-list");
    }

    RequestMapping(value = array("/new"), method = array(RequestMethod.GET))
    public fun showAreaWizard(): ModelAndView {
        return view("tools/area-new");
    }

    RequestMapping(value = array("/new"), method = array(RequestMethod.POST), params = array("_response=json"))
    public fun createArea(locale: Locale, Valid areaForm: CreateAreaForm, areaFormBinding: BindingResult): ModelAndView {
        var response = AnswerEntity<Any>();
        if (areaFormBinding.hasErrors()) {
            response.setErrors(getValidationErrors(locale, areaFormBinding));
        }
        return json(response);
    }

    RequestMapping(value = array("/new"), method = array(RequestMethod.POST), params = array("_intent=preview"))
    public fun createAreaPreview(areaForm: CreateAreaForm): ModelAndView {
        return view("tools/area-new.preview")
                .addObject("form", areaForm);
    }
}