package waypalm.site.web.extensions;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtender;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.entity.UserProfile;
import waypalm.site.services.UserAccess;
import waypalm.site.web.mvc.PrincipalProvider;

public abstract class Extender<T> implements ModelExtender {
    @Autowired
    protected UserAccess auth;
    @Autowired
    protected UserRepository userRepository;

    private String extensionKey;
    private String contentKey;

    public String getExtensionKey() {
        return extensionKey;
    }

    public void setExtensionKey(String extensionKey) {
        this.extensionKey = extensionKey;
    }

    public String getContentKey() {
        return contentKey;
    }

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
    }

    @Override
    public final void extend(WebRequest request, ModelMap model) {
        String extensionKey = getExtensionKey(), contentKey = getContentKey();

        Object extensionContent = contentKey != null ? model.get(contentKey) : null;
        Object extensionInstance = model.get(extensionKey);
        Object extensionApplied = createExtension(request, extensionInstance, extensionContent);
        if (extensionApplied != null) {
            model.put(extensionKey, extensionApplied);
        } else {
            model.remove(extensionKey);
        }
    }

    public T createExtension(WebRequest request, Object extension, Object content) {
        return populate(request, (T) extension, content);
    }

    protected abstract T populate(WebRequest request, T extension, Object content);

    protected UserProfile getLoggedUser() {
        return PrincipalProvider.getLoggedUser(userRepository);
    }

    protected <T> T instantiateIfNull(T value, Class<T> valueClass) {
        if (value == null) {
            value = BeanUtils.instantiate(valueClass);
        }
        return value;
    }
}
