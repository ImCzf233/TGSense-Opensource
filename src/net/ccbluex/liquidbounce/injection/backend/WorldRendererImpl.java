package net.ccbluex.liquidbounce.injection.backend;

import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.render.vertex.IVertexFormat;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nH\u0016J(\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u0010H\u0016J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0096\u0002J\b\u0010\u001e\u001a\u00020\u0010H\u0016J \u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020!H\u0016J\b\u0010$\u001a\u00020\u0010H\u0016J\u0018\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020!2\u0006\u0010\'\u001a\u00020!H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006("},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/WorldRendererImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IWorldRenderer;", "wrapped", "Lnet/minecraft/client/renderer/BufferBuilder;", "(Lnet/minecraft/client/renderer/BufferBuilder;)V", "byteBuffer", "Ljava/nio/ByteBuffer;", "getByteBuffer", "()Ljava/nio/ByteBuffer;", "vertexFormat", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/vertex/IVertexFormat;", "getVertexFormat", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/vertex/IVertexFormat;", "getWrapped", "()Lnet/minecraft/client/renderer/BufferBuilder;", "begin", "", "mode", "", "color", "red", "", "green", "blue", "alpha", "endVertex", "equals", "", "other", "", "finishDrawing", "pos", "x", "", "y", "z", "reset", "tex", "u", "v", "LiquidBounce"}
)
public final class WorldRendererImpl implements IWorldRenderer {

    @NotNull
    private final BufferBuilder wrapped;

    @NotNull
    public ByteBuffer getByteBuffer() {
        ByteBuffer bytebuffer = this.wrapped.getByteBuffer();

        Intrinsics.checkExpressionValueIsNotNull(bytebuffer, "wrapped.byteBuffer");
        return bytebuffer;
    }

    @NotNull
    public IVertexFormat getVertexFormat() {
        VertexFormat vertexformat = this.wrapped.getVertexFormat();

        Intrinsics.checkExpressionValueIsNotNull(vertexformat, "wrapped.vertexFormat");
        VertexFormat $this$wrap$iv = vertexformat;
        boolean $i$f$wrap = false;

        return (IVertexFormat) (new VertexFormatImpl($this$wrap$iv));
    }

    public void begin(int mode, @NotNull IVertexFormat vertexFormat) {
        Intrinsics.checkParameterIsNotNull(vertexFormat, "vertexFormat");
        this.wrapped.begin(mode, ((VertexFormatImpl) vertexFormat).getWrapped());
    }

    @NotNull
    public IWorldRenderer pos(double x, double y, double z) {
        this.wrapped.pos(x, y, z);
        return (IWorldRenderer) this;
    }

    public void endVertex() {
        this.wrapped.endVertex();
    }

    @NotNull
    public IWorldRenderer tex(double u, double v) {
        this.wrapped.tex(u, v);
        return (IWorldRenderer) this;
    }

    @NotNull
    public IWorldRenderer color(float red, float green, float blue, float alpha) {
        this.wrapped.color(red, green, blue, alpha);
        return (IWorldRenderer) this;
    }

    public void finishDrawing() {
        this.wrapped.finishDrawing();
    }

    public void reset() {
        this.wrapped.reset();
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof WorldRendererImpl && Intrinsics.areEqual(((WorldRendererImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final BufferBuilder getWrapped() {
        return this.wrapped;
    }

    public WorldRendererImpl(@NotNull BufferBuilder wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
