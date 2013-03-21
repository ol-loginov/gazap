package waypalm.common.services;

import org.springframework.context.MessageSourceResolvable;

import java.util.Locale;

public interface FormatService {
    String getMessage(Locale locale, String code, Object... args);

    String getMessage(Locale locale, MessageSourceResolvable resolvable);
}
