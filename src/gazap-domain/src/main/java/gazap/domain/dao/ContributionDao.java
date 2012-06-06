package gazap.domain.dao;

import gazap.domain.entity.*;

import java.util.Date;
import java.util.List;

public interface ContributionDao extends Dao {
    List<Contribution> findContributions(Map map, UserProfile author, ContributionDecision actualDecision, Date after);

    ContributionTile loadTile(int id);
}
