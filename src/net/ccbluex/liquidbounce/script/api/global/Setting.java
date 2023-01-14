package net.ccbluex.liquidbounce.script.api.global;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/script/api/global/Setting;", "", "()V", "block", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "settingInfo", "Ljdk/nashorn/api/scripting/JSObject;", "boolean", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "float", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "integer", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "list", "Lnet/ccbluex/liquidbounce/value/ListValue;", "text", "Lnet/ccbluex/liquidbounce/value/TextValue;", "LiquidBounce"}
)
public final class Setting {

    public static final Setting INSTANCE;

    @JvmStatic
    @NotNull
    public static final BoolValue boolean(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object object = settingInfo.getMember("name");

        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        } else {
            String name = (String) object;

            object = settingInfo.getMember("default");
            if (object == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
            } else {
                boolean default = ((Boolean) object).booleanValue();

                return new BoolValue(name, default);
            }
        }
    }

    @JvmStatic
    @NotNull
    public static final IntegerValue integer(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object object = settingInfo.getMember("name");

        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        } else {
            String name = (String) object;

            object = settingInfo.getMember("default");
            if (object == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
            } else {
                int default = ((Number) object).intValue();

                object = settingInfo.getMember("min");
                if (object == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
                } else {
                    int min = ((Number) object).intValue();

                    object = settingInfo.getMember("max");
                    if (object == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
                    } else {
                        int max = ((Number) object).intValue();

                        return new IntegerValue(name, default, min, max);
                    }
                }
            }
        }
    }

    @JvmStatic
    @NotNull
    public static final FloatValue float(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object object = settingInfo.getMember("name");

        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        } else {
            String name = (String) object;

            object = settingInfo.getMember("default");
            if (object == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
            } else {
                float default = ((Number) object).floatValue();

                object = settingInfo.getMember("min");
                if (object == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
                } else {
                    float min = ((Number) object).floatValue();

                    object = settingInfo.getMember("max");
                    if (object == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
                    } else {
                        float max = ((Number) object).floatValue();

                        return new FloatValue(name, default, min, max);
                    }
                }
            }
        }
    }

    @JvmStatic
    @NotNull
    public static final TextValue text(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object object = settingInfo.getMember("name");

        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        } else {
            String name = (String) object;

            object = settingInfo.getMember("default");
            if (object == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
            } else {
                String default = (String) object;

                return new TextValue(name, default);
            }
        }
    }

    @JvmStatic
    @NotNull
    public static final BlockValue block(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object object = settingInfo.getMember("name");

        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        } else {
            String name = (String) object;

            object = settingInfo.getMember("default");
            if (object == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
            } else {
                int default = ((Number) object).intValue();

                return new BlockValue(name, default);
            }
        }
    }

    @JvmStatic
    @NotNull
    public static final ListValue list(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object object = settingInfo.getMember("name");

        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        } else {
            String name = (String) object;

            object = ScriptUtils.convert(settingInfo.getMember("values"), String[].class);
            if (object == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
            } else {
                String[] values = (String[]) object;

                object = settingInfo.getMember("default");
                if (object == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                } else {
                    String default = (String) object;

                    return new ListValue(name, values, default);
                }
            }
        }
    }

    static {
        Setting setting = new Setting();

        INSTANCE = setting;
    }
}
