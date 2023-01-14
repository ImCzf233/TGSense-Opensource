package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.inventory.IGuiContainer;
import net.ccbluex.liquidbounce.api.minecraft.inventory.IContainer;
import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot;
import net.ccbluex.liquidbounce.injection.implementations.IMixinGuiContainer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0010H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0013"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/GuiContainerImpl;", "T", "Lnet/minecraft/client/gui/inventory/GuiContainer;", "Lnet/ccbluex/liquidbounce/injection/backend/GuiScreenImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/inventory/IGuiContainer;", "wrapped", "(Lnet/minecraft/client/gui/inventory/GuiContainer;)V", "inventorySlots", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/IContainer;", "getInventorySlots", "()Lnet/ccbluex/liquidbounce/api/minecraft/inventory/IContainer;", "handleMouseClick", "", "slot", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/ISlot;", "slotNumber", "", "clickedButton", "clickType", "LiquidBounce"}
)
public class GuiContainerImpl extends GuiScreenImpl implements IGuiContainer {

    public void handleMouseClick(@NotNull ISlot slot, int slotNumber, int clickedButton, int clickType) {
        Intrinsics.checkParameterIsNotNull(slot, "slot");
        Gui gui = this.getWrapped();

        if (gui == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.implementations.IMixinGuiContainer");
        } else {
            IMixinGuiContainer imixinguicontainer = (IMixinGuiContainer) gui;
            boolean $i$f$toClickType = false;
            Slot slot = ((SlotImpl) slot).getWrapped();

            $i$f$toClickType = false;
            ClickType clicktype;

            switch (clickType) {
            case 0:
                clicktype = ClickType.PICKUP;
                break;

            case 1:
                clicktype = ClickType.QUICK_MOVE;
                break;

            case 2:
                clicktype = ClickType.SWAP;
                break;

            case 3:
                clicktype = ClickType.CLONE;
                break;

            case 4:
                clicktype = ClickType.THROW;
                break;

            case 5:
                clicktype = ClickType.QUICK_CRAFT;
                break;

            case 6:
                clicktype = ClickType.PICKUP_ALL;
                break;

            default:
                throw (Throwable) (new IllegalArgumentException("Invalid mode " + clickType));
            }

            ClickType clicktype1 = clicktype;

            imixinguicontainer.publicHandleMouseClick(slot, slotNumber, clickedButton, clicktype1);
        }
    }

    @Nullable
    public IContainer getInventorySlots() {
        Container container = ((GuiContainer) this.getWrapped()).inventorySlots;
        IContainer icontainer;

        if (container != null) {
            Container $this$wrap$iv = container;
            boolean $i$f$wrap = false;

            icontainer = (IContainer) (new ContainerImpl($this$wrap$iv));
        } else {
            icontainer = null;
        }

        return icontainer;
    }

    public GuiContainerImpl(@NotNull GuiContainer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((GuiScreen) wrapped);
    }
}
