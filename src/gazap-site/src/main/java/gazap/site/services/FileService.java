package gazap.site.services;

import gazap.domain.entity.Surface;
import gazap.site.model.ServiceErrorException;
import gazap.site.model.TileImage;

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
