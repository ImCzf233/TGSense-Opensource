package net.ccbluex.liquidbounce.features.command.special;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¢\u0006\u0002\u0010\fJ!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¢\u0006\u0002\u0010\u000fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/special/XrayCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "xRay", "Lnet/ccbluex/liquidbounce/features/module/modules/render/XRay;", "getXRay", "()Lnet/ccbluex/liquidbounce/features/module/modules/render/XRay;", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class XrayCommand extends Command {

    @NotNull
    private final XRay xRay;

    @NotNull
    public final XRay getXRay() {
        return this.xRay;
    }

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            IExtractedFunctions iextractedfunctions;
            int i;
            IBlock $i$f$forEach1;
            String $i$f$forEach2;
            boolean exception1;
            IBlock element$iv1;

            if (StringsKt.equals(args[1], "add", true)) {
                if (args.length > 2) {
                    try {
                        try {
                            $i$f$forEach2 = args[2];
                            iextractedfunctions = MinecraftInstance.functions;
                            exception1 = false;
                            i = Integer.parseInt($i$f$forEach2);
                            $i$f$forEach1 = iextractedfunctions.getBlockById(i);
                        } catch (NumberFormatException numberformatexception) {
                            element$iv1 = MinecraftInstance.functions.getBlockFromName(args[2]);
                            if (element$iv1 == null || MinecraftInstance.functions.getIdFromBlock(element$iv1) <= 0) {
                                this.chat("§7Block §8" + args[2] + "§7 does not exist!");
                                return;
                            }

                            $i$f$forEach1 = element$iv1;
                        }

                        if ($i$f$forEach1 == null || this.xRay.getXrayBlocks().contains($i$f$forEach1)) {
                            this.chat("This block is already on the list.");
                            return;
                        }

                        this.xRay.getXrayBlocks().add($i$f$forEach1);
                        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().xrayConfig);
                        this.chat("§7Added block §8" + $i$f$forEach1.getLocalizedName() + "§7.");
                        this.playEdit();
                    } catch (NumberFormatException numberformatexception1) {
                        this.chatSyntaxError();
                    }

                    return;
                }

                this.chatSyntax("xray add <block_id>");
                return;
            }

            if (StringsKt.equals(args[1], "remove", true)) {
                if (args.length > 2) {
                    try {
                        try {
                            $i$f$forEach2 = args[2];
                            iextractedfunctions = MinecraftInstance.functions;
                            exception1 = false;
                            i = Integer.parseInt($i$f$forEach2);
                            $i$f$forEach1 = iextractedfunctions.getBlockById(i);
                        } catch (NumberFormatException numberformatexception2) {
                            element$iv1 = MinecraftInstance.functions.getBlockFromName(args[2]);
                            if (element$iv1 == null || MinecraftInstance.functions.getIdFromBlock(element$iv1) <= 0) {
                                this.chat("§7Block §8" + args[2] + "§7 does not exist!");
                                return;
                            }

                            $i$f$forEach1 = element$iv1;
                        }

                        if ($i$f$forEach1 == null || !this.xRay.getXrayBlocks().contains($i$f$forEach1)) {
                            this.chat("This block is not on the list.");
                            return;
                        }

                        this.xRay.getXrayBlocks().remove($i$f$forEach1);
                        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().xrayConfig);
                        this.chat("§7Removed block §8" + $i$f$forEach1.getLocalizedName() + "§7.");
                        this.playEdit();
                    } catch (NumberFormatException numberformatexception3) {
                        this.chatSyntaxError();
                    }

                    return;
                }

                this.chatSyntax("xray remove <block_id>");
                return;
            }

            if (StringsKt.equals(args[1], "list", true)) {
                this.chat("§8Xray blocks:");
                Iterable $this$forEach$iv = (Iterable) this.xRay.getXrayBlocks();
                boolean $i$f$forEach = false;
                Iterator exception = $this$forEach$iv.iterator();

                while (exception.hasNext()) {
                    Object element$iv = exception.next();
                    IBlock it = (IBlock) element$iv;
                    boolean $i$a$-forEach-XrayCommand$execute$1 = false;

                    this.chat("§8" + it.getLocalizedName() + " §7-§c " + MinecraftInstance.functions.getIdFromBlock(it));
                }

                return;
            }
        }

        this.chatSyntax("xray <add, remove, list>");
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean $this$filter$iv1 = false;

        if (args.length == 0) {
            return CollectionsKt.emptyList();
        } else {
            String s;
            String s1;
            List list;
            String s2;

            switch (args.length) {
            case 1:
                String[] astring = new String[] { "add", "remove", "list"};

                $this$filter$iv1 = false;
                Collection $this$filterTo$iv$iv = (Collection) (new ArrayList(astring.length));
                boolean flag = false;
                String[] astring1 = astring;
                int i = astring.length;

                for (int j = 0; j < i; ++j) {
                    s1 = astring1[j];
                    boolean flag1 = false;
                    boolean flag2 = false;

                    if (s1 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s2 = s1.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(s2, "(this as java.lang.String).toLowerCase()");
                    s = s2;
                    $this$filterTo$iv$iv.add(s);
                }

                Iterable iterable = (Iterable) ((List) $this$filterTo$iv$iv);

                $this$filter$iv1 = false;
                $this$filterTo$iv$iv = (Collection) (new ArrayList());
                flag = false;
                Iterator iterator = iterable.iterator();

                while (iterator.hasNext()) {
                    Object object = iterator.next();
                    String s3 = (String) object;
                    boolean flag3 = false;

                    if (StringsKt.startsWith(s3, args[0], true)) {
                        $this$filterTo$iv$iv.add(object);
                    }
                }

                list = (List) $this$filterTo$iv$iv;
                break;

            case 2:
                String $this$filter$iv = args[0];

                $this$filter$iv1 = false;
                if ($this$filter$iv == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                s2 = $this$filter$iv.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(s2, "(this as java.lang.String).toLowerCase()");
                $this$filter$iv = s2;
                boolean $i$f$filter;
                Collection destination$iv$iv;
                boolean $i$f$filterTo;
                Iterator element$iv$iv;
                Object element$iv$iv1;
                IResourceLocation it;
                boolean $i$a$-filter-XrayCommand$tabComplete$9;
                String $i$a$-map-XrayCommand$tabComplete$1;
                boolean flag4;
                Iterable iterable1;

                switch ($this$filter$iv.hashCode()) {
                case -934610812:
                    if ($this$filter$iv.equals("remove")) {
                        iterable1 = (Iterable) MinecraftInstance.functions.getBlockRegistryKeys();
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable1, 10)));
                        $i$f$filterTo = false;
                        element$iv$iv = iterable1.iterator();

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            it = (IResourceLocation) element$iv$iv1;
                            $i$a$-filter-XrayCommand$tabComplete$9 = false;
                            $i$a$-map-XrayCommand$tabComplete$1 = it.getResourcePath();
                            flag4 = false;
                            if ($i$a$-map-XrayCommand$tabComplete$1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s2 = $i$a$-map-XrayCommand$tabComplete$1.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s2, "(this as java.lang.String).toLowerCase()");
                            s = s2;
                            destination$iv$iv.add(s);
                        }

                        iterable1 = (Iterable) ((List) destination$iv$iv);
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList());
                        $i$f$filterTo = false;
                        element$iv$iv = iterable1.iterator();

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            s1 = (String) element$iv$iv1;
                            $i$a$-filter-XrayCommand$tabComplete$9 = false;
                            if (CollectionsKt.contains((Iterable) this.xRay.getXrayBlocks(), MinecraftInstance.functions.getBlockFromName(s1))) {
                                destination$iv$iv.add(element$iv$iv1);
                            }
                        }

                        iterable1 = (Iterable) ((List) destination$iv$iv);
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList());
                        $i$f$filterTo = false;
                        element$iv$iv = iterable1.iterator();

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            s1 = (String) element$iv$iv1;
                            $i$a$-filter-XrayCommand$tabComplete$9 = false;
                            if (StringsKt.startsWith(s1, args[1], true)) {
                                destination$iv$iv.add(element$iv$iv1);
                            }
                        }

                        return (List) destination$iv$iv;
                    }
                    break;

                case 96417:
                    if ($this$filter$iv.equals("add")) {
                        iterable1 = (Iterable) MinecraftInstance.functions.getBlockRegistryKeys();
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable1, 10)));
                        $i$f$filterTo = false;
                        element$iv$iv = iterable1.iterator();

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            it = (IResourceLocation) element$iv$iv1;
                            $i$a$-filter-XrayCommand$tabComplete$9 = false;
                            $i$a$-map-XrayCommand$tabComplete$1 = it.getResourcePath();
                            flag4 = false;
                            if ($i$a$-map-XrayCommand$tabComplete$1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s2 = $i$a$-map-XrayCommand$tabComplete$1.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s2, "(this as java.lang.String).toLowerCase()");
                            s = s2;
                            destination$iv$iv.add(s);
                        }

                        iterable1 = (Iterable) ((List) destination$iv$iv);
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList());
                        $i$f$filterTo = false;
                        element$iv$iv = iterable1.iterator();

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            s1 = (String) element$iv$iv1;
                            $i$a$-filter-XrayCommand$tabComplete$9 = false;
                            flag4 = false;
                            if (s1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s2 = s1.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s2, "(this as java.lang.String).toLowerCase()");
                            if (Block.getBlockFromName(s2) != null) {
                                destination$iv$iv.add(element$iv$iv1);
                            }
                        }

                        iterable1 = (Iterable) ((List) destination$iv$iv);
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList());
                        $i$f$filterTo = false;
                        element$iv$iv = iterable1.iterator();

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            s1 = (String) element$iv$iv1;
                            $i$a$-filter-XrayCommand$tabComplete$9 = false;
                            Iterable iterable2 = (Iterable) this.xRay.getXrayBlocks();
                            IExtractedFunctions iextractedfunctions = MinecraftInstance.functions;
                            Iterable iterable3 = iterable2;
                            boolean flag5 = false;

                            if (s1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s2 = s1.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s2, "(this as java.lang.String).toLowerCase()");
                            String s4 = s2;

                            if (!CollectionsKt.contains(iterable3, iextractedfunctions.getBlockFromName(s4))) {
                                destination$iv$iv.add(element$iv$iv1);
                            }
                        }

                        iterable1 = (Iterable) ((List) destination$iv$iv);
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList());
                        $i$f$filterTo = false;
                        element$iv$iv = iterable1.iterator();

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            s1 = (String) element$iv$iv1;
                            $i$a$-filter-XrayCommand$tabComplete$9 = false;
                            if (StringsKt.startsWith(s1, args[1], true)) {
                                destination$iv$iv.add(element$iv$iv1);
                            }
                        }

                        return (List) destination$iv$iv;
                    }
                }

                list = CollectionsKt.emptyList();
                break;

            default:
                list = CollectionsKt.emptyList();
            }

            return list;
        }
    }

    public XrayCommand() {
        super("xray", new String[0]);
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(XRay.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.XRay");
        } else {
            this.xRay = (XRay) module;
        }
    }
}
