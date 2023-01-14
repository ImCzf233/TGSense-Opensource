package me.Skid.Tenacity.utils.render;

import me.Skid.Tenacity.utils.animations.Animation;
import me.Skid.Tenacity.utils.animations.Direction;
import me.Skid.Tenacity.utils.animations.impl.SmoothStepAnimation;
import org.lwjgl.input.Mouse;

public class Scroll {

    private float maxScroll = Float.MAX_VALUE;
    private float minScroll = 0.0F;
    private float rawScroll;
    private float scroll;
    private Animation scrollAnimation;

    public Scroll() {
        this.scrollAnimation = new SmoothStepAnimation(0, 0.0D, Direction.BACKWARDS);
    }

    public void setMaxScroll(float maxScroll) {
        this.maxScroll = maxScroll;
    }

    public float getMaxScroll() {
        return this.maxScroll;
    }

    public void onScroll(int ms) {
        this.scroll = (float) ((double) this.rawScroll - this.scrollAnimation.getOutput());
        this.rawScroll += (float) Mouse.getDWheel() / 4.0F;
        this.rawScroll = Math.max(Math.min(this.minScroll, this.rawScroll), -this.maxScroll);
        this.scrollAnimation = new SmoothStepAnimation(ms, (double) (this.rawScroll - this.scroll), Direction.BACKWARDS);
    }

    public boolean isScrollAnimationDone() {
        return this.scrollAnimation.isDone();
    }

    public float getScroll() {
        this.scroll = (float) ((double) this.rawScroll - this.scrollAnimation.getOutput());
        return this.scroll;
    }
}
