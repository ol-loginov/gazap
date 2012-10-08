package waypalm.site.web.extensions;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtender;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.entity.Profile;
import waypalm.site.services.UserAccess;
import waypalm.site.web.mvc.PrincipalProvider;

public abstract class Extender<T> implements ModelExtender {
    @Autowired
    protected UserAccess auth;
    @Autowired
    protected UserRepository userRepository;

    @SuppressWarnings("unchecked")
    @Override
    public final void extend(String extensionKey, WebRequest request, ModelMap model) {
        Object extensionInstance = model.get(extensionKey);
        Object extensionApplied = createExtension(request, (T) extensionInstance, model);
        if (extensionApplied != null) {
            model.put(extensionKey, extensionApplied);
        } else {
            model.remove(extensionKey);
        }
    }

    public T createExtension(WebRequest request, T extension, ModelMap model) {
        return populate(request, extension, model);
    }

    protected abstract T populate(WebRequest request, T extension, ModelMap model);

    protected Profile getLoggedUser() {
        return PrincipalProvider.getLoggedUser(userRepository);
    }

    protected <T> T instantiateIfNull(T value, Class<T> valueClass) {
        if (value == null) {
            value = BeanUtils.instantiate(valueClass);
        }
        return value;
    }
}
