package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketChat;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "NoHurt",
    description = "faq",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\b\u0010\f\u001a\u00020\u0007H\u0016J\u0010\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0012\u0010\u0010\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u0011H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/NoHurt;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "playerName", "", "", "clearAll", "", "isBot", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "onDisable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"}
)
public final class NoHurt extends Module {

    private final List playerName = (List) (new ArrayList());

    public void onDisable() {
        this.clearAll();
        super.onDisable();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getThePlayer() != null && MinecraftInstance.mc.getTheWorld() != null) {
            IPacket packet = event.getPacket();

            if (MinecraftInstance.classProvider.isSPacketChat(packet)) {
                ISPacketChat chatMessage = packet.asSPacketChat();
                Matcher matcher = Pattern.compile("�?死了 (.*?)\\(").matcher((CharSequence) chatMessage.getGetChat().getUnformattedText());
                Matcher matcher2 = Pattern.compile("> (.*?)\\(").matcher((CharSequence) chatMessage.getGetChat().getUnformattedText());
                final String name;

                if (matcher.find()) {
                    name = matcher.group(1);
                    if (Intrinsics.areEqual(name, "") ^ true && !this.playerName.contains(name)) {
                        List list = this.playerName;

                        Intrinsics.checkExpressionValueIsNotNull(name, "name");
                        list.add(name);
                        (new Thread((Runnable) (new Runnable() {
                            public final void run() {
                                try {
                                    Thread.sleep(6000L);
                                    NoHurt.this.playerName.remove(name);
                                } catch (InterruptedException interruptedexception) {
                                    interruptedexception.printStackTrace();
                                }

                            }
                        }))).start();
                    }
                }

                if (matcher2.find()) {
                    name = matcher2.group(1);
                    if (Intrinsics.areEqual(name, "") ^ true) {
                        Intrinsics.checkExpressionValueIsNotNull(name, "name");
                        if (!StringsKt.contains$default((CharSequence) name, (CharSequence) "[", false, 2, (Object) null) && !this.playerName.contains(name)) {
                            (new Thread((Runnable) (new Runnable() {
                                public final void run() {
                                    try {
                                        Thread.sleep(6000L);
                                        NoHurt.this.playerName.remove(name);
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

    @EventTarget
    public final void onWorld(@Nullable WorldEvent event) {
        this.clearAll();
    }

    private final void clearAll() {
        this.playerName.clear();
    }

    public final boolean isBot(@NotNull IEntityLivingBase entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        if (!MinecraftInstance.classProvider.isEntityPlayer(entity)) {
            return false;
        } else {
            NoHurt antiBot = (NoHurt) LiquidBounce.INSTANCE.getModuleManager().getModule(NoHurt.class);

            if (antiBot != null && antiBot.getState()) {
                if (CollectionsKt.contains((Iterable) antiBot.playerName, entity.getName())) {
                    return true;
                } else {
                    String s = entity.getName();

                    if (s == null) {
                        Intrinsics.throwNpe();
                    }

                    CharSequence charsequence = (CharSequence) s;
                    boolean flag = false;
                    boolean flag1;

                    if (charsequence.length() != 0) {
                        s = entity.getName();
                        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (!Intrinsics.areEqual(s, ientityplayersp.getName())) {
                            flag1 = false;
                            return flag1;
                        }
                    }

                    flag1 = true;
                    return flag1;
                }
            } else {
                return false;
            }
        }
    }
}
