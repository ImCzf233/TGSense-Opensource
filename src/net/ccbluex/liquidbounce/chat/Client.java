package net.ccbluex.liquidbounce.chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.chat.packet.PacketDeserializer;
import net.ccbluex.liquidbounce.chat.packet.PacketSerializer;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientErrorPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientMojangInfoPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientNewJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientPrivateMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientSuccessPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerBanUserPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerLoginJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerLoginMojangPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerPrivateMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerRequestJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerRequestMojangInfoPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerUnbanUserPacket;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0018J\u0006\u0010 \u001a\u00020\u001eJ\u0006\u0010!\u001a\u00020\u001eJ\u0006\u0010\"\u001a\u00020\rJ\u000e\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020\u0018J\u0006\u0010%\u001a\u00020\u001eJ\u0015\u0010&\u001a\u00020\u001e2\u0006\u0010\'\u001a\u00020\u0018H\u0000¢\u0006\u0002\b(J\u000e\u0010)\u001a\u00020\u001e2\u0006\u0010\'\u001a\u00020\u0018J\u000e\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020,J\u0016\u0010-\u001a\u00020\u001e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\'\u001a\u00020\u0018J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u0018H\u0002J\u000e\u0010/\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0018R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c¨\u00060"},
    d2 = { "Lnet/ccbluex/liquidbounce/chat/Client;", "Lnet/ccbluex/liquidbounce/chat/ClientListener;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "channel", "Lio/netty/channel/Channel;", "getChannel$LiquidBounce", "()Lio/netty/channel/Channel;", "setChannel$LiquidBounce", "(Lio/netty/channel/Channel;)V", "deserializer", "Lnet/ccbluex/liquidbounce/chat/packet/PacketDeserializer;", "jwt", "", "getJwt", "()Z", "setJwt", "(Z)V", "loggedIn", "getLoggedIn", "setLoggedIn", "serializer", "Lnet/ccbluex/liquidbounce/chat/packet/PacketSerializer;", "username", "", "getUsername", "()Ljava/lang/String;", "setUsername", "(Ljava/lang/String;)V", "banUser", "", "target", "connect", "disconnect", "isConnected", "loginJWT", "token", "loginMojang", "onMessage", "message", "onMessage$LiquidBounce", "sendMessage", "sendPacket", "packet", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "sendPrivateMessage", "toUUID", "unbanUser", "LiquidBounce"}
)
public abstract class Client extends MinecraftInstance implements ClientListener {

    @Nullable
    private Channel channel;
    @NotNull
    private String username = "";
    private boolean jwt;
    private boolean loggedIn;
    private final PacketSerializer serializer = new PacketSerializer();
    private final PacketDeserializer deserializer = new PacketDeserializer();

    @Nullable
    public final Channel getChannel$LiquidBounce() {
        return this.channel;
    }

    public final void setChannel$LiquidBounce(@Nullable Channel <set-?>) {
        this.channel = <set-?>;
    }

    @NotNull
    public final String getUsername() {
        return this.username;
    }

    public final void setUsername(@NotNull String <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.username = <set-?>;
    }

    public final boolean getJwt() {
        return this.jwt;
    }

    public final void setJwt(boolean <set-?>) {
        this.jwt = <set-?>;
    }

    public final boolean getLoggedIn() {
        return this.loggedIn;
    }

    public final void setLoggedIn(boolean <set-?>) {
        this.loggedIn = <set-?>;
    }

