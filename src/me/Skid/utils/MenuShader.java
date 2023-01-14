package me.Skid.utils;

import net.ccbluex.liquidbounce.utils.blur.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class MenuShader {

    protected static final Minecraft mc = Minecraft.getMinecraft();
    private ShaderProgram menuShader = new ShaderProgram("fragment/menu.frag");
    private int pass;

    public MenuShader(int pass) {
        this.pass = pass;
    }

    public void render(ScaledResolution scaledResolution) {
        this.menuShader.init();
        this.setupUniforms();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.menuShader.renderCanvas(scaledResolution);
        this.menuShader.uninit();
        ++this.pass;
    }

    public void setupUniforms() {
        this.menuShader.setUniformf("time", new float[] { (float) this.pass / 100.0F});
        this.menuShader.setUniformf("resolution", new float[] { (float) MenuShader.mc.displayWidth, (float) MenuShader.mc.displayHeight});
    }
}
