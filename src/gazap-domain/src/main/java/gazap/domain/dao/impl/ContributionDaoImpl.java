package gazap.domain.dao.impl;

import gazap.domain.dao.ContributionDao;
import gazap.domain.entity.Contribution;
import gazap.domain.entity.ContributionDecision;
import gazap.domain.entity.Map;
import gazap.domain.entity.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContributionDaoImpl extends DaoImpl implements ContributionDao {
    @Override
    public List<Contribution> findContributionsByAuthor(Map map, UserProfile author, ContributionDecision decision) {
        return getSession().createQuery("from Contribution c where c.map=:map and c.author=:author and c.decision=:decision")
                .setParameter("decision", decision)
                .setEntity("map", map)
                .setEntity("author", author)
                .list();
    }
}
