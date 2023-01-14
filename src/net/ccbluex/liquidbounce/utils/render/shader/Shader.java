package net.ccbluex.liquidbounce.utils.render.shader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader extends MinecraftInstance {

    private int program;
    private Map uniformsMap;

    public Shader(String fragmentShader) {
        int vertexShaderID;
        int fragmentShaderID;

        try {
            InputStream e = this.getClass().getResourceAsStream("/assets/minecraft/langya/shader/vertex.vert");

            vertexShaderID = this.createShader(IOUtils.toString(e), 'è¬?');
            IOUtils.closeQuietly(e);
            InputStream fragmentStream = this.getClass().getResourceAsStream("/assets/minecraft/langya/shader/fragment/" + fragmentShader);

            fragmentShaderID = this.createShader(IOUtils.toString(fragmentStream), 'è¬?');
            IOUtils.closeQuietly(fragmentStream);
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

        if (vertexShaderID != 0 && fragmentShaderID != 0) {
            this.program = ARBShaderObjects.glCreateProgramObjectARB();
            if (this.program != 0) {
                ARBShaderObjects.glAttachObjectARB(this.program, vertexShaderID);
                ARBShaderObjects.glAttachObjectARB(this.program, fragmentShaderID);
                ARBShaderObjects.glLinkProgramARB(this.program);
                ARBShaderObjects.glValidateProgramARB(this.program);
                ClientUtils.getLogger().info("[Shader] Successfully loaded: " + fragmentShader);
            }
        }
    }

    public void startShader() {
        GL11.glPushMatrix();
        GL20.glUseProgram(this.program);
        if (this.uniformsMap == null) {
            this.uniformsMap = new HashMap();
            this.setupUniforms();
        }

        this.updateUniforms();
    }

    public void stopShader() {
        GL20.glUseProgram(0);
        GL11.glPopMatrix();
    }

    public abstract void setupUniforms();

    public abstract void updateUniforms();

    private int createShader(String shaderSource, int shaderType) {
        byte shader = 0;

        try {
            int shader1 = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if (shader1 == 0) {
                return 0;
            } else {
                ARBShaderObjects.glShaderSourceARB(shader1, shaderSource);
                ARBShaderObjects.glCompileShaderARB(shader1);
                if (ARBShaderObjects.glGetObjectParameteriARB(shader1, 'è®?') == 0) {
                    throw new RuntimeException("Error creating shader: " + this.getLogInfo(shader1));
                } else {
                    return shader1;
                }
            }
        } catch (Exception exception) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw exception;
        }
    }

    private String getLogInfo(int i) {
        return ARBShaderObjects.glGetInfoLogARB(i, ARBShaderObjects.glGetObjectParameteriARB(i, 'è®?'));
    }

    public void setUniform(String uniformName, int location) {
        this.uniformsMap.put(uniformName, Integer.valueOf(location));
    }

    public void setupUniform(String uniformName) {
        this.setUniform(uniformName, GL20.glGetUniformLocation(this.program, uniformName));
    }

    public int getUniform(String uniformName) {
        return ((Integer) this.uniformsMap.get(uniformName)).intValue();
    }

    public int getProgramId() {
        return this.program;
    }
}
