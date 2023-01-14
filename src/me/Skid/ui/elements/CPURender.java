package me.Skid.ui.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.ccbluex.liquidbounce.font.FontLoaders;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.blur.BlurBuffer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "CpuRender",
    disableScale = true,
    priority = 1
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016¨\u0006\b"},
    d2 = { "Lme/Skid/ui/elements/CPURender;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"}
)
public final class CPURender extends Element {

    @Nullable
    public Border drawElement() {
        long maxCpu = 100L;
        double totalCpu = ClientUtils.getCPUUse();
        double freeCpu = (double) maxCpu - totalCpu;
        double usedCpu = totalCpu - freeCpu;

        RenderUtils.drawRect(0, 0, 60, 70, (new Color(0, 0, 0, 30)).getRGB());
        RenderUtils.drawShadowWithCustomAlpha(0.0F, 0.0F, 60.0F, 70.0F, 255.0F);
        GL11.glTranslated(-this.getRenderX(), -this.getRenderY(), 0.0D);
        GL11.glPushMatrix();
        BlurBuffer.blurArea((float) this.getRenderX(), (float) this.getRenderY(), 60.0F, 70.0F);
        GL11.glPopMatrix();
        GL11.glTranslated(this.getRenderX(), this.getRenderY(), 0.0D);
        RenderUtils.drawArc(30.0F, 35.0F, 20.0D, (new Color(0, 0, 0, 50)).getRGB(), 0, 360.0D, 8);
        RenderUtils.drawArc(30.0F, 35.0F, 20.0D, (new Color(255, 255, 255)).getRGB(), 0, 360.0D * usedCpu / (double) maxCpu, 8);
        Fonts.font35.drawString("RAM", 30.0F - (float) (Fonts.font35.getStringWidth("RAM") / 2), 35.0F - (float) (Fonts.font35.getFontHeight() / 2), -1);
        FontLoaders.F16.drawString(usedCpu * (double) 100L / (double) maxCpu + "%", 30.0F - (float) (FontLoaders.C16.getStringWidth(usedCpu * (double) 100L / totalCpu + "%") / 2), 60.0F, -1);
        return new Border(0.0F, 0.0F, 60.0F, 70.0F);
    }

    public CPURender(double x, double y) {
        super(x, y, 0.0F, (Side) null, 12, (DefaultConstructorMarker) null);
    }

    public CPURender(double d0, double d1, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 5.0D;
        }

        if ((i & 2) != 0) {
            d1 = 130.0D;
        }

        this(d0, d1);
    }

    public CPURender() {
        this(0.0D, 0.0D, 3, (DefaultConstructorMarker) null);
    }
}
