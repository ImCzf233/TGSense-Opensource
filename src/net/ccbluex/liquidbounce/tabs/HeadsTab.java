package net.ccbluex.liquidbounce.tabs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0007H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/tabs/HeadsTab;", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "()V", "heads", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "displayAllReleventItems", "", "itemList", "", "getTabIconItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getTranslatedTabLabel", "", "hasSearchBar", "", "loadHeads", "LiquidBounce"}
)
public final class HeadsTab extends WrappedCreativeTabs {

    private final ArrayList heads = new ArrayList();

    private final void loadHeads() {
        try {
            ClientUtils.getLogger().info("Loading heads...");
            JsonElement e = (new JsonParser()).parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/heads.json"));

            Intrinsics.checkExpressionValueIsNotNull(e, "headsConfiguration");
            if (!e.isJsonObject()) {
                return;
            }

            JsonObject headsConf = e.getAsJsonObject();
            JsonElement jsonelement = headsConf.get("enabled");

            Intrinsics.checkExpressionValueIsNotNull(jsonelement, "headsConf.get(\"enabled\")");
            if (jsonelement.getAsBoolean()) {
                jsonelement = headsConf.get("url");
                Intrinsics.checkExpressionValueIsNotNull(jsonelement, "headsConf.get(\"url\")");
                String url = jsonelement.getAsString();

                ClientUtils.getLogger().info("Loading heads from " + url + "...");
                JsonParser jsonparser = new JsonParser();

                Intrinsics.checkExpressionValueIsNotNull(url, "url");
                JsonElement headsElement = jsonparser.parse(HttpUtils.get(url));

                Intrinsics.checkExpressionValueIsNotNull(headsElement, "headsElement");
                if (!headsElement.isJsonObject()) {
                    ClientUtils.getLogger().error("Something is wrong, the heads json is not a JsonObject!");
                    return;
                }

                JsonObject headsObject = headsElement.getAsJsonObject();
                Iterator iterator = headsObject.entrySet().iterator();

                while (iterator.hasNext()) {
                    Entry entry = (Entry) iterator.next();
                    boolean flag = false;
                    JsonElement value = (JsonElement) entry.getValue();

                    Intrinsics.checkExpressionValueIsNotNull(value, "value");
                    JsonObject headElement = value.getAsJsonObject();
                    ArrayList arraylist = this.heads;
                    StringBuilder stringbuilder = (new StringBuilder()).append("skull 1 3 {display:{Name:\"");
                    JsonElement jsonelement1 = headElement.get("name");

                    Intrinsics.checkExpressionValueIsNotNull(jsonelement1, "headElement.get(\"name\")");
                    stringbuilder = stringbuilder.append(jsonelement1.getAsString()).append("\"},SkullOwner:{Id:\"");
                    jsonelement1 = headElement.get("uuid");
                    Intrinsics.checkExpressionValueIsNotNull(jsonelement1, "headElement.get(\"uuid\")");
                    stringbuilder = stringbuilder.append(jsonelement1.getAsString()).append("\",Properties:{textures:[{Value:\"");
                    jsonelement1 = headElement.get("value");
                    Intrinsics.checkExpressionValueIsNotNull(jsonelement1, "headElement.get(\"value\")");
                    arraylist.add(ItemUtils.createItem(stringbuilder.append(jsonelement1.getAsString()).append("\"}]}}}").toString()));
                }

                ClientUtils.getLogger().info("Loaded " + this.heads.size() + " heads from HeadDB.");
            } else {
                ClientUtils.getLogger().info("Heads are disabled.");
            }
        } catch (Exception exception) {
            ClientUtils.getLogger().error("Error while reading heads.", (Throwable) exception);
        }

    }

    public void displayAllReleventItems(@NotNull List itemList) {
        Intrinsics.checkParameterIsNotNull(itemList, "itemList");
        itemList.addAll((Collection) this.heads);
    }

    @NotNull
    public IItem getTabIconItem() {
        return WrapperImpl.INSTANCE.getClassProvider().getItemEnum(ItemType.SKULL);
    }

    @NotNull
    public String getTranslatedTabLabel() {
        return "Heads";
    }

    public boolean hasSearchBar() {
        return true;
    }

    public HeadsTab() {
        super("Heads");
        this.getRepresentedType().setBackgroundImageName("item_search.png");
        this.loadHeads();
    }
}
