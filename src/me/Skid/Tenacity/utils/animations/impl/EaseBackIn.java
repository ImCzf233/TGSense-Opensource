package me.Skid.Tenacity.utils.animations.impl;

import me.Skid.Tenacity.utils.animations.Animation;
import me.Skid.Tenacity.utils.animations.Direction;

public class EaseBackIn extends Animation {

    private final float easeAmount;

    public EaseBackIn(int ms, double endPoint, float easeAmount) {
        super(ms, endPoint);
        this.easeAmount = easeAmount;
    }

    public EaseBackIn(int ms, double endPoint, float easeAmount, Direction direction) {
        super(ms, endPoint, direction);
        this.easeAmount = easeAmount;
    }

    protected boolean correctOutput() {
        return true;
    }

    protected double getEquation(double x) {
        double x1 = x / (double) this.duration;
        float shrink = this.easeAmount + 1.0F;

        return Math.max(0.0D, 1.0D + (double) shrink * Math.pow(x1 - 1.0D, 3.0D) + (double) this.easeAmount * Math.pow(x1 - 1.0D, 2.0D));
    }
}
