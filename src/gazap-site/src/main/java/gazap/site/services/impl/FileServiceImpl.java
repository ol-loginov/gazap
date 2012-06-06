package gazap.site.services.impl;

import gazap.common.util.HashUtil;
import gazap.domain.dao.FileDao;
import gazap.domain.entity.FileImage;
import gazap.site.model.FileStreamInfo;
import gazap.site.model.ServiceError;
import gazap.site.model.ServiceErrorException;
import gazap.site.services.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileOutputStream;
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
        folderFile = new File(folder);
        if (!folderFile.exists()) {
            if (!folderFile.mkdirs()) {
                throw new BeanInitializationException("cannot create folder " + folderFile.getAbsolutePath());
            }
        }
        if (!folderFile.isDirectory()) {
            throw new BeanInitializationException("target folder " + folderFile.getAbsolutePath() + " is not a directory");
        }
    }

    @Override
    public FileImage createImage(FileStreamInfo file, ImageValidator readerSelector) throws ServiceErrorException {
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
            reader.setInput(iis);
            try {
                fileFormat = reader.getFormatName().toLowerCase(Locale.ENGLISH);
                if (readerSelector == null || readerSelector.test(fileFormat, reader)) {
                    fileReader = reader;
                }
            } catch (IOException e) {
                throw new ServiceErrorException(ServiceError.INVALID_FILE_FORMAT, e);
            }
        }

        if (fileReader == null) {
            return null;
        }

        FileImage image = new FileImage();
        try {
            image.setWidth(fileReader.getWidth(0));
            image.setHeight(fileReader.getHeight(0));
        } catch (IOException e) {
            throw new ServiceErrorException(ServiceError.INVALID_FILE_FORMAT);
        }

        try {
            storeFile(image, file, fileFormat);
        } catch (IOException e) {
            throw new ServiceErrorException(ServiceError.INTERNAL_ERROR, e);
        }

        fileDao.create(image);
        return image;
    }

    private void storeFile(FileImage record, FileStreamInfo file, String fileFormat) throws IOException {
        Date now = new Date();
        String level1 = DATE_FORMAT.format(now);
        String level2 = TIME_FORMAT.format(now);
        File targetFolder = new File(new File(folderFile, level1), level2);
        if (!targetFolder.exists()) {
            if (!targetFolder.mkdirs()) {
                throw new IOException("unable to create storage folder");
            }
        }

        File output = new File(targetFolder, HashUtil.md5(file.getName() + ":" + fileFormat + ":" + System.nanoTime()) + "." + fileFormat);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(output);
            IOUtils.copyLarge(file.getInputStream(), outputStream);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }

        record.setServer("");
        record.setPath(level1 + "/" + level2);
        record.setName(output.getName());
    }
}
