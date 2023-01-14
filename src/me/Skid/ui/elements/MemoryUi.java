package me.Skid.ui.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import me.Skid.manager.Memory;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@ElementInfo(
    name = "MemoryUi"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"},
    d2 = { "Lme/Skid/ui/elements/MemoryUi;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "decimalFormat", "Ljava/text/DecimalFormat;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"}
)
public final class MemoryUi extends Element {

    private final DecimalFormat decimalFormat;

    @NotNull
    public Border drawElement() {
        RenderUtils.drawShadow(0.0F, 11.0F, 100.0F, 8.0F);
        Fonts.regular35.drawString("Memory", 2.5F, 2.5F, (new Color(255, 255, 255)).getRGB(), true);
        Fonts.regular35.drawString("" + Memory.usedMemorySize + '/' + Memory.maxMemorySize + "MB", 100.0F - (float) Fonts.regular35.getStringWidth("" + Memory.usedMemorySize + '/' + Memory.maxMemorySize + "MB"), 21.5F, (new Color(255, 255, 255)).getRGB(), true);
        Fonts.regular35.drawString(this.decimalFormat.format(Float.valueOf(Memory.getMemory() * (float) 100)).toString() + "%", 100.0F - (float) Fonts.regular35.getStringWidth(this.decimalFormat.format(Float.valueOf(Memory.getMemory() * (float) 100)).toString() + "%"), 2.5F, (new Color(255, 255, 255)).getRGB(), true);
        RenderUtils.drawRect(0.0F, 11.0F, 100.0F, 19.0F, new Color(0, 0, 0, 95));
        RenderUtils.drawRect(0.0F, 11.0F, (float) 100 * Memory.getMemory(), 19.0F, new Color(255, 255, 255, 200));
        return new Border(-1.0F, 0.0F, 101.0F, 22.5F + (float) Fonts.regular35.getFontHeight());
    }

    public MemoryUi(double x, double y) {
        super(x, y, 0.0F, (Side) null, 12, (DefaultConstructorMarker) null);
        this.decimalFormat = new DecimalFormat("##0", new DecimalFormatSymbols(Locale.ENGLISH));
    }

    public MemoryUi(double d0, double d1, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 0.0D;
        }

        if ((i & 2) != 0) {
            d1 = 0.0D;
        }

        this(d0, d1);
    }

    public MemoryUi() {
        this(0.0D, 0.0D, 3, (DefaultConstructorMarker) null);
    }
}
