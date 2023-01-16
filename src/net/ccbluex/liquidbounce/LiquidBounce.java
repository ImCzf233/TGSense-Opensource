package net.ccbluex.liquidbounce;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.text.StringsKt;
import me.manager.CombatManager;
import net.ccbluex.liquidbounce.api.Wrapper;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.cape.CapeAPI;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.EventManager;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.macro.MacroManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.features.module.modules.render.PlayerHealthSend;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.features.special.ClientRichPresence;
import net.ccbluex.liquidbounce.features.special.DonatorCape;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.script.ScriptManager;
import net.ccbluex.liquidbounce.script.remapper.Remapper;
import net.ccbluex.liquidbounce.tabs.BlocksTab;
import net.ccbluex.liquidbounce.tabs.ExploitsTab;
import net.ccbluex.liquidbounce.tabs.HeadsTab;
import net.ccbluex.liquidbounce.ui.client.GuiMainMenu;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.client.hud.HUD;
import net.ccbluex.liquidbounce.ui.client.keybind.KeyBindManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.sound.TipSoundManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.Display;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000Ð\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u009f\u0001\u001a\u00030 \u00012\u0007\u0010¡\u0001\u001a\u00020\u0004J\b\u0010¢\u0001\u001a\u00030 \u0001J\b\u0010£\u0001\u001a\u00030 \u0001J\u0012\u0010¤\u0001\u001a\u00020\u00042\t\u0010¤\u0001\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R!\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0015j\b\u0012\u0004\u0012\u00020\u0004`\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020+X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001a\u00100\u001a\u000201X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u001a\u00106\u001a\u000207X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u001a\u0010<\u001a\u00020=X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001a\u0010B\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u001a\u0010G\u001a\u00020HX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\u001a\u0010M\u001a\u00020NX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR\u001a\u0010S\u001a\u00020TX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010V\"\u0004\bW\u0010XR\u001a\u0010Y\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010D\"\u0004\bZ\u0010FR\u001a\u0010[\u001a\u00020\\X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010^\"\u0004\b_\u0010`R\u0014\u0010a\u001a\u00020bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\bc\u0010dR\u001a\u0010e\u001a\u00020bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bf\u0010d\"\u0004\bg\u0010hR\u001a\u0010i\u001a\u00020jX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bk\u0010l\"\u0004\bm\u0010nR\u001a\u0010o\u001a\u00020pX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010r\"\u0004\bs\u0010tR\u001a\u0010u\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bv\u0010D\"\u0004\bw\u0010FR\u001c\u0010x\u001a\u0004\u0018\u00010pX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\by\u0010r\"\u0004\bz\u0010tR\u001d\u0010{\u001a\u0004\u0018\u00010|X\u0086\u000e¢\u0006\u000f\n\u0000\u001a\u0004\b}\u0010~\"\u0005\b\u007f\u0010\u0080\u0001R \u0010\u0081\u0001\u001a\u00030\u0082\u0001X\u0086.¢\u0006\u0012\n\u0000\u001a\u0006\b\u0083\u0001\u0010\u0084\u0001\"\u0006\b\u0085\u0001\u0010\u0086\u0001R \u0010\u0087\u0001\u001a\u00030\u0088\u0001X\u0086.¢\u0006\u0012\n\u0000\u001a\u0006\b\u0089\u0001\u0010\u008a\u0001\"\u0006\b\u008b\u0001\u0010\u008c\u0001R \u0010\u008d\u0001\u001a\u00030\u008e\u0001X\u0086.¢\u0006\u0012\n\u0000\u001a\u0006\b\u008f\u0001\u0010\u0090\u0001\"\u0006\b\u0091\u0001\u0010\u0092\u0001R \u0010\u0093\u0001\u001a\u00030\u0094\u0001X\u0086.¢\u0006\u0012\n\u0000\u001a\u0006\b\u0095\u0001\u0010\u0096\u0001\"\u0006\b\u0097\u0001\u0010\u0098\u0001R \u0010\u0099\u0001\u001a\u00030\u009a\u0001X\u0086.¢\u0006\u0012\n\u0000\u001a\u0006\b\u009b\u0001\u0010\u009c\u0001\"\u0006\b\u009d\u0001\u0010\u009e\u0001¨\u0006¥\u0001"},
    d2 = { "Lnet/ccbluex/liquidbounce/LiquidBounce;", "", "()V", "CLIENT_CLOUD", "", "CLIENT_CREATOR", "CLIENT_DEV", "CLIENT_FONTS", "CLIENT_NAME", "CLIENT_VERSIO", "CLIENT_VERSION", "GuiMainMenu", "Lnet/ccbluex/liquidbounce/ui/client/GuiMainMenu;", "getGuiMainMenu", "()Lnet/ccbluex/liquidbounce/ui/client/GuiMainMenu;", "setGuiMainMenu", "(Lnet/ccbluex/liquidbounce/ui/client/GuiMainMenu;)V", "IN_DEV", "", "MINECRAFT_VERSION", "UPDATE_LIST", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getUPDATE_LIST", "()Ljava/util/ArrayList;", "USERNAME", "getUSERNAME", "()Ljava/lang/String;", "setUSERNAME", "(Ljava/lang/String;)V", "background", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getBackground", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "setBackground", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;)V", "background2", "Lnet/minecraft/util/ResourceLocation;", "getBackground2", "()Lnet/minecraft/util/ResourceLocation;", "setBackground2", "(Lnet/minecraft/util/ResourceLocation;)V", "clickGui", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;", "getClickGui", "()Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;", "setClickGui", "(Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;)V", "clientRichPresence", "Lnet/ccbluex/liquidbounce/features/special/ClientRichPresence;", "getClientRichPresence", "()Lnet/ccbluex/liquidbounce/features/special/ClientRichPresence;", "setClientRichPresence", "(Lnet/ccbluex/liquidbounce/features/special/ClientRichPresence;)V", "combatManager", "Lme/manager/CombatManager;", "getCombatManager", "()Lme/manager/CombatManager;", "setCombatManager", "(Lme/manager/CombatManager;)V", "commandManager", "Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "getCommandManager", "()Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "setCommandManager", "(Lnet/ccbluex/liquidbounce/features/command/CommandManager;)V", "darkMode", "getDarkMode", "()Z", "setDarkMode", "(Z)V", "eventManager", "Lnet/ccbluex/liquidbounce/event/EventManager;", "getEventManager", "()Lnet/ccbluex/liquidbounce/event/EventManager;", "setEventManager", "(Lnet/ccbluex/liquidbounce/event/EventManager;)V", "fileManager", "Lnet/ccbluex/liquidbounce/file/FileManager;", "getFileManager", "()Lnet/ccbluex/liquidbounce/file/FileManager;", "setFileManager", "(Lnet/ccbluex/liquidbounce/file/FileManager;)V", "hud", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "getHud", "()Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "setHud", "(Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;)V", "isStarting", "setStarting", "keyBindManager", "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyBindManager;", "getKeyBindManager", "()Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyBindManager;", "setKeyBindManager", "(Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyBindManager;)V", "kills", "", "getKills", "()I", "latestVersion", "getLatestVersion", "setLatestVersion", "(I)V", "macroManager", "Lnet/ccbluex/liquidbounce/features/macro/MacroManager;", "getMacroManager", "()Lnet/ccbluex/liquidbounce/features/macro/MacroManager;", "setMacroManager", "(Lnet/ccbluex/liquidbounce/features/macro/MacroManager;)V", "mainMenu", "Lnet/minecraft/client/gui/GuiScreen;", "getMainMenu", "()Lnet/minecraft/client/gui/GuiScreen;", "setMainMenu", "(Lnet/minecraft/client/gui/GuiScreen;)V", "mainMenuPrep", "getMainMenuPrep", "setMainMenuPrep", "mainmenu", "getMainmenu", "setMainmenu", "module", "Lnet/ccbluex/liquidbounce/features/module/Module;", "getModule", "()Lnet/ccbluex/liquidbounce/features/module/Module;", "setModule", "(Lnet/ccbluex/liquidbounce/features/module/Module;)V", "moduleManager", "Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "getModuleManager", "()Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "setModuleManager", "(Lnet/ccbluex/liquidbounce/features/module/ModuleManager;)V", "playerhealthsend", "Lnet/ccbluex/liquidbounce/features/module/modules/render/PlayerHealthSend;", "getPlayerhealthsend", "()Lnet/ccbluex/liquidbounce/features/module/modules/render/PlayerHealthSend;", "setPlayerhealthsend", "(Lnet/ccbluex/liquidbounce/features/module/modules/render/PlayerHealthSend;)V", "scriptManager", "Lnet/ccbluex/liquidbounce/script/ScriptManager;", "getScriptManager", "()Lnet/ccbluex/liquidbounce/script/ScriptManager;", "setScriptManager", "(Lnet/ccbluex/liquidbounce/script/ScriptManager;)V", "tipSoundManager", "Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundManager;", "getTipSoundManager", "()Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundManager;", "setTipSoundManager", "(Lnet/ccbluex/liquidbounce/utils/misc/sound/TipSoundManager;)V", "wrapper", "Lnet/ccbluex/liquidbounce/api/Wrapper;", "getWrapper", "()Lnet/ccbluex/liquidbounce/api/Wrapper;", "setWrapper", "(Lnet/ccbluex/liquidbounce/api/Wrapper;)V", "setUsername", "", "name", "startClient", "stopClient", "wight", "LiquidBounce"}
)
public final class LiquidBounce {

