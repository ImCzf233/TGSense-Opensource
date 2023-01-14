package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import me.manager.ColorManager;
import me.utils.player.PlayerUtil;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "TargetStrafe",
    description = "quq",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"J\u0010\u0010#\u001a\u00020$2\u0006\u0010!\u001a\u00020\"H\u0002J\u0018\u0010%\u001a\u00020 2\u0006\u0010&\u001a\u00020\u00042\u0006\u0010\'\u001a\u00020\u0004H\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020)2\u0006\u0010*\u001a\u00020-H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u0011\u0010\u0013\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\fR\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\f¨\u0006."},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/TargetStrafe;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "direction", "", "getDirection", "()I", "setDirection", "(I)V", "holdSpaceValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getHoldSpaceValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "killAura", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "getKillAura", "()Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "onlySpeedValue", "getOnlySpeedValue", "onlyflyValue", "getOnlyflyValue", "radiusValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getRadiusValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "renderModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getRenderModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "thirdPersonViewValue", "getThirdPersonViewValue", "canStrafe", "", "target", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getStrafeDistance", "", "isVoid", "xPos", "zPos", "onMove", "", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"}
)
public final class TargetStrafe extends Module {

    @NotNull
    private final ListValue renderModeValue = new ListValue("RenderMode", new String[] { "Circle", "Pentagon", "None"}, "Pentagon");
    @NotNull
    private final BoolValue thirdPersonViewValue = new BoolValue("ThirdPersonView", false);
    @NotNull
    private final FloatValue radiusValue = new FloatValue("Radius", 0.1F, 0.5F, 5.0F);
    @NotNull
    private final BoolValue holdSpaceValue = new BoolValue("HoldSpace", false);
    @NotNull
    private final BoolValue onlySpeedValue = new BoolValue("OnlySpeed", false);
    @NotNull
    private final BoolValue onlyflyValue = new BoolValue("keyFly", false);
    private int direction = -1;
    @NotNull
    private final KillAura killAura;

    @NotNull
    public final ListValue getRenderModeValue() {
        return this.renderModeValue;
    }

    @NotNull
    public final BoolValue getThirdPersonViewValue() {
        return this.thirdPersonViewValue;
    }

    @NotNull
    public final FloatValue getRadiusValue() {
        return this.radiusValue;
    }

    @NotNull
    public final BoolValue getHoldSpaceValue() {
        return this.holdSpaceValue;
    }

    @NotNull
    public final BoolValue getOnlySpeedValue() {
        return this.onlySpeedValue;
    }

    @NotNull
    public final BoolValue getOnlyflyValue() {
        return this.onlyflyValue;
    }

    public final int getDirection() {
        return this.direction;
    }

    public final void setDirection(int <set-?>) {
        this.direction = <set-?>;
    }

