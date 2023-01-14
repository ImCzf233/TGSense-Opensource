package net.ccbluex.liquidbounce.ui.client.hud.element;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 \u000f2\u00020\u0001:\u0003\u000f\u0010\u0011B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "", "horizontal", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "vertical", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;)V", "getHorizontal", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "setHorizontal", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;)V", "getVertical", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "setVertical", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;)V", "Companion", "Horizontal", "Vertical", "LiquidBounce"}
)
public final class Side {

    @NotNull
    private Side.Horizontal horizontal;
    @NotNull
    private Side.Vertical vertical;
    public static final Side.Companion Companion = new Side.Companion((DefaultConstructorMarker) null);

    @NotNull
    public final Side.Horizontal getHorizontal() {
        return this.horizontal;
    }

    public final void setHorizontal(@NotNull Side.Horizontal <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.horizontal = <set-?>;
    }

    @NotNull
    public final Side.Vertical getVertical() {
        return this.vertical;
    }

    public final void setVertical(@NotNull Side.Vertical <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.vertical = <set-?>;
    }

    public Side(@NotNull Side.Horizontal horizontal, @NotNull Side.Vertical vertical) {
        Intrinsics.checkParameterIsNotNull(horizontal, "horizontal");
        Intrinsics.checkParameterIsNotNull(vertical, "vertical");
        super();
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "", "sideName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSideName", "()Ljava/lang/String;", "LEFT", "MIDDLE", "RIGHT", "Companion", "LiquidBounce"}
    )
    public static enum Horizontal {

        LEFT, MIDDLE, RIGHT;

        @NotNull
        private final String sideName;
        public static final Side.Horizontal.Companion Companion = new Side.Horizontal.Companion((DefaultConstructorMarker) null);

        @NotNull
        public final String getSideName() {
            return this.sideName;
        }

        private Horizontal(String sideName) {
            this.sideName = sideName;
        }

        @JvmStatic
        @Nullable
        public static final Side.Horizontal getByName(@NotNull String name) {
            return Side.Horizontal.Companion.getByName(name);
        }

        @Metadata(
            mv = { 1, 1, 16},
            bv = { 1, 0, 3},
            k = 1,
            d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
            d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal$Companion;", "", "()V", "getByName", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "name", "", "LiquidBounce"}
        )
        public static final class Companion {

            @JvmStatic
            @Nullable
            public final Side.Horizontal getByName(@NotNull String name) {
                Intrinsics.checkParameterIsNotNull(name, "name");
                Side.Horizontal[] aside_horizontal = Side.Horizontal.values();
                boolean flag = false;
                boolean flag1 = false;
                Side.Horizontal[] aside_horizontal1 = aside_horizontal;
                int i = aside_horizontal.length;
                int j = 0;

                Side.Horizontal side_horizontal;

                while (true) {
                    if (j >= i) {
                        side_horizontal = null;
                        break;
                    }

                    Side.Horizontal side_horizontal1 = aside_horizontal1[j];
                    boolean $i$a$-find-Side$Horizontal$Companion$getByName$1 = false;

                    if (Intrinsics.areEqual(side_horizontal1.getSideName(), name)) {
                        side_horizontal = side_horizontal1;
                        break;
                    }

                    ++j;
                }

                return side_horizontal;
            }

            private Companion() {}

            public Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "", "sideName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSideName", "()Ljava/lang/String;", "UP", "MIDDLE", "DOWN", "Companion", "LiquidBounce"}
    )
    public static enum Vertical {

        UP, MIDDLE, DOWN;

        @NotNull
        private final String sideName;
        public static final Side.Vertical.Companion Companion = new Side.Vertical.Companion((DefaultConstructorMarker) null);

        @NotNull
        public final String getSideName() {
            return this.sideName;
        }

        private Vertical(String sideName) {
            this.sideName = sideName;
        }

        @JvmStatic
        @Nullable
        public static final Side.Vertical getByName(@NotNull String name) {
            return Side.Vertical.Companion.getByName(name);
        }

        @Metadata(
            mv = { 1, 1, 16},
            bv = { 1, 0, 3},
            k = 1,
            d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
            d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical$Companion;", "", "()V", "getByName", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "name", "", "LiquidBounce"}
        )
        public static final class Companion {

            @JvmStatic
            @Nullable
            public final Side.Vertical getByName(@NotNull String name) {
                Intrinsics.checkParameterIsNotNull(name, "name");
                Side.Vertical[] aside_vertical = Side.Vertical.values();
                boolean flag = false;
                boolean flag1 = false;
                Side.Vertical[] aside_vertical1 = aside_vertical;
                int i = aside_vertical.length;
                int j = 0;

                Side.Vertical side_vertical;

                while (true) {
                    if (j >= i) {
                        side_vertical = null;
                        break;
                    }

                    Side.Vertical side_vertical1 = aside_vertical1[j];
                    boolean $i$a$-find-Side$Vertical$Companion$getByName$1 = false;

                    if (Intrinsics.areEqual(side_vertical1.getSideName(), name)) {
                        side_vertical = side_vertical1;
                        break;
                    }

                    ++j;
                }

                return side_vertical;
            }

            private Companion() {}

            public Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Companion;", "", "()V", "default", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final Side default() {
            return new Side(Side.Horizontal.LEFT, Side.Vertical.UP);
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
