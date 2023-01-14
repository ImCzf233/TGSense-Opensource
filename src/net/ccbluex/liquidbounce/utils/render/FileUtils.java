package net.ccbluex.liquidbounce.utils.render;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.io.IOUtils;

public class FileUtils {

    public static String readFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream e = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(e));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader e = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = e.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static void unpackFile(File file, String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);

        IOUtils.copy(FileUtils.class.getClassLoader().getResourceAsStream(name), fos);
        fos.close();
    }
}
