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
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/EnchantCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class EnchantCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length <= 2) {
            this.chatSyntax("enchant <type> [level]");
        } else if (MinecraftInstance.mc.getPlayerController().isNotCreative()) {
            this.chat("§c§lError: §3You need to be in creative mode.");
        } else {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();
            IItemStack item = ientityplayersp != null ? ientityplayersp.getHeldItem() : null;

            if ((item != null ? item.getItem() : null) == null) {
                this.chat("§c§lError: §3You need to hold an item.");
            } else {
                int enchantment;

                try {
                    String enchantment2 = args[1];
                    boolean level = false;

                    enchantment = Integer.parseInt(enchantment2);
                } catch (NumberFormatException numberformatexception) {
                    IEnchantment enchantment1 = MinecraftInstance.functions.getEnchantmentByLocation(args[1]);

                    if (enchantment1 == null) {
                        this.chat("There is no enchantment with the name \'" + args[1] + '\'');
                        return;
                    }

                    enchantment = enchantment1.getEffectId();
                }

                int enchantID = enchantment;
                IEnchantment enchantment3 = MinecraftInstance.functions.getEnchantmentById(enchantment);

                if (enchantment3 == null) {
                    this.chat("There is no enchantment with the ID \'" + enchantID + '\'');
                } else {
                    int enchantment5;

                    try {
                        String enchantment4 = args[2];
                        boolean e = false;

                        enchantment5 = Integer.parseInt(enchantment4);
                    } catch (NumberFormatException numberformatexception1) {
                        this.chatSyntaxError();
                        return;
                    }

                    item.addEnchantment(enchantment3, enchantment5);
                    IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    IClassProvider iclassprovider = MinecraftInstance.classProvider;
                    IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue(iclassprovider.createCPacketCreativeInventoryAction(36 + ientityplayersp1.getInventory().getCurrentItem(), item));
                    this.chat(enchantment3.getTranslatedName(enchantment5) + " added to " + item.getDisplayName() + '.');
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
                Iterable $this$filter$iv = (Iterable) MinecraftInstance.functions.getEnchantments();

                $i$f$filter = false;
                Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv, 10)));
                boolean $i$f$filterTo = false;
                Iterator iterator = $this$filter$iv.iterator();

                Object element$iv$iv;
                boolean $i$a$-filter-EnchantCommand$tabComplete$2;

                while (iterator.hasNext()) {
                    element$iv$iv = iterator.next();
                    IResourceLocation it = (IResourceLocation) element$iv$iv;

                    $i$a$-filter-EnchantCommand$tabComplete$2 = false;
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

                    $i$a$-filter-EnchantCommand$tabComplete$2 = false;
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

    public EnchantCommand() {
        super("enchant", new String[0]);
    }
}
