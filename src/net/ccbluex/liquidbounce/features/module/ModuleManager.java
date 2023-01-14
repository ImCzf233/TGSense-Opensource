package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.Skid.Modules.Render.Compass;
import me.Skid.Modules.Render.JelloArraylist;
import me.Skid.Modules.Render.JelloTabGui;
import me.Skid.Modules.Render.Particles;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.color.Gident;
import net.ccbluex.liquidbounce.features.module.modules.color.GodLightSync;
import net.ccbluex.liquidbounce.features.module.modules.color.VisualColor;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aimbot;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiFireBall;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiKB;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoBow;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoHead;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoL;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoLeave;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoPot;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoSoup;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoWeapon;
import net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.FastBow;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitBox;
import net.ccbluex.liquidbounce.features.module.modules.combat.Ignite;
import net.ccbluex.liquidbounce.features.module.modules.combat.KAHelper;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura2;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura3;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.combat.SuperKnockback;
import net.ccbluex.liquidbounce.features.module.modules.combat.TNTBlock;
import net.ccbluex.liquidbounce.features.module.modules.combat.TeleportHit;
import net.ccbluex.liquidbounce.features.module.modules.combat.Trigger;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AntiHunger;
import net.ccbluex.liquidbounce.features.module.modules.exploit.BedGodMode;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Clip;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ConsoleSpammer;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Damage;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Disabler;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ForceUnicodeChat;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Ghost;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GhostHand;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GodMode;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ItemTeleport;
import net.ccbluex.liquidbounce.features.module.modules.exploit.KeepContainer;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Kick;
import net.ccbluex.liquidbounce.features.module.modules.exploit.MoreCarry;
import net.ccbluex.liquidbounce.features.module.modules.exploit.MultiActions;
import net.ccbluex.liquidbounce.features.module.modules.exploit.NoPitchLimit;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Phase;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PingSpoof;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Plugins;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PortalMenu;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ServerCrasher;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Teleport;
import net.ccbluex.liquidbounce.features.module.modules.exploit.VehicleOneHit;
import net.ccbluex.liquidbounce.features.module.modules.fun.Derp;
import net.ccbluex.liquidbounce.features.module.modules.fun.SkinDerp;
import net.ccbluex.liquidbounce.features.module.modules.hyt.HYTDisabler;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiVoid;
import net.ccbluex.liquidbounce.features.module.modules.misc.AtAllProvider;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoGG;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoLeos;
import net.ccbluex.liquidbounce.features.module.modules.misc.AutoPlay;
import net.ccbluex.liquidbounce.features.module.modules.misc.ComponentOnHover;
import net.ccbluex.liquidbounce.features.module.modules.misc.HytGetName;
import net.ccbluex.liquidbounce.features.module.modules.misc.HytSpeed;
import net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat;
import net.ccbluex.liquidbounce.features.module.modules.misc.MemoryFix;
import net.ccbluex.liquidbounce.features.module.modules.misc.MidClick;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.misc.NoFucker;
import net.ccbluex.liquidbounce.features.module.modules.misc.NoHurt;
import net.ccbluex.liquidbounce.features.module.modules.misc.NoRotateSet;
import net.ccbluex.liquidbounce.features.module.modules.misc.ResourcePackSpoof;
import net.ccbluex.liquidbounce.features.module.modules.misc.Spammer;
import net.ccbluex.liquidbounce.features.module.modules.misc.TGSenseSense;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.features.module.modules.misc.Title;
import net.ccbluex.liquidbounce.features.module.modules.movement.AirJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.AirLadder;
import net.ccbluex.liquidbounce.features.module.modules.movement.AutoWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.BlockWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.BufferSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.BugUp;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastClimb;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastStairs;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.movement.Freeze;
import net.ccbluex.liquidbounce.features.module.modules.movement.HighJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.IceSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.InventoryMove;
import net.ccbluex.liquidbounce.features.module.modules.movement.KeepSprint;
import net.ccbluex.liquidbounce.features.module.modules.movement.LadderJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.LongJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoClip;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoJumpDelay;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoWeb;
import net.ccbluex.liquidbounce.features.module.modules.movement.Parkour;
import net.ccbluex.liquidbounce.features.module.modules.movement.PerfectHorseJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.ReverseStep;
import net.ccbluex.liquidbounce.features.module.modules.movement.SafeWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.SlimeJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sneak;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.module.modules.movement.Step;
import net.ccbluex.liquidbounce.features.module.modules.movement.Strafe;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.features.module.modules.movement.WallClimb;
import net.ccbluex.liquidbounce.features.module.modules.movement.WaterSpeed;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiAFK;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiCactus;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoFish;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoLobby;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoRespawn;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoTool;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.player.Eagle;
import net.ccbluex.liquidbounce.features.module.modules.player.FastUse;
import net.ccbluex.liquidbounce.features.module.modules.player.Gapple;
import net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner;
import net.ccbluex.liquidbounce.features.module.modules.player.KeepAlive;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.player.PotionSaver;
import net.ccbluex.liquidbounce.features.module.modules.player.Reach;
import net.ccbluex.liquidbounce.features.module.modules.player.Regen;
import net.ccbluex.liquidbounce.features.module.modules.player.Zoot;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.AsianHat;
import net.ccbluex.liquidbounce.features.module.modules.render.AttackEffects;
import net.ccbluex.liquidbounce.features.module.modules.render.BetterHotBar;
import net.ccbluex.liquidbounce.features.module.modules.render.BlockESP;
import net.ccbluex.liquidbounce.features.module.modules.render.BlockOverlay;
import net.ccbluex.liquidbounce.features.module.modules.render.Breadcrumbs;
import net.ccbluex.liquidbounce.features.module.modules.render.CameraClip;
import net.ccbluex.liquidbounce.features.module.modules.render.Cape;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.features.module.modules.render.Crosshair;
import net.ccbluex.liquidbounce.features.module.modules.render.DMGParticle;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.features.module.modules.render.EnchantEffect;
import net.ccbluex.liquidbounce.features.module.modules.render.FollowTargetHud;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.features.module.modules.render.Fullbright;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.features.module.modules.render.Health;
import net.ccbluex.liquidbounce.features.module.modules.render.ItemESP;
import net.ccbluex.liquidbounce.features.module.modules.render.ItemShow;
import net.ccbluex.liquidbounce.features.module.modules.render.KeyBindManager;
import net.ccbluex.liquidbounce.features.module.modules.render.ModuleInfoS;
import net.ccbluex.liquidbounce.features.module.modules.render.MotionBlur;
import net.ccbluex.liquidbounce.features.module.modules.render.MusicPlayer;
import net.ccbluex.liquidbounce.features.module.modules.render.NameTag;
import net.ccbluex.liquidbounce.features.module.modules.render.NewClickGUI;
import net.ccbluex.liquidbounce.features.module.modules.render.NewLogo;
import net.ccbluex.liquidbounce.features.module.modules.render.NoBob;
import net.ccbluex.liquidbounce.features.module.modules.render.NoFOV;
import net.ccbluex.liquidbounce.features.module.modules.render.NoHurtCam;
import net.ccbluex.liquidbounce.features.module.modules.render.NoScoreboard;
import net.ccbluex.liquidbounce.features.module.modules.render.NoSwing;
import net.ccbluex.liquidbounce.features.module.modules.render.OldHitting;
import net.ccbluex.liquidbounce.features.module.modules.render.PlayerEdit;
import net.ccbluex.liquidbounce.features.module.modules.render.PlayerInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.PotionRender;
import net.ccbluex.liquidbounce.features.module.modules.render.Projectiles;
import net.ccbluex.liquidbounce.features.module.modules.render.ProphuntESP;
import net.ccbluex.liquidbounce.features.module.modules.render.Rotations;
import net.ccbluex.liquidbounce.features.module.modules.render.SessionInfos;
import net.ccbluex.liquidbounce.features.module.modules.render.StorageESP;
import net.ccbluex.liquidbounce.features.module.modules.render.SwingAnimation;
import net.ccbluex.liquidbounce.features.module.modules.render.TNTESP;
import net.ccbluex.liquidbounce.features.module.modules.render.Tracers;
import net.ccbluex.liquidbounce.features.module.modules.render.Trail;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.features.module.modules.world.AutoBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer;
import net.ccbluex.liquidbounce.features.module.modules.world.CivBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.FastBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.FastPlace;
import net.ccbluex.liquidbounce.features.module.modules.world.Fucker;
import net.ccbluex.liquidbounce.features.module.modules.world.Liquids;
import net.ccbluex.liquidbounce.features.module.modules.world.NoSlowBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.Nuker;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.world.Timer;
import net.ccbluex.liquidbounce.features.module.modules.world.Tower;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u001bJ\u0015\u0010\u001c\u001a\u00020\u00062\n\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0086\u0002J\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00060\u001f2\u0006\u0010 \u001a\u00020\u0013J\u0012\u0010!\u001a\u00020\u00062\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030\u0005J\u0012\u0010!\u001a\u0004\u0018\u00010\u00062\b\u0010#\u001a\u0004\u0018\u00010$J\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00060&2\u0006\u0010\'\u001a\u00020(J\b\u0010)\u001a\u00020\rH\u0016J\u0010\u0010*\u001a\u00020\u00192\u0006\u0010+\u001a\u00020,H\u0003J\u0018\u0010-\u001a\u00020\u00192\u000e\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0002J\u000e\u0010-\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0006J\u0006\u0010.\u001a\u00020\u0019J1\u0010.\u001a\u00020\u00192\"\u0010\b\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050/\"\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0007¢\u0006\u0002\u00100J\u000e\u00101\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0006R2\u0010\u0003\u001a&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u00062"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "moduleClassMap", "Ljava/util/HashMap;", "Ljava/lang/Class;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "Lkotlin/collections/HashMap;", "modules", "Ljava/util/TreeSet;", "getModules", "()Ljava/util/TreeSet;", "shouldNotify", "", "getShouldNotify", "()Z", "setShouldNotify", "(Z)V", "toggleSoundMode", "", "getToggleSoundMode", "()I", "setToggleSoundMode", "(I)V", "generateCommand", "", "module", "generateCommand$LiquidBounce", "get", "clazz", "getKeyBind", "", "key", "getModule", "moduleClass", "moduleName", "", "getModuleInCategory", "Ljava/util/ArrayList;", "Category", "Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "handleEvents", "onKey", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "registerModule", "registerModules", "", "([Ljava/lang/Class;)V", "unregisterModule", "LiquidBounce"}
)
public final class ModuleManager implements Listenable {

