package waypalm.site.validation

import javax.validation.ConstraintValidator
import javax.inject.Inject
import waypalm.domain.dao.WorldRepository
import javax.validation.ConstraintValidatorContext
import org.springframework.beans.BeanUtils

public class WorldTitleValidator [Inject] (
        val  worldRepository: WorldRepository,
        var idField: String = "",
        var titleField: String = ""
)
: ConstraintValidator<WorldTitle, Any>
{
    public override fun initialize(constraintAnnotation: WorldTitle?) {
        idField = constraintAnnotation!!.idField() as String;
        titleField = constraintAnnotation.titleField() as String;
    }

    public override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return false;
        }

        val titleValue = readTitle(value);
        val idValue = readId(value);

        val world = worldRepository.findWorldByTitle(titleValue);
        if (world == null || world.getId().equals(idValue)) {
            return true;
        }

        context!!
                .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())!!
                .addNode(titleField)!!
                .addConstraintViolation()!!
                .disableDefaultConstraintViolation();
        return false;
    }


    private fun readId(obj: Any): Int? {
        if (idField.length() == 0) {
            return null;
        }

        val idProperty = BeanUtils.getPropertyDescriptor(obj.javaClass, idField);
        if (idProperty == null) {
            throw IllegalStateException("property set is not complete");
        }
        try {
            return idProperty.getReadMethod()!!.invoke(obj) as Int;
        } catch (e: Exception) {
            throw IllegalStateException("property set is not readable");
        }
    }

    private fun readTitle(obj: Any): String {
        val titleProperty = BeanUtils.getPropertyDescriptor(obj.javaClass, titleField);
        if (titleProperty == null) {
            throw  IllegalStateException("property set is not complete");
        }
        try {
            return  titleProperty.getReadMethod()!!.invoke(obj) as String;
        } catch (e: Exception) {
            throw IllegalStateException("property set is not readable");
        }
    }
}