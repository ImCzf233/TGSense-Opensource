package net.ccbluex.liquidbounce.utils.blur;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderGroup {

    public final List listShaders = Lists.newArrayList();
    private final Map mapFramebuffers = Maps.newHashMap();
    private final List listFramebuffers = Lists.newArrayList();
    public final Framebuffer mainFramebuffer;
    private final IResourceManager resourceManager;
    private final String shaderGroupName;
    private Matrix4f projectionMatrix;
    private int mainFramebufferWidth;
    private int mainFramebufferHeight;
    private float time;
    private float lastStamp;

    public ShaderGroup(TextureManager p_i1050_1_, IResourceManager resourceManagerIn, Framebuffer mainFramebufferIn, ResourceLocation p_i1050_4_) throws IOException, JsonSyntaxException {
        this.resourceManager = resourceManagerIn;
        this.mainFramebuffer = mainFramebufferIn;
        this.time = 0.0F;
        this.lastStamp = 0.0F;
        this.mainFramebufferWidth = mainFramebufferIn.framebufferWidth;
        this.mainFramebufferHeight = mainFramebufferIn.framebufferHeight;
        this.shaderGroupName = p_i1050_4_.toString();
        this.resetProjectionMatrix();
        this.parseGroup(p_i1050_1_, p_i1050_4_);
    }

    public void parseGroup(TextureManager p_152765_1_, ResourceLocation p_152765_2_) throws IOException, JsonSyntaxException {
        JsonParser jsonparser = new JsonParser();
        InputStream inputstream = null;

        try {
            IResource exception2 = this.resourceManager.getResource(p_152765_2_);

            inputstream = exception2.getInputStream();
            JsonObject jsonobject = jsonparser.parse(IOUtils.toString(inputstream, Charsets.UTF_8)).getAsJsonObject();
            JsonArray jsonarray1;
            int j;
            Iterator iterator;
            JsonElement jsonelement1;
            JsonException jsonexception2;

            if (JsonUtils.isJsonArray(jsonobject, "targets")) {
                jsonarray1 = jsonobject.getAsJsonArray("targets");
                j = 0;

                for (iterator = jsonarray1.iterator(); iterator.hasNext(); ++j) {
                    jsonelement1 = (JsonElement) iterator.next();

                    try {
                        this.initTarget(jsonelement1);
                    } catch (Exception exception) {
                        jsonexception2 = JsonException.forException(exception);
                        jsonexception2.prependJsonKey("targets[" + j + "]");
                        throw jsonexception2;
                    }
                }
            }

            if (JsonUtils.isJsonArray(jsonobject, "passes")) {
                jsonarray1 = jsonobject.getAsJsonArray("passes");
                j = 0;

                for (iterator = jsonarray1.iterator(); iterator.hasNext(); ++j) {
                    jsonelement1 = (JsonElement) iterator.next();

                    try {
                        this.parsePass(p_152765_1_, jsonelement1);
                    } catch (Exception exception1) {
                        jsonexception2 = JsonException.forException(exception1);
                        jsonexception2.prependJsonKey("passes[" + j + "]");
                        throw jsonexception2;
                    }
                }
            }
        } catch (Exception exception2) {
            JsonException jsonexception = JsonException.forException(exception2);

            jsonexception.setFilenameAndFlush(p_152765_2_.getPath());
            throw jsonexception;
        } finally {
            IOUtils.closeQuietly(inputstream);
        }

    }

    private void initTarget(JsonElement p_148027_1_) throws JsonException {
        if (JsonUtils.isString(p_148027_1_)) {
            this.addFramebuffer(p_148027_1_.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
        } else {
            JsonObject jsonobject = JsonUtils.getJsonObject(p_148027_1_, "target");
            String s = JsonUtils.getString(jsonobject, "name");
            int i = JsonUtils.getInt(jsonobject, "width", this.mainFramebufferWidth);
            int j = JsonUtils.getInt(jsonobject, "height", this.mainFramebufferHeight);

            if (this.mapFramebuffers.containsKey(s)) {
                throw new JsonException(s + " is already defined");
            }

            this.addFramebuffer(s, i, j);
        }

    }

    private void parsePass(TextureManager p_152764_1_, JsonElement json) throws IOException {
        JsonObject jsonobject = JsonUtils.getJsonObject(json, "pass");
        String s = JsonUtils.getString(jsonobject, "name");
        String s1 = JsonUtils.getString(jsonobject, "intarget");
        String s2 = JsonUtils.getString(jsonobject, "outtarget");
        Framebuffer framebuffer = this.getFramebuffer(s1);
        Framebuffer framebuffer1 = this.getFramebuffer(s2);

        if (framebuffer == null) {
            throw new JsonException("Input target \'" + s1 + "\' does not exist");
        } else if (framebuffer1 == null) {
            throw new JsonException("Output target \'" + s2 + "\' does not exist");
        } else {
            Shader shader = this.addShader(s, framebuffer, framebuffer1);
            JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "auxtargets", (JsonArray) null);

            if (jsonarray != null) {
                int jsonarray1 = 0;

                for (Iterator l = jsonarray.iterator(); l.hasNext(); ++jsonarray1) {
                    JsonElement jsonelement = (JsonElement) l.next();

                    try {
                        JsonObject jsonelement1 = JsonUtils.getJsonObject(jsonelement, "auxtarget");
                        String s = JsonUtils.getString(jsonelement1, "name");
                        String jsonexception1 = JsonUtils.getString(jsonelement1, "id");
                        Framebuffer framebuffer2 = this.getFramebuffer(jsonexception1);

                        if (framebuffer2 == null) {
                            ResourceLocation resourcelocation = new ResourceLocation("textures/effect/" + jsonexception1 + "Scare.png");

                            try {
                                this.resourceManager.getResource(resourcelocation);
                            } catch (FileNotFoundException filenotfoundexception) {
                                throw new JsonException("Render target or texture \'" + jsonexception1 + "\' does not exist");
                            }

                            p_152764_1_.bindTexture(resourcelocation);
                            ITextureObject itextureobject = p_152764_1_.getTexture(resourcelocation);
                            int j = JsonUtils.getInt(jsonelement1, "width");
                            int k = JsonUtils.getInt(jsonelement1, "height");
                            boolean flag = JsonUtils.getBoolean(jsonelement1, "bilinear");

                            if (flag) {
                                GL11.glTexParameteri(3553, 10241, 9729);
                                GL11.glTexParameteri(3553, 10240, 9729);
                            } else {
                                GL11.glTexParameteri(3553, 10241, 9728);
                                GL11.glTexParameteri(3553, 10240, 9728);
                            }

                            shader.addAuxFramebuffer(s, Integer.valueOf(itextureobject.getGlTextureId()), j, k);
                        } else {
                            shader.addAuxFramebuffer(s, framebuffer2, framebuffer2.framebufferTextureWidth, framebuffer2.framebufferTextureHeight);
                        }
                    } catch (Exception exception) {
                        JsonException exception = JsonException.forException(exception);

                        exception.setFilenameAndFlush("auxtargets[" + jsonarray1 + "]");
                        throw exception;
                    }
                }
            }

            JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "uniforms", (JsonArray) null);

            if (jsonarray != null) {
                int i = 0;

                for (Iterator iterator = jsonarray.iterator(); iterator.hasNext(); ++i) {
                    JsonElement jsonelement = (JsonElement) iterator.next();

                    try {
                        this.initUniform(jsonelement);
                    } catch (Exception exception1) {
                        JsonException jsonexception = JsonException.forException(exception1);

                        jsonexception.prependJsonKey("uniforms[" + i + "]");
                        throw jsonexception;
                    }
                }
            }

        }
    }

    private void initUniform(JsonElement json) throws JsonException {
        JsonObject jsonobject = JsonUtils.getJsonObject(json, "uniform");
        String s = JsonUtils.getString(jsonobject, "name");
        ShaderUniform shaderuniform = ((Shader) this.listShaders.get(this.listShaders.size() - 1)).getShaderManager().getShaderUniform(s);

        if (shaderuniform == null) {
            throw new JsonException("Uniform \'" + s + "\' does not exist");
        } else {
            float[] afloat = new float[4];
            int i = 0;

            for (Iterator iterator = JsonUtils.getJsonArray(jsonobject, "values").iterator(); iterator.hasNext(); ++i) {
                JsonElement jsonelement = (JsonElement) iterator.next();

                try {
                    afloat[i] = JsonUtils.getFloat(jsonelement, "value");
                } catch (Exception exception) {
                    JsonException jsonexception = JsonException.forException(exception);

                    jsonexception.prependJsonKey("values[" + i + "]");
                    throw jsonexception;
                }
            }

            switch (i) {
            case 0:
            default:
                break;

            case 1:
                shaderuniform.set(afloat[0]);
                break;

            case 2:
                shaderuniform.set(afloat[0], afloat[1]);
                break;

            case 3:
                shaderuniform.set(afloat[0], afloat[1], afloat[2]);
                break;

            case 4:
                shaderuniform.set(afloat[0], afloat[1], afloat[2], afloat[3]);
            }

        }
    }

    public Framebuffer getFramebufferRaw(String attributeName) {
        return (Framebuffer) this.mapFramebuffers.get(attributeName);
    }

    public void addFramebuffer(String name, int width, int height) {
        Framebuffer framebuffer = new Framebuffer(width, height, true);

        framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
        this.mapFramebuffers.put(name, framebuffer);
        if (width == this.mainFramebufferWidth && height == this.mainFramebufferHeight) {
            this.listFramebuffers.add(framebuffer);
        }

    }

    public void deleteShaderGroup() {
        Iterator iterator = this.mapFramebuffers.values().iterator();

        while (iterator.hasNext()) {
            Framebuffer shader = (Framebuffer) iterator.next();

            shader.deleteFramebuffer();
        }

        iterator = this.listShaders.iterator();

        while (iterator.hasNext()) {
            Shader shader1 = (Shader) iterator.next();

            shader1.deleteShader();
        }

        this.listShaders.clear();
    }

    public Shader addShader(String programName, Framebuffer framebufferIn, Framebuffer framebufferOut) throws IOException {
        Shader shader = new Shader(this.resourceManager, programName, framebufferIn, framebufferOut);

        this.listShaders.add(this.listShaders.size(), shader);
        return shader;
    }

    private void resetProjectionMatrix() {
        this.projectionMatrix = new Matrix4f();
        this.projectionMatrix.setIdentity();
        this.projectionMatrix.m00 = 2.0F / (float) this.mainFramebuffer.framebufferTextureWidth;
        this.projectionMatrix.m11 = 2.0F / (float) (-this.mainFramebuffer.framebufferTextureHeight);
        this.projectionMatrix.m22 = -0.0020001999F;
        this.projectionMatrix.m33 = 1.0F;
        this.projectionMatrix.m03 = -1.0F;
        this.projectionMatrix.m13 = 1.0F;
        this.projectionMatrix.m23 = -1.0001999F;
    }

    public void createBindFramebuffers(int width, int height) {
        this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
        this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
        this.resetProjectionMatrix();
        Iterator iterator = this.listShaders.iterator();

        while (iterator.hasNext()) {
            Shader framebuffer = (Shader) iterator.next();

            framebuffer.setProjectionMatrix(this.projectionMatrix);
        }

        iterator = this.listFramebuffers.iterator();

        while (iterator.hasNext()) {
            Framebuffer framebuffer1 = (Framebuffer) iterator.next();

            framebuffer1.createBindFramebuffer(width, height);
        }

    }

    public void loadShaderGroup(float partialTicks) {
        if (partialTicks < this.lastStamp) {
            this.time += 1.0F - this.lastStamp;
            this.time += partialTicks;
        } else {
            this.time += partialTicks - this.lastStamp;
        }

        for (this.lastStamp = partialTicks; this.time > 20.0F; this.time -= 20.0F) {
            ;
        }

        Iterator iterator = this.listShaders.iterator();

        while (iterator.hasNext()) {
            Shader shader = (Shader) iterator.next();

            shader.render(this.time / 20.0F);
        }

    }

    public final String getShaderGroupName() {
        return this.shaderGroupName;
    }

    private Framebuffer getFramebuffer(String p_148017_1_) {
        return p_148017_1_ == null ? null : (p_148017_1_.equals("minecraft:main") ? this.mainFramebuffer : (Framebuffer) this.mapFramebuffers.get(p_148017_1_));
    }

    public List getShaders() {
        return this.listShaders;
    }
}
