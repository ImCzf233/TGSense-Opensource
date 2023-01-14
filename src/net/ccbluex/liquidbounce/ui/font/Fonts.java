package net.ccbluex.liquidbounce.ui.font;

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
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Fonts extends MinecraftInstance {

    @FontDetails(
        fontName = "Minecraft Font"
    )
    public static final IFontRenderer minecraftFont = Fonts.mc.getFontRendererObj();
    private static final HashMap CUSTOM_FONT_RENDERERS = new HashMap();
    @FontDetails(
        fontName = "flux",
        fontSize = 35
    )
    public static IFontRenderer flux;
    @FontDetails(
        fontName = "flux",
        fontSize = 45
    )
    public static IFontRenderer flux45;
    @FontDetails(
        fontName = "Flux",
        fontSize = 38
    )
    public static GameFontRenderer flux38;
    @FontDetails(
        fontName = "Nico",
        fontSize = 180
    )
    public static GameFontRenderer nico80;
    @FontDetails(
        fontName = "Tenacitybold",
        fontSize = 180
    )
    public static IFontRenderer tenacitybold30;
    @FontDetails(
        fontName = "Tenacitybold",
        fontSize = 22
    )
    public static IFontRenderer tenacitybold22;
    @FontDetails(
        fontName = "Move",
        fontSize = 180
    )
    public static IFontRenderer move35;
    @FontDetails(
        fontName = "Roboto-Italic ",
        fontSize = 35
    )
    public static IFontRenderer Italic35;
    @FontDetails(
        fontName = "Roboto-Italic ",
        fontSize = 45
    )
    public static IFontRenderer Italic45;
    @FontDetails(
        fontName = "Roboto-Italic ",
        fontSize = 60
    )
    public static IFontRenderer Italic60;
    @FontDetails(
        fontName = "Roboto-Italic ",
        fontSize = 120
    )
    public static IFontRenderer Italic120;
    @FontDetails(
        fontName = "Roboto-Light.ttf ",
        fontSize = 35
    )
    public static IFontRenderer Light35;
    @FontDetails(
        fontName = "Roboto-Light.ttf ",
        fontSize = 45
    )
    public static IFontRenderer Light45;
    @FontDetails(
        fontName = "Roboto-Light.ttf ",
        fontSize = 60
    )
    public static IFontRenderer Light60;
    @FontDetails(
        fontName = "Bold",
        fontSize = 30
    )
    public static IFontRenderer bold30;
    @FontDetails(
        fontName = "Bold",
        fontSize = 40
    )
    public static IFontRenderer bold40;
    @FontDetails(
        fontName = "Bold",
        fontSize = 35
    )
    public static IFontRenderer bold35;
    @FontDetails(
        fontName = "Bold",
        fontSize = 28
    )
    public static IFontRenderer bold28;
    @FontDetails(
        fontName = "Bold",
        fontSize = 45
    )
    public static IFontRenderer bold45;
    @FontDetails(
        fontName = "Bold",
        fontSize = 72
    )
    public static IFontRenderer bold72;
    @FontDetails(
        fontName = "Bold",
        fontSize = 180
    )
    public static IFontRenderer bold180;
    @FontDetails(
        fontName = "Roboto-Light.ttf ",
        fontSize = 120
    )
    public static IFontRenderer Light120;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 35
    )
    public static IFontRenderer font35;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 30
    )
    public static IFontRenderer font30;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 24
    )
    public static IFontRenderer fontTiny;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 25
    )
    public static IFontRenderer font25;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 40
    )
    public static IFontRenderer font40;
    @FontDetails(
        fontName = "Roboto Medium",
        fontSize = 80
    )
    public static IFontRenderer font80;
    @FontDetails(
        fontName = "Roboto Bold",
        fontSize = 72
    )
    public static IFontRenderer fontBold72;
    @FontDetails(
        fontName = "Roboto Bold",
        fontSize = 120
    )
    public static IFontRenderer fontBold120;
    @FontDetails(
        fontName = "Roboto Bold",
        fontSize = 180
    )
    public static IFontRenderer fontBold180;
    @FontDetails(
        fontName = "jellom",
        fontSize = 20
    )
    public static GameFontRenderer jellom;
    @FontDetails(
        fontName = "jello",
        fontSize = 20
    )
    public static GameFontRenderer jello;
    @FontDetails(
        fontName = "jello120 two ",
        fontSize = 120
    )
    public static IFontRenderer jello120;
    @FontDetails(
        fontName = "jello35",
        fontSize = 35
    )
    public static IFontRenderer jello35;
    @FontDetails(
        fontName = "jellolight38",
        fontSize = 38
    )
    public static IFontRenderer jellolight38;
    @FontDetails(
        fontName = "jellolight21",
        fontSize = 43
    )
    public static IFontRenderer jellolight21;
    @FontDetails(
        fontName = "jellolight24",
        fontSize = 65
    )
    public static IFontRenderer jellolight24;
    @FontDetails(
        fontName = "jellolight30",
        fontSize = 45
    )
    public static IFontRenderer jellolight30;
    @FontDetails(
        fontName = "jellolight48",
        fontSize = 48
    )
    public static IFontRenderer jellolight48;
    @FontDetails(
        fontName = "JelloMedium_44",
        fontSize = 44
    )
    public static IFontRenderer JelloMedium_44;
    @FontDetails(
        fontName = "jellolight40",
        fontSize = 40
    )
    public static IFontRenderer jellolight40;
    @FontDetails(
        fontName = "jellolightBig",
        fontSize = 86
    )
    public static IFontRenderer jellolightBig;
    @FontDetails(
        fontName = "JelloMedium_28",
        fontSize = 28
    )
    public static IFontRenderer JelloMedium_28;
    @FontDetails(
        fontName = "JelloMedium_56",
        fontSize = 56
    )
    public static IFontRenderer JelloMedium_56;
    @FontDetails(
        fontName = "JelloMedium_38",
        fontSize = 38
    )
    public static IFontRenderer JelloMedium_38;
    @FontDetails(
        fontName = "JelloMedium_24",
        fontSize = 24
    )
    public static IFontRenderer JelloMedium_24;
    @FontDetails(
        fontName = "JelloMedium_22",
        fontSize = 22
    )
    public static IFontRenderer JelloMedium_22;
    @FontDetails(
        fontName = "fontTahoma",
        fontSize = 35
    )
    public static IFontRenderer fontTahoma;
    @FontDetails(
        fontName = "fontTahomaSmall",
        fontSize = 11
    )
    public static IFontRenderer fontTahomaSmall;
    @FontDetails(
        fontName = "regular35",
        fontSize = 35
    )
    public static IFontRenderer regular35;
    @FontDetails(
        fontName = "regular28",
        fontSize = 28
    )
    public static IFontRenderer regular28;
    @FontDetails(
        fontName = "regular40",
        fontSize = 40
    )
    public static IFontRenderer regular40;
    @FontDetails(
        fontName = "tenacity35",
        fontSize = 35
    )
    public static IFontRenderer tenacity35;
    @FontDetails(
        fontName = "jello35",
        fontSize = 37
    )
    public static IFontRenderer jello37;
    @FontDetails(
        fontName = "jello35",
        fontSize = 45
    )
    public static IFontRenderer jello45;
    @FontDetails(
        fontName = "jello60",
        fontSize = 60
    )
    public static IFontRenderer jello60;
    @FontDetails(
        fontName = "jello",
        fontSize = 24
    )
    public static IFontRenderer jello40;
    @FontDetails(
        fontName = "jello30",
        fontSize = 30
    )
    public static IFontRenderer jello30;
    @FontDetails(
        fontName = "jello72",
        fontSize = 72
    )
    public static IFontRenderer jello72;
    @FontDetails(
        fontName = "SFUI Regular",
        fontSize = 18
    )
    public static IFontRenderer fontSFUI18;
    @FontDetails(
        fontName = "SFUI Regular",
        fontSize = 16
    )
    public static IFontRenderer fontSFUI16;
    @FontDetails(
        fontName = "SFUI Regular",
        fontSize = 35
    )
    public static IFontRenderer fontSFUI35;
    @FontDetails(
        fontName = "SFUI Regular",
        fontSize = 40
    )
    public static IFontRenderer fontSFUI40;
    @FontDetails(
        fontName = "SFUI Regular",
        fontSize = 56
    )
    public static IFontRenderer fontSFUI56;
    @FontDetails(
        fontName = "SFUI Regular",
        fontSize = 120
    )
    public static IFontRenderer fontSFUI120;
    @FontDetails(
        fontName = "AlibabaSansLight35",
        fontSize = 35
    )
    public static IFontRenderer AlibabaSansLight35;
    @FontDetails(
        fontName = "AlibabaSansLight45",
        fontSize = 45
    )
    public static IFontRenderer AlibabaSansLight45;
    @FontDetails(
        fontName = "AlibabaSansLight60",
        fontSize = 60
    )
    public static IFontRenderer AlibabaSansLight60;
    @FontDetails(
        fontName = "ComfortaaRegular35",
        fontSize = 35
    )
    public static IFontRenderer ComfortaaRegular35;
    @FontDetails(
        fontName = "ComfortaaRegular45",
        fontSize = 45
    )
    public static IFontRenderer ComfortaaRegular45;
    @FontDetails(
        fontName = "ComfortaaRegular60",
        fontSize = 60
    )
    public static IFontRenderer ComfortaaRegular60;
    @FontDetails(
        fontName = "Roboto Comfortaa",
        fontSize = 35
    )
    public static IFontRenderer Comfortaa35;
    @FontDetails(
        fontName = "CasanovaScotia35",
        fontSize = 35
    )
    public static IFontRenderer CasanovaScotia35;
    @FontDetails(
        fontName = "CasanovaScotia45",
        fontSize = 45
    )
    public static IFontRenderer CasanovaScotia45;
    @FontDetails(
        fontName = "CasanovaScotia60",
        fontSize = 60
    )
    public static IFontRenderer CasanovaScotia60;
    @FontDetails(
        fontName = "Posteraaa",
        fontSize = 35
    )
    public static IFontRenderer Posterama35;
    @FontDetails(
        fontName = "Posterama",
        fontSize = 40
    )
    public static IFontRenderer Posterama40;
    @FontDetails(
        fontName = "Posterama",
        fontSize = 30
    )
    public static IFontRenderer Posterama30;
    @FontDetails(
        fontName = "Posterama",
        fontSize = 52
    )
    public static IFontRenderer Posterama52;
    @FontDetails(
        fontName = "Posterama",
        fontSize = 96
    )
    public static IFontRenderer Posterama96;
    @FontDetails(
        fontName = "Move",
        fontSize = 35
    )
    public static IFontRenderer Move35;
    @FontDetails(
        fontName = "Move",
        fontSize = 36
    )
    public static IFontRenderer Move36;
    @FontDetails(
        fontName = "comfortaa",
        fontSize = 96
    )
    public static IFontRenderer com96;
    @FontDetails(
        fontName = "comfortaa",
        fontSize = 40
    )
    public static IFontRenderer com40;
    @FontDetails(
        fontName = "comfortaa",
        fontSize = 30
    )
    public static IFontRenderer com30;
    @FontDetails(
        fontName = "comfortaa",
        fontSize = 35
    )
    public static IFontRenderer com35;
    @FontDetails(
        fontName = "comfortaa",
        fontSize = 18
    )
    public static IFontRenderer com18;

    public static void loadFonts() {
        long l = System.currentTimeMillis();

        ClientUtils.getLogger().info("Loading Fonts.");
        downloadFonts();
        Fonts.com35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getCom(35)));
        Fonts.com96 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getCom(72)));
        Fonts.com40 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getCom(40)));
        Fonts.com30 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getCom(30)));
        Fonts.com18 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getCom(18)));
        Fonts.flux = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFlux(30)));
        Fonts.flux45 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFlux(45)));
        Fonts.flux38 = new GameFontRenderer(getFlux2(38));
        Fonts.nico80 = new GameFontRenderer(getnico(80));
        Fonts.tenacitybold30 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("tenacitybold.ttf", 30)));
        Fonts.tenacitybold22 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("tenacitybold.ttf", 22)));
        Fonts.move35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("langya/font/TahomaBold.ttf", 35)));
        Fonts.Comfortaa35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Comfortaa.ttf", 35)));
        Fonts.Light35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Light.ttf", 35)));
        Fonts.Light45 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Light.ttf", 45)));
        Fonts.Light60 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Light.ttf", 60)));
        Fonts.Light120 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Light.ttf", 120)));
        Fonts.Posterama35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getPosterama(35)));
        Fonts.Posterama30 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getPosterama(30)));
        Fonts.Posterama40 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getPosterama(40)));
        Fonts.Posterama52 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getPosterama(52)));
        Fonts.Posterama96 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getPosterama(96)));
        Fonts.Move35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getMove(35)));
        Fonts.Move36 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getMove(36)));
        Fonts.Italic35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Italic.ttf", 35)));
        Fonts.Italic45 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Italic.ttf", 45)));
        Fonts.Italic60 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Italic.ttf", 60)));
        Fonts.Italic120 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Italic.ttf", 120)));
        Fonts.font35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(35)));
        Fonts.font25 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(25)));
        Fonts.fontTiny = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Medium.ttf", 24)));
        Fonts.font40 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(40)));
        Fonts.font30 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(30)));
        Fonts.font80 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(80)));
        Fonts.fontBold72 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Medium.ttf", 72)));
        Fonts.fontBold120 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Medium.ttf", 120)));
        Fonts.fontBold180 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Bold.ttf", 180)));
        Fonts.fontSFUI18 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(18)));
        Fonts.fontSFUI16 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(16)));
        Fonts.fontSFUI35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(35)));
        Fonts.fontSFUI40 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(40)));
        Fonts.fontSFUI56 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(56)));
        Fonts.fontSFUI120 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getSFUI(120)));
        Fonts.jello = new GameFontRenderer(getFont("jelloSB.ttf", 20));
        Fonts.jellom = new GameFontRenderer(getFont("jellom.ttf", 20));
        Fonts.jello40 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jello.ttf", 40)));
        Fonts.jello120 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jello.ttf", 120)));
        Fonts.jello35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jello.ttf", 35)));
        Fonts.jello60 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jello.ttf", 60)));
        Fonts.jello37 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jello.ttf", 37)));
        Fonts.jello30 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jello.ttf", 30)));
        Fonts.jello45 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jello.ttf", 45)));
        Fonts.jello72 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jello.ttf", 72)));
        Fonts.jello72 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jello.ttf", 72)));
        Fonts.bold35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getBold(35)));
        Fonts.bold28 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getBold(28)));
        Fonts.bold40 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getBold(40)));
        Fonts.bold45 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getBold(45)));
        Fonts.bold30 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getBold(30)));
        Fonts.bold72 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getBold(72)));
        Fonts.bold180 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getBold(180)));
        Fonts.AlibabaSansLight35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getAlibabaSansLight(35)));
        Fonts.AlibabaSansLight45 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getAlibabaSansLight(45)));
        Fonts.AlibabaSansLight60 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getAlibabaSansLight(60)));
        Fonts.ComfortaaRegular35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getComfortaaRegular(35)));
        Fonts.ComfortaaRegular45 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getComfortaaRegular(45)));
        Fonts.ComfortaaRegular60 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getComfortaaRegular(60)));
        Fonts.CasanovaScotia35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getCasanovaScotia(35)));
        Fonts.CasanovaScotia45 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getCasanovaScotia(45)));
        Fonts.CasanovaScotia60 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getCasanovaScotia(60)));
        Fonts.jellolight38 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellolight.ttf", 38)));
        Fonts.jellolight21 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellolight.ttf", 43)));
        Fonts.jellolightBig = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellolight.ttf", 86)));
        Fonts.jellolight24 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellolight.ttf", 65)));
        Fonts.jellolight30 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellolight.ttf", 45)));
        Fonts.jellolight48 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellolight.ttf", 48)));
        Fonts.jellolight40 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellolight.ttf", 40)));
        Fonts.JelloMedium_28 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellomedium.ttf", 28)));
        Fonts.JelloMedium_44 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellomedium.ttf", 44)));
        Fonts.JelloMedium_56 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellomedium.ttf", 56)));
        Fonts.JelloMedium_38 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellomedium.ttf", 38)));
        Fonts.JelloMedium_24 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellomedium.ttf", 24)));
        Fonts.JelloMedium_22 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("jellomedium.ttf", 22)));
        Fonts.fontTahoma = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("tahomabold.ttf", 35)));
        Fonts.fontTahomaSmall = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("tahomabold.ttf", 11)));
        Fonts.regular35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Regular.ttf", 35)));
        Fonts.regular28 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Regular.ttf", 28)));
        Fonts.regular40 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Regular.ttf", 40)));
        Fonts.tenacity35 = Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(getFont("tenacity.ttf", 35)));

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
                    Font font = getFont(fontObject.get("fontFile").getAsString(), fontObject.get("fontSize").getAsInt());

                    Fonts.CUSTOM_FONT_RENDERERS.put(new Fonts.FontInfo(font), Fonts.classProvider.wrapFontRenderer(new GameFontRenderer(font)));
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

            if (!e.exists()) {
                ClientUtils.getLogger().info("Downloading fonts...");
                HttpUtils.download("https://cloud.liquidbounce.net/LiquidBounce/fonts/Roboto.zip", e);
                ClientUtils.getLogger().info("Extract fonts...");
                extractZip(e.getPath(), LiquidBounce.fileManager.fontsDir.getPath());
            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

    }

    private static Font getJello(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/jello.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    public static IFontRenderer getFontRenderer(String name, int size) {
        Field[] afield = Fonts.class.getDeclaredFields();
        int i = afield.length;

        for (int j = 0; j < i; ++j) {
            Field field = afield[j];

            try {
                field.setAccessible(true);
                Object e = field.get((Object) null);

                if (e instanceof IFontRenderer) {
                    FontDetails fontDetails = (FontDetails) field.getAnnotation(FontDetails.class);

                    if (fontDetails.fontName().equals(name) && fontDetails.fontSize() == size) {
                        return (IFontRenderer) e;
                    }
                }
            } catch (IllegalAccessException illegalaccessexception) {
                illegalaccessexception.printStackTrace();
            }
        }

        return (IFontRenderer) Fonts.CUSTOM_FONT_RENDERERS.getOrDefault(new Fonts.FontInfo(name, size), Fonts.minecraftFont);
    }

    private static Font getFlux2(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/flux.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    private static Font getnico(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/nico.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    private static Font gettenacitybold(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/tenacitybold.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    private static Font getmove(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/TahomaBold.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    public static Fonts.FontInfo getFontDetails(IFontRenderer fontRenderer) {
        Field[] afield = Fonts.class.getDeclaredFields();
        int entry = afield.length;

        for (int i = 0; i < entry; ++i) {
            Field field = afield[i];

            try {
                field.setAccessible(true);
                Object e = field.get((Object) null);

                if (e.equals(fontRenderer)) {
                    FontDetails fontDetails = (FontDetails) field.getAnnotation(FontDetails.class);

                    return new Fonts.FontInfo(fontDetails.fontName(), fontDetails.fontSize());
                }
            } catch (IllegalAccessException illegalaccessexception) {
                illegalaccessexception.printStackTrace();
            }
        }

        Iterator iterator = Fonts.CUSTOM_FONT_RENDERERS.entrySet().iterator();

        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            entry = (Entry) iterator.next();
        } while (entry.getValue() != fontRenderer);

        return (Fonts.FontInfo) entry.getKey();
    }

    private static Font getPosterama(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/PosteramaText-Regular.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("Posterama", 0, size);
        }

        return font;
    }

    private static Font getMove(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/PosteramaText-Regular.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("Posterama", 0, size);
        }

        return font;
    }

    private static Font getBold(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/bold.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    public float getCharWidth(char c) {
        return (float) Fonts.fontSFUI35.getStringWidth(String.valueOf(c));
    }

    private static Font getSFUI(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/sfuidisplayregular.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    private static Font getComfortaaRegular(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/ComfortaaRegular.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    private static Font getCasanovaScotia(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/CasanovaScotia.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    private static Font getAlibabaSansLight(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/AlibabaSansLight.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    private static Font getFlux(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/fluxicon.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    private static Font getCom(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/comfortaa.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
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

                if (e instanceof IFontRenderer) {
                    fonts.add((IFontRenderer) e);
                }
            } catch (IllegalAccessException illegalaccessexception) {
                illegalaccessexception.printStackTrace();
            }
        }

        fonts.addAll(Fonts.CUSTOM_FONT_RENDERERS.values());
        return fonts;
    }

    private static Font getFont(String fontName, int size) {
        try {
            InputStream e = Fonts.class.getResourceAsStream("/assets/minecraft/langya/font/" + fontName);

            assert e != null;

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

    public static class FontInfo {

        private final String name;
        private final int fontSize;

        public FontInfo(String name, int fontSize) {
            this.name = name;
            this.fontSize = fontSize;
        }

        public FontInfo(Font font) {
            this(font.getName(), font.getSize());
        }

        public String getName() {
            return this.name;
        }

        public int getFontSize() {
            return this.fontSize;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                Fonts.FontInfo fontInfo = (Fonts.FontInfo) o;

                return this.fontSize != fontInfo.fontSize ? false : Objects.equals(this.name, fontInfo.name);
            } else {
                return false;
            }
        }

        public int hashCode() {
            int result = this.name != null ? this.name.hashCode() : 0;

            result = 31 * result + this.fontSize;
            return result;
        }
    }
}
