package waypalm.site.web.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.util.DateUtil;
import waypalm.site.services.EngineSetup;

import java.util.Calendar;

public class CdnExtender extends Extender<CdnExtension> {
    @Autowired
    protected EngineSetup engineSetup;

    @Override
    protected CdnExtension populate(WebRequest request, CdnExtension extension, Object content) {
        extension = instantiateIfNull(extension, CdnExtension.class);
        extension.setServer(engineSetup.getSiteUrl());
        extension.setContextPath(engineSetup.getServletContext());
        extension.setYear(DateUtil.utc().get(Calendar.YEAR));
        extension.setLocale(request.getLocale().toString());
        extension.setDebugMode(engineSetup.isDebugMode());
        return extension;
    }
}
