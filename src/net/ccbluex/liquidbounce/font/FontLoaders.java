package net.ccbluex.liquidbounce.font;

import java.awt.Font;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public abstract class FontLoaders {

    public static CFontRenderer F14 = new CFontRenderer(getFont(14), true, true);
    public static CFontRenderer F16 = new CFontRenderer(getFont(16), true, true);
    public static CFontRenderer F18 = new CFontRenderer(getFont(18), true, true);
    public static CFontRenderer F20 = new CFontRenderer(getFont(20), true, true);
    public static CFontRenderer F22 = new CFontRenderer(getFont(22), true, true);
    public static CFontRenderer F23 = new CFontRenderer(getFont(23), true, true);
    public static CFontRenderer F24 = new CFontRenderer(getFont(24), true, true);
    public static CFontRenderer F30 = new CFontRenderer(getFont(30), true, true);
    public static CFontRenderer F40 = new CFontRenderer(getFont(40), true, true);
    public static CFontRenderer F50 = new CFontRenderer(getFont(50), true, true);
    public static CFontRenderer C12 = new CFontRenderer(getComfortaa(12), true, true);
    public static CFontRenderer C14 = new CFontRenderer(getComfortaa(14), true, true);
    public static CFontRenderer C16 = new CFontRenderer(getComfortaa(16), true, true);
    public static CFontRenderer C18 = new CFontRenderer(getComfortaa(18), true, true);
    public static CFontRenderer C20 = new CFontRenderer(getComfortaa(20), true, true);
    public static CFontRenderer C22 = new CFontRenderer(getComfortaa(22), true, true);
    public static CFontRenderer Logo = new CFontRenderer(getNovo(40), true, true);
    public static CFontRenderer Nico80 = new CFontRenderer(getNico80(40), true, true);
    public static ArrayList fonts = new ArrayList();

    public static CFontRenderer getFontRender(int size) {
        return (CFontRenderer) FontLoaders.fonts.get(size - 10);
    }

    public static Font getFont(int size) {
        Font font;

        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("langya/font/misans.ttf")).getInputStream()).deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    public static Font getComfortaa(int size) {
        Font font;

        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("fdpclient/font/misans.ttf")).getInputStream()).deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    public static Font getNovo(int size) {
        Font font;

        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("fdpclient/font/misans.ttf")).getInputStream()).deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    public static Font getNico80(int size) {
        Font font;

        try {
            font = Font.createFont(0, Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("Rise/font/nico.ttf")).getInputStream()).deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }
}
