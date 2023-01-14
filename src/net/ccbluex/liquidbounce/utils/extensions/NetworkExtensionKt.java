package net.ccbluex.liquidbounce.utils.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 2,
    d1 = { "\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"},
    d2 = { "getFullName", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/INetworkPlayerInfo;", "LiquidBounce"}
)
public final class NetworkExtensionKt {

    @NotNull
    public static final String getFullName(@NotNull INetworkPlayerInfo $this$getFullName) {
        Intrinsics.checkParameterIsNotNull($this$getFullName, "$this$getFullName");
        if ($this$getFullName.getDisplayName() != null) {
            IIChatComponent iichatcomponent = $this$getFullName.getDisplayName();

            if (iichatcomponent == null) {
                Intrinsics.throwNpe();
            }

            return iichatcomponent.getFormattedText();
        } else {
            ITeam team = $this$getFullName.getPlayerTeam();
            String name = $this$getFullName.getGameProfile().getName();
            String s;

            if (team != null) {
                Intrinsics.checkExpressionValueIsNotNull(name, "name");
                s = team.formatString(name);
                if (s != null) {
                    return s;
                }
            }

            s = name;
            Intrinsics.checkExpressionValueIsNotNull(name, "name");
            return s;
        }
    }
}
