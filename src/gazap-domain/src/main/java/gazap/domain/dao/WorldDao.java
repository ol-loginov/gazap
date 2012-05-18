package gazap.domain.dao;

import gazap.domain.entity.World;
import gazap.domain.entity.UserWorldRole;
import gazap.domain.entity.UserProfile;

import java.util.List;

public interface WorldDao extends Dao {
    World findWorldByTitle(String title);

    List<UserWorldRole> listWorldRoleByUser(UserProfile user);

    World getWorld(int id);

    World findWorldByAlias(String alias);
}
