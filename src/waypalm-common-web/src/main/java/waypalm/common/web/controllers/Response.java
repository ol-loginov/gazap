package waypalm.common.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public interface Response {
    ModelAndView json(Object answer);

    ModelAndView redirect(String url);

    ModelAndView forward(String url);

    ModelAndView view(String viewName);
}
