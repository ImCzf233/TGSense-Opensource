package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nJ\u000e\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020\bR\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0010\"\u0004\b\u0015\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0010\"\u0004\b\u001a\u0010\u0012R\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\fR\u0011\u0010#\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u001a\u0010\'\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\f\"\u0004\b)\u0010*R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0011\u0010/\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\fR\u001a\u00101\u001a\u000202X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001a\u00107\u001a\u000202X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00104\"\u0004\b9\u00106¨\u0006="},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "", "title", "", "content", "type", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/NotifyType;", "time", "", "animeTime", "(Ljava/lang/String;Ljava/lang/String;Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/NotifyType;II)V", "getAnimeTime", "()I", "animeXTime", "", "getAnimeXTime", "()J", "setAnimeXTime", "(J)V", "animeYTime", "getAnimeYTime", "setAnimeYTime", "getContent", "()Ljava/lang/String;", "displayTime", "getDisplayTime", "setDisplayTime", "fadeState", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/FadeState;", "getFadeState", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/FadeState;", "setFadeState", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/FadeState;)V", "height", "getHeight", "image", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getImage", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "nowY", "getNowY", "setNowY", "(I)V", "getTime", "getTitle", "getType", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/NotifyType;", "width", "getWidth", "x", "", "getX", "()F", "setX", "(F)V", "y", "getY", "setY", "drawNotification", "", "index", "LiquidBounce"}
)
public final class Notification {

    private final int width;
    private final int height;
    @NotNull
    private final IResourceLocation image;
    @NotNull
    private FadeState fadeState;
    private float x;
    private float y;
    private int nowY;
    private long displayTime;
    private long animeXTime;
    private long animeYTime;
    @NotNull
    private final String title;
    @NotNull
    private final String content;
    @NotNull
    private final NotifyType type;
    private final int time;
    private final int animeTime;

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    @NotNull
    public final IResourceLocation getImage() {
        return this.image;
    }

    @NotNull
    public final FadeState getFadeState() {
        return this.fadeState;
    }

    public final void setFadeState(@NotNull FadeState <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.fadeState = <set-?>;
    }

    public final float getX() {
        return this.x;
    }

    public final void setX(float <set-?>) {
        this.x = <set-?>;
    }

    public final float getY() {
        return this.y;
    }

    public final void setY(float <set-?>) {
        this.y = <set-?>;
    }

    public final int getNowY() {
        return this.nowY;
    }

    public final void setNowY(int <set-?>) {
        this.nowY = <set-?>;
    }

    public final long getDisplayTime() {
        return this.displayTime;
    }

    public final void setDisplayTime(long <set-?>) {
        this.displayTime = <set-?>;
    }

    public final long getAnimeXTime() {
        return this.animeXTime;
    }

    public final void setAnimeXTime(long <set-?>) {
        this.animeXTime = <set-?>;
    }

    public final long getAnimeYTime() {
        return this.animeYTime;
    }

    public final void setAnimeYTime(long <set-?>) {
        this.animeYTime = <set-?>;
    }

    public final boolean drawNotification(int index) {
        int realY = -(index + 1) * this.height;
        long nowTime = System.currentTimeMillis();
        double pct;

        if (this.nowY != realY) {
            pct = (double) (nowTime - this.animeYTime) / (double) this.animeTime;
            if (pct > (double) 1) {
                this.nowY = realY;
                pct = 1.0D;
            } else {
                pct = EaseUtils.easeOutExpo(pct);
            }

            GL11.glTranslated(0.0D, (double) (realY - this.nowY) * pct, 0.0D);
        } else {
            this.animeYTime = nowTime;
        }

        GL11.glTranslated(0.0D, (double) this.nowY, 0.0D);
        pct = (double) (nowTime - this.animeXTime) / (double) this.animeTime;
        switch (Notification$WhenMappings.$EnumSwitchMapping$0[this.fadeState.ordinal()]) {
        case 1:
            if (pct > (double) 1) {
                this.fadeState = FadeState.STAY;
                this.animeXTime = nowTime;
                pct = 1.0D;
            }

            pct = EaseUtils.easeOutExpo(pct);
            break;

        case 2:
            pct = 1.0D;
            if (nowTime - this.animeXTime > (long) this.time) {
                this.fadeState = FadeState.OUT;
                this.animeXTime = nowTime;
            }
            break;

        case 3:
            if (pct > (double) 1) {
                this.fadeState = FadeState.END;
                this.animeXTime = nowTime;
                pct = 1.0D;
            }

            pct = (double) 1 - EaseUtils.easeInExpo(pct);
            break;

        case 4:
            return true;
        }

        GL11.glTranslated((double) this.width - (double) this.width * pct, 0.0D, 0.0D);
        GL11.glTranslatef(-((float) this.width), 0.0F, 0.0F);
        RenderUtils.drawRoundRect(-25.0F, 0.0F, (float) this.width, (float) this.height, 3.0F, -1);
        float f = (float) this.height - 2.0F;
        float f1 = (float) this.width - (float) this.width * ((float) (nowTime - this.displayTime) / ((float) this.animeTime * 2.0F + (float) this.time));
        float f2 = 0.0F;
        float f3 = f;
        float f4 = -23.0F;
        boolean flag = false;
        float f5 = Math.max(f1, f2);

        RenderUtils.drawRect(f4, f3, f5, (float) this.height, this.type.getRenderColor());
        IFontRenderer ifontrenderer = Fonts.bold40;
        Color color = Color.BLACK;

        Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
        ifontrenderer.drawString("Module Toggled", 4.0F, 6.0F, color.getRGB());
        ifontrenderer = Fonts.font25;
        String s = this.content;

        color = Color.BLACK;
        Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
        ifontrenderer.drawString(s, 4.0F, 17.0F, color.getRGB());
        RenderUtils.drawImage(this.image, -((int) this.x) - 21, -((int) this.y) - 28 + 32, 20, 20);
        return false;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }

    @NotNull
    public final NotifyType getType() {
        return this.type;
    }

    public final int getTime() {
        return this.time;
    }

    public final int getAnimeTime() {
        return this.animeTime;
    }

    public Notification(@NotNull String title, @NotNull String content, @NotNull NotifyType type, int time, int animeTime) {
        Intrinsics.checkParameterIsNotNull(title, "title");
        Intrinsics.checkParameterIsNotNull(content, "content");
        Intrinsics.checkParameterIsNotNull(type, "type");
        super();
        this.title = title;
        this.content = content;
        this.type = type;
        this.time = time;
        this.animeTime = animeTime;
        this.width = RangesKt.coerceAtLeast(100, RangesKt.coerceAtLeast(Fonts.font40.getStringWidth(this.title), Fonts.font40.getStringWidth(this.content)) + 10);
        this.height = 30;
        this.image = MinecraftInstance.classProvider.createResourceLocation("langya/notifications/" + this.type.name() + ".png");
        this.fadeState = FadeState.IN;
        this.nowY = -this.height;
        this.displayTime = System.currentTimeMillis();
        this.animeXTime = System.currentTimeMillis();
        this.animeYTime = System.currentTimeMillis();
    }

    public Notification(String s, String s1, NotifyType notifytype, int i, int j, int k, DefaultConstructorMarker defaultconstructormarker) {
        if ((k & 8) != 0) {
            i = 1500;
        }

        if ((k & 16) != 0) {
            j = 500;
        }

        this(s, s1, notifytype, i, j);
    }
}
