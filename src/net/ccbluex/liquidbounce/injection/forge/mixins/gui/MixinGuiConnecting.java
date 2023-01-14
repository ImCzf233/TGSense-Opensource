package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.mojang.authlib.GameProfile;
import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import net.ccbluex.liquidbounce.injection.backend.ServerDataImplKt;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.vitox.mcleaks.MCLeaks;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({ GuiConnecting.class})
public abstract class MixinGuiConnecting extends GuiScreen {

    @Shadow
    @Final
    private static AtomicInteger CONNECTION_ID;
    @Shadow
    @Final
    private static Logger LOGGER;
    @Shadow
    private NetworkManager networkManager;
    @Shadow
    private boolean cancel;
    @Shadow
    @Final
    private GuiScreen previousGuiScreen;

    @Inject(
        method = { "connect"},
        at = {             @At("HEAD")}
    )
    private void headConnect(String ip, int port, CallbackInfo callbackInfo) {
        ServerUtils.serverData = ServerDataImplKt.wrap(new ServerData("", ip + ":" + port, false));
    }

    @Inject(
        method = { "connect"},
        at = {             @At(
                value = "NEW",
                target = "net/minecraft/network/login/client/CPacketLoginStart"
            )},
        cancellable = true
    )
    private void mcLeaks(CallbackInfo callbackInfo) {
        if (MCLeaks.isAltActive()) {
            this.networkManager.sendPacket(new CPacketLoginStart(new GameProfile((UUID) null, MCLeaks.getSession().getUsername())));
            callbackInfo.cancel();
        }

    }

    @Overwrite
    private void connect(String ip, int port) {
        MixinGuiConnecting.LOGGER.info("Connecting to " + ip + ", " + port);
        (new Thread(() -> {
            // $FF: Couldn't be decompiled
        }, "Server Connector #" + MixinGuiConnecting.CONNECTION_ID.incrementAndGet())).start();
    }

    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        this.drawDefaultBackground();
        RenderUtils.drawLoadingCircle((float) (scaledResolution.getScaledWidth() / 2), (float) (scaledResolution.getScaledHeight() / 4 + 70));
        String ip = "Unknown";
        ServerData serverData = this.mc.getCurrentServerData();

        if (serverData != null) {
            ip = serverData.serverIP;
        }

        Fonts.font40.drawCenteredString("Connecting to", (float) (scaledResolution.getScaledWidth() / 2), (float) (scaledResolution.getScaledHeight() / 4 + 110), 16777215, true);
        Fonts.font35.drawCenteredString(ip, (float) (scaledResolution.getScaledWidth() / 2), (float) (scaledResolution.getScaledHeight() / 4 + 120), 5407227, true);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
