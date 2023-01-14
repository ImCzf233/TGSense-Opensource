package net.ccbluex.liquidbounce.injection.forge.mixins.packets;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({ C00Handshake.class})
public class MixinC00Handshake {

    @Shadow
    public int port;
    @Shadow
    public String ip;
    @Shadow
    private int protocolVersion;
    @Shadow
    private EnumConnectionState requestedState;

    @Overwrite
    public void writePacketData(PacketBuffer buf) {
        buf.writeVarInt(this.protocolVersion);
        buf.writeString(this.ip + "\u0000FML\u0000");
        buf.writeShort(this.port);
        buf.writeVarInt(this.requestedState.getId());
    }
}
