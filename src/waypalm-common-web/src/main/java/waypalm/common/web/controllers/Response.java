package waypalm.common.web.controllers;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import waypalm.common.web.model.ObjectErrors;

public interface Response {
    //ModelAndView json(Object answer);

    ModelAndView redirect(String url);

    ModelAndView forward(String url);

    //ModelAndView view(String viewName);

    ObjectErrors getValidationErrors(Errors errors);
}
