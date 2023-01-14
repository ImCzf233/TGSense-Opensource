package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FontValue;
import org.jetbrains.annotations.NotNull;

@ElementInfo(
    name = "Effects"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Effects;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "shadow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"}
)
public final class Effects extends Element {

    private final FontValue fontValue;
    private final BoolValue shadow;

    @NotNull
    public Border drawElement() {
        float y = 0.0F;
        float width = 0.0F;
        IFontRenderer fontRenderer = (IFontRenderer) this.fontValue.get();

        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        for (Iterator iterator = ientityplayersp.getActivePotionEffects().iterator(); iterator.hasNext(); y -= (float) fontRenderer.getFontHeight()) {
            IPotionEffect effect = (IPotionEffect) iterator.next();
            IPotion potion = MinecraftInstance.functions.getPotionById(effect.getPotionID());
            String number = effect.getAmplifier() == 1 ? "II" : (effect.getAmplifier() == 2 ? "III" : (effect.getAmplifier() == 3 ? "IV" : (effect.getAmplifier() == 4 ? "V" : (effect.getAmplifier() == 5 ? "VI" : (effect.getAmplifier() == 6 ? "VII" : (effect.getAmplifier() == 7 ? "VIII" : (effect.getAmplifier() == 8 ? "IX" : (effect.getAmplifier() == 9 ? "X" : (effect.getAmplifier() > 10 ? "X+" : "I")))))))));
            String name = MinecraftInstance.functions.formatI18n(potion.getName(), new String[0]) + ' ' + number + "§f: §7" + effect.getDurationString();
            float stringWidth = (float) fontRenderer.getStringWidth(name);

            if (width < stringWidth) {
                width = stringWidth;
            }

            fontRenderer.drawString(name, -stringWidth, y, potion.getLiquidColor(), ((Boolean) this.shadow.get()).booleanValue());
        }

        AWTFontRenderer.Companion.setAssumeNonVolatile(false);
        if (width == 0.0F) {
            width = 40.0F;
        }

        if (y == 0.0F) {
            y = -10.0F;
        }

        return new Border(2.0F, (float) fontRenderer.getFontHeight(), -width - 2.0F, y + (float) fontRenderer.getFontHeight() - 2.0F);
    }

    public Effects(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        super(x, y, scale, side);
        IFontRenderer ifontrenderer = Fonts.font35;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font35, "Fonts.font35");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.shadow = new BoolValue("Shadow", true);
    }

    public Effects(double d0, double d1, float f, Side side, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 2.0D;
        }

        if ((i & 2) != 0) {
            d1 = 10.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        if ((i & 8) != 0) {
            side = new Side(Side.Horizontal.RIGHT, Side.Vertical.DOWN);
        }

        this(d0, d1, f, side);
    }

    public Effects() {
        this(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
    }
}
