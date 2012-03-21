package gazap.site.web.mvc.wrime;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.ui.ModelMap;

import java.io.StringWriter;

public class WrimeEngineTest {
    private String write(Resource resource, ModelMap map) throws Exception {
        StringWriter result = new StringWriter();
        WrimeEngine engine = new WrimeEngine();
        engine
                .newWriter(resource, result)
                .render(map);
        return result.toString();
    }

    @Test
    public void emptyTemplate() throws Exception {
        String result = write(TestResource.load("000.template.html"), new ModelMap());
        TestResource.verify("000.output.html", result);
    }

    @Test
    public void oneVar() throws Exception {
        String result = write(TestResource.load("001.template.html"), new ModelMap());
        TestResource.verify("001.output.html", result);
    }
}