    @NotNull
    public static final String CLIENT_NAME = "TGSense";
    @NotNull
    public static final String CLIENT_VERSION = "230116";
    @NotNull
    public static final String CLIENT_VERSIO = "Release";
    public static final boolean IN_DEV = true;
    @NotNull
    public static final String CLIENT_CREATOR = "";
    @NotNull
    public static final String CLIENT_DEV = "LangYa";
    @NotNull
    public static final String CLIENT_FONTS = "https://nightx.api-minecraft.net/s/icwf3t8op4";
    private static final int kills = 0;
    @NotNull
    public static GuiScreen mainMenu;
    @NotNull
    public static final String MINECRAFT_VERSION = "1.12.2";
    @NotNull
    public static final String CLIENT_CLOUD = "https://cloud.liquidbounce.net/LiquidBounce";
    @NotNull
    private static final ArrayList UPDATE_LIST;
    private static boolean mainMenuPrep;
    private static boolean isStarting;
    private static boolean darkMode;
    @NotNull
    public static KeyBindManager keyBindManager;
    @NotNull
    public static MacroManager macroManager;
    @NotNull
    public static ModuleManager moduleManager;
    @NotNull
    public static CommandManager commandManager;
    @NotNull
    public static EventManager eventManager;
    @NotNull
    public static PlayerHealthSend playerhealthsend;
    @NotNull
    public static FileManager fileManager;
    @NotNull
    public static ScriptManager scriptManager;
    @NotNull
    public static CombatManager combatManager;
    @NotNull
    public static TipSoundManager tipSoundManager;
    @Nullable
    private static Module module;
    @NotNull
    public static HUD hud;
    @NotNull
    private static String USERNAME;
    @NotNull
    public static ClickGui clickGui;
    private static int latestVersion;
    @Nullable
    private static IResourceLocation background;
    @NotNull
    public static ResourceLocation background2;
    @NotNull
    public static GuiMainMenu GuiMainMenu;
    @Nullable
    private static GuiScreen mainmenu;
    @NotNull
    public static ClientRichPresence clientRichPresence;
    @NotNull
    public static Wrapper wrapper;
    public static final LiquidBounce INSTANCE;

