package gazap.site.web.controllers;

import gazap.site.services.FormatService;
import gazap.site.services.ModelViewer;
import gazap.site.services.UserAccess;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.*;

public abstract class BaseController {
    private static final Set<String> internalAnnotationAttributes = new HashSet<String>(3);

    static {
        internalAnnotationAttributes.add("message");
        internalAnnotationAttributes.add("groups");
        internalAnnotationAttributes.add("payload");
    }

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
        return new BaseControllerContentMixin(format, locale);
    }

    protected <T> void validate(T form, Errors errors, Class<?>... groups) {
        for (ConstraintViolation<T> violation : validator.validate(form, groups)) {
            String field = violation.getPropertyPath().toString();
            FieldError fieldError = errors.getFieldError(field);
            if (fieldError == null || !fieldError.isBindingFailure()) {
                try {
                    errors.rejectValue(field,
                            violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
                            getArgumentsForConstraint(errors.getObjectName(), field, violation.getConstraintDescriptor()),
                            violation.getMessage());
                } catch (NotReadablePropertyException ex) {
                    throw new IllegalStateException("JSR-303 validated property '" + field +
                            "' does not have a corresponding accessor for Spring data binding - " +
                            "check your DataBinder's configuration (bean property versus direct field access)", ex);
                }
            }
        }
    }

    protected Object[] getArgumentsForConstraint(String objectName, String field, ConstraintDescriptor<?> descriptor) {
        List<Object> arguments = new LinkedList<Object>();
        String[] codes = new String[]{objectName + Errors.NESTED_PATH_SEPARATOR + field, field};
        arguments.add(new DefaultMessageSourceResolvable(codes, field));
        // Using a TreeMap for alphabetical ordering of attribute names
        Map<String, Object> attributesToExpose = new TreeMap<String, Object>();
        for (Map.Entry<String, Object> entry : descriptor.getAttributes().entrySet()) {
            String attributeName = entry.getKey();
            Object attributeValue = entry.getValue();
            if (!internalAnnotationAttributes.contains(attributeName)) {
                attributesToExpose.put(attributeName, attributeValue);
            }
        }
        arguments.addAll(attributesToExpose.values());
        return arguments.toArray(new Object[arguments.size()]);
    }
}
