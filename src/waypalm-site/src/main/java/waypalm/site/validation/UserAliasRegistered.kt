package waypalm.site.validation

import javax.validation.ConstraintValidator
import javax.inject.Inject
import waypalm.domain.dao.UserRepository
import org.springframework.transaction.annotation.Transactional
import javax.validation.ConstraintValidatorContext

public class UserAliasRegisteredValidator [Inject](
        val userRepository: UserRepository
)
: ConstraintValidator<UserAliasRegistered, String>
{
    var shouldBeRegistered: Boolean = false

    public override  fun initialize(constraintAnnotation: UserAliasRegistered?) {
        shouldBeRegistered = constraintAnnotation!!.value()
    }

    Transactional
    public override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(shouldBeRegistered)
            return userRepository.findProfileByAlias(value) != null
        else
            return userRepository.findProfileByAlias(value) == null
    }
}