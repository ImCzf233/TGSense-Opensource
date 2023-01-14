package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.VisualUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@ElementInfo(
    name = "SessionInfo"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010#\u001a\u00020$H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/SessionInfo;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "H", "", "getH", "()I", "setH", "(I)V", "HM", "getHM", "setHM", "M", "getM", "setM", "S", "getS", "setS", "addX", "", "addY", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blueValue", "gblueValue", "ggreenValue", "gredValue", "greenValue", "iconValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "raduiValue", "redValue", "shadow", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"}
)
public final class SessionInfo extends Element {

    private final ListValue modeValue = new ListValue("BackGround", new String[] { "Default", "Shadow", "Round"}, "Default");
    private final IntegerValue alphaValue = new IntegerValue("Background-Alpha", 180, 0, 255);
    private final IntegerValue redValue = new IntegerValue("Red", 255, 0, 255);
    private final IntegerValue greenValue = new IntegerValue("Green", 255, 0, 255);
    private final IntegerValue blueValue = new IntegerValue("Blue", 255, 0, 255);
    private final IntegerValue gredValue = new IntegerValue("GradientRed", 255, 0, 255);
    private final IntegerValue ggreenValue = new IntegerValue("GradientGreen", 255, 0, 255);
    private final IntegerValue gblueValue = new IntegerValue("GradientBlue", 255, 0, 255);
    private final IntegerValue raduiValue = new IntegerValue("RoundRadio", 2, 0, 10);
    private final BoolValue shadow = new BoolValue("Shadow", true);
    private final BoolValue iconValue = new BoolValue("Icon", true);
    private float addX;
    private float addY;
    private int HM;
    private int S;
    private int M;
    private int H;

    public final int getHM() {
        return this.HM;
    }

    public final void setHM(int <set-?>) {
        this.HM = <set-?>;
    }

    public final int getS() {
        return this.S;
    }

    public final void setS(int <set-?>) {
        this.S = <set-?>;
    }

    public final int getM() {
        return this.M;
    }

    public final void setM(int <set-?>) {
        this.M = <set-?>;
    }

    public final int getH() {
        return this.H;
    }

    public final void setH(int <set-?>) {
        this.H = <set-?>;
    }

    @NotNull
    public Border drawElement() {
        Color color1 = new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue());
        Color color2 = new Color(((Number) this.gredValue.get()).intValue(), ((Number) this.ggreenValue.get()).intValue(), ((Number) this.gblueValue.get()).intValue());

        ++this.HM;
        if (this.HM == 120) {
            ++this.S;
            this.HM = 0;
        }

        if (this.S == 60) {
            ++this.M;
            this.S = 0;
        }

        if (this.M == 60) {
            ++this.H;
            this.M = 0;
        }

        IFontRenderer font = Fonts.font25;
        IFontRenderer icon = Fonts.flux;
        Color color = Color.WHITE;

        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        int color = color.getRGB();
        int fontHeight = Fonts.bold30.getFontHeight();
        DecimalFormat format = new DecimalFormat("#.##");

        if (((Boolean) this.shadow.get()).booleanValue() && StringsKt.equals((String) this.modeValue.get(), "Default", true)) {
            VisualUtils.drawShadow(0, 0, 150, 3 + fontHeight + font.getFontHeight() * 3 + 30);
        }

        String s = (String) this.modeValue.get();
        boolean flag = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            s = s1;
            float f;

            switch (s.hashCode()) {
            case -903579360:
                if (s.equals("shadow")) {
                    VisualUtils.drawShadow(0, 0, 150, 3 + fontHeight + font.getFontHeight() * 3 + 30);
                }
                break;

            case 108704142:
                if (s.equals("round")) {
                    f = 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 3) + 30.0F;
                    float f1 = (float) ((Number) this.raduiValue.get()).intValue();
                    ColorUtils colorutils = ColorUtils.INSTANCE;
                    Color color1 = Color.BLACK;

                    Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
                    RenderUtils.drawCircleRect(0.0F, 0.0F, 150.0F, f, f1, colorutils.reAlpha(color1, ((Number) this.alphaValue.get()).intValue()).getRGB());
                }
                break;

