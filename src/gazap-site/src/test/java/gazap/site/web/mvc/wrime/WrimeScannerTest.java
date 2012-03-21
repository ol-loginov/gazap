package gazap.site.web.mvc.wrime;

import org.junit.Test;

import java.io.StringWriter;

public class WrimeScannerTest {
    private String parse(ScriptResource resource) throws WrimeException {
        StringWriter result = new StringWriter();
        WrimeEngine engine = new WrimeEngine();
        engine.scan(resource, new WrimeScannerDumper(result));
        return result.toString();
    }

    @Test
    public void emptyTemplate() throws Exception {
        String result = parse(TestResource.load("000.html"));
        TestResource.verify("000.html.scanner", result);
    }

    @Test
    public void oneVar() throws Exception {
        String result = parse(TestResource.load("001.html"));
        TestResource.verify("001.html.scanner", result);
    }

    @Test
    public void e003() throws Exception {
        String result = parse(TestResource.load("003.txt"));
        TestResource.verify("003.txt.scanner", result);
    }

    @Test
    public void e004() throws Exception {
        String result = parse(TestResource.load("004.txt"));
        TestResource.verify("004.txt.scanner", result);
    }
}
