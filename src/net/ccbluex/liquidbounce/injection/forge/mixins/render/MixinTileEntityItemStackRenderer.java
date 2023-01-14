package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;

@SideOnly(Side.CLIENT)
@Mixin({ TileEntityItemStackRenderer.class})
public class MixinTileEntityItemStackRenderer {

}
