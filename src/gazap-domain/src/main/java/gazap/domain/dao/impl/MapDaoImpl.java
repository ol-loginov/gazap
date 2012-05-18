package gazap.domain.dao.impl;

import gazap.domain.dao.MapDao;
import gazap.domain.entity.World;
import gazap.domain.entity.Map;
import gazap.domain.entity.UserMapRole;
import gazap.domain.entity.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class MapDaoImpl extends DaoImpl implements MapDao {
    @Override
    public Map getMap(int id) {
        return (Map) getSession().get(World.class, id);
    }

    @Override
    public Map findMapByAlias(String alias) {
        if (!StringUtils.hasText(alias)) {
            return null;
        }
        return (Map) getSession().createQuery("from Map where alias=:alias")
                .setParameter("alias", alias)
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserMapRole> listMapRoleByUser(UserProfile user) {
        return getSession().createQuery("from UserMapRole where id.user=:user")
                .setEntity("user", user)
                .list();
    }
}
