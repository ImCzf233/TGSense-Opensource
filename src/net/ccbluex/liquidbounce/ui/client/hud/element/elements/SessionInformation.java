package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.InfosUtils.Recorder;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.ScaleUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

@ElementInfo(
    name = "SessionInformation",
    disableScale = true,
    priority = 1
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0003R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/SessionInformation;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "blueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorBlueValue2", "colorGreenValue2", "colorRedValue2", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "greenValue", "lineValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "playerbans", "", "getPlayerbans", "()I", "setPlayerbans", "(I)V", "redValue", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"}
)
public final class SessionInformation extends Element {

    private final BoolValue lineValue;
    private final IntegerValue redValue;
    private final IntegerValue greenValue;
    private final IntegerValue blueValue;
    private final IntegerValue colorRedValue2;
    private final IntegerValue colorGreenValue2;
    private final IntegerValue colorBlueValue2;
    private final FontValue fontValue;
    private int playerbans;

    public final int getPlayerbans() {
        return this.playerbans;
    }

    public final void setPlayerbans(int <set-?>) {
        this.playerbans = <set-?>;
    }

    @NotNull
    public Border drawElement() {
        IFontRenderer fontRenderer = (IFontRenderer) this.fontValue.get();
        double y2 = (double) (fontRenderer.getFontHeight() * 3) + 11.0D;
        double x2 = 140.0D;
        byte playerbans = 0;
        long durationInMillis = System.currentTimeMillis() - Recorder.INSTANCE.getStartTime();
        long second = durationInMillis / (long) 1000 % (long) 60;
        long minute = durationInMillis / (long) '\uea60' % (long) 60;
        long hour = durationInMillis / (long) 3600000 % (long) 24;
        String time = null;
        StringCompanionObject username = StringCompanionObject.INSTANCE;
        String s = "%02dh %02dm %02ds";
        Object[] aobject = new Object[] { Long.valueOf(hour), Long.valueOf(minute), Long.valueOf(second)};
        boolean flag = false;
        String s1 = String.format(s, Arrays.copyOf(aobject, aobject.length));

        Intrinsics.checkExpressionValueIsNotNull(s1, "java.lang.String.format(format, *args)");
        time = s1;
        ScaleUtils.drawOutline(-2.0F, -2.0F, (float) x2, (float) y2, 5.05F, 0.0F, 0.0F);
        if (((Boolean) this.lineValue.get()).booleanValue()) {
            RenderUtils.drawGradientSideways(2.44D, (double) fontRenderer.getFontHeight() + 2.5D + 0.0D, 135.56D, (double) fontRenderer.getFontHeight() + 2.5D + 1.16D, (new Color(((Number) this.redValue.get()).intValue(), ((Number) this.greenValue.get()).intValue(), ((Number) this.blueValue.get()).intValue())).getRGB(), (new Color(((Number) this.colorRedValue2.get()).intValue(), ((Number) this.colorGreenValue2.get()).intValue(), ((Number) this.colorBlueValue2.get()).intValue())).getRGB());
        }

        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        String username1 = ientityplayersp.getName();
        int i = (int) ((float) x2 / 5.0F);
        Color color = Color.WHITE;

        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        fontRenderer.drawStringWithShadow("Session Information", i, 3, color.getRGB());
        String s2 = "Play Time: " + time;
        int j = (int) ((float) fontRenderer.getFontHeight() + 8.0F);

        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        fontRenderer.drawStringWithShadow(s2, 2, j, color.getRGB());
        s2 = "Current ID: " + username1;
        j = (int) ((float) (fontRenderer.getFontHeight() * 2) + 8.0F);
        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        fontRenderer.drawStringWithShadow(s2, 2, j, color.getRGB());
        s2 = "OtherplayerBanned: " + playerbans;
        j = (int) ((float) (fontRenderer.getFontHeight() * 3) + 8.0F);
        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        fontRenderer.drawStringWithShadow(s2, 2, j, color.getRGB());
        return new Border(-2.0F, -2.0F, (float) x2, (float) y2);
    }

    @EventTarget
    private final void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C00Handshake) {
            Recorder.INSTANCE.setStartTime(System.currentTimeMillis());
        }

        IPacket ipacket = event.getPacket();

        if (ipacket == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketChat");
        } else {
            ITextComponent itextcomponent = ((SPacketChat) ipacket).getChatComponent();

            Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "(event.packet as SPacketChat).chatComponent");
            String message = itextcomponent.getUnformattedText();

            ipacket = event.getPacket();
            if (ipacket == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketChat");
            } else {
                itextcomponent = ((SPacketChat) ipacket).getChatComponent();
                Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "(event.packet as SPacketChat).chatComponent");
                String text = itextcomponent.getUnformattedText();
                IPacket packet = event.getPacket();

                if (packet instanceof SPacketTitle) {
                    LiquidBounce.INSTANCE.getHud().addNotification(new Notification("PlayerBanned", "�?位使用垃圾配置的玩家被封了！", NotifyType.INFO, 0, 0, 24, (DefaultConstructorMarker) null));
                    Intrinsics.checkExpressionValueIsNotNull(text, "text");
                    if (StringsKt.contains$default((CharSequence) text, (CharSequence) "在本�?游戏中行为异�?", false, 2, (Object) null)) {
                        ++this.playerbans;
                    }
                }

            }
        }
    }

    public SessionInformation(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        super(x, y, scale, side);
        this.lineValue = new BoolValue("Line", true);
        this.redValue = new IntegerValue("Line-Red-1", 255, 0, 255);
        this.greenValue = new IntegerValue("Line-Green-1", 255, 0, 255);
        this.blueValue = new IntegerValue("Line-Blue-1", 255, 0, 255);
        this.colorRedValue2 = new IntegerValue("Line-Red-2", 0, 0, 255);
        this.colorGreenValue2 = new IntegerValue("Line-Green-2", 111, 0, 255);
        this.colorBlueValue2 = new IntegerValue("Line-Blue-2", 255, 0, 255);
        IFontRenderer ifontrenderer = Fonts.fontSFUI40;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.fontSFUI40, "Fonts.fontSFUI40");
        this.fontValue = new FontValue("Font", ifontrenderer);
    }

    public SessionInformation(double d0, double d1, float f, Side side, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 5.0D;
        }

        if ((i & 2) != 0) {
            d1 = 19.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        if ((i & 8) != 0) {
            side = new Side(Side.Horizontal.LEFT, Side.Vertical.UP);
        }

        this(d0, d1, f, side);
    }

    public SessionInformation() {
        this(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
    }
}
