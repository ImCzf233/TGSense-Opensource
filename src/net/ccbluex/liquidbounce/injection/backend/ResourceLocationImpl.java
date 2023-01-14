package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ResourceLocationImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "wrapped", "Lnet/minecraft/util/ResourceLocation;", "(Lnet/minecraft/util/ResourceLocation;)V", "resourcePath", "", "getResourcePath", "()Ljava/lang/String;", "getWrapped", "()Lnet/minecraft/util/ResourceLocation;", "equals", "", "other", "", "LiquidBounce"}
)
public final class ResourceLocationImpl implements IResourceLocation {

    @NotNull
    private final ResourceLocation wrapped;

    @NotNull
    public String getResourcePath() {
        String s = this.wrapped.getPath();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.resourcePath");
        return s;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ResourceLocationImpl && Intrinsics.areEqual(((ResourceLocationImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final ResourceLocation getWrapped() {
        return this.wrapped;
    }

    public ResourceLocationImpl(@NotNull ResourceLocation wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
