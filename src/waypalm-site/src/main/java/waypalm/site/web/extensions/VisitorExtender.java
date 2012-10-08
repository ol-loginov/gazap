package waypalm.site.web.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtension;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.ProfileSummary;
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
        Profile user = getLoggedUser();
        extension = instantiateIfNull(extension, VisitorExtender.Content.class);
        if (user != null) {
            extension.user = modelViewer.profileTitle(user);

            ProfileSummary summary = userRepository.getProfileSummary(user);
            extension.worldTotalCount = summary.getWorldFavourite() + summary.getWorldOwned();
            extension.avatarTotalCount = summary.getAvatarFavourite() + summary.getAvatarOwned();
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
