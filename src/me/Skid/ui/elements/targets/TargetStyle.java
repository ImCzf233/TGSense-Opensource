package me.Skid.ui.elements.targets;

import java.awt.Color;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import me.Skid.ui.elements.LBplusTarget;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ&\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020-JX\u00101\u001a\u00020+2\u0006\u00102\u001a\u0002032\u0006\u0010,\u001a\u00020\u00122\u0006\u0010.\u001a\u00020\u00122\u0006\u00104\u001a\u00020\u00122\u0006\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020-2\u0006\u00105\u001a\u00020\u00122\u0006\u00106\u001a\u00020\u00122\u0006\u00107\u001a\u00020\u00122\b\b\u0002\u00108\u001a\u00020\u0012J<\u00101\u001a\u00020+2\u0006\u00102\u001a\u0002032\b\b\u0002\u0010,\u001a\u00020-2\b\b\u0002\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020-2\b\b\u0002\u00108\u001a\u00020\u0012J\u0010\u00109\u001a\u00020+2\u0006\u0010:\u001a\u00020;H&J\u0014\u0010<\u001a\u0004\u0018\u00010=2\b\u0010:\u001a\u0004\u0018\u00010;H&J\u000e\u0010>\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020\u001cJ\u000e\u0010>\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020-J\u0010\u0010@\u001a\u00020+2\u0006\u0010A\u001a\u00020BH\u0016J\u0010\u0010C\u001a\u00020+2\u0006\u0010A\u001a\u00020BH\u0016J\u0010\u0010D\u001a\u00020+2\u0006\u0010A\u001a\u00020BH\u0016J\u0010\u0010E\u001a\u00020+2\u0006\u0010A\u001a\u00020BH\u0016J\u0010\u0010F\u001a\u00020+2\u0006\u0010G\u001a\u00020\u0012H\u0016R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u000f\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001e\u0010%\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\'0&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u0006H"},
    d2 = { "Lme/Skid/ui/elements/targets/TargetStyle;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "name", "", "targetInstance", "Lme/Skid/ui/elements/LBplusTarget;", "shaderSupport", "", "(Ljava/lang/String;Lme/Skid/ui/elements/LBplusTarget;Z)V", "decimalFormat", "Ljava/text/DecimalFormat;", "getDecimalFormat", "()Ljava/text/DecimalFormat;", "decimalFormat2", "getDecimalFormat2", "decimalFormat3", "getDecimalFormat3", "easingHealth", "", "getEasingHealth", "()F", "setEasingHealth", "(F)V", "getName", "()Ljava/lang/String;", "getShaderSupport", "()Z", "shadowOpaque", "Ljava/awt/Color;", "getShadowOpaque", "()Ljava/awt/Color;", "shieldIcon", "Lnet/minecraft/util/ResourceLocation;", "getShieldIcon", "()Lnet/minecraft/util/ResourceLocation;", "getTargetInstance", "()Lme/Skid/ui/elements/LBplusTarget;", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "()Ljava/util/List;", "drawArmorIcon", "", "x", "", "y", "width", "height", "drawHead", "skin", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "scale", "red", "green", "blue", "alpha", "drawTarget", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getColor", "color", "handleBlur", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "handleDamage", "handleShadow", "handleShadowCut", "updateAnim", "targetHealth", "LiquidBounce"}
)
public abstract class TargetStyle extends MinecraftInstance {

    private float easingHealth;
    @NotNull
    private final ResourceLocation shieldIcon;
    @NotNull
    private final DecimalFormat decimalFormat;
    @NotNull
    private final DecimalFormat decimalFormat2;
    @NotNull
    private final DecimalFormat decimalFormat3;
    @NotNull
    private final String name;
    @NotNull
    private final LBplusTarget targetInstance;
    private final boolean shaderSupport;

    public final float getEasingHealth() {
        return this.easingHealth;
    }

    public final void setEasingHealth(float <set-?>) {
        this.easingHealth = <set-?>;
    }

    @NotNull
    public final ResourceLocation getShieldIcon() {
        return this.shieldIcon;
    }

    @NotNull
    public final DecimalFormat getDecimalFormat() {
        return this.decimalFormat;
    }

    @NotNull
    public final DecimalFormat getDecimalFormat2() {
        return this.decimalFormat2;
    }

    @NotNull
    public final DecimalFormat getDecimalFormat3() {
        return this.decimalFormat3;
    }

    @NotNull
    public final Color getShadowOpaque() {
        String s = (String) this.targetInstance.getShadowColorMode().get();
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
                    color = new Color(((Number) this.targetInstance.getShadowColorRedValue().get()).intValue(), ((Number) this.targetInstance.getShadowColorGreenValue().get()).intValue(), ((Number) this.targetInstance.getShadowColorBlueValue().get()).intValue());
                    return ColorUtils.reAlpha(color, 1.0F - this.targetInstance.getAnimProgress());
                }
                break;

