package waypalm.site.web.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.util.DateUtil;
import waypalm.common.web.extensions.ModelExtension;
import waypalm.site.services.EngineSetup;

import java.util.Calendar;

@ModelExtension("eCdn")
public class CdnExtender extends Extender<CdnExtender.Content> {
    @Autowired
    protected EngineSetup engineSetup;

    @Override
    protected CdnExtender.Content populate(WebRequest request, CdnExtender.Content extension, Object content) {
        extension = instantiateIfNull(extension, CdnExtender.Content.class);
        extension.debugMode = engineSetup.isDebugMode();
        extension.server = engineSetup.getSiteUrl();
        extension.serverStart = engineSetup.getStartTime();
        extension.contextPath = engineSetup.getServletContext();
        extension.locale = request.getLocale().getLanguage();
        return extension;
    }

    public static class Content {
        private boolean debugMode;
        private String server;
        private long serverStart;
        private String contextPath;
        private String locale;
        private String appZone;

        public long getServerStart() {
            return debugMode ? System.currentTimeMillis() : serverStart;
        }

        public String getServer() {
            return server;
        }

        public String getContextPath() {
            return contextPath;
        }

        public String getAppZone() {
            return appZone;
        }

        public void setAppZone(String appZone) {
            this.appZone = appZone;
        }

        public String getLocale() {
            return locale;
        }

        public boolean isDebugMode() {
            return debugMode;
        }
    }
}
