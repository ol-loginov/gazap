package gazap.domain.dao.impl;

import gazap.domain.dao.GameProfileDao;
import gazap.domain.entity.GameProfile;
import org.springframework.stereotype.Repository;

@Repository
public class GameProfileDaoImpl extends DaoImpl implements GameProfileDao {
    @Override
    public GameProfile findGameByTitle(String title) {
        return (GameProfile) getSession().createQuery("from GameProfile where title=:title")
                .setParameter("title", title)
                .uniqueResult();
    }
}
