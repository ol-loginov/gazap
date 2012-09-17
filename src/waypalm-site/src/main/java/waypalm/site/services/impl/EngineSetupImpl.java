package waypalm.site.services.impl;

import waypalm.site.services.EngineSetup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EngineSetupImpl implements EngineSetup {
    @Value("${engine.url}")
    private String siteUrl;
    @Value("${engine.context}")
    private String servletContext;
    @Value("${engine.debug}")
    private boolean debugMode;

    @Override
    public String getSiteUrl() {
        return siteUrl;
    }

    @Override
    public String getServletContext() {
        return servletContext;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}
