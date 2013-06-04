package waypalm.site.validation

import javax.validation.ConstraintValidator
import javax.inject.Inject
import waypalm.domain.dao.UserRepository
import javax.validation.ConstraintValidatorContext

public class UserEmailRegisteredValidator [Inject](
        val userRepository: UserRepository
)
: ConstraintValidator<UserEmailRegistered, String>
{
    var shouldBeRegistered: Boolean = false

    public override fun initialize(constraintAnnotation: UserEmailRegistered?) {
        shouldBeRegistered = constraintAnnotation!!.value()
    }

    public override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(shouldBeRegistered)
            return userRepository.findProfileByEmail(value) != null
        else
            return userRepository.findProfileByEmail(value) == null
    }
}