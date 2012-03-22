package gazap.site.web.mvc.wrime;

import org.junit.Test;

public class SimpleCompilerTest {
    private TestResource resources = new TestResource(SimpleCompilerTest.class);

    private void check(String resource) throws WrimeException {
        WrimeCompiler compiler = parse(resources.load(resource + ".txt"));
        resources.verify(resource + ".code", compiler.getClassCode());
    }

    private WrimeCompiler parse(ScriptResource resource) throws WrimeException {
        WrimeEngine engine = new WrimeEngine();
        engine.configure(WrimeEngine.Scanner.EAT_SPACE, true);
        return engine.parse(resource);
    }

    @Test
    public void empty() throws WrimeException {
        check("005");
    }

    @Test
    public void defineOneModelParameter() throws WrimeException {
        check("006");
    }

    @Test
    public void oneModelParameter() throws WrimeException {
        check("007");
    }

    @Test
    public void simpleWithCall() throws WrimeException {
        check("008");
    }

    @Test
    public void nestedProp() throws WrimeException {
        check("009");
    }

}

