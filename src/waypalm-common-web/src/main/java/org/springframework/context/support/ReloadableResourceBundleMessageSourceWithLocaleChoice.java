package org.springframework.context.support;

import org.apache.commons.lang.text.ExtendedMessageFormat;
import org.apache.commons.lang.text.FormatFactory;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReloadableResourceBundleMessageSourceWithLocaleChoice extends ReloadableResourceBundleMessageSource {
    public static final Map<String, FormatFactory> messageFormats = new TreeMap<>();

    static {
        messageFormats.put("plural", NumberPluralFormat.FACTORY);
    }

    @Override
    protected ExtendedMessageFormat createMessageFormat(String msg, Locale locale) {
        return new ExtendedMessageFormat(msg, locale, messageFormats);
    }

    public static class NumberPluralFormat extends Format {
        public static final Pattern SELECTOR = Pattern.compile("^((-?\\d+)|(-?\\d+--?\\d+)|one)#");
        public static final FormatFactory FACTORY = new FormatFactory() {
            @Override
            public NumberPluralFormat getFormat(String name, String arguments, Locale locale) {
                NumberPluralFormat format = new NumberPluralFormat();
                for (String selector : arguments.split("\\|")) {
                    Matcher matcher = SELECTOR.matcher(selector);
                    while (matcher.find()) {
                    }
                }
                return format;
            }
        };

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            throw new IllegalStateException("not implemented");
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            throw new IllegalStateException("not implemented");
        }
    }
}
