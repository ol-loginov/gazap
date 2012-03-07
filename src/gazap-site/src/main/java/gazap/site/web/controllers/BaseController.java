package gazap.site.web.controllers;

import com.iserv2.commons.lang.collections.Collections;
import com.iserv2.commons.mvc.validation.FieldError;
import com.iserv2.commons.mvc.validation.FieldErrors;
import com.iserv2.commons.mvc.views.Content;
import com.iserv2.commons.mvc.views.ContentModule;
import com.iserv2.commons.mvc.views.OperationStatusKeeper;
import gazap.site.model.ApiFieldMessage;
import gazap.site.model.ServiceError;
import gazap.site.services.FormatService;
import gazap.site.services.ModelViewer;
import gazap.site.services.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
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
        return error == null ? response : error(response, error, error.code(), locale);
    }

    public <T extends OperationStatusKeeper> T error(T response, ServiceError error, String resString, Locale locale) {
        response.getOperationStatus().setSuccess(false);
        if (error != null) {
            response.getOperationStatus().setCode(error.code());
            response.getOperationStatus().setMessage(format.getMessage(locale, resString));
        }
        return response;
    }

    public <T extends OperationStatusKeeper> T error(T response, Errors errors, Locale locale) {
        storeErrors(response.getOperationStatus(), errors.getFieldErrors(), locale);
        return error(response, ServiceError.VALIDATION_FAILED, locale);
    }

    public <T extends FieldErrors> T storeErrors(T errorReceiver, List<org.springframework.validation.FieldError> errors, Locale locale) {
        if (errors == null) {
            return errorReceiver;
        }
        for (org.springframework.validation.FieldError err : errors) {
            FieldError fieldError = new FieldError();
            fieldError.setField(err.getField());
            fieldError.setError(getErrorMessage(locale, err));
            errorReceiver.getInvalidFields().add(fieldError);
        }
        return errorReceiver;
    }

    public ApiFieldMessage[] storeErrors(List<ObjectError> errors, Locale locale) {
        if (errors == null) {
            return null;
        }
        List<ApiFieldMessage> resultList = new ArrayList<ApiFieldMessage>();
        for (ObjectError err : errors) {
            ApiFieldMessage fieldError = new ApiFieldMessage();
            if (err instanceof org.springframework.validation.FieldError) {
                fieldError.setField(((org.springframework.validation.FieldError) err).getField());
            }
            fieldError.setMessage(getErrorMessage(locale, err));
            resultList.add(fieldError);
        }
        return resultList.toArray(new ApiFieldMessage[resultList.size()]);
    }

    public String getErrorMessage(Locale locale, MessageSourceResolvable message) {
        if (message == null) {
            return null;
        }
        String code = Collections.firstOrNull(message.getCodes());
        return format.getMessage(locale, code == null ? "message.unknown" : code, message.getArguments());
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
