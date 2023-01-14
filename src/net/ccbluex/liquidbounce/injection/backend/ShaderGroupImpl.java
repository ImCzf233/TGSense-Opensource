package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.shader.IShaderGroup;
import net.minecraft.client.shader.ShaderGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ShaderGroupImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IShaderGroup;", "wrapped", "Lnet/minecraft/client/shader/ShaderGroup;", "(Lnet/minecraft/client/shader/ShaderGroup;)V", "shaderGroupName", "", "getShaderGroupName", "()Ljava/lang/String;", "getWrapped", "()Lnet/minecraft/client/shader/ShaderGroup;", "equals", "", "other", "", "LiquidBounce"}
)
public final class ShaderGroupImpl implements IShaderGroup {

    @NotNull
    private final ShaderGroup wrapped;

    @NotNull
    public String getShaderGroupName() {
        String s = this.wrapped.getShaderGroupName();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.shaderGroupName");
        return s;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ShaderGroupImpl && Intrinsics.areEqual(((ShaderGroupImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final ShaderGroup getWrapped() {
        return this.wrapped;
    }

    public ShaderGroupImpl(@NotNull ShaderGroup wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
