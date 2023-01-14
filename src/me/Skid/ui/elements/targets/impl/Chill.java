package me.Skid.ui.elements.targets.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import me.Skid.ui.elements.LBplusTarget;
import me.Skid.ui.elements.targets.TargetStyle;
import me.Skid.ui.elements.targets.utils.CharRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.ColorExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J&\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"},
    d2 = { "Lme/Skid/ui/elements/targets/impl/Chill;", "Lme/Skid/ui/elements/targets/TargetStyle;", "inst", "Lme/Skid/ui/elements/LBplusTarget;", "(Lme/Skid/ui/elements/LBplusTarget;)V", "calcScaleX", "", "calcScaleY", "calcTranslateX", "calcTranslateY", "chillFontSpeed", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getChillFontSpeed", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "chillRoundValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getChillRoundValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "numberRenderer", "Lme/Skid/ui/elements/targets/utils/CharRenderer;", "drawTarget", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "handleBlur", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "handleShadow", "handleShadowCut", "updateData", "_a", "_b", "_c", "_d", "LiquidBounce"}
)
public final class Chill extends TargetStyle {

    @NotNull
    private final FloatValue chillFontSpeed;
    @NotNull
    private final BoolValue chillRoundValue;
    private final CharRenderer numberRenderer;
    private float calcScaleX;
    private float calcScaleY;
    private float calcTranslateX;
    private float calcTranslateY;

    @NotNull
    public final FloatValue getChillFontSpeed() {
        return this.chillFontSpeed;
    }

    @NotNull
    public final BoolValue getChillRoundValue() {
        return this.chillRoundValue;
    }

    public final void updateData(float _a, float _b, float _c, float _d) {
        this.calcTranslateX = _a;
        this.calcTranslateY = _b;
        this.calcScaleX = _c;
        this.calcScaleY = _d;
    }

    public void drawTarget(@NotNull IEntityLivingBase entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        this.updateAnim(entity.getHealth());
        String name = entity.getName();
        float health = entity.getHealth();
        IFontRenderer ifontrenderer = Fonts.font40;

        if (name == null) {
            Intrinsics.throwNpe();
        }

        int i = ifontrenderer.getStringWidth(name);
        IFontRenderer ifontrenderer1 = Fonts.fontBold72;
        String s = this.getDecimalFormat().format(Float.valueOf(health));

        Intrinsics.checkExpressionValueIsNotNull(s, "decimalFormat.format(health)");
        float tWidth = RangesKt.coerceAtLeast(45.0F + (float) RangesKt.coerceAtLeast(i, ifontrenderer1.getStringWidth(s)), 120.0F);
        INetworkPlayerInfo playerInfo = MinecraftInstance.mc.getNetHandler().getPlayerInfo(entity.getUniqueID());

        RenderUtils.drawRoundedRect(0.0F, 0.0F, tWidth, 48.0F, 7, this.getTargetInstance().getBgColor().getRGB());
        GlStateManager.resetColor();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (playerInfo != null) {
            Stencil.write(false);
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            RenderUtils.fastRoundedRect(4.0F, 4.0F, 34.0F, 34.0F, 7.0F);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            Stencil.erase(true);
            this.drawHead(playerInfo.getLocationSkin(), 4, 4, 30, 30, 1.0F - this.getTargetInstance().getFadeProgress());
            Stencil.dispose();
        }

        GlStateManager.resetColor();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Fonts.font40.drawString(name, 38.0F, 6.0F, this.getColor(-1).getRGB());
        this.numberRenderer.renderChar(health, this.calcTranslateX, this.calcTranslateY, 38.0F, 17.0F, this.calcScaleX, this.calcScaleY, false, ((Number) this.chillFontSpeed.get()).floatValue(), this.getColor(-1).getRGB());
        RenderUtils.drawRoundedRect(4.0F, 38.0F, tWidth - 4.0F, 44.0F, 3, ColorExtensionKt.darker(this.getTargetInstance().getBarColor(), 0.5F).getRGB());
        Stencil.write(false);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        RenderUtils.fastRoundedRect(4.0F, 38.0F, tWidth - 4.0F, 44.0F, 3.0F);
        GL11.glDisable(3042);
        Stencil.erase(true);
        if (((Boolean) this.chillRoundValue.get()).booleanValue()) {
            RenderUtils.customRounded(4.0F, 38.0F, 4.0F + this.getEasingHealth() / entity.getMaxHealth() * (tWidth - 8.0F), 44.0F, 0.0F, 3.0F, 3.0F, 0.0F, this.getTargetInstance().getBarColor().getRGB());
        } else {
            RenderUtils.drawRect(4.0F, 38.0F, 4.0F + this.getEasingHealth() / entity.getMaxHealth() * (tWidth - 8.0F), 44.0F, this.getTargetInstance().getBarColor().getRGB());
        }

        Stencil.dispose();
    }

