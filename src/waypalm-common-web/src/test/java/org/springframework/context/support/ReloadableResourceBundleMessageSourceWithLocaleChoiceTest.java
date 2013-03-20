package org.springframework.context.support;

import com.iserv2.test.ServiceTest;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class ReloadableResourceBundleMessageSourceWithLocaleChoiceTest extends ServiceTest<ReloadableResourceBundleMessageSourceWithLocaleChoice> {
    @Test
    public void createChoiceFormat() {
        MessageFormat messageFormat = service.createMessageFormat("{0,plural,0#are {3} no files|p1#cegf|n#is one file}", Locale.getDefault());
        assertFormatted("", messageFormat, 1);
    }

    private void assertFormatted(String expected, MessageFormat messageFormat, Object... arguments) {
        assertEquals(expected, messageFormat.format(arguments));
    }
}
