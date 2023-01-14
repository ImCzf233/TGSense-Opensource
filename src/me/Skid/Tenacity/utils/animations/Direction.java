package me.Skid.Tenacity.utils.animations;

public enum Direction {

    FORWARDS, BACKWARDS;

    public Direction opposite() {
        return this == Direction.FORWARDS ? Direction.BACKWARDS : Direction.FORWARDS;
    }
}
