package waypalm.site.validation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.World;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;

public class WorldTitleValidator implements ConstraintValidator<WorldTitle, Object> {
    @Autowired
    protected WorldRepository worldRepository;

    private String idField;
    private String titleField;

    @Override
    public void initialize(WorldTitle constraintAnnotation) {
        idField = constraintAnnotation.idField();
        titleField = constraintAnnotation.titleField();
    }

    @Override
    @Transactional
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (object == null) {
            return false;
        }

        String titleValue = readTitle(object);
        Integer idValue = readId(object);

        World world = worldRepository.findWorldByTitle(titleValue);
        if (world == null || world.getId().equals(idValue)) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addNode(titleField)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }

    private Integer readId(Object object) {
        if (idField.length() == 0) {
            return null;
        }

        PropertyDescriptor idProperty = BeanUtils.getPropertyDescriptor(object.getClass(), idField);
        if (idProperty == null) {
            throw new IllegalStateException("property set is not complete");
        }
        try {
            return (Integer) idProperty.getReadMethod().invoke(object);
        } catch (Exception e) {
            throw new IllegalStateException("property set is not readable");
        }
    }

    private String readTitle(Object object) {
        PropertyDescriptor titleProperty = BeanUtils.getPropertyDescriptor(object.getClass(), titleField);
        if (titleProperty == null) {
            throw new IllegalStateException("property set is not complete");
        }
        try {
            return (String) titleProperty.getReadMethod().invoke(object);
        } catch (Exception e) {
            throw new IllegalStateException("property set is not readable");
        }
    }
}
