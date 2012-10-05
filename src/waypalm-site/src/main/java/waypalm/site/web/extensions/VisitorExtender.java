package waypalm.site.web.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtension;
import waypalm.domain.entity.UserProfile;
import waypalm.site.model.viewer.UserTitle;
import waypalm.site.services.ModelViewer;

@ModelExtension("eVisitor")
public class VisitorExtender extends Extender<VisitorExtender.Content> {
    @Autowired
    protected ModelViewer modelViewer;

    @Override
    protected VisitorExtender.Content populate(WebRequest request, VisitorExtender.Content extension, Object content) {
        UserProfile user = getLoggedUser();
        extension = instantiateIfNull(extension, VisitorExtender.Content.class);
        extension.setUser(user != null ? modelViewer.userTitle(user) : null);
        return extension;
    }

    public static class Content {
        private UserTitle user;

        public UserTitle getUser() {
            return user;
        }

        public void setUser(UserTitle user) {
            this.user = user;
        }

        public boolean isLogged() {
            return user != null;
        }

        public int getUserId() {
            return user != null ? user.getId() : 0;
        }
    }

}
