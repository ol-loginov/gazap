package gazap.domain.dao;

import gazap.domain.entity.*;

import java.util.Date;
import java.util.List;

public interface MapDao extends Dao {
    Map getMap(int id);

    Map findMapByAlias(String alias);

    List<Map> listMapBelongsToUser(UserProfile user);

    List<UserMapRole> listMapRoleByUser(UserProfile user);

    List<UserMapRole> listMapRoleByUser(UserProfile user, int map);

    GeometryPlainTile loadGeometryPlainTile(GeometryPlain geometry, int scale, int size, int x, int y);

    int countMapPendingApproves(UserProfile profile, Map map);

    List<Contribution> listContributionsToShow(Map map, UserProfile viewer, Date after);

    List<Contribution> listContributionsToApprove(Map map, UserProfile viewer, Date after);

    ContributionTile getContributionTile(int id);

    Contribution getContribution(int id);
}
