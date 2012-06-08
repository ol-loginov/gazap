package gazap.site.services;

import gazap.domain.entity.Map;
import gazap.site.model.ServiceErrorException;
import gazap.site.model.TileImage;

import javax.imageio.ImageReader;
import java.io.IOException;
import java.net.URL;

public interface FileService {
    String storeTile(Map map, TileImage tileImage, ImageValidator imageSelector) throws ServiceErrorException;

    void deleteTile(Map map, String file);

    URL getTileURL(Map map, String fileName);

    public static interface ImageValidator {
        boolean test(String fileFormat, ImageReader reader) throws ServiceErrorException, IOException;
    }
}
