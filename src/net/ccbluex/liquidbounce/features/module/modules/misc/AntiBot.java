package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketAnimation;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.NetworkExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "BotKiller",
    description = "Prevents KillAura from attacking AntiCheat bots.",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010)\u001a\u00020*H\u0002J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0007J\u0010\u0010/\u001a\u00020*2\u0006\u00100\u001a\u000201H\u0007J\b\u00102\u001a\u00020*H\u0016J\u0010\u00103\u001a\u00020*2\u0006\u00104\u001a\u000205H\u0007J\u0012\u00106\u001a\u00020*2\b\u00104\u001a\u0004\u0018\u000107H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010$\u001a\u0004\u0018\u00010%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\'R\u000e\u0010(\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/AntiBot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "air", "", "", "airValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "allwaysInRadiusValue", "allwaysRadiusValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "armorValue", "colorValue", "derpValue", "duplicateInTabValue", "duplicateInWorldValue", "entityIDValue", "ground", "groundValue", "healthValue", "hitted", "invalidGround", "", "invalidGroundValue", "invisible", "livingTimeTicksValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "livingTimeValue", "needHitValue", "notAlwaysInRadius", "pingValue", "swing", "swingValue", "tabModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tabValue", "tag", "", "getTag", "()Ljava/lang/String;", "wasInvisibleValue", "clearAll", "", "isBot", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "onAttack", "e", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"}
)
public final class AntiBot extends Module {

    private static final BoolValue tabValue;
    private static final ListValue tabModeValue;
    private static final BoolValue entityIDValue;
    private static final BoolValue colorValue;
    private static final BoolValue livingTimeValue;
    private static final IntegerValue livingTimeTicksValue;
    private static final BoolValue groundValue;
    private static final BoolValue airValue;
    private static final BoolValue invalidGroundValue;
    private static final BoolValue swingValue;
    private static final BoolValue healthValue;
    private static final BoolValue derpValue;
    private static final BoolValue wasInvisibleValue;
    private static final BoolValue armorValue;
    private static final BoolValue pingValue;
    private static final BoolValue needHitValue;
    private static final BoolValue duplicateInWorldValue;
    private static final BoolValue duplicateInTabValue;
    private static final BoolValue allwaysInRadiusValue;
    private static final FloatValue allwaysRadiusValue;
    private static final List ground;
    private static final List air;
    private static final Map invalidGround;
    private static final List swing;
    private static final List invisible;
    private static final List hitted;
    private static final List notAlwaysInRadius;
    public static final AntiBot INSTANCE;

