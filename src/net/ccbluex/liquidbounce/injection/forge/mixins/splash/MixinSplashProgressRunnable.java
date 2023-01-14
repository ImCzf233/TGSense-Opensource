package net.ccbluex.liquidbounce.injection.forge.mixins.splash;

import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import net.ccbluex.liquidbounce.utils.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.AnimatedValue;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraftforge.fml.client.SplashProgress;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
    targets = { "net.minecraftforge.fml.client.SplashProgress$2"},
    remap = false
)
public abstract class MixinSplashProgressRunnable {

    @Shadow(
        remap = false
    )
    protected abstract void setGL();

    @Shadow(
        remap = false
    )
    protected abstract void clearGL();

    @Inject(
        method = { "run()V"},
        at = {             @At("HEAD")},
        remap = false,
        cancellable = true
    )
    private void run(CallbackInfo callbackInfo) {
        callbackInfo.cancel();
        this.setGL();
        GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553);

        int tex;

        try {
            tex = RenderUtils.R2DUtils.loadGlTexture(ImageIO.read(this.getClass().getResourceAsStream("/assets/minecraft/langya/splash.png")));
        } catch (IOException ioexception) {
            tex = 0;
        }

        GL11.glDisable(3553);
        AnimatedValue animatedValue = new AnimatedValue();

        animatedValue.setType(EaseUtils.EnumEasingType.CIRC);
        animatedValue.setDuration(600L);

        for (; !SplashProgress.done; Display.sync(60)) {
            GL11.glClear(16384);
            int width = Display.getWidth();
            int height = Display.getHeight();

            GL11.glViewport(0, 0, width, height);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, (double) width, (double) height, 0.0D, -1.0D, 1.0D);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3553);
            GL11.glBindTexture(3553, tex);
            GL11.glBegin(7);
            GL11.glTexCoord2f(0.0F, 0.0F);
            GL11.glVertex2f(0.0F, 0.0F);
            GL11.glTexCoord2f(1.0F, 0.0F);
            GL11.glVertex2f((float) width, 0.0F);
            GL11.glTexCoord2f(1.0F, 1.0F);
            GL11.glVertex2f((float) width, (float) height);
            GL11.glTexCoord2f(0.0F, 1.0F);
            GL11.glVertex2f(0.0F, (float) height);
            GL11.glEnd();
            GL11.glDisable(3553);
            float rectX = (float) width * 0.2F;
            float rectX2 = (float) width * 0.8F;
            float rectY = (float) height * 0.85F;
            float rectY2 = (float) height * 0.8F;
            float rectRadius = (float) height * 0.015F;
            float progress = (float) animatedValue.sync((double) getProgress());

            if (progress != 1.0F) {
                GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.3F);
                RenderUtils.drawRoundedCornerRect(rectX, rectY, rectX2, rectY2, rectRadius);
            }

            if (progress != 0.0F) {
                GL11.glColor4f(12.0F, 11.0F, 11.0F, 60.0F);
                RenderUtils.drawRoundedCornerRect(rectX, rectY, rectX + (float) width * 0.6F * progress, rectY2, rectRadius);
            }

            SplashProgress.mutex.acquireUninterruptibly();
            Display.update();
            SplashProgress.mutex.release();
            if (SplashProgress.pause) {
                this.clearGL();
                this.setGL();
            }
        }

        GL11.glDeleteTextures(tex);
        this.clearGL();
    }

    private static float getProgress() {
        float progress = 0.0F;
        Iterator it = ProgressManager.barIterator();

        if (it.hasNext()) {
            ProgressBar bar = (ProgressBar) it.next();

            progress = (float) bar.getStep() / (float) bar.getSteps();
        }

        return progress;
    }
}
