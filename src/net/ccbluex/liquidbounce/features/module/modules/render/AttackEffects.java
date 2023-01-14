package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "AttackEffects",
    description = "Rise.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u0010\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001bH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u001d"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/AttackEffects;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "amount", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getAmount", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "lightingSoundValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "sound", "target", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getTarget", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "setTarget", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)V", "target2", "Lnet/minecraft/entity/EntityLivingBase;", "getTarget2", "()Lnet/minecraft/entity/EntityLivingBase;", "setTarget2", "(Lnet/minecraft/entity/EntityLivingBase;)V", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "Companion", "LiquidBounce"}
)
public final class AttackEffects extends Module {

    @NotNull
    private final IntegerValue amount = new IntegerValue("Amount", 5, 1, 20);
    private final BoolValue sound = new BoolValue("Sound", true);
    private final BoolValue lightingSoundValue = new BoolValue("LightingSound", true);
    @Nullable
    private IEntityLivingBase target;
    @Nullable
    private EntityLivingBase target2;
    @NotNull
    private static final ListValue mode = new ListValue("Mode", new String[] { "Blood", "Lighting", "Fire", "Heart", "Water"}, "Blood");
    public static final AttackEffects.Companion Companion = new AttackEffects.Companion((DefaultConstructorMarker) null);

    @NotNull
    public final IntegerValue getAmount() {
        return this.amount;
    }

    @Nullable
    public final IEntityLivingBase getTarget() {
        return this.target;
    }

    public final void setTarget(@Nullable IEntityLivingBase <set-?>) {
        this.target = <set-?>;
    }

    @Nullable
    public final EntityLivingBase getTarget2() {
        return this.target2;
    }

