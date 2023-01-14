package me.Skid.ui.elements.targets.impl;

import java.awt.Color;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0016J\u0014\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\t\u001a\u0004\u0018\u00010\u0006H\u0016J\u0012\u0010\f\u001a\u00020\r2\b\u0010\t\u001a\u0004\u0018\u00010\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"},
    d2 = { "Lme/Skid/ui/elements/targets/impl/Moon;", "Lme/Skid/ui/elements/targets/TargetStyle;", "inst", "Lme/Skid/ui/elements/LBplusTarget;", "(Lme/Skid/ui/elements/LBplusTarget;)V", "lastTarget", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "drawTarget", "", "entity", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getHealth2", "", "Lnet/minecraft/entity/EntityLivingBase;", "handleBlur", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "handleShadow", "handleShadowCut", "LiquidBounce"}
)
public final class Moon extends TargetStyle {

    private IEntityLivingBase lastTarget;

    public void drawTarget(@NotNull IEntityLivingBase entity) {
        float width;
        label27: {
            Intrinsics.checkParameterIsNotNull(entity, "entity");
            if (!(Intrinsics.areEqual(entity, this.lastTarget) ^ true) && this.getEasingHealth() >= (float) 0 && this.getEasingHealth() <= entity.getMaxHealth()) {
                width = this.getEasingHealth() - entity.getHealth();
                boolean playerInfo = false;

                if ((double) Math.abs(width) >= 0.01D) {
                    break label27;
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
        RenderUtils.drawRect(0.0F, 0.0F, width + 40.5F, 51.5F, (new Color(0, 0, 0, 100)).getRGB());
        float f = width + 40.5F;
        Color color = Color.black.darker().darker();

        Intrinsics.checkExpressionValueIsNotNull(color, "Color.black.darker().darker()");
        RenderUtils.drawBorder(0.0F, 0.0F, f, 51.5F, 2.0F, this.getColor(color).getRGB());
        color = Color.black.darker().darker();
        Intrinsics.checkExpressionValueIsNotNull(color, "Color.black.darker().darker()");
        RenderUtils.drawBorder(53.0F, 41.0F, 155.5F, 48.5F, 2.0F, this.getColor(color).getRGB());
        Color color1 = Color.darkGray.darker().darker();

        Intrinsics.checkExpressionValueIsNotNull(color1, "Color.darkGray.darker().darker()");
        RenderUtils.drawRect(53.0F, 41.0F, 155.5F, 48.5F, this.getColor(color1).getRGB());
        RenderUtils.drawRect(53.0F, 41.0F, 17.5F + RangesKt.coerceIn(this.getEasingHealth() / entity.getMaxHealth(), 0.0F, 1.0F) * 138.0F, 48.5F, this.getTargetInstance().getBarColor().getRGB());
        this.updateAnim(entity.getHealth());
        Fonts.fontSFUI35.drawString("Name: " + entity.getName(), 53.5F, 3.5F, this.getColor(-1).getRGB());
        IFontRenderer ifontrenderer1 = Fonts.fontSFUI35;
        StringBuilder stringbuilder = (new StringBuilder()).append("Distance: ");
        DecimalFormat decimalformat = this.getDecimalFormat();
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        ifontrenderer1.drawString(stringbuilder.append(decimalformat.format(PlayerExtensionKt.getDistanceToEntityBox((IEntity) ientityplayersp, (IEntity) entity))).toString(), 53.5F, 13.0F, this.getColor(-1).getRGB());
        Fonts.fontSFUI35.drawString("Health: " + this.getDecimalFormat().format(Float.valueOf(entity.getHealth())), 53.5F, 22.0F, this.getTargetInstance().getBarColor().getRGB());
        INetworkPlayerInfo playerInfo1 = MinecraftInstance.mc.getNetHandler().getPlayerInfo(entity.getUniqueID());

        if (playerInfo1 != null) {
            Fonts.fontSFUI35.drawString("Ping: " + RangesKt.coerceAtLeast(playerInfo1.getResponseTime(), 0) + "ms", 53.5F, 31.5F, (new Color(120, 120, 120)).getRGB());
            IResourceLocation locationSkin = playerInfo1.getLocationSkin();

            TargetStyle.drawHead$default(this, locationSkin, 0.5F, 0.5F, 1.68F, 30, 30, 1.0F, 1.0F, 1.0F, 0.0F, 512, (Object) null);
            color = Color.black.darker().darker();
            Intrinsics.checkExpressionValueIsNotNull(color, "Color.black.darker().darker()");
            RenderUtils.drawBorder(0.5F, 0.5F, 51.0F, 51.0F, 2.0F, this.getColor(color).getRGB());
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
        RenderUtils.quickDrawRect(0.0F, 0.0F, width + 40.5F, 51.5F);
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

            return new Border(0.0F, 0.0F, width + 40.5F, 51.5F);
        } else {
            return new Border(0.0F, 0.0F, 158.5F, 51.5F);
        }
    }

    private final float getHealth2(EntityLivingBase entity) {
        return entity != null && !entity.isDead ? entity.getHealth() : 0.0F;
    }

    public Moon(@NotNull LBplusTarget inst) {
        Intrinsics.checkParameterIsNotNull(inst, "inst");
        super("Moon", inst, true);
    }
}
