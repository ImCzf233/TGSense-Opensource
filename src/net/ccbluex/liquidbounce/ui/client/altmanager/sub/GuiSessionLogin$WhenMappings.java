package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 3
)
public final class GuiSessionLogin$WhenMappings {

    public static final int[] $EnumSwitchMapping$0 = new int[LoginUtils.LoginResult.values().length];

    static {
        GuiSessionLogin$WhenMappings.$EnumSwitchMapping$0[LoginUtils.LoginResult.LOGGED.ordinal()] = 1;
        GuiSessionLogin$WhenMappings.$EnumSwitchMapping$0[LoginUtils.LoginResult.FAILED_PARSE_TOKEN.ordinal()] = 2;
        GuiSessionLogin$WhenMappings.$EnumSwitchMapping$0[LoginUtils.LoginResult.INVALID_ACCOUNT_DATA.ordinal()] = 3;
    }
}
