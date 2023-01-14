package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.function.Consumer;
import javax.vecmath.Vector3d;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.PathUtils;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;

@ModuleInfo(
    name = "TeleportHit",
    description = "Allows to hit entities from far away.",
    category = ModuleCategory.COMBAT
)
public class TeleportHit extends Module {

    private IEntityLivingBase targetEntity;
    private boolean shouldHit;

    @EventTarget
    public void onMotion(MotionEvent event) {
        if (event.getEventState() == EventState.PRE) {
            IClassProvider iclassprovider = TeleportHit.classProvider;

            TeleportHit.classProvider.getClass();
            IEntity facedEntity = RaycastUtils.raycastEntity(100.0D, iclassprovider::isEntityLivingBase);
            IEntityPlayerSP thePlayer = TeleportHit.mc.getThePlayer();

            if (thePlayer != null) {
                if (TeleportHit.mc.getGameSettings().getKeyBindAttack().isKeyDown() && EntityUtils.isSelected(facedEntity, true) && facedEntity.getDistanceSqToEntity(thePlayer) >= 1.0D) {
                    this.targetEntity = facedEntity.asEntityLivingBase();
                }

                if (this.targetEntity != null) {
                    if (!this.shouldHit) {
                        this.shouldHit = true;
                        return;
                    }

                    if (thePlayer.getFallDistance() > 0.0F) {
                        WVec3 rotationVector = RotationUtils.getVectorForRotation(new Rotation(thePlayer.getRotationYaw(), 0.0F));
                        double x = thePlayer.getPosX() + rotationVector.getXCoord() * (double) (thePlayer.getDistanceToEntity(this.targetEntity) - 1.0F);
                        double z = thePlayer.getPosZ() + rotationVector.getZCoord() * (double) (thePlayer.getDistanceToEntity(this.targetEntity) - 1.0F);
                        double y = (double) this.targetEntity.getPosition().getY() + 0.25D;

                        PathUtils.findPath(x, y + 1.0D, z, 4.0D).forEach((pos) -> {
                            TeleportHit.mc.getNetHandler().addToSendQueue(TeleportHit.classProvider.createCPacketPlayerPosition(pos.getX(), pos.getY(), pos.getZ(), false));
                        });
                        thePlayer.swingItem();
                        TeleportHit.mc.getNetHandler().addToSendQueue(TeleportHit.classProvider.createCPacketUseEntity(this.targetEntity, ICPacketUseEntity.WAction.ATTACK));
                        thePlayer.onCriticalHit(this.targetEntity);
                        this.shouldHit = false;
                        this.targetEntity = null;
                    } else if (thePlayer.getOnGround()) {
                        thePlayer.jump();
                    }
                } else {
                    this.shouldHit = false;
                }

            }
        }
    }
}
