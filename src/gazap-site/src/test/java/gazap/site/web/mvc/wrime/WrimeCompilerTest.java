package gazap.site.web.mvc.wrime;

import org.junit.Test;

public class WrimeCompilerTest {
    @Test
    public void empty() throws WrimeException {
        WrimeEngine engine = new WrimeEngine();
        WrimeCompiler compiler = engine.parse(TestResource.load("005.txt"));
        TestResource.verify("005.code", compiler.getClassCode());
    }
}

