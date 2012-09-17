package waypalm.site.services;

import waypalm.domain.entity.Surface;
import waypalm.site.model.ServiceErrorException;
import waypalm.site.model.TileImage;

import javax.imageio.ImageReader;
import java.io.IOException;
import java.net.URL;

public interface FileService {
    String storeTile(Surface surface, TileImage tileImage, ImageValidator imageSelector) throws ServiceErrorException;

    void deleteTile(Surface surface, String file);

    URL getTileURL(Surface surface, String fileName);

    public static interface ImageValidator {
        boolean test(String fileFormat, ImageReader reader) throws ServiceErrorException, IOException;
    }
}
