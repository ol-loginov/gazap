package waypalm.site.web.extensions;

import waypalm.common.web.extensions.ModelExtension;
import waypalm.domain.entity.UserProfile;
import waypalm.site.model.viewer.UserTitle;
import waypalm.site.services.ModelViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.WebRequest;

@ModelExtension("eVisitor")
public class VisitorExtender extends Extender<VisitorExtender.Content> {
    @Value("${engine.debug}")
    protected boolean debug;
    @Autowired
    protected ModelViewer modelViewer;

    @Override
    protected VisitorExtender.Content populate(WebRequest request, VisitorExtender.Content extension, Object content) {
        UserProfile user = getLoggedUser();
        extension = instantiateIfNull(extension, VisitorExtender.Content.class);
        extension.setDebug(debug);
        extension.setUser(user != null ? modelViewer.userTitle(user) : null);
        return extension;
    }

    public static class Content {
        private boolean debug;
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

        public boolean isDebug() {
            return debug;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
        }

        public int getUserId() {
            return user != null ? user.getId() : 0;
        }
    }

}