            case 1544803905:
                if (s.equals("default")) {
                    f = 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 3) + 30.0F;
                    ColorUtils colorutils1 = ColorUtils.INSTANCE;
                    Color color2 = Color.BLACK;

                    Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
                    RenderUtils.drawRect(0.0F, 0.0F, 150.0F, f, colorutils1.reAlpha(color2, ((Number) this.alphaValue.get()).intValue()));
                }
            }

            if (StringsKt.equals((String) this.modeValue.get(), "Default", true)) {
                RenderUtils.drawGradientSideways(0.0D, 0.0D, 150.0D, 1.0D, color1.getRGB(), color2.getRGB());
            }

            Fonts.bold30.drawString("Session State", 5.0F, 3.0F, color);
            if (((Boolean) this.iconValue.get()).booleanValue()) {
                icon.drawString("F", 5.0F, 3.0F + (float) fontHeight + 6.0F, color);
                icon.drawString("I", 5.0F, 3.0F + (float) fontHeight + (float) font.getFontHeight() + 11.0F, color);
                icon.drawString("K", 5.0F, 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 2) + 16.0F, color);
                icon.drawString("G", 5.0F, 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 3) + 21.0F, color);
            }

            if (((Boolean) this.iconValue.get()).booleanValue()) {
                font.drawString("Played Time", 7.0F + (float) icon.getStringWidth("F"), 3.0F + (float) fontHeight + 5.0F, color);
                font.drawString("Speed", 7.0F + (float) icon.getStringWidth("I"), 3.0F + (float) fontHeight + (float) font.getFontHeight() + 10.0F, color);
                font.drawString("Ping", 7.0F + (float) icon.getStringWidth("K"), 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 2) + 15.0F, color);
                font.drawString("Kills", 7.0F + (float) icon.getStringWidth("G"), 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 3) + 20.0F, color);
            } else {
                font.drawString("Played Time", 7.0F, 3.0F + (float) fontHeight + 5.0F, color);
                font.drawString("Speed", 7.0F, 3.0F + (float) fontHeight + (float) font.getFontHeight() + 10.0F, color);
                font.drawString("Ping", 7.0F, 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 2) + 15.0F, color);
                font.drawString("Kills", 7.0F, 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 3) + 20.0F, color);
            }

            font.drawString(this.H + "h " + this.M + "m " + this.S + 's', (float) (150 - font.getStringWidth(this.H + "h " + this.M + "m " + this.S + 's')) - 5.0F, 3.0F + (float) fontHeight + 5.0F, color);
            String s2 = format.format(MovementUtils.INSTANCE.getBps());

            Intrinsics.checkExpressionValueIsNotNull(s2, "format.format(MovementUtils.bps)");
            String s3 = format.format(MovementUtils.INSTANCE.getBps());

            Intrinsics.checkExpressionValueIsNotNull(s3, "format.format(MovementUtils.bps)");
            font.drawString(s2, (float) (150 - font.getStringWidth(s3)) - 5.0F, 3.0F + (float) fontHeight + (float) font.getFontHeight() + 10.0F, color);
            Minecraft minecraft = MinecraftInstance.mc2;

            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2, "mc2");
            if (minecraft.isSingleplayer()) {
                font.drawString("0ms (Singleplayer)", (float) (150 - font.getStringWidth("0ms (Singleplayer)")) - 5.0F, 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 2) + 15.0F, color);
            } else {
                IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                INetworkPlayerInfo inetworkplayerinfo = iinethandlerplayclient.getPlayerInfo(ientityplayersp.getUniqueID());

                if (inetworkplayerinfo == null) {
                    Intrinsics.throwNpe();
                }

                s2 = String.valueOf(inetworkplayerinfo.getResponseTime());
                IINetHandlerPlayClient iinethandlerplayclient1 = MinecraftInstance.mc.getNetHandler();
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                INetworkPlayerInfo inetworkplayerinfo1 = iinethandlerplayclient1.getPlayerInfo(ientityplayersp1.getUniqueID());

                if (inetworkplayerinfo1 == null) {
                    Intrinsics.throwNpe();
                }

                font.drawString(s2, (float) (150 - font.getStringWidth(String.valueOf(inetworkplayerinfo1.getResponseTime()))) - 5.0F, 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 2) + 15.0F, color);
            }

            font.drawString(String.valueOf(KillAura.Companion.getKillCounts()), (float) (150 - font.getStringWidth(String.valueOf(KillAura.Companion.getKillCounts()))) - 5.0F, 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 3) + 20.0F, color);
            return new Border(0.0F, 0.0F, 150.0F, 3.0F + (float) fontHeight + (float) (font.getFontHeight() * 3) + 30.0F);
        }
    }

    public SessionInfo() {
        super(200.0D, 100.0D, 1.0F, new Side(Side.Horizontal.RIGHT, Side.Vertical.UP));
    }
}
