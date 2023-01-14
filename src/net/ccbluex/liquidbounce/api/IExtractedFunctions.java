package net.ccbluex.liquidbounce.api;

import com.mojang.authlib.GameProfile;
import java.util.Collection;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.entity.IEnumCreatureAttribute;
import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.tileentity.ITileEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\nH&J\b\u0010\f\u001a\u00020\nH&J)\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0012\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0\u0011\"\u00020\u000eH&¢\u0006\u0002\u0010\u0012J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0018\u001a\u00020\u000eH&J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH&J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u0016H&J\u0012\u0010\u001f\u001a\u0004\u0018\u00010\u001d2\u0006\u0010 \u001a\u00020\u000eH&J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH&J\u0010\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\u0014H&J\u0010\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020&H&J\u0012\u0010\'\u001a\u0004\u0018\u00010&2\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0012\u0010(\u001a\u0004\u0018\u00010&2\u0006\u0010\u0018\u001a\u00020\u000eH&J\u000e\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH&J\u001a\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010\u00072\u0006\u0010-\u001a\u00020.H&J\u0012\u0010/\u001a\u0004\u0018\u00010&2\u0006\u00100\u001a\u00020\u001bH&J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u0016H&J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u000eH&J \u00107\u001a\u00020\n2\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020+2\u0006\u0010;\u001a\u00020\u0016H&J\u001a\u0010<\u001a\u00020\u000e2\b\u0010=\u001a\u0004\u0018\u00010>2\u0006\u0010?\u001a\u00020\u000eH&J \u0010@\u001a\u00020\n2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u000e2\u0006\u0010D\u001a\u00020\u000eH&J\b\u0010E\u001a\u00020\nH&J\b\u0010F\u001a\u00020\nH&¨\u0006G"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/IExtractedFunctions;", "", "canAddItemToSlot", "", "slotIn", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/ISlot;", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "stackSizeMatters", "disableFastRender", "", "disableStandardItemLighting", "enableStandardItemLighting", "formatI18n", "", "key", "values", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getBlockById", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "id", "", "getBlockFromName", "name", "getBlockRegistryKeys", "", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getEnchantmentById", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "enchantID", "getEnchantmentByLocation", "location", "getEnchantments", "getIdFromBlock", "block", "getIdFromItem", "item", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getItemById", "getItemByName", "getItemRegistryKeys", "getModifierForCreature", "", "heldItem", "creatureAttribute", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/IEnumCreatureAttribute;", "getObjectFromItemRegistry", "res", "getPotionById", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotion;", "potionID", "jsonToComponent", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "toString", "renderTileEntity", "tileEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/tileentity/ITileEntity;", "partialTicks", "destroyStage", "scoreboardFormatPlayerName", "scorePlayerTeam", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "playerName", "sessionServiceJoinServer", "profile", "Lcom/mojang/authlib/GameProfile;", "token", "sessionHash", "setActiveTextureDefaultTexUnit", "setActiveTextureLightMapTexUnit", "LiquidBounce"}
)
public interface IExtractedFunctions {

    @Nullable
    IBlock getBlockById(int i);

    int getIdFromBlock(@NotNull IBlock iblock);

    float getModifierForCreature(@Nullable IItemStack iitemstack, @NotNull IEnumCreatureAttribute ienumcreatureattribute);

    @Nullable
    IItem getObjectFromItemRegistry(@NotNull IResourceLocation iresourcelocation);

    void renderTileEntity(@NotNull ITileEntity itileentity, float f, int i);

    @Nullable
    IBlock getBlockFromName(@NotNull String s);

    @Nullable
    IItem getItemByName(@NotNull String s);

    @Nullable
    IEnchantment getEnchantmentByLocation(@NotNull String s);

    @Nullable
    IEnchantment getEnchantmentById(int i);

    @NotNull
    Collection getEnchantments();

    @Nullable
    IItem getItemById(int i);

    @NotNull
    Collection getItemRegistryKeys();

    @NotNull
    Collection getBlockRegistryKeys();

    void disableStandardItemLighting();

    @NotNull
    String formatI18n(@NotNull String s, @NotNull String... astring);

    void sessionServiceJoinServer(@NotNull GameProfile gameprofile, @NotNull String s, @NotNull String s1);

    @NotNull
    IPotion getPotionById(int i);

    void enableStandardItemLighting();

    @NotNull
    String scoreboardFormatPlayerName(@Nullable ITeam iteam, @NotNull String s);

    void disableFastRender();

    @NotNull
    IIChatComponent jsonToComponent(@NotNull String s);

    void setActiveTextureLightMapTexUnit();

    void setActiveTextureDefaultTexUnit();

    boolean canAddItemToSlot(@NotNull ISlot islot, @NotNull IItemStack iitemstack, boolean flag);

    @NotNull
    Object getIdFromItem(@NotNull IItem iitem);
}
