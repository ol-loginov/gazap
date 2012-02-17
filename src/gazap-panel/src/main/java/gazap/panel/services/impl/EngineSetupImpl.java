package gazap.panel.services.impl;

import gazap.panel.services.EngineSetup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EngineSetupImpl implements EngineSetup {
    private String siteUrl;
    private String servletContext;

    @Override
    public String getSiteUrl() {
        return siteUrl;
    }

    @Value("${engine.url}")
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    @Override
    public String getServletContext() {
        return servletContext;
    }

    @Value("${engine.context}")
    public void setServletContext(String servletContext) {
        this.servletContext = servletContext;
    }
}
