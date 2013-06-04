package waypalm.site.services

import javax.inject.Named
import javax.inject.Inject
import org.springframework.beans.factory.annotation.Value

Named
public class EngineSetup [Inject](
        Value("\${engine.url}")
        val siteUrl: String,
        Value("\${engine.context}")
        val servletContext: String,
        Value("\${engine.debug}")
        val debugMode: Boolean
)
{
    val startTime = System.currentTimeMillis()
}