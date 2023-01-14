package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Iterator;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WMathHelper;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;

@ModuleInfo(
    name = "Ignite",
    description = "Automatically sets targets around you on fire.",
    category = ModuleCategory.COMBAT
)
public class Ignite extends Module {

    private final BoolValue lighterValue = new BoolValue("Lighter", true);
    private final BoolValue lavaBucketValue = new BoolValue("Lava", true);
    private final MSTimer msTimer = new MSTimer();

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (this.msTimer.hasTimePassed(500L)) {
            IEntityPlayerSP thePlayer = Ignite.mc.getThePlayer();
            IWorldClient theWorld = Ignite.mc.getTheWorld();

            if (thePlayer != null && theWorld != null) {
                int lighterInHotbar = ((Boolean) this.lighterValue.get()).booleanValue() ? InventoryUtils.findItem(36, 45, Ignite.classProvider.getItemEnum(ItemType.FLINT_AND_STEEL)) : -1;
                int lavaInHotbar = ((Boolean) this.lavaBucketValue.get()).booleanValue() ? InventoryUtils.findItem(26, 45, Ignite.classProvider.getItemEnum(ItemType.LAVA_BUCKET)) : -1;

                if (lighterInHotbar != -1 || lavaInHotbar != -1) {
                    int fireInHotbar = lighterInHotbar != -1 ? lighterInHotbar : lavaInHotbar;
                    Iterator iterator = theWorld.getLoadedEntityList().iterator();

                    while (iterator.hasNext()) {
                        IEntity entity = (IEntity) iterator.next();

                        if (EntityUtils.isSelected(entity, true) && !entity.isBurning()) {
                            WBlockPos blockPos = entity.getPosition();

                            if (Ignite.mc.getThePlayer().getDistanceSq(blockPos) < 22.3D && BlockUtils.isReplaceable(blockPos) && Ignite.classProvider.isBlockAir(BlockUtils.getBlock(blockPos))) {
                                RotationUtils.keepCurrentRotation = true;
                                Ignite.mc.getNetHandler().addToSendQueue(Ignite.classProvider.createCPacketHeldItemChange(fireInHotbar - 36));
                                IItemStack itemStack = Ignite.mc.getThePlayer().getInventory().getStackInSlot(fireInHotbar);
                                double diffX1;

                                if (Ignite.classProvider.isItemBucket(itemStack.getItem())) {
                                    double diffX = (double) blockPos.getX() + 0.5D - Ignite.mc.getThePlayer().getPosX();
                                    double diffY = (double) blockPos.getY() + 0.5D - (thePlayer.getEntityBoundingBox().getMinY() + (double) thePlayer.getEyeHeight());
                                    double side = (double) blockPos.getZ() + 0.5D - thePlayer.getPosZ();

                                    diffX1 = Math.sqrt(diffX * diffX + side * side);
                                    float diffY1 = (float) (Math.atan2(side, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
                                    float pitch = (float) (-(Math.atan2(diffY, diffX1) * 180.0D / 3.141592653589793D));

                                    Ignite.mc.getNetHandler().addToSendQueue(Ignite.classProvider.createCPacketPlayerLook(thePlayer.getRotationYaw() + WMathHelper.wrapAngleTo180_float(diffY1 - thePlayer.getRotationYaw()), thePlayer.getRotationPitch() + WMathHelper.wrapAngleTo180_float(pitch - thePlayer.getRotationPitch()), thePlayer.getOnGround()));
                                    Ignite.mc.getPlayerController().sendUseItem(thePlayer, theWorld, itemStack);
                                } else {
                                    EnumFacingType[] aenumfacingtype = EnumFacingType.values();
                                    int i = aenumfacingtype.length;

                                    for (int j = 0; j < i; ++j) {
                                        EnumFacingType enumFacingType = aenumfacingtype[j];
                                        IEnumFacing ienumfacing = Ignite.classProvider.getEnumFacing(enumFacingType);
                                        WBlockPos neighbor = blockPos.offset(ienumfacing);

                                        if (BlockUtils.canBeClicked(neighbor)) {
                                            diffX1 = (double) neighbor.getX() + 0.5D - thePlayer.getPosX();
                                            double d0 = (double) neighbor.getY() + 0.5D - (thePlayer.getEntityBoundingBox().getMinY() + (double) thePlayer.getEyeHeight());
                                            double diffZ = (double) neighbor.getZ() + 0.5D - thePlayer.getPosZ();
                                            double sqrt = Math.sqrt(diffX1 * diffX1 + diffZ * diffZ);
                                            float yaw = (float) (Math.atan2(diffZ, diffX1) * 180.0D / 3.141592653589793D) - 90.0F;
                                            float pitch1 = (float) (-(Math.atan2(d0, sqrt) * 180.0D / 3.141592653589793D));

                                            Ignite.mc.getNetHandler().addToSendQueue(Ignite.classProvider.createCPacketPlayerLook(thePlayer.getRotationYaw() + WMathHelper.wrapAngleTo180_float(yaw - thePlayer.getRotationYaw()), thePlayer.getRotationPitch() + WMathHelper.wrapAngleTo180_float(pitch1 - thePlayer.getRotationPitch()), thePlayer.getOnGround()));
                                            if (Ignite.mc.getPlayerController().onPlayerRightClick(thePlayer, theWorld, itemStack, neighbor, ienumfacing.getOpposite(), new WVec3(ienumfacing.getDirectionVec()))) {
                                                thePlayer.swingItem();
                                                break;
                                            }
                                        }
                                    }
                                }

                                Ignite.mc.getNetHandler().addToSendQueue(Ignite.classProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
                                RotationUtils.keepCurrentRotation = false;
                                Ignite.mc.getNetHandler().addToSendQueue(Ignite.classProvider.createCPacketPlayerLook(thePlayer.getRotationYaw(), thePlayer.getRotationPitch(), thePlayer.getOnGround()));
                                this.msTimer.reset();
                                break;
                            }
                        }
                    }

                }
            }
        }
    }
}
