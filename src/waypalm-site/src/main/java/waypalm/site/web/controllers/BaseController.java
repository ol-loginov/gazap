package waypalm.site.web.controllers;

import com.iserv2.commons.lang.collections.IservCollections;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import waypalm.common.services.FormatService;
import waypalm.common.web.model.ObjectErrors;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.base.DomainEntity;
import waypalm.site.exceptions.ObjectNotFoundException;
import waypalm.site.services.ModelViewer;
import waypalm.site.services.UserAccess;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Locale;

public abstract class BaseController {
    @Inject
    protected UserAccess auth;
    @Inject
    protected FormatService res;
    @Inject
    protected ModelViewer viewer;
    @Inject
    protected SmartValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    public Profile requireProfile() {
        Profile profile = auth.loadCurrentProfile();
        if (profile == null) {
            throw new InsufficientAuthenticationException("require authenticated visitor");
        }
        return profile;
    }

    protected static <K extends Serializable, E extends DomainEntity> E requireEntity(Class<E> entityClass, E entity, K id) throws ObjectNotFoundException {
        if (entity == null)
            throw new ObjectNotFoundException(entityClass, id);
        return entity;
    }

    public ModelAndView json(Object val) {
        return view("jsonView").addObject("content", val);
    }

    public ModelAndView redirect(String url) {
        return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + url);
    }

    public ModelAndView forward(String url) {
        return new ModelAndView(UrlBasedViewResolver.FORWARD_URL_PREFIX + url);
    }

    public ModelAndView view(String viewName) {
        return new ModelAndView(viewName);
    }

    public ObjectErrors getValidationErrors(Locale locale, Errors formBinding) {
        if (formBinding == null || !formBinding.hasErrors()) {
            return null;
        }

        ObjectErrors formErrors = new ObjectErrors();
        for (FieldError formBindingFieldError : formBinding.getFieldErrors()) {
            ObjectErrors.FieldError fieldError = new ObjectErrors.FieldError();
            fieldError.setField(formBindingFieldError.getField());
            fieldError.setMessage(res.getMessage(locale, formBindingFieldError));
            formErrors.getFieldErrors().add(fieldError);
        }

        for (ObjectError formBindingFieldError : formBinding.getGlobalErrors()) {
            ObjectErrors.GlobalError fieldError = new ObjectErrors.GlobalError();
            fieldError.setMessage(res.getMessage(locale, formBindingFieldError));
            formErrors.getGlobalErrors().add(fieldError);
        }
        return formErrors;
    }

    public String getErrorMessage(Locale locale, MessageSourceResolvable message) {
        if (message == null) {
            return null;
        }
        String code = IservCollections.firstOrNull(message.getCodes());
        return res.getMessage(locale, code == null ? "message.unknown" : code, message.getArguments());
    }
}
