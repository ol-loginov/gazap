package gazap.site.services;

import gazap.domain.entity.ContributionTile;
import gazap.domain.entity.Map;
import gazap.domain.entity.UserProfile;
import gazap.site.model.ServiceErrorException;
import gazap.site.model.TileImage;

public interface ContributionService {
    ContributionTile addMapTile(UserProfile author, Map map, TileImage file) throws ServiceErrorException;
}