    @NotNull
    private final TreeSet modules;
    private final HashMap moduleClassMap;
    private boolean shouldNotify;
    private int toggleSoundMode;

    @NotNull
    public final TreeSet getModules() {
        return this.modules;
    }

    public final boolean getShouldNotify() {
        return this.shouldNotify;
    }

    public final void setShouldNotify(boolean <set-?>) {
        this.shouldNotify = <set-?>;
    }

    public final int getToggleSoundMode() {
        return this.toggleSoundMode;
    }

    public final void setToggleSoundMode(int <set-?>) {
        this.toggleSoundMode = <set-?>;
    }

    @NotNull
    public final List getKeyBind(int key) {
        Iterable $this$filter$iv = (Iterable) this.modules;
        boolean $i$f$filter = false;
        Collection destination$iv$iv = (Collection) (new ArrayList());
        boolean $i$f$filterTo = false;
        Iterator iterator = $this$filter$iv.iterator();

        while (iterator.hasNext()) {
            Object element$iv$iv = iterator.next();
            Module it = (Module) element$iv$iv;
            boolean $i$a$-filter-ModuleManager$getKeyBind$1 = false;

            if (it.getKeyBind() == key) {
                destination$iv$iv.add(element$iv$iv);
            }
        }

        return (List) destination$iv$iv;
    }

