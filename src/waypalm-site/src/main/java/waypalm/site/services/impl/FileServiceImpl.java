package waypalm.site.services.impl;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import waypalm.common.util.IOUtils;
import waypalm.domain.entity.Surface;
import waypalm.site.model.TileImage;
import waypalm.site.services.FileService;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.inject.Named;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;

import static org.apache.commons.io.IOUtils.copyLarge;

@Named
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
    public String storeTile(Surface surface, TileImage tileImage, ImageValidator imageSelector) throws IOException {
        String fileName = String.format("c%ds%dx%dy%d-%d",
                tileImage.getTileScale(), tileImage.getTileSize(), tileImage.getTileX(), tileImage.getTileY(),
                System.currentTimeMillis());

        storeFile(getMapFolder(surface), fileName, tileImage.getFileInputStream());

        File tempFile = getTileFile(surface, fileName);

        try {
            String fileExtension = validateTileImage(tempFile, imageSelector);
            if (fileExtension == null) {
                return null;
            }

            try (InputStream is = new FileInputStream(tempFile)) {
                fileName = fileName + "." + fileExtension;
                storeFile(getMapFolder(surface), fileName, is);
            }
        } finally {
            IOUtils.deleteQuietly(tempFile);
        }

        return fileName;
    }

    @Override
    public void deleteTile(Surface surface, String file) {
        File target = getTileFile(surface, file);
        IOUtils.deleteQuietly(target);
    }

    private String validateTileImage(File file, ImageValidator imageSelector) throws IOException {
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
        try (FileOutputStream outputStream = new FileOutputStream(output)) {
            copyLarge(file, outputStream);
        }
    }

    private static String getMapFolder(Surface surface) {
        return "m" + Integer.toString(surface.getId());
    }

    @Override
    public URL getTileURL(Surface surface, String fileName) {
        try {
            return getTileFile(surface, fileName).toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public File getTileFile(Surface surface, String fileName) {
        File targetFolder = new File(tilesFolderFile, getMapFolder(surface));
        return new File(targetFolder, fileName);
    }
}
