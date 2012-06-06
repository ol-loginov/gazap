package gazap.site.services;

import gazap.domain.entity.FileImage;
import gazap.site.model.FileStreamInfo;
import gazap.site.model.ServiceErrorException;

import javax.imageio.ImageReader;
import java.io.File;
import java.io.IOException;

public interface FileService {
    FileImage createImage(FileStreamInfo file, ImageValidator readerSelector) throws ServiceErrorException;

    File getImageFile(FileImage file);

    public static interface ImageValidator {
        boolean test(String fileFormat, ImageReader reader) throws ServiceErrorException, IOException;
    }
}
