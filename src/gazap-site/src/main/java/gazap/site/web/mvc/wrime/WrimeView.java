package gazap.site.web.mvc.wrime;

import gazap.site.web.mvc.wrime.spring.UnknownScriptSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class WrimeView extends AbstractTemplateView {
    private WrimeEngine wrimeEngine;
    private ResourceLoader resourceLoader;

    public WrimeEngine getWrimeEngine() {
        return wrimeEngine;
    }

    public void setWrimeEngine(WrimeEngine wrimeEngine) {
        this.wrimeEngine = wrimeEngine;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelMap map = new ModelMap();
        map.addAllAttributes(model);
        map.put("request", request);
        map.put("response", response);

        PrintWriter writer = new PrintWriter(response.getOutputStream());
        Resource resource = resourceLoader.getResource(getUrl());
        wrimeEngine
                .newWriter(new UnknownScriptSource(resource), writer)
                .render(map);
    }
}
