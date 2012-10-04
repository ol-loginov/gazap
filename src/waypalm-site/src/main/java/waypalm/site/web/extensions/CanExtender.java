package waypalm.site.web.extensions;

import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtension;

@ModelExtension("eCan")
public class CanExtender extends Extender<CanExtender.Content> {
    @Override
    protected CanExtender.Content populate(WebRequest request, CanExtender.Content extension, Object content) {
        return instantiateIfNull(extension, CanExtender.Content.class);
    }

    public static class Content {
        public boolean editMap(int mapId) {
            return true;
        }
    }

}
