package net.ccbluex.liquidbounce.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.value.ListValue;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002+,B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010 \u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010!\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\"\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010#\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010$\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010%\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020)J\u000e\u0010*\u001a\u00020\'2\u0006\u0010(\u001a\u00020)¨\u0006-"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/EaseUtils;", "", "()V", "apply", "", "type", "Lnet/ccbluex/liquidbounce/utils/EaseUtils$EnumEasingType;", "order", "Lnet/ccbluex/liquidbounce/utils/EaseUtils$EnumEasingOrder;", "value", "easeInBack", "x", "easeInCirc", "easeInCubic", "easeInElastic", "easeInExpo", "easeInOutBack", "easeInOutCirc", "easeInOutCubic", "easeInOutElastic", "easeInOutExpo", "easeInOutQuad", "easeInOutQuart", "easeInOutQuint", "easeInOutSine", "easeInQuad", "easeInQuart", "easeInQuint", "easeInSine", "easeOutBack", "easeOutCirc", "easeOutCubic", "easeOutElastic", "easeOutExpo", "easeOutQuad", "easeOutQuart", "easeOutQuint", "easeOutSine", "getEnumEasingList", "Lnet/ccbluex/liquidbounce/value/ListValue;", "name", "", "getEnumEasingOrderList", "EnumEasingOrder", "EnumEasingType", "LiquidBounce"}
)
public final class EaseUtils {

    public static final EaseUtils INSTANCE;

    public final double easeInSine(double x) {
        double d0 = (double) 1;
        double d1 = x * 3.141592653589793D / (double) 2;
        double d2 = d0;
        boolean flag = false;
        double d3 = Math.cos(d1);

        return d2 - d3;
    }

    public final double easeOutSine(double x) {
        double d0 = x * 3.141592653589793D / (double) 2;
        boolean flag = false;

        return Math.sin(d0);
    }

    public final double easeInOutSine(double x) {
        double d0 = 3.141592653589793D * x;
        boolean flag = false;

        return -(Math.cos(d0) - (double) 1) / (double) 2;
    }

    public final double easeInQuad(double x) {
        return x * x;
    }

    public final double easeOutQuad(double x) {
        return (double) 1 - ((double) 1 - x) * ((double) 1 - x);
    }

    public final double easeInOutQuad(double x) {
        double d0;

        if (x < 0.5D) {
            d0 = (double) 2 * x * x;
        } else {
            d0 = (double) 1;
            double d1 = (double) -2 * x + (double) 2;
            byte b0 = 2;
            double d2 = d0;
            boolean flag = false;
            double d3 = Math.pow(d1, (double) b0);

            d0 = d2 - d3 / (double) 2;
        }

        return d0;
    }

    public final double easeInCubic(double x) {
        return x * x * x;
    }

    public final double easeOutCubic(double x) {
        double d0 = (double) 1;
        double d1 = (double) 1 - x;
        byte b0 = 3;
        double d2 = d0;
        boolean flag = false;
        double d3 = Math.pow(d1, (double) b0);

        return d2 - d3;
    }

    public final double easeInOutCubic(double x) {
        double d0;

        if (x < 0.5D) {
            d0 = (double) 4 * x * x * x;
        } else {
            d0 = (double) 1;
            double d1 = (double) -2 * x + (double) 2;
            byte b0 = 3;
            double d2 = d0;
            boolean flag = false;
            double d3 = Math.pow(d1, (double) b0);

            d0 = d2 - d3 / (double) 2;
        }

        return d0;
    }

    public final double easeInQuart(double x) {
        return x * x * x * x;
    }

    @JvmStatic
    public static final double easeOutQuart(double x) {
        double d0 = (double) 1;
        double d1 = (double) 1 - x;
        byte b0 = 4;
        double d2 = d0;
        boolean flag = false;
        double d3 = Math.pow(d1, (double) b0);

        return d2 - d3;
    }

    public final double easeInOutQuart(double x) {
        double d0;

        if (x < 0.5D) {
            d0 = (double) 8 * x * x * x * x;
        } else {
            d0 = (double) 1;
            double d1 = (double) -2 * x + (double) 2;
            byte b0 = 4;
            double d2 = d0;
            boolean flag = false;
            double d3 = Math.pow(d1, (double) b0);

            d0 = d2 - d3 / (double) 2;
        }

        return d0;
    }

    public final double easeInQuint(double x) {
        return x * x * x * x * x;
    }

    public final double easeOutQuint(double x) {
        double d0 = (double) 1;
        double d1 = (double) 1 - x;
        byte b0 = 5;
        double d2 = d0;
        boolean flag = false;
        double d3 = Math.pow(d1, (double) b0);

        return d2 - d3;
    }

