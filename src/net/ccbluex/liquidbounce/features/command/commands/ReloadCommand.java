package net.ccbluex.liquidbounce.features.command.commands;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/ReloadCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"}
)
public final class ReloadCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        this.chat("Reloading...");
        this.chat("§c§lReloading commands...");
        LiquidBounce.INSTANCE.setCommandManager(new CommandManager());
        LiquidBounce.INSTANCE.getCommandManager().registerCommands();
        LiquidBounce.INSTANCE.setStarting(true);
        LiquidBounce.INSTANCE.getScriptManager().disableScripts();
        LiquidBounce.INSTANCE.getScriptManager().unloadScripts();
        Iterator iterator = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();

        while (iterator.hasNext()) {
            Module module = (Module) iterator.next();
            ModuleManager modulemanager = LiquidBounce.INSTANCE.getModuleManager();

            Intrinsics.checkExpressionValueIsNotNull(module, "module");
            modulemanager.generateCommand$LiquidBounce(module);
        }

        this.chat("§c§lReloading scripts...");
        LiquidBounce.INSTANCE.getScriptManager().reloadScripts();
        this.chat("§c§lReloading fonts...");
        Fonts.loadFonts();
        this.chat("§c§lReloading modules...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
        LiquidBounce.INSTANCE.setStarting(false);
        this.chat("§c§lReloading values...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
        this.chat("§c§lReloading accounts...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig((FileConfig) LiquidBounce.INSTANCE.getFileManager().accountsConfig);
        this.chat("§c§lReloading friends...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig((FileConfig) LiquidBounce.INSTANCE.getFileManager().friendsConfig);
        this.chat("§c§lReloading xray...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().xrayConfig);
        this.chat("§c§lReloading HUD...");
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
        this.chat("§c§lReloading ClickGUI...");
        LiquidBounce.INSTANCE.setClickGui(new ClickGui());
        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
        LiquidBounce.INSTANCE.setStarting(false);
        this.chat("Reloaded.");
    }

    public ReloadCommand() {
        super("reload", new String[] { "configreload"});
    }
}
