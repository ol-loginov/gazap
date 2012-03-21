package gazap.site.web.mvc.wrime.spring;

import gazap.site.web.mvc.wrime.ScriptResource;
import gazap.site.web.mvc.wrime.WrimeException;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class ClassPathScriptSource implements ScriptResource {
    private final ClassPathResource resource;
    private final String resourceBase;

    public ClassPathScriptSource(ClassPathResource resource, String resourceBase) {
        this.resource = resource;
        this.resourceBase = resourceBase;
    }

    @Override
    public InputStream getInputStream() throws WrimeException {
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            throw new WrimeException("fail to open resource", e);
        }
    }

    @Override
    public String getPath() {
        String path = resource.getPath();
        if (resourceBase != null && path.startsWith(resourceBase)) {
            return path.substring(resourceBase.length());
        }
        return path;
    }
}
