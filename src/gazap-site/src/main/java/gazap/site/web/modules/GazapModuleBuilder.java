package gazap.site.web.modules;

import com.iserv2.commons.mvc.support.ModuleBuilderBase;
import com.iserv2.commons.mvc.views.ContentModule;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserProfile;
import gazap.site.services.UserAccess;
import gazap.site.web.mvc.PrincipalProvider;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GazapModuleBuilder<T extends ContentModule> extends ModuleBuilderBase<T> {
    @Autowired
    protected UserAccess auth;
    @Autowired
    protected UserProfileDao userProfileDao;

    protected UserProfile getLoggedUser() {
        return PrincipalProvider.getLoggedUser(userProfileDao);
    }
}
