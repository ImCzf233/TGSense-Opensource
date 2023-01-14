package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.utils.Colors;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "Crosshair",
    description = "Cancels blindness effects.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/Crosshair;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorBlueValue", "colorGreenValue", "colorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "colorRedValue", "crosshairColor", "Ljava/awt/Color;", "getCrosshairColor", "()Ljava/awt/Color;", "dynamicValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "gapValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "hitMarkerValue", "sizeValue", "widthValue", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "LiquidBounce"}
)
public final class Crosshair extends Module {

    private final ListValue colorModeValue = new ListValue("Color", new String[] { "Custom", "Slowly", "Rainbow"}, "Custom");
    private final IntegerValue colorRedValue = new IntegerValue("Red", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("Green", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
    private final IntegerValue colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
    private final FloatValue widthValue = new FloatValue("Width", 0.5F, 0.25F, 10.0F);
    private final FloatValue sizeValue = new FloatValue("Length", 7.0F, 0.25F, 15.0F);
    private final FloatValue gapValue = new FloatValue("Gap", 5.0F, 0.25F, 15.0F);
    private final BoolValue dynamicValue = new BoolValue("Dynamic", true);
    private final BoolValue hitMarkerValue = new BoolValue("HitMarker", true);

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IClassProvider iclassprovider = MinecraftInstance.classProvider;
        IMinecraft iminecraft = MinecraftInstance.mc;

        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
        IScaledResolution sr = iclassprovider.createScaledResolution(iminecraft);
        float width = ((Number) this.widthValue.get()).floatValue();
        float size = ((Number) this.sizeValue.get()).floatValue();
        float gap = ((Number) this.gapValue.get()).floatValue();
        boolean isMoving = ((Boolean) this.dynamicValue.get()).booleanValue() && MovementUtils.isMoving();

        GL11.glPushMatrix();
        RenderUtils.drawBorderedRect((float) sr.getScaledWidth() / 2.0F - width, (float) sr.getScaledHeight() / 2.0F - gap - size - (float) (isMoving ? 2 : 0), (float) sr.getScaledWidth() / 2.0F + 1.0F + width, (float) sr.getScaledHeight() / 2.0F - gap - (float) (isMoving ? 2 : 0), 0.5F, (new Color(0, 0, 0)).getRGB(), this.getCrosshairColor().getRGB());
        RenderUtils.drawBorderedRect((float) sr.getScaledWidth() / 2.0F - width, (float) sr.getScaledHeight() / 2.0F + gap + (float) 1 + (float) (isMoving ? 2 : 0) - 0.15F, (float) sr.getScaledWidth() / 2.0F + 1.0F + width, (float) sr.getScaledHeight() / 2.0F + (float) 1 + gap + size + (float) (isMoving ? 2 : 0) - 0.15F, 0.5F, (new Color(0, 0, 0)).getRGB(), this.getCrosshairColor().getRGB());
        RenderUtils.drawBorderedRect((float) sr.getScaledWidth() / 2.0F - gap - size - (float) (isMoving ? 2 : 0) + 0.15F, (float) sr.getScaledHeight() / 2.0F - width, (float) sr.getScaledWidth() / 2.0F - gap - (float) (isMoving ? 2 : 0) + 0.15F, (float) (sr.getScaledHeight() / 2) + 1.0F + width, 0.5F, (new Color(0, 0, 0)).getRGB(), this.getCrosshairColor().getRGB());
        RenderUtils.drawBorderedRect((float) sr.getScaledWidth() / 2.0F + (float) 1 + gap + (float) (isMoving ? 2 : 0), (float) sr.getScaledHeight() / 2.0F - width, (float) sr.getScaledWidth() / 2.0F + size + gap + 1.0F + (float) (isMoving ? 2 : 0), (float) (sr.getScaledHeight() / 2) + 1.0F + width, 0.5F, (new Color(0, 0, 0)).getRGB(), this.getCrosshairColor().getRGB());
        GL11.glPopMatrix();
        GlStateManager.resetColor();
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        } else {
            KillAura aura = (KillAura) module;
            IEntityLivingBase target = aura.getTarget();

            if (((Boolean) this.hitMarkerValue.get()).booleanValue() && target != null && target.getHurtTime() > 0) {
                GL11.glPushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableTexture2D();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, (float) target.getHurtTime() / (float) target.getMaxHurtTime());
                GL11.glEnable(2848);
                GL11.glLineWidth(1.0F);
                GL11.glBegin(3);
                GL11.glVertex2f((float) sr.getScaledWidth() / 2.0F + gap, (float) sr.getScaledHeight() / 2.0F + gap);
                GL11.glVertex2f((float) sr.getScaledWidth() / 2.0F + gap + size, (float) sr.getScaledHeight() / 2.0F + gap + size);
                GL11.glEnd();
                GL11.glBegin(3);
                GL11.glVertex2f((float) sr.getScaledWidth() / 2.0F - gap, (float) sr.getScaledHeight() / 2.0F - gap);
                GL11.glVertex2f((float) sr.getScaledWidth() / 2.0F - gap - size, (float) sr.getScaledHeight() / 2.0F - gap - size);
                GL11.glEnd();
                GL11.glBegin(3);
                GL11.glVertex2f((float) sr.getScaledWidth() / 2.0F - gap, (float) sr.getScaledHeight() / 2.0F + gap);
                GL11.glVertex2f((float) sr.getScaledWidth() / 2.0F - gap - size, (float) sr.getScaledHeight() / 2.0F + gap + size);
                GL11.glEnd();
                GL11.glBegin(3);
                GL11.glVertex2f((float) sr.getScaledWidth() / 2.0F + gap, (float) sr.getScaledHeight() / 2.0F - gap);
                GL11.glVertex2f((float) sr.getScaledWidth() / 2.0F + gap + size, (float) sr.getScaledHeight() / 2.0F - gap - size);
                GL11.glEnd();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GL11.glPopMatrix();
            }

        }
    }

    private final Color getCrosshairColor() {
        String s = (String) this.colorModeValue.get();
        boolean flag = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            Color color;

            switch (s.hashCode()) {
            case -1349088399:
                if (s.equals("custom")) {
                    color = new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), ((Number) this.colorAlphaValue.get()).intValue());
                    return color;
                }
                break;

            case -899450034:
                if (s.equals("slowly")) {
                    color = ColorUtils.rainbow();
                    return color;
                }
                break;

            case 973576630:
                if (s.equals("rainbow")) {
                    color = Colors.getColorFromInt(Colors.getRainbow(500, 0, 0.6F));
                    Intrinsics.checkExpressionValueIsNotNull(color, "Colors.getColorFromInt(C…getRainbow(500, 0, 0.6F))");
                    return color;
                }
            }

            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            return color;
        }
    }
}
