package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.event.IClickEvent;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.chat.Client;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientErrorPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientNewJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientPrivateMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientSuccessPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "LiquidChat",
    description = "Allows you to chat with other LiquidBounce users.",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 !2\u00020\u0001:\u0001!B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "client", "Lnet/ccbluex/liquidbounce/chat/Client;", "getClient", "()Lnet/ccbluex/liquidbounce/chat/Client;", "connectTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "jwtValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getJwtValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "loggedIn", "", "loginThread", "Ljava/lang/Thread;", "urlPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "connect", "", "onDisable", "onSession", "sessionEvent", "Lnet/ccbluex/liquidbounce/event/SessionEvent;", "onUpdate", "updateEvent", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "toChatComponent", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "string", "", "Companion", "LiquidBounce"}
)
public final class LiquidChat extends Module {

    @NotNull
    private final BoolValue jwtValue;
    @NotNull
    private final Client client;
    private boolean loggedIn;
    private Thread loginThread;
    private final MSTimer connectTimer;
    private final Pattern urlPattern;
    @NotNull
    private static String jwtToken = "";
    public static final LiquidChat.Companion Companion = new LiquidChat.Companion((DefaultConstructorMarker) null);

    @NotNull
    public final BoolValue getJwtValue() {
        return this.jwtValue;
    }

    @NotNull
    public final Client getClient() {
        return this.client;
    }

    public void onDisable() {
        this.loggedIn = false;
        this.client.disconnect();
    }

    @EventTarget
    public final void onSession(@NotNull SessionEvent sessionEvent) {
        Intrinsics.checkParameterIsNotNull(sessionEvent, "sessionEvent");
        this.client.disconnect();
        this.connect();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent updateEvent) {
        Intrinsics.checkParameterIsNotNull(updateEvent, "updateEvent");
        if (!this.client.isConnected()) {
            if (this.loginThread != null) {
                Thread thread = this.loginThread;

                if (this.loginThread == null) {
                    Intrinsics.throwNpe();
                }

                if (thread.isAlive()) {
                    return;
                }
            }

            if (this.connectTimer.hasTimePassed(5000L)) {
                this.connect();
                this.connectTimer.reset();
            }

        }
    }

