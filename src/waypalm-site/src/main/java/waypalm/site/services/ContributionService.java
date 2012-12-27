package waypalm.site.services;

import org.springframework.transaction.annotation.Transactional;
import waypalm.domain.entity.ContributionTile;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.Surface;
import waypalm.site.exceptions.ObjectNotFoundException;
import waypalm.site.model.TileImage;

import java.io.IOException;

public interface ContributionService {
    @Transactional
    ContributionTile addMapTile(Profile author, Surface surface, TileImage file) throws IOException;

    @Transactional
    void reject(Profile visitor, Surface surface, int contributionId) throws ObjectNotFoundException;
}
