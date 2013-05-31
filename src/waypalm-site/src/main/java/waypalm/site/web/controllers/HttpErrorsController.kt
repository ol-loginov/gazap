package waypalm.site.web.controllers

import org.springframework.web.bind.annotation.RequestMapping
import javax.inject.Named
import javax.inject.Inject

Named
RequestMapping(array("/errors"))
public class HttpErrorsController [Inject](
        override var services: ControllerServices
): ControllerBase {
    fun genericHttp(code: String): ModelAndView {
        return view("errors/http." + code);
    }

    RequestMapping(array("/404", "/404.*"))
    public fun for404(): ModelAndView {
        return genericHttp("404");
    }

    RequestMapping(array("/500", "/500.*"))
    public fun for500(): ModelAndView {
        return genericHttp("500");
    }
}