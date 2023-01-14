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
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¢\u0006\u0002\u0010\fJ!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000e2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¢\u0006\u0002\u0010\u000fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/special/ChatAdminCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "lChat", "Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat;", "getLChat", "()Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat;", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class ChatAdminCommand extends Command {

    @NotNull
    private final LiquidChat lChat;

    @NotNull
    public final LiquidChat getLChat() {
        return this.lChat;
    }

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (!this.lChat.getState()) {
            this.chat("§cError: §7LiquidChat is disabled!");
        } else {
            if (args.length > 1) {
                if (StringsKt.equals(args[1], "ban", true)) {
                    if (args.length > 2) {
                        this.lChat.getClient().banUser(args[2]);
                    } else {
                        this.chatSyntax("chatadmin ban <username>");
                    }
                } else if (StringsKt.equals(args[1], "unban", true)) {
                    if (args.length > 2) {
                        this.lChat.getClient().unbanUser(args[2]);
                    } else {
                        this.chatSyntax("chatadmin unban <username>");
                    }
                }
            } else {
                this.chatSyntax("chatadmin <ban/unban>");
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
            List list;

            switch (args.length) {
            case 1:
                String[] $this$filter$iv = new String[] { "ban", "unban"};

                $i$f$filter = false;
                Collection destination$iv$iv = (Collection) (new ArrayList($this$filter$iv.length));
                boolean $i$f$filterTo = false;
                String[] astring = $this$filter$iv;
                int element$iv$iv = $this$filter$iv.length;

                for (int it = 0; it < element$iv$iv; ++it) {
                    String $i$a$-filter-ChatAdminCommand$tabComplete$2 = astring[it];
                    boolean $i$a$-map-ChatAdminCommand$tabComplete$1 = false;
                    boolean flag = false;

                    if ($i$a$-filter-ChatAdminCommand$tabComplete$2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    String s = $i$a$-filter-ChatAdminCommand$tabComplete$2.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                    String s1 = s;

                    destination$iv$iv.add(s1);
                }

                Iterable iterable = (Iterable) ((List) destination$iv$iv);

                $i$f$filter = false;
                destination$iv$iv = (Collection) (new ArrayList());
                $i$f$filterTo = false;
                Iterator iterator = iterable.iterator();

                while (iterator.hasNext()) {
                    Object object = iterator.next();
                    String s2 = (String) object;
                    boolean flag1 = false;

                    if (StringsKt.startsWith(s2, args[0], true)) {
                        destination$iv$iv.add(object);
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

    public ChatAdminCommand() {
        super("chatadmin", new String[0]);
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(LiquidChat.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat");
        } else {
            this.lChat = (LiquidChat) module;
        }
    }
}
