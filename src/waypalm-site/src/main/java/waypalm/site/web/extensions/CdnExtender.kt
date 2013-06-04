package waypalm.site.web.extensions

import waypalm.common.web.extensions.ModelExtension
import javax.inject.Inject
import waypalm.site.services.EngineSetup
import org.springframework.web.context.request.WebRequest
import org.springframework.ui.ModelMap

ModelExtension("eCdn")
public class CdnExtender [Inject](
        var engineSetup: EngineSetup
)
: Extender<CdnExtender.Content>()
{
    protected override fun populate(request: WebRequest, extension: CdnExtender.Content?, model: ModelMap): CdnExtender.Content {
        var res = instantiateIfNull(extension, javaClass<Content>());
        res.debugMode = engineSetup.debugMode;
        res.server = engineSetup.siteUrl
        res.serverStart = engineSetup.startTime
        res.contextPath = engineSetup.servletContext
        res.locale = request.getLocale()!!.getLanguage();
        return res;
    }

    public class Content {
        var controlId: Int = 0
        var debugMode: Boolean = false
        var server: String = ""
        var serverStart: Long = 0
            get() = if(debugMode) System.currentTimeMillis() else $serverStart

        var contextPath: String = ""
        var locale: String = ""
        var appZone: String = ""

        fun getNextCid(): String {
            ++controlId
            return getCid()
        }

        fun getCid(): String {
            return "c${controlId}"
        }
    }
}