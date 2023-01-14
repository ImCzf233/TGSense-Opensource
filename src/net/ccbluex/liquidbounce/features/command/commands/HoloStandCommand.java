package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTBase;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/HoloStandCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"}
)
public final class HoloStandCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 4) {
            if (MinecraftInstance.mc.getPlayerController().isNotCreative()) {
                this.chat("§c§lError: §3You need to be in creative mode.");
            } else {
                try {
                    String y = args[1];
                    boolean flag = false;
                    double exception = Double.parseDouble(y);
                    String z = args[2];
                    boolean flag1 = false;
                    double y1 = Double.parseDouble(z);
                    String message = args[3];
                    boolean itemStack = false;
                    double z1 = Double.parseDouble(message);

                    message = StringUtils.toCompleteString(args, 4);
                    IItemStack itemStack1 = MinecraftInstance.classProvider.createItemStack(MinecraftInstance.classProvider.getItemEnum(ItemType.ARMOR_STAND));
                    INBTTagCompound base = MinecraftInstance.classProvider.createNBTTagCompound();
                    INBTTagCompound entityTag = MinecraftInstance.classProvider.createNBTTagCompound();

                    entityTag.setInteger("Invisible", 1);
                    Intrinsics.checkExpressionValueIsNotNull(message, "message");
                    entityTag.setString("CustomName", message);
                    entityTag.setInteger("CustomNameVisible", 1);
                    entityTag.setInteger("NoGravity", 1);
                    INBTTagList position = MinecraftInstance.classProvider.createNBTTagList();

                    position.appendTag((INBTBase) MinecraftInstance.classProvider.createNBTTagDouble(exception));
                    position.appendTag((INBTBase) MinecraftInstance.classProvider.createNBTTagDouble(y1));
                    position.appendTag((INBTBase) MinecraftInstance.classProvider.createNBTTagDouble(z1));
                    entityTag.setTag("Pos", (INBTBase) position);
                    base.setTag("EntityTag", (INBTBase) entityTag);
                    itemStack1.setTagCompound(base);
                    itemStack1.setStackDisplayName("§c§lHolo§eStand");
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketCreativeInventoryAction(36, itemStack1));
                    this.chat("The HoloStand was successfully added to your inventory.");
                } catch (NumberFormatException numberformatexception) {
                    this.chatSyntaxError();
                }

            }
        } else {
            this.chatSyntax("holostand <x> <y> <z> <message...>");
        }
    }

    public HoloStandCommand() {
        super("holostand", new String[0]);
    }
}