    @NotNull
    public final KillAura getKillAura() {
        return this.killAura;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityLivingBase ientitylivingbase = this.killAura.getTarget();

        if (ientitylivingbase == null) {
            Intrinsics.throwNpe();
        }

        IEntityLivingBase target = ientitylivingbase;

        if (Intrinsics.areEqual((String) this.renderModeValue.get(), "None") ^ true && this.canStrafe(target)) {
            ientitylivingbase = this.killAura.getTarget();
            if (ientitylivingbase == null) {
                Intrinsics.throwNpe();
            }

            if (ientitylivingbase == null) {
                return;
            }

            int[] counter = new int[] { 0};

            ientitylivingbase = this.killAura.getTarget();
            if (ientitylivingbase == null) {
                Intrinsics.throwNpe();
            }

            IEntityLivingBase target1 = ientitylivingbase;
            double d0;
            double d1;
            double d2;
            double d3;
            double d4;
            double d5;
            double d6;
            IEntityLivingBase ientitylivingbase1;
            IEntityLivingBase ientitylivingbase2;
            double d7;

            if (StringsKt.equals((String) this.renderModeValue.get(), "Circle", true)) {
                GL11.glPushMatrix();
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glEnable(2881);
                GL11.glEnable(2832);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glHint(3154, 4354);
                GL11.glHint(3155, 4354);
                GL11.glHint(3153, 4354);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glLineWidth(1.0F);
                GL11.glBegin(3);
                if (target1 == null) {
                    Intrinsics.throwNpe();
                }

                d5 = target1.getLastTickPosX();
                ientitylivingbase1 = this.killAura.getTarget();
                if (ientitylivingbase1 == null) {
                    Intrinsics.throwNpe();
                }

                d6 = ientitylivingbase1.getPosX();
                ientitylivingbase2 = this.killAura.getTarget();
                if (ientitylivingbase2 == null) {
                    Intrinsics.throwNpe();
                }

                double rad = d5 + (d6 - ientitylivingbase2.getLastTickPosX()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosX();

                ientitylivingbase = this.killAura.getTarget();
                if (ientitylivingbase == null) {
                    Intrinsics.throwNpe();
                }

                d5 = ientitylivingbase.getLastTickPosY();
                ientitylivingbase1 = this.killAura.getTarget();
                if (ientitylivingbase1 == null) {
                    Intrinsics.throwNpe();
                }

                d6 = ientitylivingbase1.getPosY();
                ientitylivingbase2 = this.killAura.getTarget();
                if (ientitylivingbase2 == null) {
                    Intrinsics.throwNpe();
                }

                double y = d5 + (d6 - ientitylivingbase2.getLastTickPosY()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosY();

                ientitylivingbase = this.killAura.getTarget();
                if (ientitylivingbase == null) {
                    Intrinsics.throwNpe();
                }

                d5 = ientitylivingbase.getLastTickPosZ();
                ientitylivingbase1 = this.killAura.getTarget();
                if (ientitylivingbase1 == null) {
                    Intrinsics.throwNpe();
                }

                d6 = ientitylivingbase1.getPosZ();
                ientitylivingbase2 = this.killAura.getTarget();
                if (ientitylivingbase2 == null) {
                    Intrinsics.throwNpe();
                }

                double z = d5 + (d6 - ientitylivingbase2.getLastTickPosZ()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosZ();
                int i = 0;

                for (short i1 = 359; i <= i1; ++i) {
                    IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    d5 = (double) ientityplayersp.getTicksExisted() / 70.0D;
                    double rainbow1 = (double) i / 50.0D * 1.75D;

                    d1 = d5;
                    boolean flag = false;

                    d2 = Math.sin(rainbow1);
                    int i = Color.HSBtoRGB((float) ((d1 + d2) % (double) 1.0F), 0.7F, 1.0F);
                    Color rainbow = new Color(i);

                    GL11.glColor3f((float) rainbow.getRed() / 255.0F, (float) rainbow.getGreen() / 255.0F, (float) rainbow.getBlue() / 255.0F);
                    d6 = ((Number) this.radiusValue.get()).doubleValue();
                    rainbow1 = (double) i * 6.283185307179586D / 45.0D;
                    d1 = d6;
                    flag = false;
                    d2 = Math.cos(rainbow1);
                    d5 = rad + d1 * d2;
                    d7 = ((Number) this.radiusValue.get()).doubleValue();
                    rainbow1 = (double) i * 6.283185307179586D / 45.0D;
                    d3 = d7;
                    d0 = d5;
                    flag = false;
                    d4 = Math.sin(rainbow1);
                    GL11.glVertex3d(d0, y, z + d3 * d4);
                }

                GL11.glEnd();
                GL11.glDepthMask(true);
                GL11.glEnable(2929);
                GL11.glDisable(2848);
                GL11.glDisable(2881);
                GL11.glEnable(2832);
                GL11.glEnable(3553);
                GL11.glPopMatrix();
            } else {
                float f = ((Number) this.radiusValue.get()).floatValue();

                if (target1 == null) {
                    return;
                }

                GL11.glPushMatrix();
                GL11.glDisable(3553);
                RenderUtils.startDrawing();
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glLineWidth(1.0F);
                GL11.glBegin(3);
                ientitylivingbase = this.killAura.getTarget();
                if (ientitylivingbase == null) {
                    Intrinsics.throwNpe();
                }

                d5 = ientitylivingbase.getLastTickPosX();
                ientitylivingbase1 = this.killAura.getTarget();
                if (ientitylivingbase1 == null) {
                    Intrinsics.throwNpe();
                }

                d6 = ientitylivingbase1.getPosX();
                ientitylivingbase2 = this.killAura.getTarget();
                if (ientitylivingbase2 == null) {
                    Intrinsics.throwNpe();
                }

                double x = d5 + (d6 - ientitylivingbase2.getLastTickPosX()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosX();

                ientitylivingbase = this.killAura.getTarget();
                if (ientitylivingbase == null) {
                    Intrinsics.throwNpe();
                }

                d5 = ientitylivingbase.getLastTickPosY();
                ientitylivingbase1 = this.killAura.getTarget();
                if (ientitylivingbase1 == null) {
                    Intrinsics.throwNpe();
                }

                d6 = ientitylivingbase1.getPosY();
                ientitylivingbase2 = this.killAura.getTarget();
                if (ientitylivingbase2 == null) {
                    Intrinsics.throwNpe();
                }

                double y1 = d5 + (d6 - ientitylivingbase2.getLastTickPosY()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosY();

                ientitylivingbase = this.killAura.getTarget();
                if (ientitylivingbase == null) {
                    Intrinsics.throwNpe();
                }

                d5 = ientitylivingbase.getLastTickPosZ();
                ientitylivingbase1 = this.killAura.getTarget();
                if (ientitylivingbase1 == null) {
                    Intrinsics.throwNpe();
                }

                d6 = ientitylivingbase1.getPosZ();
                ientitylivingbase2 = this.killAura.getTarget();
                if (ientitylivingbase2 == null) {
                    Intrinsics.throwNpe();
                }

                double z1 = d5 + (d6 - ientitylivingbase2.getLastTickPosZ()) * (double) event.getPartialTicks() - MinecraftInstance.mc.getRenderManager().getViewerPosZ();
                int j = 0;

                for (byte b0 = 10; j <= b0; ++j) {
                    ++counter[0];
                    Color color = new Color(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));

                    GL11.glColor3f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F);
                    double d8;
                    boolean flag1;

                    if ((double) f < 0.8D && (double) f > 0.0D) {
                        d6 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 3.0D;
                        d1 = d6;
                        flag1 = false;
                        d2 = Math.cos(d8);
                        d5 = x + d1 * d2;
                        d7 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 3.0D;
                        d3 = d7;
                        d0 = d5;
                        flag1 = false;
                        d4 = Math.sin(d8);
                        GL11.glVertex3d(d0, y1, z1 + d3 * d4);
                    }

                    if ((double) f < 1.5D && (double) f > 0.7D) {
                        ++counter[0];
                        RenderUtils.glColor3(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        d6 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 4.0D;
                        d1 = d6;
                        flag1 = false;
                        d2 = Math.cos(d8);
                        d5 = x + d1 * d2;
                        d7 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 4.0D;
                        d3 = d7;
                        d0 = d5;
                        flag1 = false;
                        d4 = Math.sin(d8);
                        GL11.glVertex3d(d0, y1, z1 + d3 * d4);
                    }

                    if ((double) f < 2.0D && (double) f > 1.4D) {
                        ++counter[0];
                        RenderUtils.glColor3(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        d6 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 5.0D;
                        d1 = d6;
                        flag1 = false;
                        d2 = Math.cos(d8);
                        d5 = x + d1 * d2;
                        d7 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 5.0D;
                        d3 = d7;
                        d0 = d5;
                        flag1 = false;
                        d4 = Math.sin(d8);
                        GL11.glVertex3d(d0, y1, z1 + d3 * d4);
                    }

                    if ((double) f < 2.4D && (double) f > 1.9D) {
                        ++counter[0];
                        RenderUtils.glColor3(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        d6 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 6.0D;
                        d1 = d6;
                        flag1 = false;
                        d2 = Math.cos(d8);
                        d5 = x + d1 * d2;
                        d7 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 6.0D;
                        d3 = d7;
                        d0 = d5;
                        flag1 = false;
                        d4 = Math.sin(d8);
                        GL11.glVertex3d(d0, y1, z1 + d3 * d4);
                    }

                    if ((double) f < 2.7D && (double) f > 2.3D) {
                        ++counter[0];
                        RenderUtils.glColor3(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        d6 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 7.0D;
                        d1 = d6;
                        flag1 = false;
                        d2 = Math.cos(d8);
                        d5 = x + d1 * d2;
                        d7 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 7.0D;
                        d3 = d7;
                        d0 = d5;
                        flag1 = false;
                        d4 = Math.sin(d8);
                        GL11.glVertex3d(d0, y1, z1 + d3 * d4);
                    }

                    if ((double) f < 6.0D && (double) f > 2.6D) {
                        ++counter[0];
                        RenderUtils.glColor3(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        d6 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 8.0D;
                        d1 = d6;
                        flag1 = false;
                        d2 = Math.cos(d8);
                        d5 = x + d1 * d2;
                        d7 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 8.0D;
                        d3 = d7;
                        d0 = d5;
                        flag1 = false;
                        d4 = Math.sin(d8);
                        GL11.glVertex3d(d0, y1, z1 + d3 * d4);
                    }

                    if ((double) f < 7.0D && (double) f > 5.9D) {
                        ++counter[0];
                        RenderUtils.glColor3(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        d6 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 9.0D;
                        d1 = d6;
                        flag1 = false;
                        d2 = Math.cos(d8);
                        d5 = x + d1 * d2;
                        d7 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 9.0D;
                        d3 = d7;
                        d0 = d5;
                        flag1 = false;
                        d4 = Math.sin(d8);
                        GL11.glVertex3d(d0, y1, z1 + d3 * d4);
                    }

                    if ((double) f < 11.0D && (double) f > 6.9D) {
                        ++counter[0];
                        RenderUtils.glColor3(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        d6 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 10.0D;
                        d1 = d6;
                        flag1 = false;
                        d2 = Math.cos(d8);
                        d5 = x + d1 * d2;
                        d7 = (double) f;
                        d8 = (double) j * 6.283185307179586D / 10.0D;
                        d3 = d7;
                        d0 = d5;
                        flag1 = false;
                        d4 = Math.sin(d8);
                        GL11.glVertex3d(d0, y1, z1 + d3 * d4);
                    }
                }

                GL11.glEnd();
                GL11.glDepthMask(true);
                GL11.glEnable(2929);
                RenderUtils.stopDrawing();
                GL11.glEnable(3553);
                GL11.glPopMatrix();
            }
        }

    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityLivingBase ientitylivingbase = this.killAura.getTarget();

        if (ientitylivingbase == null) {
            Intrinsics.throwNpe();
        }

        IEntityLivingBase target = ientitylivingbase;

        if (this.canStrafe(target)) {
            boolean aroundVoid = false;
            int yaw = -1;

            for (boolean targetStrafe = false; yaw <= 0; ++yaw) {
                int rotAssist = -1;

                for (boolean moveAssist = false; rotAssist <= 0; ++rotAssist) {
                    if (this.isVoid(yaw, rotAssist)) {
                        aroundVoid = true;
                    }
                }
            }

            float f = RotationUtils.getRotationFromEyeHasPrev(this.killAura.getTarget()).getYaw();
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.isCollidedHorizontally() || aroundVoid) {
                this.direction *= -1;
            }

            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            float f1;

            if (ientityplayersp.getMoveStrafing() != 0.0F) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                f1 = ientityplayersp.getMoveStrafing() * (float) this.direction;
            } else {
                f1 = (float) this.direction;
            }

            float f2 = f1;

            if (!PlayerUtil.isBlockUnder()) {
                f2 = 0.0F;
            }

            f1 = (float) 45;
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            float f3 = f1 / ientityplayersp1.getDistanceToEntity2(this.killAura.getTarget());
            IEntityLivingBase ientitylivingbase1 = this.killAura.getTarget();

            if (ientitylivingbase1 == null) {
                Intrinsics.throwNpe();
            }

            double d0 = (double) (45.0F / this.getStrafeDistance(ientitylivingbase1));
            float mathStrafe = 0.0F;
            double d1;
            IEntityLivingBase ientitylivingbase2;

            if (f2 > (float) 0) {
                ientitylivingbase = this.killAura.getTarget();
                if (ientitylivingbase == null) {
                    Intrinsics.throwNpe();
                }

                d1 = ientitylivingbase.getEntityBoundingBox().getMinY();
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                label161: {
                    if (d1 <= ientityplayersp1.getEntityBoundingBox().getMaxY()) {
                        ientitylivingbase = this.killAura.getTarget();
                        if (ientitylivingbase == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getEntityBoundingBox().getMaxY();
                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        if (d1 >= ientityplayersp1.getEntityBoundingBox().getMinY()) {
                            break label161;
                        }
                    }

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientitylivingbase2 = this.killAura.getTarget();
                    if (ientitylivingbase2 == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getDistanceToEntity((IEntity) ientitylivingbase2) < ((Number) this.radiusValue.get()).floatValue()) {
                        f += -f3;
                    }
                }

                mathStrafe += -((float) d0);
            } else if (f2 < (float) 0) {
                ientitylivingbase = this.killAura.getTarget();
                if (ientitylivingbase == null) {
                    Intrinsics.throwNpe();
                }

                d1 = ientitylivingbase.getEntityBoundingBox().getMinY();
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                label164: {
                    if (d1 <= ientityplayersp1.getEntityBoundingBox().getMaxY()) {
                        ientitylivingbase = this.killAura.getTarget();
                        if (ientitylivingbase == null) {
                            Intrinsics.throwNpe();
                        }

                        d1 = ientitylivingbase.getEntityBoundingBox().getMaxY();
                        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp1 == null) {
                            Intrinsics.throwNpe();
                        }

                        if (d1 >= ientityplayersp1.getEntityBoundingBox().getMinY()) {
                            break label164;
                        }
                    }

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientitylivingbase2 = this.killAura.getTarget();
                    if (ientitylivingbase2 == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getDistanceToEntity((IEntity) ientitylivingbase2) < ((Number) this.radiusValue.get()).floatValue()) {
                        f += f3;
                    }
                }

                mathStrafe += (float) d0;
            }

            double[] adouble = new double[2];
            double moveSpeed = Math.toRadians((double) (f + 90.0F + mathStrafe));
            byte b0 = 0;
            double[] adouble1 = adouble;
            double[] adouble2 = adouble;
            boolean asLast = false;
            double d2 = Math.cos(moveSpeed);

            adouble1[b0] = d2;
            moveSpeed = Math.toRadians((double) (f + 90.0F + mathStrafe));
            b0 = 1;
            asLast = false;
            d2 = Math.sin(moveSpeed);
            adouble2[b0] = d2;
            double d3 = event.getX();
            double d4 = 2.0D;
            boolean flag = false;

            d1 = Math.pow(d3, d4);
            d3 = event.getZ();
            d4 = 2.0D;
            double d5 = d1;

            flag = false;
            double d6 = Math.pow(d3, d4);

            d3 = d5 + d6;
            boolean flag1 = false;

            moveSpeed = Math.sqrt(d3);
            double[] adouble3 = new double[] { moveSpeed * adouble2[0], moveSpeed * adouble2[1]};

            event.setX(adouble3[0]);
            event.setZ(adouble3[1]);
            if (((Boolean) this.thirdPersonViewValue.get()).booleanValue()) {
                MinecraftInstance.mc2.gameSettings.thirdPersonView = this.canStrafe(target) ? 3 : 0;
            }
        }
    }

    public final boolean canStrafe(@Nullable IEntityLivingBase target) {
        boolean flag;

        if (target != null) {
            label47: {
                if (((Boolean) this.holdSpaceValue.get()).booleanValue()) {
                    IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (!ientityplayersp.getMovementInput().getJump()) {
                        break label47;
                    }
                }

                if (((Boolean) this.onlySpeedValue.get()).booleanValue()) {
                    Module module = LiquidBounce.INSTANCE.getModuleManager().get(Speed.class);

                    if (module == null) {
                        Intrinsics.throwNpe();
                    }

                    if (!module.getState()) {
                        if (!((Boolean) this.onlyflyValue.get()).booleanValue()) {
                            break label47;
                        }

                        module = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);
                        if (module == null) {
                            Intrinsics.throwNpe();
                        }

                        if (!module.getState()) {
                            break label47;
                        }
                    }
                }

                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }

    private final float getStrafeDistance(IEntityLivingBase target) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        float f = ientityplayersp.getDistanceToEntity((IEntity) target) - ((Number) this.radiusValue.get()).floatValue();
        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        float f1 = ientityplayersp1.getDistanceToEntity((IEntity) target);
        IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp2 == null) {
            Intrinsics.throwNpe();
        }

        return RangesKt.coerceAtLeast(f, f1 - (ientityplayersp2.getDistanceToEntity((IEntity) target) - ((Number) this.radiusValue.get()).floatValue() / (((Number) this.radiusValue.get()).floatValue() * (float) 2)));
    }

    private final boolean isVoid(int xPos, int zPos) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getPosY() < 0.0D) {
            return true;
        } else {
            int off = 0;

            while (true) {
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (off >= (int) ientityplayersp1.getPosY() + 2) {
                    return true;
                }

                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IAxisAlignedBB bb = ientityplayersp.getEntityBoundingBox().offset((double) xPos, -((double) off), (double) zPos);
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (!iworldclient.getCollidingBoundingBoxes((IEntity) ientityplayersp1, bb).isEmpty()) {
                    return false;
                }

                off += 2;
            }
        }
    }

    public TargetStrafe() {
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        } else {
            this.killAura = (KillAura) module;
        }
    }
}
