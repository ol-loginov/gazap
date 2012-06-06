package gazap.site.services;

import gazap.domain.entity.ContributionTile;
import gazap.domain.entity.Map;
import gazap.domain.entity.UserProfile;
import gazap.site.model.FileStreamInfo;
import gazap.site.model.ServiceErrorException;

public interface ContributionService {
    ContributionTile addMapTile(UserProfile author, Map map, FileStreamInfo file, int scale, int x, int y, int size) throws ServiceErrorException;
}
