package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.Jello.trans.Translate;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0015\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0005\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001bJ\b\u0010\u001f\u001a\u00020\bH\u0016J\u0010\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"H&J\u000b\u0010#\u001a\u00028\u0000¢\u0006\u0002\u0010\u0019J\u001d\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00028\u00002\u0006\u0010&\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\'J\u001d\u0010(\u001a\u00020\u001e2\u0006\u0010%\u001a\u00028\u00002\u0006\u0010&\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\'J\u0013\u0010)\u001a\u00020\u001e2\u0006\u0010&\u001a\u00028\u0000¢\u0006\u0002\u0010\u001bJ\n\u0010*\u001a\u0004\u0018\u00010\"H&R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0005\u001a\u00028\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u001c\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006+"},
    d2 = { "Lnet/ccbluex/liquidbounce/value/Value;", "T", "", "name", "", "value", "canDisplay", "Lkotlin/Function0;", "", "(Ljava/lang/String;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)V", "ValueTranslate", "Lme/Skid/Jello/trans/Translate;", "getValueTranslate", "()Lme/Skid/Jello/trans/Translate;", "getCanDisplay", "()Lkotlin/jvm/functions/Function0;", "setCanDisplay", "(Lkotlin/jvm/functions/Function0;)V", "isSupported", "()Z", "setSupported", "(Z)V", "getName", "()Ljava/lang/String;", "getValue", "()Ljava/lang/Object;", "setValue", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "changeValue", "", "display", "fromJson", "element", "Lcom/google/gson/JsonElement;", "get", "onChange", "oldValue", "newValue", "(Ljava/lang/Object;Ljava/lang/Object;)V", "onChanged", "set", "toJson", "LiquidBounce"}
)
public abstract class Value {

    private boolean isSupported;
    @NotNull
    private final Translate ValueTranslate;
    @NotNull
    private final String name;
    private Object value;
    @NotNull
    private Function0 canDisplay;

    public boolean display() {
        return true;
    }

    public final boolean isSupported() {
        return this.isSupported;
    }

    public final void setSupported(boolean <set-?>) {
        this.isSupported = <set-?>;
    }

    @NotNull
    public final Translate getValueTranslate() {
        return this.ValueTranslate;
    }

    public final void set(Object newValue) {
        if (!Intrinsics.areEqual(newValue, this.value)) {
            Object oldValue = this.get();

            try {
                this.onChange(oldValue, newValue);
                this.changeValue(newValue);
                this.onChanged(oldValue, newValue);
                LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
            } catch (Exception exception) {
                ClientUtils.getLogger().error("[ValueSystem (" + this.name + ")]: " + exception.getClass().getName() + " (" + exception.getMessage() + ") [" + oldValue + " >> " + newValue + ']');
            }

        }
    }

    public final Object get() {
        return this.value;
    }

    public void changeValue(Object value) {
        this.value = value;
    }

    @Nullable
    public abstract JsonElement toJson();

    public abstract void fromJson(@NotNull JsonElement jsonelement);

    protected void onChange(Object oldValue, Object newValue) {}

    protected void onChanged(Object oldValue, Object newValue) {}

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final Object getValue() {
        return this.value;
    }

    public final void setValue(Object <set-?>) {
        this.value = <set-?>;
    }

    @NotNull
    public final Function0 getCanDisplay() {
        return this.canDisplay;
    }

    public final void setCanDisplay(@NotNull Function0 <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.canDisplay = <set-?>;
    }

    public Value(@NotNull String name, Object value, @NotNull Function0 canDisplay) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(canDisplay, "canDisplay");
        super();
        this.name = name;
        this.value = value;
        this.canDisplay = canDisplay;
        this.isSupported = true;
        this.ValueTranslate = new Translate(0.0F, 0.0F);
    }
}
