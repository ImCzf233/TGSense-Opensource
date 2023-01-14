package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "HytGetName",
    description = "已修�?:)",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\nH\u0016J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0012\u0010\u000f\u001a\u00020\n2\b\u0010\r\u001a\u0004\u0018\u00010\u0010H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/HytGetName;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "mode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "clearAll", "", "onDisable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"}
)
public final class HytGetName extends Module {

    private final ListValue mode = new ListValue("GetNameMode", new String[] { "4V4/1V1", "32/64", "16V16"}, "4V4/1V1");

    public void onDisable() {
        this.clearAll();
        super.onDisable();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket $this$unwrap$iv = event.getPacket();
        boolean matcher = false;

        if ($this$unwrap$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.PacketImpl<*>");
        } else {
            Packet packet = ((PacketImpl) $this$unwrap$iv).getWrapped();

            if (packet instanceof SPacketChat) {
                String $this$unwrap$iv1 = (String) this.mode.get();

                matcher = false;
                if ($this$unwrap$iv1 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s = $this$unwrap$iv1.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                $this$unwrap$iv1 = s;
                Matcher matcher2;
                final String name;
                String s1;
                boolean flag;
                Matcher matcher1;
                Pattern pattern;
                ITextComponent itextcomponent;

                switch ($this$unwrap$iv1.hashCode()) {
                case -1961702257:
                    if ($this$unwrap$iv1.equals("4v4/1v1")) {
                        pattern = Pattern.compile("�?死了 (.*?)\\(");
                        itextcomponent = ((SPacketChat) packet).getChatComponent();
                        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "packet.chatComponent");
                        matcher1 = pattern.matcher((CharSequence) itextcomponent.getUnformattedText());
                        pattern = Pattern.compile("起床战争>> (.*?) (\\((((.*?)死了!)))");
                        itextcomponent = ((SPacketChat) packet).getChatComponent();
                        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "packet.chatComponent");
                        matcher2 = pattern.matcher((CharSequence) itextcomponent.getUnformattedText());
                        if (matcher1.find()) {
                            s = matcher1.group(1);
                            Intrinsics.checkExpressionValueIsNotNull(s, "matcher.group(1)");
                            s1 = s;
                            flag = false;
                            if (s1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }

                            name = StringsKt.trim((CharSequence) s1).toString();
                            if (Intrinsics.areEqual(name, "") ^ true) {
                                LiquidBounce.INSTANCE.getFileManager().friendsConfig.addFriend(name);
                                ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fAdd HYT Bot:" + name);
                                (new Thread((Runnable) (new Runnable() {
                                    public final void run() {
                                        try {
                                            Thread.sleep(5000L);
                                            LiquidBounce.INSTANCE.getFileManager().friendsConfig.removeFriend(name);
                                            ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fDeleted HYT Bot:" + name);
                                        } catch (InterruptedException interruptedexception) {
                                            interruptedexception.printStackTrace();
                                        }

                                    }
                                }))).start();
                            }
                        }

                        if (matcher2.find()) {
                            s = matcher2.group(1);
                            Intrinsics.checkExpressionValueIsNotNull(s, "matcher2.group(1)");
                            s1 = s;
                            flag = false;
                            if (s1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }

                            name = StringsKt.trim((CharSequence) s1).toString();
                            if (Intrinsics.areEqual(name, "") ^ true) {
                                LiquidBounce.INSTANCE.getFileManager().friendsConfig.addFriend(name);
                                ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fAdd HYT Bot:" + name);
                                (new Thread((Runnable) (new Runnable() {
                                    public final void run() {
                                        try {
                                            Thread.sleep(5000L);
                                            LiquidBounce.INSTANCE.getFileManager().friendsConfig.removeFriend(name);
                                            ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fDeleted HYT Bot:" + name);
                                        } catch (InterruptedException interruptedexception) {
                                            interruptedexception.printStackTrace();
                                        }

                                    }
                                }))).start();
                            }
                        }
                    }
                    break;

                case 46976214:
                    if ($this$unwrap$iv1.equals("16v16")) {
                        pattern = Pattern.compile("击败�? (.*?)!");
                        itextcomponent = ((SPacketChat) packet).getChatComponent();
                        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "packet.chatComponent");
                        matcher1 = pattern.matcher((CharSequence) itextcomponent.getUnformattedText());
                        pattern = Pattern.compile("玩家 (.*?)死了�?");
                        itextcomponent = ((SPacketChat) packet).getChatComponent();
                        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "packet.chatComponent");
                        matcher2 = pattern.matcher((CharSequence) itextcomponent.getUnformattedText());
                        if (matcher1.find()) {
                            s = matcher1.group(1);
                            Intrinsics.checkExpressionValueIsNotNull(s, "matcher.group(1)");
                            s1 = s;
                            flag = false;
                            if (s1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }

                            name = StringsKt.trim((CharSequence) s1).toString();
                            if (Intrinsics.areEqual(name, "") ^ true) {
                                LiquidBounce.INSTANCE.getFileManager().friendsConfig.addFriend(name);
                                ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fAdd HYT Bot:" + name);
                                (new Thread((Runnable) (new Runnable() {
                                    public final void run() {
                                        try {
                                            Thread.sleep(10000L);
                                            LiquidBounce.INSTANCE.getFileManager().friendsConfig.removeFriend(name);
                                            ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fDeleted HYT Bot:" + name);
                                        } catch (InterruptedException interruptedexception) {
                                            interruptedexception.printStackTrace();
                                        }

                                    }
                                }))).start();
                            }
                        }

                        if (matcher2.find()) {
                            s = matcher2.group(1);
                            Intrinsics.checkExpressionValueIsNotNull(s, "matcher2.group(1)");
                            s1 = s;
                            flag = false;
                            if (s1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }

                            name = StringsKt.trim((CharSequence) s1).toString();
                            if (Intrinsics.areEqual(name, "") ^ true) {
                                LiquidBounce.INSTANCE.getFileManager().friendsConfig.addFriend(name);
                                ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fAdd HYT Bot:" + name);
                                (new Thread((Runnable) (new Runnable() {
                                    public final void run() {
                                        try {
                                            Thread.sleep(10000L);
                                            ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fDeleted HYT Bot:" + name);
                                        } catch (InterruptedException interruptedexception) {
                                            interruptedexception.printStackTrace();
                                        }

                                    }
                                }))).start();
                            }
                        }
                    }
                    break;

                case 48636014:
                    if ($this$unwrap$iv1.equals("32/64")) {
                        pattern = Pattern.compile("�?死了 (.*?)\\(");
                        itextcomponent = ((SPacketChat) packet).getChatComponent();
                        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "packet.chatComponent");
                        matcher1 = pattern.matcher((CharSequence) itextcomponent.getUnformattedText());
                        pattern = Pattern.compile("起床战争>> (.*?) (\\((((.*?)死了!)))");
                        itextcomponent = ((SPacketChat) packet).getChatComponent();
                        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "packet.chatComponent");
                        matcher2 = pattern.matcher((CharSequence) itextcomponent.getUnformattedText());
                        if (matcher1.find()) {
                            s = matcher1.group(1);
                            Intrinsics.checkExpressionValueIsNotNull(s, "matcher.group(1)");
                            s1 = s;
                            flag = false;
                            if (s1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }

                            name = StringsKt.trim((CharSequence) s1).toString();
                            if (Intrinsics.areEqual(name, "") ^ true) {
                                LiquidBounce.INSTANCE.getFileManager().friendsConfig.addFriend(name);
                                ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fAdd HYT Bot:" + name);
                                (new Thread((Runnable) (new Runnable() {
                                    public final void run() {
                                        try {
                                            Thread.sleep(10000L);
                                            LiquidBounce.INSTANCE.getFileManager().friendsConfig.removeFriend(name);
                                            ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fDeleted HYT Bot:" + name);
                                        } catch (InterruptedException interruptedexception) {
                                            interruptedexception.printStackTrace();
                                        }

                                    }
                                }))).start();
                            }
                        }

