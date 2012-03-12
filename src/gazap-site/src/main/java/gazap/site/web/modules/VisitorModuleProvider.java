package gazap.site.web.modules;

import com.iserv2.commons.mvc.views.Content;
import gazap.domain.entity.UserProfile;
import gazap.site.services.ModelViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class VisitorModuleProvider extends GazapModuleBuilder<VisitorModule> {
    @Value("${engine.debug}")
    protected boolean debug;
    @Autowired
    protected ModelViewer modelViewer;

    @Override
    protected boolean complete(HttpServletRequest request, HttpServletResponse response, Content content, VisitorModule module) {
        UserProfile user = getLoggedUser();
        module.setDebug(debug);
        module.setUser(user != null ? modelViewer.userDetails(user) : null);
        return true;
    }
}
