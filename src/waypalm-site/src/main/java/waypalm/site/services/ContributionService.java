package waypalm.site.services;

import waypalm.domain.entity.ContributionTile;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.UserProfile;
import waypalm.site.model.ServiceErrorException;
import waypalm.site.model.TileImage;
import org.springframework.transaction.annotation.Transactional;

public interface ContributionService {
    @Transactional
    ContributionTile addMapTile(UserProfile author, Surface surface, TileImage file) throws ServiceErrorException;

    @Transactional
    void reject(UserProfile visitor, Surface surface, int contributionId) throws ServiceErrorException;
}
