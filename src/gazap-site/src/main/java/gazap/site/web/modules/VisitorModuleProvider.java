package gazap.site.web.modules;

import com.iserv2.commons.mvc.views.Content;
import gazap.common.util.GravatarHelper;
import gazap.domain.entity.UserProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Service
public class VisitorModuleProvider extends GazapModuleBuilder<VisitorModule> {
    private final Random random = new Random(System.currentTimeMillis());
    private boolean debug;

    @Value("${engine.debug}")
    protected void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    protected boolean complete(HttpServletRequest request, HttpServletResponse response, Content content, VisitorModule module) {
        UserProfile user = getLoggedUser();
        module.setDebug(debug);
        if (user != null) {
            module.setLogged(true);
            module.setId(user.getId());
            setThumbnail(module, user);
            setProfileInfo(module, user);
        }
        module.setWelcomePrompt(Integer.toString(random.nextInt(5)));
        return true;
    }

    private void setProfileInfo(VisitorModule module, UserProfile user) {
        module.setName(user.getDisplayName());
    }

    private void setThumbnail(VisitorModule module, UserProfile profile) {
        module.setGravatar(GravatarHelper.hashOrDefault(profile.getEmail()));
    }
}
