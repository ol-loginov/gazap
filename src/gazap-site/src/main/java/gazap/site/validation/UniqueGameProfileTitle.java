package gazap.site.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UniqueGameProfileTitleValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface UniqueGameProfileTitle {
    String message() default "{gazap.site.validation.UniqueGameProfileTitle.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}