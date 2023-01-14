package net.ccbluex.liquidbounce.features.command.commands;

import java.util.Comparator;
import java.util.List;
import joptsimple.internal.Strings;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/HelpCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"}
)
public final class HelpCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        int page = 1;

        if (args.length > 1) {
            try {
                String maxPageDouble = args[1];
                boolean flag = false;

                page = Integer.parseInt(maxPageDouble);
            } catch (NumberFormatException numberformatexception) {
                this.chatSyntaxError();
            }
        }

        if (page <= 0) {
            this.chat("The number you have entered is too low, it must be over 0");
        } else {
            double d0 = (double) LiquidBounce.INSTANCE.getCommandManager().getCommands().size() / 8.0D;
            int maxPage = d0 > (double) ((int) d0) ? (int) d0 + 1 : (int) d0;

            if (page > maxPage) {
                this.chat("The number you have entered is too big, it must be under " + maxPage + '.');
            } else {
                this.chat("§c§lHelp");
                ClientUtils.displayChatMessage("§7> Page: §8" + page + " / " + maxPage);
                Iterable i = (Iterable) LiquidBounce.INSTANCE.getCommandManager().getCommands();
                boolean command = false;
                boolean flag1 = false;
                Comparator comparator = (Comparator) (new HelpCommand$execute$$inlined$sortedBy$1());
                List commands = CollectionsKt.sortedWith(i, comparator);

                for (int i = 8 * (page - 1); i < 8 * page && i < commands.size(); ++i) {
                    Command command = (Command) commands.get(i);
                    StringBuilder stringbuilder = (new StringBuilder()).append("§6> §7").append(LiquidBounce.INSTANCE.getCommandManager().getPrefix()).append(command.getCommand());
                    String[] astring = command.getAlias();
                    StringBuilder stringbuilder1 = stringbuilder;

                    flag1 = false;
                    boolean flag2 = astring.length == 0;

                    ClientUtils.displayChatMessage(stringbuilder1.append(flag2 ? "" : " §7(§8" + Strings.join(command.getAlias(), "§7, §8") + "§7)").toString());
                }

                ClientUtils.displayChatMessage("§a------------\n§7> §c" + LiquidBounce.INSTANCE.getCommandManager().getPrefix() + "help §8<§7§lpage§8>");
            }
        }
    }

    public HelpCommand() {
        super("help", new String[0]);
    }
}
