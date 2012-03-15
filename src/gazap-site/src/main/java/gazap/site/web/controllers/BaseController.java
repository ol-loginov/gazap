package gazap.site.web.controllers;

import gazap.site.services.FormatService;
import gazap.site.services.ModelViewer;
import gazap.site.services.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    protected ResponseBuilder responseBuilder(Locale locale) {
        return new BaseControllerContentMixin(format, locale);
    }
}
