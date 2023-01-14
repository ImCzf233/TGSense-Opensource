package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.Shader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL20;

public final class BackgroundShader2 extends Shader {

    public static final BackgroundShader2 BACKGROUND_SHADER = new BackgroundShader2();
    private float time;

    public BackgroundShader2() {
        super("background2.frag");
    }

    public void setupUniforms() {
        this.setupUniform("iResolution");
        this.setupUniform("iTime");
    }

    public void updateUniforms() {
        int resolutionID = this.getUniform("iResolution");

        if (resolutionID > -1) {
            GL20.glUniform2f(resolutionID, (float) Display.getWidth(), (float) Display.getHeight());
        }

        int timeID = this.getUniform("iTime");

        if (timeID > -1) {
            GL20.glUniform1f(timeID, this.time);
        }

        this.time += 0.003F * (float) RenderUtils.deltaTime;
    }
}
