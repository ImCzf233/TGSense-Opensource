package me.Skid;

import java.awt.Color;
import me.Skid.novoline.api.FontManager;
import me.Skid.novoline.impl.SimpleFontManager;
import me.Skid.utils.ScaleUtils;
import net.minecraft.client.Minecraft;

public class Client {

    public static double fontScaleOffset = 1.0D;
    public static FontManager fontManager = SimpleFontManager.create();
    public static String name = "TGSense";
    public static String version = "230105";
    public static int THEME_RGB_COLOR = (new Color(36, 240, 0)).getRGB();
    public static Client instance = new Client();
    public static ScaleUtils scaleUtils = new ScaleUtils(2);

    public static FontManager getFontManager() {
        return Client.fontManager;
    }

    public static double deltaTime() {
        return Minecraft.getDebugFPS() > 0 ? 1.0D / (double) Minecraft.getDebugFPS() : 1.0D;
    }
}
