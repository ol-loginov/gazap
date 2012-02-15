package gazap.site.web.modules;

import com.iserv2.commons.mvc.support.ModuleBuilderBase;
import com.iserv2.commons.mvc.support.StaticPageUrlHandler;
import com.iserv2.commons.mvc.views.ContentModule;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserProfile;
import gazap.site.services.UserAccess;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.mvc.PrincipalProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

public abstract class GazapModuleBuilder<T extends ContentModule> extends ModuleBuilderBase<T> {
    @Autowired
    protected UserAccess auth;
    @Autowired
    protected UserProfileDao userProfileDao;

    protected UserProfile getLoggedUser() {
        return PrincipalProvider.getLoggedUser(userProfileDao);
    }

    public static BaseController resolveController(Object handler) {
        if (handler instanceof BaseController) {
            return (BaseController) handler;
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            return resolveController(method.getBean());
        }
        if (handler instanceof StaticPageUrlHandler) {
            /*
            StaticPageUrlHandler urlHandler = (StaticPageUrlHandler) handler;
            if (urlHandler.getContent() instanceof StaticPageController.StaticPageContentImpl) {
                return ((StaticPageController.StaticPageContentImpl) urlHandler.getContent()).getController();
            }
            */
        }
        return null;
    }
}
