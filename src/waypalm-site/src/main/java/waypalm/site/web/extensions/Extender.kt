package waypalm.site.web.extensions

import waypalm.common.web.extensions.ModelExtender
import org.springframework.web.context.request.WebRequest
import org.springframework.ui.ModelMap
import org.springframework.beans.BeanUtils

public abstract class Extender<T> ()
: ModelExtender
{
    public override fun extend(extensionName: String, request: WebRequest, model: ModelMap) {
        val extensionInstance = model.get(extensionName);
        val extensionApplied = createExtension(request, extensionInstance as T, model);
        if (extensionApplied != null) {
            model.put(extensionName, extensionApplied);
        } else {
            model.remove(extensionName);
        }
    }

    public fun  createExtension(request: WebRequest, extension: T?, model: ModelMap): T? {
        return populate(request, extension, model);
    }

    protected abstract fun populate(request: WebRequest, extension: T?, model: ModelMap): T?;

    protected fun instantiateIfNull<C>(value: C?, valueClass: Class<C>): C {
        return if (value != null) value else BeanUtils.instantiate(valueClass)!!
    }
}