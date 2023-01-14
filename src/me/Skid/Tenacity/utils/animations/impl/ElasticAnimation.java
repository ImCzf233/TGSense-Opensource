package me.Skid.Tenacity.utils.animations.impl;

import me.Skid.Tenacity.utils.animations.Animation;
import me.Skid.Tenacity.utils.animations.Direction;

public class ElasticAnimation extends Animation {

    float easeAmount;
    float smooth;
    boolean reallyElastic;

    public ElasticAnimation(int ms, double endPoint, float elasticity, float smooth, boolean moreElasticity) {
        super(ms, endPoint);
        this.easeAmount = elasticity;
        this.smooth = smooth;
        this.reallyElastic = moreElasticity;
    }

    public ElasticAnimation(int ms, double endPoint, float elasticity, float smooth, boolean moreElasticity, Direction direction) {
        super(ms, endPoint, direction);
        this.easeAmount = elasticity;
        this.smooth = smooth;
        this.reallyElastic = moreElasticity;
    }

    protected double getEquation(double x) {
        double x1 = Math.pow(x / (double) this.duration, (double) this.smooth);
        double elasticity = (double) (this.easeAmount * 0.1F);

        return Math.pow(2.0D, -10.0D * (this.reallyElastic ? Math.sqrt(x1) : x1)) * Math.sin((x1 - elasticity / 4.0D) * (6.283185307179586D / elasticity)) + 1.0D;
    }
}
