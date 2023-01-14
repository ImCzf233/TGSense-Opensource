package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ Gui.class})
public abstract class MixinGui {

    @Shadow
    protected float zLevel;

    @Shadow
    public abstract void drawTexturedModalRect(int i, int j, int k, int l, int i1, int j1);
}
