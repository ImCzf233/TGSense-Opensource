package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(
    name = "Notifications",
    single = true
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010\f\u001a\u0004\u0018\u00010\rH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notifications;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "exampleNotification", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"}
)
public final class Notifications extends Element {

    private final Notification exampleNotification;

    @Nullable
    public Border drawElement() {
        Iterable $this$forEachIndexed$iv = (Iterable) LiquidBounce.INSTANCE.getHud().getNotifications();
        boolean $i$f$forEachIndexed = false;
        Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$forEachIndexed$iv, 10)));
        boolean item$iv = false;
        Iterator iterator = $this$forEachIndexed$iv.iterator();

        while (iterator.hasNext()) {
            Object item$iv$iv = iterator.next();
            Notification it = (Notification) item$iv$iv;
            boolean notify = false;

            destination$iv$iv.add(it);
        }

        $this$forEachIndexed$iv = (Iterable) ((List) destination$iv$iv);
        $i$f$forEachIndexed = false;
        int index$iv = 0;

        for (Iterator iterator1 = $this$forEachIndexed$iv.iterator(); iterator1.hasNext(); GL11.glPopMatrix()) {
            Object object = iterator1.next();
            int i = index$iv++;
            boolean flag = false;

            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }

            Notification notification = (Notification) object;
            boolean $i$a$-forEachIndexed-Notifications$drawElement$2 = false;

            GL11.glPushMatrix();
            if (notification.drawNotification(i)) {
                LiquidBounce.INSTANCE.getHud().getNotifications().remove(notification);
            }
        }

        if (MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen())) {
            if (!LiquidBounce.INSTANCE.getHud().getNotifications().contains(this.exampleNotification)) {
                LiquidBounce.INSTANCE.getHud().addNotification(this.exampleNotification);
            }

            this.exampleNotification.setFadeState(FadeState.STAY);
            this.exampleNotification.setDisplayTime(System.currentTimeMillis());
            return new Border(-((float) this.exampleNotification.getWidth()), -((float) this.exampleNotification.getHeight()), 0.0F, 0.0F);
        } else {
            return null;
        }
    }

    public Notifications(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        super(x, y, scale, side);
        this.exampleNotification = new Notification("Notification", "This is an example notification.", NotifyType.INFO, 0, 0, 24, (DefaultConstructorMarker) null);
    }

    public Notifications(double d0, double d1, float f, Side side, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 3.0D;
        }

        if ((i & 2) != 0) {
            d1 = 11.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        if ((i & 8) != 0) {
            side = new Side(Side.Horizontal.RIGHT, Side.Vertical.DOWN);
        }

        this(d0, d1, f, side);
    }

    public Notifications() {
        this(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
    }
}
