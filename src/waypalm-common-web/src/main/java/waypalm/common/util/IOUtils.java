package waypalm.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class IOUtils extends org.apache.commons.io.IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static void deleteQuietly(File file) {
        if (file == null || !file.exists()) {
            return;
        }

        if (!file.delete()) {
            logger.warn("cannot remove file " + file.getAbsolutePath());
        }
    }
}
