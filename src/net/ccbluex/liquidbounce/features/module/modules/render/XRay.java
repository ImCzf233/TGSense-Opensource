package net.ccbluex.liquidbounce.features.module.modules.render;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "XRay",
    description = "Allows you to see ores through walls.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/XRay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "xrayBlocks", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "getXrayBlocks", "()Ljava/util/List;", "onToggle", "", "state", "", "LiquidBounce"}
)
public final class XRay extends Module {

    @NotNull
    private final List xrayBlocks;

    @NotNull
    public final List getXrayBlocks() {
        return this.xrayBlocks;
    }

    public void onToggle(boolean state) {
        MinecraftInstance.mc.getRenderGlobal().loadRenderers();
    }

    public XRay() {
        this.xrayBlocks = CollectionsKt.mutableListOf(new IBlock[] { MinecraftInstance.classProvider.getBlockEnum(BlockType.COAL_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.IRON_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.GOLD_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.REDSTONE_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.LAPIS_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.DIAMOND_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.EMERALD_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.QUARTZ_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.CLAY), MinecraftInstance.classProvider.getBlockEnum(BlockType.GLOWSTONE), MinecraftInstance.classProvider.getBlockEnum(BlockType.CRAFTING_TABLE), MinecraftInstance.classProvider.getBlockEnum(BlockType.TORCH), MinecraftInstance.classProvider.getBlockEnum(BlockType.LADDER), MinecraftInstance.classProvider.getBlockEnum(BlockType.TNT), MinecraftInstance.classProvider.getBlockEnum(BlockType.COAL_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.IRON_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.GOLD_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.DIAMOND_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.EMERALD_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.REDSTONE_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.LAPIS_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.FIRE), MinecraftInstance.classProvider.getBlockEnum(BlockType.MOSSY_COBBLESTONE), MinecraftInstance.classProvider.getBlockEnum(BlockType.MOB_SPAWNER), MinecraftInstance.classProvider.getBlockEnum(BlockType.END_PORTAL_FRAME), MinecraftInstance.classProvider.getBlockEnum(BlockType.ENCHANTING_TABLE), MinecraftInstance.classProvider.getBlockEnum(BlockType.BOOKSHELF), MinecraftInstance.classProvider.getBlockEnum(BlockType.COMMAND_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.LAVA), MinecraftInstance.classProvider.getBlockEnum(BlockType.FLOWING_LAVA), MinecraftInstance.classProvider.getBlockEnum(BlockType.WATER), MinecraftInstance.classProvider.getBlockEnum(BlockType.FLOWING_WATER), MinecraftInstance.classProvider.getBlockEnum(BlockType.FURNACE), MinecraftInstance.classProvider.getBlockEnum(BlockType.LIT_FURNACE)});
    }
}
