package waypalm.site.web.extensions;

import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtension;

@ModelExtension("eSummary")
public class SummaryExtender extends Extender<SummaryExtender.Content> {
    @Override
    protected SummaryExtender.Content populate(WebRequest request, SummaryExtender.Content extension, Object content) {
        return instantiateIfNull(extension, SummaryExtender.Content.class);
    }

    public static class Content {
        int worldCount;
        int avatarCount;

        public int getWorldCount() {
            return worldCount;
        }

        public int getAvatarCount() {
            return avatarCount;
        }
    }
}
