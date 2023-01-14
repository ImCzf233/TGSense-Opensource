package net.ccbluex.liquidbounce.utils;

import java.util.UUID;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fJ\u001a\u0010\u0010\u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0011\u001a\u00020\u0004H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/EntityUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "targetAnimals", "", "targetDead", "targetInvisible", "targetMobs", "targetPlayer", "getPing", "", "entityPlayer", "Lnet/minecraft/entity/player/EntityPlayer;", "isFriend", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "isSelected", "canAttackCheck", "LiquidBounce"}
)
public final class EntityUtils extends MinecraftInstance {

    @JvmField
    public static boolean targetInvisible;
    @JvmField
    public static boolean targetPlayer;
    @JvmField
    public static boolean targetMobs;
    @JvmField
    public static boolean targetAnimals;
    @JvmField
    public static boolean targetDead;
    public static final EntityUtils INSTANCE;

    @JvmStatic
    public static final boolean isSelected(@Nullable IEntity entity, boolean canAttackCheck) {
        if (MinecraftInstance.classProvider.isEntityLivingBase(entity)) {
            if (!EntityUtils.targetDead) {
                if (entity == null) {
                    Intrinsics.throwNpe();
                }

                if (!entity.isEntityAlive()) {
                    return false;
                }
            }

            if (entity != null && Intrinsics.areEqual(entity, MinecraftInstance.mc.getThePlayer()) ^ true && (EntityUtils.targetInvisible || !entity.isInvisible())) {
                if (EntityUtils.targetPlayer && MinecraftInstance.classProvider.isEntityPlayer(entity)) {
                    IEntityPlayer entityPlayer = entity.asEntityPlayer();

                    if (!canAttackCheck) {
                        return true;
                    }

                    if (AntiBot.isBot((IEntityLivingBase) entityPlayer)) {
                        return false;
                    }

                    if (PlayerExtensionKt.isClientFriend(entityPlayer) && !LiquidBounce.INSTANCE.getModuleManager().getModule(NoFriends.class).getState()) {
                        return false;
                    }

                    if (entityPlayer.isSpectator()) {
                        return false;
                    }

                    Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(Teams.class);

                    if (module == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.misc.Teams");
                    }

                    Teams teams = (Teams) module;

                    return !teams.getState() || !teams.isInYourTeam((IEntityLivingBase) entityPlayer);
                }

                return EntityUtils.targetMobs && PlayerExtensionKt.isMob(entity) || EntityUtils.targetAnimals && PlayerExtensionKt.isAnimal(entity);
            }
        }

        return false;
    }

    public final boolean isFriend(@NotNull IEntity entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        return MinecraftInstance.classProvider.isEntityPlayer(entity) && entity.getName() != null && LiquidBounce.INSTANCE.getFileManager().friendsConfig.isFriend(ColorUtils.stripColor(entity.getName()));
    }

    public final int getPing(@Nullable EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return 0;
        } else {
            IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
            UUID uuid = entityPlayer.getUniqueID();

            Intrinsics.checkExpressionValueIsNotNull(uuid, "entityPlayer.uniqueID");
            INetworkPlayerInfo networkPlayerInfo = iinethandlerplayclient.getPlayerInfo(uuid);

            return networkPlayerInfo != null ? networkPlayerInfo.getResponseTime() : 0;
        }
    }

    static {
        EntityUtils entityutils = new EntityUtils();

        INSTANCE = entityutils;
        EntityUtils.targetPlayer = true;
        EntityUtils.targetMobs = true;
    }
}
