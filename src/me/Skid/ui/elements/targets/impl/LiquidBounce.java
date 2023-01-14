package me.Skid.ui.elements.targets.impl;

import java.awt.Color;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import me.Skid.ui.elements.LBplusTarget;
import me.Skid.ui.elements.targets.TargetStyle;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001cH\u0016J\u0014\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\u001f\u001a\u0004\u0018\u00010\u001cH\u0016J\u0010\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010&\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020$H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u0011\u0010\u0011\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\'"},
    d2 = { "Lme/Skid/ui/elements/targets/impl/LiquidBounce;", "Lme/Skid/ui/elements/targets/TargetStyle;", "inst", "Lme/Skid/ui/elements/LBplusTarget;", "(Lme/Skid/ui/elements/LBplusTarget;)V", "borderAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getBorderAlphaValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "borderBlueValue", "getBorderBlueValue", "borderColorMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getBorderColorMode", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "borderGreenValue", "getBorderGreenValue", "borderRedValue", "getBorderRedValue", "borderWidthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getBorderWidthValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "hurtTimeAnim", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getHurtTimeAnim", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "lastTarget", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "drawTarget", "", "entity", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "handleBlur", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "handleShadow", "handleShadowCut", "LiquidBounce"}
)
public final class LiquidBounce extends TargetStyle {

    @NotNull
    private final BoolValue hurtTimeAnim;
    @NotNull
    private final ListValue borderColorMode;
    @NotNull
    private final FloatValue borderWidthValue;
    @NotNull
    private final IntegerValue borderRedValue;
    @NotNull
    private final IntegerValue borderGreenValue;
    @NotNull
    private final IntegerValue borderBlueValue;
    @NotNull
    private final IntegerValue borderAlphaValue;
    private IEntityLivingBase lastTarget;

    @NotNull
    public final BoolValue getHurtTimeAnim() {
        return this.hurtTimeAnim;
    }

    @NotNull
    public final ListValue getBorderColorMode() {
        return this.borderColorMode;
    }

    @NotNull
    public final FloatValue getBorderWidthValue() {
        return this.borderWidthValue;
    }

    @NotNull
    public final IntegerValue getBorderRedValue() {
        return this.borderRedValue;
    }

    @NotNull
    public final IntegerValue getBorderGreenValue() {
        return this.borderGreenValue;
    }

    @NotNull
    public final IntegerValue getBorderBlueValue() {
        return this.borderBlueValue;
    }

    @NotNull
    public final IntegerValue getBorderAlphaValue() {
        return this.borderAlphaValue;
    }

    public void drawTarget(@NotNull IEntityLivingBase entity) {
        float width;
        label52: {
            Intrinsics.checkParameterIsNotNull(entity, "entity");
            if (!(Intrinsics.areEqual(entity, this.lastTarget) ^ true) && this.getEasingHealth() >= (float) 0 && this.getEasingHealth() <= entity.getMaxHealth()) {
                width = this.getEasingHealth() - entity.getHealth();
                boolean borderColor = false;

                if ((double) Math.abs(width) >= 0.01D) {
                    break label52;
                }
            }

            this.setEasingHealth(entity.getHealth());
        }

        IFontRenderer ifontrenderer = Fonts.font40;
        String s = entity.getName();

        if (s == null) {
            Intrinsics.throwNpe();
        }

        width = (float) RangesKt.coerceAtLeast(38 + ifontrenderer.getStringWidth(s), 118);
        Color borderColor1 = this.getColor(new Color(((Number) this.borderRedValue.get()).intValue(), ((Number) this.borderGreenValue.get()).intValue(), ((Number) this.borderBlueValue.get()).intValue(), ((Number) this.borderAlphaValue.get()).intValue()));

        if (StringsKt.equals((String) this.borderColorMode.get(), "none", true)) {
            RenderUtils.drawRect(0.0F, 0.0F, width, 36.0F, this.getTargetInstance().getBgColor().getRGB());
        } else {
            RenderUtils.drawBorderedRect(0.0F, 0.0F, width, 36.0F, ((Number) this.borderWidthValue.get()).floatValue(), StringsKt.equals((String) this.borderColorMode.get(), "matchbar", true) ? this.getTargetInstance().getBarColor().getRGB() : borderColor1.getRGB(), this.getTargetInstance().getBgColor().getRGB());
        }

        if (this.getEasingHealth() > entity.getHealth()) {
            RenderUtils.drawRect(0.0F, 34.0F, this.getEasingHealth() / entity.getMaxHealth() * width, 36.0F, this.getColor(new Color(252, 185, 65)).getRGB());
        }

        RenderUtils.drawRect(0.0F, 34.0F, entity.getHealth() / entity.getMaxHealth() * width, 36.0F, this.getTargetInstance().getBarColor().getRGB());
        if (this.getEasingHealth() < entity.getHealth()) {
            RenderUtils.drawRect(this.getEasingHealth() / entity.getMaxHealth() * width, 34.0F, entity.getHealth() / entity.getMaxHealth() * width, 36.0F, this.getColor(new Color(44, 201, 144)).getRGB());
        }

        this.updateAnim(entity.getHealth());
        IFontRenderer ifontrenderer1 = Fonts.font40;
        String s1 = entity.getName();

        if (s1 == null) {
            Intrinsics.throwNpe();
        }

        ifontrenderer1.drawString(s1, 36, 3, this.getColor(-1).getRGB());
        ifontrenderer1 = Fonts.fontSFUI35;
        StringBuilder stringbuilder = (new StringBuilder()).append("Distance: ");
        DecimalFormat decimalformat = this.getDecimalFormat();
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        ifontrenderer1.drawString(stringbuilder.append(decimalformat.format(PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp, (IEntity) entity))).toString(), 36, 15, this.getColor(-1).getRGB());
        INetworkPlayerInfo playerInfo = MinecraftInstance.mc.getNetHandler().getPlayerInfo(entity.getUniqueID());

