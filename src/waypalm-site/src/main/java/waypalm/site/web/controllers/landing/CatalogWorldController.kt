package waypalm.site.web.controllers.landing

import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.RequestMapping
import waypalm.domain.dao.WorldRepository
import javax.inject.Inject
import waypalm.site.web.controllers.ControllerServices
import waypalm.site.web.controllers.ControllerBase
import javax.inject.Named

Named
public class CatalogWorldController [Inject](
        override var services: ControllerServices,
        var worldRepository: WorldRepository
): ControllerBase {
    RequestMapping(array("/world"))
    public fun index(): ModelAndView? {
        return view("worlds")
                .addObject("worldList", worldRepository.listWorld())
    }
}
