package waypalm.site.validation

import javax.inject.Inject
import javax.validation.Constraint
import java.lang.annotation.Documented
import java.lang.annotation.Target
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.ElementType
import javax.validation.Payload
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
        challengeField = constraintAnnotation!!.challengeField;
        responseField = constraintAnnotation!!.responseField;
    }

    public override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        val beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value)!!;
        val challenge = beanWrapper.getPropertyValue(challengeField) as String?;
        val response = beanWrapper.getPropertyValue(responseField) as String?;
        return captchaValidator.validate(challenge, response);
    }
}

Documented
Constraint(validatedBy = array(javaClass<CaptchaValidator>()))
Target(ElementType.TYPE)
Retention(RetentionPolicy .RUNTIME)
public annotation class Captcha(
        val challengeField: String = "recaptcha_challenge_field",
        val responseField: String = "recaptcha_response_field",
        val message: String = "{waypalm.site.validation.ReCaptcha.message}",
        val groups: Array<Class<*>> = array(),
        val payload: Array<Class<in Payload>> = array()
)