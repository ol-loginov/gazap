package gazap.panel.web.controllers;

import com.iserv2.commons.lang.collections.Collections;
import com.iserv2.commons.mvc.validation.FieldError;
import com.iserv2.commons.mvc.validation.FieldErrors;
import com.iserv2.commons.mvc.views.Content;
import com.iserv2.commons.mvc.views.ContentModule;
import com.iserv2.commons.mvc.views.OperationStatusKeeper;
import gazap.panel.model.ServiceError;
import gazap.panel.services.FormatService;
import gazap.panel.services.ModelViewer;
import gazap.panel.services.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Locale;

public abstract class BaseController {
    protected UserAccess auth;
    protected FormatService format;
    protected ModelViewer viewer;

    @Autowired
    protected void setViewer(ModelViewer viewer) {
        this.viewer = viewer;
    }

    @Autowired
    protected void setFormat(FormatService format) {
        this.format = format;
    }

    @Autowired
    protected void setAuth(UserAccess auth) {
        this.auth = auth;
    }

    protected ModelAndView json(Object val) {
        ModelAndView content = new ModelAndView();
        content.setViewName("jsonView");
        content.addObject("content", val);
        return content;
    }

    protected RedirectContent redirect(String url) {
        return new RedirectContent(url);
    }

    public <T extends OperationStatusKeeper> T error(T response, ServiceError error, Locale locale) {
        return error == null ? response : error(response, error, error.getCode(), locale);
    }

    public <T extends OperationStatusKeeper> T error(T response, ServiceError error, String resString, Locale locale) {
        response.getOperationStatus().setSuccess(false);
        if (error != null) {
            response.getOperationStatus().setCode(error.getCode());
            response.getOperationStatus().setMessage(format.getMessage(locale, resString));
        }
        return response;
    }

    public <T extends OperationStatusKeeper> T error(T response, Errors errors, Locale locale) {
        storeErrors(response.getOperationStatus(), errors.getFieldErrors(), locale);
        return error(response, ServiceError.PAGE_VALIDATION_FAILED, locale);
    }

    public <T extends FieldErrors> T storeErrors(T errorReceiver, List<org.springframework.validation.FieldError> errors, Locale locale) {
        if (errors == null) {
            return errorReceiver;
        }
        for (org.springframework.validation.FieldError err : errors) {
            FieldError fieldError = new FieldError();
            fieldError.setField(err.getField());
            fieldError.setError(getErrorMessage(err, locale));
            errorReceiver.getInvalidFields().add(fieldError);
        }
        return errorReceiver;
    }

    public String getErrorMessage(org.springframework.validation.FieldError error, Locale locale) {
        if (error == null) {
            return null;
        }
        String code = Collections.firstOrNull(error.getCodes());
        return format.getMessage(locale, code == null ? "message.unknown" : code, error.getArguments());
    }

    private static class ModelAndViewContent extends ModelAndView implements Content {
        @Override
        public List<ContentModule> getModules() {
            return null;
        }

        @Override
        public <T extends ContentModule> T getModule(Class<T> moduleClass) {
            return null;
        }
    }

    private static class RedirectContent extends RedirectView implements Content {
        public RedirectContent(String url) {
            super(url);
        }

        @Override
        public List<ContentModule> getModules() {
            return null;
        }

        @Override
        public <T extends ContentModule> T getModule(Class<T> moduleClass) {
            return null;
        }
    }
}
