package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Cape",
    description = "Capes",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/Cape;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "styleValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getStyleValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "getCapeLocation", "Lnet/minecraft/util/ResourceLocation;", "value", "CapeStyle", "LiquidBounce"}
)
public final class Cape extends Module {

    @NotNull
    private final ListValue styleValue = new ListValue("Style", new String[] { "Astolfo", "Astolfo2", "Envy", "ETB", "Flux", "Hypixel", "Liquidbounce", "Lunar", "original", "PowerX", "Sunny", "Tenacity"}, "Tenacity");

    @NotNull
    public final ListValue getStyleValue() {
        return this.styleValue;
    }

    @NotNull
    public final ResourceLocation getCapeLocation(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");

        ResourceLocation resourcelocation;

        try {
            boolean e = false;
            String s = value.toUpperCase();

            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toUpperCase()");
            resourcelocation = Cape.CapeStyle.valueOf(s).getLocation();
        } catch (IllegalArgumentException illegalargumentexception) {
            resourcelocation = Cape.CapeStyle.TENACITY.getLocation();
        }

        return resourcelocation;
    }

    @NotNull
    public String getTag() {
        return (String) this.styleValue.get();
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012¨\u0006\u0013"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/Cape$CapeStyle;", "", "location", "Lnet/minecraft/util/ResourceLocation;", "(Ljava/lang/String;ILnet/minecraft/util/ResourceLocation;)V", "getLocation", "()Lnet/minecraft/util/ResourceLocation;", "ASTOLFO", "ASTOLFO2", "ENVY", "ETB", "FLUX", "HYPIXEL", "LIQUIDBOUNCE", "LUNAR", "ORIGINAL", "POWERX", "SUNNY", "TENACITY", "LiquidBounce"}
    )
    public static enum CapeStyle {

        ASTOLFO, ASTOLFO2, ENVY, ETB, FLUX, HYPIXEL, LIQUIDBOUNCE, LUNAR, ORIGINAL, POWERX, SUNNY, TENACITY;

        @NotNull
        private final ResourceLocation location;

        @NotNull
        public final ResourceLocation getLocation() {
            return this.location;
        }

        private CapeStyle(ResourceLocation location) {
            this.location = location;
        }
    }
}
