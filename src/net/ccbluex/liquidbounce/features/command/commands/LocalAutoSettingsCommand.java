package net.ccbluex.liquidbounce.features.command.commands;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.SettingsUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0006H\u0002¢\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000e¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/LocalAutoSettingsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "getLocalSettings", "Ljava/io/File;", "()[Ljava/io/File;", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class LocalAutoSettingsCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            File file;
            String s;

            if (StringsKt.equals(args[1], "load", true)) {
                if (args.length > 2) {
                    file = new File(LiquidBounce.INSTANCE.getFileManager().settingsDir, args[2]);
                    if (file.exists()) {
                        try {
                            this.chat("§9Loading settings...");
                            s = FilesKt.readText$default(file, (Charset) null, 1, (Object) null);
                            this.chat("§9Set settings...");
                            SettingsUtils.INSTANCE.executeScript(s);
                            this.chat("§6Settings applied successfully.");
                            LiquidBounce.INSTANCE.getHud().addNotification(new Notification("LocalAutoSetting", "Updated Settings", NotifyType.INFO, 0, 0, 24, (DefaultConstructorMarker) null));
                            this.playEdit();
                        } catch (IOException ioexception) {
                            ioexception.printStackTrace();
                        }

                        return;
                    }

                    this.chat("§cSettings file does not exist!");
                    return;
                }

                this.chatSyntax("localautosettings load <name>");
                return;
            }

            if (StringsKt.equals(args[1], "save", true)) {
                if (args.length > 2) {
                    file = new File(LiquidBounce.INSTANCE.getFileManager().settingsDir, args[2]);

                    try {
                        if (file.exists()) {
                            file.delete();
                        }

                        file.createNewFile();
                        boolean flag;
                        String s1;

                        if (args.length > 3) {
                            s1 = StringUtils.toCompleteString(args, 3);
                            Intrinsics.checkExpressionValueIsNotNull(s1, "StringUtils.toCompleteString(args, 3)");
                            String s2 = s1;

                            flag = false;
                            if (s2 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }

                            s1 = s2.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                        } else {
                            s1 = "values";
                        }

                        s = s1;
                        boolean flag1 = StringsKt.contains$default((CharSequence) s, (CharSequence) "all", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) s, (CharSequence) "values", false, 2, (Object) null);

                        flag = StringsKt.contains$default((CharSequence) s, (CharSequence) "all", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) s, (CharSequence) "binds", false, 2, (Object) null);
                        boolean flag2 = StringsKt.contains$default((CharSequence) s, (CharSequence) "all", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) s, (CharSequence) "states", false, 2, (Object) null);

                        if (!flag1 && !flag && !flag2) {
                            this.chatSyntaxError();
                            return;
                        }

                        this.chat("§9Creating settings...");
                        String settingsScript = SettingsUtils.INSTANCE.generateScript(flag1, flag, flag2);

                        this.chat("§9Saving settings...");
                        FilesKt.writeText$default(file, settingsScript, (Charset) null, 2, (Object) null);
                        this.chat("§6Settings saved successfully.");
                    } catch (Throwable throwable) {
                        this.chat("§cFailed to create local config: §3" + throwable.getMessage());
                        ClientUtils.getLogger().error("Failed to create local config.", throwable);
                    }

                    return;
                }

                this.chatSyntax("localsettings save <name> [all/values/binds/states]...");
                return;
            }

            if (StringsKt.equals(args[1], "delete", true)) {
                if (args.length > 2) {
                    file = new File(LiquidBounce.INSTANCE.getFileManager().settingsDir, args[2]);
                    if (file.exists()) {
                        file.delete();
                        this.chat("§6Settings file deleted successfully.");
                        return;
                    }

                    this.chat("§cSettings file does not exist!");
                    return;
                }

                this.chatSyntax("localsettings delete <name>");
                return;
            }

            if (StringsKt.equals(args[1], "list", true)) {
                this.chat("§cSettings:");
                File[] afile = this.getLocalSettings();

                if (afile == null) {
                    return;
                }

                File[] settings = afile;
                File[] binds = settings;
                int states = settings.length;

                for (int values = 0; values < states; ++values) {
                    File file = binds[values];

                    this.chat("> " + file.getName());
                }

                return;
            }
        }

        this.chatSyntax("localsettings <load/save/list/delete>");
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean settings = false;

        if (args.length == 0) {
            return CollectionsKt.emptyList();
        } else {
            List list;

            switch (args.length) {
            case 1:
                Iterable iterable = (Iterable) CollectionsKt.listOf(new String[] { "delete", "list", "load", "save"});

                settings = false;
                Collection collection = (Collection) (new ArrayList());
                boolean $this$filterTo$iv$iv = false;
                Iterator iterator = iterable.iterator();

                while (iterator.hasNext()) {
                    Object object = iterator.next();
                    String s = (String) object;
                    boolean flag = false;

                    if (StringsKt.startsWith(s, args[0], true)) {
                        collection.add(object);
                    }
                }

                list = (List) collection;
                break;

            case 2:
                String $this$filter$iv = args[0];

                settings = false;
                if ($this$filter$iv == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                label60: {
                    String s1 = $this$filter$iv.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    $this$filter$iv = s1;
                    switch ($this$filter$iv.hashCode()) {
                    case -1335458389:
                        if ($this$filter$iv.equals("delete")) {
                            break label60;
                        }
                        break;

                    case 3327206:
                        if ($this$filter$iv.equals("load")) {
                            break label60;
                        }
                    }

                    return CollectionsKt.emptyList();
                }

                File[] afile = this.getLocalSettings();

                if (afile == null) {
                    return CollectionsKt.emptyList();
                }

                File[] afile1 = afile;
                boolean $i$f$filter = false;
                Collection destination$iv$iv = (Collection) (new ArrayList(afile1.length));
                boolean $i$f$filterTo = false;
                File[] it = afile1;
                int element$iv$iv = afile1.length;

                for (int it1 = 0; it1 < element$iv$iv; ++it1) {
                    File $i$a$-filter-LocalAutoSettingsCommand$tabComplete$3 = it[it1];
                    boolean $i$a$-map-LocalAutoSettingsCommand$tabComplete$2 = false;
                    String s2 = $i$a$-filter-LocalAutoSettingsCommand$tabComplete$3.getName();

                    destination$iv$iv.add(s2);
                }

                Iterable $this$filter$iv1 = (Iterable) ((List) destination$iv$iv);

                $i$f$filter = false;
                destination$iv$iv = (Collection) (new ArrayList());
                $i$f$filterTo = false;
                Iterator iterator1 = $this$filter$iv1.iterator();

                while (iterator1.hasNext()) {
                    Object object1 = iterator1.next();
                    String s3 = (String) object1;
                    boolean flag1 = false;

                    Intrinsics.checkExpressionValueIsNotNull(s3, "it");
                    if (StringsKt.startsWith(s3, args[1], true)) {
                        destination$iv$iv.add(object1);
                    }
                }

                return (List) destination$iv$iv;

            default:
                list = CollectionsKt.emptyList();
            }

            return list;
        }
    }

    private final File[] getLocalSettings() {
        return LiquidBounce.INSTANCE.getFileManager().settingsDir.listFiles();
    }

    public LocalAutoSettingsCommand() {
        super("localautosettings", new String[] { "localsetting", "localsettings", "localconfig"});
    }
}