    @JvmStatic
    public static final boolean isBot(@NotNull IEntityLivingBase entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        if (!MinecraftInstance.classProvider.isEntityPlayer(entity)) {
            return false;
        } else if (!AntiBot.INSTANCE.getState()) {
            return false;
        } else {
            IIChatComponent iichatcomponent;

            if (((Boolean) AntiBot.colorValue.get()).booleanValue()) {
                iichatcomponent = entity.getDisplayName();
                if (iichatcomponent == null) {
                    Intrinsics.throwNpe();
                }

                if (!StringsKt.contains$default((CharSequence) StringsKt.replace$default(iichatcomponent.getFormattedText(), "§r", "", false, 4, (Object) null), (CharSequence) "§", false, 2, (Object) null)) {
                    return true;
                }
            }

            if (((Boolean) AntiBot.livingTimeValue.get()).booleanValue() && entity.getTicksExisted() < ((Number) AntiBot.livingTimeTicksValue.get()).intValue()) {
                return true;
            } else if (((Boolean) AntiBot.groundValue.get()).booleanValue() && !AntiBot.ground.contains(Integer.valueOf(entity.getEntityId()))) {
                return true;
            } else if (((Boolean) AntiBot.airValue.get()).booleanValue() && !AntiBot.air.contains(Integer.valueOf(entity.getEntityId()))) {
                return true;
            } else if (((Boolean) AntiBot.swingValue.get()).booleanValue() && !AntiBot.swing.contains(Integer.valueOf(entity.getEntityId()))) {
                return true;
            } else if (((Boolean) AntiBot.healthValue.get()).booleanValue() && entity.getHealth() > 20.0F) {
                return true;
            } else if (((Boolean) AntiBot.entityIDValue.get()).booleanValue() && (entity.getEntityId() >= 1000000000 || entity.getEntityId() <= -1)) {
                return true;
            } else if (((Boolean) AntiBot.derpValue.get()).booleanValue() && (entity.getRotationPitch() > 90.0F || entity.getRotationPitch() < -90.0F)) {
                return true;
            } else if (((Boolean) AntiBot.wasInvisibleValue.get()).booleanValue() && AntiBot.invisible.contains(Integer.valueOf(entity.getEntityId()))) {
                return true;
            } else {
                if (((Boolean) AntiBot.armorValue.get()).booleanValue()) {
                    IEntityPlayer $this$filter$iv = entity.asEntityPlayer();

                    if ($this$filter$iv.getInventory().getArmorInventory().get(0) == null && $this$filter$iv.getInventory().getArmorInventory().get(1) == null && $this$filter$iv.getInventory().getArmorInventory().get(2) == null && $this$filter$iv.getInventory().getArmorInventory().get(3) == null) {
                        return true;
                    }
                }

                if (((Boolean) AntiBot.pingValue.get()).booleanValue()) {
                    INetworkPlayerInfo inetworkplayerinfo = MinecraftInstance.mc.getNetHandler().getPlayerInfo(entity.asEntityPlayer().getUniqueID());

                    if (inetworkplayerinfo != null) {
                        if (inetworkplayerinfo.getResponseTime() == 0) {
                            return true;
                        }
                    }
                }

                if (((Boolean) AntiBot.needHitValue.get()).booleanValue() && !AntiBot.hitted.contains(Integer.valueOf(entity.getEntityId()))) {
                    return true;
                } else if (((Boolean) AntiBot.invalidGroundValue.get()).booleanValue() && ((Number) AntiBot.invalidGround.getOrDefault(Integer.valueOf(entity.getEntityId()), Integer.valueOf(0))).intValue() >= 10) {
                    return true;
                } else {
                    String s;

                    if (((Boolean) AntiBot.tabValue.get()).booleanValue()) {
                        boolean $this$filter$iv1 = StringsKt.equals((String) AntiBot.tabModeValue.get(), "Equals", true);

                        iichatcomponent = entity.getDisplayName();
                        if (iichatcomponent == null) {
                            Intrinsics.throwNpe();
                        }

                        String $i$f$filter = ColorUtils.stripColor(iichatcomponent.getFormattedText());

                        if ($i$f$filter != null) {
                            Iterator destination$iv$iv1 = MinecraftInstance.mc.getNetHandler().getPlayerInfoMap().iterator();

                            while (destination$iv$iv1.hasNext()) {
                                INetworkPlayerInfo $this$filterTo$iv$iv = (INetworkPlayerInfo) destination$iv$iv1.next();

                                s = ColorUtils.stripColor(NetworkExtensionKt.getFullName($this$filterTo$iv$iv));
                                if (s != null) {
                                    String $i$f$filterTo1 = s;

                                    if ($this$filter$iv1 ? Intrinsics.areEqual($i$f$filter, $i$f$filterTo1) : StringsKt.contains$default((CharSequence) $i$f$filter, (CharSequence) $i$f$filterTo1, false, 2, (Object) null)) {
                                        return false;
                                    }
                                }
                            }

                            return true;
                        }
                    }

                    Collection destination$iv$iv;
                    boolean $i$f$filterTo;
                    Iterator iterator;
                    Object element$iv$iv;
                    boolean $i$a$-filter-AntiBot$isBot$2;
                    Iterable $this$filter$iv2;
                    Collection $this$filter$iv3;
                    boolean $i$f$filter1;

                    if (((Boolean) AntiBot.duplicateInWorldValue.get()).booleanValue()) {
                        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                        if (iworldclient == null) {
                            Intrinsics.throwNpe();
                        }

                        $this$filter$iv2 = (Iterable) iworldclient.getLoadedEntityList();
                        $i$f$filter1 = false;
                        destination$iv$iv = (Collection) (new ArrayList());
                        $i$f$filterTo = false;
                        iterator = $this$filter$iv2.iterator();

                        while (iterator.hasNext()) {
                            element$iv$iv = iterator.next();
                            IEntity it = (IEntity) element$iv$iv;

                            $i$a$-filter-AntiBot$isBot$2 = false;
                            if (MinecraftInstance.classProvider.isEntityPlayer(it) && Intrinsics.areEqual(it.asEntityPlayer().getDisplayNameString(), it.asEntityPlayer().getDisplayNameString())) {
                                destination$iv$iv.add(element$iv$iv);
                            }
                        }

                        $this$filter$iv3 = (Collection) ((List) destination$iv$iv);
                        $i$f$filter1 = false;
                        if ($this$filter$iv3.size() > 1) {
                            return true;
                        }
                    }

                    if (((Boolean) AntiBot.duplicateInTabValue.get()).booleanValue()) {
                        $this$filter$iv2 = (Iterable) MinecraftInstance.mc.getNetHandler().getPlayerInfoMap();
                        $i$f$filter1 = false;
                        destination$iv$iv = (Collection) (new ArrayList());
                        $i$f$filterTo = false;
                        iterator = $this$filter$iv2.iterator();

                        while (iterator.hasNext()) {
                            element$iv$iv = iterator.next();
                            INetworkPlayerInfo it1 = (INetworkPlayerInfo) element$iv$iv;

                            $i$a$-filter-AntiBot$isBot$2 = false;
                            if (Intrinsics.areEqual(entity.getName(), ColorUtils.stripColor(NetworkExtensionKt.getFullName(it1)))) {
                                destination$iv$iv.add(element$iv$iv);
                            }
                        }

                        $this$filter$iv3 = (Collection) ((List) destination$iv$iv);
                        $i$f$filter1 = false;
                        if ($this$filter$iv3.size() > 1) {
                            return true;
                        }
                    }

                    if (((Boolean) AntiBot.allwaysInRadiusValue.get()).booleanValue() && !AntiBot.notAlwaysInRadius.contains(Integer.valueOf(entity.getEntityId()))) {
                        return true;
                    } else {
                        s = entity.getName();
                        if (s == null) {
                            Intrinsics.throwNpe();
                        }

                        CharSequence $this$filter$iv4 = (CharSequence) s;

                        $i$f$filter1 = false;
                        boolean flag;

                        if ($this$filter$iv4.length() != 0) {
                            s = entity.getName();
                            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (!Intrinsics.areEqual(s, ientityplayersp.getName())) {
                                flag = false;
                                return flag;
                            }
                        }

                        flag = true;
                        return flag;
                    }
                }
            }
        }
    }

