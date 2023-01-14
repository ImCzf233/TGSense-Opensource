package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/RenameCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"}
)
public final class RenameCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            if (MinecraftInstance.mc.getPlayerController().isNotCreative()) {
                this.chat("§c§lError: §3You need to be in creative mode.");
            } else {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IItemStack item = ientityplayersp.getHeldItem();

                if ((item != null ? item.getItem() : null) == null) {
                    this.chat("§c§lError: §3You need to hold a item.");
                } else {
                    String s = StringUtils.toCompleteString(args, 1);

                    Intrinsics.checkExpressionValueIsNotNull(s, "StringUtils.toCompleteString(args, 1)");
                    item.setStackDisplayName(ColorUtils.translateAlternateColorCodes(s));
                    IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    IClassProvider iclassprovider = MinecraftInstance.classProvider;
                    IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue(iclassprovider.createCPacketCreativeInventoryAction(36 + ientityplayersp1.getInventory().getCurrentItem(), item));
                    this.chat("§3Item renamed to \'" + item.getDisplayName() + "§3\'");
                }
            }
        } else {
            this.chatSyntax("rename <name>");
        }
    }

    public RenameCommand() {
        super("rename", new String[0]);
    }
}
