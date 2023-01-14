package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.injection.backend.EntityLivingBaseImpl;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.WorldToScreen;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

@ModuleInfo(
    name = "ESP",
    description = "Allows you to see targets through walls.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dJ\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0007J\u0012\u0010\"\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010#H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u00118\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u00020\u00118\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006%"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/ESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "botValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorRainbow", "colorRedValue", "colorTeam", "healthValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "nameValue", "outlineValue", "outlineWidth", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "shaderGlowRadius", "shaderOutlineRadius", "tag", "", "getTag", "()Ljava/lang/String;", "widthValue", "wireframeWidth", "getColor", "Ljava/awt/Color;", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "Companion", "LiquidBounce"}
)
public final class ESP extends Module {

    @JvmField
    @NotNull
    public final ListValue modeValue = new ListValue("Mode", new String[] { "Cylinder", "Box", "OtherBox", "WireFrame", "2D", "Real2D", "2DBox", "Outline", "ShaderOutline", "ShaderGlow"}, "Box");
    private final BoolValue healthValue = new BoolValue("Real2D-Health", false);
    private final FloatValue widthValue = new FloatValue("Real2D-Width", 0.0F, 0.0F, 0.5F);
    private final BoolValue outlineValue = new BoolValue("Real2D-Outline", false);
    private final BoolValue nameValue = new BoolValue("Real2D-Name", false);
    @JvmField
    @NotNull
    public final FloatValue outlineWidth = new FloatValue("Outline-Width", 3.0F, 0.5F, 5.0F);
    @JvmField
    @NotNull
    public final FloatValue wireframeWidth = new FloatValue("WireFrame-Width", 2.0F, 0.5F, 5.0F);
    private final FloatValue shaderOutlineRadius = new FloatValue("ShaderOutline-Radius", 1.35F, 1.0F, 2.0F);
    private final FloatValue shaderGlowRadius = new FloatValue("ShaderGlow-Radius", 2.3F, 2.0F, 3.0F);
    private final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    private final BoolValue colorTeam = new BoolValue("Team", false);
    private final BoolValue botValue = new BoolValue("Bots", true);
    @JvmField
    public static boolean renderNameTags = true;
    public static final ESP.Companion Companion = new ESP.Companion((DefaultConstructorMarker) null);

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        String mode = (String) this.modeValue.get();
        Matrix4f mvMatrix = WorldToScreen.getMatrix(2982);
        Matrix4f projectionMatrix = WorldToScreen.getMatrix(2983);
        boolean real2d = StringsKt.equals(mode, "real2d", true);