    public final int getKills() {
        return LiquidBounce.kills;
    }

    @NotNull
    public final GuiScreen getMainMenu() {
        GuiScreen guiscreen = LiquidBounce.mainMenu;

        if (LiquidBounce.mainMenu == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mainMenu");
        }

        return guiscreen;
    }

    public final void setMainMenu(@NotNull GuiScreen <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.mainMenu = <set-?>;
    }

    @NotNull
    public final ArrayList getUPDATE_LIST() {
        return LiquidBounce.UPDATE_LIST;
    }

    public final boolean getMainMenuPrep() {
        return LiquidBounce.mainMenuPrep;
    }

    public final void setMainMenuPrep(boolean <set-?>) {
        LiquidBounce.mainMenuPrep = <set-?>;
    }

    public final boolean isStarting() {
        return LiquidBounce.isStarting;
    }

    public final void setStarting(boolean <set-?>) {
        LiquidBounce.isStarting = <set-?>;
    }

    public final boolean getDarkMode() {
        return LiquidBounce.darkMode;
    }

    public final void setDarkMode(boolean <set-?>) {
        LiquidBounce.darkMode = <set-?>;
    }

    @NotNull
    public final KeyBindManager getKeyBindManager() {
        KeyBindManager keybindmanager = LiquidBounce.keyBindManager;

        if (LiquidBounce.keyBindManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keyBindManager");
        }

        return keybindmanager;
    }

