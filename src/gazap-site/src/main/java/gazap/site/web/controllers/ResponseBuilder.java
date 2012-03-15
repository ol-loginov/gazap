package gazap.site.web.controllers;

import com.iserv2.commons.mvc.views.Content;
import gazap.site.model.ApiAnswer;
import org.springframework.validation.Errors;

public interface ResponseBuilder {
    ResponseBuilder validationErrors(ApiAnswer answer, Errors errors);

    Content json(Object answer);

    Content redirect(String url);

    Content forward(String url);
}
