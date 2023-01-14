package me.Skid.ui.Fonts;

import java.awt.Font;
import java.io.InputStream;
import me.Skid.ui.Fonts.font.Yarukon;

public class FontManager {

    public static FontUtils BoldFont30;
    public static FontUtils poppins16;
    public static FontUtils poppins18;
    public static FontUtils siyuan14;
    public static FontUtils siyuan16;
    public static FontUtils siyuan18;
    public static FontUtils siyuan20;
    public static FontUtils productsans14;
    public static FontUtils productsans16;
    public static FontUtils productsans18;
    public static FontUtils productsans20;
    public static FontUtils productsans35;
    public static FontUtils SFREGULAR14;
    public static FontUtils SFREGULAR16;
    public static FontUtils SFREGULAR18;
    public static FontUtils SFREGULAR20;
    public static FontUtils PoppinsSemiBold14;
    public static FontUtils PoppinsSemiBold16;
    public static FontUtils PoppinsSemiBold18;
    public static FontUtils PoppinsSemiBold20;
    public static FontUtils FluxIcon16;
    public static FontUtils FluxIcon18;
    public static FontUtils icon10;
    public static FontUtils icon20;
    public static FontUtils icon18;
    public static FontUtils icon14;
    public static FontUtils icon50;
    public static FontUtils HanabiIcon14;
    public static FontUtils HanabiIcon38;

    public static Font getFont(String name, int size) {
        Font font;

        try {
            InputStream ex = FontManager.class.getResourceAsStream("/assets/minecraft/langya/font/" + name);

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            System.out.println("Error loading font " + name);
            font = new Font("Arial", 0, size);
        }

        return font;
    }

    static {
        new Yarukon();
        FontManager.BoldFont30 = new FontUtils(getFont("BoldFont.ttf", 30), 0, 30, 7, false);
        FontManager.poppins16 = new FontUtils(getFont("PoppinsRegular.ttf", 16), 0, 16, 7, false);
        FontManager.poppins18 = new FontUtils(getFont("PoppinsRegular.ttf", 18), 0, 18, 7, false);
        FontManager.siyuan14 = new FontUtils(getFont("siyuan.ttf", 14), 0, 14, 7, false);
        FontManager.siyuan16 = new FontUtils(getFont("siyuan.ttf", 16), 0, 16, 7, false);
        FontManager.siyuan18 = new FontUtils(getFont("siyuan.ttf", 18), 0, 18, 7, false);
        FontManager.siyuan20 = new FontUtils(getFont("siyuan.ttf", 20), 0, 20, 7, false);
        FontManager.productsans14 = new FontUtils(getFont("productsans.ttf", 14), 0, 14, 7, false);
        FontManager.productsans16 = new FontUtils(getFont("productsans.ttf", 16), 0, 16, 7, false);
        FontManager.productsans18 = new FontUtils(getFont("productsans.ttf", 18), 0, 18, 7, false);
        FontManager.productsans20 = new FontUtils(getFont("productsans.ttf", 20), 0, 20, 7, false);
        FontManager.productsans35 = new FontUtils(getFont("productsans.ttf", 20), 0, 20, 7, false);
        FontManager.SFREGULAR14 = new FontUtils(getFont("SFREGULAR.ttf", 14), 0, 14, 7, false);
        FontManager.SFREGULAR16 = new FontUtils(getFont("SFREGULAR.ttf", 16), 0, 16, 7, false);
        FontManager.SFREGULAR18 = new FontUtils(getFont("SFREGULAR.ttf", 18), 0, 18, 7, false);
        FontManager.SFREGULAR20 = new FontUtils(getFont("SFREGULAR.ttf", 20), 0, 20, 7, false);
        FontManager.PoppinsSemiBold14 = new FontUtils(getFont("PoppinsSemiBold.ttf", 14), 0, 14, 7, false);
        FontManager.PoppinsSemiBold16 = new FontUtils(getFont("PoppinsSemiBold.ttf", 16), 0, 16, 7, false);
        FontManager.PoppinsSemiBold18 = new FontUtils(getFont("PoppinsSemiBold.ttf", 18), 0, 18, 7, false);
        FontManager.PoppinsSemiBold20 = new FontUtils(getFont("PoppinsSemiBold.ttf", 20), 0, 20, 7, false);
        FontManager.FluxIcon16 = new FontUtils(getFont("icon.ttf", 16), 0, 14, 7, false);
        FontManager.FluxIcon18 = new FontUtils(getFont("icon.ttf", 18), 0, 14, 7, false);
        FontManager.icon10 = new FontUtils(getFont("fluxicon.ttf", 10), 0, 14, 7, false);
        FontManager.icon20 = new FontUtils(getFont("fluxicon.ttf", 20), 0, 50, 7, false);
        FontManager.icon18 = new FontUtils(getFont("fluxicon.ttf", 20), 0, 50, 7, false);
        FontManager.icon14 = new FontUtils(getFont("fluxicon.ttf", 14), 0, 14, 7, false);
        FontManager.icon50 = new FontUtils(getFont("fluxicon.ttf", 50), 0, 50, 7, false);
        FontManager.HanabiIcon14 = new FontUtils(getFont("HanabiFont.ttf", 14), 0, 14, 7, false);
        FontManager.HanabiIcon38 = new FontUtils(getFont("HanabiFont.ttf", 80), 0, 80, 7, false);
    }
}
