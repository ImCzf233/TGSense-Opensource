package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "BlockOverlay",
    description = "Allows you to change the design of the block overlay.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0016H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0017"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/BlockOverlay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "colora", "currentBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getCurrentBlock", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "infoValue", "getInfoValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"}
)
public final class BlockOverlay extends Module {

    private final IntegerValue colorRedValue = new IntegerValue("R", 68, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 117, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    private final IntegerValue colora = new IntegerValue("A", 255, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    @NotNull
    private final BoolValue infoValue = new BoolValue("Info", false);

    @NotNull
    public final BoolValue getInfoValue() {
        return this.infoValue;
    }

    @Nullable
    public final WBlockPos getCurrentBlock() {
        IMovingObjectPosition imovingobjectposition = MinecraftInstance.mc.getObjectMouseOver();

        if (imovingobjectposition != null) {
            WBlockPos wblockpos = imovingobjectposition.getBlockPos();

            if (wblockpos != null) {
                WBlockPos blockPos = wblockpos;

                if (BlockUtils.canBeClicked(blockPos)) {
                    IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                    if (iworldclient == null) {
                        Intrinsics.throwNpe();
                    }

                    if (iworldclient.getWorldBorder().contains(blockPos)) {
                        return blockPos;
                    }
                }

                return null;
            }
        }

        return null;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos wblockpos = this.getCurrentBlock();

        if (wblockpos != null) {
            WBlockPos blockPos = wblockpos;
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            IBlock block = iworldclient.getBlockState(blockPos).getBlock();
            float partialTicks = event.getPartialTicks();
            Color color = ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow(0.4F) : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), ((Number) this.colora.get()).intValue());

            MinecraftInstance.classProvider.getGlStateManager().enableBlend();
            MinecraftInstance.classProvider.getGlStateManager().tryBlendFuncSeparate(770, 771, 1, 0);
            RenderUtils.glColor(color);
            GL11.glLineWidth(2.0F);
            MinecraftInstance.classProvider.getGlStateManager().disableTexture2D();
            GL11.glDepthMask(false);
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp != null) {
                IEntityPlayerSP thePlayer = ientityplayersp;
                double x = thePlayer.getLastTickPosX() + (thePlayer.getPosX() - thePlayer.getLastTickPosX()) * (double) partialTicks;
                double y = thePlayer.getLastTickPosY() + (thePlayer.getPosY() - thePlayer.getLastTickPosY()) * (double) partialTicks;
                double z = thePlayer.getLastTickPosZ() + (thePlayer.getPosZ() - thePlayer.getLastTickPosZ()) * (double) partialTicks;
                IWorldClient iworldclient1 = MinecraftInstance.mc.getTheWorld();

                if (iworldclient1 == null) {
                    Intrinsics.throwNpe();
                }

                IWorld iworld = (IWorld) iworldclient1;
                IWorldClient iworldclient2 = MinecraftInstance.mc.getTheWorld();

                if (iworldclient2 == null) {
                    Intrinsics.throwNpe();
                }

                IAxisAlignedBB axisAlignedBB = block.getSelectedBoundingBox(iworld, iworldclient2.getBlockState(blockPos), blockPos).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-x, -y, -z);

                RenderUtils.drawSelectionBoundingBox(axisAlignedBB);
                RenderUtils.drawFilledBox(axisAlignedBB);
                GL11.glDepthMask(true);
                MinecraftInstance.classProvider.getGlStateManager().enableTexture2D();
                MinecraftInstance.classProvider.getGlStateManager().disableBlend();
                MinecraftInstance.classProvider.getGlStateManager().resetColor();
            }
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (((Boolean) this.infoValue.get()).booleanValue()) {
            WBlockPos wblockpos = this.getCurrentBlock();

            if (wblockpos == null) {
                return;
            }

            WBlockPos blockPos = wblockpos;
            IBlock iblock = BlockUtils.getBlock(blockPos);

            if (iblock == null) {
                return;
            }

            IBlock block = iblock;
            String info = block.getLocalizedName() + " §7ID: " + MinecraftInstance.functions.getIdFromBlock(block);
            IClassProvider iclassprovider = MinecraftInstance.classProvider;
            IMinecraft iminecraft = MinecraftInstance.mc;

            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
            IScaledResolution scaledResolution = iclassprovider.createScaledResolution(iminecraft);

            MinecraftInstance.classProvider.getGlStateManager().resetColor();
            IFontRenderer ifontrenderer = Fonts.font40;
            float f = (float) scaledResolution.getScaledWidth() / 2.0F;
            float f1 = (float) scaledResolution.getScaledHeight() / 2.0F + 7.0F;
            Color color = Color.WHITE;

            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(info, f, f1, color.getRGB(), false);
        }

    }
}