        if (real2d) {
            GL11.glPushAttrib(8192);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, (double) MinecraftInstance.mc.getDisplayWidth(), (double) MinecraftInstance.mc.getDisplayHeight(), 0.0D, -1.0D, 1.0D);
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glDisable(2929);
            GL11.glBlendFunc(770, 771);
            MinecraftInstance.classProvider.getGlStateManager().enableTexture2D();
            GL11.glDepthMask(true);
            GL11.glLineWidth(1.0F);
        }

        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        Iterator iterator = iworldclient.getLoadedEntityList().iterator();

        while (iterator.hasNext()) {
            IEntity entity = (IEntity) iterator.next();

            if (MinecraftInstance.classProvider.isEntityLivingBase(entity) && (((Boolean) this.botValue.get()).booleanValue() || !AntiBot.isBot(entity.asEntityLivingBase())) && (EntityUtils.isSelected(entity, false) || Intrinsics.areEqual(entity, MinecraftInstance.mc.getThePlayer()) && MinecraftInstance.mc.getGameSettings().getThirdPersonView() != 0)) {
                IEntityLivingBase entityLiving = entity.asEntityLivingBase();
                Color color = this.getColor((IEntity) entityLiving);
                boolean renderManager = false;

                if (mode == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s = mode.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                String s1 = s;
                ITimer timer;
                double bb;
                double minX;
                double maxX;
                IRenderManager irendermanager;

                switch (s1.hashCode()) {
                case -1171135301:
                    if (!s1.equals("otherbox")) {
                        continue;
                    }
                    break;

                case -934973296:
                    if (!s1.equals("real2d")) {
                        continue;
                    }

                    irendermanager = MinecraftInstance.mc.getRenderManager();
                    timer = MinecraftInstance.mc.getTimer();
                    IAxisAlignedBB iaxisalignedbb = entityLiving.getEntityBoundingBox().offset(-entityLiving.getPosX(), -entityLiving.getPosY(), -entityLiving.getPosZ()).offset(entityLiving.getLastTickPosX() + (entityLiving.getPosX() - entityLiving.getLastTickPosX()) * (double) timer.getRenderPartialTicks(), entityLiving.getLastTickPosY() + (entityLiving.getPosY() - entityLiving.getLastTickPosY()) * (double) timer.getRenderPartialTicks(), entityLiving.getLastTickPosZ() + (entityLiving.getPosZ() - entityLiving.getLastTickPosZ()) * (double) timer.getRenderPartialTicks()).offset(-irendermanager.getRenderPosX(), -irendermanager.getRenderPosY(), -irendermanager.getRenderPosZ());
                    double[][] boxVertices = (double[][]) (new double[][] { { iaxisalignedbb.getMinX() - ((Number) this.widthValue.get()).doubleValue(), iaxisalignedbb.getMinY(), iaxisalignedbb.getMinZ() - ((Number) this.widthValue.get()).doubleValue()}, { iaxisalignedbb.getMinX() - ((Number) this.widthValue.get()).doubleValue(), iaxisalignedbb.getMaxY() + 0.1D, iaxisalignedbb.getMinZ() - ((Number) this.widthValue.get()).doubleValue()}, { iaxisalignedbb.getMaxX() + ((Number) this.widthValue.get()).doubleValue(), iaxisalignedbb.getMaxY() + 0.1D, iaxisalignedbb.getMinZ() - ((Number) this.widthValue.get()).doubleValue()}, { iaxisalignedbb.getMaxX() + ((Number) this.widthValue.get()).doubleValue(), iaxisalignedbb.getMinY(), iaxisalignedbb.getMinZ() - ((Number) this.widthValue.get()).doubleValue()}, { iaxisalignedbb.getMinX() - ((Number) this.widthValue.get()).doubleValue(), iaxisalignedbb.getMinY(), iaxisalignedbb.getMaxZ() + ((Number) this.widthValue.get()).doubleValue()}, { iaxisalignedbb.getMinX() - ((Number) this.widthValue.get()).doubleValue(), iaxisalignedbb.getMaxY() + 0.1D, iaxisalignedbb.getMaxZ() + ((Number) this.widthValue.get()).doubleValue()}, { iaxisalignedbb.getMaxX() + ((Number) this.widthValue.get()).doubleValue(), iaxisalignedbb.getMaxY() + 0.1D, iaxisalignedbb.getMaxZ() + ((Number) this.widthValue.get()).doubleValue()}, { iaxisalignedbb.getMaxX() + ((Number) this.widthValue.get()).doubleValue(), iaxisalignedbb.getMinY(), iaxisalignedbb.getMaxZ() + ((Number) this.widthValue.get()).doubleValue()}});
                    float f = FloatCompanionObject.INSTANCE.getMAX_VALUE();
                    float minY = FloatCompanionObject.INSTANCE.getMAX_VALUE();
                    float f1 = -1.0F;
                    float maxY = -1.0F;
                    double[][] adouble = boxVertices;
                    int i = boxVertices.length;

                    for (int $i$f$unwrap = 0; $i$f$unwrap < i; ++$i$f$unwrap) {
                        double[] $this$unwrap$iv = adouble[$i$f$unwrap];
                        Vector2f vector2f = WorldToScreen.worldToScreen(new Vector3f((float) $this$unwrap$iv[0], (float) $this$unwrap$iv[1], (float) $this$unwrap$iv[2]), mvMatrix, projectionMatrix, MinecraftInstance.mc.getDisplayWidth(), MinecraftInstance.mc.getDisplayHeight());

                        if (vector2f != null) {
                            Vector2f screenPos = vector2f;
                            float f2 = screenPos.x;
                            boolean flag = false;

                            f = Math.min(f2, f);
                            f2 = screenPos.y;
                            flag = false;
                            minY = Math.min(f2, minY);
                            f2 = screenPos.x;
                            flag = false;
                            f1 = Math.max(f2, f1);
                            f2 = screenPos.y;
                            flag = false;
                            maxY = Math.max(f2, maxY);
                        }
                    }

                    if (f <= (float) 0 && minY <= (float) 0 && f1 > (float) MinecraftInstance.mc.getDisplayWidth() && maxY > (float) MinecraftInstance.mc.getDisplayWidth()) {
                        continue;
                    }

                    if (((Boolean) this.healthValue.get()).booleanValue()) {
                        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glBegin(7);
                        GL11.glVertex2f(f - 8.0F, minY - 1.0F);
                        GL11.glVertex2f(f - 8.0F, maxY + 1.0F);
                        GL11.glVertex2f(f - 3.0F, maxY + 1.0F);
                        GL11.glVertex2f(f - 3.0F, minY - 1.0F);
                        GL11.glEnd();
                        GL11.glColor4f(0.2F, 0.2F, 0.2F, 1.0F);
                        GL11.glBegin(7);
                        GL11.glVertex2f(f - 7.0F, minY);
                        GL11.glVertex2f(f - 7.0F, maxY);
                        GL11.glVertex2f(f - 4.0F, maxY);
                        GL11.glVertex2f(f - 4.0F, minY);
                        GL11.glEnd();
                        RenderUtils.glColorHex(ColorUtils.INSTANCE.getHealthColor(entityLiving.getHealth(), entityLiving.getMaxHealth()));
                        GL11.glBegin(7);
                        GL11.glVertex2f(f - 7.0F, maxY - entityLiving.getHealth() / entityLiving.getMaxHealth() * (maxY - minY));
                        GL11.glVertex2f(f - 7.0F, maxY);
                        GL11.glVertex2f(f - 4.0F, maxY);
                        GL11.glVertex2f(f - 4.0F, maxY - entityLiving.getHealth() / entityLiving.getMaxHealth() * (maxY - minY));
                        GL11.glEnd();
                    }

                    if (((Boolean) this.outlineValue.get()).booleanValue()) {
                        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glLineWidth(1.0F);
                        GL11.glBegin(2);
                        GL11.glVertex2f(f - 1.0F, minY - 1.0F);
                        GL11.glVertex2f(f - 1.0F, maxY + 1.0F);
                        GL11.glVertex2f(f1 + 1.0F, maxY + 1.0F);
                        GL11.glVertex2f(f1 + 1.0F, minY - 1.0F);
                        GL11.glEnd();
                        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glLineWidth(1.0F);
                        GL11.glBegin(2);
                        GL11.glVertex2f(f + 1.0F, minY + 1.0F);
                        GL11.glVertex2f(f + 1.0F, maxY - 1.0F);
                        GL11.glVertex2f(f1 - 1.0F, maxY - 1.0F);
                        GL11.glVertex2f(f1 - 1.0F, minY + 1.0F);
                        GL11.glEnd();
                    }

                    GL11.glColor4f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, 1.0F);
                    GL11.glLineWidth(1.0F);
                    GL11.glBegin(2);
                    GL11.glVertex2f(f, minY);
                    GL11.glVertex2f(f, maxY);
                    GL11.glVertex2f(f1, maxY);
                    GL11.glVertex2f(f1, minY);
                    GL11.glEnd();
                    if (((Boolean) this.nameValue.get()).booleanValue()) {
                        if (!LiquidBounce.INSTANCE.getModuleManager().getModule(NameTag.class).getState()) {
                            GL11.glEnable(3553);
                            GL11.glEnable(2929);
                            IFontRenderer ifontrenderer = MinecraftInstance.mc.getFontRendererObj();
                            IIChatComponent iichatcomponent = entityLiving.getDisplayName();

                            if (iichatcomponent == null) {
                                Intrinsics.throwNpe();
                            }

                            ifontrenderer.drawCenteredString(iichatcomponent.getFormattedText(), f + (f1 - f) / 2.0F, minY - 2.0F - (float) MinecraftInstance.mc.getFontRendererObj().getFontHeight(), -1, true);
                            GL11.glDisable(3553);
                            GL11.glDisable(2929);
                        }

                        GL11.glEnable(3553);
                        GL11.glEnable(2929);
                        IFontRenderer ifontrenderer1 = MinecraftInstance.mc.getFontRendererObj();
                        boolean flag1 = false;

                        if (entityLiving == null) {
                            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.EntityLivingBaseImpl<*>");
                        }

                        EntityLivingBase entitylivingbase = (EntityLivingBase) ((EntityLivingBaseImpl) entityLiving).getWrapped();
                        ItemStack itemstack = entitylivingbase.getHeldItemMainhand();

                        Intrinsics.checkExpressionValueIsNotNull(itemstack, "entityLiving.unwrap().heldItemMainhand");
                        String s2 = itemstack.getDisplayName();

                        Intrinsics.checkExpressionValueIsNotNull(s2, "entityLiving.unwrap().heldItemMainhand.displayName");
                        ifontrenderer1.drawCenteredString(s2, f + (f1 - f) / 2.0F, maxY + 2.0F, -1, true);
                        GL11.glDisable(3553);
                        GL11.glDisable(2929);
                    }
                    continue;

                case -349378602:
                    if (s1.equals("cylinder")) {
                        irendermanager = MinecraftInstance.mc.getRenderManager();
                        timer = MinecraftInstance.mc.getTimer();
                        bb = entityLiving.getLastTickPosX() + (entityLiving.getPosX() - entityLiving.getLastTickPosX()) * (double) timer.getRenderPartialTicks() - irendermanager.getRenderPosX();
                        minX = entityLiving.getLastTickPosY() + (entityLiving.getPosY() - entityLiving.getLastTickPosY()) * (double) timer.getRenderPartialTicks() - irendermanager.getRenderPosY();
                        maxX = entityLiving.getLastTickPosZ() + (entityLiving.getPosZ() - entityLiving.getLastTickPosZ()) * (double) timer.getRenderPartialTicks() - irendermanager.getRenderPosZ();
                        RenderUtils.drawWolframEntityESP(entity, color.getRGB(), bb, minX, maxX);
                    }
                    continue;

                case 1650:
                    if (s1.equals("2d")) {
                        irendermanager = MinecraftInstance.mc.getRenderManager();
                        timer = MinecraftInstance.mc.getTimer();
                        bb = entityLiving.getLastTickPosX() + (entityLiving.getPosX() - entityLiving.getLastTickPosX()) * (double) timer.getRenderPartialTicks() - irendermanager.getRenderPosX();
                        minX = entityLiving.getLastTickPosY() + (entityLiving.getPosY() - entityLiving.getLastTickPosY()) * (double) timer.getRenderPartialTicks() - irendermanager.getRenderPosY();
                        maxX = entityLiving.getLastTickPosZ() + (entityLiving.getPosZ() - entityLiving.getLastTickPosZ()) * (double) timer.getRenderPartialTicks() - irendermanager.getRenderPosZ();
                        int j = color.getRGB();
                        Color color = Color.BLACK;

                        Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
                        RenderUtils.draw2D(entityLiving, bb, minX, maxX, j, color.getRGB());
                    }
                    continue;

                case 97739:
                    if (!s1.equals("box")) {
                        continue;
                    }
                    break;

                case 49252889:
                    if (s1.equals("2dbox")) {
                        RenderUtils.draw2DBox(entity, color, event);
                    }

                default:
                    continue;
                }

                RenderUtils.drawEntityBox(entity, color, !StringsKt.equals(mode, "otherbox", true));
            }
        }

        if (real2d) {
            GL11.glEnable(2929);
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }

    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String partialTicks = (String) this.modeValue.get();
        boolean shader = false;

        if (partialTicks == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s = partialTicks.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
            String mode = s;
            float partialTicks1 = event.getPartialTicks();
            FramebufferShader framebuffershader = StringsKt.equals(mode, "shaderoutline", true) ? (FramebufferShader) OutlineShader.OUTLINE_SHADER : (FramebufferShader) (StringsKt.equals(mode, "shaderglow", true) ? GlowShader.GLOW_SHADER : null);

            if (framebuffershader != null) {
                FramebufferShader shader1 = framebuffershader;
                float radius = StringsKt.equals(mode, "shaderoutline", true) ? ((Number) this.shaderOutlineRadius.get()).floatValue() : (StringsKt.equals(mode, "shaderglow", true) ? ((Number) this.shaderGlowRadius.get()).floatValue() : 1.0F);

                ESP.renderNameTags = false;

                try {
                    HashMap ex = new HashMap();
                    IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                    if (iworldclient == null) {
                        Intrinsics.throwNpe();
                    }

                    Iterator iterator = iworldclient.getLoadedEntityList().iterator();

                    Color color;

                    while (iterator.hasNext()) {
                        IEntity entity = (IEntity) iterator.next();

                        if (EntityUtils.isSelected(entity, false) && (!AntiBot.isBot(entity.asEntityLivingBase()) || ((Boolean) this.botValue.get()).booleanValue())) {
                            color = this.getColor(entity);
                            if (!ex.containsKey(color)) {
                                ex.put(color, new ArrayList());
                            }

                            Object object = ex.get(color);

                            if (object == null) {
                                Intrinsics.throwNpe();
                            }

                            ((ArrayList) object).add(entity);
                        }
                    }

                    Map color1 = (Map) ex;
                    boolean arr = false;

                    iterator = color1.entrySet().iterator();

                    while (iterator.hasNext()) {
                        Entry entity2 = (Entry) iterator.next();
                        boolean flag = false;

                        color = (Color) entity2.getKey();
                        flag = false;
                        ArrayList arr1 = (ArrayList) entity2.getValue();

                        shader1.startDraw(partialTicks1);
                        Iterator iterator1 = arr1.iterator();

                        while (iterator1.hasNext()) {
                            IEntity entity1 = (IEntity) iterator1.next();
                            IRenderManager irendermanager = MinecraftInstance.mc.getRenderManager();

                            Intrinsics.checkExpressionValueIsNotNull(entity1, "entity");
                            irendermanager.renderEntityStatic(entity1, partialTicks1, true);
                        }

                        shader1.stopDraw(color, radius, 1.0F);
                    }
                } catch (Exception exception) {
                    ClientUtils.getLogger().error("An error occurred while rendering all entities for shader esp", (Throwable) exception);
                }

                ESP.renderNameTags = true;
                shader1.stopDraw(this.getColor((IEntity) null), radius, 1.0F);
            }
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }

    @NotNull
    public final Color getColor(@Nullable IEntity entity) {
        boolean flag = false;
        boolean flag1 = false;
        ESP $this$run = (ESP) this;
        boolean $i$a$-run-ESP$getColor$1 = false;

        if (entity != null && MinecraftInstance.classProvider.isEntityLivingBase(entity)) {
            IEntityLivingBase entityLivingBase = entity.asEntityLivingBase();

            if (MinecraftInstance.classProvider.isEntityPlayer(entityLivingBase) && PlayerExtensionKt.isClientFriend(entityLivingBase.asEntityPlayer())) {
                Color color = Color.BLUE;

                Intrinsics.checkExpressionValueIsNotNull(Color.BLUE, "Color.BLUE");
                return color;
            }

            if (((Boolean) $this$run.colorTeam.get()).booleanValue()) {
                IIChatComponent iichatcomponent = entityLivingBase.getDisplayName();

                if (iichatcomponent != null) {
                    String color = iichatcomponent.getFormattedText();
                    boolean i = false;

                    if (color == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    char[] achar = color.toCharArray();

                    Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
                    char[] chars = achar;
                    int i = Integer.MAX_VALUE;
                    int j = 0;

                    for (int k = chars.length; j < k; ++j) {
                        if (chars[j] == 167 && j + 1 < chars.length) {
                            int index = GameFontRenderer.Companion.getColorIndex(chars[j + 1]);

                            if (index >= 0 && index <= 15) {
                                i = ColorUtils.hexColors[index];
                                break;
                            }
                        }
                    }

                    return new Color(i);
                }
            }
        }

        return ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue());
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0005"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/ESP$Companion;", "", "()V", "renderNameTags", "", "LiquidBounce"}
    )
    public static final class Companion {

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