    public final void connect() {
        this.onConnect();
        URI uri = new URI("wss://chat.liquidbounce.net:7886/ws");
        boolean ssl = StringsKt.equals(uri.getScheme(), "wss", true);
        final SslContext sslContext = ssl ? SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE) : null;
        NioEventLoopGroup group = new NioEventLoopGroup();
        WebSocketClientHandshaker websocketclienthandshaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, (String) null, true, (HttpHeaders) (new DefaultHttpHeaders()));

        Intrinsics.checkExpressionValueIsNotNull(websocketclienthandshaker, "WebSocketClientHandshake…ue, DefaultHttpHeaders())");
        final ClientHandler handler = new ClientHandler(this, websocketclienthandshaker);
        Bootstrap bootstrap = new Bootstrap();

        ((Bootstrap) ((Bootstrap) bootstrap.group((EventLoopGroup) group)).channel(NioSocketChannel.class)).handler((ChannelHandler) (new ChannelInitializer() {
            protected void initChannel(@NotNull SocketChannel ch) {
                Intrinsics.checkParameterIsNotNull(ch, "ch");
                ChannelPipeline pipeline = ch.pipeline();

                if (sslContext != null) {
                    pipeline.addLast(new ChannelHandler[] { (ChannelHandler) sslContext.newHandler(ch.alloc())});
                }

                pipeline.addLast(new ChannelHandler[] { (ChannelHandler) (new HttpClientCodec()), (ChannelHandler) (new HttpObjectAggregator(8192)), (ChannelHandler) handler});
            }
        }));
        this.channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
        handler.getHandshakeFuture().sync();
        if (this.isConnected()) {
            this.onConnected();
        }

    }

    public final void disconnect() {
        Channel channel = this.channel;

        if (this.channel != null) {
            channel.close();
        }

        this.channel = (Channel) null;
        this.username = "";
        this.jwt = false;
    }

    public final void loginMojang() {
        this.sendPacket((Packet) (new ServerRequestMojangInfoPacket()));
    }

    public final void loginJWT(@NotNull String token) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        this.onLogon();
        this.sendPacket((Packet) (new ServerLoginJWTPacket(token, true)));
        this.jwt = true;
    }

    public final boolean isConnected() {
        boolean flag;

        if (this.channel != null) {
            Channel channel = this.channel;

            if (this.channel == null) {
                Intrinsics.throwNpe();
            }

            if (channel.isOpen()) {
                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }

    public final void onMessage$LiquidBounce(@NotNull String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        Gson gson = (new GsonBuilder()).registerTypeAdapter((Type) Packet.class, this.deserializer).create();
        Packet packet = (Packet) gson.fromJson(message, Packet.class);

        if (packet instanceof ClientMojangInfoPacket) {
            this.onLogon();

            try {
                String throwable = ((ClientMojangInfoPacket) packet).getSessionHash();

                MinecraftInstance.functions.sessionServiceJoinServer(MinecraftInstance.mc.getSession().getProfile(), MinecraftInstance.mc.getSession().getToken(), throwable);
                this.username = MinecraftInstance.mc.getSession().getUsername();
                this.jwt = false;
                String s = MinecraftInstance.mc.getSession().getUsername();
                UUID uuid = MinecraftInstance.mc.getSession().getProfile().getId();

                Intrinsics.checkExpressionValueIsNotNull(uuid, "mc.session.profile.id");
                this.sendPacket((Packet) (new ServerLoginMojangPacket(s, uuid, true)));
            } catch (Throwable throwable) {
                this.onError(throwable);
            }

        } else {
            Intrinsics.checkExpressionValueIsNotNull(packet, "packet");
            this.onPacket(packet);
        }
    }

    public final void sendPacket(@NotNull Packet packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        Gson gson = (new GsonBuilder()).registerTypeAdapter((Type) Packet.class, this.serializer).create();
        Channel channel = this.channel;

        if (this.channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(packet, (Type) Packet.class)));
        }

    }

    public final void sendMessage(@NotNull String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        this.sendPacket((Packet) (new ServerMessagePacket(message)));
    }

    public final void sendPrivateMessage(@NotNull String username, @NotNull String message) {
        Intrinsics.checkParameterIsNotNull(username, "username");
        Intrinsics.checkParameterIsNotNull(message, "message");
        this.sendPacket((Packet) (new ServerPrivateMessagePacket(username, message)));
    }

    public final void banUser(@NotNull String target) {
        Intrinsics.checkParameterIsNotNull(target, "target");
        this.sendPacket((Packet) (new ServerBanUserPacket(this.toUUID(target))));
    }

    public final void unbanUser(@NotNull String target) {
        Intrinsics.checkParameterIsNotNull(target, "target");
        this.sendPacket((Packet) (new ServerUnbanUserPacket(this.toUUID(target))));
    }

    private final String toUUID(String target) {
        String s;

        try {
            UUID.fromString(target);
            s = target;
        } catch (IllegalArgumentException illegalargumentexception) {
            String incomingUUID = UserUtils.INSTANCE.getUUID(target);

            if (StringsKt.isBlank((CharSequence) incomingUUID)) {
                return "";
            }

            StringBuffer uuid = (new StringBuffer(incomingUUID)).insert(20, '-').insert(16, '-').insert(12, '-').insert(8, '-');
            String s1 = uuid.toString();

            Intrinsics.checkExpressionValueIsNotNull(s1, "uuid.toString()");
            s = s1;
        }

        return s;
    }

    public Client() {
        this.serializer.registerPacket("RequestMojangInfo", ServerRequestMojangInfoPacket.class);
        this.serializer.registerPacket("LoginMojang", ServerLoginMojangPacket.class);
        this.serializer.registerPacket("Message", ServerMessagePacket.class);
        this.serializer.registerPacket("PrivateMessage", ServerPrivateMessagePacket.class);
        this.serializer.registerPacket("BanUser", ServerBanUserPacket.class);
        this.serializer.registerPacket("UnbanUser", ServerUnbanUserPacket.class);
        this.serializer.registerPacket("RequestJWT", ServerRequestJWTPacket.class);
        this.serializer.registerPacket("LoginJWT", ServerLoginJWTPacket.class);
        this.deserializer.registerPacket("MojangInfo", ClientMojangInfoPacket.class);
        this.deserializer.registerPacket("NewJWT", ClientNewJWTPacket.class);
        this.deserializer.registerPacket("Message", ClientMessagePacket.class);
        this.deserializer.registerPacket("PrivateMessage", ClientPrivateMessagePacket.class);
        this.deserializer.registerPacket("Error", ClientErrorPacket.class);
        this.deserializer.registerPacket("Success", ClientSuccessPacket.class);
    }
}
