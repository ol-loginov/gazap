package waypalm.site.services.impl;

import com.iserv2.test.ServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

public class FormatServiceImplTest extends ServiceTest<FormatServiceImpl> {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        service.messageSource = messageSource;
    }

    @Test
    public void getUGA() {
        Assert.assertEquals("Welcome, UGA!", service.getMessage(null, "just.message", "UGA"));
    }
}
