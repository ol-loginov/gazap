package gazap.site.web.mvc.wrime;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FunctionCallTest {
    private TestResource resources = new TestResource(FunctionCallTest.class);

    private void check(String resource) throws WrimeException {
        WrimeCompiler compiler = parse(resources.load(resource + ".txt"));
        resources.verify(resource + ".code", compiler.getClassCode());
    }

    private void checkError(String resource, String message) {
        boolean caught = false;
        try {
            check(resource);
        } catch (WrimeException e) {
            caught = true;
            assertEquals(message, e.getMessage());
        }
        if (!caught) {
            fail("Exception expected");
        }
    }

    private WrimeCompiler parse(ScriptResource resource) throws WrimeException {
        WrimeEngine engine = new WrimeEngine();
        engine.configure(WrimeEngine.Scanner.EAT_SPACE, true);
        return engine.parse(resource);
    }

    @Test
    public void vararg() throws WrimeException {
        check("013");
    }

    @Test
    public void argumentCountCheck() throws WrimeException {
        checkError("012", "Expression analyser reports an error: cannot find suitable method with name 'call' (FunctionCallTest/012.txt:2, column 37)");
    }

    @Test
    public void call2Arg() throws WrimeException {
        check("010");
    }

    @Test
    public void callNativeOverload() throws WrimeException {
        check("011");
    }

    @Test
    public void callOnVoidResult() throws WrimeException {
        checkError("014", "Expression analyser reports an error: no invocable at the point (FunctionCallTest/014.txt:2, column 33)");
    }
}
