package net.ccbluex.liquidbounce.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import javax.imageio.ImageIO;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.configs.AccountsConfig;
import net.ccbluex.liquidbounce.file.configs.ClickGuiConfig;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.file.configs.HudConfig;
import net.ccbluex.liquidbounce.file.configs.ModulesConfig;
import net.ccbluex.liquidbounce.file.configs.ShortcutsConfig;
import net.ccbluex.liquidbounce.file.configs.ValuesConfig;
import net.ccbluex.liquidbounce.file.configs.XRayConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FileManager extends MinecraftInstance {

    public final File dir;
    public final File fontsDir;
    public final File settingsDir;
    public final File soundsDir;
    public final FileConfig modulesConfig;
    public final FileConfig valuesConfig;
    public final FileConfig clickGuiConfig;
    public final AccountsConfig accountsConfig;
    public final FriendsConfig friendsConfig;
    public final FileConfig xrayConfig;
    public final FileConfig hudConfig;
    public final FileConfig shortcutsConfig;
    public final File backgroundFile;
    public boolean firstStart;
    public static final Gson PRETTY_GSON = (new GsonBuilder()).setPrettyPrinting().create();

    public FileManager() {
        this.dir = new File(FileManager.mc.getDataDir(), "TGSense-1.12");
        this.fontsDir = new File(this.dir, "fonts");
        this.settingsDir = new File(this.dir, "settings");
        this.soundsDir = new File(this.dir, "sounds");
        this.modulesConfig = new ModulesConfig(new File(this.dir, "modules.json"));
        this.valuesConfig = new ValuesConfig(new File(this.dir, "values.json"));
        this.clickGuiConfig = new ClickGuiConfig(new File(this.dir, "clickgui.json"));
        this.accountsConfig = new AccountsConfig(new File(this.dir, "accounts.json"));
        this.friendsConfig = new FriendsConfig(new File(this.dir, "friends.json"));
        this.xrayConfig = new XRayConfig(new File(this.dir, "xray-blocks.json"));
        this.hudConfig = new HudConfig(new File(this.dir, "hud.json"));
        this.shortcutsConfig = new ShortcutsConfig(new File(this.dir, "shortcuts.json"));
        this.backgroundFile = new File(this.dir, "userbackground.png");
        this.firstStart = false;
        this.setupFolder();
        this.loadBackground();
    }

    public void setupFolder() {
        if (!this.dir.exists()) {
            this.dir.mkdir();
            this.firstStart = true;
        }

        if (!this.fontsDir.exists()) {
            this.fontsDir.mkdir();
        }

        if (!this.soundsDir.exists()) {
            this.soundsDir.mkdir();
        }

        if (!this.settingsDir.exists()) {
            this.settingsDir.mkdir();
        }

    }

    public void loadAllConfigs() {
        Field[] afield = this.getClass().getDeclaredFields();
        int i = afield.length;

        for (int j = 0; j < i; ++j) {
            Field field = afield[j];

            if (field.getType() == FileConfig.class) {
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }

                    FileConfig e = (FileConfig) field.get(this);

                    this.loadConfig(e);
                } catch (IllegalAccessException illegalaccessexception) {
                    ClientUtils.getLogger().error("Failed to load config file of field " + field.getName() + ".", illegalaccessexception);
                }
            }
        }

    }

    public void loadConfigs(FileConfig... configs) {
        FileConfig[] afileconfig = configs;
        int i = configs.length;

        for (int j = 0; j < i; ++j) {
            FileConfig fileConfig = afileconfig[j];

            this.loadConfig(fileConfig);
        }

    }

    public void loadConfig(FileConfig config) {
        if (!config.hasConfig()) {
            ClientUtils.getLogger().info("[FileManager] Skipped loading config: " + config.getFile().getName() + ".");
            this.saveConfig(config, true);
        } else {
            try {
                config.loadConfig();
                ClientUtils.getLogger().info("[FileManager] Loaded config: " + config.getFile().getName() + ".");
            } catch (Throwable throwable) {
                ClientUtils.getLogger().error("[FileManager] Failed to load config file: " + config.getFile().getName() + ".", throwable);
            }

        }
    }

    public void saveAllConfigs() {
        Field[] afield = this.getClass().getDeclaredFields();
        int i = afield.length;

        for (int j = 0; j < i; ++j) {
            Field field = afield[j];

            if (field.getType() == FileConfig.class) {
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }

                    FileConfig e = (FileConfig) field.get(this);

                    this.saveConfig(e);
                } catch (IllegalAccessException illegalaccessexception) {
                    ClientUtils.getLogger().error("[FileManager] Failed to save config file of field " + field.getName() + ".", illegalaccessexception);
                }
            }
        }

    }

    public void saveConfigs(FileConfig... configs) {
        FileConfig[] afileconfig = configs;
        int i = configs.length;

        for (int j = 0; j < i; ++j) {
            FileConfig fileConfig = afileconfig[j];

            this.saveConfig(fileConfig);
        }

    }

    public void saveConfig(FileConfig config) {
        this.saveConfig(config, false);
    }

    private void saveConfig(FileConfig config, boolean ignoreStarting) {
        if (ignoreStarting || !LiquidBounce.INSTANCE.isStarting()) {
            try {
                if (!config.hasConfig()) {
                    config.createConfig();
                }

                config.saveConfig();
                ClientUtils.getLogger().info("[FileManager] Saved config: " + config.getFile().getName() + ".");
            } catch (Throwable throwable) {
                ClientUtils.getLogger().error("[FileManager] Failed to save config file: " + config.getFile().getName() + ".", throwable);
            }

        }
    }

    public void loadBackground() {
        if (this.backgroundFile.exists()) {
            try {
                BufferedImage e = ImageIO.read(new FileInputStream(this.backgroundFile));

                if (e == null) {
                    return;
                }

                FileManager.mc.getTextureManager().loadTexture(LiquidBounce.INSTANCE.getBackground(), FileManager.classProvider.createDynamicTexture(e));
                ClientUtils.getLogger().info("[FileManager] Loaded background.");
            } catch (Exception exception) {
                ClientUtils.getLogger().error("[FileManager] Failed to load background.", exception);
            }
        }

    }
}
