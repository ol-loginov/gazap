package waypalm.site.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public interface ResponseBuilder {
    ModelAndView json(Object answer);

    ModelAndView redirect(String url);

    ModelAndView forward(String url);

    ModelAndView view(String viewName);
}
