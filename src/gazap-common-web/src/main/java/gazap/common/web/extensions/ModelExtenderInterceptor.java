package gazap.common.web.extensions;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

public class ModelExtenderInterceptor implements WebRequestInterceptor {
    private ModelExtender[] extenders;

    public void setExtenders(ModelExtender[] extenders) {
        this.extenders = extenders;
    }

    @Override
    public void preHandle(WebRequest request) throws Exception {
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        if (model == null) {
            return;
        }
        if (hasExtenders()) {
            for (ModelExtender extender : extenders) {
                extender.extend(request, model);
            }
        }
    }

    private boolean hasExtenders() {
        return extenders != null && extenders.length > 0;
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
    }
}
