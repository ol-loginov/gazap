package gazap.site.services.impl;

import gazap.domain.entity.Map;
import gazap.site.model.ServiceError;
import gazap.site.model.ServiceErrorException;
import gazap.site.model.TileImage;
import gazap.site.services.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;

@Service
public class FileServiceImpl implements FileService {
    @Value("${FileServiceImpl.tilesFolder}")
    private String tilesFolder;
    private File tilesFolderFile;

    @PostConstruct
    public void createFolder() {
        tilesFolderFile = new File(tilesFolder);
        if (!tilesFolderFile.exists()) {
            if (!tilesFolderFile.mkdirs()) {
                throw new BeanInitializationException("cannot create folder " + tilesFolderFile.getAbsolutePath());
            }
        }
        if (!tilesFolderFile.isDirectory()) {
            throw new BeanInitializationException("target folder " + tilesFolderFile.getAbsolutePath() + " is not a directory");
        }
    }

    @Override
    public String storeTile(Map map, TileImage tileImage, ImageValidator imageSelector) throws ServiceErrorException {
        String fileName = String.format("c%ds%dx%dy%d-%d",
                tileImage.getTileScale(), tileImage.getTileSize(), tileImage.getTileX(), tileImage.getTileY(),
                System.currentTimeMillis());

        try {
            storeFile(getMapFolder(map), fileName, tileImage.getFileInputStream());
        } catch (IOException e) {
            throw new ServiceErrorException(ServiceError.INTERNAL_ERROR, e);
        }

        File tempFile = getTileFile(map, fileName);

        InputStream is = null;
        try {
            String fileExtension = validateTileImage(tempFile, imageSelector);
            if (fileExtension == null) {
                return null;
            }

            is = new FileInputStream(tempFile);
            fileName = fileName + "." + fileExtension;
            storeFile(getMapFolder(map), fileName, is);
        } catch (IOException e) {
            throw new ServiceErrorException(ServiceError.INTERNAL_ERROR, e);
        } finally {
            IOUtils.closeQuietly(is);
            if (tempFile != null) {
                tempFile.delete();
            }
        }

        return fileName;
    }

    @Override
    public void deleteTile(Map map, String file) {
        File target = getTileFile(map, file);
        if (target.exists()) {
            target.delete();
        }
    }

    private String validateTileImage(File file, ImageValidator imageSelector) throws ServiceErrorException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            ImageInputStream iis = ImageIO.createImageInputStream(is);

            String fileFormat = null;
            ImageReader fileReader = null;

            Iterator<ImageReader> readerIterator = ImageIO.getImageReaders(iis);
            while (fileReader == null && readerIterator.hasNext()) {
                ImageReader reader = readerIterator.next();
                reader.setInput(iis);

                fileFormat = reader.getFormatName().toLowerCase(Locale.ENGLISH);
                if (imageSelector == null || imageSelector.test(fileFormat, reader)) {
                    fileReader = reader;
                }
            }

            return fileReader == null ? null : fileFormat;
        } catch (IOException e) {
            throw new ServiceErrorException(ServiceError.INVALID_FILE_FORMAT, e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private void storeFile(String folder, String fileName, InputStream file) throws IOException {
        File targetFolder = new File(tilesFolderFile, folder);
        if (!targetFolder.exists()) {
            if (!targetFolder.mkdirs()) {
                throw new IOException("unable to create storage folder");
            }
        }

        File output = new File(targetFolder, fileName);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(output);
            IOUtils.copyLarge(file, outputStream);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    private static String getMapFolder(Map map) {
        return "m" + Integer.toString(map.getId());
    }

    @Override
    public URL getTileURL(Map map, String fileName) {
        try {
            return getTileFile(map, fileName).toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public File getTileFile(Map map, String fileName) {
        File targetFolder = new File(tilesFolderFile, getMapFolder(map));
        return new File(targetFolder, fileName);
    }
}
