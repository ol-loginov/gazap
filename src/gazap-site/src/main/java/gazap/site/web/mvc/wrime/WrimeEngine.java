package gazap.site.web.mvc.wrime;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class WrimeEngine {
    private final Map<String, Class<? extends WrimeWriter>> urlToClassMappings = new HashMap<String, Class<? extends WrimeWriter>>();

    public WrimeWriter newWriter(Resource resource, Writer writer) throws Exception {
        String url = resource.getURL().toString();
        Class<? extends WrimeWriter> writerClass = urlToClassMappings.get(url);
        if (writerClass == null) {
            writerClass = compile(resource);
            urlToClassMappings.put(url, writerClass);
        }

        Constructor<? extends WrimeWriter> writerConstructor;
        try {
            writerConstructor = writerClass.getConstructor(Writer.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Wrime constructor is na", e);
        }

        try {
            return writerConstructor.newInstance(writer);
        } catch (InstantiationException e) {
            throw new IllegalStateException("Wrime instance is na", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Wrime instance is na", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Wrime instance is na", e);
        }
    }

    private Class<WrimeWriter> compile(Resource resource) throws IOException {
        parse(resource, null);
        return null;
    }

    public void parse(Resource resource, WrimeScanner.Receiver receiver) throws IOException {
        WrimeScanner wrimeScanner = new WrimeScanner();
        wrimeScanner.parse(resource, receiver);
    }
}
