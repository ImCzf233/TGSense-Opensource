package net.ccbluex.liquidbounce.features.command.special;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
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
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerRequestJWTPacket;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016¢\u0006\u0002\u0010\nJ!\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\f2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016¢\u0006\u0002\u0010\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/special/ChatTokenCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "lChat", "Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat;", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class ChatTokenCommand extends Command {

    private final LiquidChat lChat;

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            if (StringsKt.equals(args[1], "set", true)) {
                if (args.length > 2) {
                    LiquidChat.Companion liquidchat_companion = LiquidChat.Companion;
                    String s = StringUtils.toCompleteString(args, 2);

                    Intrinsics.checkExpressionValueIsNotNull(s, "StringUtils.toCompleteString(args, 2)");
                    liquidchat_companion.setJwtToken(s);
                    this.lChat.getJwtValue().set(Boolean.valueOf(true));
                    if (this.lChat.getState()) {
                        this.lChat.setState(false);
                        this.lChat.setState(true);
                    }
                } else {
                    this.chatSyntax("chattoken set <token>");
                }
            } else if (StringsKt.equals(args[1], "generate", true)) {
                if (!this.lChat.getState()) {
                    this.chat("§cError: §7LiquidChat is disabled!");
                    return;
                }

                this.lChat.getClient().sendPacket((Packet) (new ServerRequestJWTPacket()));
            } else if (StringsKt.equals(args[1], "copy", true)) {
                CharSequence stringSelection = (CharSequence) LiquidChat.Companion.getJwtToken();
                boolean flag = false;

                if (stringSelection.length() == 0) {
                    this.chat("§cError: §7No token set! Generate one first using \'" + LiquidBounce.INSTANCE.getCommandManager().getPrefix() + "chattoken generate\'.");
                    return;
                }

                StringSelection stringSelection1 = new StringSelection(LiquidChat.Companion.getJwtToken());
                Toolkit toolkit = Toolkit.getDefaultToolkit();

                Intrinsics.checkExpressionValueIsNotNull(toolkit, "Toolkit.getDefaultToolkit()");
                toolkit.getSystemClipboard().setContents((Transferable) stringSelection1, (ClipboardOwner) stringSelection1);
                this.chat("§aCopied to clipboard!");
            }
        } else {
            this.chatSyntax("chattoken <set/copy/generate>");
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
                String[] $this$filter$iv = new String[] { "set", "generate", "copy"};

                $i$f$filter = false;
                Collection destination$iv$iv = (Collection) (new ArrayList($this$filter$iv.length));
                boolean $i$f$filterTo = false;
                String[] astring = $this$filter$iv;
                int element$iv$iv = $this$filter$iv.length;

                for (int it = 0; it < element$iv$iv; ++it) {
                    String $i$a$-filter-ChatTokenCommand$tabComplete$2 = astring[it];
                    boolean $i$a$-map-ChatTokenCommand$tabComplete$1 = false;
                    boolean flag = false;

                    if ($i$a$-filter-ChatTokenCommand$tabComplete$2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    String s = $i$a$-filter-ChatTokenCommand$tabComplete$2.toLowerCase();

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

    public ChatTokenCommand() {
        super("chattoken", new String[0]);
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(LiquidChat.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat");
        } else {
            this.lChat = (LiquidChat) module;
        }
    }
}
