package net.ccbluex.liquidbounce.features.command.commands;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.script.Script;
import net.ccbluex.liquidbounce.script.ScriptManager;
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/ScriptManagerCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class ScriptManagerCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean $i$f$forEachIndexed;

        if (args.length > 1) {
            String s;

            if (StringsKt.equals(args[1], "import", true)) {
                try {
                    File file = MiscUtils.openFileChooser();

                    if (file == null) {
                        return;
                    }

                    File file1 = file;

                    s = file1.getName();
                    Intrinsics.checkExpressionValueIsNotNull(s, "fileName");
                    if (StringsKt.endsWith$default(s, ".js", false, 2, (Object) null)) {
                        LiquidBounce.INSTANCE.getScriptManager().importScript(file1);
                        LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                        this.chat("Successfully imported script.");
                        return;
                    }

                    if (StringsKt.endsWith$default(s, ".zip", false, 2, (Object) null)) {
                        ZipFile zipfile = new ZipFile(file1);
                        Enumeration enumeration = zipfile.entries();
                        ArrayList arraylist = new ArrayList();

                        while (enumeration.hasMoreElements()) {
                            ZipEntry zipentry = (ZipEntry) enumeration.nextElement();

                            Intrinsics.checkExpressionValueIsNotNull(zipentry, "entry");
                            String s1 = zipentry.getName();
                            File file2 = new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), s1);

                            if (zipentry.isDirectory()) {
                                file2.mkdir();
                            } else {
                                InputStream element$iv = zipfile.getInputStream(zipentry);
                                FileOutputStream fileoutputstream = new FileOutputStream(file2);

                                IOUtils.copy(element$iv, (OutputStream) fileoutputstream);
                                fileoutputstream.close();
                                element$iv.close();
                                Intrinsics.checkExpressionValueIsNotNull(s1, "entryName");
                                if (!StringsKt.contains$default((CharSequence) s1, (CharSequence) "/", false, 2, (Object) null)) {
                                    arraylist.add(file2);
                                }
                            }
                        }

                        Iterable iterable = (Iterable) arraylist;
                        boolean flag = false;
                        Iterator iterator = iterable.iterator();

                        while (iterator.hasNext()) {
                            Object object = iterator.next();
                            File file3 = (File) object;
                            boolean index = false;

                            LiquidBounce.INSTANCE.getScriptManager().loadScript(file3);
                        }

                        LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                        this.chat("Successfully imported script.");
                        return;
                    }

                    this.chat("The file extension has to be .js or .zip");
                } catch (Throwable throwable) {
                    ClientUtils.getLogger().error("Something went wrong while importing a script.", throwable);
                    this.chat(throwable.getClass().getName() + ": " + throwable.getMessage());
                }
            } else if (StringsKt.equals(args[1], "delete", true)) {
                try {
                    if (args.length <= 2) {
                        this.chatSyntax("scriptmanager delete <index>");
                        return;
                    }

                    s = args[2];
                    $i$f$forEachIndexed = false;
                    int i = Integer.parseInt(s);
                    List list = LiquidBounce.INSTANCE.getScriptManager().getScripts();

                    if (i >= list.size()) {
                        this.chat("Index " + i + " is too high.");
                        return;
                    }

                    Script script = (Script) list.get(i);

                    LiquidBounce.INSTANCE.getScriptManager().deleteScript(script);
                    LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                    this.chat("Successfully deleted script.");
                } catch (NumberFormatException numberformatexception) {
                    this.chatSyntaxError();
                } catch (Throwable throwable1) {
                    ClientUtils.getLogger().error("Something went wrong while deleting a script.", throwable1);
                    this.chat(throwable1.getClass().getName() + ": " + throwable1.getMessage());
                }
            } else if (StringsKt.equals(args[1], "reload", true)) {
                try {
                    LiquidBounce.INSTANCE.setCommandManager(new CommandManager());
                    LiquidBounce.INSTANCE.getCommandManager().registerCommands();
                    LiquidBounce.INSTANCE.setStarting(true);
                    LiquidBounce.INSTANCE.getScriptManager().disableScripts();
                    LiquidBounce.INSTANCE.getScriptManager().unloadScripts();
                    Iterator iterator1 = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();

                    while (iterator1.hasNext()) {
                        Module module = (Module) iterator1.next();
                        ModuleManager modulemanager = LiquidBounce.INSTANCE.getModuleManager();

                        Intrinsics.checkExpressionValueIsNotNull(module, "module");
                        modulemanager.generateCommand$LiquidBounce(module);
                    }

                    LiquidBounce.INSTANCE.getScriptManager().loadScripts();
                    LiquidBounce.INSTANCE.getScriptManager().enableScripts();
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
                    LiquidBounce.INSTANCE.setStarting(false);
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
                    LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                    LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                    this.chat("Successfully reloaded all scripts.");
                } catch (Throwable throwable2) {
                    ClientUtils.getLogger().error("Something went wrong while reloading all scripts.", throwable2);
                    this.chat(throwable2.getClass().getName() + ": " + throwable2.getMessage());
                }
            } else if (StringsKt.equals(args[1], "folder", true)) {
                try {
                    Desktop.getDesktop().open(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder());
                    this.chat("Successfully opened scripts folder.");
                } catch (Throwable throwable3) {
                    ClientUtils.getLogger().error("Something went wrong while trying to open your scripts folder.", throwable3);
                    this.chat(throwable3.getClass().getName() + ": " + throwable3.getMessage());
                }
            }

        } else {
            ScriptManager scriptManager = LiquidBounce.INSTANCE.getScriptManager();
            Collection $this$forEachIndexed$iv = (Collection) scriptManager.getScripts();

            $i$f$forEachIndexed = false;
            if (!$this$forEachIndexed$iv.isEmpty()) {
                this.chat("§c§lScripts");
                Iterable iterable1 = (Iterable) scriptManager.getScripts();

                $i$f$forEachIndexed = false;
                int index$iv = 0;
                Iterator scriptFiles = iterable1.iterator();

                while (scriptFiles.hasNext()) {
                    Object item$iv = scriptFiles.next();
                    int $i$f$forEach = index$iv++;
                    boolean entryFile = false;

                    if ($i$f$forEach < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }

                    Script script = (Script) item$iv;
                    boolean $i$a$-forEachIndexed-ScriptManagerCommand$execute$2 = false;

                    this.chat($i$f$forEach + ": §a§l" + script.getScriptName() + " §a§lv" + script.getScriptVersion() + " §3by §a§l" + ArraysKt.joinToString$default(script.getScriptAuthors(), (CharSequence) ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
                }
            }

            this.chatSyntax("scriptmanager <import/delete/reload/folder>");
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
                Iterable $this$filter$iv = (Iterable) CollectionsKt.listOf(new String[] { "delete", "import", "folder", "reload"});

                $i$f$filter = false;
                Collection destination$iv$iv = (Collection) (new ArrayList());
                boolean $i$f$filterTo = false;
                Iterator iterator = $this$filter$iv.iterator();

                while (iterator.hasNext()) {
                    Object element$iv$iv = iterator.next();
                    String it = (String) element$iv$iv;
                    boolean $i$a$-filter-ScriptManagerCommand$tabComplete$1 = false;

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

    public ScriptManagerCommand() {
        super("scriptmanager", new String[] { "scripts"});
    }
}
