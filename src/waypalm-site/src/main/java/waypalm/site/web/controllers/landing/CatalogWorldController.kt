package waypalm.site.web.controllers.landing

import waypalm.site.web.controllers.BaseController
import org.springframework.stereotype.Controller
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.RequestMapping
import waypalm.domain.dao.WorldRepository
import javax.inject.Inject

Controller
public class CatalogWorldController [Inject](
        worldRepository: WorldRepository
): BaseController() {
    val worldRepository = worldRepository;

    RequestMapping(array("/world"))
    public fun index(): ModelAndView? {
        return view("worlds")
                .addObject("worldList", worldRepository.listWorld())
    }
}