    public final void registerModules() {
        ClientUtils.getLogger().info("[ModuleManager] Loading modules...");
        this.registerModules(new Class[] { AutoGG.class, PlayerInfo.class, NoHurt.class, KAHelper.class, VisualColor.class, ModuleInfoS.class, HYTDisabler.class, AsianHat.class, AntiKB.class, AutoLobby.class, Disabler.class, HYTDisabler.class, Gident.class, TGSenseSense.class, KeepSprint.class, SessionInfos.class, MusicPlayer.class, GodLightSync.class, FollowTargetHud.class, KeyBindManager.class, NewClickGUI.class, BetterHotBar.class, MemoryFix.class, AntiVoid.class, DMGParticle.class, AttackEffects.class, EnchantEffect.class, Crosshair.class, Health.class, ColorMixer.class, AutoPlay.class, AutoArmor.class, AutoBow.class, AutoLeave.class, PotionRender.class, AutoPot.class, HytSpeed.class, Trail.class, MotionBlur.class, AutoSoup.class, AutoWeapon.class, AutoL.class, BowAimbot.class, Criticals.class, KillAura.class, Trigger.class, Velocity.class, Fly.class, ClickGUI.class, Animations.class, HighJump.class, InventoryMove.class, NoSlow.class, LiquidWalk.class, SafeWalk.class, WallClimb.class, Strafe.class, Sprint.class, Teams.class, NoRotateSet.class, ChestStealer.class, Scaffold.class, NewLogo.class, CivBreak.class, Tower.class, NameTag.class, FastBreak.class, FastPlace.class, ESP.class, Speed.class, Tracers.class, TargetStrafe.class, FastUse.class, Teleport.class, Fullbright.class, ItemESP.class, StorageESP.class, Projectiles.class, AutoHead.class, NoClip.class, Nuker.class, PingSpoof.class, FastClimb.class, Step.class, NoFucker.class, AutoRespawn.class, AutoTool.class, AntiFireBall.class, NoWeb.class, Spammer.class, IceSpeed.class, Zoot.class, HytGetName.class, AutoLeos.class, Gapple.class, Regen.class, NoFall.class, Blink.class, NameProtect.class, NoHurtCam.class, Ghost.class, MidClick.class, XRay.class, Timer.class, Sneak.class, SkinDerp.class, GhostHand.class, AutoWalk.class, AutoBreak.class, FreeCam.class, Aimbot.class, Eagle.class, HitBox.class, AntiCactus.class, Plugins.class, AntiHunger.class, ConsoleSpammer.class, LongJump.class, Parkour.class, LadderJump.class, FastBow.class, MultiActions.class, AirJump.class, AutoClicker.class, NoBob.class, BlockOverlay.class, NoFriends.class, BlockESP.class, Chams.class, Clip.class, Phase.class, ServerCrasher.class, NoFOV.class, FastStairs.class, SwingAnimation.class, Derp.class, ReverseStep.class, TNTBlock.class, InventoryCleaner.class, TrueSight.class, LiquidChat.class, AntiBlind.class, NoSwing.class, BedGodMode.class, BugUp.class, Breadcrumbs.class, AbortBreaking.class, PotionSaver.class, CameraClip.class, WaterSpeed.class, Ignite.class, SlimeJump.class, MoreCarry.class, NoPitchLimit.class, Kick.class, Liquids.class, AtAllProvider.class, PlayerEdit.class, AirLadder.class, GodMode.class, TeleportHit.class, ForceUnicodeChat.class, ItemTeleport.class, BufferSpeed.class, SuperKnockback.class, ProphuntESP.class, AutoFish.class, Damage.class, Freeze.class, KeepContainer.class, VehicleOneHit.class, Reach.class, Rotations.class, NoJumpDelay.class, BlockWalk.class, AntiAFK.class, PerfectHorseJump.class, HUD.class, TNTESP.class, ComponentOnHover.class, KeepAlive.class, ResourcePackSpoof.class, OldHitting.class, Cape.class, Title.class, NoSlowBreak.class, PortalMenu.class, Particles.class, JelloTabGui.class, JelloArraylist.class, Compass.class, KillAura2.class, KillAura3.class, ItemShow.class});
        this.registerModule((Module) NoScoreboard.INSTANCE);
        this.registerModule((Module) Fucker.INSTANCE);
        this.registerModule((Module) ChestAura.INSTANCE);
        this.registerModule((Module) AntiBot.INSTANCE);
        ClientUtils.getLogger().info("[ModuleManager] Loaded " + this.modules.size() + " modules.");
    }

