package waypalm.site.validation;

import org.springframework.transaction.annotation.Transactional;
import waypalm.domain.dao.UserRepository;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserAliasRegisteredValidator implements ConstraintValidator<UserAliasRegistered, String> {
    private boolean shouldBeRegistered;

    @Inject
    protected UserRepository userRepository;

    @Override
    public void initialize(UserAliasRegistered constraintAnnotation) {
        initialize(constraintAnnotation.value());
    }

    public void initialize(boolean shouldBeRegistered) {
        this.shouldBeRegistered = shouldBeRegistered;
    }

    @Override
    @Transactional
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findProfileByAlias(value) == null ^ shouldBeRegistered;
    }
}
