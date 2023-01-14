package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import io.netty.buffer.Unpooled;
import java.net.URI;
import java.net.URISyntaxException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EntityMovementEvent;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.injection.backend.EntityImplKt;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketResourcePackStatus.Action;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ NetHandlerPlayClient.class})
public abstract class MixinNetHandlerPlayClient {

    @Shadow
    public int currentServerMaxPlayers;
    @Shadow
    @Final
    private NetworkManager netManager;
    @Shadow
    private Minecraft client;
    @Shadow
    private WorldClient world;

    @Inject(
        method = { "handleResourcePack"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void handleResourcePack(SPacketResourcePackSend p_handleResourcePack_1_, CallbackInfo callbackInfo) {
        String url = p_handleResourcePack_1_.getURL();
        String hash = p_handleResourcePack_1_.getHash();

        try {
            String e = (new URI(url)).getScheme();
            boolean isLevelProtocol = "level".equals(e);

            if (!"http".equals(e) && !"https".equals(e) && !isLevelProtocol) {
                throw new URISyntaxException(url, "Wrong protocol");
            }

            if (isLevelProtocol && (url.contains("..") || !url.endsWith("/resources.zip"))) {
                throw new URISyntaxException(url, "Invalid levelstorage resourcepack path");
            }
        } catch (URISyntaxException urisyntaxexception) {
            ClientUtils.getLogger().error("Failed to handle resource pack", urisyntaxexception);
            this.netManager.sendPacket(new CPacketResourcePackStatus(Action.FAILED_DOWNLOAD));
            callbackInfo.cancel();
        }

    }

    @Inject(
        method = { "handleJoinGame"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void handleJoinGameWithAntiForge(SPacketJoinGame packetIn, CallbackInfo callbackInfo) {
        if (AntiForge.enabled && AntiForge.blockFML && !Minecraft.getMinecraft().isIntegratedServerRunning()) {
            PacketThreadUtil.checkThreadAndEnqueue(packetIn, (NetHandlerPlayClient) this, this.client);
            this.client.playerController = new PlayerControllerMP(this.client, (NetHandlerPlayClient) this);
            this.world = new WorldClient((NetHandlerPlayClient) this, new WorldSettings(0L, packetIn.getGameType(), false, packetIn.isHardcoreMode(), packetIn.getWorldType()), packetIn.getDimension(), packetIn.getDifficulty(), this.client.profiler);
            this.client.gameSettings.difficulty = packetIn.getDifficulty();
            this.client.loadWorld(this.world);
            this.client.player.dimension = packetIn.getDimension();
            this.client.displayGuiScreen(new GuiDownloadTerrain());
            this.client.player.setEntityId(packetIn.getPlayerId());
            this.currentServerMaxPlayers = packetIn.getMaxPlayers();
            this.client.player.setReducedDebug(packetIn.isReducedDebugInfo());
            this.client.playerController.setGameType(packetIn.getGameType());
            this.client.gameSettings.sendSettingsToServer();
            this.netManager.sendPacket(new CPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
            callbackInfo.cancel();
        }
    }

    @Inject(
        method = { "handleEntityMovement"},
        at = {             @At(
                value = "FIELD",
                target = "Lnet/minecraft/entity/Entity;onGround:Z"
            )}
    )
    private void handleEntityMovementEvent(SPacketEntity packetIn, CallbackInfo callbackInfo) {
        Entity entity = packetIn.getEntity(this.world);

        if (entity != null) {
            LiquidBounce.eventManager.callEvent(new EntityMovementEvent(EntityImplKt.wrap(entity)));
        }

    }
}
