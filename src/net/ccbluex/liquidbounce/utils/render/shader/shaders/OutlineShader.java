package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import org.lwjgl.opengl.GL20;

public final class OutlineShader extends FramebufferShader {

    public static final OutlineShader OUTLINE_SHADER = new OutlineShader();

    public OutlineShader() {
        super("outline.frag");
    }

    public void setupUniforms() {
        this.setupUniform("texture");
        this.setupUniform("texelSize");
        this.setupUniform("color");
        this.setupUniform("divider");
        this.setupUniform("radius");
        this.setupUniform("maxSample");
    }

    public void updateUniforms() {
        GL20.glUniform1i(this.getUniform("texture"), 0);
        GL20.glUniform2f(this.getUniform("texelSize"), 1.0F / (float) OutlineShader.mc.getDisplayWidth() * this.radius * this.quality, 1.0F / (float) OutlineShader.mc.getDisplayHeight() * this.radius * this.quality);
        GL20.glUniform4f(this.getUniform("color"), this.red, this.green, this.blue, this.alpha);
        GL20.glUniform1f(this.getUniform("radius"), this.radius);
    }
}
