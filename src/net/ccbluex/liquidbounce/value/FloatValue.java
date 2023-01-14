package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.Jello.trans.Translate;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B5\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bB/\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0004¢\u0006\u0002\u0010\rB\'\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002¢\u0006\u0002\u0010\u000eB?\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0004\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001fJ\b\u0010 \u001a\u00020!H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\""},
    d2 = { "Lnet/ccbluex/liquidbounce/value/FloatValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "", "value", "minimum", "maximum", "displayable", "Lkotlin/Function0;", "", "(Ljava/lang/String;FFFLkotlin/jvm/functions/Function0;)V", "suffix", "(Ljava/lang/String;FFFLjava/lang/String;)V", "(Ljava/lang/String;FFF)V", "(Ljava/lang/String;FFFLjava/lang/String;Lkotlin/jvm/functions/Function0;)V", "getMaximum", "()F", "getMinimum", "getSuffix", "()Ljava/lang/String;", "translate", "Lme/Skid/Jello/trans/Translate;", "getTranslate", "()Lme/Skid/Jello/trans/Translate;", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "set", "newValue", "", "toJson", "Lcom/google/gson/JsonPrimitive;", "LiquidBounce"}
)
public class FloatValue extends Value {

    @NotNull
    private final Translate translate;
    private final float minimum;
    private final float maximum;
    @NotNull
    private final String suffix;

    public final void set(@NotNull Number newValue) {
        Intrinsics.checkParameterIsNotNull(newValue, "newValue");
        this.set((Object) Float.valueOf(newValue.floatValue()));
    }

    @NotNull
    public final Translate getTranslate() {
        return this.translate;
    }

    @NotNull
    public JsonPrimitive toJson() {
        return new JsonPrimitive((Number) this.getValue());
    }

    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (element.isJsonPrimitive()) {
            this.setValue(Float.valueOf(element.getAsFloat()));
        }

    }

    public final float getMinimum() {
        return this.minimum;
    }

    public final float getMaximum() {
        return this.maximum;
    }

    @NotNull
    public final String getSuffix() {
        return this.suffix;
    }

    public FloatValue(@NotNull String name, float value, float minimum, float maximum, @NotNull String suffix, @NotNull Function0 displayable) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(suffix, "suffix");
        Intrinsics.checkParameterIsNotNull(displayable, "displayable");
        super(name, Float.valueOf(value), displayable);
        this.minimum = minimum;
        this.maximum = maximum;
        this.suffix = suffix;
        this.translate = new Translate(0.0F, 0.0F);
    }

    public FloatValue(String s, float f, float f1, float f2, String s1, Function0 function0, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 4) != 0) {
            f1 = 0.0F;
        }

        if ((i & 8) != 0) {
            f2 = FloatCompanionObject.INSTANCE.getMAX_VALUE();
        }

        this(s, f, f1, f2, s1, function0);
    }

    public FloatValue(@NotNull String name, float value, float minimum, float maximum, @NotNull Function0 displayable) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(displayable, "displayable");
        this(name, value, minimum, maximum, "", displayable);
    }

    public FloatValue(@NotNull String name, float value, float minimum, float maximum, @NotNull String suffix) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(suffix, "suffix");
        this(name, value, minimum, maximum, suffix, (Function0) null.INSTANCE);
    }

    public FloatValue(@NotNull String name, float value, float minimum, float maximum) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        this(name, value, minimum, maximum, (Function0) null.INSTANCE);
    }
}