    private final void connect() {
        if (!this.client.isConnected()) {
            if (this.loginThread != null) {
                Thread thread = this.loginThread;

                if (this.loginThread == null) {
                    Intrinsics.throwNpe();
                }

                if (thread.isAlive()) {
                    return;
                }
            }

            if (((Boolean) this.jwtValue.get()).booleanValue()) {
                CharSequence charsequence = (CharSequence) LiquidChat.jwtToken;
                boolean flag = false;

                if (charsequence.length() == 0) {
                    ClientUtils.displayChatMessage("§7[§a§lChat§7] §cError: §7No token provided!");
                    this.setState(false);
                    return;
                }
            }

            this.loggedIn = false;
            this.loginThread = ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) (new Function0(0) {
                public final void invoke() {
                    try {
                        LiquidChat.this.getClient().connect();
                        if (((Boolean) LiquidChat.this.getJwtValue().get()).booleanValue()) {
                            LiquidChat.this.getClient().loginJWT(LiquidChat.Companion.getJwtToken());
                        } else if (UserUtils.INSTANCE.isValidToken(MinecraftInstance.mc.getSession().getToken())) {
                            LiquidChat.this.getClient().loginMojang();
                        }
                    } catch (Exception exception) {
                        ClientUtils.getLogger().error("LiquidChat error", (Throwable) exception);
                        ClientUtils.displayChatMessage("§7[§a§lChat§7] §cError: §7" + exception.getClass().getName() + ": " + exception.getMessage());
                    }

                    LiquidChat.this.loginThread = (Thread) null;
                }
            }), 31, (Object) null);
        }
    }

    private final IIChatComponent toChatComponent(String string) {
        IIChatComponent component = (IIChatComponent) null;
        Matcher matcher = this.urlPattern.matcher((CharSequence) string);
        int lastEnd = 0;

        String s;

        while (matcher.find()) {
            int end = matcher.start();
            int end1 = matcher.end();
            boolean link = false;

            if (string == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            s = string.substring(lastEnd, end);
            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            String part = s;
            CharSequence url = (CharSequence) part;

            link = false;
            if (url.length() > 0) {
                if (component == null) {
                    component = MinecraftInstance.classProvider.createChatComponentText(part);
                    component.getChatStyle().setColor(WEnumChatFormatting.GRAY);
                } else {
                    component.appendText(part);
                }
            }

            lastEnd = end1;
            boolean flag = false;

            if (string == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            s = string.substring(end, end1);
            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            String url1 = s;

            try {
                if ((new URI(url1)).getScheme() != null) {
                    IIChatComponent link1 = MinecraftInstance.classProvider.createChatComponentText(url1);

                    link1.getChatStyle().setChatClickEvent(MinecraftInstance.classProvider.createClickEvent(IClickEvent.WAction.OPEN_URL, url1));
                    link1.getChatStyle().setUnderlined(true);
                    link1.getChatStyle().setColor(WEnumChatFormatting.GRAY);
                    if (component == null) {
                        component = link1;
                    } else {
                        component.appendSibling(link1);
                    }
                    continue;
                }
            } catch (URISyntaxException urisyntaxexception) {
                ;
            }

            if (component == null) {
                component = MinecraftInstance.classProvider.createChatComponentText(url1);
                component.getChatStyle().setColor(WEnumChatFormatting.GRAY);
            } else {
                component.appendText(url1);
            }
        }

        boolean part1 = false;

        if (string == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            s = string.substring(lastEnd);
            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).substring(startIndex)");
            String end2 = s;

            if (component == null) {
                component = MinecraftInstance.classProvider.createChatComponentText(end2);
                component.getChatStyle().setColor(WEnumChatFormatting.GRAY);
            } else {
                CharSequence end3 = (CharSequence) end2;

                part1 = false;
                if (end3.length() > 0) {
                    part1 = false;
                    if (string == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s = string.substring(lastEnd);
                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).substring(startIndex)");
                    String s1 = s;

                    component.appendText(s1);
                }
            }

            return component;
        }
    }

    public LiquidChat() {
        this.setState(true);
        this.setArray(false);
        this.jwtValue = (BoolValue) (new BoolValue("JWT", false) {
            protected void onChanged(boolean oldValue, boolean newValue) {
                if (LiquidChat.this.getState()) {
                    LiquidChat.this.setState(false);
                    LiquidChat.this.setState(true);
                }

            }
        });
        this.client = (Client) (new Client() {
            public void onConnect() {
                ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Connecting to chat server...");
            }

            public void onConnected() {
                ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Connected to chat server!");
            }

            public void onHandshake(boolean success) {}

            public void onDisconnect() {
                ClientUtils.displayChatMessage("§7[§a§lChat§7] §cDisconnected from chat server!");
            }

            public void onLogon() {
                ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Logging in...");
            }

            public void onPacket(@NotNull Packet packet) {
                Intrinsics.checkParameterIsNotNull(packet, "packet");
                if (packet instanceof ClientMessagePacket) {
                    IEntityPlayerSP message = MinecraftInstance.mc.getThePlayer();

                    if (message == null) {
                        ClientUtils.getLogger().info("[LiquidChat] " + ((ClientMessagePacket) packet).getUser().getName() + ": " + ((ClientMessagePacket) packet).getContent());
                        return;
                    }

                    IIChatComponent chatComponent = MinecraftInstance.classProvider.createChatComponentText("§7[§a§lChat§7] §9" + ((ClientMessagePacket) packet).getUser().getName() + ": ");
                    IIChatComponent messageComponent = LiquidChat.this.toChatComponent(((ClientMessagePacket) packet).getContent());

                    chatComponent.appendSibling(messageComponent);
                    message.addChatMessage(chatComponent);
                } else if (packet instanceof ClientPrivateMessagePacket) {
                    ClientUtils.displayChatMessage("§7[§a§lChat§7] §c(P)§9 " + ((ClientPrivateMessagePacket) packet).getUser().getName() + ": §7" + ((ClientPrivateMessagePacket) packet).getContent());
                } else {
                    String message1;

                    if (packet instanceof ClientErrorPacket) {
                        String s;
                        label103: {
                            String chatComponent1 = ((ClientErrorPacket) packet).getMessage();

                            switch (chatComponent1.hashCode()) {
                            case -1702222618:
                                if (chatComponent1.equals("LoginFailed")) {
                                    s = "Login Failed!";
                                    break label103;
                                }
                                break;

                            case -1510372431:
                                if (chatComponent1.equals("NotBanned")) {
                                    s = "You are not banned!";
                                    break label103;
                                }
                                break;

                            case -1285959671:
                                if (chatComponent1.equals("MessageTooLong")) {
                                    s = "Message is too long!";
                                    break label103;
                                }
                                break;

                            case -1045325153:
                                if (chatComponent1.equals("AlreadyLoggedIn")) {
                                    s = "You are already logged in!";
                                    break label103;
                                }
                                break;

                            case -643429254:
                                if (chatComponent1.equals("RateLimited")) {
                                    s = "You have been rate limited. Please try again later.";
                                    break label103;
                                }
                                break;

                            case -187442662:
                                if (chatComponent1.equals("NotLoggedIn")) {
                                    s = "You must be logged in to use the chat! Enable LiquidChat.";
                                    break label103;
                                }
                                break;

                            case -133334702:
                                if (chatComponent1.equals("InvalidId")) {
                                    s = "The given ID is invalid!";
                                    break label103;
                                }
                                break;

                            case -52595950:
                                if (chatComponent1.equals("InvalidCharacter")) {
                                    s = "Message contains a non-ASCII character!";
                                    break label103;
                                }
                                break;

                            case 227434454:
                                if (chatComponent1.equals("PrivateMessageNotAccepted")) {
                                    s = "Private message not accepted!";
                                    break label103;
                                }
                                break;

                            case 248847163:
                                if (chatComponent1.equals("NotSupported")) {
                                    s = "This method is not supported!";
                                    break label103;
                                }
                                break;

                            case 635054813:
                                if (chatComponent1.equals("Internal")) {
                                    s = "An internal server error occurred!";
                                    break label103;
                                }
                                break;

                            case 944720037:
                                if (chatComponent1.equals("NotPermitted")) {
                                    s = "You are missing the required permissions!";
                                    break label103;
                                }
                                break;

                            case 1022838298:
                                if (chatComponent1.equals("EmptyMessage")) {
                                    s = "You are trying to send an empty message!";
                                    break label103;
                                }
                                break;

                            case 1982491454:
                                if (chatComponent1.equals("Banned")) {
                                    s = "You are banned!";
                                    break label103;
                                }
                                break;

                            case 1990882921:
                                if (chatComponent1.equals("MojangRequestMissing")) {
                                    s = "Mojang request missing!";
                                    break label103;
                                }
                            }

                            s = ((ClientErrorPacket) packet).getMessage();
                        }

                        message1 = s;
                        ClientUtils.displayChatMessage("§7[§a§lChat§7] §cError: §7" + message1);
                    } else if (packet instanceof ClientSuccessPacket) {
                        message1 = ((ClientSuccessPacket) packet).getReason();
                        switch (message1.hashCode()) {
                        case 66543:
                            if (message1.equals("Ban")) {
                                ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Successfully banned user!");
                            }
                            break;

                        case 73596745:
                            if (message1.equals("Login")) {
                                ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Logged in!");
                                ClientUtils.displayChatMessage("====================================");
                                ClientUtils.displayChatMessage("§c>> §lLiquidChat");
                                ClientUtils.displayChatMessage("§7Write message: §a.chat <message>");
                                ClientUtils.displayChatMessage("§7Write private message: §a.pchat <user> <message>");
                                ClientUtils.displayChatMessage("====================================");
                                this.setLoggedIn(true);
                            }
                            break;

                        case 81873590:
                            if (message1.equals("Unban")) {
                                ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Successfully unbanned user!");
                            }
                        }
                    } else if (packet instanceof ClientNewJWTPacket) {
                        LiquidChat.Companion.setJwtToken(((ClientNewJWTPacket) packet).getToken());
                        LiquidChat.this.getJwtValue().set(Boolean.valueOf(true));
                        LiquidChat.this.setState(false);
                        LiquidChat.this.setState(true);
                    }
                }

            }

            public void onError(@NotNull Throwable cause) {
                Intrinsics.checkParameterIsNotNull(cause, "cause");
                ClientUtils.displayChatMessage("§7[§a§lChat§7] §c§lError: §7" + cause.getClass().getName() + ": " + cause.getMessage());
            }
        });
        this.connectTimer = new MSTimer();
        this.urlPattern = Pattern.compile("((?:[a-z0-9]{2,}:\\/\\/)?(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3}|(?:[-\\w_\\.]{1,}\\.[a-z]{2,}?))(?::[0-9]{1,5})?.*?(?=[!\"§ \n]|$))", 2);
    }

    public static final Thread access$getLoginThread$p(LiquidChat $this) {
        return $this.loginThread;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat$Companion;", "", "()V", "jwtToken", "", "getJwtToken", "()Ljava/lang/String;", "setJwtToken", "(Ljava/lang/String;)V", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final String getJwtToken() {
            return LiquidChat.jwtToken;
        }

        public final void setJwtToken(@NotNull String <set-?>) {
            Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
            LiquidChat.jwtToken = <set-?>;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
