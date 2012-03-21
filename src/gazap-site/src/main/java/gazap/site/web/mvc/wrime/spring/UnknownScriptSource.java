package gazap.site.web.mvc.wrime.spring;

import gazap.site.web.mvc.wrime.ScriptResource;
import gazap.site.web.mvc.wrime.WrimeException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class UnknownScriptSource implements ScriptResource {
    private final Resource resource;

    public UnknownScriptSource(Resource resource) {
        this.resource = resource;
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
        try {
            return resource.getURL().toString();
        } catch (IOException e) {
            return resource.getFilename();
        }
    }
}
