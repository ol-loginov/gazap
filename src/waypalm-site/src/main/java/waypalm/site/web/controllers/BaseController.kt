package waypalm.site.web.controllers

import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.WebDataBinder
import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.web.servlet.view.UrlBasedViewResolver
import waypalm.domain.entity.Profile
import org.springframework.security.authentication.InsufficientAuthenticationException
import waypalm.site.services.UserAccess
import javax.inject.Inject
import waypalm.common.web.model.ObjectErrors
import org.springframework.validation.Errors
import java.util.Locale
import org.springframework.context.MessageSourceResolvable
import waypalm.common.services.FormatService
import waypalm.common.util.firstSure
import waypalm.domain.entity.World
import java.io.Serializable
import waypalm.site.exceptions.ObjectNotFoundException
import waypalm.site.services.ModelViewer
import org.springframework.validation.SmartValidator

abstract class BaseController() {
    Inject
    protected var auth: UserAccess? = null
    Inject
    protected var format: FormatService? = null
    Inject
    protected var viewer: ModelViewer ? = null
    Inject
    protected var validator: SmartValidator? = null

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

    public fun currentProfile(): Profile {
        var profile = auth!!.loadCurrentProfile();
        if (profile == null) {
            throw InsufficientAuthenticationException("require authenticated visitor");
        }
        return profile!!;
    }

    protected fun getValidationErrors(locale: Locale, formBinding: Errors?): ObjectErrors? {
        val formErrors = ObjectErrors();
        formBinding?.getFieldErrors()?.forEach {
            val fieldError = ObjectErrors.FieldError();
            fieldError.setField(it.getField());
            fieldError.setMessage(format!!.getMessage(locale, it));
            formErrors.getFieldErrors()!!.add(fieldError);
        }

        formBinding?.getGlobalErrors()?.forEach{
            var fieldError = ObjectErrors.GlobalError();
            fieldError.setMessage(format!!.getMessage(locale, it));
            formErrors.getGlobalErrors()!!.add(fieldError);
        }
        return formErrors;
    }

    protected fun getErrorMessage(locale: Locale, message: MessageSourceResolvable): String {
        var code = message.getCodes()!!.firstSure("message.unknown")
        return format!!.getMessage(locale, code, message.getArguments())!!;
    }

    protected fun requireEntity(entity: World?, id: Serializable): World {
        return if (entity == null) throw ObjectNotFoundException(javaClass<World>(), id) else entity;
    }
}