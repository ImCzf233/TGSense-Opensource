package net.ccbluex.liquidbounce.injection.implementations;

import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;

public interface IMixinGuiContainer {

    void publicHandleMouseClick(Slot slot, int i, int j, ClickType clicktype);
}
