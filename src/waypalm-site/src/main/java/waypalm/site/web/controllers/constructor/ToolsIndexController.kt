package waypalm.site.web.controllers.constructor

import waypalm.site.web.controllers.ControllerBase
import waypalm.site.web.controllers.ControllerServices
import javax.inject.Inject
import org.springframework.web.bind.annotation.RequestMapping
import javax.inject.Named
import waypalm.site.web.controllers.ModelAndView
import org.springframework.web.bind.annotation.RequestMethod

Named
RequestMapping(array("/tools"))
public class ToolsIndexController [Inject](
        override var services: ControllerServices
): ControllerBase
{
    RequestMapping(method = array(RequestMethod.GET))
    public fun showWelcome(): ModelAndView {
        return view("tools/index")
    }
}