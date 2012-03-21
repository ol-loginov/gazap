package gazap.site.web.mvc.wrime;

import javassist.ClassPool;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class WrimeEngine {
    private final Map<String, Class<? extends WrimeWriter>> urlToClassMappings = new HashMap<String, Class<? extends WrimeWriter>>();

    private ClassPool rootPool;
    private String rootPath;

    public ClassPool getRootPool() {
        if (rootPool == null) {
            rootPool = new ClassPool(true);
        }
        return rootPool;
    }

    public String getRootPath() {
        return rootPath;
    }

    public WrimeEngine() throws WrimeException {
        File tmpFolder;
        try {
            tmpFolder = File.createTempFile("wrime", "");
        } catch (IOException e) {
            throw new WrimeException("Fail to create temporary folder", e);
        }
        tmpFolder.delete();
        tmpFolder.mkdir();
        tmpFolder.deleteOnExit();
        this.rootPath = tmpFolder.getAbsolutePath();
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
        new WrimeScanner().parse(resource, receiver);
    }

    protected WrimeCompiler parse(ScriptResource resource) throws WrimeException {
        WrimeCompiler compiler = new WrimeCompiler(this);
        scan(resource, compiler);
        return compiler;
    }

    private Class<WrimeWriter> compile(WrimeCompiler parse) {
        return null;
    }
}
