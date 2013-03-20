package waypalm.site.web.mvc;

import com.iserv2.commons.lang.collections.IservCollections;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import waypalm.common.services.FormatService;
import waypalm.common.web.controllers.Response;
import waypalm.common.web.model.ObjectErrors;

import java.util.Locale;

public class ResponseBuilder implements Response {
    private final FormatService formatService;
    private final Locale locale;

    public ResponseBuilder(FormatService formatService, Locale locale) {
        this.formatService = formatService;
        this.locale = locale;
    }

    @Override
    public ModelAndView json(Object val) {
        return view("jsonView").addObject("content", val);
    }

    @Override
    public ModelAndView redirect(String url) {
        return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + url);
    }

    @Override
    public ModelAndView forward(String url) {
        return new ModelAndView(UrlBasedViewResolver.FORWARD_URL_PREFIX + url);
    }

    @Override
    public ModelAndView view(String viewName) {
        return new ModelAndView(viewName);
    }

    @Override
    public ObjectErrors getValidationErrors(Errors formBinding) {
        if (formBinding == null || !formBinding.hasErrors()) {
            return null;
        }

        ObjectErrors formErrors = new ObjectErrors();
        for (FieldError formBindingFieldError : formBinding.getFieldErrors()) {
            ObjectErrors.FieldError fieldError = new ObjectErrors.FieldError();
            fieldError.setField(formBindingFieldError.getField());
            fieldError.setMessage(formatService.getMessage(locale, formBindingFieldError));
            formErrors.getFieldErrors().add(fieldError);
        }

        for (ObjectError formBindingFieldError : formBinding.getGlobalErrors()) {
            ObjectErrors.GlobalError fieldError = new ObjectErrors.GlobalError();
            fieldError.setMessage(formatService.getMessage(locale, formBindingFieldError));
            formErrors.getGlobalErrors().add(fieldError);
        }
        return formErrors;
    }

    public String getErrorMessage(MessageSourceResolvable message) {
        if (message == null) {
            return null;
        }
        String code = IservCollections.firstOrNull(message.getCodes());
        return formatService.getMessage(locale, code == null ? "message.unknown" : code, message.getArguments());
    }
}
