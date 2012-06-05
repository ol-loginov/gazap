package gazap.site.services.impl;

import gazap.domain.entity.ContributionTile;
import gazap.domain.entity.ContributionTileAction;
import gazap.domain.entity.FileImage;
import gazap.domain.entity.Map;
import gazap.site.model.FileStreamInfo;
import gazap.site.services.ContributionService;
import gazap.site.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContributionServiceImpl implements ContributionService {
    @Autowired
    private FileService fileService;

    @Override
    public ContributionTile addMapTile(Map map, FileStreamInfo file) {
        FileImage image = fileService.loadImage(file);

        ContributionTile tile = new ContributionTile();
        tile.setAction(ContributionTileAction.ADD);
        return tile;
    }
}
