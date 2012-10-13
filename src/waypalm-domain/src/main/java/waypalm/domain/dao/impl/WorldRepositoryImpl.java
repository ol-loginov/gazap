package waypalm.domain.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.*;

import java.util.Date;
import java.util.List;

@Repository
public class WorldRepositoryImpl extends DaoImpl implements WorldRepository {
    @Override
    public World findWorldByTitle(String title) {
        return (World) getSession().createQuery("from World where title=:title")
                .setParameter("title", title)
                .uniqueResult();
    }

    @Override
    public World getWorld(int id) {
        return (World) getSession().get(World.class, id);
    }

    @Override
    public WorldPublishing getWorldPublishing(World world) {
        return (WorldPublishing) getSession().get(WorldPublishing.class, new WorldPublishing.ID(world.getId()));
    }

    @Override
    public World findWorldByAlias(String alias) {
        if (!StringUtils.hasText(alias)) {
            return null;
        }
        return (World) getSession().createQuery("from World where alias=:alias")
                .setParameter("alias", alias)
                .uniqueResult();
    }

    @Override
    public Surface getSurface(int id) {
        return (Surface) getSession().get(Surface.class, id);
    }

    @Override
    public Surface findSurfaceByAlias(World world, String alias) {
        if (!StringUtils.hasText(alias)) {
            return null;
        }
        return (Surface) getSession().createQuery("from Surface where world=:world and alias=:alias")
                .setParameter("alias", alias)
                .setEntity("world", world)
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Surface> listSurfaceBelongsToUser(Profile profile) {
        return getSession().createQuery("select distinct r.surface from ProfileSurfaceRel r where r.profile=:profile")
                .setEntity("profile", profile)
                .list();
    }

    @Override
    public GeometryPlainTile loadGeometryPlainTile(GeometryPlain geometry, int scale, int x, int y) {
        return (GeometryPlainTile) getSession().createQuery("from GeometryPlainTile t where geometry=:geometry and scale=:scale and x=:x and y=:y")
                .setEntity("geometry", geometry)
                .setParameter("scale", scale)
                .setParameter("x", x)
                .setParameter("y", y)
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Contribution> listContributionsToShow(Surface surface, Profile profile, Date after, int limit) {
        throw new IllegalStateException("revise");
    }

    @Override
    public ContributionTile getContributionTile(int id) {
        return (ContributionTile) getSession().get(ContributionTile.class, id);
    }

    @Override
    public Contribution getContribution(int id) {
        return (Contribution) getSession().get(Contribution.class, id);
    }

    @Override
    public ProfileWorldRel getWorldRelation(World world, Profile profile) {
        return (ProfileWorldRel) getSession().get(ProfileWorldRel.class, new ProfileWorldRel.ID(profile, world));
    }

    @Override
    public ProfileSurfaceRel getSurfaceRelation(Surface surface, Profile profile) {
        return (ProfileSurfaceRel) getSession().get(ProfileSurfaceRel.class, new ProfileSurfaceRel.ID(profile, surface));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProfileWorldRel> listWorldRelation(Profile profile) {
        return (List<ProfileWorldRel>) getSession().createQuery("from ProfileWorldRel where profile=:profile")
                .setEntity("profile", profile)
                .list();
    }

    @Override
    public int countWorld() {
        return toNumberInt(getSession().createQuery("select count(*) from World").uniqueResult());
    }

    @Override
    public int countAvatar() {
        return toNumberInt(getSession().createQuery("select count(*) from Avatar").uniqueResult());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<World> listWorld() {
        return (List<World>) getSession().createQuery("from World order by createdAt desc").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<World> listSurfaceBelongsToWorld(World world) {
        return (List<World>) getSession().createQuery("from Surface where world=:world")
                .setEntity("world", world)
                .list();
    }
}
