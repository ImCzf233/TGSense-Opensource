package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.awt.Color;
import java.util.Arrays;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001aB%\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0002¢\u0006\u0002\u0010\u0007B1\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u0002H\u0016J\u0013\u0010\u0012\u001a\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0002H\u0086\u0002J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0006\u0010\u0017\u001a\u00020\u0011J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0012\u0010\f\u001a\u00020\n8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000e¨\u0006\u001b"},
    d2 = { "Lnet/ccbluex/liquidbounce/value/ListValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "values", "", "value", "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)V", "displayable", "Lkotlin/Function0;", "", "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "openList", "getValues", "()[Ljava/lang/String;", "[Ljava/lang/String;", "changeValue", "", "contains", "string", "fromJson", "element", "Lcom/google/gson/JsonElement;", "listtoggle", "toJson", "Lcom/google/gson/JsonPrimitive;", "ColorValue", "LiquidBounce"}
)
public class ListValue extends Value {

    @JvmField
    public boolean openList;
    @NotNull
    private final String[] values;

    public final boolean contains(@Nullable final String string) {
        return Arrays.stream(this.values).anyMatch((Predicate) (new Predicate() {
            public final boolean test(@NotNull String s) {
                Intrinsics.checkParameterIsNotNull(s, "s");
                return StringsKt.equals(s, string, true);
            }
        }));
    }

    public void changeValue(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        String[] astring = this.values;
        int i = astring.length;

        for (int j = 0; j < i; ++j) {
            String element = astring[j];

            if (StringsKt.equals(element, value, true)) {
                this.setValue(element);
                break;
            }
        }

    }

    @NotNull
    public JsonPrimitive toJson() {
        return new JsonPrimitive((String) this.getValue());
    }

    public final void listtoggle() {
        this.openList = !this.openList;
    }

    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (element.isJsonPrimitive()) {
            String s = element.getAsString();

            Intrinsics.checkExpressionValueIsNotNull(s, "element.asString");
            this.changeValue(s);
        }

    }

    @NotNull
    public final String[] getValues() {
        return this.values;
    }

    public ListValue(@NotNull String name, @NotNull String[] values, @NotNull String value, @NotNull Function0 displayable) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(displayable, "displayable");
        super(name, value, displayable);
        this.values = values;
        this.setValue(value);
    }

    public ListValue(@NotNull String name, @NotNull String[] values, @NotNull String value) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this(name, values, value, (Function0) null.INSTANCE);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0004\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0002H\u0016J\b\u0010\u0016\u001a\u00020\bH\u0016J\u000e\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u001c"},
        d2 = { "Lnet/ccbluex/liquidbounce/value/ListValue$ColorValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "", "value", "(Ljava/lang/String;I)V", "Expanded", "", "getExpanded", "()Z", "setExpanded", "(Z)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "getAwtColor", "Ljava/awt/Color;", "getHSB", "", "getValue", "isExpanded", "set", "newValue", "", "toJson", "Lcom/google/gson/JsonPrimitive;", "LiquidBounce"}
    )
    public static class ColorValue extends Value {

        private boolean Expanded;

        public int getValue() {
            return ((Number) super.get()).intValue();
        }

        public final void set(@NotNull Number newValue) {
            Intrinsics.checkParameterIsNotNull(newValue, "newValue");
            this.set((Object) Integer.valueOf(newValue.intValue()));
        }

        public final boolean getExpanded() {
            return this.Expanded;
        }

        public final void setExpanded(boolean <set-?>) {
            this.Expanded = <set-?>;
        }

        public boolean isExpanded() {
            return this.Expanded;
        }

        @NotNull
        public JsonPrimitive toJson() {
            return new JsonPrimitive((Number) Integer.valueOf(this.getValue()));
        }

        public void fromJson(@NotNull JsonElement element) {
            Intrinsics.checkParameterIsNotNull(element, "element");
            if (element.isJsonPrimitive()) {
                this.setValue(Integer.valueOf(element.getAsInt()));
            }

        }

        @NotNull
        public float[] getHSB() {
            float[] hsbValues = new float[3];
            float saturation = 0.0F;
            float brightness = 0.0F;
            float hue = 0.0F;
            int cMax = Math.max(this.getValue() >>> 16 & 255, this.getValue() >>> 8 & 255);

            if ((this.getValue() & 255) > cMax) {
                cMax = this.getValue() & 255;
            }

            int cMin = Math.min(this.getValue() >>> 16 & 255, this.getValue() >>> 8 & 255);

            if ((this.getValue() & 255) < cMin) {
                cMin = this.getValue() & 255;
            }

            brightness = (float) cMax / 255.0F;
            saturation = cMax != 0 ? (float) (cMax - cMin) / (float) cMax : 0.0F;
            if (saturation == 0.0F) {
                hue = 0.0F;
            } else {
                float redC = (float) (cMax - (this.getValue() >>> 16 & 255)) / (float) (cMax - cMin);
                float greenC = (float) (cMax - (this.getValue() >>> 8 & 255)) / (float) (cMax - cMin);
                float blueC = (float) (cMax - (this.getValue() & 255)) / (float) (cMax - cMin);

                hue = ((this.getValue() >>> 16 & 255) == cMax ? blueC - greenC : ((this.getValue() >>> 8 & 255) == cMax ? 2.0F + redC - blueC : 4.0F + greenC - redC)) / 6.0F;
                if (hue < (float) 0) {
                    ++hue;
                }
            }

            hsbValues[0] = hue;
            hsbValues[1] = saturation;
            hsbValues[2] = brightness;
            return hsbValues;
        }

        @NotNull
        public Color getAwtColor() {
            return new Color(((Number) this.getValue()).intValue(), true);
        }

        public ColorValue(@NotNull String name, int value) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            super(name, Integer.valueOf(value), (Function0) null.INSTANCE);
        }
    }
}