        if (playerInfo != null) {
            Fonts.fontSFUI35.drawString("Ping: " + RangesKt.coerceAtLeast(playerInfo.getResponseTime(), 0), 36, 24, this.getColor(-1).getRGB());
            IResourceLocation locationSkin = playerInfo.getLocationSkin();

            if (((Boolean) this.hurtTimeAnim.get()).booleanValue()) {
                float scaleHT = RangesKt.coerceIn((float) entity.getHurtTime() / (float) RangesKt.coerceAtLeast(entity.getMaxHurtTime(), 1), 0.0F, 1.0F);

                TargetStyle.drawHead$default(this, locationSkin, 2.0F + 15.0F * scaleHT * 0.2F, 2.0F + 15.0F * scaleHT * 0.2F, 1.0F - scaleHT * 0.2F, 30, 30, 1.0F, 0.4F + (1.0F - scaleHT) * 0.6F, 0.4F + (1.0F - scaleHT) * 0.6F, 0.0F, 512, (Object) null);
            } else {
                TargetStyle.drawHead$default(this, locationSkin, 0, 0, 30, 30, 1.0F - this.getTargetInstance().getFadeProgress(), 6, (Object) null);
            }
        }

        this.lastTarget = entity;
    }

    public void handleBlur(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        IFontRenderer ifontrenderer = Fonts.font40;
        String s = player.getName();

        if (s == null) {
            Intrinsics.throwNpe();
        }

        float width = (float) RangesKt.coerceAtLeast(38 + ifontrenderer.getStringWidth(s), 118);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderUtils.quickDrawRect(0.0F, 0.0F, width, 36.0F);
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

        float width = (float) RangesKt.coerceAtLeast(38 + ifontrenderer.getStringWidth(s), 118);

        RenderUtils.drawRect(0.0F, 0.0F, width, 36.0F, this.getShadowOpaque().getRGB());
    }

    @Nullable
    public Border getBorder(@Nullable IEntityLivingBase entity) {
        if (entity != null) {
            IFontRenderer ifontrenderer = Fonts.font40;
            String s = entity.getName();

            if (s == null) {
                Intrinsics.throwNpe();
            }

            float width = (float) RangesKt.coerceAtLeast(38 + ifontrenderer.getStringWidth(s), 118);

            return new Border(0.0F, 0.0F, width, 36.0F);
        } else {
            return new Border(0.0F, 0.0F, 118.0F, 36.0F);
        }
    }

    public LiquidBounce(@NotNull LBplusTarget inst) {
        Intrinsics.checkParameterIsNotNull(inst, "inst");
        super("LiquidBounce", inst, true);
        this.hurtTimeAnim = new BoolValue("HurtTimeAnim", true);
        this.borderColorMode = new ListValue("Border-Color", new String[] { "Custom", "MatchBar", "None"}, "None");
        this.borderWidthValue = new FloatValue("Border-Width", 3.0F, 0.5F, 5.0F);
        this.borderRedValue = new IntegerValue("Border-Red", 0, 0, 255);
        this.borderGreenValue = new IntegerValue("Border-Green", 0, 0, 255);
        this.borderBlueValue = new IntegerValue("Border-Blue", 0, 0, 255);
        this.borderAlphaValue = new IntegerValue("Border-Alpha", 0, 0, 255);
    }
}
