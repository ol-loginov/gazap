package waypalm.site.validation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import org.springframework.util.StringUtils

public class UserEmailValidator
: ConstraintValidator<UserEmail, String>
{
    val emailValidator: org.apache.commons.validator.routines.EmailValidator = org.apache.commons.validator.routines.EmailValidator.getInstance()!!;

    public override fun initialize(constraintAnnotation: UserEmail?) = Unit.VALUE

    public override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return !StringUtils.hasLength(value) || emailValidator.isValid(value);
    }
}