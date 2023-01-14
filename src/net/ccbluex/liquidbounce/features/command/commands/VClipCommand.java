package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/VClipCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"}
)
public final class VClipCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            try {
                String thePlayer = args[1];
                boolean entity = false;
                double ex = Double.parseDouble(thePlayer);
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    return;
                }

                IEntityPlayerSP thePlayer1 = ientityplayersp;
                IEntity ientity;

                if (thePlayer1.isRiding()) {
                    ientity = thePlayer1.getRidingEntity();
                    if (ientity == null) {
                        Intrinsics.throwNpe();
                    }
                } else {
                    ientity = (IEntity) thePlayer1;
                }

                IEntity entity1 = ientity;

                entity1.setPosition(entity1.getPosX(), entity1.getPosY() + ex, entity1.getPosZ());
                this.chat("You were teleported.");
            } catch (NumberFormatException numberformatexception) {
                this.chatSyntaxError();
            }

        } else {
            this.chatSyntax("vclip <value>");
        }
    }

    public VClipCommand() {
        super("vclip", new String[0]);
    }
}
