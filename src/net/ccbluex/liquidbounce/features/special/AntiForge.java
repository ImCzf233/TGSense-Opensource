package net.ccbluex.liquidbounce.features.special;

import io.netty.buffer.Unpooled;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

public class AntiForge extends MinecraftInstance implements Listenable {

    public static boolean enabled = true;
    public static boolean blockFML = true;
    public static boolean blockProxyPacket = true;
    public static boolean blockPayloadPackets = true;

    @EventTarget
    public void onPacket(PacketEvent event) {
        IPacket packet = event.getPacket();

        if (AntiForge.enabled && !AntiForge.mc.isIntegratedServerRunning()) {
            try {
                if (AntiForge.blockProxyPacket && packet.getClass().getName().equals("net.minecraftforge.fml.common.network.internal.FMLProxyPacket")) {
                    event.cancelEvent();
                }

                if (AntiForge.blockPayloadPackets && AntiForge.classProvider.isCPacketCustomPayload(packet)) {
                    ICPacketCustomPayload e = packet.asCPacketCustomPayload();

                    if (!e.getChannelName().startsWith("MC|")) {
                        event.cancelEvent();
                    } else if (e.getChannelName().equalsIgnoreCase("MC|Brand")) {
                        e.setData(AntiForge.classProvider.createPacketBuffer(Unpooled.buffer()).writeString("vanilla"));
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    public boolean handleEvents() {
        return true;
    }
}