    public void onDisable() {
        this.clearAll();
        super.onDisable();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getThePlayer() != null && MinecraftInstance.mc.getTheWorld() != null) {
            IPacket packet = event.getPacket();
            IEntity entity;

            if (MinecraftInstance.classProvider.isSPacketEntity(packet)) {
                ISPacketEntity packetAnimation = packet.asSPacketEntity();
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                entity = packetAnimation.getEntity((IWorld) iworldclient);
                if (MinecraftInstance.classProvider.isEntityPlayer(entity) && entity != null) {
                    if (packetAnimation.getOnGround() && !AntiBot.ground.contains(Integer.valueOf(entity.getEntityId()))) {
                        AntiBot.ground.add(Integer.valueOf(entity.getEntityId()));
                    }

                    if (!packetAnimation.getOnGround() && !AntiBot.air.contains(Integer.valueOf(entity.getEntityId()))) {
                        AntiBot.air.add(Integer.valueOf(entity.getEntityId()));
                    }

                    if (packetAnimation.getOnGround()) {
                        if (entity.getPrevPosY() != entity.getPosY()) {
                            AntiBot.invalidGround.put(Integer.valueOf(entity.getEntityId()), Integer.valueOf(((Number) AntiBot.invalidGround.getOrDefault(Integer.valueOf(entity.getEntityId()), Integer.valueOf(0))).intValue() + 1));
                        }
                    } else {
                        int currentVL = ((Number) AntiBot.invalidGround.getOrDefault(Integer.valueOf(entity.getEntityId()), Integer.valueOf(0))).intValue() / 2;

                        if (currentVL <= 0) {
                            AntiBot.invalidGround.remove(Integer.valueOf(entity.getEntityId()));
                        } else {
                            AntiBot.invalidGround.put(Integer.valueOf(entity.getEntityId()), Integer.valueOf(currentVL));
                        }
                    }

                    if (entity.isInvisible() && !AntiBot.invisible.contains(Integer.valueOf(entity.getEntityId()))) {
                        AntiBot.invisible.add(Integer.valueOf(entity.getEntityId()));
                    }

                    if (!AntiBot.notAlwaysInRadius.contains(Integer.valueOf(entity.getEntityId()))) {
                        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getDistanceToEntity(entity) > ((Number) AntiBot.allwaysRadiusValue.get()).floatValue()) {
                            AntiBot.notAlwaysInRadius.add(Integer.valueOf(entity.getEntityId()));
                        }
                    }
                }
            }

