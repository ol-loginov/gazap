package waypalm.site.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.*;
import waypalm.site.model.ServiceError;
import waypalm.site.model.ServiceErrorException;
import waypalm.site.model.TileImage;
import waypalm.site.services.ContributionService;
import waypalm.site.services.FileService;

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
    private WorldRepository worldRepository;

    @Override
    public ContributionTile addMapTile(UserProfile author, Surface surface, final TileImage file) throws ServiceErrorException {
        String fileName = fileService.storeTile(surface, file, new FileService.ImageValidator() {
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
        tile.setSurface(surface);
        tile.setAction(ContributionTileAction.ADD);
        tile.setFile(fileName);
        tile.setX(file.getTileX());
        tile.setY(file.getTileY());
        tile.setScale(file.getTileScale());
        tile.setSize(file.getTileSize());
        worldRepository.create(tile);
        return tile;
    }

    @Override
    public void reject(UserProfile visitor, Surface surface, int contributionId) throws ServiceErrorException {
        Contribution contribution = worldRepository.getContribution(contributionId);
        if (contribution == null) {
            throw new ServiceErrorException(ServiceError.INVALID_PARAM);
        }

        if (ContributionTile.CLASS.equals(contribution.getContributionClass())) {
            ContributionTile tile = (ContributionTile) contribution;
            fileService.deleteTile(surface, tile.getFile());
            worldRepository.delete(tile);
        } else {
            throw new ServiceErrorException(ServiceError.INVALID_PARAM);
        }
    }
}
