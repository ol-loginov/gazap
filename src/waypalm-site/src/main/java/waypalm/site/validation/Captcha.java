package waypalm.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CaptchaValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Captcha {
    String message() default "{waypalm.site.validation.Captcha.message}";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String challengeField() default "recaptcha_challenge_field";

    String responseField() default "recaptcha_response_field";
}
