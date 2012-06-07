package gazap.domain.dao.impl;

import gazap.domain.dao.ContributionDao;
import gazap.domain.entity.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ContributionDaoImpl extends DaoImpl implements ContributionDao {
    @SuppressWarnings("unchecked")
    @Override
    public List<Contribution> findContributions(Map map, UserProfile author, ContributionDecision actualDecision, Date after) {
        return getSession().createQuery("from Contribution c where c.map=:map and c.author=:author and c.decision=:decision and c.createdAt>:after order by c.createdAt")
                .setEntity("map", map)
                .setEntity("author", author)
                .setParameter("decision", actualDecision)
                .setParameter("after", after)
                .list();
    }

    @Override
    public ContributionTile loadTile(int id) {
        return (ContributionTile) getSession().get(ContributionTile.class, id);
    }
}
