package gazap.domain.dao.impl;

import gazap.domain.dao.WorldDao;
import gazap.domain.entity.World;
import gazap.domain.entity.UserWorldRole;
import gazap.domain.entity.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class WorldDaoImpl extends DaoImpl implements WorldDao {
    @Override
    public World findWorldByTitle(String title) {
        return (World) getSession().createQuery("from World where title=:title")
                .setParameter("title", title)
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserWorldRole> listWorldRoleByUser(UserProfile user) {
        return getSession().createQuery("from UserWorldRole  where id.user=:user")
                .setEntity("user", user)
                .list();
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
}
