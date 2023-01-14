package net.ccbluex.liquidbounce.script;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.script.ScriptEngine;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.injection.backend.MinecraftImpl;
import net.ccbluex.liquidbounce.script.api.ScriptCommand;
import net.ccbluex.liquidbounce.script.api.ScriptModule;
import net.ccbluex.liquidbounce.script.api.ScriptTab;
import net.ccbluex.liquidbounce.script.api.global.Chat;
import net.ccbluex.liquidbounce.script.api.global.Item;
import net.ccbluex.liquidbounce.script.api.global.Setting;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001:\u00018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u0007H\u0002J\u0012\u0010(\u001a\u0004\u0018\u00010\u00072\u0006\u0010)\u001a\u00020\u0007H\u0002J\u000e\u0010*\u001a\u00020&2\u0006\u0010\u0002\u001a\u00020\u0007J\u0006\u0010+\u001a\u00020&J\u0016\u0010,\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\bJ\u0006\u0010.\u001a\u00020&J\u0006\u0010/\u001a\u00020&J\u0016\u00100\u001a\u00020&2\u0006\u00101\u001a\u00020\b2\u0006\u00102\u001a\u00020\bJ\u0016\u00103\u001a\u00020&2\u0006\u00104\u001a\u00020\b2\u0006\u00102\u001a\u00020\bJ\u000e\u00105\u001a\u00020&2\u0006\u00106\u001a\u00020\bJ\b\u00107\u001a\u00020&H\u0002R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0010X\u0086.¢\u0006\u0010\n\u0002\u0010\u0015\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u0007X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010 \u001a\u00020\u0007X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001c\"\u0004\b\"\u0010\u001eR\u000e\u0010#\u001a\u00020$X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00069"},
    d2 = { "Lnet/ccbluex/liquidbounce/script/Script;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "scriptFile", "Ljava/io/File;", "(Ljava/io/File;)V", "events", "Ljava/util/HashMap;", "", "Ljdk/nashorn/api/scripting/JSObject;", "Lkotlin/collections/HashMap;", "registeredCommands", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "registeredModules", "Lnet/ccbluex/liquidbounce/features/module/Module;", "scriptAuthors", "", "getScriptAuthors", "()[Ljava/lang/String;", "setScriptAuthors", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "scriptEngine", "Ljavax/script/ScriptEngine;", "getScriptFile", "()Ljava/io/File;", "scriptName", "getScriptName", "()Ljava/lang/String;", "setScriptName", "(Ljava/lang/String;)V", "scriptText", "scriptVersion", "getScriptVersion", "setScriptVersion", "state", "", "callEvent", "", "eventName", "getMagicComment", "name", "import", "initScript", "on", "handler", "onDisable", "onEnable", "registerCommand", "commandObject", "callback", "registerModule", "moduleObject", "registerTab", "tabObject", "supportLegacyScripts", "RegisterScript", "LiquidBounce"}
)
public final class Script extends MinecraftInstance {

    private final ScriptEngine scriptEngine;
    private final String scriptText;
    @NotNull
    public String scriptName;
    @NotNull
    public String scriptVersion;
    @NotNull
    public String[] scriptAuthors;
    private boolean state;
    private final HashMap events;
    private final List registeredModules;
    private final List registeredCommands;
    @NotNull
    private final File scriptFile;

    @NotNull
    public final String getScriptName() {
        String s = this.scriptName;

        if (this.scriptName == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptName");
        }

        return s;
    }

    public final void setScriptName(@NotNull String <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.scriptName = <set-?>;
    }

    @NotNull
    public final String getScriptVersion() {
        String s = this.scriptVersion;

        if (this.scriptVersion == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptVersion");
        }

        return s;
    }

    public final void setScriptVersion(@NotNull String <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.scriptVersion = <set-?>;
    }

    @NotNull
    public final String[] getScriptAuthors() {
        String[] astring = this.scriptAuthors;

        if (this.scriptAuthors == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptAuthors");
        }

        return astring;
    }

    public final void setScriptAuthors(@NotNull String[] <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.scriptAuthors = <set-?>;
    }

    public final void initScript() {
        this.scriptEngine.eval(this.scriptText);
        this.callEvent("load");
        ClientUtils.getLogger().info("[ScriptAPI] Successfully loaded script \'" + this.scriptFile.getName() + "\'.");
    }

    public final void registerModule(@NotNull JSObject moduleObject, @NotNull JSObject callback) {
        Intrinsics.checkParameterIsNotNull(moduleObject, "moduleObject");
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        ScriptModule module = new ScriptModule(moduleObject);

        LiquidBounce.INSTANCE.getModuleManager().registerModule((Module) module);
        Collection collection = (Collection) this.registeredModules;
        boolean flag = false;

        collection.add(module);
        callback.call(moduleObject, new Object[] { module});
    }

    public final void registerCommand(@NotNull JSObject commandObject, @NotNull JSObject callback) {
        Intrinsics.checkParameterIsNotNull(commandObject, "commandObject");
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        ScriptCommand command = new ScriptCommand(commandObject);

        LiquidBounce.INSTANCE.getCommandManager().registerCommand((Command) command);
        Collection collection = (Collection) this.registeredCommands;
        boolean flag = false;

        collection.add(command);
        callback.call(commandObject, new Object[] { command});
    }

    public final void registerTab(@NotNull JSObject tabObject) {
        Intrinsics.checkParameterIsNotNull(tabObject, "tabObject");
        new ScriptTab(tabObject);
    }

    private final String getMagicComment(String name) {
        String magicPrefix = "///";
        Iterable $this$forEach$iv = (Iterable) StringsKt.lines((CharSequence) this.scriptText);
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv.iterator();

        List commentData;
        String s;
        boolean flag;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            Object element$iv = iterator.next();
            String it = (String) element$iv;
            boolean $i$a$-forEach-Script$getMagicComment$1 = false;

            if (!StringsKt.startsWith$default(it, magicPrefix, false, 2, (Object) null)) {
                return null;
            }

            int i = magicPrefix.length();
            boolean flag1 = false;

            if (it == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String s1 = it.substring(i);

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).substring(startIndex)");
            CharSequence charsequence = (CharSequence) s1;
            String[] astring = new String[] { "="};

            flag = false;
            byte b0 = 2;

            commentData = StringsKt.split$default(charsequence, astring, flag, b0, 2, (Object) null);
            s = (String) CollectionsKt.first(commentData);
            flag = false;
            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
        } while (!Intrinsics.areEqual(StringsKt.trim((CharSequence) s).toString(), name));

        s = (String) CollectionsKt.last(commentData);
        flag = false;
        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        } else {
            return StringsKt.trim((CharSequence) s).toString();
        }
    }

    private final void supportLegacyScripts() {
        if (Intrinsics.areEqual(this.getMagicComment("api_version"), "2") ^ true) {
            ClientUtils.getLogger().info("[ScriptAPI] Running script \'" + this.scriptFile.getName() + "\' with legacy support.");
            URL url = LiquidBounce.class.getResource("/assets/minecraft/langya/scriptapi/legacy.js");

            Intrinsics.checkExpressionValueIsNotNull(url, "LiquidBounce::class.java…gya/scriptapi/legacy.js\")");
            URL url1 = url;
            Charset charset = Charsets.UTF_8;
            boolean flag = false;
            byte[] abyte = TextStreamsKt.readBytes(url1);
            boolean flag1 = false;
            boolean flag2 = false;
            String legacyScript = new String(abyte, charset);

            this.scriptEngine.eval(legacyScript);
        }

    }

    public final void on(@NotNull String eventName, @NotNull JSObject handler) {
        Intrinsics.checkParameterIsNotNull(eventName, "eventName");
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        ((Map) this.events).put(eventName, handler);
    }

    public final void onEnable() {
        if (!this.state) {
            this.callEvent("enable");
            this.state = true;
        }
    }

    public final void onDisable() {
        if (this.state) {
            Iterable $this$forEach$iv = (Iterable) this.registeredModules;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv.iterator();

            Object element$iv;
            boolean $i$a$-forEach-Script$onDisable$2;

            while (iterator.hasNext()) {
                element$iv = iterator.next();
                Module it = (Module) element$iv;

                $i$a$-forEach-Script$onDisable$2 = false;
                LiquidBounce.INSTANCE.getModuleManager().unregisterModule(it);
            }

            $this$forEach$iv = (Iterable) this.registeredCommands;
            $i$f$forEach = false;
            iterator = $this$forEach$iv.iterator();

            while (iterator.hasNext()) {
                element$iv = iterator.next();
                Command it1 = (Command) element$iv;

                $i$a$-forEach-Script$onDisable$2 = false;
                LiquidBounce.INSTANCE.getCommandManager().unregisterCommand(it1);
            }

            this.callEvent("disable");
            this.state = false;
        }
    }

    public final void import(@NotNull String scriptFile) {
        Intrinsics.checkParameterIsNotNull(scriptFile, "scriptFile");
        String scriptText = FilesKt.readText$default(new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), scriptFile), (Charset) null, 1, (Object) null);

        this.scriptEngine.eval(scriptText);
    }

    private final void callEvent(String eventName) {
        try {
            JSObject jsobject = (JSObject) this.events.get(eventName);

            if (jsobject != null) {
                jsobject.call((Object) null, new Object[0]);
            }
        } catch (Throwable throwable) {
            Logger logger = ClientUtils.getLogger();
            StringBuilder stringbuilder = (new StringBuilder()).append("[ScriptAPI] Exception in script \'");
            String s = this.scriptName;

            if (this.scriptName == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scriptName");
            }

            logger.error(stringbuilder.append(s).append("\'!").toString(), throwable);
        }

    }

    @NotNull
    public final File getScriptFile() {
        return this.scriptFile;
    }

    public Script(@NotNull File scriptFile) {
        boolean $i$f$unwrap;
        String[] astring;
        label22: {
            Intrinsics.checkParameterIsNotNull(scriptFile, "scriptFile");
            super();
            this.scriptFile = scriptFile;
            this.scriptText = FilesKt.readText$default(this.scriptFile, (Charset) null, 1, (Object) null);
            this.events = new HashMap();
            boolean engineFlags = false;
            List list = (List) (new ArrayList());

            this.registeredModules = list;
            engineFlags = false;
            list = (List) (new ArrayList());
            this.registeredCommands = list;
            String s = this.getMagicComment("engine_flags");

            if (s != null) {
                List list1 = StringsKt.split$default((CharSequence) s, new String[] { ","}, false, 0, 6, (Object) null);

                if (list1 != null) {
                    Collection $this$unwrap$iv = (Collection) list1;

                    $i$f$unwrap = false;
                    Object[] aobject = $this$unwrap$iv.toArray(new String[0]);

                    if (aobject == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    }

                    astring = (String[]) aobject;
                    if (astring != null) {
                        break label22;
                    }
                }
            }

            astring = new String[0];
        }

        String[] engineFlags1 = astring;
        ScriptEngine scriptengine = (new NashornScriptEngineFactory()).getScriptEngine((String[]) Arrays.copyOf(engineFlags1, engineFlags1.length));

        Intrinsics.checkExpressionValueIsNotNull(scriptengine, "NashornScriptEngineFacto…criptEngine(*engineFlags)");
        this.scriptEngine = scriptengine;
        this.scriptEngine.put("Chat", StaticClass.forClass(Chat.class));
        this.scriptEngine.put("Setting", StaticClass.forClass(Setting.class));
        this.scriptEngine.put("Item", StaticClass.forClass(Item.class));
        ScriptEngine scriptengine1 = this.scriptEngine;
        IMinecraft iminecraft = MinecraftInstance.mc;

        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
        IMinecraft $this$unwrap$iv1 = iminecraft;
        String s1 = "mc";
        ScriptEngine scriptengine2 = scriptengine1;

        $i$f$unwrap = false;
        if ($this$unwrap$iv1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.MinecraftImpl");
        } else {
            Minecraft minecraft = ((MinecraftImpl) $this$unwrap$iv1).getWrapped();

            scriptengine2.put(s1, minecraft);
            this.scriptEngine.put("moduleManager", LiquidBounce.INSTANCE.getModuleManager());
            this.scriptEngine.put("commandManager", LiquidBounce.INSTANCE.getCommandManager());
            this.scriptEngine.put("scriptManager", LiquidBounce.INSTANCE.getScriptManager());
            this.scriptEngine.put("imc", MinecraftInstance.mc);
            this.scriptEngine.put("classProvider", MinecraftInstance.classProvider);
            this.scriptEngine.put("registerScript", new Script.RegisterScript());
            this.supportLegacyScripts();
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0002H\u0016¨\u0006\u0007"},
        d2 = { "Lnet/ccbluex/liquidbounce/script/Script$RegisterScript;", "Ljava/util/function/Function;", "Ljdk/nashorn/api/scripting/JSObject;", "Lnet/ccbluex/liquidbounce/script/Script;", "(Lnet/ccbluex/liquidbounce/script/Script;)V", "apply", "scriptObject", "LiquidBounce"}
    )
    public final class RegisterScript implements Function {

        @NotNull
        public Script apply(@NotNull JSObject scriptObject) {
            Intrinsics.checkParameterIsNotNull(scriptObject, "scriptObject");
            Script script = Script.this;
            Object object = scriptObject.getMember("name");

            if (object == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
            } else {
                script.setScriptName((String) object);
                script = Script.this;
                object = scriptObject.getMember("version");
                if (object == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                } else {
                    script.setScriptVersion((String) object);
                    script = Script.this;
                    object = ScriptUtils.convert(scriptObject.getMember("authors"), String[].class);
                    if (object == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
                    } else {
                        script.setScriptAuthors((String[]) object);
                        return Script.this;
                    }
                }
            }
        }
    }
}
