package gazap.site.web.controllers.user;

import gazap.domain.dao.GameDao;
import gazap.domain.dao.MapDao;
import gazap.domain.entity.*;
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
    protected GameDao gameDao;
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

        SimpleRegistry<Integer, Game> gameRegistry = new SimpleRegistry<Integer, Game>();
        for (UserGameRole role : gameDao.listGameRoleByUser(account)) {
            gameRegistry.add(role.getGame().getId(), role.getGame());
            page.getGameRolesList(role.getGame().getId()).add(role.getRole());
        }
        for (Game game : gameRegistry.values()) {
            page.getGames().add(viewer.gameTitle(game));
        }

        SimpleRegistry<Integer, Map> mapRegistry = new SimpleRegistry<Integer, Map>();
        for (UserMapRole role : mapDao.listMapRoleByUser(account)) {
            mapRegistry.add(role.getMap().getId(), role.getMap());
            page.getMapRolesList(role.getMap().getId()).add(role.getRole());
        }
        for (Map map : mapRegistry.values()) {
            page.getMaps().add(viewer.mapTitle(map));
        }

        return responseBuilder(locale).view("user/profile", page);
    }
}
