package gazap.domain.dao.impl;

import gazap.domain.dao.GameProfileDao;
import gazap.domain.entity.GameProfile;
import gazap.domain.entity.UserGameRole;
import gazap.domain.entity.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameProfileDaoImpl extends DaoImpl implements GameProfileDao {
    @Override
    public GameProfile findGameByTitle(String title) {
        return (GameProfile) getSession().createQuery("from GameProfile where title=:title")
                .setParameter("title", title)
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserGameRole> listGameRoleByUser(UserProfile user) {
        return getSession().createQuery("from UserGameRole  where id.user=:user")
                .setEntity("user", user)
                .list();
    }
}
