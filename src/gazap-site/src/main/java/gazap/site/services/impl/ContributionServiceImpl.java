package gazap.site.services.impl;

import gazap.domain.dao.ContributionDao;
import gazap.domain.entity.*;
import gazap.site.model.ServiceError;
import gazap.site.model.ServiceErrorException;
import gazap.site.model.TileImage;
import gazap.site.services.ContributionService;
import gazap.site.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class ContributionServiceImpl implements ContributionService {
    private static final Set<String> ALLOWED_TILE_FORMATS = new HashSet<String>() {{
        add("jpeg");
        add("jpg");
        add("png");
    }};

    @Autowired
    private FileService fileService;
    @Autowired
    private ContributionDao contributionDao;

    @Override
    public ContributionTile addMapTile(UserProfile author, Map map, final TileImage file) throws ServiceErrorException {
        String fileName = fileService.storeTile(map, file, new FileService.ImageValidator() {
            @Override
            public boolean test(String fileFormat, ImageReader reader) throws ServiceErrorException, IOException {
                return reader.getHeight(0) == file.getTileSize() && reader.getWidth(0) == file.getTileSize()
                        && ALLOWED_TILE_FORMATS.contains(fileFormat.toLowerCase(Locale.ENGLISH));
            }
        });

        if (fileName == null) {
            throw new ServiceErrorException(ServiceError.INVALID_PARAM);
        }

        ContributionTile tile = new ContributionTile();
        tile.setAuthor(author);
        tile.setMap(map);
        tile.setAction(ContributionTileAction.ADD);
        tile.setFile(fileName);
        tile.setX(file.getTileX());
        tile.setY(file.getTileY());
        tile.setScale(file.getTileScale());
        tile.setSize(file.getTileSize());
        contributionDao.create(tile);
        return tile;
    }

    @Override
    public void reject(UserProfile visitor, Map map, int contributionId) throws ServiceErrorException {
        Contribution contribution = contributionDao.load(contributionId);
        if (contribution == null) {
            throw new ServiceErrorException(ServiceError.INVALID_PARAM);
        }

        if (ContributionTile.CLASS.equals(contribution.getContributionClass())) {
            ContributionTile tile = (ContributionTile) contribution;
            fileService.deleteTile(map, tile.getFile());
            contributionDao.delete(tile);
        } else {
            throw new ServiceErrorException(ServiceError.INVALID_PARAM);
        }
    }
}
