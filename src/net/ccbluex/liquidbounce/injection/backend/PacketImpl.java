package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketChatMessage;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketHeldItemChange;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ISPacketCloseWindow;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketAnimation;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketChat;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntity;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketResourcePackSend;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketTabComplete;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketWindowItems;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.network.play.server.SPacketWindowItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000~\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\b\u0016\u0018\u0000*\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\"H\u0016J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010\'\u001a\u00020(H\u0016J\u0013\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010,H\u0096\u0002R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006-"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "T", "Lnet/minecraft/network/Packet;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "wrapped", "(Lnet/minecraft/network/Packet;)V", "getWrapped", "()Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/Packet;", "asCPacketChatMessage", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketChatMessage;", "asCPacketCustomPayload", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCustomPayload;", "asCPacketHandshake", "Lnet/ccbluex/liquidbounce/api/minecraft/network/handshake/client/ICPacketHandshake;", "asCPacketHeldItemChange", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketHeldItemChange;", "asCPacketPlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayer;", "asCPacketPlayerDigging", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging;", "asCPacketUseEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity;", "asSPacketAnimation", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketAnimation;", "asSPacketChat", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketChat;", "asSPacketCloseWindow", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ISPacketCloseWindow;", "asSPacketEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketEntity;", "asSPacketEntityVelocity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketEntityVelocity;", "asSPacketPosLook", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketPosLook;", "asSPacketResourcePackSend", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketResourcePackSend;", "asSPacketTabComplete", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketTabComplete;", "asSPacketWindowItems", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketWindowItems;", "equals", "", "other", "", "LiquidBounce"}
)
public class PacketImpl implements IPacket {

    @NotNull
    private final Packet wrapped;

