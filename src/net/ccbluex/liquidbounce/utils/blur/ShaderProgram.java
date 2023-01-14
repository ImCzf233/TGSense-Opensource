package net.ccbluex.liquidbounce.utils.blur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {

    protected static final Minecraft mc = Minecraft.getMinecraft();
    private final String vertexName;
    private final String fragmentName;
    private final int vertexShaderID;
    private final int fragmentShaderID;
    private final int programID;
    private boolean initiated;

    public ShaderProgram(String vertexName, String fragmentName) {
        this.vertexName = vertexName;
        this.fragmentName = fragmentName;
        int program = GL20.glCreateProgram();
        String vertexSource = readShader(vertexName);

        this.vertexShaderID = GL20.glCreateShader('è¬?');
        GL20.glShaderSource(this.vertexShaderID, vertexSource);
        GL20.glCompileShader(this.vertexShaderID);
        if (GL20.glGetShaderi(this.vertexShaderID, 'è®?') == 0) {
            System.err.println(GL20.glGetShaderInfoLog(this.vertexShaderID, 4096));
            throw new IllegalStateException(String.format("Vertex Shader (%s) failed to compile!", new Object[] { Integer.valueOf('è¬?')}));
        } else {
            String fragmentSource = readShader(fragmentName);

            this.fragmentShaderID = GL20.glCreateShader('è¬?');
            GL20.glShaderSource(this.fragmentShaderID, fragmentSource);
            GL20.glCompileShader(this.fragmentShaderID);
            if (GL20.glGetShaderi(this.fragmentShaderID, 'è®?') == 0) {
                System.err.println(GL20.glGetShaderInfoLog(this.fragmentShaderID, 4096));
                throw new IllegalStateException(String.format("Fragment Shader (%s) failed to compile!", new Object[] { Integer.valueOf('è¬?')}));
            } else {
                GL20.glAttachShader(program, this.vertexShaderID);
                GL20.glAttachShader(program, this.fragmentShaderID);
                GL20.glLinkProgram(program);
                this.programID = program;
            }
        }
    }

    public ShaderProgram(String fragmentName) {
        this("vertex/vertex.vert", fragmentName);
    }

    private static String readShader(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStreamReader e = new InputStreamReader(ShaderProgram.class.getClassLoader().getResourceAsStream(String.format("assets/minecraft/novo/shaders/%s", new Object[] { fileName})));
            BufferedReader bufferedReader = new BufferedReader(e);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }

            bufferedReader.close();
            e.close();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public void renderCanvas(ScaledResolution scaledResolution) {
        float width = (float) scaledResolution.getScaledWidth();
        float height = (float) scaledResolution.getScaledHeight();

        this.renderCanvas(0.0F, 0.0F, width, height);
    }

    public void renderCanvas(float x, float y, float width, float height) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex2f(x, height);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex2f(width, y);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3008);
    }

    public void deleteShaderProgram() {
        GL20.glDeleteShader(this.vertexShaderID);
        GL20.glDeleteShader(this.fragmentShaderID);
        GL20.glDeleteProgram(this.programID);
    }

    public void init() {
        GL20.glUseProgram(this.programID);
    }

    public void uninit() {
        GL20.glUseProgram(0);
    }

    public int getUniform(String name) {
        return GL20.glGetUniformLocation(this.programID, name);
    }

    public void setUniformf(String name, float... args) {
        int loc = GL20.glGetUniformLocation(this.programID, name);

        if (args.length > 1) {
            if (args.length > 2) {
                if (args.length > 3) {
                    GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
                } else {
                    GL20.glUniform3f(loc, args[0], args[1], args[2]);
                }
            } else {
                GL20.glUniform2f(loc, args[0], args[1]);
            }
        } else {
            GL20.glUniform1f(loc, args[0]);
        }

    }

    public void setUniformi(String name, int... args) {
        int loc = GL20.glGetUniformLocation(this.programID, name);

        if (args.length > 1) {
            GL20.glUniform2i(loc, args[0], args[1]);
        } else {
            GL20.glUniform1i(loc, args[0]);
        }

    }

    public int getProgramID() {
        return this.programID;
    }

    public String toString() {
        return "ShaderProgram{programID=" + this.programID + ", vertexName=\'" + this.vertexName + '\'' + ", fragmentName=\'" + this.fragmentName + '\'' + '}';
    }
}
