package net.ccbluex.liquidbounce.utils;

import java.util.Arrays;
import java.util.List;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.item.Item;

public final class InventoryUtils extends MinecraftInstance implements Listenable {

    public static final MSTimer CLICK_TIMER = new MSTimer();
    public static final List BLOCK_BLACKLIST = Arrays.asList(new IBlock[] { InventoryUtils.classProvider.getBlockEnum(BlockType.CHEST), InventoryUtils.classProvider.getBlockEnum(BlockType.ENDER_CHEST), InventoryUtils.classProvider.getBlockEnum(BlockType.TRAPPED_CHEST), InventoryUtils.classProvider.getBlockEnum(BlockType.ANVIL), InventoryUtils.classProvider.getBlockEnum(BlockType.SAND), InventoryUtils.classProvider.getBlockEnum(BlockType.WEB), InventoryUtils.classProvider.getBlockEnum(BlockType.TORCH), InventoryUtils.classProvider.getBlockEnum(BlockType.CRAFTING_TABLE), InventoryUtils.classProvider.getBlockEnum(BlockType.FURNACE), InventoryUtils.classProvider.getBlockEnum(BlockType.WATERLILY), InventoryUtils.classProvider.getBlockEnum(BlockType.DISPENSER), InventoryUtils.classProvider.getBlockEnum(BlockType.STONE_PRESSURE_PLATE), InventoryUtils.classProvider.getBlockEnum(BlockType.WODDEN_PRESSURE_PLATE), InventoryUtils.classProvider.getBlockEnum(BlockType.NOTEBLOCK), InventoryUtils.classProvider.getBlockEnum(BlockType.DROPPER), InventoryUtils.classProvider.getBlockEnum(BlockType.TNT), InventoryUtils.classProvider.getBlockEnum(BlockType.STANDING_BANNER), InventoryUtils.classProvider.getBlockEnum(BlockType.WALL_BANNER), InventoryUtils.classProvider.getBlockEnum(BlockType.REDSTONE_TORCH)});

    public static boolean isBlockListBlock(IItemBlock itemBlock) {
        IBlock block = itemBlock.getBlock();

        return InventoryUtils.BLOCK_BLACKLIST.contains(block) || !block.isFullCube(block.getDefaultState());
    }

    public static int findItem2(int startSlot, int endSlot, Item item) {
        for (int i = startSlot; i < endSlot; ++i) {
            IItemStack stack = InventoryUtils.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack();

            if (stack != null && stack.getItem() == item) {
                return i;
            }
        }

        return -1;
    }

    public static int findItem(int startSlot, int endSlot, IItem item) {
        for (int i = startSlot; i < endSlot; ++i) {
            IItemStack stack = InventoryUtils.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack();

            if (stack != null && stack.getItem().equals(item)) {
                return i;
            }
        }

        return -1;
    }

    public static boolean hasSpaceHotbar() {
        for (int i = 36; i < 45; ++i) {
            IItemStack stack = InventoryUtils.mc.getThePlayer().getInventory().getStackInSlot(i);

            if (stack == null) {
                return true;
            }
        }

        return false;
    }

    public static int findAutoBlockBlock() {
        int i;
        IItemStack itemStack;
        IItemBlock itemBlock;
        IBlock block;

        for (i = 36; i < 45; ++i) {
            itemStack = InventoryUtils.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack();
            if (itemStack != null && InventoryUtils.classProvider.isItemBlock(itemStack.getItem()) && itemStack.getStackSize() > 0) {
                itemBlock = itemStack.getItem().asItemBlock();
                block = itemBlock.getBlock();
                if (block.isFullCube(block.getDefaultState()) && !InventoryUtils.BLOCK_BLACKLIST.contains(block) && !InventoryUtils.classProvider.isBlockBush(block)) {
                    return i;
                }
            }
        }

        for (i = 36; i < 45; ++i) {
            itemStack = InventoryUtils.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack();
            if (itemStack != null && InventoryUtils.classProvider.isItemBlock(itemStack.getItem()) && itemStack.getStackSize() > 0) {
                itemBlock = itemStack.getItem().asItemBlock();
                block = itemBlock.getBlock();
                if (!InventoryUtils.BLOCK_BLACKLIST.contains(block) && !InventoryUtils.classProvider.isBlockBush(block)) {
                    return i;
                }
            }
        }

        return -1;
    }

    @EventTarget
    public void onClick(ClickWindowEvent event) {
        InventoryUtils.CLICK_TIMER.reset();
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        IPacket packet = event.getPacket();

        if (InventoryUtils.classProvider.isCPacketPlayerBlockPlacement(packet)) {
            InventoryUtils.CLICK_TIMER.reset();
        }

    }

    public boolean handleEvents() {
        return true;
    }
}
