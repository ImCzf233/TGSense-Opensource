package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "Model"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\u0010\u001a\u00020\u0011H\u0016J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Model;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "customPitch", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "customYaw", "pitchMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "rotate", "", "rotateDirection", "", "yawMode", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "drawEntityOnScreen", "", "yaw", "pitch", "entityLivingBase", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "LiquidBounce"}
)
public final class Model extends Element {

    private final ListValue yawMode;
    private final FloatValue customYaw;
    private final ListValue pitchMode;
    private final FloatValue customPitch;
    private float rotate;
    private boolean rotateDirection;

    @NotNull
    public Border drawElement() {
        String pitch = (String) this.yawMode.get();
        boolean delta = false;

        if (pitch == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            float f;
            IEntityPlayerSP ientityplayersp;
            String s;
            label73: {
                s = pitch.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                pitch = s;
                switch (pitch.hashCode()) {
                case -1349088399:
                    if (pitch.equals("custom")) {
                        f = ((Number) this.customYaw.get()).floatValue();
                        break label73;
                    }
                    break;

                case -985752863:
                    if (pitch.equals("player")) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        f = ientityplayersp.getRotationYaw();
                        break label73;
                    }
                    break;

                case 1118509956:
                    if (pitch.equals("animation")) {
                        int delta1 = RenderUtils.deltaTime;

                        if (this.rotateDirection) {
                            if (this.rotate <= 70.0F) {
                                this.rotate += 0.12F * (float) delta1;
                            } else {
                                this.rotateDirection = false;
                                this.rotate = 70.0F;
                            }
                        } else if (this.rotate >= -70.0F) {
                            this.rotate -= 0.12F * (float) delta1;
                        } else {
                            this.rotateDirection = true;
                            this.rotate = -70.0F;
                        }

                        f = this.rotate;
                        break label73;
                    }
                }

                f = 0.0F;
            }

            float yaw = f;
            String delta2 = (String) this.pitchMode.get();
            boolean flag = false;

            if (delta2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                label63: {
                    s = delta2.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                    delta2 = s;
                    switch (delta2.hashCode()) {
                    case -1349088399:
                        if (delta2.equals("custom")) {
                            f = ((Number) this.customPitch.get()).floatValue();
                            break label63;
                        }
                        break;

                    case -985752863:
                        if (delta2.equals("player")) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            f = ientityplayersp.getRotationPitch();
                            break label63;
                        }
                    }

                    f = 0.0F;
                }

                float pitch1 = f;

                if (pitch1 > (float) 0) {
                    f = -pitch1;
                } else {
                    delta = false;
                    f = Math.abs(pitch1);
                }

                pitch1 = f;
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                this.drawEntityOnScreen(yaw, pitch1, (IEntityLivingBase) ientityplayersp1);
                return new Border(30.0F, 10.0F, -30.0F, -100.0F);
            }
        }
    }

    private final void drawEntityOnScreen(float yaw, float pitch, IEntityLivingBase entityLivingBase) {
        MinecraftInstance.classProvider.getGlStateManager().resetColor();
        MinecraftInstance.classProvider.getGlStateManager().enableColorMaterial();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 50.0F);
        GL11.glScalef(-50.0F, 50.0F, 50.0F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float renderYawOffset = entityLivingBase.getRenderYawOffset();
        float rotationYaw = entityLivingBase.getRotationYaw();
        float rotationPitch = entityLivingBase.getRotationPitch();
        float prevRotationYawHead = entityLivingBase.getPrevRotationYawHead();
        float rotationYawHead = entityLivingBase.getRotationYawHead();

        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        MinecraftInstance.functions.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        float renderManager = pitch / 40.0F;
        boolean flag = false;

        GL11.glRotatef(-((float) Math.atan((double) renderManager)) * 20.0F, 1.0F, 0.0F, 0.0F);
        renderManager = yaw / 40.0F;
        flag = false;
        float f = (float) Math.atan((double) renderManager);

        entityLivingBase.setRenderYawOffset(f * 20.0F);
        renderManager = yaw / 40.0F;
        flag = false;
        f = (float) Math.atan((double) renderManager);
        entityLivingBase.setRotationYaw(f * 40.0F);
        renderManager = pitch / 40.0F;
        flag = false;
        f = (float) Math.atan((double) renderManager);
        entityLivingBase.setRotationPitch(-f * 20.0F);
        entityLivingBase.setRotationYawHead(entityLivingBase.getRotationYaw());
        entityLivingBase.setPrevRotationYawHead(entityLivingBase.getRotationYaw());
        GL11.glTranslatef(0.0F, 0.0F, 0.0F);
        IRenderManager renderManager1 = MinecraftInstance.mc.getRenderManager();

        renderManager1.setPlayerViewY(180.0F);
        renderManager1.setRenderShadow(false);
        renderManager1.renderEntityWithPosYaw(entityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        renderManager1.setRenderShadow(true);
        entityLivingBase.setRenderYawOffset(renderYawOffset);
        entityLivingBase.setRotationYaw(rotationYaw);
        entityLivingBase.setRotationPitch(rotationPitch);
        entityLivingBase.setPrevRotationYawHead(prevRotationYawHead);
        entityLivingBase.setRotationYawHead(rotationYawHead);
        GL11.glPopMatrix();
        MinecraftInstance.functions.disableStandardItemLighting();
        MinecraftInstance.classProvider.getGlStateManager().disableRescaleNormal();
        MinecraftInstance.functions.setActiveTextureLightMapTexUnit();
        MinecraftInstance.classProvider.getGlStateManager().disableTexture2D();
        MinecraftInstance.functions.setActiveTextureDefaultTexUnit();
        MinecraftInstance.classProvider.getGlStateManager().resetColor();
    }

    public Model(double x, double y) {
        super(x, y, 0.0F, (Side) null, 12, (DefaultConstructorMarker) null);
        this.yawMode = new ListValue("Yaw", new String[] { "Player", "Animation", "Custom"}, "Animation");
        this.customYaw = new FloatValue("CustomYaw", 0.0F, -180.0F, 180.0F);
        this.pitchMode = new ListValue("Pitch", new String[] { "Player", "Custom"}, "Player");
        this.customPitch = new FloatValue("CustomPitch", 0.0F, -90.0F, 90.0F);
    }

    public Model(double d0, double d1, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 40.0D;
        }

        if ((i & 2) != 0) {
            d1 = 100.0D;
        }

        this(d0, d1);
    }

    public Model() {
        this(0.0D, 0.0D, 3, (DefaultConstructorMarker) null);
    }
}
