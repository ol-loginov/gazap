package waypalm.domain.dao.impl;

import waypalm.domain.dao.WorldRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
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
    public List<Surface> listSurfaceBelongsToUser(UserProfile user) {
        return getSession().createQuery("select distinct r.surface from SurfaceActor r where r.user=:user")
                .setEntity("user", user)
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
    public List<Contribution> listContributionsToShow(Surface surface, UserProfile viewer, Date after, int limit) {
        return getSession().createQuery("from Contribution c where c.surface=:surface and c.author=:user and c.decision=:decision and c.createdAt>:after order by c.createdAt")
                .setEntity("surface", surface)
                .setEntity("user", viewer)
                .setParameter("decision", ContributionDecision.NONE)
                .setParameter("after", after)
                .setMaxResults(limit)
                .list();
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
    public WorldActor getWorldActor(World world, UserProfile user) {
        return (WorldActor) getSession().get(WorldActor.class, new WorldActor.PK(user, world));
    }

    @Override
    public SurfaceActor getSurfaceActor(Surface surface, UserProfile user) {
        return (SurfaceActor) getSession().get(SurfaceActor.class, new SurfaceActor.PK(user, surface));
    }
}
