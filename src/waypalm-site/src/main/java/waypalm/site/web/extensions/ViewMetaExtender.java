package waypalm.site.web.extensions;

import org.springframework.web.context.request.WebRequest;

public class ViewMetaExtender extends Extender<ViewMetaExtension> {
    @Override
    public ViewMetaExtension populate(WebRequest request, ViewMetaExtension extension, Object content) {
        if (content == null) {
            return null;
        }
        return instantiateIfNull(extension, ViewMetaExtension.class);
    }
}
