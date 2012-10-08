package waypalm.site.web.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtension;
import waypalm.domain.entity.UserProfile;
import waypalm.site.model.viewer.UserTitle;
import waypalm.site.services.ModelViewer;
import waypalm.site.services.UserService;
import waypalm.site.services.WorldService;

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
            extension.worldCount = userRepository.getProfileSummary(user).getWorldCount();
            extension.avatarCount = userRepository.getProfileSummary(user).getAvatarCount();
        }
        return extension;
    }

    public static class Content {
        private UserTitle user;
        private int worldCount;
        private int avatarCount;

        public UserTitle getUser() {
            return user;
        }

        public boolean isLogged() {
            return user != null;
        }

        public int getUserId() {
            return user != null ? user.getId() : 0;
        }

        public int getWorldCount() {
            return worldCount;
        }

        public int getAvatarCount() {
            return avatarCount;
        }
    }
}
