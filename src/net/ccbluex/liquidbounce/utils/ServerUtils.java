package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ServerUtils extends MinecraftInstance {

    public static IServerData serverData;

    public static void connectToLastServer() {
        if (ServerUtils.serverData != null) {
            ServerUtils.mc.displayGuiScreen((IGuiScreen) (new GuiConnecting(new GuiMultiplayer(LiquidBounce.GuiMainMenu), (Minecraft) ServerUtils.mc, (ServerData) ServerUtils.serverData)));
        }
    }

    public static String getRemoteIp() {
        String serverIp = "Singleplayer";

        if (ServerUtils.mc.getTheWorld().isRemote()) {
            IServerData serverData = ServerUtils.mc.getCurrentServerData();

            if (serverData != null) {
                serverIp = serverData.getServerIP();
            }
        }

        return serverIp;
    }
}
