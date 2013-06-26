package waypalm.site.services

import waypalm.domain.entity.Surface
import waypalm.site.model.TileImage
import java.net.URL
import javax.imageio.ImageReader
import javax.inject.Named
import javax.inject.Inject
import org.springframework.beans.factory.annotation.Value
import java.io.File
import javax.annotation.PostConstruct
import org.springframework.beans.factory.BeanInitializationException
import waypalm.site.services.FileService.ImageValidator
import java.io.InputStream
import java.io.FileInputStream
import javax.imageio.ImageIO
import java.util.Locale
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import org.apache.commons.io.IOUtils as CommonIOUtils
import waypalm.common.util.IOUtils as WaypalmIOUtils

public trait FileService {
    fun storeTile(surface: Surface, tileImage: TileImage, imageSelector: ImageValidator): String?

    fun deleteTile(surface: Surface, file: String)

    fun getTileURL(surface: Surface, fileName: String): URL?

    trait  ImageValidator {
        fun test(fileFormat: String, reader: ImageReader): Boolean
    }
}

Named
public class FileServiceImpl [Inject](
        Value("\${FileServiceImpl.tilesFolder}")
        val tilesFolder: String
)
: FileService
{
    val tilesFolderFile: File = File(tilesFolder)

    PostConstruct
            fun validate() {
        if (!tilesFolderFile.exists()) {
            if (!tilesFolderFile.mkdirs()) {
                throw BeanInitializationException("cannot create folder " + tilesFolderFile.getAbsolutePath());
            }
        }
        if (!tilesFolderFile.isDirectory()) {
            throw BeanInitializationException("target folder " + tilesFolderFile.getAbsolutePath() + " is not a directory");
        }
    }


    public override fun storeTile(surface: Surface, tileImage: TileImage, imageSelector: ImageValidator): String? {
        var fileName = "c${tileImage.getTileScale()}s${tileImage.getTileSize()}x${tileImage.getTileX()}y${tileImage.getTileY()}-${System.currentTimeMillis()}"
        storeFile(getMapFolder(surface), fileName, tileImage.getFileInputStream()!!)

        val tempFile = getTileFile(surface, fileName)
        try {
            val fileExtension = validateTileImage(tempFile, imageSelector)
            if (fileExtension == null) return null

            var iStream: InputStream = FileInputStream(tempFile)
            try {
                fileName = fileName + "." + fileExtension;
                storeFile(getMapFolder(surface), fileName, iStream);
            } finally {
                CommonIOUtils.closeQuietly(iStream);
            }
        } finally {
            WaypalmIOUtils.deleteQuietly(tempFile);
        }
        return fileName;
    }

    public override fun deleteTile(surface: Surface, file: String) {
        val target = getTileFile(surface, file);
        WaypalmIOUtils.deleteQuietly(target);
    }

    private fun validateTileImage(file: File, imageSelector: ImageValidator): String? {
        val iStream = FileInputStream(file)
        try {
            val iis = ImageIO.createImageInputStream(iStream)!!

            var fileFormat: String
            val readerIterator = ImageIO.getImageReaders(iis);
            while (readerIterator.hasNext()) {
                val reader = readerIterator.next()
                reader.setInput(iis);

                fileFormat = reader.getFormatName()!!.toLowerCase(Locale.ENGLISH);
                if (imageSelector.test(fileFormat, reader)) {
                    return fileFormat
                }
            }

            return null
        } finally {
            CommonIOUtils.closeQuietly(iStream);
        }
    }

    private fun storeFile(folder: String, fileName: String, file: InputStream) {
        val targetFolder = File(tilesFolderFile, folder);
        if (!targetFolder.exists()) {
            if (!targetFolder.mkdirs()) {
                throw  IOException("unable to create storage folder");
            }
        }

        val output = File(targetFolder, fileName);
        var outputStream: FileOutputStream ? = null;
        try {
            outputStream = FileOutputStream(output);
            CommonIOUtils.copyLarge(file, outputStream);
        } finally {
            CommonIOUtils.closeQuietly(outputStream);
        }
    }

    private fun getMapFolder(surface: Surface): String {
        return "m" + surface.getId();
    }

    public override fun getTileURL(surface: Surface, fileName: String): URL? {
        try {
            return getTileFile(surface, fileName).toURI().toURL();
        } catch (e: MalformedURLException) {
            return null;
        }
    }

    public fun getTileFile(surface: Surface, fileName: String): File {
        return File(File(tilesFolderFile, getMapFolder(surface)), fileName);
    }
}