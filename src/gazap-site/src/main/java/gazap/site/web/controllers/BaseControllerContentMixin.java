package gazap.site.web.controllers;

import com.iserv2.commons.lang.collections.Collections;
import com.iserv2.commons.mvc.views.Content;
import com.iserv2.commons.mvc.views.ContentModule;
import com.iserv2.commons.mvc.views.ForwardContent;
import com.iserv2.commons.mvc.views.RedirectContent;
import gazap.site.model.ApiAnswer;
import gazap.site.model.ApiFieldMessage;
import gazap.site.model.ServiceError;
import gazap.site.services.FormatService;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BaseControllerContentMixin implements ResponseBuilder {
    private final FormatService formatService;
    private final Locale locale;

    public BaseControllerContentMixin(FormatService formatService, Locale locale) {
        this.formatService = formatService;
        this.locale = locale;
    }

    @Override
    public ModelAndViewContent json(Object val) {
        ModelAndViewContent content = new ModelAndViewContent();
        content.setViewName("jsonView");
        content.addObject("content", val);
        return content;
    }

    @Override
    public RedirectContent redirect(String url) {
        return new RedirectContent(url, false);
    }

    @Override
    public ForwardContent forward(String url) {
        return new ForwardContent(url);
    }

    @Override
    public BaseControllerContentMixin validationErrors(ApiAnswer apiAnswer, Errors errors) {
        apiAnswer.setSuccess(false);
        apiAnswer.setCode(ServiceError.VALIDATION_FAILED.code());
        apiAnswer.setMessage(formatService.getMessage(locale, apiAnswer.getCode()));
        apiAnswer.setErrorList(storeErrors(errors.getAllErrors()));
        return this;
    }

    private ApiFieldMessage[] storeErrors(List<ObjectError> errors) {
        if (errors == null) {
            return null;
        }
        List<ApiFieldMessage> resultList = new ArrayList<ApiFieldMessage>();
        for (ObjectError err : errors) {
            ApiFieldMessage fieldError = new ApiFieldMessage();
            if (err instanceof org.springframework.validation.FieldError) {
                fieldError.setField(((org.springframework.validation.FieldError) err).getField());
            }
            fieldError.setMessage(getErrorMessage(err));
            resultList.add(fieldError);
        }
        return resultList.toArray(new ApiFieldMessage[resultList.size()]);
    }

    public String getErrorMessage(MessageSourceResolvable message) {
        if (message == null) {
            return null;
        }
        String code = Collections.firstOrNull(message.getCodes());
        return formatService.getMessage(locale, code == null ? "message.unknown" : code, message.getArguments());
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
}