    public final void setTarget2(@Nullable EntityLivingBase <set-?>) {
        this.target2 = <set-?>;
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (event.getTargetEntity() instanceof IEntityLivingBase) {
            this.target = (IEntityLivingBase) event.getTargetEntity();
        }

    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (event.isPre() && this.target != null) {
            IEntityLivingBase ientitylivingbase = this.target;

            if (this.target == null) {
                Intrinsics.throwNpe();
            }

            if (ientitylivingbase.getHurtTime() >= 3) {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IEntityLivingBase ientitylivingbase1 = this.target;

                if (this.target == null) {
                    Intrinsics.throwNpe();
                }

                double d0 = ientitylivingbase1.getPosX();
                IEntityLivingBase ientitylivingbase2 = this.target;

                if (this.target == null) {
                    Intrinsics.throwNpe();
                }

                double d1 = ientitylivingbase2.getPosY();
                IEntityLivingBase ientitylivingbase3 = this.target;

                if (this.target == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getDistance(d0, d1, ientitylivingbase3.getPosZ()) < (double) 10) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getTicksExisted() > 3) {
                        String s = (String) AttackEffects.mode.get();
                        boolean i = false;

                        if (s == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        String s1 = s.toLowerCase();

                        Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                        s = s1;
                        int i;
                        ParticleManager particlemanager;
                        int j;
                        double d2;
                        double d3;
                        IEntityLivingBase ientitylivingbase4;
                        double d4;
                        double d5;
                        IEntityLivingBase ientitylivingbase5;
                        IEntityLivingBase ientitylivingbase6;
                        IEntityLivingBase ientitylivingbase7;

                        switch (s.hashCode()) {
                        case 3143222:
                            if (s.equals("fire")) {
                                particlemanager = MinecraftInstance.mc2.effectRenderer;
                                j = EnumParticleTypes.LAVA.getParticleID();
                                ientitylivingbase2 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d1 = ientitylivingbase2.getPosX();
                                ientitylivingbase3 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d2 = ientitylivingbase3.getPosY();
                                ientitylivingbase5 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d3 = ientitylivingbase5.getPosZ();
                                ientitylivingbase4 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d4 = ientitylivingbase4.getPosX();
                                ientitylivingbase6 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d5 = ientitylivingbase6.getPosY();
                                ientitylivingbase7 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                particlemanager.spawnEffectParticle(j, d1, d2, d3, d4, d5, ientitylivingbase7.getPosZ(), new int[0]);
                            }
                            break;

                        case 93832698:
                            if (s.equals("blood")) {
                                for (i = 0; i < ((Number) this.amount.getValue()).intValue(); ++i) {
                                    WorldClient worldclient = MinecraftInstance.mc2.world;
                                    EnumParticleTypes enumparticletypes = EnumParticleTypes.BLOCK_CRACK;

                                    ientitylivingbase2 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d1 = ientitylivingbase2.getPosX();
                                    ientitylivingbase3 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d2 = ientitylivingbase3.getPosY();
                                    ientitylivingbase5 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d2 = d2 + (double) ientitylivingbase5.getHeight() - 0.75D;
                                    ientitylivingbase5 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d3 = ientitylivingbase5.getPosZ();
                                    int[] aint = new int[1];
                                    Block block = Blocks.REDSTONE_BLOCK;

                                    Intrinsics.checkExpressionValueIsNotNull(Blocks.REDSTONE_BLOCK, "Blocks.REDSTONE_BLOCK");
                                    aint[0] = Block.getStateId(block.getDefaultState());
                                    worldclient.spawnParticle(enumparticletypes, d1, d2, d3, 0.0D, 0.0D, 0.0D, aint);
                                }

                                if (((Boolean) this.sound.get()).booleanValue()) {
                                    MinecraftInstance.mc.getSoundHandler().playSound("minecraft:block.anvil.break", 1.0F);
                                }
                            }
                            break;

                        case 99151942:
                            if (s.equals("heart")) {
                                particlemanager = MinecraftInstance.mc2.effectRenderer;
                                j = EnumParticleTypes.HEART.getParticleID();
                                ientitylivingbase2 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d1 = ientitylivingbase2.getPosX();
                                ientitylivingbase3 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d2 = ientitylivingbase3.getPosY();
                                ientitylivingbase5 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d3 = ientitylivingbase5.getPosZ();
                                ientitylivingbase4 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d4 = ientitylivingbase4.getPosX();
                                ientitylivingbase6 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d5 = ientitylivingbase6.getPosY();
                                ientitylivingbase7 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                particlemanager.spawnEffectParticle(j, d1, d2, d3, d4, d5, ientitylivingbase7.getPosZ(), new int[0]);
                            }
                            break;

                        case 103655853:
                            if (s.equals("magic")) {
                                for (i = 0; i < ((Number) this.amount.getValue()).intValue(); ++i) {
                                    particlemanager = MinecraftInstance.mc2.effectRenderer;
                                    j = EnumParticleTypes.CRIT_MAGIC.getParticleID();
                                    ientitylivingbase2 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d1 = ientitylivingbase2.getPosX();
                                    ientitylivingbase3 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d2 = ientitylivingbase3.getPosY();
                                    ientitylivingbase5 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d3 = ientitylivingbase5.getPosZ();
                                    ientitylivingbase4 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d4 = ientitylivingbase4.getPosX();
                                    ientitylivingbase6 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d5 = ientitylivingbase6.getPosY();
                                    ientitylivingbase7 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    particlemanager.spawnEffectParticle(j, d1, d2, d3, d4, d5, ientitylivingbase7.getPosZ(), new int[0]);
                                }
                            }
                            break;

                        case 109562223:
                            if (s.equals("smoke")) {
                                particlemanager = MinecraftInstance.mc2.effectRenderer;
                                j = EnumParticleTypes.SMOKE_NORMAL.getParticleID();
                                ientitylivingbase2 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d1 = ientitylivingbase2.getPosX();
                                ientitylivingbase3 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d2 = ientitylivingbase3.getPosY();
                                ientitylivingbase5 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d3 = ientitylivingbase5.getPosZ();
                                ientitylivingbase4 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d4 = ientitylivingbase4.getPosX();
                                ientitylivingbase6 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d5 = ientitylivingbase6.getPosY();
                                ientitylivingbase7 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                particlemanager.spawnEffectParticle(j, d1, d2, d3, d4, d5, ientitylivingbase7.getPosZ(), new int[0]);
                            }
                            break;

                        case 112903447:
                            if (s.equals("water")) {
                                particlemanager = MinecraftInstance.mc2.effectRenderer;
                                j = EnumParticleTypes.WATER_DROP.getParticleID();
                                ientitylivingbase2 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d1 = ientitylivingbase2.getPosX();
                                ientitylivingbase3 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d2 = ientitylivingbase3.getPosY();
                                ientitylivingbase5 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d3 = ientitylivingbase5.getPosZ();
                                ientitylivingbase4 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d4 = ientitylivingbase4.getPosX();
                                ientitylivingbase6 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d5 = ientitylivingbase6.getPosY();
                                ientitylivingbase7 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                particlemanager.spawnEffectParticle(j, d1, d2, d3, d4, d5, ientitylivingbase7.getPosZ(), new int[0]);
                            }
                            break;

                        case 387153076:
                            if (s.equals("criticals")) {
                                for (i = 0; i < ((Number) this.amount.getValue()).intValue(); ++i) {
                                    particlemanager = MinecraftInstance.mc2.effectRenderer;
                                    j = EnumParticleTypes.CRIT.getParticleID();
                                    ientitylivingbase2 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d1 = ientitylivingbase2.getPosX();
                                    ientitylivingbase3 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d2 = ientitylivingbase3.getPosY();
                                    ientitylivingbase5 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d3 = ientitylivingbase5.getPosZ();
                                    ientitylivingbase4 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d4 = ientitylivingbase4.getPosX();
                                    ientitylivingbase6 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    d5 = ientitylivingbase6.getPosY();
                                    ientitylivingbase7 = this.target;
                                    if (this.target == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    particlemanager.spawnEffectParticle(j, d1, d2, d3, d4, d5, ientitylivingbase7.getPosZ(), new int[0]);
                                }
                            }
                            break;

                        case 991970060:
                            if (s.equals("lighting")) {
                                INetHandlerPlayClient inethandlerplayclient = MinecraftInstance.mc.getNetHandler2();
                                SPacketSpawnGlobalEntity spacketspawnglobalentity = new SPacketSpawnGlobalEntity;
                                EntityLightningBolt entitylightningbolt = new EntityLightningBolt;
                                World world = (World) MinecraftInstance.mc2.world;

                                ientitylivingbase6 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                d5 = ientitylivingbase6.getPosX();
                                ientitylivingbase7 = this.target;
                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                double d6 = ientitylivingbase7.getPosY();
                                IEntityLivingBase ientitylivingbase8 = this.target;

                                if (this.target == null) {
                                    Intrinsics.throwNpe();
                                }

                                entitylightningbolt.<init>(world, d5, d6, ientitylivingbase8.getPosZ(), true);
                                spacketspawnglobalentity.<init>((Entity) entitylightningbolt);
                                inethandlerplayclient.handleSpawnGlobalEntity(spacketspawnglobalentity);
                                if (((Boolean) this.lightingSoundValue.get()).booleanValue()) {
                                    MinecraftInstance.mc.getSoundHandler().playSound("entity.lightning.impact", 0.5F);
                                }
                            }
                        }
                    }

                    this.target = (IEntityLivingBase) null;
                }
            }
        }

    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/AttackEffects$Companion;", "", "()V", "mode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getMode", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final ListValue getMode() {
            return AttackEffects.mode;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
