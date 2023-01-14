package net.ccbluex.liquidbounce.api.minecraft.scoreboard;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H&J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0000H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "", "chatFormat", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "getChatFormat", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "formatString", "", "name", "isSameTeam", "", "team", "LiquidBounce"}
)
public interface ITeam {

    @NotNull
    WEnumChatFormatting getChatFormat();

    @NotNull
    String formatString(@NotNull String s);

    boolean isSameTeam(@NotNull ITeam iteam);
}
