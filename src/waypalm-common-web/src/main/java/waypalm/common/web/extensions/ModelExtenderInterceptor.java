package waypalm.common.web.extensions;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class ModelExtenderInterceptor implements WebRequestInterceptor {
    private Map<String, ModelExtender> extenders;
    private String basePackage;
    private String contentKey = "content";

    @Autowired
    AutowireCapableBeanFactory beanFactory;


    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
    }

    @PostConstruct
    public void pickExtenders() {
        if (basePackage == null) {
            return;
        }
        Map<String, ModelExtender> extenders = new HashMap<>();
        if (this.extenders != null) {
            extenders.putAll(extenders);
        }

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(ModelExtension.class));
        for (BeanDefinition bd : provider.findCandidateComponents(basePackage)) {
            Class<ModelExtender> extenderClass;
            try {
                extenderClass = (Class<ModelExtender>) Class.forName(bd.getBeanClassName());
            } catch (Exception e) {
                throw new BeanInitializationException("extender class is wrong or not available " + bd.getBeanClassName());
            }
            String extensionName = extenderClass.getAnnotation(ModelExtension.class).value();
            extenders.put(extensionName, beanFactory.createBean(extenderClass));
        }
        this.extenders = extenders;
    }

    @Override
    public void preHandle(WebRequest request) throws Exception {
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        if (model == null) {
            return;
        }
        if (hasExtenders()) {
            for (Map.Entry<String, ModelExtender> kv : extenders.entrySet()) {
                kv.getValue().extend(kv.getKey(), model.get(contentKey), request, model);
            }
        }
    }

    private boolean hasExtenders() {
        return extenders != null && !extenders.isEmpty();
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
    }
}
