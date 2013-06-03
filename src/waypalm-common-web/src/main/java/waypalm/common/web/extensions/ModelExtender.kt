package waypalm.common.web.extensions

import org.springframework.ui.ModelMap
import org.springframework.web.context.request.WebRequest

public trait ModelExtender {
    fun extend(extensionName: String, request: WebRequest, model: ModelMap)
}