package net.ccbluex.liquidbounce.utils.render;

import kotlin.Metadata;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 3
)
public final class Animation$WhenMappings {

    public static final int[] $EnumSwitchMapping$0 = new int[Animation.EnumAnimationState.values().length];

    static {
        Animation$WhenMappings.$EnumSwitchMapping$0[Animation.EnumAnimationState.NOT_STARTED.ordinal()] = 1;
        Animation$WhenMappings.$EnumSwitchMapping$0[Animation.EnumAnimationState.DURING.ordinal()] = 2;
        Animation$WhenMappings.$EnumSwitchMapping$0[Animation.EnumAnimationState.STOPPED.ordinal()] = 3;
    }
}
