package net.ccbluex.liquidbounce.features.command;

import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0005\"\u00020\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003H\u0004J\u001b\u0010\u000f\u001a\u00020\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0004¢\u0006\u0002\u0010\u0011J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0003H\u0004J\b\u0010\u0013\u001a\u00020\rH\u0004J\u001b\u0010\u0014\u001a\u00020\r2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H&¢\u0006\u0002\u0010\u0011J\b\u0010\u0016\u001a\u00020\rH\u0004J!\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u00182\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0016¢\u0006\u0002\u0010\u0019R\u001b\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0005¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001a"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/Command;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "command", "", "alias", "", "(Ljava/lang/String;[Ljava/lang/String;)V", "getAlias", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getCommand", "()Ljava/lang/String;", "chat", "", "msg", "chatSyntax", "syntaxes", "([Ljava/lang/String;)V", "syntax", "chatSyntaxError", "execute", "args", "playEdit", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public abstract class Command extends MinecraftInstance {

    @NotNull
    private final String command;
    @NotNull
    private final String[] alias;

    public abstract void execute(@NotNull String[] astring);

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        return CollectionsKt.emptyList();
    }

    protected final void chat(@NotNull String msg) {
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        ClientUtils.displayChatMessage("§8[§9§lTGSense§8] §3" + msg);
    }

    protected final void chatSyntax(@NotNull String syntax) {
        Intrinsics.checkParameterIsNotNull(syntax, "syntax");
        ClientUtils.displayChatMessage("§8[§9§lTGSense§8] §3Syntax: §7" + LiquidBounce.INSTANCE.getCommandManager().getPrefix() + syntax);
    }

    protected final void chatSyntax(@NotNull String[] syntaxes) {
        Intrinsics.checkParameterIsNotNull(syntaxes, "syntaxes");
        ClientUtils.displayChatMessage("§8[§9§lTGSense§8] §3Syntax:");
        String[] astring = syntaxes;
        int i = syntaxes.length;

        for (int j = 0; j < i; ++j) {
            String syntax = astring[j];
            StringBuilder stringbuilder = (new StringBuilder()).append("§8> §7").append(LiquidBounce.INSTANCE.getCommandManager().getPrefix()).append(this.command).append(' ');
            boolean flag = false;

            if (syntax == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String s = syntax.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
            String s1 = s;

            ClientUtils.displayChatMessage(stringbuilder.append(s1).toString());
        }

    }

    protected final void chatSyntaxError() {
        ClientUtils.displayChatMessage("§8[§9§lTGSense§8] §3Syntax error");
    }

    protected final void playEdit() {
        MinecraftInstance.mc.getSoundHandler().playSound("random.anvil_use", 1.0F);
    }

    @NotNull
    public final String getCommand() {
        return this.command;
    }

    @NotNull
    public final String[] getAlias() {
        return this.alias;
    }

    public Command(@NotNull String command, @NotNull String... alias) {
        Intrinsics.checkParameterIsNotNull(command, "command");
        Intrinsics.checkParameterIsNotNull(alias, "alias");
        super();
        this.command = command;
        this.alias = alias;
    }
}
