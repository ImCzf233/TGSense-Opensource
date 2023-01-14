package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketChat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/SPacketChatImpl;", "T", "Lnet/minecraft/network/play/server/SPacketChat;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketChat;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketChat;)V", "chatComponent", "Lnet/minecraft/util/text/ITextComponent;", "getChatComponent", "()Lnet/minecraft/util/text/ITextComponent;", "getChat", "getGetChat", "type", "Lnet/minecraft/util/text/ChatType;", "getType", "()Lnet/minecraft/util/text/ChatType;", "LiquidBounce"}
)
public final class SPacketChatImpl extends PacketImpl implements ISPacketChat {

    @NotNull
    public ITextComponent getChatComponent() {
        ITextComponent itextcomponent = ((SPacketChat) this.getWrapped()).getChatComponent();

        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "wrapped.chatComponent");
        return itextcomponent;
    }

    @NotNull
    public ChatType getType() {
        ChatType chattype = ((SPacketChat) this.getWrapped()).getType();

        Intrinsics.checkExpressionValueIsNotNull(chattype, "wrapped.type");
        return chattype;
    }

    @NotNull
    public ITextComponent getGetChat() {
        ITextComponent itextcomponent = ((SPacketChat) this.getWrapped()).getChatComponent();

        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "wrapped.getChatComponent()");
        return itextcomponent;
    }

    public SPacketChatImpl(@NotNull SPacketChat wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Packet) wrapped);
    }
}
