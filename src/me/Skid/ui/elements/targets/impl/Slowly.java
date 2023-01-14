package me.Skid.ui.elements.targets.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import me.Skid.ui.elements.LBplusTarget;
import me.Skid.ui.elements.targets.TargetStyle;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¬¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0014\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016¬®\u0006\u0010"},
    d2 = { "Lme/Skid/ui/elements/targets/impl/Slowly;", "Lme/Skid/ui/elements/targets/TargetStyle;", "inst", "Lme/Skid/ui/elements/LBplusTarget;", "(Lme/Skid/ui/elements/LBplusTarget;)V", "drawTarget", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "handleBlur", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "handleShadow", "handleShadowCut", "LiquidBounce"}
)
public final class Slowly extends TargetStyle {

    public void drawTarget(@NotNull IEntityLivingBase entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        IFontRenderer font = Fonts.minecraftFont;
        String healthString = this.getDecimalFormat2().format(Float.valueOf(entity.getHealth())) + " ‚ù?";
        String s = entity.getName();

        if (s == null) {
            Intrinsics.throwNpe();
        }

        float length = (float) RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(60, font.getStringWidth(s)), font.getStringWidth(healthString)) + 10.0F;

        this.updateAnim(entity.getHealth());
        RenderUtils.drawRect(0.0F, 0.0F, 32.0F + length, 36.0F, this.getTargetInstance().getBgColor().getRGB());
        if (MinecraftInstance.mc.getNetHandler().getPlayerInfo(entity.getUniqueID()) != null) {
            INetworkPlayerInfo inetworkplayerinfo = MinecraftInstance.mc.getNetHandler().getPlayerInfo(entity.getUniqueID());

            if (inetworkplayerinfo == null) {
                Intrinsics.throwNpe();
            }

            this.drawHead(inetworkplayerinfo.getLocationSkin(), 1, 1, 30, 30, 1.0F - this.getTargetInstance().getFadeProgress());
        }

        String s1 = entity.getName();

        if (s1 == null) {
            Intrinsics.throwNpe();
        }

        font.drawStringWithShadow(s1, 33, 2, this.getColor(-1).getRGB());
        font.drawStringWithShadow(healthString, (int) (length + 31.0F - (float) font.getStringWidth(healthString)), 22, this.getTargetInstance().getBarColor().getRGB());
        RenderUtils.drawRect(0.0F, 32.0F, RangesKt.coerceIn(this.getEasingHealth() / entity.getMaxHealth(), 0.0F, entity.getMaxHealth()) * (length + 32.0F), 36.0F, this.getTargetInstance().getBarColor().getRGB());
    }

    public void handleBlur(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        IFontRenderer font = Fonts.minecraftFont;
        String healthString = this.getDecimalFormat2().format(Float.valueOf(player.getHealth())) + " ‚ù?";
        String s = player.getName();

        if (s == null) {
            Intrinsics.throwNpe();
        }

        float length = (float) RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(60, font.getStringWidth(s)), font.getStringWidth(healthString)) + 10.0F;

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderUtils.quickDrawRect(0.0F, 0.0F, 32.0F + length, 36.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void handleShadowCut(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        this.handleBlur(player);
    }

    public void handleShadow(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        IFontRenderer font = Fonts.minecraftFont;
        String healthString = this.getDecimalFormat2().format(Float.valueOf(player.getHealth())) + " ‚ù?";
        String s = player.getName();

        if (s == null) {
            Intrinsics.throwNpe();
        }

        float length = (float) RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(60, font.getStringWidth(s)), font.getStringWidth(healthString)) + 10.0F;

        RenderUtils.drawRect(0.0F, 0.0F, 32.0F + length, 36.0F, this.getShadowOpaque().getRGB());
    }

    @Nullable
    public Border getBorder(@Nullable IEntityLivingBase entity) {
        if (entity != null) {
            IFontRenderer font = Fonts.minecraftFont;
            String healthString = this.getDecimalFormat2().format(Float.valueOf(entity.getHealth())) + " ‚ù?";
            String s = entity.getName();

            if (s == null) {
                Intrinsics.throwNpe();
            }

            float length = (float) RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(60, font.getStringWidth(s)), font.getStringWidth(healthString)) + 10.0F;

            return new Border(0.0F, 0.0F, 32.0F + length, 36.0F);
        } else {
            return new Border(0.0F, 0.0F, 102.0F, 36.0F);
        }
    }

    public Slowly(@NotNull LBplusTarget inst) {
        Intrinsics.checkParameterIsNotNull(inst, "inst");
        super("Slowly", inst, true);
    }
}
