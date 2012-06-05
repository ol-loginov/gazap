package gazap.site.services.impl;

import gazap.common.util.HashUtil;
import gazap.common.util.Predicate;
import gazap.domain.dao.FileDao;
import gazap.domain.entity.FileImage;
import gazap.site.model.FileStreamInfo;
import gazap.site.model.ServiceError;
import gazap.site.model.ServiceErrorException;
import gazap.site.services.FileService;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

@Service
public class FileServiceImpl implements FileService {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HHmm");

    @Autowired
    private FileDao fileDao;
    @Value("${FileServiceImpl.folder}")
    private String folder;
    private File folderFile;

    @PostConstruct
    public void createFolder() {
        File folderFile = new File(folder);
        if (!folderFile.mkdirs()) {
            throw new BeanInitializationException("cannot create folder " + folderFile.getAbsolutePath());
        }
    }

    @Override
    public FileImage loadImage(FileStreamInfo file, Predicate<String> readerSelector) throws ServiceErrorException {
        ImageInputStream iis;
        try {
            iis = ImageIO.createImageInputStream(file.getInputStream());
        } catch (IOException e) {
            throw new ServiceErrorException(ServiceError.INVALID_FILE_FORMAT, e);
        }

        String fileFormat = null;
        ImageReader fileReader = null;

        Iterator<ImageReader> readerIterator = ImageIO.getImageReaders(iis);
        while (fileReader == null && readerIterator.hasNext()) {
            ImageReader reader = readerIterator.next();
            try {
                fileFormat = reader.getFormatName().toLowerCase(Locale.ENGLISH);
            } catch (IOException e) {
                throw new ServiceErrorException(ServiceError.INVALID_FILE_FORMAT, e);
            }
            if (readerSelector == null || readerSelector.evaluate(fileFormat)) {
                fileReader = reader;
            }
        }

        if (fileReader == null || fileFormat == null) {
            throw new ServiceErrorException(ServiceError.INVALID_FILE_FORMAT);
        }

        FileImage image = storeFile(file, fileFormat);
        fileDao.create(image);
        return image;
    }

    private FileImage storeFile(FileStreamInfo file, String fileFormat) {
        Date now = new Date();
        String level1 = DATE_FORMAT.format(now);
        String level2 = TIME_FORMAT.format(now);
        File targetFolder = new File(new File(folderFile, level1), level2);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        HashUtil.md5(file.getName() + ":" + fileFormat + ":");
        return null;
    }
}