    public final void setKeyBindManager(@NotNull KeyBindManager <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.keyBindManager = <set-?>;
    }

    @NotNull
    public final MacroManager getMacroManager() {
        MacroManager macromanager = LiquidBounce.macroManager;

        if (LiquidBounce.macroManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("macroManager");
        }

        return macromanager;
    }

    public final void setMacroManager(@NotNull MacroManager <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.macroManager = <set-?>;
    }

    @NotNull
    public final ModuleManager getModuleManager() {
        ModuleManager modulemanager = LiquidBounce.moduleManager;

        if (LiquidBounce.moduleManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moduleManager");
        }

        return modulemanager;
    }

    public final void setModuleManager(@NotNull ModuleManager <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.moduleManager = <set-?>;
    }

    @NotNull
    public final CommandManager getCommandManager() {
        CommandManager commandmanager = LiquidBounce.commandManager;

        if (LiquidBounce.commandManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("commandManager");
        }

        return commandmanager;
    }

    public final void setCommandManager(@NotNull CommandManager <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.commandManager = <set-?>;
    }

    @NotNull
    public final EventManager getEventManager() {
        EventManager eventmanager = LiquidBounce.eventManager;

        if (LiquidBounce.eventManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }

        return eventmanager;
    }

    public final void setEventManager(@NotNull EventManager <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.eventManager = <set-?>;
    }

    @NotNull
    public final PlayerHealthSend getPlayerhealthsend() {
        PlayerHealthSend playerhealthsend = LiquidBounce.playerhealthsend;

        if (LiquidBounce.playerhealthsend == null) {
            Intrinsics.throwUninitializedPropertyAccessException("playerhealthsend");
        }

        return playerhealthsend;
    }

    public final void setPlayerhealthsend(@NotNull PlayerHealthSend <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.playerhealthsend = <set-?>;
    }

    @NotNull
    public final FileManager getFileManager() {
        FileManager filemanager = LiquidBounce.fileManager;

        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        return filemanager;
    }

    public final void setFileManager(@NotNull FileManager <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.fileManager = <set-?>;
    }

    @NotNull
    public final ScriptManager getScriptManager() {
        ScriptManager scriptmanager = LiquidBounce.scriptManager;

        if (LiquidBounce.scriptManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptManager");
        }

        return scriptmanager;
    }

    public final void setScriptManager(@NotNull ScriptManager <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.scriptManager = <set-?>;
    }

    @NotNull
    public final CombatManager getCombatManager() {
        CombatManager combatmanager = LiquidBounce.combatManager;

        if (LiquidBounce.combatManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("combatManager");
        }

        return combatmanager;
    }

    public final void setCombatManager(@NotNull CombatManager <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.combatManager = <set-?>;
    }

    @NotNull
    public final TipSoundManager getTipSoundManager() {
        TipSoundManager tipsoundmanager = LiquidBounce.tipSoundManager;

        if (LiquidBounce.tipSoundManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tipSoundManager");
        }

        return tipsoundmanager;
    }

    public final void setTipSoundManager(@NotNull TipSoundManager <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.tipSoundManager = <set-?>;
    }

    @Nullable
    public final Module getModule() {
        return LiquidBounce.module;
    }

    public final void setModule(@Nullable Module <set-?>) {
        LiquidBounce.module = <set-?>;
    }

    @NotNull
    public final HUD getHud() {
        HUD hud = LiquidBounce.hud;

        if (LiquidBounce.hud == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
        }

        return hud;
    }

    public final void setHud(@NotNull HUD <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.hud = <set-?>;
    }

    @NotNull
    public final String getUSERNAME() {
        return LiquidBounce.USERNAME;
    }

    public final void setUSERNAME(@NotNull String <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.USERNAME = <set-?>;
    }

    @NotNull
    public final ClickGui getClickGui() {
        ClickGui clickgui = LiquidBounce.clickGui;

        if (LiquidBounce.clickGui == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clickGui");
        }

        return clickgui;
    }

