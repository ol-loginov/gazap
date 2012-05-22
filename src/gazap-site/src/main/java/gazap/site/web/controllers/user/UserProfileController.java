package gazap.site.web.controllers.user;

import gazap.domain.dao.MapDao;
import gazap.domain.dao.WorldDao;
import gazap.domain.entity.Map;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserWorldRole;
import gazap.domain.entity.World;
import gazap.site.exceptions.UserProfileNotFound;
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
    protected WorldDao worldDao;
    @Autowired
    protected MapDao mapDao;

    @RequestMapping("/user/{accountId}")
    public ModelAndView showProfilePage(Locale locale, @PathVariable String accountId) throws UserProfileNotFound {
        UserProfile account = userService.findUserByAliasOrId(accountId);
        if (account == null) {
            throw new UserProfileNotFound();
        }

        UserProfilePage page = new UserProfilePage();
        page.setUser(viewer.userTitle(account));

        SimpleRegistry<Integer, World> worldRegistry = new SimpleRegistry<Integer, World>();
        for (UserWorldRole role : worldDao.listWorldRoleByUser(account)) {
            worldRegistry.add(role.getWorld().getId(), role.getWorld());
            page.getWorldRolesList(role.getWorld().getId()).add(role.getRole());
        }
        for (World world : worldRegistry.values()) {
            page.getWorlds().add(viewer.worldTitle(world));
        }

        for (Map map : mapDao.listMapBelongsToUser(account)) {
            page.getMaps().add(viewer.mapTitle(map));
        }

        return responseBuilder(locale).view("user/profile", page);
    }
}
