package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/TargetCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class TargetCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            if (StringsKt.equals(args[1], "players", true)) {
                EntityUtils.targetPlayer = !EntityUtils.targetPlayer;
                this.chat("§7Target player toggled " + (EntityUtils.targetPlayer ? "on" : "off") + '.');
                this.playEdit();
                return;
            }

            if (StringsKt.equals(args[1], "mobs", true)) {
                EntityUtils.targetMobs = !EntityUtils.targetMobs;
                this.chat("§7Target mobs toggled " + (EntityUtils.targetMobs ? "on" : "off") + '.');
                this.playEdit();
                return;
            }

            if (StringsKt.equals(args[1], "animals", true)) {
                EntityUtils.targetAnimals = !EntityUtils.targetAnimals;
                this.chat("§7Target animals toggled " + (EntityUtils.targetAnimals ? "on" : "off") + '.');
                this.playEdit();
                return;
            }

            if (StringsKt.equals(args[1], "invisible", true)) {
                EntityUtils.targetInvisible = !EntityUtils.targetInvisible;
                this.chat("§7Target Invisible toggled " + (EntityUtils.targetInvisible ? "on" : "off") + '.');
                this.playEdit();
                return;
            }
        }

        this.chatSyntax("target <players/mobs/animals/invisible>");
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean $i$f$filter = false;

        if (args.length == 0) {
            return CollectionsKt.emptyList();
        } else {
            List list;

            switch (args.length) {
            case 1:
                Iterable $this$filter$iv = (Iterable) CollectionsKt.listOf(new String[] { "players", "mobs", "animals", "invisible"});

                $i$f$filter = false;
                Collection destination$iv$iv = (Collection) (new ArrayList());
                boolean $i$f$filterTo = false;
                Iterator iterator = $this$filter$iv.iterator();

                while (iterator.hasNext()) {
                    Object element$iv$iv = iterator.next();
                    String it = (String) element$iv$iv;
                    boolean $i$a$-filter-TargetCommand$tabComplete$1 = false;

                    if (StringsKt.startsWith(it, args[0], true)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }

                list = (List) destination$iv$iv;
                break;

            default:
                list = CollectionsKt.emptyList();
            }

            return list;
        }
    }

    public TargetCommand() {
        super("target", new String[0]);
    }
}
