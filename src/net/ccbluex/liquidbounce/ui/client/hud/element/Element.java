package net.ccbluex.liquidbounce.ui.client.hud.element;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u001a\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010>\u001a\u00020\u0011H\u0016J\b\u0010?\u001a\u00020@H\u0016J\n\u0010A\u001a\u0004\u0018\u00010\u000bH&J\u0018\u0010B\u001a\u00020@2\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FH\u0016J \u0010G\u001a\u00020@2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010H\u001a\u00020FH\u0016J\u0018\u0010I\u001a\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0016J\b\u0010J\u001a\u00020@H\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u001b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010 \"\u0004\b%\u0010\"R$\u0010\'\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00038F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R$\u0010,\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00038F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b-\u0010)\"\u0004\b.\u0010+R&\u0010\u0005\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\u00068F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010 \"\u0004\b0\u0010\"R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001e\u00105\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u000307068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010)\"\u0004\b;\u0010+R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010)\"\u0004\b=\u0010+¨\u0006K"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "border", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getBorder", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "setBorder", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;)V", "drag", "", "getDrag", "()Z", "setDrag", "(Z)V", "info", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/ElementInfo;", "getInfo", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/ElementInfo;", "name", "", "getName", "()Ljava/lang/String;", "prevMouseX", "getPrevMouseX", "()F", "setPrevMouseX", "(F)V", "prevMouseY", "getPrevMouseY", "setPrevMouseY", "value", "renderX", "getRenderX", "()D", "setRenderX", "(D)V", "renderY", "getRenderY", "setRenderY", "getScale", "setScale", "getSide", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "setSide", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "()Ljava/util/List;", "getX", "setX", "getY", "setY", "createElement", "destroyElement", "", "drawElement", "handleKey", "c", "", "keyCode", "", "handleMouseClick", "mouseButton", "isInBorder", "updateElement", "LiquidBounce"}
)
public abstract class Element extends MinecraftInstance {

    @NotNull
    private final ElementInfo info;
    private float scale;
    @Nullable
    private Border border;
    private boolean drag;
    private float prevMouseX;
    private float prevMouseY;
    private double x;
    private double y;
    @NotNull
    private Side side;

    @NotNull
    public final ElementInfo getInfo() {
        return this.info;
    }

    public final float getScale() {
        return this.info.disableScale() ? 1.0F : this.scale;
    }

    public final void setScale(float value) {
        if (!this.info.disableScale()) {
            this.scale = value;
        }
    }

    @NotNull
    public final String getName() {
        return this.info.name();
    }

