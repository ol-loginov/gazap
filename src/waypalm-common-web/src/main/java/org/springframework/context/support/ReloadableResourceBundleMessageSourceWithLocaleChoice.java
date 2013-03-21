package org.springframework.context.support;

import com.iserv2.commons.text.ExtendedMessageFormatUtil;
import org.apache.commons.lang.text.ExtendedMessageFormat;

import java.util.Locale;

public class ReloadableResourceBundleMessageSourceWithLocaleChoice extends ReloadableResourceBundleMessageSource {
    @Override
    protected ExtendedMessageFormat createMessageFormat(String msg, Locale locale) {
        return ExtendedMessageFormatUtil.createMessageFormat(msg, locale);
    }
}
