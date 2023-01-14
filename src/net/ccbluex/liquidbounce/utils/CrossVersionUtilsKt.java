package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 2,
    d1 = { "\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\t\u0010\u0000\u001a\u00020\u0001H\u0086\b\u001a\u001b\u0010\u0002\u001a\u00020\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\b\u001a\u001d\u0010\u0007\u001a\u0004\u0018\u00010\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\b\u001a\u001d\u0010\b\u001a\u0004\u0018\u00010\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\b¨\u0006\t"},
    d2 = { "createOpenInventoryPacket", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "createUseItemPacket", "itemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "hand", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", "createblockc07", "createblockc08c07", "LiquidBounce"}
)
public final class CrossVersionUtilsKt {

    @NotNull
    public static final IPacket createUseItemPacket(@Nullable IItemStack itemStack, @NotNull WEnumHand hand) {
        byte $i$f$createUseItemPacket = 0;

        Intrinsics.checkParameterIsNotNull(hand, "hand");
        return (IPacket) WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(hand);
    }

    @Nullable
    public static final IPacket createblockc08c07(@Nullable IItemStack itemStack, @NotNull WEnumHand hand) {
        byte $i$f$createblockc08c07 = 0;

        Intrinsics.checkParameterIsNotNull(hand, "hand");
        return WrapperImpl.INSTANCE.getClassProvider().createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
    }

    @Nullable
    public static final IPacket createblockc07(@Nullable IItemStack itemStack, @NotNull WEnumHand hand) {
        byte $i$f$createblockc07 = 0;

        Intrinsics.checkParameterIsNotNull(hand, "hand");
        return (IPacket) WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(hand);
    }

    @NotNull
    public static final IPacket createOpenInventoryPacket() {
        byte $i$f$createOpenInventoryPacket = 0;
        IClassProvider iclassprovider = WrapperImpl.INSTANCE.getClassProvider();
        IEntityPlayerSP ientityplayersp = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        return (IPacket) iclassprovider.createCPacketEntityAction((IEntity) ientityplayersp, ICPacketEntityAction.WAction.OPEN_INVENTORY);
    }
}
