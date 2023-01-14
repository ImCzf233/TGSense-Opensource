package net.ccbluex.liquidbounce.features.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.commands.AutoSettingsCommand;
import net.ccbluex.liquidbounce.features.command.commands.BindCommand;
import net.ccbluex.liquidbounce.features.command.commands.BindsCommand;
import net.ccbluex.liquidbounce.features.command.commands.EnchantCommand;
import net.ccbluex.liquidbounce.features.command.commands.FriendCommand;
import net.ccbluex.liquidbounce.features.command.commands.GiveCommand;
import net.ccbluex.liquidbounce.features.command.commands.HClipCommand;
import net.ccbluex.liquidbounce.features.command.commands.HelpCommand;
import net.ccbluex.liquidbounce.features.command.commands.HideCommand;
import net.ccbluex.liquidbounce.features.command.commands.HoloStandCommand;
import net.ccbluex.liquidbounce.features.command.commands.HurtCommand;
import net.ccbluex.liquidbounce.features.command.commands.LocalAutoSettingsCommand;
import net.ccbluex.liquidbounce.features.command.commands.LoginCommand;
import net.ccbluex.liquidbounce.features.command.commands.PanicCommand;
import net.ccbluex.liquidbounce.features.command.commands.PingCommand;
import net.ccbluex.liquidbounce.features.command.commands.PrefixCommand;
import net.ccbluex.liquidbounce.features.command.commands.ReloadCommand;
import net.ccbluex.liquidbounce.features.command.commands.RemoteViewCommand;
import net.ccbluex.liquidbounce.features.command.commands.RenameCommand;
import net.ccbluex.liquidbounce.features.command.commands.SayCommand;
import net.ccbluex.liquidbounce.features.command.commands.ScriptManagerCommand;
import net.ccbluex.liquidbounce.features.command.commands.ServerInfoCommand;
import net.ccbluex.liquidbounce.features.command.commands.ShortcutCommand;
import net.ccbluex.liquidbounce.features.command.commands.TacoCommand;
import net.ccbluex.liquidbounce.features.command.commands.TargetCommand;
import net.ccbluex.liquidbounce.features.command.commands.ToggleCommand;
import net.ccbluex.liquidbounce.features.command.commands.UsernameCommand;
import net.ccbluex.liquidbounce.features.command.commands.VClipCommand;
import net.ccbluex.liquidbounce.features.command.shortcuts.Shortcut;
import net.ccbluex.liquidbounce.features.command.shortcuts.ShortcutParser;
import net.ccbluex.liquidbounce.features.command.special.ChatAdminCommand;
import net.ccbluex.liquidbounce.features.command.special.ChatTokenCommand;
import net.ccbluex.liquidbounce.features.command.special.LiquidChatCommand;
import net.ccbluex.liquidbounce.features.command.special.PrivateChatCommand;
import net.ccbluex.liquidbounce.features.command.special.XrayCommand;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\nJ\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\nJ\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001c\u001a\u00020\nJ\u001d\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u0018\u001a\u00020\nH\u0002¢\u0006\u0002\u0010\u001eJ\u000e\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u0005J\u0006\u0010!\u001a\u00020\u001aJ\u0016\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010#\u001a\u00020\nJ\u0010\u0010$\u001a\u00020\u00172\b\u0010 \u001a\u0004\u0018\u00010\u0005J\u000e\u0010%\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\nR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\"\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006&"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "", "()V", "commands", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "getCommands", "()Ljava/util/List;", "latestAutoComplete", "", "", "getLatestAutoComplete", "()[Ljava/lang/String;", "setLatestAutoComplete", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "prefix", "", "getPrefix", "()C", "setPrefix", "(C)V", "autoComplete", "", "input", "executeCommands", "", "getCommand", "name", "getCompletions", "(Ljava/lang/String;)[Ljava/lang/String;", "registerCommand", "command", "registerCommands", "registerShortcut", "script", "unregisterCommand", "unregisterShortcut", "LiquidBounce"}
)
public final class CommandManager {

    @NotNull
    private final List commands;
    @NotNull
    private String[] latestAutoComplete;
    private char prefix;

    @NotNull
    public final List getCommands() {
        return this.commands;
    }

    @NotNull
    public final String[] getLatestAutoComplete() {
        return this.latestAutoComplete;
    }

