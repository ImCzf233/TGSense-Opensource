package me.fuckpcl.utils;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.StdCallLibrary;
import java.util.ArrayList;

public class WindowUtils {

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    public static String[] getWindowNames() {
        WindowUtils.User32 user32 = WindowUtils.User32.INSTANCE;
        ArrayList list = new ArrayList();

        user32.EnumWindows(callback<invokedynamic>(user32, list), (Pointer) null);
        return (String[]) list.toArray(new String[0]);
    }

    private static boolean lambda$getWindowNames$0(WindowUtils.User32 user32, ArrayList list, HWND hwnd, Pointer arg1) {
        byte[] windowText = new byte[512];

        user32.GetWindowTextA(hwnd, windowText, 512);
        String wText = Native.toString(windowText);

        list.add(wText);
        return true;
    }

    public interface User32 extends StdCallLibrary {

        WindowUtils.User32 INSTANCE = (WindowUtils.User32) Native.loadLibrary("user32", WindowUtils.User32.class);

        boolean EnumWindows(WNDENUMPROC wndenumproc, Pointer pointer);

        int GetWindowTextA(HWND hwnd, byte[] abyte, int i);
    }
}