            case -1332194002:
                if (s.equals("background")) {
                    color = this.targetInstance.getBgColor();
                    return ColorUtils.reAlpha(color, 1.0F - this.targetInstance.getAnimProgress());
                }
            }

            color = this.targetInstance.getBarColor();
            return ColorUtils.reAlpha(color, 1.0F - this.targetInstance.getAnimProgress());
        }
    }

    public abstract void drawTarget(@NotNull IEntityLivingBase ientitylivingbase);

    @Nullable
    public abstract Border getBorder(@Nullable IEntityLivingBase ientitylivingbase);

    public void updateAnim(float targetHealth) {
        if (((Boolean) this.targetInstance.getNoAnimValue().get()).booleanValue()) {
            this.easingHealth = targetHealth;
        } else {
            float f = this.easingHealth;
            float f1 = targetHealth - this.easingHealth;
            float f2 = 2.0F;
            float f3 = 10.0F - ((Number) this.targetInstance.getGlobalAnimSpeed().get()).floatValue();
            float f4 = f1;
            float f5 = f;
            boolean flag = false;
            float f6 = (float) Math.pow((double) f2, (double) f3);

            this.easingHealth = f5 + f4 / f6 * (float) RenderUtils.deltaTime;
        }

    }

    public void handleDamage(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
    }

    public void handleBlur(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
    }

    public void handleShadowCut(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
    }

    public void handleShadow(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
    }

    @NotNull
    public List getValues() {
        Field[] afield = this.getClass().getDeclaredFields();

        Intrinsics.checkExpressionValueIsNotNull(afield, "javaClass.declaredFields");
        Field[] $this$filterIsInstance$iv = afield;
        boolean $i$f$filterIsInstance = false;
        Collection destination$iv$iv = (Collection) (new ArrayList($this$filterIsInstance$iv.length));
        boolean $i$f$filterIsInstanceTo = false;
        Field[] afield1 = $this$filterIsInstance$iv;
        int element$iv$iv = $this$filterIsInstance$iv.length;

        for (int i = 0; i < element$iv$iv; ++i) {
            Field item$iv$iv = afield1[i];
            boolean $i$a$-map-TargetStyle$values$1 = false;

            Intrinsics.checkExpressionValueIsNotNull(item$iv$iv, "valueField");
            item$iv$iv.setAccessible(true);
            Object object = item$iv$iv.get(this);

            destination$iv$iv.add(object);
        }

        Iterable iterable = (Iterable) ((List) destination$iv$iv);

        $i$f$filterIsInstance = false;
        destination$iv$iv = (Collection) (new ArrayList());
        $i$f$filterIsInstanceTo = false;
        Iterator iterator = iterable.iterator();

        while (iterator.hasNext()) {
            Object object1 = iterator.next();

            if (object1 instanceof Value) {
                destination$iv$iv.add(object1);
            }
        }

        return (List) destination$iv$iv;
    }

    @NotNull
    public final Color getColor(@NotNull Color color) {
        Intrinsics.checkParameterIsNotNull(color, "color");
        return ColorUtils.reAlpha(color, (float) color.getAlpha() / 255.0F * (1.0F - this.targetInstance.getFadeProgress()));
    }

    @NotNull
    public final Color getColor(int color) {
        return this.getColor(new Color(color));
    }

    public final void drawHead(@NotNull IResourceLocation skin, int x, int y, int width, int height, float alpha) {
        Intrinsics.checkParameterIsNotNull(skin, "skin");
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
        MinecraftInstance.mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawHead$default(TargetStyle targetstyle, IResourceLocation iresourcelocation, int i, int j, int k, int l, float f, int i1, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawHead");
        } else {
            if ((i1 & 2) != 0) {
                i = 2;
            }

            if ((i1 & 4) != 0) {
                j = 2;
            }

            if ((i1 & 32) != 0) {
                f = 1.0F;
            }

            targetstyle.drawHead(iresourcelocation, i, j, k, l, f);
        }
    }

    public final void drawHead(@NotNull IResourceLocation skin, float x, float y, float scale, int width, int height, float red, float green, float blue, float alpha) {
        Intrinsics.checkParameterIsNotNull(skin, "skin");
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        GL11.glScalef(scale, scale, scale);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(RangesKt.coerceIn(red, 0.0F, 1.0F), RangesKt.coerceIn(green, 0.0F, 1.0F), RangesKt.coerceIn(blue, 0.0F, 1.0F), RangesKt.coerceIn(alpha, 0.0F, 1.0F));
        MinecraftInstance.mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(0, 0, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawHead$default(TargetStyle targetstyle, IResourceLocation iresourcelocation, float f, float f1, float f2, int i, int j, float f3, float f4, float f5, float f6, int k, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: drawHead");
        } else {
            if ((k & 512) != 0) {
                f6 = 1.0F;
            }

            targetstyle.drawHead(iresourcelocation, f, f1, f2, i, j, f3, f4, f5, f6);
        }
    }

    public final void drawArmorIcon(int x, int y, int width, int height) {
        GlStateManager.disableAlpha();
        RenderUtils.drawImage4(this.shieldIcon, x, y, width, height);
        GlStateManager.enableAlpha();
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final LBplusTarget getTargetInstance() {
        return this.targetInstance;
    }

    public final boolean getShaderSupport() {
        return this.shaderSupport;
    }

    public TargetStyle(@NotNull String name, @NotNull LBplusTarget targetInstance, boolean shaderSupport) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(targetInstance, "targetInstance");
        super();
        this.name = name;
        this.targetInstance = targetInstance;
        this.shaderSupport = shaderSupport;
        this.shieldIcon = new ResourceLocation("loserline/shield.png");
        this.decimalFormat = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.ENGLISH));
        this.decimalFormat2 = new DecimalFormat("##0.0", new DecimalFormatSymbols(Locale.ENGLISH));
        this.decimalFormat3 = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH));
    }
}
