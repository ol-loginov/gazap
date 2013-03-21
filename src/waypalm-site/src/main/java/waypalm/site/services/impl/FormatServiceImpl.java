package waypalm.site.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.stereotype.Service;
import waypalm.common.services.FormatService;

import java.util.Locale;

@Service
public class FormatServiceImpl implements FormatService {
    @Autowired
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
