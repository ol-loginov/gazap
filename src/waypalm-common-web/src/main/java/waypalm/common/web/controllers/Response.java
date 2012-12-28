package waypalm.common.web.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import waypalm.common.web.model.FormErrors;

public interface Response {
    ModelAndView json(Object answer);

    ModelAndView redirect(String url);

    ModelAndView forward(String url);

    ModelAndView view(String viewName);

    FormErrors getFormErrors(BindingResult formBinding);
}
