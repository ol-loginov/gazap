package gazap.site.services;

import gazap.common.util.Predicate;
import gazap.domain.entity.FileImage;
import gazap.site.model.FileStreamInfo;
import gazap.site.model.ServiceErrorException;

public interface FileService {
    FileImage loadImage(FileStreamInfo file, Predicate<String> readerSelector) throws ServiceErrorException;
}
