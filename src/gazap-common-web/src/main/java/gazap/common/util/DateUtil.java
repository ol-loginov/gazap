package gazap.common.util;

import java.util.Calendar;
import java.util.TimeZone;

public class DateUtil {
    private final static TimeZone UTC_ZONE = TimeZone.getTimeZone("UTC");

    public static Calendar utc() {
        return Calendar.getInstance(UTC_ZONE);
    }
}
