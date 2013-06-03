package waypalm.common.web.extensions;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

public interface ModelExtender {
    void extend(String extensionName, WebRequest request, ModelMap model);
}
