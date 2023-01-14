package net.ccbluex.liquidbounce.utils.render;

import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

public class CustomTexture {

    private final BufferedImage image;
    private boolean unloaded;
    private int textureId = -1;

    public CustomTexture(BufferedImage image) {
        this.image = image;
    }

    public int getTextureId() {
        if (this.unloaded) {
            throw new IllegalStateException("Texture unloaded");
        } else {
            if (this.textureId == -1) {
                this.textureId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), this.image, true, true);
            }

            return this.textureId;
        }
    }

    public void unload() {
        if (!this.unloaded) {
            GL11.glDeleteTextures(this.textureId);
            this.unloaded = true;
        }

    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.unload();
    }
}
