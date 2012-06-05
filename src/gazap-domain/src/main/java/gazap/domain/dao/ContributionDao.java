package gazap.domain.dao;

import gazap.domain.entity.Contribution;
import gazap.domain.entity.ContributionDecision;
import gazap.domain.entity.Map;
import gazap.domain.entity.UserProfile;

import java.util.List;

public interface ContributionDao extends Dao {
    List<Contribution> findContributionsByAuthor(Map map, UserProfile author, ContributionDecision decision);
}
