package waypalm.site.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import waypalm.common.services.FormatService;
import waypalm.common.web.controllers.Response;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.World;
import waypalm.site.exceptions.ObjectNotFoundException;
import waypalm.site.services.ModelViewer;
import waypalm.site.services.UserAccess;
import waypalm.site.web.mvc.ResponseBuilder;

import java.io.Serializable;
import java.util.Locale;

public abstract class BaseController {
    @Autowired
    protected UserAccess auth;
    @Autowired
    protected FormatService format;
    @Autowired
    protected ModelViewer viewer;
    @Autowired
    protected SmartValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    protected Response responseBuilder(Locale locale) {
        return new ResponseBuilder(format, locale);
    }

    public Profile requireProfile() {
        Profile profile = auth.loadCurrentProfile();
        if (profile == null) {
            throw new InsufficientAuthenticationException("require authenticated visitor");
        }
        return profile;
    }

    protected static <K extends Serializable> World requireEntity(World entity, K id) throws ObjectNotFoundException {
        if (entity == null)
            throw new ObjectNotFoundException(World.class, id);
        return entity;
    }
}
