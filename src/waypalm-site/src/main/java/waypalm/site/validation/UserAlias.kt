package waypalm.site.validation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import java.util.regex.Pattern
import java.net.URLEncoder
import java.io.UnsupportedEncodingException

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