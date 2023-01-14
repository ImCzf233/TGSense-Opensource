package net.ccbluex.liquidbounce.features.module.modules.render;

import java.lang.reflect.Field;
import java.util.Map;
import me.utils.motionblur.MotionBlurResourceManager;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

@ModuleInfo(
    name = "MotionBlur",
    description = "Render view.",
    category = ModuleCategory.RENDER
)
public class MotionBlur extends Module {

    public static IntegerValue MOTION_BLUR_AMOUNT = new IntegerValue("BlurAmount", 2, 1, 10);
    int lastValue = 0;
    private Map domainResourceManagers;

    public void onDisable() {
        MotionBlur.mc.getEntityRenderer().stopUseShader();
    }

    public void onEnable() {
        if (this.domainResourceManagers == null) {
            try {
                Field[] exception = SimpleReloadableResourceManager.class.getDeclaredFields();
                Field[] afield = exception;
                int i = exception.length;

                for (int j = 0; j < i; ++j) {
                    Field field = afield[j];

                    if (field.getType() == Map.class) {
                        field.setAccessible(true);
                        this.domainResourceManagers = (Map) field.get(MotionBlur.mc2.getResourceManager());
                        break;
                    }
                }
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        if (!this.domainResourceManagers.containsKey("motionblur")) {
            this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
        }

        if (this.isFastRenderEnabled()) {
            ClientUtils.disableFastRender();
        }

        this.lastValue = ((Integer) MotionBlur.MOTION_BLUR_AMOUNT.get()).intValue();
        this.applyShader();
    }

    public boolean isFastRenderEnabled() {
        try {
            Field exception = GameSettings.class.getDeclaredField("ofFastRender");

            return exception.getBoolean(MotionBlur.mc2.gameSettings);
        } catch (Exception exception) {
            return false;
        }
    }

    public void applyShader() {
        MotionBlur.mc.getEntityRenderer().loadShader2(new ResourceLocation("motionblur", "motionblur"));
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if ((!MotionBlur.mc2.entityRenderer.isShaderActive() || this.lastValue != ((Integer) MotionBlur.MOTION_BLUR_AMOUNT.get()).intValue()) && MotionBlur.mc2.world != null && !this.isFastRenderEnabled()) {
            this.lastValue = ((Integer) MotionBlur.MOTION_BLUR_AMOUNT.get()).intValue();
            this.applyShader();
        }

        if (this.domainResourceManagers == null) {
            try {
                Field[] exception = SimpleReloadableResourceManager.class.getDeclaredFields();
                Field[] afield = exception;
                int i = exception.length;

                for (int j = 0; j < i; ++j) {
                    Field field = afield[j];

                    if (field.getType() == Map.class) {
                        field.setAccessible(true);
                        this.domainResourceManagers = (Map) field.get(MotionBlur.mc2.getResourceManager());
                        break;
                    }
                }
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        if (!this.domainResourceManagers.containsKey("motionblur")) {
            this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
        }

        if (this.isFastRenderEnabled()) {
            ClientUtils.disableFastRender();
        }

    }
}
