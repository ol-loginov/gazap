package gazap.site.validation;

import gazap.domain.dao.UserProfileDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserEmailRegisteredValidator implements ConstraintValidator<UserEmailRegistered, String> {
    private boolean shouldBeRegistered;

    @Autowired
    protected UserProfileDao userProfileDao;

    @Override
    public void initialize(UserEmailRegistered constraintAnnotation) {
        initialize(constraintAnnotation.value());
    }

    public void initialize(boolean shouldBeRegistered) {
        this.shouldBeRegistered = shouldBeRegistered;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userProfileDao.findUserByEmail(value) == null ^ shouldBeRegistered;
    }
}