    public final double easeInOutQuint(double x) {
        double d0;

        if (x < 0.5D) {
            d0 = (double) 16 * x * x * x * x * x;
        } else {
            d0 = (double) 1;
            double d1 = (double) -2 * x + (double) 2;
            byte b0 = 5;
            double d2 = d0;
            boolean flag = false;
            double d3 = Math.pow(d1, (double) b0);

            d0 = d2 - d3 / (double) 2;
        }

        return d0;
    }

    public final double easeInExpo(double x) {
        double d0;

        if (x == 0.0D) {
            d0 = 0.0D;
        } else {
            double d1 = 2.0D;
            double d2 = (double) 10 * x - (double) 10;
            boolean flag = false;

            d0 = Math.pow(d1, d2);
        }

        return d0;
    }

    public final double easeOutExpo(double x) {
        double d0;

        if (x == 1.0D) {
            d0 = 1.0D;
        } else {
            d0 = (double) 1;
            double d1 = 2.0D;
            double d2 = (double) -10 * x;
            double d3 = d0;
            boolean flag = false;
            double d4 = Math.pow(d1, d2);

            d0 = d3 - d4;
        }

        return d0;
    }

    public final double easeInOutExpo(double x) {
        double d0;

        if (x == 0.0D) {
            d0 = 0.0D;
        } else if (x == 1.0D) {
            d0 = 1.0D;
        } else {
            double d1;
            double d2;
            boolean flag;

            if (x < 0.5D) {
                d1 = 2.0D;
                d2 = (double) 20 * x - (double) 10;
                flag = false;
                d0 = Math.pow(d1, d2) / (double) 2;
            } else {
                d0 = (double) 2;
                d1 = 2.0D;
                d2 = (double) -20 * x + (double) 10;
                double d3 = d0;

                flag = false;
                double d4 = Math.pow(d1, d2);

                d0 = (d3 - d4) / (double) 2;
            }
        }

        return d0;
    }

    public final double easeInCirc(double x) {
        double d0 = (double) 1;
        double d1 = (double) 1;
        byte b0 = 2;
        double d2 = d1;
        double d3 = d0;
        boolean flag = false;
        double d4 = Math.pow(x, (double) b0);
        double d5 = d2 - d4;
        boolean flag1 = false;

        d2 = Math.sqrt(d5);
        return d3 - d2;
    }

    public final double easeOutCirc(double x) {
        double d0 = (double) 1;
        double d1 = x - (double) 1;
        byte b0 = 2;
        double d2 = d0;
        boolean flag = false;
        double d3 = Math.pow(d1, (double) b0);

        d1 = d2 - d3;
        boolean flag1 = false;

        return Math.sqrt(d1);
    }

    public final double easeInOutCirc(double x) {
        double d0;
        byte b0;
        boolean flag;
        double d1;
        double d2;
        boolean flag1;
        double d3;

        if (x < 0.5D) {
            d3 = (double) 1;
            double d4 = (double) 1;

            d0 = (double) 2 * x;
            b0 = 2;
            d2 = d4;
            d1 = d3;
            flag = false;
            double d5 = Math.pow(d0, (double) b0);

            d0 = d2 - d5;
            flag1 = false;
            d2 = Math.sqrt(d0);
            d3 = (d1 - d2) / (double) 2;
        } else {
            d3 = (double) 1;
            d0 = (double) -2 * x + (double) 2;
            b0 = 2;
            d1 = d3;
            flag = false;
            d2 = Math.pow(d0, (double) b0);
            d0 = d1 - d2;
            flag1 = false;
            d3 = (Math.sqrt(d0) + (double) 1) / (double) 2;
        }

        return d3;
    }

    public final double easeInBack(double x) {
        double c1 = 1.70158D;
        double c3 = c1 + (double) 1;

        return c3 * x * x * x - c1 * x * x;
    }

    public final double easeOutBack(double x) {
        double c1 = 1.70158D;
        double c3 = c1 + (double) 1;
        double d0 = (double) 1;
        double d1 = x - (double) 1;
        byte b0 = 3;
        double d2 = d0;
        boolean flag = false;
        double d3 = Math.pow(d1, (double) b0);

        d0 = d2 + c3 * d3;
        d1 = x - (double) 1;
        b0 = 2;
        d2 = d0;
        flag = false;
        d3 = Math.pow(d1, (double) b0);
        return d2 + c1 * d3;
    }

    public final double easeInOutBack(double x) {
        double c1 = 1.70158D;
        double c2 = c1 * 1.525D;
        double d0;
        byte b0;
        boolean flag;
        double d1;

        if (x < 0.5D) {
            d0 = (double) 2 * x;
            b0 = 2;
            flag = false;
            d1 = Math.pow(d0, (double) b0) * ((c2 + (double) 1) * (double) 2 * x - c2) / (double) 2;
        } else {
            d0 = (double) 2 * x - (double) 2;
            b0 = 2;
            flag = false;
            d1 = (Math.pow(d0, (double) b0) * ((c2 + (double) 1) * (x * (double) 2 - (double) 2) + c2) + (double) 2) / (double) 2;
        }

        return d1;
    }

