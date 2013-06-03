package waypalm.site.validation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention
import java.lang.annotation.ElementType
import java.lang.annotation.Target
import javax.validation.Constraint
import java.lang.annotation.Documented
import javax.validation.Payload
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import java.util.regex.Pattern
import java.net.URLEncoder
import java.io.UnsupportedEncodingException

Documented
Constraint(validatedBy = array(javaClass<UserAliasValidator>()))
Target(ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER)
Retention(RetentionPolicy .RUNTIME)
public annotation class UserAlias(
        val message: String = "{waypalm.site.validation.UserAlias.message}",
        val groups: Array<Class<*>> = array(),
        val payload: Array<Class<in Payload>> = array()
)

public class UserAliasValidator
: ConstraintValidator<UserAlias, String>
{
    class object {
        val DENIED: Pattern = Pattern.compile(StringBuilder()
                                                      .append(Pattern.quote(":")).append("|")
                                                      .append(Pattern.quote(".")).append("|")
                                                      .append(Pattern.quote("?")).append("|")
                                                      .append(Pattern.quote("*"))
                                                      .toString());
    }

    public override fun initialize(constraintAnnotation: UserAlias?) {
    }

    public override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return false

        val inUrl: String;
        try {
            inUrl = URLEncoder.encode(value, "UTF-8");
        } catch (e: UnsupportedEncodingException) {
            return false;
        }
        return inUrl.equals(value) && !DENIED.matcher(value).find() && !Character.isDigit(value.charAt(0));
    }
}