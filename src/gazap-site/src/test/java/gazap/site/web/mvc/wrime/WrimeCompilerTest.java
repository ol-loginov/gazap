package gazap.site.web.mvc.wrime;

import org.junit.Test;

public class WrimeCompilerTest {
    public WrimeCompiler parse(ScriptResource resource) throws WrimeException {
        WrimeEngine engine = new WrimeEngine();
        engine.configure(WrimeEngine.Scanner.EAT_SPACE, true);
        return engine.parse(resource);
    }

    @Test
    public void empty() throws WrimeException {
        WrimeCompiler compiler = parse(TestResource.load("005.txt"));
        TestResource.verify("005.code", compiler.getClassCode());
    }

    @Test
    public void oneVar() throws WrimeException {
        WrimeCompiler compiler = parse(TestResource.load("006.txt"));
        TestResource.verify("006.code", compiler.getClassCode());
    }
}