            if (MinecraftInstance.classProvider.isSPacketAnimation(packet)) {
                ISPacketAnimation packetAnimation1 = packet.asSPacketAnimation();
                IWorldClient iworldclient1 = MinecraftInstance.mc.getTheWorld();

                if (iworldclient1 == null) {
                    Intrinsics.throwNpe();
                }

                entity = iworldclient1.getEntityByID(packetAnimation1.getEntityID());
                if (entity != null && MinecraftInstance.classProvider.isEntityLivingBase(entity) && packetAnimation1.getAnimationType() == 0 && !AntiBot.swing.contains(Integer.valueOf(entity.getEntityId()))) {
                    AntiBot.swing.add(Integer.valueOf(entity.getEntityId()));
                }
            }

        }
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        IEntity entity = e.getTargetEntity();

        if (entity != null && MinecraftInstance.classProvider.isEntityLivingBase(entity) && !AntiBot.hitted.contains(Integer.valueOf(entity.getEntityId()))) {
            AntiBot.hitted.add(Integer.valueOf(entity.getEntityId()));
        }

    }

    @EventTarget
    public final void onWorld(@Nullable WorldEvent event) {
        this.clearAll();
    }

    private final void clearAll() {
        AntiBot.hitted.clear();
        AntiBot.swing.clear();
        AntiBot.ground.clear();
        AntiBot.invalidGround.clear();
        AntiBot.invisible.clear();
        AntiBot.notAlwaysInRadius.clear();
    }

    @Nullable
    public String getTag() {
        return (String) AntiBot.tabModeValue.get();
    }

    static {
        AntiBot antibot = new AntiBot();

        INSTANCE = antibot;
        tabValue = new BoolValue("Tab", false);
        tabModeValue = new ListValue("TabMode", new String[] { "Equals", "Contains", "HuaYuTing"}, "HuaYuTing");
        entityIDValue = new BoolValue("EntityID", false);
        colorValue = new BoolValue("Color", true);
        livingTimeValue = new BoolValue("LivingTime", true);
        livingTimeTicksValue = new IntegerValue("LivingTimeTicks", 40, 1, 200);
        groundValue = new BoolValue("Ground", true);
        airValue = new BoolValue("Air", false);
        invalidGroundValue = new BoolValue("InvalidGround", false);
        swingValue = new BoolValue("Swing", false);
        healthValue = new BoolValue("Health", true);
        derpValue = new BoolValue("Derp", false);
        wasInvisibleValue = new BoolValue("WasInvisible", false);
        armorValue = new BoolValue("Armor", true);
        pingValue = new BoolValue("Ping", false);
        needHitValue = new BoolValue("NeedHit", false);
        duplicateInWorldValue = new BoolValue("DuplicateInWorld", false);
        duplicateInTabValue = new BoolValue("DuplicateInTab", false);
        allwaysInRadiusValue = new BoolValue("AlwaysInRadius", false);
        allwaysRadiusValue = new FloatValue("AlwaysInRadiusBlocks", 20.0F, 5.0F, 30.0F);
        boolean flag = false;

        ground = (List) (new ArrayList());
        flag = false;
        air = (List) (new ArrayList());
        flag = false;
        invalidGround = (Map) (new LinkedHashMap());
        flag = false;
        swing = (List) (new ArrayList());
        flag = false;
        invisible = (List) (new ArrayList());
        flag = false;
        hitted = (List) (new ArrayList());
        flag = false;
        notAlwaysInRadius = (List) (new ArrayList());
    }
}
