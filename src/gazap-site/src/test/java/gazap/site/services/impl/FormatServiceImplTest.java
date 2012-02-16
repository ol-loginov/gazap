package gazap.site.services.impl;

import com.iserv2.test.ServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

public class FormatServiceImplTest extends ServiceTest<FormatServiceImpl> {
    private ResourceBundleMessageSource messageSource;

    @Override
    public void setUp() {
        super.setUp();
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        service.setMessageSource(messageSource);
    }

    @Test
    public void getUGA() {
        Assert.assertEquals("Welcome, UGA!", service.getMessage(null, "just.message", "UGA"));
    }

    @Test
    public void pluralize() {
        Assert.assertEquals("0 секунд", service.pluralize(null, 0, "seconds"));
        Assert.assertEquals("1 секунда", service.pluralize(null, 1, "seconds"));
        Assert.assertEquals("2 секунды", service.pluralize(null, 2, "seconds"));
        Assert.assertEquals("10 секунд", service.pluralize(null, 10, "seconds"));
    }
}
