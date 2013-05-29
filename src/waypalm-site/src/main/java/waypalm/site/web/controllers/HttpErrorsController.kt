package waypalm.site.web.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import waypalm.site.services.UserAccess
import org.springframework.beans.factory.annotation.Autowired

Controller
RequestMapping(array("/errors"))
public class HttpErrorsController(): BaseController() {

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