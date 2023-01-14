package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import kotlin.Metadata;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 3
)
public final class Notification$WhenMappings {

    public static final int[] $EnumSwitchMapping$0 = new int[FadeState.values().length];

    static {
        Notification$WhenMappings.$EnumSwitchMapping$0[FadeState.IN.ordinal()] = 1;
        Notification$WhenMappings.$EnumSwitchMapping$0[FadeState.STAY.ordinal()] = 2;
        Notification$WhenMappings.$EnumSwitchMapping$0[FadeState.OUT.ordinal()] = 3;
        Notification$WhenMappings.$EnumSwitchMapping$0[FadeState.END.ordinal()] = 4;
    }
}
