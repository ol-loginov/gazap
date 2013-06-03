package waypalm.site.validation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention
import java.lang.annotation.ElementType
import java.lang.annotation.Target
import javax.validation.Constraint
import java.lang.annotation.Documented
import javax.validation.Payload
import javax.validation.ConstraintValidator
import javax.inject.Inject
import waypalm.domain.dao.UserRepository
import org.springframework.transaction.annotation.Transactional
import javax.validation.ConstraintValidatorContext

Documented
Constraint(validatedBy = array(javaClass<UserAliasValidator>()))
Target(ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER)
Retention(RetentionPolicy .RUNTIME)
public annotation class UserAliasRegistered(
        val value: Boolean,
        val message: String = "{waypalm.site.validation.UserAliasRegistered.message}",
        val groups: Array<Class<*>> = array(),
        val payload: Array<Class<in Payload>> = array()
)

public class UserAliasRegisteredValidator [Inject](
        val userRepository: UserRepository
)
: ConstraintValidator<UserAliasRegistered, String>
{
    var shouldBeRegistered: Boolean = false

    public override  fun initialize(constraintAnnotation: UserAliasRegistered?) {
        shouldBeRegistered = constraintAnnotation!!.value
    }

    Transactional
    public override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(shouldBeRegistered)
            return userRepository.findProfileByAlias(value) != null
        else
            return userRepository.findProfileByAlias(value) == null
    }
}