    public final void registerModule(@NotNull Module module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        if (module.isSupported()) {
            Collection collection = (Collection) this.modules;
            boolean flag = false;

            collection.add(module);
            ((Map) this.moduleClassMap).put(module.getClass(), module);
            this.generateCommand$LiquidBounce(module);
            LiquidBounce.INSTANCE.getEventManager().registerListener((Listenable) module);
        }
    }

    private final void registerModule(Class moduleClass) {
        try {
            Object object = moduleClass.newInstance();

            Intrinsics.checkExpressionValueIsNotNull(object, "moduleClass.newInstance()");
            this.registerModule((Module) object);
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("Failed to load module: " + moduleClass.getName() + " (" + throwable.getClass().getName() + ": " + throwable.getMessage() + ')');
        }

    }

    @NotNull
    public final ArrayList getModuleInCategory(@NotNull ModuleCategory Category) {
        Intrinsics.checkParameterIsNotNull(Category, "Category");
        ArrayList moduleInCategory = new ArrayList();
        Iterator iterator = this.modules.iterator();

        while (iterator.hasNext()) {
            Module i = (Module) iterator.next();

            if (i.getCategory() == Category) {
                moduleInCategory.add(i);
            }
        }

        return moduleInCategory;
    }

    @SafeVarargs
    public final void registerModules(@NotNull Class... modules) {
        Intrinsics.checkParameterIsNotNull(modules, "modules");
        ModuleManager modulemanager = this;
        boolean $i$f$forEach = false;
        Class[] aclass = modules;
        int i = modules.length;

        for (int j = 0; j < i; ++j) {
            Class element$iv = aclass[j];
            boolean $i$a$-unknown-ModuleManager$registerModules$1 = false;

            ((ModuleManager) modulemanager).registerModule(element$iv);
        }

    }

