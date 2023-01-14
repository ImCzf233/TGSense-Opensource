package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "IceSpeed",
    description = "Allows you to walk faster on ice.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\u0012\u0010\b\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/IceSpeed;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "onDisable", "", "onEnable", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class IceSpeed extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "NCP", "AAC", "Spartan"}, "NCP");

    public void onEnable() {
        if (StringsKt.equals((String) this.modeValue.get(), "NCP", true)) {
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.39F);
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.39F);
        }

        super.onEnable();
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        String mode = (String) this.modeValue.get();

        if (StringsKt.equals(mode, "NCP", true)) {
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.39F);
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.39F);
        } else {
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.98F);
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.98F);
        }

        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (thePlayer.getOnGround() && !thePlayer.isOnLadder() && !thePlayer.isSneaking() && thePlayer.getSprinting() && (double) thePlayer.getMovementInput().getMoveForward() > 0.0D) {
                IMaterial imaterial;
                boolean flag;
                boolean flag1;
                boolean $i$a$-let-IceSpeed$onUpdate$2;

                if (StringsKt.equals(mode, "AAC", true)) {
                    imaterial = BlockUtils.getMaterial(thePlayer.getPosition().down());
                    flag = false;
                    flag1 = false;
                    $i$a$-let-IceSpeed$onUpdate$2 = false;
                    if (Intrinsics.areEqual(imaterial, MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE)) || Intrinsics.areEqual(imaterial, MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED))) {
                        thePlayer.setMotionX(thePlayer.getMotionX() * 1.342D);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.342D);
                        MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.6F);
                        MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.6F);
                    }
                }

                if (StringsKt.equals(mode, "Spartan", true)) {
                    imaterial = BlockUtils.getMaterial(thePlayer.getPosition().down());
                    flag = false;
                    flag1 = false;
                    $i$a$-let-IceSpeed$onUpdate$2 = false;
                    if (Intrinsics.areEqual(imaterial, MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE)) || Intrinsics.areEqual(imaterial, MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED))) {
                        IBlock upBlock = BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 2.0D, thePlayer.getPosZ()));

                        if (!MinecraftInstance.classProvider.isBlockAir(upBlock)) {
                            thePlayer.setMotionX(thePlayer.getMotionX() * 1.342D);
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.342D);
                        } else {
                            thePlayer.setMotionX(thePlayer.getMotionX() * 1.18D);
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.18D);
                        }

                        MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.6F);
                        MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.6F);
                    }
                }
            }

        }
    }

    public void onDisable() {
        MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.98F);
        MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.98F);
        super.onDisable();
    }
}
