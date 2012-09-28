package waypalm.site.web.controllers;

import waypalm.site.services.FormatService;
import waypalm.site.services.ModelViewer;
import waypalm.site.services.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Locale;

public abstract class BaseController {
    @Autowired
    protected UserAccess auth;
    @Autowired
    protected FormatService format;
    @Autowired
    protected ModelViewer viewer;
    @Autowired
    protected SecurityHelper securityHelper;
    @Autowired
    protected Validator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    protected ResponseBuilder responseBuilder(Locale locale) {
        return new ResponseBuilderImpl(format, locale);
    }
}