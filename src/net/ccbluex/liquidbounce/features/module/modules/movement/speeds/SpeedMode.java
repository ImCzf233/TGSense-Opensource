package net.ccbluex.liquidbounce.features.module.modules.movement.speeds;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u000bH\u0016J\u0010\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH&J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\u000bH\u0016J\b\u0010\u0013\u001a\u00020\u000bH&R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "modeName", "", "(Ljava/lang/String;)V", "isActive", "", "()Z", "getModeName", "()Ljava/lang/String;", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onTick", "onUpdate", "LiquidBounce"}
)
public abstract class SpeedMode extends MinecraftInstance {

    @NotNull
    private final String modeName;

    public final boolean isActive() {
        Speed speed = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
        boolean flag;

        if (speed != null) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (!ientityplayersp.isSneaking() && speed.getState() && Intrinsics.areEqual((String) speed.getModeValue().get(), this.modeName)) {
                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }

    public abstract void onMotion(@NotNull MotionEvent motionevent);

    public abstract void onUpdate();

    public abstract void onMove(@NotNull MoveEvent moveevent);

    public void onTick() {}

    public void onEnable() {}

    public void onDisable() {}

    @NotNull
    public final String getModeName() {
        return this.modeName;
    }

    public SpeedMode(@NotNull String modeName) {
        Intrinsics.checkParameterIsNotNull(modeName, "modeName");
        super();
        this.modeName = modeName;
    }
}
