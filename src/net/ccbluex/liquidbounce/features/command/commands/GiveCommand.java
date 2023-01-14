package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/GiveCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class GiveCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (MinecraftInstance.mc.getPlayerController().isNotCreative()) {
                this.chat("§c§lError: §3You need to be in creative mode.");
            } else if (args.length <= 1) {
                this.chatSyntax("give <item> [amount] [data] [datatag]");
            } else {
                IItemStack itemStack = ItemUtils.createItem(StringUtils.toCompleteString(args, 1));

                if (itemStack == null) {
                    this.chatSyntaxError();
                } else {
                    int emptySlot = -1;
                    int i = 36;

                    byte b0;

                    for (b0 = 44; i <= b0; ++i) {
                        if (thePlayer.getInventoryContainer().getSlot(i).getStack() == null) {
                            emptySlot = i;
                            break;
                        }
                    }

                    if (emptySlot == -1) {
                        i = 9;

                        for (b0 = 44; i <= b0; ++i) {
                            if (thePlayer.getInventoryContainer().getSlot(i).getStack() == null) {
                                emptySlot = i;
                                break;
                            }
                        }
                    }

                    if (emptySlot != -1) {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketCreativeInventoryAction(emptySlot, itemStack));
                        this.chat("§7Given [§8" + itemStack.getDisplayName() + "§7] * §8" + itemStack.getStackSize() + "§7 to §8" + MinecraftInstance.mc.getSession().getUsername() + "§7.");
                    } else {
                        this.chat("Your inventory is full.");
                    }

                }
            }
        }
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean $i$f$filter = false;

        if (args.length == 0) {
            return CollectionsKt.emptyList();
        } else {
            switch (args.length) {
            case 1:
                Iterable $this$filter$iv = (Iterable) MinecraftInstance.functions.getItemRegistryKeys();

                $i$f$filter = false;
                Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv, 10)));
                boolean $i$f$filterTo = false;
                Iterator iterator = $this$filter$iv.iterator();

                Object element$iv$iv;
                boolean $i$a$-filter-GiveCommand$tabComplete$2;

                while (iterator.hasNext()) {
                    element$iv$iv = iterator.next();
                    IResourceLocation it = (IResourceLocation) element$iv$iv;

                    $i$a$-filter-GiveCommand$tabComplete$2 = false;
                    String s = it.getResourcePath();
                    boolean flag = false;

                    if (s == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    String s1 = s.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    String s2 = s1;

                    destination$iv$iv.add(s2);
                }

                $this$filter$iv = (Iterable) ((List) destination$iv$iv);
                $i$f$filter = false;
                destination$iv$iv = (Collection) (new ArrayList());
                $i$f$filterTo = false;
                iterator = $this$filter$iv.iterator();

                while (iterator.hasNext()) {
                    element$iv$iv = iterator.next();
                    String it1 = (String) element$iv$iv;

                    $i$a$-filter-GiveCommand$tabComplete$2 = false;
                    if (StringsKt.startsWith(it1, args[0], true)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }

                return (List) destination$iv$iv;

            default:
                return CollectionsKt.emptyList();
            }
        }
    }

    public GiveCommand() {
        super("give", new String[] { "item", "i", "get"});
    }
}