    public final void setClickGui(@NotNull ClickGui <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.clickGui = <set-?>;
    }

    public final int getLatestVersion() {
        return LiquidBounce.latestVersion;
    }

    public final void setLatestVersion(int <set-?>) {
        LiquidBounce.latestVersion = <set-?>;
    }

    @Nullable
    public final IResourceLocation getBackground() {
        return LiquidBounce.background;
    }

    public final void setBackground(@Nullable IResourceLocation <set-?>) {
        LiquidBounce.background = <set-?>;
    }

    @NotNull
    public final ResourceLocation getBackground2() {
        ResourceLocation resourcelocation = LiquidBounce.background2;

        if (LiquidBounce.background2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("background2");
        }

        return resourcelocation;
    }

    public final void setBackground2(@NotNull ResourceLocation <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.background2 = <set-?>;
    }

    @NotNull
    public final GuiMainMenu getGuiMainMenu() {
        GuiMainMenu guimainmenu = LiquidBounce.GuiMainMenu;

        if (LiquidBounce.GuiMainMenu == null) {
            Intrinsics.throwUninitializedPropertyAccessException("GuiMainMenu");
        }

        return guimainmenu;
    }

    public final void setGuiMainMenu(@NotNull GuiMainMenu <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.GuiMainMenu = <set-?>;
    }

    @Nullable
    public final GuiScreen getMainmenu() {
        return LiquidBounce.mainmenu;
    }

    public final void setMainmenu(@Nullable GuiScreen <set-?>) {
        LiquidBounce.mainmenu = <set-?>;
    }

    @NotNull
    public final ClientRichPresence getClientRichPresence() {
        ClientRichPresence clientrichpresence = LiquidBounce.clientRichPresence;

        if (LiquidBounce.clientRichPresence == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clientRichPresence");
        }

        return clientrichpresence;
    }

    public final void setClientRichPresence(@NotNull ClientRichPresence <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.clientRichPresence = <set-?>;
    }

    @NotNull
    public final Wrapper getWrapper() {
        Wrapper wrapper = LiquidBounce.wrapper;

        if (LiquidBounce.wrapper == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wrapper");
        }

        return wrapper;
    }

    public final void setWrapper(@NotNull Wrapper <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        LiquidBounce.wrapper = <set-?>;
    }

    public final void setUsername(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        LiquidBounce.USERNAME = name;
    }

