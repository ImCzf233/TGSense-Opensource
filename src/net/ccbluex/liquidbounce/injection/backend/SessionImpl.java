package net.ccbluex.liquidbounce.injection.backend;

import com.mojang.authlib.GameProfile;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.util.ISession;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\bR\u0014\u0010\u0011\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0019"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/SessionImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;", "wrapped", "Lnet/minecraft/util/Session;", "(Lnet/minecraft/util/Session;)V", "playerId", "", "getPlayerId", "()Ljava/lang/String;", "profile", "Lcom/mojang/authlib/GameProfile;", "getProfile", "()Lcom/mojang/authlib/GameProfile;", "sessionType", "getSessionType", "token", "getToken", "username", "getUsername", "getWrapped", "()Lnet/minecraft/util/Session;", "equals", "", "other", "", "LiquidBounce"}
)
public final class SessionImpl implements ISession {

    @NotNull
    private final Session wrapped;

    @NotNull
    public GameProfile getProfile() {
        GameProfile gameprofile = this.wrapped.getProfile();

        Intrinsics.checkExpressionValueIsNotNull(gameprofile, "wrapped.profile");
        return gameprofile;
    }

    @NotNull
    public String getUsername() {
        String s = this.wrapped.getUsername();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.username");
        return s;
    }

    @NotNull
    public String getPlayerId() {
        String s = this.wrapped.getPlayerID();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.playerID");
        return s;
    }

    @NotNull
    public String getSessionType() {
        return this.wrapped.sessionType.name();
    }

    @NotNull
    public String getToken() {
        String s = this.wrapped.getToken();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.token");
        return s;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof SessionImpl && Intrinsics.areEqual(((SessionImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Session getWrapped() {
        return this.wrapped;
    }

    public SessionImpl(@NotNull Session wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
