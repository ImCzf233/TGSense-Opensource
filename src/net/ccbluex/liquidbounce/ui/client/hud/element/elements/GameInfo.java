package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.utils.render.VisualUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.color.Gident;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.InfosUtils.Recorder;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.blur.BlurBuffer;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.Palette;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "GameInfo"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u00101\u001a\u00020\u0003J\b\u00102\u001a\u000203H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001b\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010&\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001dR\u000e\u0010(\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/GameInfo;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "(DDF)V", "DATE_FORMAT", "Ljava/text/SimpleDateFormat;", "GameInfo", "Lnet/ccbluex/liquidbounce/value/ListValue;", "GameInfoRows", "", "RectA", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "alphaValue", "astolfoRainbowIndex", "astolfoRainbowOffset", "astolfoclient", "bgalphaValue", "bgblueValue", "bggreenValue", "bgredValue", "blueValue", "blur", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "blurValue", "getBlurValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "brightnessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "colorModeValue", "distanceValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "gradientAmountValue", "greenValue", "lineValue", "getLineValue", "newRainbowIndex", "radiusValue", "raduiValue", "redValue", "saturationValue", "textblueValue", "textblueae", "textgreenValue", "textredValue", "calculateBPS", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"}
)
public final class GameInfo extends Element {

    private final ListValue GameInfo;
    private final IntegerValue alphaValue;
    private final IntegerValue raduiValue;
    private final BoolValue blur;
    private final IntegerValue RectA;
    private final IntegerValue textredValue;
    private final IntegerValue textgreenValue;
    private final IntegerValue textblueValue;
    private final IntegerValue textblueae;
    private final FloatValue brightnessValue;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final FloatValue radiusValue;
    private final IntegerValue bgredValue;
    private final IntegerValue bggreenValue;
    private final IntegerValue bgblueValue;
    private final IntegerValue bgalphaValue;
    @NotNull
    private final BoolValue blurValue;
    @NotNull
    private final BoolValue lineValue;
    private final IntegerValue newRainbowIndex;
    private final IntegerValue astolfoRainbowOffset;
    private final IntegerValue astolfoclient;
    private final IntegerValue astolfoRainbowIndex;
    private final FloatValue saturationValue;
    private final ListValue colorModeValue;
    private final IntegerValue distanceValue;
    private final IntegerValue gradientAmountValue;
    private FontValue fontValue;
    private int GameInfoRows;
    private final SimpleDateFormat DATE_FORMAT;

    @NotNull
    public final BoolValue getBlurValue() {
        return this.blurValue;
    }

    @NotNull
    public final BoolValue getLineValue() {
        return this.lineValue;
    }

    @NotNull
    public Border drawElement() {
        IFontRenderer icon = Fonts.flux;
        int fontHeight = Fonts.font40.getFontHeight();
        float floatX = (float) this.getRenderX();
        float floatY = (float) this.getRenderY();
        double barLength1 = (double) 163.0F;
        String colorMode = (String) this.colorModeValue.get();
        int color = (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue(), 192)).getRGB();
        int Borderx1 = 0;
        int Bordery1 = 0;
        int Borderx2 = 0;
        int Bordery2 = 0;
        int HM;
        IFontRenderer ifontrenderer;
        StringBuilder stringbuilder;
        DecimalFormat decimalformat;
        IEntityPlayerSP ientityplayersp;
        Color color;

