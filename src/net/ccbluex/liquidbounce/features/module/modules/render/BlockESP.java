package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "BlockESP",
    description = "Allows you to see a selected block through walls.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0007J\u0012\u0010\u001e\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/BlockESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blockLimitValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blockValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "colorBlueValue", "colorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "posList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "radiusValue", "searchTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "tag", "", "getTag", "()Ljava/lang/String;", "thread", "Ljava/lang/Thread;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class BlockESP extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Box", "2D"}, "Box");
    private final BlockValue blockValue = new BlockValue("Block", 168);
    private final IntegerValue radiusValue = new IntegerValue("Radius", 40, 5, 120);
    private final IntegerValue blockLimitValue = new IntegerValue("BlockLimit", 256, 0, 2056);
    private final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 179, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 72, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    private final MSTimer searchTimer = new MSTimer();
    private final List posList = (List) (new ArrayList());
    private Thread thread;

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        if (this.searchTimer.hasTimePassed(1000L)) {
            Thread thread;

            if (this.thread != null) {
                thread = this.thread;
                if (this.thread == null) {
                    Intrinsics.throwNpe();
                }

                if (thread.isAlive()) {
                    return;
                }
            }

            final int radius = ((Number) this.radiusValue.get()).intValue();
            final IBlock selectedBlock = MinecraftInstance.functions.getBlockById(((Number) this.blockValue.get()).intValue());

            if (selectedBlock == null || Intrinsics.areEqual(selectedBlock, MinecraftInstance.classProvider.getBlockEnum(BlockType.AIR))) {
                return;
            }

            this.thread = new Thread((Runnable) (new Runnable() {
                public final void run() {
                    List blockList = (List) (new ArrayList());
                    int x = -radius;

                    for (int i = radius; x < i; ++x) {
                        int y = radius;
                        int $i$a$-synchronized-BlockESP$onUpdate$1$1 = -radius + 1;

                        if (y >= $i$a$-synchronized-BlockESP$onUpdate$1$1) {
                            while (true) {
                                int z = -radius;

                                for (int j = radius; z < j; ++z) {
                                    IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                                    if (ientityplayersp == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    IEntityPlayerSP thePlayer = ientityplayersp;
                                    int xPos = (int) thePlayer.getPosX() + x;
                                    int yPos = (int) thePlayer.getPosY() + y;
                                    int zPos = (int) thePlayer.getPosZ() + z;
                                    WBlockPos blockPos = new WBlockPos(xPos, yPos, zPos);
                                    IBlock block = BlockUtils.getBlock(blockPos);

                                    if (Intrinsics.areEqual(block, selectedBlock) && blockList.size() < ((Number) BlockESP.this.blockLimitValue.get()).intValue()) {
                                        blockList.add(blockPos);
                                    }
                                }

                                if (y == $i$a$-synchronized-BlockESP$onUpdate$1$1) {
                                    break;
                                }

                                --y;
                            }
                        }
                    }

                    BlockESP.this.searchTimer.reset();
                    List list = BlockESP.this.posList;
                    boolean flag = false;
                    boolean flag1 = false;

                    synchronized (list){}

                    try {
                        boolean flag2 = false;

                        BlockESP.this.posList.clear();
                        flag1 = BlockESP.this.posList.addAll((Collection) blockList);
                    } finally {
                        ;
                    }

                }
            }), "BlockESP-BlockFinder");
            thread = this.thread;
            if (this.thread == null) {
                Intrinsics.throwNpe();
            }

            thread.start();
        }

    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        List list = this.posList;
        boolean flag = false;
        boolean flag1 = false;

        synchronized (list){}

        try {
            boolean $i$a$-synchronized-BlockESP$onRender3D$1 = false;
            Color color = ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue());
            Iterator iterator = this.posList.iterator();

            while (iterator.hasNext()) {
                WBlockPos blockPos = (WBlockPos) iterator.next();
                String s = (String) this.modeValue.get();
                boolean flag2 = false;

                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case 1650:
                    if (s.equals("2d")) {
                        int i = color.getRGB();
                        Color color = Color.BLACK;

                        Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
                        RenderUtils.draw2D(blockPos, i, color.getRGB());
                    }
                    break;

                case 97739:
                    if (s.equals("box")) {
                        RenderUtils.drawBlockBox(blockPos, color, true);
                    }
                }
            }

            Unit unit = Unit.INSTANCE;
        } finally {
            ;
        }

    }

    @NotNull
    public String getTag() {
        return BlockUtils.getBlockName(((Number) this.blockValue.get()).intValue());
    }
}
