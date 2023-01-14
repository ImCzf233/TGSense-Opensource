package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/NotifyType;", "", "renderColor", "Ljava/awt/Color;", "(Ljava/lang/String;ILjava/awt/Color;)V", "getRenderColor", "()Ljava/awt/Color;", "setRenderColor", "(Ljava/awt/Color;)V", "SUCCESS", "ERROR", "WARNING", "INFO", "LiquidBounce"}
)
public enum NotifyType {

    SUCCESS, ERROR, WARNING, INFO;

    @NotNull
    private Color renderColor;

    @NotNull
    public final Color getRenderColor() {
        return this.renderColor;
    }

    public final void setRenderColor(@NotNull Color <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.renderColor = <set-?>;
    }

    private NotifyType(Color renderColor) {
        this.renderColor = renderColor;
    }
}
