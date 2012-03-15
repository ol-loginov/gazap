package gazap.domain.dao;

import gazap.domain.entity.Game;
import gazap.domain.entity.UserGameRole;
import gazap.domain.entity.UserProfile;

import java.util.List;

public interface GameDao extends Dao {
    Game findGameByTitle(String title);

    List<UserGameRole> listGameRoleByUser(UserProfile user);

    Game getGame(int id);

    Game findGameByAlias(String alias);
}
