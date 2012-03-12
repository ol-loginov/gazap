package gazap.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ReCaptchaValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface ReCaptcha {
    String challengeField() default "recaptcha_challenge_field";

    String responseField() default "recaptcha_response_field";

    String message() default "{gazap.site.validation.ReCaptcha.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
