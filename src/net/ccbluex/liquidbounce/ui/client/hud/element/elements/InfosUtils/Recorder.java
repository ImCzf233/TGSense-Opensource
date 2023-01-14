package net.ccbluex.liquidbounce.ui.client.hud.element.elements.InfosUtils;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0007J\u0010\u0010-\u001a\u00020*2\u0006\u0010+\u001a\u00020.H\u0003J\u0010\u0010/\u001a\u00020*2\u0006\u0010+\u001a\u000200H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001a\u0010\u0012\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001a\u0010$\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\b¨\u00061"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/InfosUtils/Recorder;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "GamesPlayed", "", "getGamesPlayed", "()I", "setGamesPlayed", "(I)V", "KD", "getKD", "setKD", "ban", "getBan", "setBan", "dead", "getDead", "setDead", "killCounts", "getKillCounts", "setKillCounts", "startTime", "", "getStartTime", "()J", "setStartTime", "(J)V", "syncEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getSyncEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "setSyncEntity", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)V", "totalPlayed", "getTotalPlayed", "setTotalPlayed", "win", "getWin", "setWin", "handleEvents", "", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Recorder implements Listenable {

    @Nullable
    private static IEntityLivingBase syncEntity;
    private static int killCounts;
    private static int win;
    private static int totalPlayed;
    private static int ban;
    private static int GamesPlayed;
    private static int KD;
    private static long startTime;
    private static int dead;
    public static final Recorder INSTANCE;

    @Nullable
    public final IEntityLivingBase getSyncEntity() {
        return Recorder.syncEntity;
    }

    public final void setSyncEntity(@Nullable IEntityLivingBase <set-?>) {
        Recorder.syncEntity = <set-?>;
    }

    public final int getKillCounts() {
        return Recorder.killCounts;
    }

    public final void setKillCounts(int <set-?>) {
        Recorder.killCounts = <set-?>;
    }

    public final int getWin() {
        return Recorder.win;
    }

    public final void setWin(int <set-?>) {
        Recorder.win = <set-?>;
    }

    public final int getTotalPlayed() {
        return Recorder.totalPlayed;
    }

    public final void setTotalPlayed(int <set-?>) {
        Recorder.totalPlayed = <set-?>;
    }

    public final int getBan() {
        return Recorder.ban;
    }

    public final void setBan(int <set-?>) {
        Recorder.ban = <set-?>;
    }

    public final int getGamesPlayed() {
        return Recorder.GamesPlayed;
    }

    public final void setGamesPlayed(int <set-?>) {
        Recorder.GamesPlayed = <set-?>;
    }

    public final int getKD() {
        return Recorder.KD;
    }

    public final void setKD(int <set-?>) {
        Recorder.KD = <set-?>;
    }

    public final long getStartTime() {
        return Recorder.startTime;
    }

    public final void setStartTime(long <set-?>) {
        Recorder.startTime = <set-?>;
    }

    public final int getDead() {
        return Recorder.dead;
    }

    public final void setDead(int <set-?>) {
        Recorder.dead = <set-?>;
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        Recorder.syncEntity = (IEntityLivingBase) event.getTargetEntity();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityLivingBase ientitylivingbase = Recorder.syncEntity;

        if (Recorder.syncEntity == null) {
            Intrinsics.throwNpe();
        }

        if (ientitylivingbase.isDead()) {
            ++Recorder.killCounts;
            int i = Recorder.killCounts;

            Recorder.syncEntity = (IEntityLivingBase) null;
        }

    }

    @EventTarget
    private final void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C00Handshake) {
            Recorder.startTime = System.currentTimeMillis();
        }

        IPacket ipacket = event.getPacket();

        if (ipacket == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketChat");
        } else {
            ITextComponent itextcomponent = ((SPacketChat) ipacket).getChatComponent();

            Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "(event.packet as SPacketChat).chatComponent");
            String message = itextcomponent.getUnformattedText();
            IPacket packet = event.getPacket();
            String KD;
            int i;

            if (packet instanceof SPacketTitle) {
                itextcomponent = ((SPacketTitle) packet).getMessage();
                if (itextcomponent == null) {
                    return;
                }

                KD = itextcomponent.getFormattedText();
                Intrinsics.checkExpressionValueIsNotNull(KD, "title");
                if (StringsKt.startsWith$default(KD, "游戏�?�?", false, 2, (Object) null)) {
                    i = Recorder.totalPlayed++;
                }

                if (StringsKt.startsWith$default(KD, "恭喜", false, 2, (Object) null)) {
                    i = Recorder.win++;
                }
            }

            Intrinsics.checkExpressionValueIsNotNull(message, "message");
            int j;

            if (StringsKt.contains$default((CharSequence) message, (CharSequence) "Reason", false, 2, (Object) null)) {
                j = Recorder.ban++;
            }

            if (event.getPacket() instanceof C00Handshake) {
                Recorder.startTime = System.currentTimeMillis();
            }

            int k;

            if (packet instanceof SPacketTitle) {
                itextcomponent = ((SPacketTitle) packet).getMessage();
                if (itextcomponent == null) {
                    return;
                }

                KD = itextcomponent.getFormattedText();
                Intrinsics.checkExpressionValueIsNotNull(KD, "title");
                if (StringsKt.startsWith$default(KD, "游戏�?�?", false, 2, (Object) null)) {
                    k = Recorder.GamesPlayed + 1;
                }

                if (StringsKt.startsWith$default(KD, "恭喜", false, 2, (Object) null)) {
                    i = Recorder.win++;
                }
            }

            if (StringsKt.contains$default((CharSequence) message, (CharSequence) "Reason", false, 2, (Object) null)) {
                j = Recorder.ban++;
            }

            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.isDead()) {
                j = Recorder.dead++;
            }

            j = Recorder.killCounts / Recorder.dead;
            if (Recorder.dead == 0) {
                k = j + 1;
            }

        }
    }

    public boolean handleEvents() {
        return true;
    }

    static {
        Recorder recorder = new Recorder();

        INSTANCE = recorder;
        Recorder.startTime = System.currentTimeMillis();
    }
}