    public final void setLatestAutoComplete(@NotNull String[] <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.latestAutoComplete = <set-?>;
    }

    public final char getPrefix() {
        return this.prefix;
    }

    public final void setPrefix(char <set-?>) {
        this.prefix = <set-?>;
    }

    public final void registerCommands() {
        this.registerCommand((Command) (new BindCommand()));
        this.registerCommand((Command) (new VClipCommand()));
        this.registerCommand((Command) (new HClipCommand()));
        this.registerCommand((Command) (new HelpCommand()));
        this.registerCommand((Command) (new SayCommand()));
        this.registerCommand((Command) (new FriendCommand()));
        this.registerCommand((Command) (new AutoSettingsCommand()));
        this.registerCommand((Command) (new LocalAutoSettingsCommand()));
        this.registerCommand((Command) (new ServerInfoCommand()));
        this.registerCommand((Command) (new ToggleCommand()));
        this.registerCommand((Command) (new HurtCommand()));
        this.registerCommand((Command) (new GiveCommand()));
        this.registerCommand((Command) (new UsernameCommand()));
        this.registerCommand((Command) (new TargetCommand()));
        this.registerCommand((Command) (new TacoCommand()));
        this.registerCommand((Command) (new BindsCommand()));
        this.registerCommand((Command) (new HoloStandCommand()));
        this.registerCommand((Command) (new PanicCommand()));
        this.registerCommand((Command) (new PingCommand()));
        this.registerCommand((Command) (new RenameCommand()));
        this.registerCommand((Command) (new EnchantCommand()));
        this.registerCommand((Command) (new ReloadCommand()));
        this.registerCommand((Command) (new LoginCommand()));
        this.registerCommand((Command) (new ScriptManagerCommand()));
        this.registerCommand((Command) (new RemoteViewCommand()));
        this.registerCommand((Command) (new PrefixCommand()));
        this.registerCommand((Command) (new ShortcutCommand()));
        this.registerCommand((Command) (new HideCommand()));
        this.registerCommand((Command) (new XrayCommand()));
        this.registerCommand((Command) (new LiquidChatCommand()));
        this.registerCommand((Command) (new PrivateChatCommand()));
        this.registerCommand((Command) (new ChatTokenCommand()));
        this.registerCommand((Command) (new ChatAdminCommand()));
    }

    public final void executeCommands(@NotNull String input) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        Iterator iterator = this.commands.iterator();

        while (iterator.hasNext()) {
            Command command = (Command) iterator.next();
            Collection alias = (Collection) StringsKt.split$default((CharSequence) input, new String[] { " "}, false, 0, 6, (Object) null);
            boolean $i$f$toTypedArray = false;
            Object[] aobject = alias.toArray(new String[0]);

            if (aobject == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }

            String[] args = (String[]) aobject;

            if (StringsKt.equals(args[0], this.prefix + command.getCommand(), true)) {
                command.execute(args);
                return;
            }

            String[] thisCollection$iv = command.getAlias();
            int i = thisCollection$iv.length;

            for (int j = 0; j < i; ++j) {
                String s = thisCollection$iv[j];

                if (StringsKt.equals(args[0], this.prefix + s, true)) {
                    command.execute(args);
                    return;
                }
            }
        }

