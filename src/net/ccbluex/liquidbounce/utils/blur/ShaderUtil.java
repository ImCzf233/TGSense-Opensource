package net.ccbluex.liquidbounce.utils.blur;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.utils.render.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderUtil {

    private final int programID;

    public ShaderUtil(String fragmentShaderLoc, String vertexShaderLoc) {
        int program = GL20.glCreateProgram();

        int status;

        try {
            String roundedRectGradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n}";
            String roundedRect = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\nuniform bool blur;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    // Smooth the result (free antialiasing).\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n\n}";
            byte b0 = -1;

            switch (fragmentShaderLoc.hashCode()) {
            case -493757311:
                if (fragmentShaderLoc.equals("roundedRectGradient")) {
                    b0 = 1;
                }
                break;

            case -72859087:
                if (fragmentShaderLoc.equals("roundedRect")) {
                    b0 = 0;
                }
            }

            switch (b0) {
            case 0:
                status = this.createShader(new ByteArrayInputStream(roundedRect.getBytes()), 'è¬?');
                break;

            case 1:
                status = this.createShader(new ByteArrayInputStream(roundedRectGradient.getBytes()), 'è¬?');
                break;

            default:
                status = this.createShader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(fragmentShaderLoc)).getInputStream(), 'è¬?');
            }

            GL20.glAttachShader(program, status);
            int vertexShaderID = this.createShader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(vertexShaderLoc)).getInputStream(), 'è¬?');

            GL20.glAttachShader(program, vertexShaderID);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

        GL20.glLinkProgram(program);
        status = GL20.glGetProgrami(program, 'è®?');
        if (status == 0) {
            throw new IllegalStateException("Shader failed to link!");
        } else {
            this.programID = program;
        }
    }

    public ShaderUtil(String fragmentShaderLoc) {
        this(fragmentShaderLoc, "shaders/vertex.vsh");
    }

    public static void drawQuads() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float width = (float) sr.getScaledWidth_double();
        float height = (float) sr.getScaledHeight_double();

        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex2f(0.0F, 0.0F);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex2f(0.0F, height);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex2f(width, 0.0F);
        GL11.glEnd();
    }

    public void init() {
        GL20.glUseProgram(this.programID);
    }

    public void unload() {
        GL20.glUseProgram(0);
    }

    public static void drawQuads(float x, float y, float width, float height) {
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();
    }

    public int getUniform(String name) {
        return GL20.glGetUniformLocation(this.programID, name);
    }

    public void setUniformf(String name, float... args) {
        int loc = GL20.glGetUniformLocation(this.programID, name);

        switch (args.length) {
        case 1:
            GL20.glUniform1f(loc, args[0]);
            break;

        case 2:
            GL20.glUniform2f(loc, args[0], args[1]);
            break;

        case 3:
            GL20.glUniform3f(loc, args[0], args[1], args[2]);
            break;

        case 4:
            GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
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

    private int createShader(InputStream inputStream, int shaderType) {
        int shader = GL20.glCreateShader(shaderType);

        GL20.glShaderSource(shader, FileUtils.readInputStream(inputStream));
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, 'è®?') == 0) {
            System.out.println(GL20.glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", new Object[] { Integer.valueOf(shaderType)}));
        } else {
            return shader;
        }
    }
}
