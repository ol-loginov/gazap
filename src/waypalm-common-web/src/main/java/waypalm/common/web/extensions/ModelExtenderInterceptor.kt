package waypalm.common.web.extensions

import javax.inject.Inject
import org.springframework.core.`type`.filter.AnnotationTypeFilter
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.web.context.request.WebRequestInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.HashMap
import javax.annotation.PostConstruct
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.web.context.request.WebRequest
import org.springframework.ui.ModelMap

public class ModelExtenderInterceptor [Inject] (
        val beanFactory: AutowireCapableBeanFactory
)
: WebRequestInterceptor
{
    class object {
        val logger: Logger = LoggerFactory.getLogger(javaClass<ModelExtenderInterceptor>())!!
    }

    val extenders = HashMap<String, ModelExtender>();
    var basePackage = "";

    [PostConstruct]
    public fun pickExtenders() {
        if (basePackage.isEmpty()) {
            logger.error("no base package set, skip extending");
            return;
        }

        val provider = ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(AnnotationTypeFilter(javaClass<ModelExtension>()));

        for (bd : BeanDefinition in provider.findCandidateComponents(basePackage)!!) {
            var extenderClass: Class<ModelExtender>? = Class.forName(bd.getBeanClassName()!!) as Class<ModelExtender>;
            val modelExtension = extenderClass!!.getAnnotation(javaClass<ModelExtension>());
            if (modelExtension == null) {
                logger.error("extender should have @ModelExtension annotation (" + bd.getBeanClassName() + ")");
                continue;
            }
            extenders.put(modelExtension.value()!!, beanFactory.createBean(extenderClass)!!);
        }
    }

    public override fun preHandle(request: WebRequest?) {
    }

    public override fun postHandle(request: WebRequest?, model: ModelMap?) {
        if (model == null || extenders.isEmpty()) return

        for (kv :Map.Entry<String, ModelExtender> in extenders.entrySet()) {
            kv.getValue().extend(kv.getKey(), request!!, model);
        }
    }

    public override fun afterCompletion(request: WebRequest?, ex: Exception?) {
    }
}