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
        extension.setServer(engineSetup.getSiteUrl());
        extension.setContextPath(engineSetup.getServletContext());
        extension.setYear(DateUtil.utc().get(Calendar.YEAR));
        extension.setLocale(request.getLocale().getLanguage());
        extension.setDebugMode(engineSetup.isDebugMode());
        return extension;
    }

    public static class Content {
        private boolean debugMode;
        private String server;
        private String contextPath;
        private int year;
        private String locale;

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getContextPath() {
            return contextPath;
        }

        public void setContextPath(String contextPath) {
            this.contextPath = contextPath;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public boolean isDebugMode() {
            return debugMode;
        }

        public void setDebugMode(boolean debugMode) {
            this.debugMode = debugMode;
        }
    }
}
