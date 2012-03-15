package gazap.domain.dao;

import gazap.domain.entity.GameProfile;
import gazap.domain.entity.UserGameRole;
import gazap.domain.entity.UserProfile;

import java.util.List;

public interface GameProfileDao extends Dao {
    GameProfile findGameByTitle(String title);

    List<UserGameRole> listGameRoleByUser(UserProfile user);
}
