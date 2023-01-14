package me.Skid.Modules.Render;

import kotlin.Metadata;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 3
)
public final class JelloTabGui$WhenMappings {

    public static final int[] $EnumSwitchMapping$0 = new int[JelloTabGui.Action.values().length];

    static {
        JelloTabGui$WhenMappings.$EnumSwitchMapping$0[JelloTabGui.Action.UP.ordinal()] = 1;
        JelloTabGui$WhenMappings.$EnumSwitchMapping$0[JelloTabGui.Action.DOWN.ordinal()] = 2;
        JelloTabGui$WhenMappings.$EnumSwitchMapping$0[JelloTabGui.Action.LEFT.ordinal()] = 3;
        JelloTabGui$WhenMappings.$EnumSwitchMapping$0[JelloTabGui.Action.RIGHT.ordinal()] = 4;
        JelloTabGui$WhenMappings.$EnumSwitchMapping$0[JelloTabGui.Action.TOGGLE.ordinal()] = 5;
    }
}