    public final double easeInElastic(double x) {
        double c4 = 2.0943951023931953D;
        double d0;

        if (x == 0.0D) {
            d0 = 0.0D;
        } else if (x == 1.0D) {
            d0 = 1.0D;
        } else {
            double d1 = -2.0D;
            double d2 = (double) 10 * x - (double) 10;
            boolean flag = false;

            d0 = Math.pow(d1, d2);
            d1 = (x * (double) 10 - 10.75D) * c4;
            double d3 = d0;
            boolean flag1 = false;
            double d4 = Math.sin(d1);

            d0 = d3 * d4;
        }

        return d0;
    }

    public final double easeOutElastic(double x) {
        double c4 = 2.0943951023931953D;
        double d0;

        if (x == 0.0D) {
            d0 = 0.0D;
        } else if (x == 1.0D) {
            d0 = 1.0D;
        } else {
            double d1 = 2.0D;
            double d2 = (double) -10 * x;
            boolean flag = false;

            d0 = Math.pow(d1, d2);
            d1 = (x * (double) 10 - 0.75D) * c4;
            double d3 = d0;
            boolean flag1 = false;
            double d4 = Math.sin(d1);

            d0 = d3 * d4 + (double) 1;
        }

        return d0;
    }

    public final double easeInOutElastic(double x) {
        double c5 = 1.3962634015954636D;
        double d0;

        if (x == 0.0D) {
            d0 = 0.0D;
        } else if (x == 1.0D) {
            d0 = 1.0D;
        } else {
            double d1;
            double d2;
            boolean flag;
            double d3;
            double d4;
            boolean flag1;

            if (x < 0.5D) {
                d1 = 2.0D;
                d2 = (double) 20 * x - (double) 10;
                flag = false;
                d0 = Math.pow(d1, d2);
                d1 = ((double) 20 * x - 11.125D) * c5;
                d3 = d0;
                flag1 = false;
                d4 = Math.sin(d1);
                d0 = -(d3 * d4) / (double) 2;
            } else {
                d1 = 2.0D;
                d2 = (double) -20 * x + (double) 10;
                flag = false;
                d0 = Math.pow(d1, d2);
                d1 = ((double) 20 * x - 11.125D) * c5;
                d3 = d0;
                flag1 = false;
                d4 = Math.sin(d1);
                d0 = d3 * d4 / (double) 2 + (double) 1;
            }
        }

        return d0;
    }

    @NotNull
    public final ListValue getEnumEasingList(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        EaseUtils.EnumEasingType[] $this$toTypedArray$iv = EaseUtils.EnumEasingType.values();
        boolean $i$f$toTypedArray = false;
        Collection destination$iv$iv = (Collection) (new ArrayList($this$toTypedArray$iv.length));
        boolean $i$f$mapTo = false;
        EaseUtils.EnumEasingType[] aeaseutils_enumeasingtype = $this$toTypedArray$iv;
        int i = $this$toTypedArray$iv.length;

        for (int j = 0; j < i; ++j) {
            EaseUtils.EnumEasingType item$iv$iv = aeaseutils_enumeasingtype[j];
            boolean $i$a$-map-EaseUtils$getEnumEasingList$1 = false;
            String s = item$iv$iv.toString();

            destination$iv$iv.add(s);
        }

        List list = (List) destination$iv$iv;
        Collection collection = (Collection) list;

        $i$f$toTypedArray = false;
        Object[] aobject = collection.toArray(new String[0]);

        if (aobject == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        } else {
            Object[] aobject1 = aobject;
            String[] astring = (String[]) aobject1;
            String s1 = EaseUtils.EnumEasingType.SINE.toString();
            String[] astring1 = astring;

            return new ListValue(name, astring1, s1);
        }
    }

    @NotNull
    public final ListValue getEnumEasingOrderList(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        EaseUtils.EnumEasingOrder[] $this$toTypedArray$iv = EaseUtils.EnumEasingOrder.values();
        boolean $i$f$toTypedArray = false;
        Collection destination$iv$iv = (Collection) (new ArrayList($this$toTypedArray$iv.length));
        boolean $i$f$mapTo = false;
        EaseUtils.EnumEasingOrder[] aeaseutils_enumeasingorder = $this$toTypedArray$iv;
        int i = $this$toTypedArray$iv.length;

        for (int j = 0; j < i; ++j) {
            EaseUtils.EnumEasingOrder item$iv$iv = aeaseutils_enumeasingorder[j];
            boolean $i$a$-map-EaseUtils$getEnumEasingOrderList$1 = false;
            String s = item$iv$iv.toString();

            destination$iv$iv.add(s);
        }

        List list = (List) destination$iv$iv;
        Collection collection = (Collection) list;

        $i$f$toTypedArray = false;
        Object[] aobject = collection.toArray(new String[0]);

        if (aobject == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        } else {
            Object[] aobject1 = aobject;
            String[] astring = (String[]) aobject1;
            String s1 = EaseUtils.EnumEasingOrder.FAST_AT_START.toString();
            String[] astring1 = astring;

            return new ListValue(name, astring1, s1);
        }
    }

