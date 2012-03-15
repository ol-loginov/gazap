package gazap.domain.dao.impl;

import gazap.domain.dao.GameDao;
import gazap.domain.entity.Game;
import gazap.domain.entity.UserGameRole;
import gazap.domain.entity.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class GameDaoImpl extends DaoImpl implements GameDao {
    @Override
    public Game findGameByTitle(String title) {
        return (Game) getSession().createQuery("from Game where title=:title")
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

    @Override
    public Game getGame(int id) {
        return (Game) getSession().get(Game.class, id);
    }

    @Override
    public Game findGameByAlias(String alias) {
        if (!StringUtils.hasText(alias)) {
            return null;
        }
        return (Game) getSession().createQuery("from Game where alias=:alias")
                .setParameter("alias", alias)
                .uniqueResult();
    }
}
