package waypalm.common.util;

import java.util.regex.Pattern;

public class ObjectUtil {
    public static final Pattern INTEGER_PATTERN = Pattern.compile("^\\d+$");

    public static boolean isInteger(String number) {
        return number != null && INTEGER_PATTERN.matcher(number).matches();
    }
}
