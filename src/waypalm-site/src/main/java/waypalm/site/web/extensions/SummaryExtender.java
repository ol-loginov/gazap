package waypalm.site.web.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtension;
import waypalm.domain.dao.WorldRepository;

@ModelExtension("eSummary")
public class SummaryExtender extends Extender<SummaryExtender.Content> {
    @Autowired
    WorldRepository worldRepository;

    @Override
    protected SummaryExtender.Content populate(WebRequest request, SummaryExtender.Content extension, ModelMap model) {
        SummaryExtender.Content module = instantiateIfNull(extension, SummaryExtender.Content.class);
        module.worldCount = worldRepository.countWorld();
        module.avatarCount = worldRepository.countAvatar();
        return module;
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
