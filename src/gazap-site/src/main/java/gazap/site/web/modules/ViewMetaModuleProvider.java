package gazap.site.web.modules;

import com.iserv2.commons.mvc.views.Content;
import gazap.site.web.views.errors.ErrorPage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ViewMetaModuleProvider extends GazapModuleBuilder<ViewMetaModule> {
    @Override
    protected boolean complete(HttpServletRequest request, HttpServletResponse response, Content content, ViewMetaModule module) {
        if (content instanceof ErrorPage) {
            ErrorPage page = (ErrorPage) content;
            if (StringUtils.hasText(page.getHttp())) {
                module.setTitleKey("http." + page.getHttp() + ".pageTitle");
            }
            return true;
        } else {
            return false;
        }
    }
}
