package net.ccbluex.liquidbounce.injection.backend.utils;

import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class SafeVertexBuffer extends VertexBuffer {

    public SafeVertexBuffer(VertexFormat vertexFormatIn) {
        super(vertexFormatIn);
    }

    protected void finalize() throws Throwable {
        this.deleteGlBuffers();
    }
}
