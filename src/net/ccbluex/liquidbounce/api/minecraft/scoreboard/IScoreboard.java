package net.ccbluex.liquidbounce.api.minecraft.scoreboard;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH&J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u0003H&¨\u0006\u000e"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScoreboard;", "", "getObjectiveInDisplaySlot", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScoreObjective;", "index", "", "getPlayersTeam", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "name", "", "getSortedScores", "", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScore;", "objective", "LiquidBounce"}
)
public interface IScoreboard {

    @Nullable
    ITeam getPlayersTeam(@Nullable String s);

    @Nullable
    IScoreObjective getObjectiveInDisplaySlot(int i);

    @NotNull
    Collection getSortedScores(@NotNull IScoreObjective iscoreobjective);
}
