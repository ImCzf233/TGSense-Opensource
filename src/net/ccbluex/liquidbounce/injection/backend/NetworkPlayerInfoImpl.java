package net.ccbluex.liquidbounce.injection.backend;

import com.mojang.authlib.GameProfile;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001f"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/NetworkPlayerInfoImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/INetworkPlayerInfo;", "wrapped", "Lnet/minecraft/client/network/NetworkPlayerInfo;", "(Lnet/minecraft/client/network/NetworkPlayerInfo;)V", "displayName", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "getDisplayName", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "gameProfile", "Lcom/mojang/authlib/GameProfile;", "getGameProfile", "()Lcom/mojang/authlib/GameProfile;", "locationSkin", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getLocationSkin", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "playerTeam", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "getPlayerTeam", "()Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "responseTime", "", "getResponseTime", "()I", "getWrapped", "()Lnet/minecraft/client/network/NetworkPlayerInfo;", "equals", "", "other", "", "LiquidBounce"}
)
public final class NetworkPlayerInfoImpl implements INetworkPlayerInfo {

    @NotNull
    private final NetworkPlayerInfo wrapped;

    @NotNull
    public IResourceLocation getLocationSkin() {
        ResourceLocation resourcelocation = this.wrapped.getLocationSkin();

        Intrinsics.checkExpressionValueIsNotNull(resourcelocation, "wrapped.locationSkin");
        ResourceLocation $this$wrap$iv = resourcelocation;
        boolean $i$f$wrap = false;

        return (IResourceLocation) (new ResourceLocationImpl($this$wrap$iv));
    }

    public int getResponseTime() {
        return this.wrapped.getResponseTime();
    }

    @NotNull
    public GameProfile getGameProfile() {
        GameProfile gameprofile = this.wrapped.getGameProfile();

        Intrinsics.checkExpressionValueIsNotNull(gameprofile, "wrapped.gameProfile");
        return gameprofile;
    }

    @Nullable
    public ITeam getPlayerTeam() {
        ScorePlayerTeam scoreplayerteam = this.wrapped.getPlayerTeam();
        ITeam iteam;

        if (scoreplayerteam != null) {
            Team $this$wrap$iv = (Team) scoreplayerteam;
            boolean $i$f$wrap = false;

            iteam = (ITeam) (new TeamImpl($this$wrap$iv));
        } else {
            iteam = null;
        }

        return iteam;
    }

    @Nullable
    public IIChatComponent getDisplayName() {
        ITextComponent itextcomponent = this.wrapped.getDisplayName();
        IIChatComponent iichatcomponent;

        if (itextcomponent != null) {
            ITextComponent $this$wrap$iv = itextcomponent;
            boolean $i$f$wrap = false;

            iichatcomponent = (IIChatComponent) (new IChatComponentImpl($this$wrap$iv));
        } else {
            iichatcomponent = null;
        }

        return iichatcomponent;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof NetworkPlayerInfoImpl && Intrinsics.areEqual(((NetworkPlayerInfoImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final NetworkPlayerInfo getWrapped() {
        return this.wrapped;
    }

    public NetworkPlayerInfoImpl(@NotNull NetworkPlayerInfo wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
