package gazap.common.web.extensions;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

public interface ModelExtender {
    void extend(WebRequest request, ModelMap model);
}
