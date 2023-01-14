package me.font;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.aspw.nightx.visual.font.GameFontRenderer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Fonts {

    @FontDetails(
        fontName = "Minecraft Font"
    )
    public static final FontRenderer minecraftFont = Minecraft.getMinecraft().fontRenderer;
    private static final List CUSTOM_FONT_RENDERERS = new ArrayList();
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 35
    )
    public static GameFontRenderer font35;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 40
    )
    public static GameFontRenderer font40;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 72
    )
    public static GameFontRenderer font72;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 30
    )
    public static GameFontRenderer fontSmall;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 24
    )
    public static GameFontRenderer fontTiny;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 52
    )
    public static GameFontRenderer fontLarge;
    @FontDetails(
        fontName = "SFUI Regular",
        fontSize = 35
    )
    public static GameFontRenderer fontSFUI35;
    @FontDetails(
        fontName = "SFUI Regular",
        fontSize = 37
    )
    public static GameFontRenderer fontSFUI37;
    @FontDetails(
        fontName = "SFUI Regular",
        fontSize = 40
    )
    public static GameFontRenderer fontSFUI40;
    @FontDetails(
        fontName = "Jello Regular",
        fontSize = 40
    )
    public static GameFontRenderer jelloRegular40;
    @FontDetails(
        fontName = "Roboto Bold",
        fontSize = 180
    )
    public static GameFontRenderer fontBold180;
    @FontDetails(
        fontName = "Tahoma Bold",
        fontSize = 38
    )
    public static GameFontRenderer fontTahoma38;
    @FontDetails(
        fontName = "Tahoma Bold",
        fontSize = 35
    )
    public static GameFontRenderer fontTahoma;
    @FontDetails(
        fontName = "Tahoma Bold",
        fontSize = 30
    )
    public static GameFontRenderer fontTahoma30;
    public static TTFFontRenderer fontTahomaSmall;
    @FontDetails(
        fontName = "Bangers",
        fontSize = 45
    )
    public static GameFontRenderer fontBangers;

    public static void loadFonts() {
        long l = System.currentTimeMillis();

        ClientUtils.getLogger().info("Loading Fonts.");
        downloadFonts();
        Fonts.font35 = new GameFontRenderer(getFont("Roboto-Medium.ttf", 35));
        Fonts.font40 = new GameFontRenderer(getFont("Roboto-Medium.ttf", 40));
        Fonts.font72 = new GameFontRenderer(getFont("Roboto-Medium.ttf", 72));
        Fonts.fontSmall = new GameFontRenderer(getFont("Roboto-Medium.ttf", 30));
        Fonts.fontTiny = new GameFontRenderer(getFont("Roboto-Medium.ttf", 24));
        Fonts.fontLarge = new GameFontRenderer(getFont("Roboto-Medium.ttf", 60));
        Fonts.fontSFUI35 = new GameFontRenderer(getFont("sfui.ttf", 35));
        Fonts.fontSFUI37 = new GameFontRenderer(getFont("sfui.ttf", 37));
        Fonts.fontSFUI40 = new GameFontRenderer(getFont("sfui.ttf", 40));
        Fonts.jelloRegular40 = new GameFontRenderer(getFont("jelloregular.ttf", 40));
        Fonts.fontBold180 = new GameFontRenderer(getFont("Roboto-Bold.ttf", 180));
        Fonts.fontTahoma = new GameFontRenderer(getFont("TahomaBold.ttf", 35));
        Fonts.fontTahoma30 = new GameFontRenderer(getFont("TahomaBold.ttf", 30));
        Fonts.fontTahoma38 = new GameFontRenderer(getFont("TahomaBold.ttf", 38));
        Fonts.fontTahomaSmall = new TTFFontRenderer(getFont("Tahoma.ttf", 11));
        Fonts.fontBangers = new GameFontRenderer(getFont("Bangers-Regular.ttf", 45));

        try {
            Fonts.CUSTOM_FONT_RENDERERS.clear();
            File e = new File(LiquidBounce.fileManager.fontsDir, "fonts.json");

            if (e.exists()) {
                JsonElement printWriter = (new JsonParser()).parse(new BufferedReader(new FileReader(e)));

                if (printWriter instanceof JsonNull) {
                    return;
                }

                JsonArray jsonArray = (JsonArray) printWriter;
                Iterator iterator = jsonArray.iterator();

                while (iterator.hasNext()) {
                    JsonElement element = (JsonElement) iterator.next();

                    if (element instanceof JsonNull) {
                        return;
                    }

                    JsonObject fontObject = (JsonObject) element;

                    Fonts.CUSTOM_FONT_RENDERERS.add(new GameFontRenderer(getFont(fontObject.get("fontFile").getAsString(), fontObject.get("fontSize").getAsInt())));
                }
            } else {
                e.createNewFile();
                PrintWriter printWriter1 = new PrintWriter(new FileWriter(e));

                printWriter1.println((new GsonBuilder()).setPrettyPrinting().create().toJson(new JsonArray()));
                printWriter1.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        ClientUtils.getLogger().info("Loaded Fonts. (" + (System.currentTimeMillis() - l) + "ms)");
    }

    private static void downloadFonts() {
        try {
            File e = new File(LiquidBounce.fileManager.fontsDir, "roboto.zip");
            File sfuiFile = new File(LiquidBounce.fileManager.fontsDir, "sfui.ttf");
            File jelloFile = new File(LiquidBounce.fileManager.fontsDir, "jelloregular.ttf");
            File prodSansFile = new File(LiquidBounce.fileManager.fontsDir, "Roboto-Medium.ttf");
            File prodBoldFile = new File(LiquidBounce.fileManager.fontsDir, "Roboto-Bold.ttf");
            File tahomaFile = new File(LiquidBounce.fileManager.fontsDir, "TahomaBold.ttf");
            File tahomaReFile = new File(LiquidBounce.fileManager.fontsDir, "Tahoma.ttf");
            File bangersFile = new File(LiquidBounce.fileManager.fontsDir, "Bangers-Regular.ttf");

            if (!e.exists() || !sfuiFile.exists() || !jelloFile.exists() || !prodSansFile.exists() || !prodBoldFile.exists() || !tahomaFile.exists() || !tahomaReFile.exists() || !bangersFile.exists()) {
                ClientUtils.getLogger().info("Downloading fonts...");
                HttpUtils.download("https://nightx.api-minecraft.net/s/icwf3t8op4", e);
                ClientUtils.getLogger().info("Extract fonts...");
                extractZip(e.getPath(), LiquidBounce.fileManager.fontsDir.getPath());
            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

    }

    public static FontRenderer getFontRenderer(String name, int size) {
        Field[] afield = Fonts.class.getDeclaredFields();
        int liquidFontRenderer = afield.length;

        for (int font = 0; font < liquidFontRenderer; ++font) {
            Field field = afield[font];

            try {
                field.setAccessible(true);
                Object e = field.get((Object) null);

                if (e instanceof FontRenderer) {
                    FontDetails fontDetails = (FontDetails) field.getAnnotation(FontDetails.class);

                    if (fontDetails.fontName().equals(name) && fontDetails.fontSize() == size) {
                        return (FontRenderer) e;
                    }
                }
            } catch (IllegalAccessException illegalaccessexception) {
                illegalaccessexception.printStackTrace();
            }
        }

        Iterator iterator = Fonts.CUSTOM_FONT_RENDERERS.iterator();

        GameFontRenderer gamefontrenderer;
        Font font;

        do {
            if (!iterator.hasNext()) {
                return Fonts.minecraftFont;
            }

            gamefontrenderer = (GameFontRenderer) iterator.next();
            font = gamefontrenderer.getDefaultFont().getFont();
        } while (!font.getName().equals(name) || font.getSize() != size);

        return gamefontrenderer;
    }

    public static Object[] getFontDetails(FontRenderer fontRenderer) {
        Field[] font = Fonts.class.getDeclaredFields();
        int i = font.length;

        for (int j = 0; j < i; ++j) {
            Field field = font[j];

            try {
                field.setAccessible(true);
                Object e = field.get((Object) null);

                if (e.equals(fontRenderer)) {
                    FontDetails fontDetails = (FontDetails) field.getAnnotation(FontDetails.class);

                    return new Object[] { fontDetails.fontName(), Integer.valueOf(fontDetails.fontSize())};
                }
            } catch (IllegalAccessException illegalaccessexception) {
                illegalaccessexception.printStackTrace();
            }
        }

        if (fontRenderer instanceof GameFontRenderer) {
            Font font = ((GameFontRenderer) fontRenderer).getDefaultFont().getFont();

            return new Object[] { font.getName(), Integer.valueOf(font.getSize())};
        } else {
            return null;
        }
    }

    public static List getFonts() {
        ArrayList fonts = new ArrayList();
        Field[] afield = Fonts.class.getDeclaredFields();
        int i = afield.length;

        for (int j = 0; j < i; ++j) {
            Field fontField = afield[j];

            try {
                fontField.setAccessible(true);
                Object e = fontField.get((Object) null);

                if (e instanceof FontRenderer) {
                    fonts.add((FontRenderer) e);
                }
            } catch (IllegalAccessException illegalaccessexception) {
                illegalaccessexception.printStackTrace();
            }
        }

        fonts.addAll(Fonts.CUSTOM_FONT_RENDERERS);
        return fonts;
    }

    private static Font getFont(String fontName, int size) {
        try {
            FileInputStream e = new FileInputStream(new File(LiquidBounce.fileManager.fontsDir, fontName));
            Font awtClientFont = Font.createFont(0, e);

            awtClientFont = awtClientFont.deriveFont(0, (float) size);
            e.close();
            return awtClientFont;
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Font("default", 0, size);
        }
    }

    private static void extractZip(String zipFile, String outputFolder) {
        byte[] buffer = new byte[1024];

        try {
            File e = new File(outputFolder);

            if (!e.exists()) {
                e.mkdir();
            }

            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));

            for (ZipEntry zipEntry = zipInputStream.getNextEntry(); zipEntry != null; zipEntry = zipInputStream.getNextEntry()) {
                File newFile = new File(outputFolder + File.separator + zipEntry.getName());

                (new File(newFile.getParent())).mkdirs();
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);

                int i;

                while ((i = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, i);
                }

                fileOutputStream.close();
            }

            zipInputStream.closeEntry();
            zipInputStream.close();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

    }
}