        ClientUtils.displayChatMessage("§cCommand not found. Type " + this.prefix + "help to view all commands.");
    }

    public final boolean autoComplete(@NotNull String input) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        CommandManager commandmanager = this;
        String[] astring = this.getCompletions(input);

        if (astring == null) {
            String[] astring1 = new String[0];

            commandmanager = this;
            astring = astring1;
        }

        commandmanager.latestAutoComplete = astring;
        boolean flag;

        if (StringsKt.startsWith$default((CharSequence) input, this.prefix, false, 2, (Object) null)) {
            String[] astring2 = this.latestAutoComplete;
            boolean flag1 = false;
            boolean flag2 = false;

            if (astring2.length != 0) {
                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }

    private final String[] getCompletions(String input) {
        CharSequence args = (CharSequence) input;
        boolean rawInput = false;

        if (args.length() > 0) {
            rawInput = false;
            if (input == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            char[] achar = input.toCharArray();

            Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
            if (achar[0] == this.prefix) {
                List list = StringsKt.split$default((CharSequence) input, new String[] { " "}, false, 0, 6, (Object) null);
                byte $i$f$toTypedArray;
                boolean thisCollection$iv;
                String s;
                String s1;
                Object[] aobject;
                String[] astring;

                if (list.size() > 1) {
                    String $this$toTypedArray$iv = (String) list.get(0);

                    $i$f$toTypedArray = 1;
                    thisCollection$iv = false;
                    if ($this$toTypedArray$iv == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s1 = $this$toTypedArray$iv.substring($i$f$toTypedArray);
                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).substring(startIndex)");
                    s = s1;
                    Command command = this.getCommand(s);
                    Collection collection;
                    List list1;

                    if (command != null) {
                        collection = (Collection) CollectionsKt.drop((Iterable) list, 1);
                        thisCollection$iv = false;
                        aobject = collection.toArray(new String[0]);
                        if (aobject == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                        }

                        Object[] aobject1 = aobject;

                        list1 = command.tabComplete((String[]) aobject1);
                    } else {
                        list1 = null;
                    }

                    List list2 = list1;

                    if (list2 != null) {
                        collection = (Collection) list2;
                        thisCollection$iv = false;
                        aobject = collection.toArray(new String[0]);
                        if (aobject == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                        }

                        astring = (String[]) aobject;
                    } else {
                        astring = null;
                    }
                } else {
                    $i$f$toTypedArray = 1;
                    thisCollection$iv = false;
                    if (input == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s1 = input.substring($i$f$toTypedArray);
                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).substring(startIndex)");
                    String s2 = s1;
                    Iterable iterable = (Iterable) this.commands;
                    boolean flag = false;
                    Collection destination$iv$iv = (Collection) (new ArrayList());
                    boolean $i$f$mapTo = false;
                    Iterator iterator = iterable.iterator();

                    Object item$iv$iv;
                    Command it;
                    boolean $i$a$-map-CommandManager$getCompletions$2;
                    String[] $this$first$iv;
                    boolean $i$f$first;
                    String[] astring1;
                    int i;
                    int j;
                    String element$iv;
                    boolean $i$a$-first-CommandManager$getCompletions$2$alias$1;

                    while (iterator.hasNext()) {
                        boolean flag1;
                        label101: {
                            item$iv$iv = iterator.next();
                            it = (Command) item$iv$iv;
                            $i$a$-map-CommandManager$getCompletions$2 = false;
                            if (!StringsKt.startsWith(it.getCommand(), s2, true)) {
                                $this$first$iv = it.getAlias();
                                $i$f$first = false;
                                astring1 = $this$first$iv;
                                i = $this$first$iv.length;
                                j = 0;

                                while (true) {
                                    if (j >= i) {
                                        flag1 = false;
                                        break;
                                    }

                                    element$iv = astring1[j];
                                    $i$a$-first-CommandManager$getCompletions$2$alias$1 = false;
                                    if (StringsKt.startsWith(element$iv, s2, true)) {
                                        flag1 = true;
                                        break;
                                    }

                                    ++j;
                                }

                                if (!flag1) {
                                    flag1 = false;
                                    break label101;
                                }
                            }

                            flag1 = true;
                        }

                        if (flag1) {
                            destination$iv$iv.add(item$iv$iv);
                        }
                    }

                    iterable = (Iterable) ((List) destination$iv$iv);
                    flag = false;
                    destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10)));
                    $i$f$mapTo = false;
                    iterator = iterable.iterator();

                    while (iterator.hasNext()) {
                        item$iv$iv = iterator.next();
                        it = (Command) item$iv$iv;
                        $i$a$-map-CommandManager$getCompletions$2 = false;
                        if (StringsKt.startsWith(it.getCommand(), s2, true)) {
                            s1 = it.getCommand();
                        } else {
                            $this$first$iv = it.getAlias();
                            $i$f$first = false;
                            astring1 = $this$first$iv;
                            i = $this$first$iv.length;
                            j = 0;

                            while (true) {
                                if (j >= i) {
                                    throw (Throwable) (new NoSuchElementException("Array contains no element matching the predicate."));
                                }

                                element$iv = astring1[j];
                                $i$a$-first-CommandManager$getCompletions$2$alias$1 = false;
                                if (StringsKt.startsWith(element$iv, s2, true)) {
                                    s1 = element$iv;
                                    break;
                                }

                                ++j;
                            }
                        }

                        String alias = s1;
                        char c0 = this.prefix;

                        $i$f$first = false;
                        s = c0 + alias;
                        destination$iv$iv.add(s);
                    }

                    Collection collection1 = (Collection) ((List) destination$iv$iv);

                    flag = false;
                    aobject = collection1.toArray(new String[0]);
                    if (aobject == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    }

                    astring = (String[]) aobject;
                }

                return astring;
            }
        }

        return null;
    }

    @Nullable
    public final Command getCommand(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Iterable iterable = (Iterable) this.commands;
        boolean flag = false;
        boolean flag1 = false;
        Iterator iterator = iterable.iterator();

        Object object;

        while (true) {
            if (iterator.hasNext()) {
                Object object1;
                boolean flag2;
                label31: {
                    object1 = iterator.next();
                    Command it = (Command) object1;
                    boolean $i$a$-find-CommandManager$getCommand$1 = false;

                    if (!StringsKt.equals(it.getCommand(), name, true)) {
                        String[] $this$any$iv = it.getAlias();
                        boolean $i$f$any = false;
                        String[] astring = $this$any$iv;
                        int i = $this$any$iv.length;
                        int j = 0;

                        while (true) {
                            if (j >= i) {
                                flag2 = false;
                                break;
                            }

                            String element$iv = astring[j];
                            boolean $i$a$-any-CommandManager$getCommand$1$1 = false;

                            if (StringsKt.equals(element$iv, name, true)) {
                                flag2 = true;
                                break;
                            }

                            ++j;
                        }

                        if (!flag2) {
                            flag2 = false;
                            break label31;
                        }
                    }

                    flag2 = true;
                }

                if (!flag2) {
                    continue;
                }

                object = object1;
                break;
            }

            object = null;
            break;
        }

        return (Command) object;
    }

    public final boolean registerCommand(@NotNull Command command) {
        Intrinsics.checkParameterIsNotNull(command, "command");
        return this.commands.add(command);
    }

    public final void registerShortcut(@NotNull String name, @NotNull String script) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(script, "script");
        if (this.getCommand(name) == null) {
            Iterable $this$map$iv = (Iterable) ShortcutParser.INSTANCE.parse(script);
            boolean $i$f$map = false;
            Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
            boolean $i$f$mapTo = false;
            Iterator iterator = $this$map$iv.iterator();

            while (iterator.hasNext()) {
                Object item$iv$iv = iterator.next();
                List it = (List) item$iv$iv;
                boolean $i$a$-map-CommandManager$registerShortcut$1 = false;
                Command command = this.getCommand((String) it.get(0));

                if (command == null) {
                    throw (Throwable) (new IllegalArgumentException("Command " + (String) it.get(0) + " not found!"));
                }

                Command command = command;
                Collection $this$toTypedArray$iv = (Collection) it;
                boolean $i$f$toTypedArray = false;
                Object[] aobject = $this$toTypedArray$iv.toArray(new String[0]);

                if (aobject == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }

                Object[] aobject1 = aobject;
                Pair pair = new Pair(command, aobject1);

                destination$iv$iv.add(pair);
            }

            List list = (List) destination$iv$iv;

            this.registerCommand((Command) (new Shortcut(name, list)));
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().shortcutsConfig);
        } else {
            throw (Throwable) (new IllegalArgumentException("Command already exists!"));
        }
    }

    public final boolean unregisterShortcut(@NotNull final String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        boolean removed = this.commands.removeIf((Predicate) (new Predicate() {
            public final boolean test(@NotNull Command it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it instanceof Shortcut && StringsKt.equals(it.getCommand(), name, true);
            }
        }));

        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().shortcutsConfig);
        return removed;
    }

    public final boolean unregisterCommand(@Nullable Command command) {
        Collection collection = (Collection) this.commands;
        boolean flag = false;

        if (collection == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        } else {
            return TypeIntrinsics.asMutableCollection(collection).remove(command);
        }
    }

    public CommandManager() {
        boolean flag = false;
        List list = (List) (new ArrayList());

        this.commands = list;
        String[] astring = new String[0];

        this.latestAutoComplete = astring;
        this.prefix = 46;
    }
}
