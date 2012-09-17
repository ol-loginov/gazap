package gazap.site.services;

import gazap.domain.entity.ContributionTile;
import gazap.domain.entity.Surface;
import gazap.domain.entity.UserProfile;
import gazap.site.model.ServiceErrorException;
import gazap.site.model.TileImage;
import org.springframework.transaction.annotation.Transactional;

public interface ContributionService {
    @Transactional
    ContributionTile addMapTile(UserProfile author, Surface surface, TileImage file) throws ServiceErrorException;

    @Transactional
    void reject(UserProfile visitor, Surface surface, int contributionId) throws ServiceErrorException;
}
