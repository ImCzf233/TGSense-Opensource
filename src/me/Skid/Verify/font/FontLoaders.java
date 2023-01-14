package me.Skid.Verify.font;

import java.awt.Font;
import java.io.InputStream;
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
    public static CFontRenderer F90 = new CFontRenderer(getFont(90), true, true);
    public static CFontRenderer xyz16 = new CFontRenderer(getxyz(16), true, true);
    public static CFontRenderer xyz18 = new CFontRenderer(getxyz(18), true, true);
    public static CFontRenderer xyz20 = new CFontRenderer(getxyz(20), true, true);
    public static CFontRenderer xyz22 = new CFontRenderer(getxyz(22), true, true);
    public static CFontRenderer xyz28 = new CFontRenderer(getxyz(28), true, true);
    public static CFontRenderer xyz32 = new CFontRenderer(getxyz(32), true, true);
    public static CFontRenderer xyz26 = new CFontRenderer(getxyz(26), true, true);
    public static CFontRenderer xyz70 = new CFontRenderer(getxyz(70), true, true);
    public static CFontRenderer C16 = new CFontRenderer(getComfortaa(16), true, true);
    public static CFontRenderer C18 = new CFontRenderer(getComfortaa(18), true, true);
    public static CFontRenderer C20 = new CFontRenderer(getComfortaa(20), true, true);
    public static CFontRenderer C22 = new CFontRenderer(getComfortaa(22), true, true);
    public static CFontRenderer C30 = new CFontRenderer(getComfortaa(30), true, true);
    public static CFontRenderer C50 = new CFontRenderer(getComfortaa(50), true, true);
    public static CFontRenderer C12 = new CFontRenderer(getComfortaa(12), true, true);
    public static CFontRenderer C14 = new CFontRenderer(getComfortaa(14), true, true);
    public static CFontRenderer C55 = new CFontRenderer(getComfortaa(55), true, true);
    public static CFontRenderer Logo = new CFontRenderer(getNovo(40), true, true);
    public static ArrayList fonts = new ArrayList();

    public static CFontRenderer getFontRender(int size) {
        return (CFontRenderer) FontLoaders.fonts.get(size - 10);
    }

    private static Font getxyz(int size) {
        Font font;

        try {
            InputStream exception = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("skyrim/Fonts/huahuo.ttf")).getInputStream();

            font = Font.createFont(0, exception);
            font = font.deriveFont(0, (float) size);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }

        return font;
    }

    public static Font getFont(int size) {
        Font font;

        try {
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("skyrim/Fonts/huahuo.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
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
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("skyrim/Fonts/huahuo.ttf")).getInputStream();

            font = Font.createFont(0, ex);
            font = font.deriveFont(0, (float) size);
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
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("storyline/font/huahuo.ttf")).getInputStream();

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
