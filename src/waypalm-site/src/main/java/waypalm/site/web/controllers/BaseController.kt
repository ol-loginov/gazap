package waypalm.site.web.controllers

import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.WebDataBinder
import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.web.servlet.view.UrlBasedViewResolver

abstract class BaseController() {
    InitBinder
    public fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(javaClass<String>(), StringTrimmerEditor(true))
    }

    public fun view(name: String): ModelAndView {
        return ModelAndView(name);
    }

    public fun json(value: Unit): ModelAndView {
        return view("json").addObject("content", value)!!
    }

    public fun redirect(url: String): ModelAndView {
        return view(UrlBasedViewResolver.REDIRECT_URL_PREFIX + url);
    }

    public fun forward(url: String): ModelAndView {
        return view(UrlBasedViewResolver.FORWARD_URL_PREFIX + url);
    }
}