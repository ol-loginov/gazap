package waypalm.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = WorldTitleValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface WorldTitle {
    String message() default "{waypalm.site.validation.WorldTitle.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String titleField() default "title";

    String idField() default "";
}
