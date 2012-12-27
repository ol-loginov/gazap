package waypalm.site.web.controllers;

import waypalm.site.web.model.ApiAnswer;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

public interface ResponseBuilder {
    ResponseBuilder validationErrors(ApiAnswer answer, Errors errors);

    ModelAndView json(Object answer);

    ModelAndView redirect(String url);

    ModelAndView forward(String url);

    ModelAndView view(String viewName);
}
