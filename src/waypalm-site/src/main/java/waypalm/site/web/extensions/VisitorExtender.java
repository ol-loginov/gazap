package waypalm.site.web.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtension;
import waypalm.domain.entity.UserProfile;
import waypalm.domain.entity.UserSummary;
import waypalm.site.model.viewer.UserTitle;
import waypalm.site.services.ModelViewer;
import waypalm.site.services.UserService;

@ModelExtension("eVisitor")
public class VisitorExtender extends Extender<VisitorExtender.Content> {
    @Autowired
    protected ModelViewer modelViewer;
    @Autowired
    protected UserService userService;

    @Override
    protected VisitorExtender.Content populate(WebRequest request, VisitorExtender.Content extension, Object content) {
        UserProfile user = getLoggedUser();
        extension = instantiateIfNull(extension, VisitorExtender.Content.class);
        if (user != null) {
            extension.user = modelViewer.userTitle(user);

            UserSummary summary = userRepository.getProfileSummary(user);
            extension.worldTotalCount = summary.getWorldTotal();
            extension.avatarTotalCount = summary.getAvatarTotal();
            extension.worldCreateAvailable = summary.getWorldOwned() < summary.getWorldOwnedLimit();
        }
        return extension;
    }

    public static class Content {
        private UserTitle user;
        private int worldTotalCount;
        private boolean worldCreateAvailable = false;
        private int avatarTotalCount;

        public UserTitle getUser() {
            return user;
        }

        public boolean isLogged() {
            return user != null;
        }

        public int getUserId() {
            return user != null ? user.getId() : 0;
        }

        public int getWorldTotalCount() {
            return worldTotalCount;
        }

        public int getAvatarTotalCount() {
            return avatarTotalCount;
        }

        public boolean isWorldCreateAvailable() {
            return worldCreateAvailable;
        }
    }
}
