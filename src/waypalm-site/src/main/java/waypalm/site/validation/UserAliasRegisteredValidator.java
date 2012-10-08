package waypalm.site.validation;

import waypalm.domain.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserAliasRegisteredValidator implements ConstraintValidator<UserAliasRegistered, String> {
    private boolean shouldBeRegistered;

    @Autowired
    protected UserRepository userRepository;

    @Override
    public void initialize(UserAliasRegistered constraintAnnotation) {
        initialize(constraintAnnotation.value());
    }

    public void initialize(boolean shouldBeRegistered) {
        this.shouldBeRegistered = shouldBeRegistered;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findProfileByAlias(value) == null ^ shouldBeRegistered;
    }
}
