package me.Skid.Jello.trans;

import net.ccbluex.liquidbounce.ui.client.clickgui.AnimationUtil;

public final class Translate {

    private float x;
    private float y;
    private float alpha;
    private boolean first = false;

    public Translate(float v, float v1) {
        this.x = this.x;
        this.y = this.y;
    }

    public final void SetAlpha(float alpha, double smoothing) {
        if (this.first) {
            this.alpha = AnimationUtil.animate(alpha, this.alpha, smoothing);
        } else {
            this.alpha = alpha;
            this.first = true;
        }

    }

    public final void interpolate(float targetX, float targetY, double smoothing) {
        if (this.first) {
            this.x = AnimationUtil.animate(targetX, this.x, smoothing);
            this.y = AnimationUtil.animate(targetY, this.y, smoothing);
        } else {
            this.x = targetX;
            this.y = targetY;
            this.first = true;
        }

    }

    public final void interpolate(float targetX, double smoothing) {
        if (this.first) {
            this.x = AnimationUtil.animate(targetX, this.x, smoothing);
        } else {
            this.x = targetX;
            this.first = true;
        }

    }

    public final void interpolate3(float targetX, float targetY, double smoothing) {
        this.x = AnimationUtil.animate(targetX, this.x, smoothing);
        this.y = AnimationUtil.animate(targetY, this.y, smoothing);
    }

    public final void interpolate2(float targetX, float targetY, double smoothing) {
        this.x = targetX;
        this.y = AnimationUtil.animate(targetY, this.y, smoothing);
    }

    public float getAlpha() {
        return this.alpha;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
