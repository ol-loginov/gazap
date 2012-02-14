package gazap.site.services.impl;

import com.iserv2.commons.text.FormatPluralizer;
import gazap.site.services.FormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FormatServiceImpl implements FormatService {
    private MessageSource messageSource;
    private final Map<String, FormatPluralizer> pluralizers = Collections.synchronizedMap(new HashMap<String, FormatPluralizer>(100));

    @Autowired
    protected void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, code, null);
    }

    @Override
    public String pluralize(long value, String code) {
        FormatPluralizer pluralizer = pluralizers.get(code);
        if (pluralizer == null) {
            pluralizer = new FormatPluralizer(code, messageSource.getMessage(code + "!pl", null, null, null));
            pluralizers.put(code, pluralizer);
        }
        String pluralForm = pluralizer.selectCode(value);
        return messageSource.getMessage(pluralForm, new Object[]{value}, pluralForm, null);
    }
}
