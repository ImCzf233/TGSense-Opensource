package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "Fullbright",
    description = "Brightens up the world around you.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\u0012\u0010\n\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0012\u0010\r\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/Fullbright;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "prevGamma", "", "onDisable", "", "onEnable", "onShutdown", "event", "Lnet/ccbluex/liquidbounce/event/ClientShutdownEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Fullbright extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Gamma", "NightVision"}, "Gamma");
    private float prevGamma = -1.0F;

    public void onEnable() {
        this.prevGamma = MinecraftInstance.mc.getGameSettings().getGammaSetting();
    }

    public void onDisable() {
        if (this.prevGamma != -1.0F) {
            MinecraftInstance.mc.getGameSettings().setGammaSetting(this.prevGamma);
            this.prevGamma = -1.0F;
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp != null) {
                ientityplayersp.removePotionEffectClient(MinecraftInstance.classProvider.getPotionEnum(PotionType.NIGHT_VISION).getId());
            }

        }
    }

    @EventTarget(
        ignoreCondition = true
    )
    public final void onUpdate(@Nullable UpdateEvent event) {
        if (!this.getState()) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(XRay.class);

            if (module == null) {
                Intrinsics.throwNpe();
            }

            if (!module.getState()) {
                if (this.prevGamma != -1.0F) {
                    MinecraftInstance.mc.getGameSettings().setGammaSetting(this.prevGamma);
                    this.prevGamma = -1.0F;
                }

                return;
            }
        }

        String s = (String) this.modeValue.get();
        boolean flag = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            switch (s.hashCode()) {
            case -820818432:
                if (s.equals("nightvision")) {
                    IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp != null) {
                        ientityplayersp.addPotionEffect(MinecraftInstance.classProvider.createPotionEffect(MinecraftInstance.classProvider.getPotionEnum(PotionType.NIGHT_VISION).getId(), 1337, 1));
                    }
                }
                break;

            case 98120615:
                if (s.equals("gamma") && MinecraftInstance.mc.getGameSettings().getGammaSetting() <= 100.0F) {
                    IGameSettings igamesettings = MinecraftInstance.mc.getGameSettings();

                    igamesettings.setGammaSetting(igamesettings.getGammaSetting() + 1.0F);
                }
            }

        }
    }

    @EventTarget(
        ignoreCondition = true
    )
    public final void onShutdown(@Nullable ClientShutdownEvent event) {
        this.onDisable();
    }
}
