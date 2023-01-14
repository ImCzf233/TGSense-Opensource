package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt$WhenMappings;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0001H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/TeamImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "wrapped", "Lnet/minecraft/scoreboard/Team;", "(Lnet/minecraft/scoreboard/Team;)V", "chatFormat", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "getChatFormat", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "getWrapped", "()Lnet/minecraft/scoreboard/Team;", "equals", "", "other", "", "formatString", "", "name", "isSameTeam", "team", "LiquidBounce"}
)
public final class TeamImpl implements ITeam {

    @NotNull
    private final Team wrapped;

    @NotNull
    public WEnumChatFormatting getChatFormat() {
        Team team = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.scoreboard.ScorePlayerTeam");
        } else {
            TextFormatting textformatting = ((ScorePlayerTeam) team).getColor();

            Intrinsics.checkExpressionValueIsNotNull(textformatting, "(wrapped as ScorePlayerTeam).color");
            TextFormatting $this$wrap$iv = textformatting;
            boolean $i$f$wrap = false;
            WEnumChatFormatting wenumchatformatting;

            switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$3[$this$wrap$iv.ordinal()]) {
            case 1:
                wenumchatformatting = WEnumChatFormatting.BLACK;
                break;

            case 2:
                wenumchatformatting = WEnumChatFormatting.DARK_BLUE;
                break;

            case 3:
                wenumchatformatting = WEnumChatFormatting.DARK_GREEN;
                break;

            case 4:
                wenumchatformatting = WEnumChatFormatting.DARK_AQUA;
                break;

            case 5:
                wenumchatformatting = WEnumChatFormatting.DARK_RED;
                break;

            case 6:
                wenumchatformatting = WEnumChatFormatting.DARK_PURPLE;
                break;

            case 7:
                wenumchatformatting = WEnumChatFormatting.GOLD;
                break;

            case 8:
                wenumchatformatting = WEnumChatFormatting.GRAY;
                break;

            case 9:
                wenumchatformatting = WEnumChatFormatting.DARK_GRAY;
                break;

            case 10:
                wenumchatformatting = WEnumChatFormatting.BLUE;
                break;

            case 11:
                wenumchatformatting = WEnumChatFormatting.GREEN;
                break;

            case 12:
                wenumchatformatting = WEnumChatFormatting.AQUA;
                break;

            case 13:
                wenumchatformatting = WEnumChatFormatting.RED;
                break;

            case 14:
                wenumchatformatting = WEnumChatFormatting.LIGHT_PURPLE;
                break;

            case 15:
                wenumchatformatting = WEnumChatFormatting.YELLOW;
                break;

            case 16:
                wenumchatformatting = WEnumChatFormatting.WHITE;
                break;

            case 17:
                wenumchatformatting = WEnumChatFormatting.OBFUSCATED;
                break;

            case 18:
                wenumchatformatting = WEnumChatFormatting.BOLD;
                break;

            case 19:
                wenumchatformatting = WEnumChatFormatting.STRIKETHROUGH;
                break;

            case 20:
                wenumchatformatting = WEnumChatFormatting.UNDERLINE;
                break;

            case 21:
                wenumchatformatting = WEnumChatFormatting.ITALIC;
                break;

            case 22:
                wenumchatformatting = WEnumChatFormatting.RESET;
                break;

            default:
                throw new NoWhenBranchMatchedException();
            }

            return wenumchatformatting;
        }
    }

    @NotNull
    public String formatString(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        String s = this.wrapped.formatString(name);

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.formatString(name)");
        return s;
    }

    public boolean isSameTeam(@NotNull ITeam team) {
        Intrinsics.checkParameterIsNotNull(team, "team");
        Team team = this.wrapped;
        boolean $i$f$unwrap = false;
        Team team1 = ((TeamImpl) team).getWrapped();

        return team.isSameTeam(team1);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof TeamImpl && Intrinsics.areEqual(((TeamImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Team getWrapped() {
        return this.wrapped;
    }

    public TeamImpl(@NotNull Team wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
