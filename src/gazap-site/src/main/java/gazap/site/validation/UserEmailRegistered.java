package gazap.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UserEmailRegisteredValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface UserEmailRegistered {
    boolean value();

    String message() default "{gazap.site.validation.UserEmailRegistered.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
