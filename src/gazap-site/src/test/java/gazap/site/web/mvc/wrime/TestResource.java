package gazap.site.web.mvc.wrime;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.StringWriter;

public class TestResource {
    public static Resource load(String url) {
        return new ClassPathResource("/wrime/" + url, TestResource.class.getClassLoader());
    }

    public static void verify(String expectedResourceName, String content) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(load(expectedResourceName).getInputStream(), writer, "UTF-8");
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(writer.toString(), content);
    }
}
