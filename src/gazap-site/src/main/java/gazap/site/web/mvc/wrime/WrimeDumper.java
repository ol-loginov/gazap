package gazap.site.web.mvc.wrime;

import org.springframework.core.io.Resource;

import java.io.IOException;

public class WrimeDumper implements WrimeScanner.Receiver {
    @Override
    public void startResource(Resource resource) throws IOException {
        System.out.println("[enter resource " + resource.getURL() + "]");
    }

    @Override
    public void finishResource() {
        System.out.println("[leave resource]");
    }

    @Override
    public void text(String text) {
        System.out.println("[write] " + text);
    }

    @Override
    public void startExpression() {
        System.out.println("[enter expression]");
    }

    @Override
    public void completeExpression() {
        System.out.println("[leave expression]");
    }
}
