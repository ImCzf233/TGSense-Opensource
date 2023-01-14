package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import java.io.Closeable;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import net.ccbluex.liquidbounce.utils.render.shader.Shader;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL20;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J)\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tH\u0087\bJ\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0019H\u0016J\b\u0010\u001b\u001a\u00020\u0019H\u0016J\b\u0010\u001c\u001a\u00020\u0019H\u0016J\b\u0010\u001d\u001a\u00020\u0019H\u0016R\u001e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\rR\u001a\u0010\u0011\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\r¨\u0006\u001e"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowFontShader;", "Lnet/ccbluex/liquidbounce/utils/render/shader/Shader;", "Ljava/io/Closeable;", "()V", "<set-?>", "", "isInUse", "()Z", "offset", "", "getOffset", "()F", "setOffset", "(F)V", "strengthX", "getStrengthX", "setStrengthX", "strengthY", "getStrengthY", "setStrengthY", "begin", "enable", "x", "y", "close", "", "setupUniforms", "startShader", "stopShader", "updateUniforms", "LiquidBounce"}
)
public final class RainbowFontShader extends Shader implements Closeable {

    private static boolean isInUse;
    private static float strengthX;
    private static float strengthY;
    private static float offset;
    public static final RainbowFontShader INSTANCE;

    public final boolean isInUse() {
        return RainbowFontShader.isInUse;
    }

    public final float getStrengthX() {
        return RainbowFontShader.strengthX;
    }

    public final void setStrengthX(float <set-?>) {
        RainbowFontShader.strengthX = <set-?>;
    }

    public final float getStrengthY() {
        return RainbowFontShader.strengthY;
    }

    public final void setStrengthY(float <set-?>) {
        RainbowFontShader.strengthY = <set-?>;
    }

    public final float getOffset() {
        return RainbowFontShader.offset;
    }

    public final void setOffset(float <set-?>) {
        RainbowFontShader.offset = <set-?>;
    }

    public void setupUniforms() {
        this.setupUniform("offset");
        this.setupUniform("strength");
    }

    public void updateUniforms() {
        GL20.glUniform2f(this.getUniform("strength"), RainbowFontShader.strengthX, RainbowFontShader.strengthY);
        GL20.glUniform1f(this.getUniform("offset"), RainbowFontShader.offset);
    }

    public void startShader() {
        super.startShader();
        RainbowFontShader.isInUse = true;
    }

    public void stopShader() {
        super.stopShader();
        RainbowFontShader.isInUse = false;
    }

    public void close() {
        if (RainbowFontShader.isInUse) {
            this.stopShader();
        }

    }

    @JvmStatic
    @NotNull
    public static final RainbowFontShader begin(boolean enable, float x, float y, float offset) {
        byte $i$f$begin = 0;

        if (enable) {
            RainbowFontShader.INSTANCE.setStrengthX(x);
            RainbowFontShader.INSTANCE.setStrengthY(y);
            RainbowFontShader.INSTANCE.setOffset(offset);
            RainbowFontShader.INSTANCE.startShader();
        }

        return RainbowFontShader.INSTANCE;
    }

    private RainbowFontShader() {
        super("rainbow_font_shader.frag");
    }

    static {
        RainbowFontShader rainbowfontshader = new RainbowFontShader();

        INSTANCE = rainbowfontshader;
    }
}