        if (StringsKt.equals((String) this.GameInfo.get(), "Default", true)) {
            Borderx1 += 0;
            Bordery1 += this.GameInfoRows * 18 + 12;
            Borderx2 += 176;
            Bordery2 += 78;
            if (((Boolean) this.blur.get()).booleanValue()) {
                GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                BlurBuffer.blurArea(floatX, floatY + (float) 8, 176.0F, 70.0F);
                GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
            }

            RenderUtils.drawRect(0.0F, (float) this.GameInfoRows * 18.0F + 25.0F, 176.0F, 80.0F, (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue(), 0)).getRGB());
            RenderUtils.drawShadowWithCustomAlpha(0.0F, 12.5F, 176.0F, 64.0F, 255.0F);
            RenderUtils.drawRect(0.0F, 11.0F, 176.0F, 77.0F, (new Color(20, 19, 18, 170)).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("Session Info", 3, this.GameInfoRows * 18 + 14, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), ((Number) this.textblueae.get()).intValue())).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 7, this.GameInfoRows * 18 + 26, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), ((Number) this.textblueae.get()).intValue())).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("Ping:" + String.valueOf(EntityUtils.INSTANCE.getPing((EntityPlayer) MinecraftInstance.mc2.player)), 7, this.GameInfoRows * 18 + 36, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), ((Number) this.textblueae.get()).intValue())).getRGB());
            ifontrenderer = (IFontRenderer) this.fontValue.get();
            stringbuilder = (new StringBuilder()).append("X:");
            decimalformat = Text.Companion.getDECIMAL_FORMAT();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            stringbuilder = stringbuilder.append(decimalformat.format(ientityplayersp.getPosX())).append(" ").append("Y:");
            decimalformat = Text.Companion.getDECIMAL_FORMAT();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            stringbuilder = stringbuilder.append(decimalformat.format(ientityplayersp.getPosY())).append(" ").append("Z:");
            decimalformat = Text.Companion.getDECIMAL_FORMAT();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ifontrenderer.drawStringWithShadow(stringbuilder.append(decimalformat.format(ientityplayersp.getPosZ())).toString(), 7, this.GameInfoRows * 18 + 47, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), ((Number) this.textblueae.get()).intValue())).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("ServerIP:" + ServerUtils.getRemoteIp(), 7, this.GameInfoRows * 18 + 56, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), ((Number) this.textblueae.get()).intValue())).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("Kills:" + Recorder.INSTANCE.getKillCounts(), 7, this.GameInfoRows * 18 + 68, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            int font = 0;

            for (HM = ((Number) this.gradientAmountValue.get()).intValue(); font < HM; ++font) {
                double S = (double) font / (double) ((Number) this.gradientAmountValue.get()).intValue() * barLength1;
                double H = (double) (font + 1) / (double) ((Number) this.gradientAmountValue.get()).intValue() * barLength1;
                double d0 = (double) 8 + S - (double) 8;
                double d1 = (double) 8 + H + (double) 5;
                int i;

                if (StringsKt.equals(colorMode, "Fade", true)) {
                    color = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), font * ((Number) this.distanceValue.get()).intValue(), 1000);
                    Intrinsics.checkExpressionValueIsNotNull(color, "Palette.fade2(\n         �?                        )");
                    i = color.getRGB();
                } else if (StringsKt.equals(colorMode, "Astolfo", true)) {
                    i = VisualUtils.Astolfo(font * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                } else if (StringsKt.equals(colorMode, "Gident", true)) {
                    color = VisualUtils.getGradientOffset(new Color(((Number) Gident.redValue.get()).intValue(), ((Number) Gident.greenValue.get()).intValue(), ((Number) Gident.blueValue.get()).intValue()), new Color(((Number) Gident.redValue2.get()).intValue(), ((Number) Gident.greenValue2.get()).intValue(), ((Number) Gident.blueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) Gident.gidentspeed.get()).intValue() + (double) (font * ((Number) this.distanceValue.get()).intValue())) / (double) 10);
                    Intrinsics.checkExpressionValueIsNotNull(color, "VisualUtils.getGradientO�?                        )");
                    i = color.getRGB();
                } else {
                    i = StringsKt.equals(colorMode, "NewRainbow", true) ? VisualUtils.getRainbow(font * ((Number) this.distanceValue.get()).intValue(), ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : color;
                }

                int j;
                Color color1;

                if (StringsKt.equals(colorMode, "Fade", true)) {
                    color1 = Palette.fade2(new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue()), font * ((Number) this.distanceValue.get()).intValue(), 1000);
                    Intrinsics.checkExpressionValueIsNotNull(color1, "Palette.fade2(\n         �?                        )");
                    j = color1.getRGB();
                } else if (StringsKt.equals(colorMode, "Astolfo", true)) {
                    j = VisualUtils.Astolfo(font * ((Number) this.distanceValue.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue(), ((Number) this.astolfoRainbowOffset.get()).intValue(), ((Number) this.astolfoRainbowIndex.get()).intValue(), (float) ((Number) this.astolfoclient.get()).intValue());
                } else if (StringsKt.equals(colorMode, "Gident", true)) {
                    color1 = VisualUtils.getGradientOffset(new Color(((Number) Gident.redValue.get()).intValue(), ((Number) Gident.greenValue.get()).intValue(), ((Number) Gident.blueValue.get()).intValue()), new Color(((Number) Gident.redValue2.get()).intValue(), ((Number) Gident.greenValue2.get()).intValue(), ((Number) Gident.blueValue2.get()).intValue(), 1), Math.abs((double) System.currentTimeMillis() / (double) ((Number) Gident.gidentspeed.get()).intValue() + (double) (font * ((Number) this.distanceValue.get()).intValue())) / (double) 10);
                    Intrinsics.checkExpressionValueIsNotNull(color1, "VisualUtils.getGradientO�?                        )");
                    j = color1.getRGB();
                } else {
                    j = StringsKt.equals(colorMode, "NewRainbow", true) ? VisualUtils.getRainbow(font * ((Number) this.distanceValue.get()).intValue(), ((Number) this.newRainbowIndex.get()).intValue(), ((Number) this.saturationValue.get()).floatValue(), ((Number) this.brightnessValue.get()).floatValue()) : color;
                }

                RenderUtils.drawGradientSideways(d0, 10.0D, d1, 11.0D, i, j);
            }
        }

        MovementUtils movementutils;
        EntityPlayerSP entityplayersp;

        if (StringsKt.equals((String) this.GameInfo.get(), "Flux", true)) {
            if (((Boolean) this.blur.get()).booleanValue()) {
                GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                BlurBuffer.blurRoundArea(floatX, floatY + (float) 10, 120.0F, 65.0F, 7.0F);
                GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
            }

            Borderx1 += 0;
            Bordery1 += this.GameInfoRows * 18 + 12;
            Borderx2 += 150;
            Bordery2 += 80;
            RenderUtils.drawCircleRect(0.0F, 10.0F, 120.0F, 75.0F, 7.0F, (new Color(0, 0, 0, 120)).getRGB());
            Fonts.flux.drawString("F", 5.0F, 16.0F, -1);
            Fonts.font35.drawString("Play Time: " + this.DATE_FORMAT.format(new Date(System.currentTimeMillis() - Recorder.INSTANCE.getStartTime() - 28800000L)), 20.0F, 15.0F, -1);
            Fonts.flux.drawString("G", 5.0F, 31.0F, -1);
            Fonts.font35.drawString("Kills: " + Recorder.INSTANCE.getKillCounts(), 20.0F, 30.0F, -1);
            Fonts.flux.drawString("H", 5.0F, 46.0F, -1);
            Fonts.font35.drawString("Win / Total: " + Recorder.INSTANCE.getWin() + " / " + Recorder.INSTANCE.getTotalPlayed(), 20.0F, 45.0F, -1);
            Fonts.flux.drawString("I", 5.0F, 61.0F, -1);
            ifontrenderer = Fonts.font35;
            stringbuilder = (new StringBuilder()).append("Speed:");
            movementutils = MovementUtils.INSTANCE;
            entityplayersp = MinecraftInstance.mc2.player;
            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
            ifontrenderer.drawString(stringbuilder.append(movementutils.getBlockSpeed((EntityLivingBase) entityplayersp)).append("/bps").toString(), 20.0F, 60.0F, -1);
        }

        if (StringsKt.equals((String) this.GameInfo.get(), "Shadow", true)) {
            Borderx1 += 0;
            Bordery1 += this.GameInfoRows * 18 + 12;
            Borderx2 += 176;
            Bordery2 += 80;
            if (((Boolean) this.blur.get()).booleanValue()) {
                GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                BlurBuffer.blurArea(floatX, floatY + (float) 11, 176.0F, 70.0F);
                GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
            }

            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("Session Info", 3, this.GameInfoRows * 18 + 14, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), ((Number) this.textblueae.get()).intValue())).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 7, this.GameInfoRows * 18 + 26, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), ((Number) this.textblueae.get()).intValue())).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("Ping:" + String.valueOf(EntityUtils.INSTANCE.getPing((EntityPlayer) MinecraftInstance.mc2.player)), 7, this.GameInfoRows * 18 + 36, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), ((Number) this.textblueae.get()).intValue())).getRGB());
            ifontrenderer = (IFontRenderer) this.fontValue.get();
            stringbuilder = (new StringBuilder()).append("X:");
            decimalformat = Text.Companion.getDECIMAL_FORMAT();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            stringbuilder = stringbuilder.append(decimalformat.format(ientityplayersp.getPosX())).append(" ").append("Y:");
            decimalformat = Text.Companion.getDECIMAL_FORMAT();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            stringbuilder = stringbuilder.append(decimalformat.format(ientityplayersp.getPosY())).append(" ").append("Z:");
            decimalformat = Text.Companion.getDECIMAL_FORMAT();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ifontrenderer.drawStringWithShadow(stringbuilder.append(decimalformat.format(ientityplayersp.getPosZ())).toString(), 7, this.GameInfoRows * 18 + 47, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), ((Number) this.textblueae.get()).intValue())).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("ServerIP:" + ServerUtils.getRemoteIp(), 7, this.GameInfoRows * 18 + 56, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), ((Number) this.textblueae.get()).intValue())).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("Kills:" + Recorder.INSTANCE.getKillCounts(), 7, this.GameInfoRows * 18 + 68, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            RenderUtils.drawRect(0.0F, (float) this.GameInfoRows * 18.0F + 25.0F, 176.0F, 80.0F, (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue(), 0)).getRGB());
            RenderUtils.drawShadowWithCustomAlpha(0.0F, 11.0F, 176.0F, 70.0F, 255.0F);
            new Border(0.0F, (float) this.GameInfoRows * 18.0F + 12.0F, 176.0F, 80.0F);
        }

        if (StringsKt.equals((String) this.GameInfo.get(), "Old", true)) {
            Borderx1 += 0;
            Bordery1 += this.GameInfoRows * 18 + 12;
            Borderx2 += 150;
            Bordery2 += 80;
            if (((Boolean) this.blur.get()).booleanValue()) {
                GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                BlurBuffer.blurArea((float) this.getRenderX(), (float) this.getRenderY() + (float) 8, 156.0F, 78.0F);
                GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
            }

            RenderUtils.drawShadowWithCustomAlpha(0.0F, 8.0F, 155.0F, 76.0F, 200.0F);
            RenderUtils.drawRect(0.0F, 8.0F, 156.0F, 85.0F, (new Color(41, 41, 41, ((Number) this.RectA.get()).intValue())).getRGB());
            icon.drawString("c", 47.0F, 2.5F + (float) fontHeight + 6.0F, color);
            Fonts.bold35.drawStringWithShadow("Session Info", (int) (50.0F + (float) icon.getStringWidth("u")), (int) ((float) this.GameInfoRows * 18.0F + (float) 16), (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            ifontrenderer = Fonts.bold30;
            stringbuilder = (new StringBuilder()).append("Speed: ");
            movementutils = MovementUtils.INSTANCE;
            entityplayersp = MinecraftInstance.mc2.player;
            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
            ifontrenderer.drawStringWithShadow(stringbuilder.append(movementutils.getBlockSpeed((EntityLivingBase) entityplayersp)).toString(), (int) (5.0F + (float) icon.getStringWidth("b")), (int) ((float) this.GameInfoRows * 20.0F + (float) 30), (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            Fonts.bold30.drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), (int) (5.0F + (float) icon.getStringWidth("e")), (int) ((float) this.GameInfoRows * 20.0F + (float) 43), (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            Fonts.bold30.drawStringWithShadow("Kills: " + Recorder.INSTANCE.getKillCounts(), (int) (5.0F + (float) icon.getStringWidth("G")), (int) ((float) this.GameInfoRows * 20.0F + (float) 54), (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            Fonts.bold30.drawStringWithShadow("Played Time: " + this.DATE_FORMAT.format(new Date(System.currentTimeMillis() - Recorder.INSTANCE.getStartTime() - 28800000L)), (int) (5.0F + (float) icon.getStringWidth("G")), (int) ((float) this.GameInfoRows * 20.0F + (float) 66), (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
        }

        if (StringsKt.equals((String) this.GameInfo.get(), "LoseLine", true)) {
            if (((Boolean) this.blur.get()).booleanValue()) {
                GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                BlurBuffer.blurArea(floatX, floatY + 10.5F, 150.0F, 70.0F);
                GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
            }

            Borderx1 += 0;
            Bordery1 += this.GameInfoRows * 18 + 12;
            Borderx2 += 150;
            Bordery2 += 80;
            RenderUtils.drawShadowWithCustomAlpha(0.0F, 10.5F, 150.0F, 70.0F, 200.0F);
            RenderUtils.drawRect(0.0F, 11.0F, 150.0F, 12.0F, ColorUtils.hslRainbow$default(ColorUtils.INSTANCE, 10, 0.0F, 0.0F, 30, 0, 22, (Object) null).getRGB());
            RenderUtils.drawRect(0.0F, (float) this.GameInfoRows * 18.0F + (float) 12, 150.0F, (float) this.GameInfoRows * 18.0F + 25.0F, (new Color(0, 0, 0, 100)).getRGB());
            RenderUtils.drawRect(0.0F, (float) this.GameInfoRows * 18.0F + 25.0F, 150.0F, 80.0F, (new Color(0, 0, 0, 100)).getRGB());
            icon.drawString("c", 3.0F, 2.5F + (float) fontHeight + 6.0F, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            icon.drawString("m", 3.0F, 15.9F + (float) fontHeight + 6.0F, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            icon.drawString("f", 3.0F, 28.5F + (float) fontHeight + 6.0F, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            icon.drawString("a", 3.0F, 39.5F + (float) fontHeight + 6.0F, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            icon.drawString("x", 3.0F, 52.0F + (float) fontHeight + 6.0F, (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("Session Info", (int) (5.0F + (float) icon.getStringWidth("u")), (int) ((float) this.GameInfoRows * 18.0F + (float) 16), (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("BPS: " + this.calculateBPS(), (int) (5.0F + (float) icon.getStringWidth("b")), (int) ((float) this.GameInfoRows * 18.0F + (float) 30), (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), (int) (5.0F + (float) icon.getStringWidth("e")), (int) ((float) this.GameInfoRows * 18.0F + (float) 43), (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("Kills: " + Recorder.INSTANCE.getKillCounts(), (int) (5.0F + (float) icon.getStringWidth("G")), (int) ((float) this.GameInfoRows * 18.0F + (float) 54), (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
            ((IFontRenderer) this.fontValue.get()).drawStringWithShadow("Played Time: " + this.DATE_FORMAT.format(new Date(System.currentTimeMillis() - Recorder.INSTANCE.getStartTime() - 28800000L)), (int) (5.0F + (float) icon.getStringWidth("G")), (int) ((float) this.GameInfoRows * 18.0F + (float) 66), (new Color(((Number) this.textredValue.get()).intValue(), ((Number) this.textgreenValue.get()).intValue(), ((Number) this.textblueValue.get()).intValue(), 255)).getRGB());
        }

        IFontRenderer ifontrenderer1;
        int k;
        String s;

        if (StringsKt.equals((String) this.GameInfo.get(), "Normal", true)) {
            ifontrenderer1 = (IFontRenderer) this.fontValue.get();
            HM = ifontrenderer1.getFontHeight() * 5 + (int) 11.0D;
            k = (int) 140.0D;
            Borderx1 += -2;
            Bordery1 += -2;
            int l = Borderx2 + k;

            l = Bordery2 + HM;
            SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm:ss");

            if (((Boolean) this.blurValue.get()).booleanValue()) {
                GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                GL11.glPushMatrix();
                BlurBuffer.blurRoundArea((float) this.getRenderX(), (float) this.getRenderY(), (float) k, (float) HM, ((Number) this.radiusValue.get()).floatValue());
                GL11.glPopMatrix();
                GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
            }

            RenderUtils.drawShadowWithCustomAlpha(-2.0F, -2.0F, (float) (k + 2), (float) (HM + 2), 255.0F);
            RenderUtils.drawRoundedRect(-2.0F, -2.0F, (float) k, (float) HM, (int) ((Number) this.radiusValue.get()).floatValue(), (new Color(((Number) this.bgredValue.get()).intValue(), ((Number) this.bggreenValue.get()).intValue(), ((Number) this.bgblueValue.get()).intValue(), ((Number) this.bgalphaValue.get()).intValue())).getRGB());
            if (((Boolean) this.lineValue.get()).booleanValue()) {
                Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(HUD.class);

                if (module == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.HUD");
                }

                HUD hud = (HUD) module;

                RenderUtils.drawGradientSideways(2.44D, (double) ifontrenderer1.getFontHeight() + 2.5D + 0.0D, 135.55999994277954D, (double) ifontrenderer1.getFontHeight() + 2.5D + (double) 1.16F, (new Color(((Number) Gident.redValue.get()).intValue(), ((Number) Gident.greenValue.get()).intValue(), ((Number) Gident.blueValue.get()).intValue())).getRGB(), (new Color(((Number) Gident.redValue2.get()).intValue(), ((Number) Gident.greenValue2.get()).intValue(), ((Number) Gident.blueValue2.get()).intValue())).getRGB());
            }

            float f = (float) k / 2.0F;

            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer1.drawCenteredString("Session Info", f, 3.0F, color.getRGB(), true);
            s = "Play Time: " + simpledateformat.format(new Date(System.currentTimeMillis() - Recorder.INSTANCE.getStartTime() - 28800000L));
            int i1 = (int) ((float) ifontrenderer1.getFontHeight() + 8.0F);

            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer1.drawStringWithShadow(s, 2, i1, color.getRGB());
            s = "Players Killed: " + Recorder.INSTANCE.getKillCounts();
            i1 = (int) ((float) (ifontrenderer1.getFontHeight() * 2) + 8.0F);
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer1.drawStringWithShadow(s, 2, i1, color.getRGB());
            s = "Win: " + Recorder.INSTANCE.getTotalPlayed();
            i1 = (int) ((float) (ifontrenderer1.getFontHeight() * 3) + 8.0F);
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer1.drawStringWithShadow(s, 2, i1, color.getRGB());
            s = "Total: " + Recorder.INSTANCE.getTotalPlayed();
            i1 = (int) ((float) (ifontrenderer1.getFontHeight() * 4) + 8.0F);
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer1.drawStringWithShadow(s, 2, i1, color.getRGB());
            return new Border(-2.0F, -2.0F, (float) k, (float) HM);
        } else {
            if (StringsKt.equals((String) this.GameInfo.get(), "Round", true)) {
                ifontrenderer1 = Fonts.bold30;
                Borderx1 += 0;
                Bordery1 += 0;
                Borderx2 += 150;
                Bordery2 += 3 + fontHeight + ifontrenderer1.getFontHeight() * 3 + 30;
                byte b0 = 0;

                k = 0;
                int M = 0;
                int j1 = 0;

                HM = b0 + 1;
                if (HM == 120) {
                    ++k;
                    boolean flag = false;
                }

                if (k == 60) {
                    ++M;
                    k = 0;
                }

                if (M == 60) {
                    ++j1;
                    M = 0;
                }

                Color color2 = Color.WHITE;

                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                int color1 = color2.getRGB();
                int fontHeight1 = Fonts.bold30.getFontHeight();
                DecimalFormat format = new DecimalFormat("#.##");

                if (((Boolean) this.blur.get()).booleanValue()) {
                    GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
                    GL11.glPushMatrix();
                    BlurBuffer.blurRoundArea((float) this.getRenderX(), (float) this.getRenderY(), 150.0F, 3.0F + (float) fontHeight1 + (float) (ifontrenderer1.getFontHeight() * 3) + 30.0F, ((Number) this.radiusValue.get()).floatValue());
                    GL11.glPopMatrix();
                    GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
                }

                VisualUtils.drawCircleRect(0.0F, 0.0F, 150.0F, 3.0F + (float) fontHeight1 + (float) (ifontrenderer1.getFontHeight() * 3) + 30.0F, (float) ((Number) this.raduiValue.get()).intValue(), (new Color(0, 0, 0, ((Number) this.alphaValue.get()).intValue())).getRGB());
                Fonts.bold40.drawString("Session State", 5.0F, 3.0F, color1);
                ifontrenderer1.drawString("Played Time", 7.0F, 3.0F + (float) fontHeight1 + 5.0F, color1);
                ifontrenderer1.drawString("Speed", 7.0F, 3.0F + (float) fontHeight1 + (float) ifontrenderer1.getFontHeight() + 10.0F, color1);
                ifontrenderer1.drawString("Ping", 7.0F, 3.0F + (float) fontHeight1 + (float) (ifontrenderer1.getFontHeight() * 2) + 15.0F, color1);
                ifontrenderer1.drawString("Kills", 7.0F, 3.0F + (float) fontHeight1 + (float) (ifontrenderer1.getFontHeight() * 3) + 20.0F, color1);
                ifontrenderer1.drawString(j1 + "h " + M + "m " + k + 's', (float) (150 - ifontrenderer1.getStringWidth(j1 + "h " + M + "m " + k + 's')) - 5.0F, 3.0F + (float) fontHeight1 + 5.0F, color1);
                s = format.format(MovementUtils.INSTANCE.getBps());
                Intrinsics.checkExpressionValueIsNotNull(s, "format.format(MovementUtils.bps)");
                String s1 = format.format(MovementUtils.INSTANCE.getBps());

                Intrinsics.checkExpressionValueIsNotNull(s1, "format.format(MovementUtils.bps)");
                ifontrenderer1.drawString(s, (float) (150 - ifontrenderer1.getStringWidth(s1)) - 5.0F, 3.0F + (float) fontHeight1 + (float) ifontrenderer1.getFontHeight() + 10.0F, color1);
                Minecraft minecraft = Minecraft.getMinecraft();

                Intrinsics.checkExpressionValueIsNotNull(minecraft, "Minecraft.getMinecraft()");
                if (minecraft.isSingleplayer()) {
                    ifontrenderer1.drawString("0ms (Singleplayer)", (float) (150 - ifontrenderer1.getStringWidth("0ms (Singleplayer)")) - 5.0F, 3.0F + (float) fontHeight1 + (float) (ifontrenderer1.getFontHeight() * 2) + 15.0F, color1);
                } else {
                    IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    EntityPlayerSP entityplayersp1 = Minecraft.getMinecraft().player;

                    Intrinsics.checkExpressionValueIsNotNull(entityplayersp1, "Minecraft.getMinecraft().player");
                    UUID uuid = entityplayersp1.getUniqueID();

                    Intrinsics.checkExpressionValueIsNotNull(uuid, "Minecraft.getMinecraft().player.uniqueID");
                    INetworkPlayerInfo inetworkplayerinfo = iinethandlerplayclient.getPlayerInfo(uuid);

                    if (inetworkplayerinfo == null) {
                        Intrinsics.throwNpe();
                    }

                    s = String.valueOf(inetworkplayerinfo.getResponseTime());
                    IINetHandlerPlayClient iinethandlerplayclient1 = MinecraftInstance.mc.getNetHandler();
                    EntityPlayerSP entityplayersp2 = Minecraft.getMinecraft().player;

                    Intrinsics.checkExpressionValueIsNotNull(entityplayersp2, "Minecraft.getMinecraft().player");
                    UUID uuid1 = entityplayersp2.getUniqueID();

                    Intrinsics.checkExpressionValueIsNotNull(uuid1, "Minecraft.getMinecraft().player.uniqueID");
                    INetworkPlayerInfo inetworkplayerinfo1 = iinethandlerplayclient1.getPlayerInfo(uuid1);

                    if (inetworkplayerinfo1 == null) {
                        Intrinsics.throwNpe();
                    }

                    ifontrenderer1.drawString(s, (float) (150 - ifontrenderer1.getStringWidth(String.valueOf(inetworkplayerinfo1.getResponseTime()))) - 5.0F, 3.0F + (float) fontHeight1 + (float) (ifontrenderer1.getFontHeight() * 2) + 15.0F, color1);
                }

                ifontrenderer1.drawString(String.valueOf(KillAura.Companion.getKillCounts()), (float) (150 - ifontrenderer1.getStringWidth(String.valueOf(KillAura.Companion.getKillCounts()))) - 5.0F, 3.0F + (float) fontHeight1 + (float) (ifontrenderer1.getFontHeight() * 3) + 20.0F, color1);
            }

            return new Border((float) Borderx1, (float) Bordery1, (float) Borderx2, (float) Bordery2);
        }
    }

    public final double calculateBPS() {
        if (MinecraftInstance.mc.getThePlayer() != null) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            double d0 = ientityplayersp.getPosX();
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            d0 -= ientityplayersp1.getPrevPosX();
            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            double d1 = ientityplayersp1.getPosZ();
            IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            double bps = Math.hypot(d0, d1 - ientityplayersp2.getPrevPosZ()) * (double) MinecraftInstance.mc.getTimer().getTimerSpeed() * (double) 20;

            return (double) Math.round(bps * 100.0D) / 100.0D;
        } else {
            return 0.0D;
        }
    }

    public GameInfo(double x, double y, float scale) {
        super(x, y, scale, (Side) null, 8, (DefaultConstructorMarker) null);
        this.GameInfo = new ListValue("Mode", new String[] { "Default", "Shadow", "Old", "LoseLine", "Flux", "Normal", "Round"}, "Default");
        this.alphaValue = new IntegerValue("Round-Background-Alpha", 180, 0, 255);
        this.raduiValue = new IntegerValue("Round-RoundRadiu", 2, 0, 10);
        this.blur = new BoolValue("Old-Blur", true);
        this.RectA = new IntegerValue("Old-RectA", 120, 0, 255);
        this.textredValue = new IntegerValue("Old-TextRed", 255, 0, 255);
        this.textgreenValue = new IntegerValue("Old-TextRed", 244, 0, 255);
        this.textblueValue = new IntegerValue("Old-TextBlue", 255, 0, 255);
        this.textblueae = new IntegerValue("Old-Textalpha", 255, 0, 255);
        this.brightnessValue = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
        this.redValue = new IntegerValue("Text-R", 255, 0, 255);
        this.greenValue = new IntegerValue("Text-G", 255, 0, 255);
        this.blueValue = new IntegerValue("Text-B", 255, 0, 255);
        this.radiusValue = new FloatValue("Normal-Radius", 4.25F, 0.0F, 10.0F);
        this.bgredValue = new IntegerValue("Normal-Bg-R", 255, 0, 255);
        this.bggreenValue = new IntegerValue("Normal-Bg-G", 255, 0, 255);
        this.bgblueValue = new IntegerValue("Normal-Bg-B", 255, 0, 255);
        this.bgalphaValue = new IntegerValue("Normal-Bg-Alpha", 150, 0, 255);
        this.blurValue = new BoolValue("Normal-Blur", true);
        this.lineValue = new BoolValue("Normal-Line", true);
        this.newRainbowIndex = new IntegerValue("NewRainbowOffset", 1, 1, 50);
        this.astolfoRainbowOffset = new IntegerValue("AstolfoOffset", 5, 1, 20);
        this.astolfoclient = new IntegerValue("AstolfoRange", 109, 1, 765);
        this.astolfoRainbowIndex = new IntegerValue("AstolfoIndex", 109, 1, 300);
        this.saturationValue = new FloatValue("Saturation", 0.9F, 0.0F, 1.0F);
        this.colorModeValue = new ListValue("Color", new String[] { "Custom", "Rainbow", "Fade", "Astolfo", "NewRainbow", "Gident"}, "Custom");
        this.distanceValue = new IntegerValue("Distance", 0, 0, 400);
        this.gradientAmountValue = new IntegerValue("Gradient-Amount", 25, 1, 50);
        IFontRenderer ifontrenderer = Fonts.fontSFUI35;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.fontSFUI35, "Fonts.fontSFUI35");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
    }

    public GameInfo(double d0, double d1, float f, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 10.0D;
        }

        if ((i & 2) != 0) {
            d1 = 10.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        this(d0, d1, f);
    }

    public GameInfo() {
        this(0.0D, 0.0D, 0.0F, 7, (DefaultConstructorMarker) null);
    }
}
