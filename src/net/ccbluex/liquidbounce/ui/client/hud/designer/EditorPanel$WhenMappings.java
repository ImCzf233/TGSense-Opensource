package net.ccbluex.liquidbounce.ui.client.hud.designer;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 3
)
public final class EditorPanel$WhenMappings {

    public static final int[] $EnumSwitchMapping$0 = new int[Side.Horizontal.values().length];
    public static final int[] $EnumSwitchMapping$1;

    static {
        EditorPanel$WhenMappings.$EnumSwitchMapping$0[Side.Horizontal.LEFT.ordinal()] = 1;
        EditorPanel$WhenMappings.$EnumSwitchMapping$0[Side.Horizontal.MIDDLE.ordinal()] = 2;
        EditorPanel$WhenMappings.$EnumSwitchMapping$0[Side.Horizontal.RIGHT.ordinal()] = 3;
        $EnumSwitchMapping$1 = new int[Side.Vertical.values().length];
        EditorPanel$WhenMappings.$EnumSwitchMapping$1[Side.Vertical.UP.ordinal()] = 1;
        EditorPanel$WhenMappings.$EnumSwitchMapping$1[Side.Vertical.MIDDLE.ordinal()] = 2;
        EditorPanel$WhenMappings.$EnumSwitchMapping$1[Side.Vertical.DOWN.ordinal()] = 3;
    }
}
