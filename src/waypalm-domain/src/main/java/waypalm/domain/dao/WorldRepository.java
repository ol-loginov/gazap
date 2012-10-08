package waypalm.domain.dao;

import waypalm.domain.entity.*;

import java.util.Date;
import java.util.List;

public interface WorldRepository extends Dao {
    World findWorldByTitle(String title);

    World getWorld(int id);

    World findWorldByAlias(String alias);

    Surface getSurface(int id);

    Surface findSurfaceByAlias(World world, String alias);

    List<Surface> listSurfaceBelongsToUser(UserProfile user);

    GeometryPlainTile loadGeometryPlainTile(GeometryPlain geometry, int scale, int x, int y);

    List<Contribution> listContributionsToShow(Surface surface, UserProfile viewer, Date after, int limit);

    ContributionTile getContributionTile(int id);

    Contribution getContribution(int id);

    WorldActor getWorldActor(World world, UserProfile user);

    SurfaceActor getSurfaceActor(Surface surface, UserProfile user);

    List<WorldActor> listWorldActor(UserProfile user);

    int countWorld();

    int countAvatar();
}
