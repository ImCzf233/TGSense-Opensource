package me.Skid.ui.elements.targets.utils;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0015B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014¨\u0006\u0016"},
    d2 = { "Lme/Skid/ui/elements/targets/utils/ShapeType;", "", "typeName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getTypeName", "()Ljava/lang/String;", "performRendering", "", "x", "", "y", "rad", "col", "Ljava/awt/Color;", "SOLID_CIRCLE", "CIRCLE", "SOLID_RECT", "RECT", "SOLID_TRIANGLE", "TRIANGLE", "Companion", "LiquidBounce"}
)
public enum ShapeType {

    SOLID_CIRCLE, CIRCLE, SOLID_RECT, RECT, SOLID_TRIANGLE, TRIANGLE;

    @NotNull
    private final String typeName;
    public static final ShapeType.Companion Companion = new ShapeType.Companion((DefaultConstructorMarker) null);

    public abstract void performRendering(float f, float f1, float f2, @NotNull Color color);

    @NotNull
    public final String getTypeName() {
        return this.typeName;
    }

    private ShapeType(String typeName) {
        this.typeName = typeName;
    }

    public ShapeType(String typeName, DefaultConstructorMarker $constructor_marker) {
        this(typeName);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"},
        d2 = { "Lme/Skid/ui/elements/targets/utils/ShapeType$SOLID_CIRCLE;", "Lme/Skid/ui/elements/targets/utils/ShapeType;", "performRendering", "", "x", "", "y", "rad", "col", "Ljava/awt/Color;", "LiquidBounce"}
    )
    static final class SOLID_CIRCLE extends ShapeType {

        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkParameterIsNotNull(col, "col");
            RenderUtils.drawFilledCircle((int) x, (int) y, rad, col);
        }

        SOLID_CIRCLE(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
            super("c_solid", (DefaultConstructorMarker) null);
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"},
        d2 = { "Lme/Skid/ui/elements/targets/utils/ShapeType$CIRCLE;", "Lme/Skid/ui/elements/targets/utils/ShapeType;", "performRendering", "", "x", "", "y", "rad", "col", "Ljava/awt/Color;", "LiquidBounce"}
    )
    static final class CIRCLE extends ShapeType {

        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkParameterIsNotNull(col, "col");
            RenderUtils.drawCircle(x, y, rad, (int) 0.5F, 0);
        }

        CIRCLE(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
            super("c_outline", (DefaultConstructorMarker) null);
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"},
        d2 = { "Lme/Skid/ui/elements/targets/utils/ShapeType$SOLID_RECT;", "Lme/Skid/ui/elements/targets/utils/ShapeType;", "performRendering", "", "x", "", "y", "rad", "col", "Ljava/awt/Color;", "LiquidBounce"}
    )
    static final class SOLID_RECT extends ShapeType {

        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkParameterIsNotNull(col, "col");
            RenderUtils.drawRect(x - rad / 2.0F, y - rad / 2.0F, x + rad / 2.0F, y + rad / 2.0F, col.getRGB());
        }

        SOLID_RECT(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
            super("r_solid", (DefaultConstructorMarker) null);
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"},
        d2 = { "Lme/Skid/ui/elements/targets/utils/ShapeType$RECT;", "Lme/Skid/ui/elements/targets/utils/ShapeType;", "performRendering", "", "x", "", "y", "rad", "col", "Ljava/awt/Color;", "LiquidBounce"}
    )
    static final class RECT extends ShapeType {

        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkParameterIsNotNull(col, "col");
            RenderUtils.drawBorder(x - rad / 2.0F, y - rad / 2.0F, x + rad / 2.0F, y + rad / 2.0F, 0.5F, col.getRGB());
        }

        RECT(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
            super("r_outline", (DefaultConstructorMarker) null);
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"},
        d2 = { "Lme/Skid/ui/elements/targets/utils/ShapeType$SOLID_TRIANGLE;", "Lme/Skid/ui/elements/targets/utils/ShapeType;", "performRendering", "", "x", "", "y", "rad", "col", "Ljava/awt/Color;", "LiquidBounce"}
    )
    static final class SOLID_TRIANGLE extends ShapeType {

        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkParameterIsNotNull(col, "col");
            RenderUtils.drawTriAngle(x, y, rad, 3.0F, col, true);
        }

        SOLID_TRIANGLE(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
            super("t_solid", (DefaultConstructorMarker) null);
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"},
        d2 = { "Lme/Skid/ui/elements/targets/utils/ShapeType$TRIANGLE;", "Lme/Skid/ui/elements/targets/utils/ShapeType;", "performRendering", "", "x", "", "y", "rad", "col", "Ljava/awt/Color;", "LiquidBounce"}
    )
    static final class TRIANGLE extends ShapeType {

        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkParameterIsNotNull(col, "col");
            RenderUtils.drawTriAngle(x, y, rad, 3.0F, col, false);
        }

        TRIANGLE(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
            super("t_outline", (DefaultConstructorMarker) null);
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
        d2 = { "Lme/Skid/ui/elements/targets/utils/ShapeType$Companion;", "", "()V", "getTypeFromName", "Lme/Skid/ui/elements/targets/utils/ShapeType;", "name", "", "LiquidBounce"}
    )
    public static final class Companion {

        @Nullable
        public final ShapeType getTypeFromName(@NotNull String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            ShapeType[] ashapetype = ShapeType.values();
            boolean flag = false;
            boolean flag1 = false;
            ShapeType[] ashapetype1 = ashapetype;
            int i = ashapetype.length;
            int j = 0;

            ShapeType shapetype;

            while (true) {
                if (j >= i) {
                    shapetype = null;
                    break;
                }

                ShapeType shapetype1 = ashapetype1[j];
                boolean $i$a$-find-ShapeType$Companion$getTypeFromName$1 = false;

                if (StringsKt.equals(shapetype1.getTypeName(), name, true)) {
                    shapetype = shapetype1;
                    break;
                }

                ++j;
            }

            return shapetype;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
