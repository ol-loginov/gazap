package gazap.site.validation;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class UserEmailValidator implements ConstraintValidator<UserEmail, String> {
    org.apache.commons.validator.routines.EmailValidator emailValidator = org.apache.commons.validator.routines.EmailValidator.getInstance();

    @Override
    public void initialize(UserEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !StringUtils.hasLength(value) || emailValidator.isValid(value);
    }
}
