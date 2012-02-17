package gazap.panel.web.mvc.support;

import gazap.panel.services.FormatService;

public class XsltView extends com.iserv2.commons.mvc.support.XsltView {
    private FormatService formatService;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        formatService = getApplicationContextBean(FormatService.class);
    }

    @Override
    protected Object createContentResources() {
        return new XsltViewResources(formatService);
    }
}