    public final void startClient() {
        final ObjectRef QQNumber = new ObjectRef();

        QQNumber.element = "dawdaw";
        <undefinedtype> $fun$displayTray$1 = null.INSTANCE;
        <undefinedtype> $fun$getSubString$2 = null.INSTANCE;
        <undefinedtype> $fun$_filterQQInfo$3 = null.INSTANCE;
        Function0 $fun$getLoginQQList$4 = new Function0(0) {
            @Nullable
            public final Map invoke() {
                final String[] QQNumber1 = new String[1];
                final Map map = (Map) (new HashMap(5));
                final WebUtils2.User32 user32 = WebUtils2.User32.INSTANCE;

                user32.EnumWindows((WebUtils2.User32.WNDENUMPROC) (new WebUtils2.User32.WNDENUMPROC() {
                    public final boolean callback(@NotNull Pointer hWnd, @Nullable Pointer userData) {
                        Intrinsics.checkParameterIsNotNull(hWnd, "hWnd");
                        byte[] windowText = new byte[512];

                        user32.GetWindowTextA(hWnd, windowText, 512);
                        String wText = Native.toString(windowText);
                        <undefinedtype> 0 = null.INSTANCE;

                        Intrinsics.checkExpressionValueIsNotNull(wText, "wText");
                        if (0.invoke(wText)) {
                            Map map = map;
                            String s = hWnd.toString();

                            Intrinsics.checkExpressionValueIsNotNull(s, "hWnd.toString()");
                            int i = StringsKt.indexOf$default((CharSequence) wText, "qqexchangewnd_shortcut_prefix_", 0, false, 6, (Object) null) + "qqexchangewnd_shortcut_prefix_".length();
                            String s1 = s;
                            Map map1 = map;
                            boolean flag = false;
                            String s2 = wText.substring(i);

                            Intrinsics.checkExpressionValueIsNotNull(s2, "(this as java.lang.String).substring(startIndex)");
                            String s3 = s2;

                            map1.put(s1, s3);
                        }

                        QQNumber1[0] = null.INSTANCE.invoke(map.toString(), "=", "}");
                        QQNumber.element = String.valueOf(QQNumber1[0]);
                        return true;
                    }
                }), (Pointer) null);
                return map;
            }
        };

        $fun$getLoginQQList$4.invoke();
        LiquidBounce.isStarting = true;
        Display.setTitle("TGSense B2 | Dev:LangYa,Happy | 纪狗气死我啦�?");
        ClientUtils.getLogger().info("Starting TGSense b230116, by ");
        LiquidBounce.fileManager = new FileManager();
        LiquidBounce.eventManager = new EventManager();
        EventManager eventmanager = LiquidBounce.eventManager;

        if (LiquidBounce.eventManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }

        eventmanager.registerListener((Listenable) (new RotationUtils()));
        eventmanager = LiquidBounce.eventManager;
        if (LiquidBounce.eventManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }

        eventmanager.registerListener((Listenable) (new AntiForge()));
        eventmanager = LiquidBounce.eventManager;
        if (LiquidBounce.eventManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }

        eventmanager.registerListener((Listenable) (new BungeeCordSpoof()));
        eventmanager = LiquidBounce.eventManager;
        if (LiquidBounce.eventManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }

        eventmanager.registerListener((Listenable) (new DonatorCape()));
        eventmanager = LiquidBounce.eventManager;
        if (LiquidBounce.eventManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }

        eventmanager.registerListener((Listenable) (new InventoryUtils()));
        LiquidBounce.tipSoundManager = new TipSoundManager();
        LiquidBounce.clientRichPresence = new ClientRichPresence();
        LiquidBounce.keyBindManager = new KeyBindManager();
        Fonts.loadFonts();
        LiquidBounce.macroManager = new MacroManager();
        eventmanager = LiquidBounce.eventManager;
        if (LiquidBounce.eventManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }

        MacroManager macromanager = LiquidBounce.macroManager;

        if (LiquidBounce.macroManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("macroManager");
        }

        eventmanager.registerListener((Listenable) macromanager);
        LiquidBounce.commandManager = new CommandManager();
        Fonts.loadFonts();
        LiquidBounce.moduleManager = new ModuleManager();
        ModuleManager modulemanager = LiquidBounce.moduleManager;

        if (LiquidBounce.moduleManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moduleManager");
        }

        modulemanager.registerModules();

        try {
            Remapper.INSTANCE.loadSrg();
            LiquidBounce.scriptManager = new ScriptManager();
            ScriptManager scriptmanager = LiquidBounce.scriptManager;

            if (LiquidBounce.scriptManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scriptManager");
            }

            scriptmanager.loadScripts();
            scriptmanager = LiquidBounce.scriptManager;
            if (LiquidBounce.scriptManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scriptManager");
            }

            scriptmanager.enableScripts();
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("Failed to load scripts.", throwable);
        }

        CommandManager commandmanager = LiquidBounce.commandManager;

        if (LiquidBounce.commandManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("commandManager");
        }

        commandmanager.registerCommands();
        FileManager filemanager = LiquidBounce.fileManager;

        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        FileConfig[] afileconfig = new FileConfig[6];
        FileManager filemanager1 = LiquidBounce.fileManager;

        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        afileconfig[0] = filemanager1.modulesConfig;
        filemanager1 = LiquidBounce.fileManager;
        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        afileconfig[1] = filemanager1.valuesConfig;
        filemanager1 = LiquidBounce.fileManager;
        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        afileconfig[2] = (FileConfig) filemanager1.accountsConfig;
        filemanager1 = LiquidBounce.fileManager;
        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        afileconfig[3] = (FileConfig) filemanager1.friendsConfig;
        filemanager1 = LiquidBounce.fileManager;
        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        afileconfig[4] = filemanager1.xrayConfig;
        filemanager1 = LiquidBounce.fileManager;
        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        afileconfig[5] = filemanager1.shortcutsConfig;
        filemanager.loadConfigs(afileconfig);
        LiquidBounce.clickGui = new ClickGui();
        filemanager = LiquidBounce.fileManager;
        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        FileManager filemanager2 = LiquidBounce.fileManager;

        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        filemanager.loadConfig(filemanager2.clickGuiConfig);
        LiquidBounce.playerhealthsend = new PlayerHealthSend();
        eventmanager = LiquidBounce.eventManager;
        if (LiquidBounce.eventManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }

        PlayerHealthSend playerhealthsend = LiquidBounce.playerhealthsend;

        if (LiquidBounce.playerhealthsend == null) {
            Intrinsics.throwUninitializedPropertyAccessException("playerhealthsend");
        }

        eventmanager.registerListener((Listenable) playerhealthsend);
        eventmanager = LiquidBounce.eventManager;
        if (LiquidBounce.eventManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }

        playerhealthsend = LiquidBounce.playerhealthsend;
        if (LiquidBounce.playerhealthsend == null) {
            Intrinsics.throwUninitializedPropertyAccessException("playerhealthsend");
        }

        eventmanager.registerListener((Listenable) playerhealthsend);
        if (ClassUtils.INSTANCE.hasForge()) {
            new BlocksTab();
            new ExploitsTab();
            new HeadsTab();
        }

        try {
            CapeAPI.INSTANCE.registerCapeService();
        } catch (Throwable throwable1) {
            ClientUtils.getLogger().error("Failed to register cape service", throwable1);
        }

        LiquidBounce.hud = HUD.Companion.createDefault();
        filemanager = LiquidBounce.fileManager;
        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        filemanager2 = LiquidBounce.fileManager;
        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        filemanager.loadConfig(filemanager2.hudConfig);
        ClientUtils.disableFastRender();

        try {
            JsonElement exception = (new JsonParser()).parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/versions.json"));

            if (exception instanceof JsonObject && ((JsonObject) exception).has("1.12.2")) {
                JsonElement jsonelement = ((JsonObject) exception).get("1.12.2");

                Intrinsics.checkExpressionValueIsNotNull(jsonelement, "jsonObj[MINECRAFT_VERSION]");
                LiquidBounce.latestVersion = jsonelement.getAsInt();
            }
        } catch (Throwable throwable2) {
            ClientUtils.getLogger().error("Failed to check for updates.", throwable2);
        }

        GuiAltManager.loadGenerators();
        ClientRichPresence clientrichpresence = LiquidBounce.clientRichPresence;

        if (LiquidBounce.clientRichPresence == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clientRichPresence");
        }

        if (clientrichpresence.getShowRichPresenceValue()) {
            ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) null.INSTANCE, 31, (Object) null);
        }

        LiquidBounce.isStarting = false;
    }

    public final void stopClient() {
        EventManager eventmanager = LiquidBounce.eventManager;

        if (LiquidBounce.eventManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        }

        eventmanager.callEvent((Event) (new ClientShutdownEvent()));
        FileManager filemanager = LiquidBounce.fileManager;

        if (LiquidBounce.fileManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        }

        filemanager.saveAllConfigs();
        ClientRichPresence clientrichpresence = LiquidBounce.clientRichPresence;

        if (LiquidBounce.clientRichPresence == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clientRichPresence");
        }

        clientrichpresence.shutdown();
    }

    @NotNull
    public final String wight(@Nullable String wight) {
        URLConnection urlconnection = (new URL(wight)).openConnection();

        if (urlconnection == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.net.HttpURLConnection");
        } else {
            HttpURLConnection con = (HttpURLConnection) urlconnection;

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader((Reader) (new InputStreamReader(con.getInputStream())));
            ObjectRef inputLine = new ObjectRef();
            StringBuilder response = new StringBuilder();

            while (true) {
                String s = in.readLine();
                boolean flag = false;
                boolean flag1 = false;
                boolean $i$a$-also-LiquidBounce$wight$1 = false;

                inputLine.element = s;
                if (s == null) {
                    in.close();
                    String s1 = response.toString();

                    Intrinsics.checkExpressionValueIsNotNull(s1, "response.toString()");
                    return s1;
                }

                response.append((String) inputLine.element);
                response.append("\n");
            }
        }
    }

    static {
        LiquidBounce liquidbounce = new LiquidBounce();

        INSTANCE = liquidbounce;
        UPDATE_LIST = CollectionsKt.arrayListOf(new String[] { ""});
        LiquidBounce.USERNAME = "";
    }
}
