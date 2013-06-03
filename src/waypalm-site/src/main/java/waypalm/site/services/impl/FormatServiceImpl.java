package waypalm.site.services.impl;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import waypalm.common.services.FormatService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;

@Named
public class FormatServiceImpl implements FormatService {
    @Inject
    MessageSource messageSource;

    @Override
    public String getMessage(Locale locale, String code, Object... args) {
        return messageSource.getMessage(code, args, code, locale);
    }

    @Override
    public String getMessage(Locale locale, MessageSourceResolvable resolvable) {
        return messageSource.getMessage(resolvable, locale);
    }
}