    public final double apply(@NotNull EaseUtils.EnumEasingType type, @NotNull EaseUtils.EnumEasingOrder order, double value) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(order, "order");
        if (type == EaseUtils.EnumEasingType.NONE) {
            return value;
        } else {
            String methodName = "ease" + order.getMethodName() + type.getFriendlyName();
            Method[] amethod = this.getClass().getDeclaredMethods();

            Intrinsics.checkExpressionValueIsNotNull(amethod, "this.javaClass.declaredMethods");
            Method[] amethod1 = amethod;
            boolean flag = false;
            boolean it = false;
            Method[] $i$a$-also-EaseUtils$apply$2 = amethod1;
            int i = amethod1.length;
            int j = 0;

            Method method;

            while (true) {
                if (j >= i) {
                    method = null;
                    break;
                }

                Method method1 = $i$a$-also-EaseUtils$apply$2[j];
                boolean $i$a$-find-EaseUtils$apply$1 = false;

                Intrinsics.checkExpressionValueIsNotNull(method1, "it");
                if (method1.getName().equals(methodName)) {
                    method = method1;
                    break;
                }

                ++j;
            }

            Method method2 = method;

            flag = false;
            boolean flag1 = false;
            boolean flag2 = false;
            double d0;

            if (method2 != null) {
                Object object = method2.invoke(EaseUtils.INSTANCE, new Object[] { Double.valueOf(value)});

                if (object == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Double");
                }

                d0 = ((Double) object).doubleValue();
            } else {
                ClientUtils.getLogger().log(Level.ERROR, "Cannot found easing method: " + methodName);
                d0 = value;
            }

            return d0;
        }
    }

    static {
        EaseUtils easeutils = new EaseUtils();

        INSTANCE = easeutils;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"},
        d2 = { "Lnet/ccbluex/liquidbounce/utils/EaseUtils$EnumEasingType;", "", "(Ljava/lang/String;I)V", "friendlyName", "", "getFriendlyName", "()Ljava/lang/String;", "NONE", "SINE", "QUAD", "CUBIC", "QUART", "QUINT", "EXPO", "CIRC", "BACK", "ELASTIC", "LiquidBounce"}
    )
    public static enum EnumEasingType {

        NONE, SINE, QUAD, CUBIC, QUART, QUINT, EXPO, CIRC, BACK, ELASTIC;

        @NotNull
        private final String friendlyName;

        @NotNull
        public final String getFriendlyName() {
            return this.friendlyName;
        }

        private EnumEasingType() {
            StringBuilder stringbuilder = new StringBuilder();
            String s = this.name();
            byte b0 = 0;
            byte b1 = 1;
            StringBuilder stringbuilder1 = stringbuilder;
            boolean flag = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.substring(b0, b1);

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                String s2 = s1;
                boolean flag1 = false;

                if (s2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                } else {
                    s1 = s2.toUpperCase();
                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toUpperCase()");
                    s2 = s1;
                    stringbuilder = stringbuilder1.append(s2);
                    s = this.name();
                    b0 = 1;
                    int i = this.name().length();

                    stringbuilder1 = stringbuilder;
                    flag = false;
                    if (s == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    } else {
                        s1 = s.substring(b0, i);
                        Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        s2 = s1;
                        flag1 = false;
                        if (s2 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        } else {
                            s1 = s2.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                            s2 = s1;
                            this.friendlyName = stringbuilder1.append(s2).toString();
                        }
                    }
                }
            }
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"},
        d2 = { "Lnet/ccbluex/liquidbounce/utils/EaseUtils$EnumEasingOrder;", "", "methodName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getMethodName", "()Ljava/lang/String;", "FAST_AT_START", "FAST_AT_END", "FAST_AT_START_AND_END", "LiquidBounce"}
    )
    public static enum EnumEasingOrder {

        FAST_AT_START, FAST_AT_END, FAST_AT_START_AND_END;

        @NotNull
        private final String methodName;

        @NotNull
        public final String getMethodName() {
            return this.methodName;
        }

        private EnumEasingOrder(String methodName) {
            this.methodName = methodName;
        }
    }
}
