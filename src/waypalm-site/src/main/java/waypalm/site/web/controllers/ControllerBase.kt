package waypalm.site.web.controllers

import java.util.Locale
import java.io.Serializable
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.WebDataBinder
import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.web.servlet.view.UrlBasedViewResolver
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.validation.Errors
import org.springframework.context.MessageSourceResolvable
import waypalm.domain.entity.Profile
import waypalm.common.web.model.ObjectErrors
import waypalm.common.util.firstSure
import waypalm.domain.entity.World
import waypalm.site.exceptions.ObjectNotFoundException
import org.springframework.validation.SmartValidator
import waypalm.site.services.ModelViewer
import waypalm.common.services.FormatService
import waypalm.site.services.UserAccess
import javax.inject.Inject
import javax.inject.Named

Named
public class ControllerServices [Inject](
        var auth: UserAccess,
        var format: FormatService,
        var viewer: ModelViewer,
        var validator: SmartValidator
)

public class ModelAndView(name: String)
: org.springframework.web.servlet.ModelAndView(name)
{
    public override fun addObject(attributeName: String?, attributeValue: Any?): ModelAndView {
        super.addObject(attributeName, attributeValue)
        return this
    }
}

public trait ControllerBase {
    val services: ControllerServices

    InitBinder
    public fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(javaClass<String>(), StringTrimmerEditor(true))
    }

    public fun view(name: String): ModelAndView {
        return ModelAndView(name);
    }

    public fun json(value: Any): ModelAndView {
        return view("json").addObject("content", value)
    }

    public fun redirect(url: String): ModelAndView {
        return view(UrlBasedViewResolver.REDIRECT_URL_PREFIX + url);
    }

    public fun forward(url: String): ModelAndView {
        return view(UrlBasedViewResolver.FORWARD_URL_PREFIX + url);
    }

    public fun currentProfile(): Profile {
        var profile = services.auth.loadCurrentProfile();
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
            fieldError.setMessage(services.format.getMessage(locale, it));
            formErrors.getFieldErrors()!!.add(fieldError);
        }

        formBinding?.getGlobalErrors()?.forEach{
            var fieldError = ObjectErrors.GlobalError();
            fieldError.setMessage(services.format.getMessage(locale, it));
            formErrors.getGlobalErrors()!!.add(fieldError);
        }
        return formErrors;
    }

    protected fun getErrorMessage(locale: Locale, message: MessageSourceResolvable): String {
        var code = message.getCodes()!!.firstSure("message.unknown")
        return services.format.getMessage(locale, code, message.getArguments())!!;
    }

    protected fun requireEntity(entity: World?, id: Serializable): World {
        return if (entity == null) throw ObjectNotFoundException(javaClass<World>(), id) else entity;
    }
}