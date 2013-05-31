package waypalm.site.web.controllers

import org.springframework.web.bind.annotation.RequestMapping
import javax.inject.Named
import javax.inject.Inject

Named
RequestMapping
public class LandingController [Inject](
        override var services: ControllerServices
): ControllerBase{
    RequestMapping(array("/index", "/index.*"))
    public fun welcome(): ModelAndView
    {
        return view("index")
    }
}