package waypalm.site.web.controllers;

import com.iserv2.commons.lang.collections.IservCollections;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import waypalm.common.services.FormatService;
import waypalm.site.web.model.ApiFieldMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResponseBuilderImpl implements Response {
    private final FormatService formatService;
    private final Locale locale;

    public ResponseBuilderImpl(FormatService formatService, Locale locale) {
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
        String code = IservCollections.firstOrNull(message.getCodes());
        return formatService.getMessage(locale, code == null ? "message.unknown" : code, message.getArguments());
    }
}
