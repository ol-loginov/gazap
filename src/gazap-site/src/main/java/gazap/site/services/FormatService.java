package gazap.site.services;

import java.util.Locale;

public interface FormatService {
    String getMessage(Locale locale, String code, Object... args);

    String pluralize(Locale locale, long value, String code);
}
