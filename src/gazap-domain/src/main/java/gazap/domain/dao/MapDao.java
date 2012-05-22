package gazap.domain.dao;

import gazap.domain.entity.Map;
import gazap.domain.entity.UserMapRole;
import gazap.domain.entity.UserProfile;

import java.util.List;

public interface MapDao extends Dao {
    Map getMap(int id);

    Map findMapByAlias(String alias);

    List<Map> listMapBelongsToUser(UserProfile user);

    List<UserMapRole> listMapRoleByUser(UserProfile user);

    List<UserMapRole> listMapRoleByUser(UserProfile user, int map);
}
