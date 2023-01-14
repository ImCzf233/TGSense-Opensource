package net.ccbluex.liquidbounce;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HWIDUtils {

    public static String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder s = new StringBuilder();
        String main = System.getenv("PROCESS_IDENTIFIER") + System.getenv("COMPUTERNAME");
        byte[] bytes = main.getBytes("UTF-8");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] md5 = messageDigest.digest(bytes);
        int i = 0;
        byte[] abyte = md5;
        int i = md5.length;

        for (int j = 0; j < i; ++j) {
            byte b = abyte[j];

            s.append(Integer.toHexString(b & 255 | 768), 0, 3);
            if (i != md5.length - 1) {
                s.append("-");
            }

            ++i;
        }

        return s.toString();
    }
}
