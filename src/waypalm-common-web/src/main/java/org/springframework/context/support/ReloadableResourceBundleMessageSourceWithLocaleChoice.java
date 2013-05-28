package org.springframework.context.support;

import com.iserv2.commons.text.ExtendedMessageFormats;
import org.apache.commons.lang.text.ExtendedMessageFormat;

import java.util.Locale;

public class ReloadableResourceBundleMessageSourceWithLocaleChoice extends ReloadableResourceBundleMessageSource {
    private final ExtendedMessageFormats messageFormats = new ExtendedMessageFormats();

    @Override
    protected ExtendedMessageFormat createMessageFormat(String msg, Locale locale) {
        return messageFormats.createMessageFormat(msg, locale);
    }
}
