package gazap.site.services.impl;

import gazap.domain.dao.GameDao;
import gazap.domain.entity.Game;
import gazap.domain.entity.UserGameRole;
import gazap.domain.entity.UserGameRoles;
import gazap.domain.entity.UserProfile;
import gazap.site.services.GameService;
import gazap.site.web.controllers.game.GameCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    protected GameDao gameDao;

    @Override
    public Game createGame(UserProfile creator, GameCreateForm form) {
        Game game = new Game();
        game.setTitle(form.getTitle());
        gameDao.create(game);

        UserGameRole role = new UserGameRole(creator, game, UserGameRoles.CREATOR);
        gameDao.create(role);

        return game;
    }

    @Override
    public Game findGameByAliasOrId(String aliasOrId) {
        if (!StringUtils.hasText(aliasOrId)) {
            return null;
        }
        return Character.isDigit(aliasOrId.charAt(0))
                ? gameDao.getGame(Integer.parseInt(aliasOrId))
                : gameDao.findGameByAlias(aliasOrId);
    }
}
