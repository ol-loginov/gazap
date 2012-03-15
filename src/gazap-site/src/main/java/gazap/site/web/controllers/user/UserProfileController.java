package gazap.site.web.controllers.user;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.domain.dao.GameProfileDao;
import gazap.domain.entity.GameProfile;
import gazap.domain.entity.UserGameRole;
import gazap.domain.entity.UserProfile;
import gazap.site.exceptions.UserProfileNotFound;
import gazap.site.model.SimpleRegistry;
import gazap.site.model.viewer.GameRole;
import gazap.site.services.UserService;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.user.UserProfilePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserProfileController extends BaseController {
    @Autowired
    protected UserService userService;
    @Autowired
    protected GameProfileDao gameProfileDao;

    @RequestMapping("/user/{accountId}")
    @ViewName(response = UserProfilePage.class, name = "user/profile")
    public UserProfilePage showProfilePage(@PathVariable String accountId) throws UserProfileNotFound {
        UserProfile account = userService.findUserByAliasOrId(accountId);
        if (account == null) {
            throw new UserProfileNotFound();
        }

        UserProfilePage page = new UserProfilePage();
        page.setUser(viewer.userTitle(account));

        SimpleRegistry<Integer, GameProfile> gameRegistry = new SimpleRegistry<Integer, GameProfile>();
        for (UserGameRole role : gameProfileDao.listGameRoleByUser(account)) {
            gameRegistry.add(role.getGame().getId(), role.getGame());
            page.getGameRoles().add(new GameRole(account.getId(), role.getGame().getId(), role.getRole()));
        }
        for (GameProfile game : gameRegistry.values()) {
            page.getGames().add(viewer.gameTitle(game));
        }
        return page;
    }
}
