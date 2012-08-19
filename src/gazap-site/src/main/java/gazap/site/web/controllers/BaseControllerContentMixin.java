package gazap.site.web.controllers;

import com.iserv2.commons.lang.collections.Collections;
import gazap.site.web.model.ApiAnswer;
import gazap.site.web.model.ApiFieldMessage;
import gazap.site.model.ServiceError;
import gazap.site.services.FormatService;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

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
    public ModelAndView json(Object val) {
        return view("jsonView", val);
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
    public ModelAndView view(String viewName, final Object content) {
        return new ModelAndView(viewName)
                .addObject("content", content);
    }

    @Override
    public ModelAndView view(String viewName) {
        return new ModelAndView(viewName);
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
}
