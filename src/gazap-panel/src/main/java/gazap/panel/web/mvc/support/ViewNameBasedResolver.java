package gazap.panel.web.mvc.support;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.request.NativeWebRequest;

import java.lang.reflect.Method;

public class ViewNameBasedResolver extends com.iserv2.commons.mvc.views.ViewNameBasedResolver {
    @Override
    protected String overrideViewName(Method handlerMethod, Class handlerType, Object returnValue, ExtendedModelMap implicitModel, NativeWebRequest webRequest, String view) {
        return "landscape/" + view;
    }
}
