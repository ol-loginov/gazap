package waypalm.site.web.extensions;

import org.springframework.web.context.request.WebRequest;

public class CanExtender extends Extender<CanExtension> {
    @Override
    protected CanExtension populate(WebRequest request, CanExtension extension, Object content) {
        return instantiateIfNull(extension, CanExtension.class);
    }
}
