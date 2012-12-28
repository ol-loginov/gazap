package waypalm.site.services.impl;

import com.iserv2.commons.text.FormatPluralizer;
import com.iserv2.commons.text.FormatPluralizerMap;
import org.springframework.context.MessageSourceResolvable;
import waypalm.common.services.FormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class FormatServiceImpl implements FormatService {
    private MessageSource messageSource;
    private FormatPluralizerMap pluralizers = new FormatPluralizerMap();

    @Autowired
    protected void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(Locale locale, String code, Object... args) {
        return messageSource.getMessage(code, args, code, locale);
    }

    @Override
    public String getMessage(Locale locale, MessageSourceResolvable resolvable) {
        return messageSource.getMessage(resolvable, locale);
    }

    @Override
    public String pluralize(Locale locale, long value, String code) {
        FormatPluralizer pluralizer = pluralizers.get(locale, code);
        if (pluralizer == null) {
            pluralizer = new FormatPluralizer(code, messageSource.getMessage(code + "!pl", null, null, locale));
            pluralizers.put(locale, code, pluralizer);
        }
        String pluralForm = pluralizer.selectCode(value);
        return messageSource.getMessage(pluralForm, new Object[]{value}, pluralForm, locale);
    }
}
