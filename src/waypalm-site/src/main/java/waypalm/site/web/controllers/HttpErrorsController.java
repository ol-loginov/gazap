package waypalm.site.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/errors")
public class HttpErrorsController extends BaseController {
    private ModelAndView genericHttp(String code) {
        return view("errors/http." + code);
    }

    @RequestMapping({"/404", "/404.*"})
    public ModelAndView for404() {
        return genericHttp("404");
    }

    @RequestMapping({"/500", "/500.*"})
    public ModelAndView for500() {
        return genericHttp("500");
    }
}
