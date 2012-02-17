package gazap.panel.web.modules;

import com.iserv2.commons.mvc.views.Content;
import gazap.common.util.DateUtil;
import gazap.panel.services.EngineSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

@Service
public class CdnModuleProvider extends GazapModuleBuilder<CdnModule> {
    @Autowired
    protected EngineSetup engineSetup;

    @Override
    protected boolean complete(HttpServletRequest request, HttpServletResponse response, Content content, CdnModule module) {
        module.setServer(engineSetup.getSiteUrl());
        module.setContextPath(engineSetup.getServletContext());
        module.setYear(DateUtil.utc().get(Calendar.YEAR));
        module.setLocale(response.getLocale().toString());
        return true;
    }
}
