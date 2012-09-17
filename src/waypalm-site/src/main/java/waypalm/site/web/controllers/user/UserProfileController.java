package waypalm.site.web.controllers.user;

import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.UserProfile;
import waypalm.domain.entity.World;
import waypalm.site.exceptions.ObjectNotFoundException;
import waypalm.site.model.SimpleRegistry;
import waypalm.site.services.UserService;
import waypalm.site.web.controllers.BaseController;
import waypalm.site.web.views.user.UserProfilePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class UserProfileController extends BaseController {
    @Autowired
    protected UserService userService;
    @Autowired
    protected WorldRepository worldRepository;

    @RequestMapping("/u/{accountId}")
    public ModelAndView showProfilePage(Locale locale, @PathVariable String accountId) throws ObjectNotFoundException {
        UserProfile account = userService.findUserByAliasOrId(accountId);
        if (account == null) {
            throw new ObjectNotFoundException(UserProfile.class, accountId);
        }

        UserProfilePage page = new UserProfilePage();
        page.setUser(viewer.userTitle(account));

        SimpleRegistry<Integer, World> worldRegistry = new SimpleRegistry<Integer, World>();
        for (World world : worldRegistry.values()) {
            page.getWorlds().add(viewer.worldTitle(world));
        }

        for (Surface surface : worldRepository.listSurfaceBelongsToUser(account)) {
            page.getSurfaces().add(viewer.surfaceTitle(surface));
        }

        return responseBuilder(locale).view("user/profile", page);
    }
}