    public final void unregisterModule(@NotNull Module module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        this.modules.remove(module);
        this.moduleClassMap.remove(module.getClass());
        LiquidBounce.INSTANCE.getEventManager().unregisterListener((Listenable) module);
    }

    public final void generateCommand$LiquidBounce(@NotNull Module module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        List values = module.getValues();

        if (!values.isEmpty()) {
            LiquidBounce.INSTANCE.getCommandManager().registerCommand((Command) (new ModuleCommand(module, values)));
        }
    }

    @NotNull
    public final Module getModule(@NotNull Class moduleClass) {
        Intrinsics.checkParameterIsNotNull(moduleClass, "moduleClass");
        Object object = this.moduleClassMap.get(moduleClass);

        if (object == null) {
            Intrinsics.throwNpe();
        }

        Intrinsics.checkExpressionValueIsNotNull(object, "moduleClassMap[moduleClass]!!");
        return (Module) object;
    }

    @NotNull
    public final Module get(@NotNull Class clazz) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return this.getModule(clazz);
    }

    @Nullable
    public final Module getModule(@Nullable String moduleName) {
        Iterable iterable = (Iterable) this.modules;
        boolean flag = false;
        boolean flag1 = false;
        Iterator iterator = iterable.iterator();

        Object object;

        while (true) {
            if (iterator.hasNext()) {
                Object object1 = iterator.next();
                Module it = (Module) object1;
                boolean $i$a$-find-ModuleManager$getModule$1 = false;

                if (!StringsKt.equals(it.getName(), moduleName, true)) {
                    continue;
                }

                object = object1;
                break;
            }

            object = null;
            break;
        }

        return (Module) object;
    }

    @EventTarget
    private final void onKey(KeyEvent event) {
        Iterable $this$forEach$iv = (Iterable) this.modules;
        boolean $i$f$forEach = false;
        Collection element$iv = (Collection) (new ArrayList());
        boolean it = false;
        Iterator $i$a$-forEach-ModuleManager$onKey$2 = $this$forEach$iv.iterator();

        while ($i$a$-forEach-ModuleManager$onKey$2.hasNext()) {
            Object element$iv$iv = $i$a$-forEach-ModuleManager$onKey$2.next();
            Module it1 = (Module) element$iv$iv;
            boolean $i$a$-filter-ModuleManager$onKey$1 = false;

            if (it1.getKeyBind() == event.getKey()) {
                element$iv.add(element$iv$iv);
            }
        }

        $this$forEach$iv = (Iterable) ((List) element$iv);
        $i$f$forEach = false;
        Iterator $this$filterTo$iv$iv = $this$forEach$iv.iterator();

        while ($this$filterTo$iv$iv.hasNext()) {
            Object element$iv1 = $this$filterTo$iv$iv.next();
            Module it2 = (Module) element$iv1;
            boolean $i$a$-forEach-ModuleManager$onKey$21 = false;

            it2.toggle();
        }

    }

    public boolean handleEvents() {
        return true;
    }

    public ModuleManager() {
        this.modules = new TreeSet((Comparator) null.INSTANCE);
        boolean flag = false;
        HashMap hashmap = new HashMap();

        this.moduleClassMap = hashmap;
        LiquidBounce.INSTANCE.getEventManager().registerListener((Listenable) this);
    }
}
