package gazap.site.services;

import gazap.domain.entity.ContributionTile;
import gazap.domain.entity.Map;
import gazap.site.model.FileStreamInfo;
import gazap.site.model.ServiceErrorException;

public interface ContributionService {
    ContributionTile addMapTile(Map map, FileStreamInfo file) throws ServiceErrorException;
}
