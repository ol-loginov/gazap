package gazap.site.web.controllers;

import gazap.site.services.FormatService;
import gazap.site.services.ModelViewer;
import gazap.site.services.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

public abstract class BaseController {
    @Autowired
    protected UserAccess auth;
    @Autowired
    protected FormatService format;
    @Autowired
    protected ModelViewer viewer;

    protected BaseControllerContentMixin contentFactory(Locale locale) {
        return new BaseControllerContentMixin(format, locale);
    }
}
