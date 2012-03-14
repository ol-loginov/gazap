package gazap.site.services.impl;

import gazap.domain.dao.GameProfileDao;
import gazap.domain.entity.GameProfile;
import gazap.domain.entity.UserGameRole;
import gazap.domain.entity.UserGameRoles;
import gazap.domain.entity.UserProfile;
import gazap.site.services.GameService;
import gazap.site.web.controllers.game.GameCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    protected GameProfileDao gameProfileDao;

    @Override
    public GameProfile createGame(UserProfile creator, GameCreateForm form) {
        GameProfile game = new GameProfile();
        game.setCreator(creator);
        game.setTitle(form.getTitle());
        gameProfileDao.create(game);

        UserGameRole role = new UserGameRole(creator, game, UserGameRoles.ADMINISTRATOR);
        gameProfileDao.create(role);

        return game;
    }
}
