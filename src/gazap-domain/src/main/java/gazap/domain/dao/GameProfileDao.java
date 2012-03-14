package gazap.domain.dao;

import gazap.domain.entity.GameProfile;

public interface GameProfileDao extends Dao {
    GameProfile findGameByTitle(String title);
}
