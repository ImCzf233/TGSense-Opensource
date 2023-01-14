package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;

public class CodeTool extends MinecraftInstance {

    public static GuiIngame guiIngame;
    public static IEnumFacing enumFacing;

    public static void setSpeed(double speed) {
        Minecraft.getMinecraft().player.motionX = -Math.sin(MovementUtils.getDirection()) * speed;
        Minecraft.getMinecraft().player.motionZ = Math.cos(MovementUtils.getDirection()) * speed;
    }
}
