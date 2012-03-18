package gazap.site.web.mvc.support;

import org.springframework.web.servlet.View;

import java.util.Locale;

public class DeviceBasedViewResolver extends org.springframework.web.servlet.view.InternalResourceViewResolver {
    private String defaultLayout = null;

    public String getDefaultLayout() {
        return defaultLayout;
    }

    public void setDefaultLayout(String defaultLayout) {
        this.defaultLayout = defaultLayout;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (supportsLayout(viewName, locale)) {
            return super.resolveViewName(defaultLayout + viewName, locale);
        }
        return super.resolveViewName(viewName, locale);
    }

    private boolean supportsLayout(String viewName, Locale locale) {
        if (viewName.startsWith(REDIRECT_URL_PREFIX) || viewName.startsWith(FORWARD_URL_PREFIX)) {
            return false;
        }
        return defaultLayout != null;
    }
}
