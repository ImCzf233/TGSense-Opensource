package net.ccbluex.liquidbounce.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.apache.commons.io.IOUtils;

public class FileUtils {

    public static void unpackFile(File file, String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);

        IOUtils.copy((InputStream) Objects.requireNonNull(FileUtils.class.getClassLoader().getResourceAsStream(name)), fos);
        fos.close();
    }
}