    public final double getRenderX() {
        double d0;
        IClassProvider iclassprovider;
        IMinecraft iminecraft;

        switch (Element$WhenMappings.$EnumSwitchMapping$0[this.side.getHorizontal().ordinal()]) {
        case 1:
            d0 = this.x;
            break;

        case 2:
            iclassprovider = MinecraftInstance.classProvider;
            iminecraft = MinecraftInstance.mc;
            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
            d0 = (double) (iclassprovider.createScaledResolution(iminecraft).getScaledWidth() / 2) - this.x;
            break;

        case 3:
            iclassprovider = MinecraftInstance.classProvider;
            iminecraft = MinecraftInstance.mc;
            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
            d0 = (double) iclassprovider.createScaledResolution(iminecraft).getScaledWidth() - this.x;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return d0;
    }

    public final void setRenderX(double value) {
        switch (Element$WhenMappings.$EnumSwitchMapping$1[this.side.getHorizontal().ordinal()]) {
        case 1:
            this.x += value;
            break;

        case 2:
        case 3:
            this.x -= value;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

    }

    public final double getRenderY() {
        double d0;
        IClassProvider iclassprovider;
        IMinecraft iminecraft;

        switch (Element$WhenMappings.$EnumSwitchMapping$2[this.side.getVertical().ordinal()]) {
        case 1:
            d0 = this.y;
            break;

        case 2:
            iclassprovider = MinecraftInstance.classProvider;
            iminecraft = MinecraftInstance.mc;
            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
            d0 = (double) (iclassprovider.createScaledResolution(iminecraft).getScaledHeight() / 2) - this.y;
            break;

        case 3:
            iclassprovider = MinecraftInstance.classProvider;
            iminecraft = MinecraftInstance.mc;
            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
            d0 = (double) iclassprovider.createScaledResolution(iminecraft).getScaledHeight() - this.y;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return d0;
    }

    public final void setRenderY(double value) {
        switch (Element$WhenMappings.$EnumSwitchMapping$3[this.side.getVertical().ordinal()]) {
        case 1:
            this.y += value;
            break;

        case 2:
        case 3:
            this.y -= value;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

    }

    @Nullable
    public final Border getBorder() {
        return this.border;
    }

    public final void setBorder(@Nullable Border <set-?>) {
        this.border = <set-?>;
    }

    public final boolean getDrag() {
        return this.drag;
    }

    public final void setDrag(boolean <set-?>) {
        this.drag = <set-?>;
    }

    public final float getPrevMouseX() {
        return this.prevMouseX;
    }

    public final void setPrevMouseX(float <set-?>) {
        this.prevMouseX = <set-?>;
    }

    public final float getPrevMouseY() {
        return this.prevMouseY;
    }

    public final void setPrevMouseY(float <set-?>) {
        this.prevMouseY = <set-?>;
    }

    @NotNull
    public List getValues() {
        Field[] afield = this.getClass().getDeclaredFields();

        Intrinsics.checkExpressionValueIsNotNull(afield, "javaClass.declaredFields");
        Field[] $this$filterIsInstance$iv = afield;
        boolean $i$f$filterIsInstance = false;
        Collection destination$iv$iv = (Collection) (new ArrayList($this$filterIsInstance$iv.length));
        boolean $i$f$filterIsInstanceTo = false;
        Field[] afield1 = $this$filterIsInstance$iv;
        int element$iv$iv = $this$filterIsInstance$iv.length;

        for (int i = 0; i < element$iv$iv; ++i) {
            Field item$iv$iv = afield1[i];
            boolean $i$a$-map-Element$values$1 = false;

            Intrinsics.checkExpressionValueIsNotNull(item$iv$iv, "valueField");
            item$iv$iv.setAccessible(true);
            Object object = item$iv$iv.get(this);

            destination$iv$iv.add(object);
        }

        Iterable iterable = (Iterable) ((List) destination$iv$iv);

        $i$f$filterIsInstance = false;
        destination$iv$iv = (Collection) (new ArrayList());
        $i$f$filterIsInstanceTo = false;
        Iterator iterator = iterable.iterator();

        while (iterator.hasNext()) {
            Object object1 = iterator.next();

            if (object1 instanceof Value) {
                destination$iv$iv.add(object1);
            }
        }

        return (List) destination$iv$iv;
    }

    public boolean createElement() {
        return true;
    }

    public void destroyElement() {}

    @Nullable
    public abstract Border drawElement();

    public void updateElement() {}

    public boolean isInBorder(double x, double y) {
        Border border = this.border;

        if (this.border == null) {
            return false;
        } else {
            Border border = border;
            float minY = border.getX();
            float maxX = border.getX2();
            boolean maxY = false;
            float minX = Math.min(minY, maxX);

            maxX = border.getY();
            float maxY1 = border.getY2();
            boolean flag = false;

            minY = Math.min(maxX, maxY1);
            maxY1 = border.getX();
            float f = border.getX2();
            boolean flag1 = false;

            maxX = Math.max(maxY1, f);
            f = border.getY();
            float f1 = border.getY2();
            boolean flag2 = false;

            maxY1 = Math.max(f, f1);
            return (double) minX <= x && (double) minY <= y && (double) maxX >= x && (double) maxY1 >= y;
        }
    }

    public void handleMouseClick(double x, double y, int mouseButton) {}

    public void handleKey(char c, int keyCode) {}

    public final double getX() {
        return this.x;
    }

    public final void setX(double <set-?>) {
        this.x = <set-?>;
    }

    public final double getY() {
        return this.y;
    }

    public final void setY(double <set-?>) {
        this.y = <set-?>;
    }

    @NotNull
    public final Side getSide() {
        return this.side;
    }

    public final void setSide(@NotNull Side <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.side = <set-?>;
    }

    public Element(double x, double y, float scale, @NotNull Side side) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        super();
        this.x = x;
        this.y = y;
        this.side = side;
        ElementInfo elementinfo = (ElementInfo) this.getClass().getAnnotation(ElementInfo.class);

        if (elementinfo != null) {
            this.info = elementinfo;
            this.scale = 1.0F;
            this.setScale(scale);
        } else {
            throw (Throwable) (new IllegalArgumentException("Passed element with missing element info"));
        }
    }

    public Element(double d0, double d1, float f, Side side, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 1) != 0) {
            d0 = 2.0D;
        }

        if ((i & 2) != 0) {
            d1 = 2.0D;
        }

        if ((i & 4) != 0) {
            f = 1.0F;
        }

        if ((i & 8) != 0) {
            side = Side.Companion.default();
        }

        this(d0, d1, f, side);
    }

    public Element() {
        this(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null);
    }
}
