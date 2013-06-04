package waypalm.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserAliasValidator.class)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAlias {
        String message() default "{waypalm.site.validation.UserAlias.message}";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
