package me.Skid.utils;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;
import java.util.HashMap;
import java.util.Map;

public class QQUtils {

    public static String QQNumber;
    private static final String QQ_WINDOW_TEXT_PRE = "qqexchangewnd_shortcut_prefix_";
    private static final QQUtils.User32 user32 = QQUtils.User32.INSTANCE;

    public static String getSubString(String text, String left, String right) {
        String result = "";
        int zLen;

        if (left != null && !left.isEmpty()) {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        } else {
            zLen = 0;
        }

        int yLen = text.indexOf(right, zLen);

        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }

        result = text.substring(zLen, yLen);
        return result;
    }

    public static Map getLoginQQList() {
        final HashMap map = new HashMap(5);

        QQUtils.user32.EnumWindows(new QQUtils.User32.WNDENUMPROC() {
            public boolean callback(Pointer hWnd, Pointer userData) {
                byte[] windowText = new byte[512];

                QQUtils.user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText);

                if (QQUtils._filterQQInfo(wText)) {
                    map.put(hWnd.toString(), wText.substring(wText.indexOf("qqexchangewnd_shortcut_prefix_") + "qqexchangewnd_shortcut_prefix_".length()));
                }

                QQUtils.QQNumber = QQUtils.getSubString(String.valueOf(map), "=", "}");
                return true;
            }
        }, (Pointer) null);
        return map;
    }

    private static boolean _filterQQInfo(String windowText) {
        return windowText.startsWith("qqexchangewnd_shortcut_prefix_");
    }

    public static void getQQ() {
        getLoginQQList();
    }

    public interface User32 extends StdCallLibrary {

        QQUtils.User32 INSTANCE = (QQUtils.User32) Native.loadLibrary("user32", QQUtils.User32.class);

        boolean EnumWindows(QQUtils.User32.WNDENUMPROC qqutils_user32_wndenumproc, Pointer pointer);

        int GetWindowTextA(Pointer pointer, byte[] abyte, int i);

        public interface WNDENUMPROC extends StdCallCallback {

            boolean callback(Pointer pointer, Pointer pointer1);
        }
    }
}
