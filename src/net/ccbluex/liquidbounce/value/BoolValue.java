package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0006\u0010\u0010\u001a\u00020\u000b¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/value/BoolValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "", "value", "(Ljava/lang/String;Z)V", "displayable", "Lkotlin/Function0;", "(Ljava/lang/String;ZLkotlin/jvm/functions/Function0;)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", "toggle", "LiquidBounce"}
)
public class BoolValue extends Value {

    @NotNull
    public JsonPrimitive toJson() {
        return new JsonPrimitive((Boolean) this.getValue());
    }

    public final void toggle() {
        this.setValue(Boolean.valueOf(!((Boolean) this.getValue()).booleanValue()));
    }

    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (element.isJsonPrimitive()) {
            this.setValue(Boolean.valueOf(element.getAsBoolean() || StringsKt.equals(element.getAsString(), "true", true)));
        }

    }

    public BoolValue(@NotNull String name, boolean value, @NotNull Function0 displayable) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(displayable, "displayable");
        super(name, Boolean.valueOf(value), displayable);
    }

    public BoolValue(@NotNull String name, boolean value) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        this(name, value, (Function0) null.INSTANCE);
    }
}
