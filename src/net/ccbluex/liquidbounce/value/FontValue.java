package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\n\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0016¨\u0006\u0010"},
    d2 = { "Lnet/ccbluex/liquidbounce/value/FontValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "valueName", "", "value", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;)V", "displayable", "Lkotlin/Function0;", "", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;Lkotlin/jvm/functions/Function0;)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "LiquidBounce"}
)
public final class FontValue extends Value {

    @Nullable
    public JsonElement toJson() {
        Fonts.FontInfo fonts_fontinfo = Fonts.getFontDetails((IFontRenderer) this.getValue());

        if (fonts_fontinfo != null) {
            Fonts.FontInfo fontDetails = fonts_fontinfo;
            JsonObject valueObject = new JsonObject();

            valueObject.addProperty("fontName", fontDetails.getName());
            valueObject.addProperty("fontSize", (Number) Integer.valueOf(fontDetails.getFontSize()));
            return (JsonElement) valueObject;
        } else {
            return null;
        }
    }

    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (element.isJsonObject()) {
            JsonObject valueObject = element.getAsJsonObject();
            JsonElement jsonelement = valueObject.get("fontName");

            Intrinsics.checkExpressionValueIsNotNull(jsonelement, "valueObject[\"fontName\"]");
            String s = jsonelement.getAsString();
            JsonElement jsonelement1 = valueObject.get("fontSize");

            Intrinsics.checkExpressionValueIsNotNull(jsonelement1, "valueObject[\"fontSize\"]");
            IFontRenderer ifontrenderer = Fonts.getFontRenderer(s, jsonelement1.getAsInt());

            Intrinsics.checkExpressionValueIsNotNull(ifontrenderer, "Fonts.getFontRenderer(va…Object[\"fontSize\"].asInt)");
            this.setValue(ifontrenderer);
        }
    }

    public FontValue(@NotNull String valueName, @NotNull IFontRenderer value, @NotNull Function0 displayable) {
        Intrinsics.checkParameterIsNotNull(valueName, "valueName");
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(displayable, "displayable");
        super(valueName, value, displayable);
    }

    public FontValue(@NotNull String valueName, @NotNull IFontRenderer value) {
        Intrinsics.checkParameterIsNotNull(valueName, "valueName");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this(valueName, value, (Function0) null.INSTANCE);
    }
}
