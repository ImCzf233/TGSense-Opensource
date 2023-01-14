package me.Skid.Tfont;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class FontLoaders {

    public static CFontRenderer Sans12 = new CFontRenderer(getFont("GoogleSans", 12), true, true);
    public static CFontRenderer Sans14 = new CFontRenderer(getFont("GoogleSans", 14), true, true);
    public static CFontRenderer Sans16 = new CFontRenderer(getFont("GoogleSans", 16), true, true);
    public static CFontRenderer Sans18 = new CFontRenderer(getFont("GoogleSans", 18), true, true);
    public static CFontRenderer Sans20 = new CFontRenderer(getFont("GoogleSans", 20), true, true);
    public static CFontRenderer PoppinsSemiBold16 = new CFontRenderer(getFont("PoppinsSemiBold", 16), true, true);
    public static CFontRenderer PoppinsSemiBold18 = new CFontRenderer(getFont("PoppinsSemiBold", 18), true, true);
    public static CFontRenderer PoppinsSemiBold20 = new CFontRenderer(getFont("PoppinsSemiBold", 20), true, true);
    public static CFontRenderer SFREGULAR12 = new CFontRenderer(getFont("SFREGULAR", 12), true, true);
    public static CFontRenderer SFREGULAR14 = new CFontRenderer(getFont("SFREGULAR", 14), true, true);
    public static CFontRenderer SFREGULAR16 = new CFontRenderer(getFont("SFREGULAR", 16), true, true);
    public static CFontRenderer SFREGULAR18 = new CFontRenderer(getFont("SFREGULAR", 18), true, true);
    public static CFontRenderer productsans16 = new CFontRenderer(getFont("productsans", 16), true, true);
    public static CFontRenderer productsans18 = new CFontRenderer(getFont("productsans", 18), true, true);
    public static CFontRenderer BoldFont12 = new CFontRenderer(getFont("BoldFont", 14), true, true);
    public static CFontRenderer BoldFont8 = new CFontRenderer(getFont("BoldFont", 8), true, true);
    public static CFontRenderer poppins14 = new CFontRenderer(getFont("PoppinsRegular", 14), true, true);
    public static CFontRenderer poppins15 = new CFontRenderer(getFont("PoppinsRegular", 15), true, true);
    public static CFontRenderer poppins16 = new CFontRenderer(getFont("PoppinsRegular", 16), true, true);
    public static CFontRenderer poppins18 = new CFontRenderer(getFont("PoppinsRegular", 18), true, true);
    public static CFontRenderer BoldFont14 = new CFontRenderer(getFont("BoldFont", 14), true, true);
    public static CFontRenderer BoldFont16 = new CFontRenderer(getFont("BoldFont", 16), true, true);
    public static CFontRenderer BoldFont10 = new CFontRenderer(getFont("BoldFont", 10), true, true);
    public static CFontRenderer BoldFont18 = new CFontRenderer(getFont("BoldFont", 18), true, true);
    public static CFontRenderer BoldFont20 = new CFontRenderer(getFont("BoldFont", 20), true, true);
    public static CFontRenderer BoldFont30 = new CFontRenderer(getFont("BoldFont", 30), true, true);
    public static CFontRenderer SFREGULAR25 = new CFontRenderer(getFont("SFREGULAR", 25), true, true);
    public static CFontRenderer Sans25 = new CFontRenderer(getFont("GoogleSans", 25), true, true);
    public static CFontRenderer ETB20 = new CFontRenderer(getFont("ETB", 20), true, true);
    public static CFontRenderer NovIcon20 = new CFontRenderer(getFont("NovIcon", 20), true, true);
    public static CFontRenderer FluxIcon14 = new CFontRenderer(getFont("fluxicon", 16), true, true);
    public static CFontRenderer FluxIcon16 = new CFontRenderer(getFont("fluxicon", 16), true, true);
    public static CFontRenderer FluxIcon18 = new CFontRenderer(getFont("fluxicon", 18), true, true);
    public static CFontRenderer FluxIcon20 = new CFontRenderer(getFont("fluxicon", 25), true, true);
    public static CFontRenderer FluxIcon30 = new CFontRenderer(getFont("fluxicon", 41), true, true);
    public static CFontRenderer FluxIcon40 = new CFontRenderer(getFont("fluxicon", 40), true, true);
    public static CFontRenderer FluxIcon50 = new CFontRenderer(getFont("fluxicon", 50), true, true);
    public static CFontRenderer Icon16 = new CFontRenderer(getFont("icon", 16), true, true);
    public static CFontRenderer JelloTitle20 = new CFontRenderer(getFont("jellolight", 20), true, true);
    public static CFontRenderer JelloTitle18 = new CFontRenderer(getFont("jellolight", 18), true, true);
    public static CFontRenderer JelloList16 = new CFontRenderer(getFont("jelloregular", 16), true, true);
    public static CFontRenderer NOTIFICATIONS20 = new CFontRenderer(getFont("NOTIFICATIONS", 20), true, true);
    public static CFontRenderer NOTIFICATIONS18 = new CFontRenderer(getFont("NOTIFICATIONS", 18), true, true);
    public static CFontRenderer NOTIFICATIONS16 = new CFontRenderer(getFont("NOTIFICATIONS", 16), true, true);
    public static CFontRenderer NOTIFICATIONS30 = new CFontRenderer(getFont("NOTIFICATIONS", 30), true, true);
    public static CFontRenderer siyuan20 = new CFontRenderer(getFont("siyuan", 20), true, true);
    public static CFontRenderer siyuan18 = new CFontRenderer(getFont("siyuan", 18), true, true);
    public static CFontRenderer siyuan16 = new CFontRenderer(getFont("siyuan", 16), true, true);
    public static CFontRenderer siyuan30 = new CFontRenderer(getFont("siyuan", 30), true, true);
    public static CFontRenderer tenacitybold14 = new CFontRenderer(getFont("tenacitybold", 14), true, true);
    public static CFontRenderer tenacitybold16 = new CFontRenderer(getFont("tenacitybold", 16), true, true);
    public static CFontRenderer tenacitybold18 = new CFontRenderer(getFont("tenacitybold", 18), true, true);
    public static CFontRenderer tenacitybold20 = new CFontRenderer(getFont("tenacitybold", 28), true, true);
    public static CFontRenderer verdana20 = new CFontRenderer(getFont("verdana", 20), true, true);
    public static CFontRenderer verdana18 = new CFontRenderer(getFont("verdana", 18), true, true);
    public static CFontRenderer verdana14 = new CFontRenderer(getFont("verdana", 14), true, true);
    public static CFontRenderer verdana16 = new CFontRenderer(getFont("verdana", 15), true, true);
    public static CFontRenderer verdana30 = new CFontRenderer(getFont("verdana", 30), true, true);
    public static CFontRenderer verdanab20 = new CFontRenderer(getFont("verdanab", 20), true, true);
    public static CFontRenderer verdanab18 = new CFontRenderer(getFont("verdanab", 18), true, true);
    public static CFontRenderer verdanab14 = new CFontRenderer(getFont("verdanab", 14), true, true);
    public static CFontRenderer verdanab16 = new CFontRenderer(getFont("verdanab", 16), true, true);
    public static CFontRenderer verdanab17 = new CFontRenderer(getFont("verdanab", 17), true, true);
    public static CFontRenderer verdanab30 = new CFontRenderer(getFont("verdanab", 30), true, true);
    public static CFontRenderer sf_ui_display_regular12 = new CFontRenderer(getFont("sf_ui_display_regular", 12), true, true);
    public static CFontRenderer sf_ui_display_regular14 = new CFontRenderer(getFont("sf_ui_display_regular", 16), true, true);
    public static CFontRenderer sf_ui_display_regular16 = new CFontRenderer(getFont("sf_ui_display_regular", 16), true, true);
    public static CFontRenderer sf_ui_display_regular18 = new CFontRenderer(getFont("sf_ui_display_regular", 18), true, true);
    public static CFontRenderer sf_ui_display_regular20 = new CFontRenderer(getFont("sf_ui_display_regular", 21), true, true);
    public static CFontRenderer sf_ui_display_regular22 = new CFontRenderer(getFont("sf_ui_display_regular", 22), true, true);
    public static CFontRenderer sf_ui_display_regular25 = new CFontRenderer(getFont("sf_ui_display_regular", 25), true, true);
    public static CFontRenderer sf_ui_display_regular28 = new CFontRenderer(getFont("sf_ui_display_regular", 28), true, true);
    public static CFontRenderer sf_ui_display_regular35 = new CFontRenderer(getFont("sf_ui_display_regular", 35), true, true);
    public static ArrayList fonts = new ArrayList();

    public static CFontRenderer getFontRender(int size) {
        return (CFontRenderer) FontLoaders.fonts.get(size - 10);
    }

    public static Font getFont(String name, int size) {
        Long Start = Long.valueOf(System.currentTimeMillis());

        Font font;

        try {
            InputStream ex = FontLoaders.class.getResourceAsStream("/assets/minecraft/langya/font/" + name + ".ttf");

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }
}
