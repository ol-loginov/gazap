package gazap.site.web.mvc.wrime;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.StringWriter;

public class WrimeScannerTest {
    private String parse(Resource resource) throws IOException {
        StringWriter result = new StringWriter();
        WrimeEngine engine = new WrimeEngine();
        engine.parse(resource, new WrimeScannerDumper(result, TestResource.getResourceBasePath()));
        return result.toString();
    }

    @Test
    @Ignore
    public void emptyTemplate() throws Exception {
        String result = parse(TestResource.load("000.html"));
        TestResource.verify("000.html.scanner", result);
    }

    @Test
    @Ignore
    public void oneVar() throws Exception {
        String result = parse(TestResource.load("001.html"));
        TestResource.verify("001.html.scanner", result);
    }

    @Test
    public void e003() throws Exception {
        String result = parse(TestResource.load("003.txt"));
        TestResource.verify("003.txt.scanner", result);
    }
}
