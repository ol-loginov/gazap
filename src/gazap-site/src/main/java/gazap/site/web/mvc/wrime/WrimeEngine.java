package gazap.site.web.mvc.wrime;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class WrimeEngine {
    private final Map<String, Class<? extends WrimeWriter>> urlToClassMappings = new HashMap<String, Class<? extends WrimeWriter>>();

    private String rootPath;
    private ClassLoader rootLoader;

    private EnumSet<Scanner> scannerOptions = EnumSet.noneOf(Scanner.class);

    public String getRootPath() {
        return rootPath;
    }

    public WrimeEngine() throws WrimeException {
        try {
            createRootLoader();
        } catch (IOException e) {
            throw new WrimeException("fail to initialize workplace", e);
        }
    }

    private void createRootLoader() throws IOException, WrimeException {
        File tmpFolder = File.createTempFile("wrime", "");
        if (!tmpFolder.delete()) {
            throw new WrimeException("fail to delete just created temporary file", null);
        }
        if (!tmpFolder.mkdir()) {
            throw new WrimeException("fail to create directory from just created temporary file", null);
        }
        tmpFolder.deleteOnExit();
        this.rootPath = tmpFolder.getAbsolutePath();
        this.rootLoader = new URLClassLoader(new URL[]{tmpFolder.toURI().toURL()}, getClass().getClassLoader());
    }

    public WrimeWriter newWriter(ScriptResource resource, Writer writer) throws Exception {
        String path = resource.getPath();
        Class<? extends WrimeWriter> writerClass = urlToClassMappings.get(path);
        if (writerClass == null) {
            writerClass = compile(parse(resource));
            urlToClassMappings.put(path, writerClass);
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

    protected void scan(ScriptResource resource, WrimeScanner.Receiver receiver) throws WrimeException {
        WrimeScanner scanner = new WrimeScanner();
        scanner.configure(scannerOptions);
        scanner.parse(resource, receiver);
    }

    protected WrimeCompiler parse(ScriptResource resource) throws WrimeException {
        WrimeCompiler compiler = new WrimeCompiler(this);
        scan(resource, compiler.createReceiver());
        return compiler;
    }

    private Class<WrimeWriter> compile(WrimeCompiler parse) {
        return null;
    }

    public void configure(Scanner option, boolean enable) {
        if (enable) {
            scannerOptions.add(option);
        } else {
            scannerOptions.remove(option);
        }
    }

    public ClassLoader getRootLoader() {
        return rootLoader;
    }


    public enum Scanner {
        EAT_SPACE
    }
}
