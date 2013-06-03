package waypalm.site.validation

import javax.validation.ConstraintValidator
import javax.inject.Inject
import waypalm.site.services.RecaptchaValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Constraint


Documented
//Constraint(validatedBy = ReCaptchaValidator.class)
Target(TYPE)
Retention(RetentionPolicy.RUNTIME)
public annotation class ReCaptcha(
        val challengeField: String = "recaptcha_challenge_field",
        val responseField: String = "recaptcha_response_field",
        val message: String = "{waypalm.site.validation.ReCaptcha.message}",
        val groups: Array<Class<*>>= array(),
        val payload: Array<Class<Payload>> = array()
)

public class ReCaptchaValidator [Inject](
        val captchaValidator: RecaptchaValidator
)
: ConstraintValidator<ReCaptcha, Any>
{
    var challengeField: String = ""
    var responseField: String = ""

    public override fun initialize(constraintAnnotation: ReCaptcha?) {
        challengeField = constraintAnnotation.challengeField();
        responseField = constraintAnnotation.responseField();
    }

    public override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        throw UnsupportedOperationException()
    }
}