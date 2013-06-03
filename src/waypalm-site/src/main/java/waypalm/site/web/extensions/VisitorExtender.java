package waypalm.site.web.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtension;
import waypalm.domain.entity.Profile;
import waypalm.site.model.view.UserName;
import waypalm.site.services.ModelViewer;
import waypalm.site.services.UserService;

@ModelExtension("eVisitor")
public class VisitorExtender extends Extender<VisitorExtender.Content> {
    @Autowired
    protected ModelViewer modelViewer;
    @Autowired
    protected UserService userService;

    @Override
    protected VisitorExtender.Content populate(WebRequest request, VisitorExtender.Content extension, ModelMap model) {
        Profile user = auth.loadCurrentProfile();
        extension = instantiateIfNull(extension, VisitorExtender.Content.class);
        if (user != null) {
            extension.user = modelViewer.createUserName(user);
        }
        return extension;
    }

    public static class Content {
        private UserName user;

        public UserName getUser() {
            return user;
        }

        public boolean isLogged() {
            return user != null;
        }
    }
}
