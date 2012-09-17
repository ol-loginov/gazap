package waypalm.site.web.extensions;

import waypalm.site.web.views.errors.AuthError;
import waypalm.site.web.views.errors.HttpError;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

public class ViewMetaExtender extends Extender<ViewMetaExtension> {
    @Override
    public ViewMetaExtension populate(WebRequest request, ViewMetaExtension extension, Object content) {
        if (content == null) {
            return null;
        }

        if (content instanceof AuthError) {
            extension = instantiateIfNull(extension, ViewMetaExtension.class);
            setHeader((AuthError) content, extension);
        } else if (content instanceof HttpError) {
            extension = instantiateIfNull(extension, ViewMetaExtension.class);
            setHeader((HttpError) content, extension);
        }

        return extension;
    }

    private void setHeader(AuthError view, ViewMetaExtension module) {
        if (StringUtils.hasText(view.getErrorCode())) {
            module.setTitleKey("auth." + view.getErrorCode() + ".pageTitle");
        }
    }

    private void setHeader(HttpError view, ViewMetaExtension module) {
        if (StringUtils.hasText(view.getHttp())) {
            module.setTitleKey("http." + view.getHttp() + ".pageTitle");
        }
    }
}
