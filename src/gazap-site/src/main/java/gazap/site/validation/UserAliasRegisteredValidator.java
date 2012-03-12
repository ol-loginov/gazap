package gazap.site.validation;

import gazap.domain.dao.UserProfileDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserAliasRegisteredValidator implements ConstraintValidator<UserAliasRegistered, String> {
    private boolean shouldBeRegistered;

    @Autowired
    protected UserProfileDao userProfileDao;

    @Override
    public void initialize(UserAliasRegistered constraintAnnotation) {
        initialize(constraintAnnotation.value());
    }

    public void initialize(boolean shouldBeRegistered) {
        this.shouldBeRegistered = shouldBeRegistered;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userProfileDao.findUserByAlias(value) == null ^ shouldBeRegistered;
    }
}