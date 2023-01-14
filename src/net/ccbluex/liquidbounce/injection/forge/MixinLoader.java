package net.ccbluex.liquidbounce.injection.forge;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.MixinEnvironment.Side;

public class MixinLoader implements IFMLLoadingPlugin {

    public MixinLoader() {
        System.out.println("[LiquidBounce] Injecting with IFMLLoadingPlugin.");
        MixinBootstrap.init();
        Mixins.addConfiguration("liquidbounce.forge.mixins.json");
        MixinEnvironment.getDefaultEnvironment().setSide(Side.CLIENT);
    }

    public String[] getASMTransformerClass() {
        return new String[0];
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map data) {}

    public String getAccessTransformerClass() {
        return null;
    }
}
