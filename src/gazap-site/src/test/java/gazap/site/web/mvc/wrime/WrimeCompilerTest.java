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
    public void defineOneModelParameter() throws WrimeException {
        WrimeCompiler compiler = parse(TestResource.load("006.txt"));
        TestResource.verify("006.code", compiler.getClassCode());
    }

    @Test
    public void oneModelParameter() throws WrimeException {
        WrimeCompiler compiler = parse(TestResource.load("007.txt"));
        TestResource.verify("007.code", compiler.getClassCode());
    }

    @Test
    public void simpleWithCall() throws WrimeException {
        WrimeCompiler compiler = parse(TestResource.load("008.txt"));
        TestResource.verify("008.code", compiler.getClassCode());
    }

    @Test
    public void nestedProp() throws WrimeException {
        WrimeCompiler compiler = parse(TestResource.load("009.txt"));
        TestResource.verify("009.code", compiler.getClassCode());
    }

    @Test
    public void call2Arg() throws WrimeException {
        WrimeCompiler compiler = parse(TestResource.load("010.txt"));
        TestResource.verify("010.code", compiler.getClassCode());
    }

    @Test
    public void callNativeOverload() throws WrimeException {
        WrimeCompiler compiler = parse(TestResource.load("011.txt"));
        TestResource.verify("011.code", compiler.getClassCode());
    }

    @Test(expected = WrimeException.class)
    public void argumentCountCheck() throws WrimeException {
        WrimeCompiler compiler = parse(TestResource.load("012.txt"));
        TestResource.verify("012.code", compiler.getClassCode());
    }
}

