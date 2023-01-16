package net.ccbluex.liquidbounce;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebUtils2 {

    public static String get(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            response.append("\n");
        }

        in.close();
        return response.toString();
    }

    public static String readContent(String stringURL) throws IOException {
        HttpURLConnection httpConnection = stringURL.toLowerCase().startsWith("https://") ? (HttpURLConnection) (new URL(stringURL)).openConnection() : (HttpURLConnection) (new URL(stringURL)).openConnection();

        httpConnection.setConnectTimeout(10000);
        httpConnection.setReadTimeout(10000);
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        HttpURLConnection.setFollowRedirects(true);
        httpConnection.setDoOutput(true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        bufferedReader.close();
        return stringBuilder.toString();
    }

    public interface User32 extends StdCallLibrary {

        WebUtils2.User32 INSTANCE = (WebUtils2.User32) Native.loadLibrary("user32", WebUtils2.User32.class);

        boolean EnumWindows(WebUtils2.User32.WNDENUMPROC webutils2_user32_wndenumproc, Pointer pointer);

        int GetWindowTextA(Pointer pointer, byte[] abyte, int i);

        public interface WNDENUMPROC extends StdCallCallback {

            boolean callback(Pointer pointer, Pointer pointer1);
        }
    }
}
