package waypalm.site.validation

import javax.inject.Inject
import waypalm.site.services.RecaptchaValidator
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import org.springframework.beans.PropertyAccessorFactory

public class CaptchaValidator [Inject](
        val captchaValidator: RecaptchaValidator,
        var challengeField: String = "",
        var responseField: String = ""
)
: ConstraintValidator<Captcha, Any>
{
    public override fun initialize(constraintAnnotation: Captcha?) {
        challengeField = constraintAnnotation!!.challengeField()!!;
        responseField = constraintAnnotation!!.responseField()!!;
    }

    public override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        val beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value)!!;
        val challenge = beanWrapper.getPropertyValue(challengeField) as String?;
        val response = beanWrapper.getPropertyValue(responseField) as String?;
        return captchaValidator.validate(challenge, response);
    }
}
