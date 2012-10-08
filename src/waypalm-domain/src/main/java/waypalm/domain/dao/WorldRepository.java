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

    List<Surface> listSurfaceBelongsToUser(Profile user);

    GeometryPlainTile loadGeometryPlainTile(GeometryPlain geometry, int scale, int x, int y);

    List<Contribution> listContributionsToShow(Surface surface, Profile viewer, Date after, int limit);

    ContributionTile getContributionTile(int id);

    Contribution getContribution(int id);

    ProfileWorldRel getWorldRelation(World world, Profile profile);

    ProfileSurfaceRel getSurfaceRelation(Surface surface, Profile profile);

    List<ProfileWorldRel> listWorldRelation(Profile profile);

    int countWorld();

    int countAvatar();

    List<World> listWorld();

    WorldPublishing getWorldPublishing(World world);
}
