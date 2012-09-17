package waypalm.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@SuppressWarnings("UnusedDeclaration")
@Documented
@Constraint(validatedBy = UserAliasRegisteredValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface UserAliasRegistered {
    boolean value();

    String message() default "{waypalm.site.validation.UserAliasRegistered.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