                        if (matcher2.find()) {
                            s = matcher2.group(1);
                            Intrinsics.checkExpressionValueIsNotNull(s, "matcher2.group(1)");
                            s1 = s;
                            flag = false;
                            if (s1 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }

                            name = StringsKt.trim((CharSequence) s1).toString();
                            if (Intrinsics.areEqual(name, "") ^ true) {
                                LiquidBounce.INSTANCE.getFileManager().friendsConfig.addFriend(name);
                                ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fAdd HYT Bot:" + name);
                                (new Thread((Runnable) (new Runnable() {
                                    public final void run() {
                                        try {
                                            Thread.sleep(10000L);
                                            LiquidBounce.INSTANCE.getFileManager().friendsConfig.removeFriend(name);
                                            ClientUtils.displayChatMessage("§7[§8§6TGSense" + "§7]§fDeleted HYT Bot:" + name);
                                        } catch (InterruptedException interruptedexception) {
                                            interruptedexception.printStackTrace();
                                        }

                                    }
                                }))).start();
                            }
                        }
                    }
                }
            }

        }
    }

    @EventTarget
    public final void onWorld(@Nullable WorldEvent event) {
        this.clearAll();
    }

    private final void clearAll() {
        LiquidBounce.INSTANCE.getFileManager().friendsConfig.clearFriends();
    }

    @Nullable
    public String getTag() {
        return (String) this.mode.get();
    }
}
