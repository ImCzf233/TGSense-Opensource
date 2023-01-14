package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/TessellatorImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "wrapped", "Lnet/minecraft/client/renderer/Tessellator;", "(Lnet/minecraft/client/renderer/Tessellator;)V", "worldRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IWorldRenderer;", "getWorldRenderer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IWorldRenderer;", "getWrapped", "()Lnet/minecraft/client/renderer/Tessellator;", "draw", "", "equals", "", "other", "", "LiquidBounce"}
)
public final class TessellatorImpl implements ITessellator {

    @NotNull
    private final Tessellator wrapped;

    @NotNull
    public IWorldRenderer getWorldRenderer() {
        BufferBuilder bufferbuilder = this.wrapped.getBuffer();

        Intrinsics.checkExpressionValueIsNotNull(bufferbuilder, "wrapped.buffer");
        return (IWorldRenderer) (new WorldRendererImpl(bufferbuilder));
    }

    public void draw() {
        this.wrapped.draw();
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof TessellatorImpl && Intrinsics.areEqual(((TessellatorImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Tessellator getWrapped() {
        return this.wrapped;
    }

    public TessellatorImpl(@NotNull Tessellator wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
