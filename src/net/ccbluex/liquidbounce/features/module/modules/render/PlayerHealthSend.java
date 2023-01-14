package net.ccbluex.liquidbounce.features.module.modules.render;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "PlayerHealthSend",
    description = "Debug Health",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/PlayerHealthSend;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "healthData", "Ljava/util/HashMap;", "", "", "Lkotlin/collections/HashMap;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/TimeUtils;", "handleEvents", "", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class PlayerHealthSend extends Module {

    private final TimeUtils timer = new TimeUtils();
    private final HashMap healthData = new HashMap();

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        Iterator iterator = iworldclient.getLoadedEntityList().iterator();

        while (iterator.hasNext()) {
            IEntity entity = (IEntity) iterator.next();

            if (entity instanceof IEntityLivingBase && EntityUtils.isSelected(entity, true)) {
                HashMap hashmap = this.healthData;
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                Integer integer = Integer.valueOf(ientityplayersp.getEntityId());
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                Object object = hashmap.getOrDefault(integer, Float.valueOf(ientityplayersp1.getMaxHealth()));

                Intrinsics.checkExpressionValueIsNotNull(object, "healthData.getOrDefault(…mc.thePlayer!!.maxHealth)");
                float lastHealth = ((Number) object).floatValue();
                Map map = (Map) this.healthData;

                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                integer = Integer.valueOf(ientityplayersp.getEntityId());
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                map.put(integer, Float.valueOf(ientityplayersp1.getHealth()));
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (lastHealth != ientityplayersp.getHealth()) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    StringBuilder stringbuilder;

                    if (lastHealth > ientityplayersp.getHealth()) {
                        stringbuilder = (new StringBuilder()).append("§c扣除�?量§a");
                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        stringbuilder = stringbuilder.append(lastHealth - ientityplayersp1.getHealth()).append("HP").append(" §f| ").append("§c当前�?量§a");
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ClientUtils.displayChatMessage(stringbuilder.append(ientityplayersp.getHealth()).append("HP").toString());
                    } else {
                        stringbuilder = (new StringBuilder()).append("§c增加�?量§a");
                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        float f = lastHealth - ientityplayersp1.getHealth();
                        StringBuilder stringbuilder1 = stringbuilder;
                        boolean flag = false;
                        float f1 = Math.abs(f);

                        stringbuilder = stringbuilder1.append(f1).append("HP").append(" §f| ").append("§c当前�?量§a");
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        ClientUtils.displayChatMessage(stringbuilder.append(ientityplayersp.getHealth()).append("HP").toString());
                    }
                }
            }
        }

        if (this.timer.delay(220.0F)) {
            IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp2.getHealth() < 10.0F) {
                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("PlayerHealthSend", "Low HP!!! ", NotifyType.WARNING, 0, 0, 24, (DefaultConstructorMarker) null));
            }
        }

        this.timer.reset();
    }

    public boolean handleEvents() {
        return true;
    }
}
