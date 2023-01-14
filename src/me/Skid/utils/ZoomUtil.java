package me.Skid.utils;

import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;

public class ZoomUtil {

    protected static final Minecraft mc = Minecraft.getMinecraft();
    private final float originalX;
    private final float originalY;
    private final float originalWidth;
    private final float originalHeight;
    private final float speed;
    private final float zoomFactor;
    private final long nextUpdateTime;
    private final me.Skid.utils.time.TimeUtils timer = new me.Skid.utils.time.TimeUtils();
    private float x;
    private float y;
    private float width;
    private float height;

    public ZoomUtil(float x, float y, float width, float height, long nextUpdateTime, float speed, float zoomFactor) {
        this.originalX = x;
        this.originalY = y;
        this.originalWidth = width;
        this.originalHeight = height;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.zoomFactor = zoomFactor;
        this.nextUpdateTime = nextUpdateTime;
    }

    public void update(int mouseX, int mouseY) {
        if (RenderUtils.isHovered(this.x, this.y, this.width, this.height, mouseX, mouseY)) {
            if (this.timer.hasElapsed(this.nextUpdateTime)) {
                this.x = (float) (RenderUtils.animate((double) (this.originalX - this.zoomFactor / 2.0F), (double) this.x, (double) this.speed) - 0.10000000149011612D);
                this.y = (float) (RenderUtils.animate((double) (this.originalY - this.zoomFactor / 2.0F), (double) this.y, (double) this.speed) - 0.10000000149011612D);
                this.width = (float) (RenderUtils.animate((double) (this.originalWidth + this.zoomFactor), (double) this.width, (double) this.speed) - 0.10000000149011612D);
                this.height = (float) (RenderUtils.animate((double) (this.originalHeight + this.zoomFactor), (double) this.height, (double) this.speed) - 0.10000000149011612D);
                this.timer.reset();
            }
        } else if (this.timer.hasElapsed(this.nextUpdateTime)) {
            this.x = (float) (RenderUtils.animate((double) this.originalX, (double) this.x, (double) this.speed) - 0.10000000149011612D);
            this.y = (float) (RenderUtils.animate((double) this.originalY, (double) this.y, (double) this.speed) - 0.10000000149011612D);
            this.width = (float) (RenderUtils.animate((double) this.originalWidth, (double) this.width, (double) this.speed) - 0.10000000149011612D);
            this.height = (float) (RenderUtils.animate((double) this.originalHeight, (double) this.height, (double) this.speed) - 0.10000000149011612D);
            this.timer.reset();
        }

    }

    public void setPosition(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getOriginalX() {
        return this.originalX;
    }

    public float getOriginalY() {
        return this.originalY;
    }

    public float getOriginalWidth() {
        return this.originalWidth;
    }

    public float getOriginalHeight() {
        return this.originalHeight;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getZoomFactor() {
        return this.zoomFactor;
    }

    public long getNextUpdateTime() {
        return this.nextUpdateTime;
    }

    public me.Skid.utils.time.TimeUtils getTimer() {
        return this.timer;
    }
}
