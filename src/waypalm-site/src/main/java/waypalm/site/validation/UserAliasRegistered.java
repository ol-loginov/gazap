package waypalm.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserAliasValidator.class)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAliasRegistered {
    String message() default "{waypalm.site.validation.UserAliasRegistered.message}";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean value() default false;
}
