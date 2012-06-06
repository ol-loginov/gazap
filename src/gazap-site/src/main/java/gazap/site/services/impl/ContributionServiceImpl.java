package gazap.site.services.impl;

import gazap.domain.dao.ContributionDao;
import gazap.domain.entity.*;
import gazap.site.model.FileStreamInfo;
import gazap.site.model.ServiceError;
import gazap.site.model.ServiceErrorException;
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
    public ContributionTile addMapTile(UserProfile author, Map map, FileStreamInfo file, int scale, int x, int y, final int size) throws ServiceErrorException {
        FileImage fileImage = fileService.createImage(file, new FileService.ImageValidator() {
            @Override
            public boolean test(String fileFormat, ImageReader reader) throws ServiceErrorException, IOException {
                return reader.getHeight(0) == size && reader.getWidth(0) == size && ALLOWED_TILE_FORMATS.contains(fileFormat.toLowerCase(Locale.ENGLISH));
            }
        });

        if (fileImage == null) {
            throw new ServiceErrorException(ServiceError.INVALID_PARAM);
        }

        ContributionTile tile = new ContributionTile();
        tile.setAuthor(author);
        tile.setMap(map);
        tile.setAction(ContributionTileAction.ADD);
        tile.setFile(fileImage);
        tile.setX(x);
        tile.setY(y);
        tile.setScale(scale);
        tile.setSize(size);
        contributionDao.create(tile);
        return tile;
    }
}
