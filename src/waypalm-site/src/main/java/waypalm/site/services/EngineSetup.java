package waypalm.site.services;

import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;

@Named
public class EngineSetup {
    public static final long startTime = System.currentTimeMillis();

    @Value("${engine.url}")
    private String siteUrl;
    @Value("${engine.context}")
    private String servletContext;
    @Value("${engine.debug}")
    private boolean debugMode;

    public long getStartTime() {
        return startTime;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getServletContext() {
        return servletContext;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}
