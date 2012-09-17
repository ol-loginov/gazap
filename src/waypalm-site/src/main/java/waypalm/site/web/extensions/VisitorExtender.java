package waypalm.site.web.extensions;

import waypalm.domain.entity.UserProfile;
import waypalm.site.services.ModelViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.WebRequest;

public class VisitorExtender extends Extender<VisitorExtension> {
    @Value("${engine.debug}")
    protected boolean debug;
    @Autowired
    protected ModelViewer modelViewer;

    @Override
    protected VisitorExtension populate(WebRequest request, VisitorExtension extension, Object content) {
        UserProfile user = getLoggedUser();
        extension = instantiateIfNull(extension, VisitorExtension.class);
        extension.setDebug(debug);
        extension.setUser(user != null ? modelViewer.userTitle(user) : null);
        return extension;
    }
}