    @NotNull
    public ISPacketChat asSPacketChat() {
        SPacketChatImpl spacketchatimpl = new SPacketChatImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketChat");
        } else {
            spacketchatimpl.<init>((SPacketChat) packet);
            return (ISPacketChat) spacketchatimpl;
        }
    }

    @NotNull
    public ISPacketAnimation asSPacketAnimation() {
        SPacketAnimationImpl spacketanimationimpl = new SPacketAnimationImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketAnimation");
        } else {
            spacketanimationimpl.<init>((SPacketAnimation) packet);
            return (ISPacketAnimation) spacketanimationimpl;
        }
    }

    @NotNull
    public ISPacketEntity asSPacketEntity() {
        SPacketEntityImpl spacketentityimpl = new SPacketEntityImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketEntity");
        } else {
            spacketentityimpl.<init>((SPacketEntity) packet);
            return (ISPacketEntity) spacketentityimpl;
        }
    }

    @NotNull
    public ICPacketPlayer asCPacketPlayer() {
        CPacketPlayerImpl cpacketplayerimpl = new CPacketPlayerImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketPlayer");
        } else {
            cpacketplayerimpl.<init>((CPacketPlayer) packet);
            return (ICPacketPlayer) cpacketplayerimpl;
        }
    }

    @NotNull
    public ICPacketUseEntity asCPacketUseEntity() {
        CPacketUseEntityImpl cpacketuseentityimpl = new CPacketUseEntityImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketUseEntity");
        } else {
            cpacketuseentityimpl.<init>((CPacketUseEntity) packet);
            return (ICPacketUseEntity) cpacketuseentityimpl;
        }
    }

    @NotNull
    public ISPacketEntityVelocity asSPacketEntityVelocity() {
        SPacketEntityVelocityImpl spacketentityvelocityimpl = new SPacketEntityVelocityImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketEntityVelocity");
        } else {
            spacketentityvelocityimpl.<init>((SPacketEntityVelocity) packet);
            return (ISPacketEntityVelocity) spacketentityvelocityimpl;
        }
    }

    @NotNull
    public ICPacketChatMessage asCPacketChatMessage() {
        CPacketChatMessageImpl cpacketchatmessageimpl = new CPacketChatMessageImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketChatMessage");
        } else {
            cpacketchatmessageimpl.<init>((CPacketChatMessage) packet);
            return (ICPacketChatMessage) cpacketchatmessageimpl;
        }
    }

    @NotNull
    public ISPacketCloseWindow asSPacketCloseWindow() {
        SPacketCloseWindowImpl spacketclosewindowimpl = new SPacketCloseWindowImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketCloseWindow");
        } else {
            spacketclosewindowimpl.<init>((SPacketCloseWindow) packet);
            return (ISPacketCloseWindow) spacketclosewindowimpl;
        }
    }

    @NotNull
    public ISPacketTabComplete asSPacketTabComplete() {
        SPacketTabCompleteImpl spackettabcompleteimpl = new SPacketTabCompleteImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketTabComplete");
        } else {
            spackettabcompleteimpl.<init>((SPacketTabComplete) packet);
            return (ISPacketTabComplete) spackettabcompleteimpl;
        }
    }

    @NotNull
    public ISPacketPosLook asSPacketPosLook() {
        SPacketPosLookImpl spacketposlookimpl = new SPacketPosLookImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketPlayerPosLook");
        } else {
            spacketposlookimpl.<init>((SPacketPlayerPosLook) packet);
            return (ISPacketPosLook) spacketposlookimpl;
        }
    }

    @NotNull
    public ISPacketResourcePackSend asSPacketResourcePackSend() {
        SPacketResourcePackSendImpl spacketresourcepacksendimpl = new SPacketResourcePackSendImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketResourcePackSend");
        } else {
            spacketresourcepacksendimpl.<init>((SPacketResourcePackSend) packet);
            return (ISPacketResourcePackSend) spacketresourcepacksendimpl;
        }
    }

    @NotNull
    public ICPacketHeldItemChange asCPacketHeldItemChange() {
        CPacketHeldItemChangeImpl cpackethelditemchangeimpl = new CPacketHeldItemChangeImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketHeldItemChange");
        } else {
            cpackethelditemchangeimpl.<init>((CPacketHeldItemChange) packet);
            return (ICPacketHeldItemChange) cpackethelditemchangeimpl;
        }
    }

    @NotNull
    public ISPacketWindowItems asSPacketWindowItems() {
        SPacketWindowItemsImpl spacketwindowitemsimpl = new SPacketWindowItemsImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketWindowItems");
        } else {
            spacketwindowitemsimpl.<init>((SPacketWindowItems) packet);
            return (ISPacketWindowItems) spacketwindowitemsimpl;
        }
    }

    @NotNull
    public ICPacketCustomPayload asCPacketCustomPayload() {
        CPacketCustomPayloadImpl cpacketcustompayloadimpl = new CPacketCustomPayloadImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketCustomPayload");
        } else {
            cpacketcustompayloadimpl.<init>((CPacketCustomPayload) packet);
            return (ICPacketCustomPayload) cpacketcustompayloadimpl;
        }
    }

    @NotNull
    public ICPacketHandshake asCPacketHandshake() {
        CPacketHandshakeImpl cpackethandshakeimpl = new CPacketHandshakeImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.handshake.client.C00Handshake");
        } else {
            cpackethandshakeimpl.<init>((C00Handshake) packet);
            return (ICPacketHandshake) cpackethandshakeimpl;
        }
    }

    @NotNull
    public ICPacketPlayerDigging asCPacketPlayerDigging() {
        CPacketPlayerDiggingImpl cpacketplayerdiggingimpl = new CPacketPlayerDiggingImpl;
        Packet packet = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketPlayerDigging");
        } else {
            cpacketplayerdiggingimpl.<init>((CPacketPlayerDigging) packet);
            return (ICPacketPlayerDigging) cpacketplayerdiggingimpl;
        }
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof PacketImpl && Intrinsics.areEqual(((PacketImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Packet getWrapped() {
        return this.wrapped;
    }

    public PacketImpl(@NotNull Packet wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
