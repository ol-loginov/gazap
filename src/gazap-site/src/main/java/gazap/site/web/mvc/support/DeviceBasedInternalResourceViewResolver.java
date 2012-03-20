package gazap.site.web.mvc.support;

import org.springframework.web.servlet.View;

import java.util.Locale;

public class DeviceBasedInternalResourceViewResolver extends org.springframework.web.servlet.view.InternalResourceViewResolver {
    private DeviceLayoutSelector layoutSelector = new DeviceLayoutSelector();

    public void setDefaultLayout(String defaultLayout) {
        layoutSelector.setDefaultLayout(defaultLayout);
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return super.resolveViewName(layoutSelector.selectVewWithLayout(viewName, locale), locale);
    }
}
