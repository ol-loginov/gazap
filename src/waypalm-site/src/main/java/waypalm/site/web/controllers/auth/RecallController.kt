package waypalm.site.web.controllers.auth

import org.springframework.web.bind.annotation.RequestMapping
import javax.inject.Named
import waypalm.site.web.controllers.ControllerBase
import waypalm.site.web.controllers.ControllerServices
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.RequestMethod
import waypalm.site.model.user.RestoreForm
import javax.inject.Inject

Named
RequestMapping(array("/auth/recall"))
public class RecallController [Inject](
        override var services: ControllerServices
): ControllerBase
{
    RequestMapping(method = array(RequestMethod.GET))
    public fun showForm(): ModelAndView {
        return view("auth/recall");
    }

    RequestMapping(method = array(RequestMethod.POST))
    public fun submitForm(form: RestoreForm): ModelAndView {
        return view("auth/recall")
                .addObject("form", form);
    }

}