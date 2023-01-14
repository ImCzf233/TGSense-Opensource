package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/HurtCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"}
)
public final class HurtCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        int damage = 1;

        if (args.length > 1) {
            try {
                String thePlayer = args[1];
                boolean x = false;

                damage = Integer.parseInt(thePlayer);
            } catch (NumberFormatException numberformatexception) {
                this.chatSyntaxError();
                return;
            }
        }

        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP ientityplayersp1 = ientityplayersp;
            double d0 = ientityplayersp1.getPosX();
            double y = ientityplayersp1.getPosY();
            double z = ientityplayersp1.getPosZ();
            int i = 0;

            for (int i = 65 * damage; i < i; ++i) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(d0, y + 0.049D, z, false));
                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(d0, y, z, false));
            }

            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(d0, y, z, true));
            this.chat("You were damaged.");
        }
    }

    public HurtCommand() {
        super("hurt", new String[0]);
    }
}
