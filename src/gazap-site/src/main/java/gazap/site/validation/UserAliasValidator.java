package gazap.site.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class UserAliasValidator implements ConstraintValidator<UserAlias, String> {
    private static final Pattern DENIED;

    static {
        StringBuilder denyList = new StringBuilder()
                .append(Pattern.quote(":")).append("|")
                .append(Pattern.quote(".")).append("|")
                .append(Pattern.quote("?")).append("|")
                .append(Pattern.quote("*"));
        DENIED = Pattern.compile(denyList.toString());
    }

    @Override
    public void initialize(UserAlias constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return false;
        }
        String inUrl;
        try {
            inUrl = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        return inUrl.equals(value) && !DENIED.matcher(value).find() && !Character.isDigit(value.charAt(0));
    }
}
