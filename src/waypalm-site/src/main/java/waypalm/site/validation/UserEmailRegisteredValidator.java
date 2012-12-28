package waypalm.site.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import waypalm.domain.dao.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserEmailRegisteredValidator implements ConstraintValidator<UserEmailRegistered, String> {
    private boolean shouldBeRegistered;

    @Autowired
    protected UserRepository userRepository;

    @Override
    public void initialize(UserEmailRegistered constraintAnnotation) {
        initialize(constraintAnnotation.value());
    }

    public void initialize(boolean shouldBeRegistered) {
        this.shouldBeRegistered = shouldBeRegistered;
    }

    @Override
    @Transactional
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findProfileByEmail(value) == null ^ shouldBeRegistered;
    }
}
