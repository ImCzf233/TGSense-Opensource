package net.ccbluex.liquidbounce.script;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0007J\u0006\u0010\u0011\u001a\u00020\u000fJ\u0006\u0010\u0012\u001a\u00020\u000fJ\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000bJ\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000bJ\u0006\u0010\u0017\u001a\u00020\u000fJ\u0006\u0010\u0018\u001a\u00020\u000fJ\u0006\u0010\u0019\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u001a"},
    d2 = { "Lnet/ccbluex/liquidbounce/script/ScriptManager;", "", "()V", "scriptFileExtension", "", "scripts", "", "Lnet/ccbluex/liquidbounce/script/Script;", "getScripts", "()Ljava/util/List;", "scriptsFolder", "Ljava/io/File;", "getScriptsFolder", "()Ljava/io/File;", "deleteScript", "", "script", "disableScripts", "enableScripts", "importScript", "file", "loadScript", "scriptFile", "loadScripts", "reloadScripts", "unloadScripts", "LiquidBounce"}
)
public final class ScriptManager {

    @NotNull
    private final List scripts;
    @NotNull
    private final File scriptsFolder;
    private final String scriptFileExtension;

    @NotNull
    public final List getScripts() {
        return this.scripts;
    }

    @NotNull
    public final File getScriptsFolder() {
        return this.scriptsFolder;
    }

    public final void loadScripts() {
        if (!this.scriptsFolder.exists()) {
            this.scriptsFolder.mkdir();
        }

        File[] afile = this.scriptsFolder.listFiles((FileFilter) (new FileFilter() {
            public final boolean accept(File it) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                String s = it.getName();

                Intrinsics.checkExpressionValueIsNotNull(s, "it.name");
                return StringsKt.endsWith$default(s, ScriptManager.this.scriptFileExtension, false, 2, (Object) null);
            }
        }));

        if (afile != null) {
            File[] $this$forEach$iv = afile;
            ScriptManager scriptmanager = this;
            boolean $i$f$forEach = false;
            File[] afile1 = $this$forEach$iv;
            int i = $this$forEach$iv.length;

            for (int j = 0; j < i; ++j) {
                File element$iv = afile1[j];
                boolean $i$a$-unknown-ScriptManager$loadScripts$2 = false;

                ((ScriptManager) scriptmanager).loadScript(element$iv);
            }
        }

    }

    public final void unloadScripts() {
        this.scripts.clear();
    }

    public final void loadScript(@NotNull File scriptFile) {
        Intrinsics.checkParameterIsNotNull(scriptFile, "scriptFile");

        try {
            Script t = new Script(scriptFile);

            t.initScript();
            this.scripts.add(t);
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("[ScriptAPI] Failed to load script \'" + scriptFile.getName() + "\'.", throwable);
        }

    }

    public final void enableScripts() {
        Iterable $this$forEach$iv = (Iterable) this.scripts;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv.iterator();

        while (iterator.hasNext()) {
            Object element$iv = iterator.next();
            Script it = (Script) element$iv;
            boolean $i$a$-forEach-ScriptManager$enableScripts$1 = false;

            it.onEnable();
        }

    }

    public final void disableScripts() {
        Iterable $this$forEach$iv = (Iterable) this.scripts;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv.iterator();

        while (iterator.hasNext()) {
            Object element$iv = iterator.next();
            Script it = (Script) element$iv;
            boolean $i$a$-forEach-ScriptManager$disableScripts$1 = false;

            it.onDisable();
        }

    }

    public final void importScript(@NotNull File file) {
        Intrinsics.checkParameterIsNotNull(file, "file");
        File scriptFile = new File(this.scriptsFolder, file.getName());

        FilesKt.copyTo$default(file, scriptFile, false, 0, 6, (Object) null);
        this.loadScript(scriptFile);
        ClientUtils.getLogger().info("[ScriptAPI] Successfully imported script \'" + scriptFile.getName() + "\'.");
    }

    public final void deleteScript(@NotNull Script script) {
        Intrinsics.checkParameterIsNotNull(script, "script");
        script.onDisable();
        this.scripts.remove(script);
        script.getScriptFile().delete();
        ClientUtils.getLogger().info("[ScriptAPI]  Successfully deleted script \'" + script.getScriptFile().getName() + "\'.");
    }

    public final void reloadScripts() {
        this.disableScripts();
        this.unloadScripts();
        this.loadScripts();
        this.enableScripts();
        ClientUtils.getLogger().info("[ScriptAPI]  Successfully reloaded scripts.");
    }

    public ScriptManager() {
        boolean flag = false;
        List list = (List) (new ArrayList());

        this.scripts = list;
        this.scriptsFolder = new File(LiquidBounce.INSTANCE.getFileManager().dir, "scripts");
        this.scriptFileExtension = ".js";
    }
}
