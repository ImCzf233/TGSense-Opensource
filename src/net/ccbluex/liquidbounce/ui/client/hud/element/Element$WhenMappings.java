package net.ccbluex.liquidbounce.ui.client.hud.element;

import kotlin.Metadata;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 3
)
public final class Element$WhenMappings {

    public static final int[] $EnumSwitchMapping$0 = new int[Side.Horizontal.values().length];
    public static final int[] $EnumSwitchMapping$1;
    public static final int[] $EnumSwitchMapping$2;
    public static final int[] $EnumSwitchMapping$3;

    static {
        Element$WhenMappings.$EnumSwitchMapping$0[Side.Horizontal.LEFT.ordinal()] = 1;
        Element$WhenMappings.$EnumSwitchMapping$0[Side.Horizontal.MIDDLE.ordinal()] = 2;
        Element$WhenMappings.$EnumSwitchMapping$0[Side.Horizontal.RIGHT.ordinal()] = 3;
        $EnumSwitchMapping$1 = new int[Side.Horizontal.values().length];
        Element$WhenMappings.$EnumSwitchMapping$1[Side.Horizontal.LEFT.ordinal()] = 1;
        Element$WhenMappings.$EnumSwitchMapping$1[Side.Horizontal.MIDDLE.ordinal()] = 2;
        Element$WhenMappings.$EnumSwitchMapping$1[Side.Horizontal.RIGHT.ordinal()] = 3;
        $EnumSwitchMapping$2 = new int[Side.Vertical.values().length];
        Element$WhenMappings.$EnumSwitchMapping$2[Side.Vertical.UP.ordinal()] = 1;
        Element$WhenMappings.$EnumSwitchMapping$2[Side.Vertical.MIDDLE.ordinal()] = 2;
        Element$WhenMappings.$EnumSwitchMapping$2[Side.Vertical.DOWN.ordinal()] = 3;
        $EnumSwitchMapping$3 = new int[Side.Vertical.values().length];
        Element$WhenMappings.$EnumSwitchMapping$3[Side.Vertical.UP.ordinal()] = 1;
        Element$WhenMappings.$EnumSwitchMapping$3[Side.Vertical.MIDDLE.ordinal()] = 2;
        Element$WhenMappings.$EnumSwitchMapping$3[Side.Vertical.DOWN.ordinal()] = 3;
    }
}
