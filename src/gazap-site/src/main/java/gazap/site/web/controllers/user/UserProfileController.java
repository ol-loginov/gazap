package gazap.site.web.controllers.user;

import gazap.domain.dao.WorldRepository;
import gazap.domain.entity.Surface;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.World;
import gazap.site.exceptions.ObjectNotFoundException;
import gazap.site.model.SimpleRegistry;
import gazap.site.services.UserService;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.user.UserProfilePage;
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
