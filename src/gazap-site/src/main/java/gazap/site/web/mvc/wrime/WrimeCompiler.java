package gazap.site.web.mvc.wrime;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class WrimeCompiler {
    public Class<WrimeWriter> compile(ResourceLoader resourceLoader, String url) {
        Resource resource = resourceLoader.getResource(url);
        return null;
    }
}
