package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;

public final class EntityUtils2 extends MinecraftInstance {

    public static boolean targetInvisible = false;
    public static boolean targetPlayer = true;
    public static boolean targetMobs = true;
    public static boolean targetAnimals = false;
    public static boolean targetDead = false;

    public static boolean isSelected(IEntity entity, boolean canAttackCheck) {
        if (EntityUtils2.classProvider.isEntityLivingBase(entity) && (EntityUtils2.targetDead || entity.isEntityAlive()) && entity != null && !entity.equals(EntityUtils2.mc.getThePlayer()) && (EntityUtils2.targetInvisible || !entity.isInvisible())) {
            if (EntityUtils2.targetPlayer && EntityUtils2.classProvider.isEntityPlayer(entity)) {
                IEntityPlayer entityPlayer = entity.asEntityPlayer();

                if (canAttackCheck) {
                    if (AntiBot.isBot(entityPlayer)) {
                        return false;
                    } else if (isFriend(entityPlayer) && !LiquidBounce.moduleManager.getModule(NoFriends.class).getState()) {
                        return false;
                    } else if (entityPlayer.isSpectator()) {
                        return false;
                    } else {
                        Teams teams = (Teams) LiquidBounce.moduleManager.getModule(Teams.class);

                        return !teams.getState() || !teams.isInYourTeam(entityPlayer);
                    }
                } else {
                    return true;
                }
            } else {
                return EntityUtils2.targetMobs && isMob(entity) || EntityUtils2.targetAnimals && isAnimal(entity);
            }
        } else {
            return false;
        }
    }

    public static boolean isFriend(IEntity entity) {
        return EntityUtils2.classProvider.isEntityPlayer(entity) && entity.getName() != null && LiquidBounce.fileManager.friendsConfig.isFriend(ColorUtils.stripColor(entity.getName()));
    }

    public static boolean isAnimal(IEntity entity) {
        return EntityUtils2.classProvider.isEntityAnimal(entity) || EntityUtils2.classProvider.isEntitySquid(entity) || EntityUtils2.classProvider.isEntityGolem(entity) || EntityUtils2.classProvider.isEntityBat(entity);
    }

    public static boolean isMob(IEntity entity) {
        return EntityUtils2.classProvider.isEntityMob(entity) || EntityUtils2.classProvider.isEntityVillager(entity) || EntityUtils2.classProvider.isEntitySlime(entity) || EntityUtils2.classProvider.isEntityGhast(entity) || EntityUtils2.classProvider.isEntityDragon(entity) || EntityUtils2.classProvider.isEntityShulker(entity);
    }

    public static String getName(INetworkPlayerInfo networkPlayerInfoIn) {
        if (networkPlayerInfoIn.getDisplayName() != null) {
            return networkPlayerInfoIn.getDisplayName().getFormattedText();
        } else {
            ITeam team = networkPlayerInfoIn.getPlayerTeam();
            String name = networkPlayerInfoIn.getGameProfile().getName();

            return team == null ? name : team.formatString(name);
        }
    }

    public static int getPing(IEntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return 0;
        } else {
            INetworkPlayerInfo networkPlayerInfo = EntityUtils2.mc.getNetHandler().getPlayerInfo(entityPlayer.getUniqueID());

            return networkPlayerInfo == null ? 0 : networkPlayerInfo.getResponseTime();
        }
    }
}