    public void handleBlur(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        IFontRenderer ifontrenderer = Fonts.font40;
        String s = player.getName();

        if (s == null) {
            Intrinsics.throwNpe();
        }

        int i = ifontrenderer.getStringWidth(s);
        IFontRenderer ifontrenderer1 = Fonts.fontBold72;
        String s1 = this.getDecimalFormat().format(Float.valueOf(player.getHealth()));

        Intrinsics.checkExpressionValueIsNotNull(s1, "decimalFormat.format(player.health)");
        float tWidth = RangesKt.coerceAtLeast(45.0F + (float) RangesKt.coerceAtLeast(i, ifontrenderer1.getStringWidth(s1)), 120.0F);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderUtils.fastRoundedRect(0.0F, 0.0F, tWidth, 48.0F, 7.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void handleShadowCut(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        this.handleBlur(player);
    }

    public void handleShadow(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        IFontRenderer ifontrenderer = Fonts.font40;
        String s = player.getName();

        if (s == null) {
            Intrinsics.throwNpe();
        }

        int i = ifontrenderer.getStringWidth(s);
        IFontRenderer ifontrenderer1 = Fonts.fontBold72;
        String s1 = this.getDecimalFormat().format(Float.valueOf(player.getHealth()));

        Intrinsics.checkExpressionValueIsNotNull(s1, "decimalFormat.format(player.health)");
        float tWidth = RangesKt.coerceAtLeast(45.0F + (float) RangesKt.coerceAtLeast(i, ifontrenderer1.getStringWidth(s1)), 120.0F);

        RenderUtils.drawRoundedRect(0.0F, 0.0F, tWidth, 48.0F, 7, this.getShadowOpaque().getRGB());
    }

    @Nullable
    public Border getBorder(@Nullable IEntityLivingBase entity) {
        if (entity != null) {
            IFontRenderer ifontrenderer = Fonts.font40;
            String s = entity.getName();

            if (s == null) {
                Intrinsics.throwNpe();
            }

            int i = ifontrenderer.getStringWidth(s);
            IFontRenderer ifontrenderer1 = Fonts.fontBold72;
            String s1 = this.getDecimalFormat().format(Float.valueOf(entity.getHealth()));

            Intrinsics.checkExpressionValueIsNotNull(s1, "decimalFormat.format(entity.health)");
            float tWidth = RangesKt.coerceAtLeast(45.0F + (float) RangesKt.coerceAtLeast(i, ifontrenderer1.getStringWidth(s1)), 120.0F);

            return new Border(0.0F, 0.0F, tWidth, 48.0F);
        } else {
            return new Border(0.0F, 0.0F, 120.0F, 48.0F);
        }
    }

    public Chill(@NotNull LBplusTarget inst) {
        Intrinsics.checkParameterIsNotNull(inst, "inst");
        super("Chill", inst, true);
        this.chillFontSpeed = new FloatValue("Chill-FontSpeed", 0.5F, 0.01F, 1.0F);
        this.chillRoundValue = new BoolValue("Chill-RoundedBar", true);
        this.numberRenderer = new CharRenderer(false);
    }
}
