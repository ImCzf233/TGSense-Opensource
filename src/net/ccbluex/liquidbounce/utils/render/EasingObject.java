package net.ccbluex.liquidbounce.utils.render;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000e\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u0013"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/render/EasingObject;", "", "lastTime", "", "lastValue", "", "currentValue", "(JFF)V", "getCurrentValue", "()F", "setCurrentValue", "(F)V", "getLastTime", "()J", "setLastTime", "(J)V", "getLastValue", "setLastValue", "update", "LiquidBounce"}
)
public final class EasingObject {

    private long lastTime;
    private float lastValue;
    private float currentValue;

    public final float update(float currentValue) {
        if (currentValue != this.currentValue) {
            if (currentValue < this.currentValue) {
                this.lastValue = currentValue;
            } else {
                this.lastValue = this.currentValue;
            }

            this.currentValue = currentValue;
            this.lastTime = System.currentTimeMillis();
        }

        return AnimationUtils.easeOutElastic(RangesKt.coerceIn((float) (System.currentTimeMillis() - this.lastTime) / 500.0F, 0.0F, 1.0F)) * (currentValue - this.lastValue) + this.lastValue;
    }

    public final long getLastTime() {
        return this.lastTime;
    }

    public final void setLastTime(long <set-?>) {
        this.lastTime = <set-?>;
    }

    public final float getLastValue() {
        return this.lastValue;
    }

    public final void setLastValue(float <set-?>) {
        this.lastValue = <set-?>;
    }

    public final float getCurrentValue() {
        return this.currentValue;
    }

    public final void setCurrentValue(float <set-?>) {
        this.currentValue = <set-?>;
    }

    public EasingObject(long lastTime, float lastValue, float currentValue) {
        this.lastTime = lastTime;
        this.lastValue = lastValue;
        this.currentValue = currentValue;
    }

    public EasingObject(long i, float f, float f1, int j, DefaultConstructorMarker defaultconstructormarker) {
        if ((j & 1) != 0) {
            i = 0L;
        }

        if ((j & 2) != 0) {
            f = -1.0F;
        }

        if ((j & 4) != 0) {
            f1 = -1.0F;
        }

        this(i, f, f1);
    }

    public EasingObject() {
        this(0L, 0.0F, 0.0F, 7, (DefaultConstructorMarker) null);
    }
}
