package gazap.site.web.mvc.wrime;

import gazap.site.web.mvc.wrime.spring.ClassPathScriptSource;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.StringWriter;

public class TestResource {
    public static String getResourceBasePath() {
        try {
            return new ClassPathResource("/wrime", TestResource.class.getClassLoader()).getURL().toString();
        } catch (IOException e) {
            return "";
        }
    }

    public static ScriptResource load(String url) {
        return new ClassPathScriptSource(new ClassPathResource("/wrime/" + url, TestResource.class.getClassLoader()), getResourceBasePath());
    }

    public static void verify(String expectedResourceName, String content) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(load(expectedResourceName).getInputStream(), writer, "UTF-8");
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } catch (WrimeException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(writer.toString(), content);
    }
}
