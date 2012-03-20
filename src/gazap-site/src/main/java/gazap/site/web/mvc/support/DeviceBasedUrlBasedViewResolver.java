package gazap.site.web.mvc.support;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Locale;

public class DeviceBasedUrlBasedViewResolver extends UrlBasedViewResolver {
    private DeviceLayoutSelector layoutSelector = new DeviceLayoutSelector();

    public void setDefaultLayout(String defaultLayout) {
        layoutSelector.setDefaultLayout(defaultLayout);
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return super.resolveViewName(layoutSelector.selectVewWithLayout(viewName, locale), locale);
    }
}
