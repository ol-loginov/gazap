package waypalm.common.web.extensions

import javax.inject.Inject

public class ModelExtenderInterceptor [Inject] (
        val beanFactory: AutowireCapableBeanFactory
)
: WebRequestInterceptor
{
    class object {
        val logger: Logger = LoggerFactory.getLogger(javaClass<ModelExtenderInterceptor>())
    }

    val extenders: Map<String, ModelExtender> = HashMap<String, ModelExtender>();
    var basePackage: String = "";

    [PostConstruct]
    public fun pickExtenders() {
        if (basePackage.isEmpty()) {
            logger.error("no base package set, skip extending");
            return;
        }

        val provider = ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(AnnotationTypeFilter(javaClass<ModelExtension>()));

        for (val bd : BeanDefinition in provider.findCandidateComponents(basePackage)) {
            var extenderClass: Class<ModelExtender> = null
            try {
                extenderClass = Class.forName(bd.getBeanClassName()) as Class<ModelExtender>;
            } catch (e: Exception) {
                logger.error("extender class is wrong or not available " + bd.getBeanClassName());
                continue;
            }
            val modelExtension = extenderClass.getAnnotation(javaClass<ModelExtension>());
            if (modelExtension == null) {
                logger.error("extender should have @ModelExtension annotation (" + bd.getBeanClassName() + ")");
                continue;
            }
            extenders.put(modelExtension.value(), beanFactory.createBean(extenderClass));
        }
    }

    public override fun preHandle(request: WebRequest) {
    }

    public override void postHandle(request:WebRequest, model:ModelMap) {
        if (model == null || extenders.isEmpty()) {
            return;
        }

        for (val kv :Map.Entry<String, ModelExtender> in extenders.entrySet()) {
            kv.getValue().extend(kv.getKey(), request, model);
        }
    }

    public override fun afterCompletion(request: WebRequest, ex: Exception) {
    }
}