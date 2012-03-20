package gazap.site.web.mvc.support;

import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Locale;

public class DeviceLayoutSelector {
    private String defaultLayout = null;

    public void setDefaultLayout(String defaultLayout) {
        this.defaultLayout = defaultLayout;
    }

    public boolean supportsLayout(String viewName) {
        if (viewName.startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX) || viewName.startsWith(UrlBasedViewResolver.FORWARD_URL_PREFIX)) {
            return false;
        }
        return defaultLayout != null;
    }

    public String selectVewWithLayout(String viewName, Locale locale) {
        if (supportsLayout(viewName)) {
            return defaultLayout + viewName;
        }
        return viewName;
    }
}
