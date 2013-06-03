package waypalm.common.web.extensions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ModelExtenderInterceptor implements WebRequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ModelExtenderInterceptor.class);

    private final Map<String, ModelExtender> extenders = new HashMap<>();
    private String basePackage;

    @Inject
    AutowireCapableBeanFactory beanFactory;

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void pickExtenders() {
        if (basePackage == null) {
            logger.error("no base package set, skip extending");
            return;
        }

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(ModelExtension.class));
        for (BeanDefinition bd : provider.findCandidateComponents(basePackage)) {
            Class<ModelExtender> extenderClass;
            try {
                extenderClass = (Class<ModelExtender>) Class.forName(bd.getBeanClassName());
            } catch (Exception e) {
                logger.error("extender class is wrong or not available " + bd.getBeanClassName());
                continue;
            }

            ModelExtension modelExtension = extenderClass.getAnnotation(ModelExtension.class);
            if (modelExtension == null) {
                logger.error("extender should have @ModelExtension annotation (" + bd.getBeanClassName() + ")");
                continue;
            }

            extenders.put(modelExtension.value(), beanFactory.createBean(extenderClass));
        }
    }

    @Override
    public void preHandle(WebRequest request) throws Exception {
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        if (model == null || extenders.isEmpty()) {
            return;
        }

        for (Map.Entry<String, ModelExtender> kv : extenders.entrySet()) {
            kv.getValue().extend(kv.getKey(), request, model);
        }
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
    }
}
