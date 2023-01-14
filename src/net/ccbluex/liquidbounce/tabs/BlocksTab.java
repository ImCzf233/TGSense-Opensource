package net.ccbluex.liquidbounce.tabs;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016¨\u0006\u000e"},
    d2 = { "Lnet/ccbluex/liquidbounce/tabs/BlocksTab;", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "()V", "displayAllReleventItems", "", "itemList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "getTabIconItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getTranslatedTabLabel", "", "hasSearchBar", "", "LiquidBounce"}
)
public final class BlocksTab extends WrappedCreativeTabs {

    public void displayAllReleventItems(@NotNull List itemList) {
        Intrinsics.checkParameterIsNotNull(itemList, "itemList");
        itemList.add(WrapperImpl.INSTANCE.getClassProvider().createItemStack(WrapperImpl.INSTANCE.getClassProvider().getBlockEnum(BlockType.COMMAND_BLOCK)));
        itemList.add(WrapperImpl.INSTANCE.getClassProvider().createItemStack(WrapperImpl.INSTANCE.getClassProvider().getItemEnum(ItemType.COMMAND_BLOCK_MINECART)));
        itemList.add(WrapperImpl.INSTANCE.getClassProvider().createItemStack(WrapperImpl.INSTANCE.getClassProvider().getBlockEnum(BlockType.BARRIER)));
        itemList.add(WrapperImpl.INSTANCE.getClassProvider().createItemStack(WrapperImpl.INSTANCE.getClassProvider().getBlockEnum(BlockType.DRAGON_EGG)));
        itemList.add(WrapperImpl.INSTANCE.getClassProvider().createItemStack(WrapperImpl.INSTANCE.getClassProvider().getBlockEnum(BlockType.BROWN_MUSHROOM_BLOCK)));
        itemList.add(WrapperImpl.INSTANCE.getClassProvider().createItemStack(WrapperImpl.INSTANCE.getClassProvider().getBlockEnum(BlockType.RED_MUSHROOM_BLOCK)));
        itemList.add(WrapperImpl.INSTANCE.getClassProvider().createItemStack(WrapperImpl.INSTANCE.getClassProvider().getBlockEnum(BlockType.FARMLAND)));
        itemList.add(WrapperImpl.INSTANCE.getClassProvider().createItemStack(WrapperImpl.INSTANCE.getClassProvider().getBlockEnum(BlockType.MOB_SPAWNER)));
        itemList.add(WrapperImpl.INSTANCE.getClassProvider().createItemStack(WrapperImpl.INSTANCE.getClassProvider().getBlockEnum(BlockType.LIT_FURNACE)));
    }

    @NotNull
    public IItem getTabIconItem() {
        IItem iitem = WrapperImpl.INSTANCE.getClassProvider().createItemStack(WrapperImpl.INSTANCE.getClassProvider().getBlockEnum(BlockType.COMMAND_BLOCK)).getItem();

        if (iitem == null) {
            Intrinsics.throwNpe();
        }

        return iitem;
    }

    @NotNull
    public String getTranslatedTabLabel() {
        return "Special blocks";
    }

    public boolean hasSearchBar() {
        return true;
    }

    public BlocksTab() {
        super("Special blocks");
        this.getRepresentedType().setBackgroundImageName("item_search.png");
    }
}
