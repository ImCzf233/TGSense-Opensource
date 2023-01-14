package net.ccbluex.liquidbounce.features.command.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.SettingsUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016¢\u0006\u0002\u0010\fJ;\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0018\u0010\u0012\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0014\u0012\u0004\u0012\u00020\t0\u0013H\u0002¢\u0006\u0002\u0010\u0015J!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u00142\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016¢\u0006\u0002\u0010\u0017R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/AutoSettingsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "autoSettingFiles", "", "", "loadingLock", "Ljava/lang/Object;", "execute", "", "args", "", "([Ljava/lang/String;)V", "loadSettings", "useCached", "", "join", "", "callback", "Lkotlin/Function1;", "", "(ZLjava/lang/Long;Lkotlin/jvm/functions/Function1;)V", "tabComplete", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class AutoSettingsCommand extends Command {

    private final Object loadingLock = new Object();
    private List autoSettingFiles;

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length <= 1) {
            this.chatSyntax("settings <load/list>");
        } else {
            if (StringsKt.equals(args[1], "load", true)) {
                if (args.length < 3) {
                    this.chatSyntax("settings load <name/url>");
                    return;
                }

                String s;

                if (StringsKt.startsWith$default(args[2], "http", false, 2, (Object) null)) {
                    s = args[2];
                } else {
                    StringBuilder stringbuilder = (new StringBuilder()).append("https://cloud.liquidbounce.net/LiquidBounce/settings/");
                    String s1 = args[2];
                    StringBuilder stringbuilder1 = stringbuilder;
                    boolean flag = false;

                    if (s1 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s = s1.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                    String s2 = s;

                    s = stringbuilder1.append(s2).toString();
                }

                final String url = s;

                this.chat("Loading settings...");
                ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) (new Function0(0) {
                    public final void invoke() {
                        try {
                            String exception = HttpUtils.get(url);

                            AutoSettingsCommand.this.chat("Applying settings...");
                            SettingsUtils.INSTANCE.executeScript(exception);
                            AutoSettingsCommand.this.chat("§6Settings applied successfully");
                            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("AutoSettingsCommand", "setting", NotifyType.INFO, 0, 0, 24, (DefaultConstructorMarker) null));
                            AutoSettingsCommand.this.playEdit();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            AutoSettingsCommand.this.chat("Failed to fetch auto settings.");
                        }

                    }
                }), 31, (Object) null);
            } else if (StringsKt.equals(args[1], "list", true)) {
                this.chat("Loading settings...");
                loadSettings$default(this, false, (Long) null, (Function1) (new Function1(1) {
                    public final void invoke(@NotNull List it) {
                        Intrinsics.checkParameterIsNotNull(it, "it");
                        Iterator iterator = it.iterator();

                        while (iterator.hasNext()) {
                            String setting = (String) iterator.next();

                            AutoSettingsCommand.this.chat("> " + setting);
                        }

                    }
                }), 2, (Object) null);
            }

        }
    }

    private final void loadSettings(final boolean useCached, Long join, final Function1 callback) {
        Thread thread = ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) (new Function0(0) {
            public final void invoke() {
                Object object = AutoSettingsCommand.this.loadingLock;
                boolean flag = false;
                boolean flag1 = false;

                synchronized (object){}

                try {
                    boolean $i$a$-synchronized-AutoSettingsCommand$loadSettings$thread$1$1 = false;

                    if (!useCached || AutoSettingsCommand.this.autoSettingFiles == null) {
                        try {
                            JsonElement e = (new JsonParser()).parse(HttpUtils.get("https://api.github.com/repos/CCBlueX/LiquidCloud/contents/LiquidBounce/settings"));
                            boolean setting = false;
                            List autoSettings = (List) (new ArrayList());

                            if (e instanceof JsonArray) {
                                Iterator iterator = ((JsonArray) e).iterator();

                                while (iterator.hasNext()) {
                                    JsonElement setting1 = (JsonElement) iterator.next();

                                    Intrinsics.checkExpressionValueIsNotNull(setting1, "setting");
                                    JsonElement jsonelement = setting1.getAsJsonObject().get("name");

                                    Intrinsics.checkExpressionValueIsNotNull(jsonelement, "setting.asJsonObject[\"name\"]");
                                    String s = jsonelement.getAsString();

                                    Intrinsics.checkExpressionValueIsNotNull(s, "setting.asJsonObject[\"name\"].asString");
                                    autoSettings.add(s);
                                }
                            }

                            callback.invoke(autoSettings);
                            AutoSettingsCommand.this.autoSettingFiles = autoSettings;
                        } catch (Exception exception) {
                            AutoSettingsCommand.this.chat("Failed to fetch auto settings list.");
                        }

                        Unit unit = Unit.INSTANCE;

                        return;
                    }

                    Function1 function1 = callback;
                    List list = AutoSettingsCommand.this.autoSettingFiles;

                    if (list == null) {
                        Intrinsics.throwNpe();
                    }

                    function1.invoke(list);
                } finally {
                    ;
                }

            }
        }), 31, (Object) null);

        if (join != null) {
            thread.join(join.longValue());
        }

    }

    static void loadSettings$default(AutoSettingsCommand autosettingscommand, boolean flag, Long olong, Function1 function1, int i, Object object) {
        if ((i & 2) != 0) {
            olong = (Long) null;
        }

        autosettingscommand.loadSettings(flag, olong, function1);
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean $i$f$filter = false;

        if (args.length == 0) {
            return CollectionsKt.emptyList();
        } else {
            Iterable $this$filter$iv;
            Collection destination$iv$iv;
            boolean $i$f$filterTo;
            Iterator iterator;
            Object element$iv$iv;
            String it;
            boolean $i$a$-filter-AutoSettingsCommand$tabComplete$3;
            List list;

            switch (args.length) {
            case 1:
                $this$filter$iv = (Iterable) CollectionsKt.listOf(new String[] { "list", "load"});
                $i$f$filter = false;
                destination$iv$iv = (Collection) (new ArrayList());
                $i$f$filterTo = false;
                iterator = $this$filter$iv.iterator();

                while (iterator.hasNext()) {
                    element$iv$iv = iterator.next();
                    it = (String) element$iv$iv;
                    $i$a$-filter-AutoSettingsCommand$tabComplete$3 = false;
                    if (StringsKt.startsWith(it, args[0], true)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }

                list = (List) destination$iv$iv;
                break;

            case 2:
                if (StringsKt.equals(args[0], "load", true)) {
                    if (this.autoSettingFiles == null) {
                        this.loadSettings(true, Long.valueOf(500L), (Function1) null.INSTANCE);
                    }

                    if (this.autoSettingFiles != null) {
                        list = this.autoSettingFiles;
                        if (this.autoSettingFiles == null) {
                            Intrinsics.throwNpe();
                        }

                        $this$filter$iv = (Iterable) list;
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList());
                        $i$f$filterTo = false;
                        iterator = $this$filter$iv.iterator();

                        while (iterator.hasNext()) {
                            element$iv$iv = iterator.next();
                            it = (String) element$iv$iv;
                            $i$a$-filter-AutoSettingsCommand$tabComplete$3 = false;
                            if (StringsKt.startsWith(it, args[1], true)) {
                                destination$iv$iv.add(element$iv$iv);
                            }
                        }

                        return (List) destination$iv$iv;
                    }
                }

                return CollectionsKt.emptyList();

            default:
                list = CollectionsKt.emptyList();
            }

            return list;
        }
    }

    public AutoSettingsCommand() {
        super("autosettings", new String[] { "setting", "settings", "config", "autosetting"});
    }
}
