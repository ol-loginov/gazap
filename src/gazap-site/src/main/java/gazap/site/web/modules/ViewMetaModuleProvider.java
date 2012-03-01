package gazap.site.web.modules;

import com.iserv2.commons.mvc.views.Content;
import gazap.site.web.annotations.PageTitleKey;
import gazap.site.web.views.errors.AuthError;
import gazap.site.web.views.errors.HttpError;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ViewMetaModuleProvider extends GazapModuleBuilder<ViewMetaModule> {
    @Override
    protected boolean complete(HttpServletRequest request, HttpServletResponse response, Content content, ViewMetaModule module) {
        if (content == null) {
            return false;
        }

        if (content instanceof AuthError) {
            setHeader((AuthError) content, module);
        } else if (content instanceof HttpError) {
            setHeader((HttpError) content, module);
        } else if (content.getClass().getAnnotation(PageTitleKey.class) != null) {
            setHeader(content.getClass().getAnnotation(PageTitleKey.class), module);
        }

        return true;
    }

    private void setHeader(PageTitleKey annotation, ViewMetaModule module) {
        if (StringUtils.hasText(annotation.value())) {
            module.setTitleKey(annotation.value());
        }
    }

    private void setHeader(AuthError view, ViewMetaModule module) {
        if (StringUtils.hasText(view.getErrorCode())) {
            module.setTitleKey("auth." + view.getErrorCode() + ".pageTitle");
        }
    }

    private void setHeader(HttpError view, ViewMetaModule module) {
        if (StringUtils.hasText(view.getHttp())) {
            module.setTitleKey("http." + view.getHttp() + ".pageTitle");
        }
    }
}
