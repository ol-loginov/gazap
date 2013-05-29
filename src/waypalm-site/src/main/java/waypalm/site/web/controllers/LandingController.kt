package waypalm.site.web.controllers

import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.stereotype.Controller
import waypalm.site.services.UserAccess
import org.springframework.beans.factory.annotation.Autowired

Controller
public class LandingController(): BaseController(){
    RequestMapping(array("/index", "/index.*"))
    public fun welcome(): ModelAndView
    {
        return view("index")
    }
}