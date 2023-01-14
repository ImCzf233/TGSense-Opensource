package net.ccbluex.liquidbounce.utils;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.minecraft.block.BlockSlime;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\r\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/PlayerUtils;", "", "()V", "findSlimeBlock", "", "()Ljava/lang/Integer;", "getAr", "", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getHp", "isBlockUnder", "", "isUsingFood", "randomUnicode", "", "str", "LiquidBounce"}
)
public final class PlayerUtils {

    public static final PlayerUtils INSTANCE;

    @NotNull
    public final String randomUnicode(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "str");
        StringBuilder stringBuilder = new StringBuilder();
        boolean flag = false;
        char[] achar = str.toCharArray();

        Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
        char[] achar1 = achar;
        int i = achar1.length;

        for (int j = 0; j < i; ++j) {
            char c = achar1[j];

            if (Math.random() > 0.5D) {
                int k = Character.hashCode(c);

                if (33 <= k) {
                    if (128 >= k) {
                        stringBuilder.append(Character.toChars(Character.hashCode(c) + '�?'));
                        continue;
                    }
                }
            }

            stringBuilder.append(c);
        }

        String s = stringBuilder.toString();

        Intrinsics.checkExpressionValueIsNotNull(s, "stringBuilder.toString()");
        return s;
    }

    public final double getAr(@NotNull IEntityLivingBase player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        double arPercentage = (double) ((float) player.getTotalArmorValue() / player.getMaxHealth());

        arPercentage = MathHelper.clamp(arPercentage, 0.0D, 1.0D);
        return (double) 100 * arPercentage;
    }

    public final double getHp(@NotNull IEntityLivingBase player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        float heal = (float) ((int) player.getHealth());
        double hpPercentage = (double) (heal / player.getMaxHealth());

        hpPercentage = MathHelper.clamp(hpPercentage, 0.0D, 1.0D);
        return (double) 100 * hpPercentage;
    }

    public final boolean isUsingFood() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IItemStack iitemstack = ientityplayersp.getItemInUse();

        if (iitemstack == null) {
            Intrinsics.throwNpe();
        }

        IItem usingItem = iitemstack.getItem();

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        boolean flag;

        if (ientityplayersp.getItemInUse() != null) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            flag = ientityplayersp.isUsingItem() && (usingItem instanceof ItemFood || usingItem instanceof ItemBucketMilk || usingItem instanceof ItemPotion);
        } else {
            flag = false;
        }

        return flag;
    }

    public final boolean isBlockUnder() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getPosY() < (double) 0) {
            return false;
        } else {
            int off = 0;

            while (true) {
                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                if (off >= (int) ientityplayersp1.getPosY() + 2) {
                    return false;
                }

                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IAxisAlignedBB bb = ientityplayersp.getEntityBoundingBox().offset(0.0D, -((double) off), 0.0D);
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                Collection collection = iworldclient.getCollidingBoundingBoxes((IEntity) ientityplayersp1, bb);
                boolean flag = false;

                if (!collection.isEmpty()) {
                    return true;
                }

                off += 2;
            }
        }
    }

    @Nullable
    public final Integer findSlimeBlock() {
        int i = 0;

        for (byte b0 = 8; i <= b0; ++i) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IItemStack itemStack = ientityplayersp.getInventory().getStackInSlot(i);

            if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemBlock) {
                IItem iitem = itemStack.getItem();

                if (iitem == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
                }

                ItemBlock block = (ItemBlock) iitem;

                if (block.getBlock() instanceof BlockSlime) {
                    return Integer.valueOf(i);
                }
            }
        }

        return Integer.valueOf(-1);
    }

    static {
        PlayerUtils playerutils = new PlayerUtils();

        INSTANCE = playerutils;
    }
}
