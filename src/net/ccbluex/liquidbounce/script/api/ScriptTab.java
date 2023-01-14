package net.ccbluex.liquidbounce.script.api;

import java.util.Iterator;
import java.util.List;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0019\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/script/api/ScriptTab;", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "tabObject", "Ljdk/nashorn/api/scripting/JSObject;", "(Ljdk/nashorn/api/scripting/JSObject;)V", "items", "", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "getItems", "()[Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "[Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "displayAllReleventItems", "", "", "getTabIconItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getTranslatedTabLabel", "", "LiquidBounce"}
)
public final class ScriptTab extends WrappedCreativeTabs {

    @NotNull
    private final IItemStack[] items;
    private final JSObject tabObject;

    @NotNull
    public final IItemStack[] getItems() {
        return this.items;
    }

    @NotNull
    public IItem getTabIconItem() {
        Object object = this.tabObject.getMember("icon");

        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        } else {
            IItemStack iitemstack = ItemUtils.createItem((String) object);
            IItem iitem = iitemstack != null ? iitemstack.getItem() : null;

            if (iitem == null) {
                Intrinsics.throwNpe();
            }

            return iitem;
        }
    }

    @NotNull
    public String getTranslatedTabLabel() {
        Object object = this.tabObject.getMember("name");

        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        } else {
            return (String) object;
        }
    }

    public void displayAllReleventItems(@NotNull List items) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        Iterable $this$forEach$iv = (Iterable) items;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv.iterator();

        while (iterator.hasNext()) {
            Object element$iv = iterator.next();
            IItemStack it = (IItemStack) element$iv;
            boolean $i$a$-forEach-ScriptTab$displayAllReleventItems$1 = false;

            items.add(it);
        }

    }

    public ScriptTab(@NotNull JSObject tabObject) {
        Intrinsics.checkParameterIsNotNull(tabObject, "tabObject");
        Object object = tabObject.getMember("name");

        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        } else {
            super((String) object);
            this.tabObject = tabObject;
            object = ScriptUtils.convert(this.tabObject.getMember("items"), IItemStack[].class);
            if (object == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<net.ccbluex.liquidbounce.api.minecraft.item.IItemStack>");
            } else {
                this.items = (IItemStack[]) object;
            }
        }
    }
}
