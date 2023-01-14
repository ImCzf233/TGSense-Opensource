package net.ccbluex.liquidbounce.injection.backend;

import com.mojang.authlib.GameProfile;
import java.lang.reflect.Field;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
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
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextComponent.Serializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\b\u0010\u000f\u001a\u00020\rH\u0016J)\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u0014\"\u00020\u0011H\u0016¢\u0006\u0002\u0010\u0015J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001b\u001a\u00020\u0011H\u0016J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\u0019H\u0016J\u0012\u0010\"\u001a\u0004\u0018\u00010 2\u0006\u0010#\u001a\u00020\u0011H\u0016J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016J\u0010\u0010%\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u0017H\u0016J\u0015\u0010\'\u001a\u00020\u00192\u0006\u0010(\u001a\u00020)H\u0016¢\u0006\u0002\u0010*J\u0012\u0010+\u001a\u0004\u0018\u00010)2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010,\u001a\u0004\u0018\u00010)2\u0006\u0010\u001b\u001a\u00020\u0011H\u0016J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016J\u001a\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\n2\u0006\u00101\u001a\u000202H\u0016J\u0012\u00103\u001a\u0004\u0018\u00010)2\u0006\u00104\u001a\u00020\u001eH\u0016J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u0019H\u0016J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u0011H\u0016J \u0010;\u001a\u00020\r2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020/2\u0006\u0010?\u001a\u00020\u0019H\u0016J\u001a\u0010@\u001a\u00020\u00112\b\u0010A\u001a\u0004\u0018\u00010B2\u0006\u0010C\u001a\u00020\u0011H\u0016J \u0010D\u001a\u00020\r2\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020\u00112\u0006\u0010H\u001a\u00020\u0011H\u0016J\b\u0010I\u001a\u00020\rH\u0016J\b\u0010J\u001a\u00020\rH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006K"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ExtractedFunctionsImpl;", "Lnet/ccbluex/liquidbounce/api/IExtractedFunctions;", "()V", "fastRenderField", "Ljava/lang/reflect/Field;", "canAddItemToSlot", "", "slotIn", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/ISlot;", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "stackSizeMatters", "disableFastRender", "", "disableStandardItemLighting", "enableStandardItemLighting", "formatI18n", "", "key", "values", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getBlockById", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "id", "", "getBlockFromName", "name", "getBlockRegistryKeys", "", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getEnchantmentById", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "enchantID", "getEnchantmentByLocation", "location", "getEnchantments", "getIdFromBlock", "block", "getIdFromItem", "item", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "(Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;)Ljava/lang/Integer;", "getItemById", "getItemByName", "getItemRegistryKeys", "getModifierForCreature", "", "heldItem", "creatureAttribute", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/IEnumCreatureAttribute;", "getObjectFromItemRegistry", "res", "getPotionById", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotion;", "potionID", "jsonToComponent", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "toString", "renderTileEntity", "tileEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/tileentity/ITileEntity;", "partialTicks", "destroyStage", "scoreboardFormatPlayerName", "scorePlayerTeam", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "playerName", "sessionServiceJoinServer", "profile", "Lcom/mojang/authlib/GameProfile;", "token", "sessionHash", "setActiveTextureDefaultTexUnit", "setActiveTextureLightMapTexUnit", "LiquidBounce"}
)
public final class ExtractedFunctionsImpl implements IExtractedFunctions {

    private static Field fastRenderField;
    public static final ExtractedFunctionsImpl INSTANCE;

    @Nullable
    public IBlock getBlockById(int id) {
        Block block = Block.getBlockById(id);
        BlockImpl blockimpl;

        if (block != null) {
            Block block1 = block;
            boolean flag = false;
            boolean flag1 = false;
            boolean $i$a$-unknown-ExtractedFunctionsImpl$getBlockById$1 = false;

            blockimpl = new BlockImpl(block1);
        } else {
            blockimpl = null;
        }

        return (IBlock) blockimpl;
    }

    public int getIdFromBlock(@NotNull IBlock block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        boolean $i$f$unwrap = false;

        return Block.getIdFromBlock(((BlockImpl) block).getWrapped());
    }

    @NotNull
    public Integer getIdFromItem(@NotNull IItem item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        boolean $i$f$unwrap = false;

        return Integer.valueOf(Item.getIdFromItem(((ItemImpl) item).getWrapped()));
    }

    public float getModifierForCreature(@Nullable IItemStack heldItem, @NotNull IEnumCreatureAttribute creatureAttribute) {
        Intrinsics.checkParameterIsNotNull(creatureAttribute, "creatureAttribute");
        boolean $i$f$unwrap;
        ItemStack itemstack;

        if (heldItem != null) {
            $i$f$unwrap = false;
            if (heldItem == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.ItemStackImpl");
            }

            itemstack = ((ItemStackImpl) heldItem).getWrapped();
        } else {
            itemstack = null;
        }

        ItemStack itemstack1 = itemstack;

        $i$f$unwrap = false;
        EnumCreatureAttribute enumcreatureattribute = ((EnumCreatureAttributeImpl) creatureAttribute).getWrapped();

        return EnchantmentHelper.getModifierForCreature(itemstack1, enumcreatureattribute);
    }

    @Nullable
    public IItem getObjectFromItemRegistry(@NotNull IResourceLocation res) {
        Intrinsics.checkParameterIsNotNull(res, "res");
        RegistryNamespaced registrynamespaced = Item.REGISTRY;
        boolean $i$f$wrap = false;
        ResourceLocation resourcelocation = ((ResourceLocationImpl) res).getWrapped();
        Item item = (Item) registrynamespaced.getObject(resourcelocation);
        IItem iitem;

        if (item != null) {
            Item $this$wrap$iv = item;

            $i$f$wrap = false;
            iitem = (IItem) (new ItemImpl($this$wrap$iv));
        } else {
            iitem = null;
        }

        return iitem;
    }

    public void renderTileEntity(@NotNull ITileEntity tileEntity, float partialTicks, int destroyStage) {
        Intrinsics.checkParameterIsNotNull(tileEntity, "tileEntity");
        TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
        boolean $i$f$unwrap = false;
        TileEntity tileentity = ((TileEntityImpl) tileEntity).getWrapped();

        tileentityrendererdispatcher.render(tileentity, partialTicks, destroyStage);
    }

    @Nullable
    public IBlock getBlockFromName(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Block block = Block.getBlockFromName(name);
        IBlock iblock;

        if (block != null) {
            Block $this$wrap$iv = block;
            boolean $i$f$wrap = false;

            iblock = (IBlock) (new BlockImpl($this$wrap$iv));
        } else {
            iblock = null;
        }

        return iblock;
    }

    @Nullable
    public IItem getItemByName(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Object object = Items.class.getField(name).get((Object) null);

        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.Item");
        } else {
            Item $this$wrap$iv = (Item) object;
            boolean $i$f$wrap = false;

            return (IItem) (new ItemImpl($this$wrap$iv));
        }
    }

    @Nullable
    public IEnchantment getEnchantmentByLocation(@NotNull String location) {
        Intrinsics.checkParameterIsNotNull(location, "location");
        Enchantment enchantment = Enchantment.getEnchantmentByLocation(location);
        IEnchantment ienchantment;

        if (enchantment != null) {
            Enchantment $this$wrap$iv = enchantment;
            boolean $i$f$wrap = false;

            ienchantment = (IEnchantment) (new EnchantmentImpl($this$wrap$iv));
        } else {
            ienchantment = null;
        }

        return ienchantment;
    }

    @Nullable
    public IEnchantment getEnchantmentById(int enchantID) {
        Enchantment enchantment = Enchantment.getEnchantmentByID(enchantID);
        IEnchantment ienchantment;

        if (enchantment != null) {
            Enchantment $this$wrap$iv = enchantment;
            boolean $i$f$wrap = false;

            ienchantment = (IEnchantment) (new EnchantmentImpl($this$wrap$iv));
        } else {
            ienchantment = null;
        }

        return ienchantment;
    }

    @NotNull
    public Collection getEnchantments() {
        RegistryNamespaced registrynamespaced = Enchantment.REGISTRY;

        Intrinsics.checkExpressionValueIsNotNull(Enchantment.REGISTRY, "Enchantment.REGISTRY");
        return (Collection) (new WrappedCollection((Collection) registrynamespaced.getKeys(), (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    @NotNull
    public Collection getItemRegistryKeys() {
        RegistryNamespaced registrynamespaced = Item.REGISTRY;

        Intrinsics.checkExpressionValueIsNotNull(Item.REGISTRY, "Item.REGISTRY");
        return (Collection) (new WrappedCollection((Collection) registrynamespaced.getKeys(), (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    @NotNull
    public Collection getBlockRegistryKeys() {
        RegistryNamespacedDefaultedByKey registrynamespaceddefaultedbykey = Block.REGISTRY;

        Intrinsics.checkExpressionValueIsNotNull(Block.REGISTRY, "Block.REGISTRY");
        return (Collection) (new WrappedCollection((Collection) registrynamespaceddefaultedbykey.getKeys(), (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    public void disableStandardItemLighting() {
        RenderHelper.disableStandardItemLighting();
    }

    @NotNull
    public String formatI18n(@NotNull String key, @NotNull String... values) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(values, "values");
        String s = I18n.format(key, new Object[] { values});

        Intrinsics.checkExpressionValueIsNotNull(s, "I18n.format(key, values)");
        return s;
    }

    public void sessionServiceJoinServer(@NotNull GameProfile profile, @NotNull String token, @NotNull String sessionHash) {
        Intrinsics.checkParameterIsNotNull(profile, "profile");
        Intrinsics.checkParameterIsNotNull(token, "token");
        Intrinsics.checkParameterIsNotNull(sessionHash, "sessionHash");
        Minecraft minecraft = Minecraft.getMinecraft();

        Intrinsics.checkExpressionValueIsNotNull(minecraft, "Minecraft.getMinecraft()");
        minecraft.getSessionService().joinServer(profile, token, sessionHash);
    }

    @NotNull
    public IPotion getPotionById(int potionID) {
        Potion potion = Potion.getPotionById(potionID);

        if (potion == null) {
            Intrinsics.throwNpe();
        }

        Intrinsics.checkExpressionValueIsNotNull(potion, "Potion.getPotionById(potionID)!!");
        Potion $this$wrap$iv = potion;
        boolean $i$f$wrap = false;

        return (IPotion) (new PotionImpl($this$wrap$iv));
    }

    public void enableStandardItemLighting() {
        RenderHelper.enableStandardItemLighting();
    }

    @NotNull
    public String scoreboardFormatPlayerName(@Nullable ITeam scorePlayerTeam, @NotNull String playerName) {
        Intrinsics.checkParameterIsNotNull(playerName, "playerName");
        Team team;

        if (scorePlayerTeam != null) {
            boolean $i$f$unwrap = false;

            if (scorePlayerTeam == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.TeamImpl");
            }

            team = ((TeamImpl) scorePlayerTeam).getWrapped();
        } else {
            team = null;
        }

        String s = ScorePlayerTeam.formatPlayerName(team, playerName);

        Intrinsics.checkExpressionValueIsNotNull(s, "ScorePlayerTeam.formatPl…am?.unwrap(), playerName)");
        return s;
    }

    public void disableFastRender() {
        try {
            Field fastRenderer = ExtractedFunctionsImpl.fastRenderField;

            if (fastRenderer != null) {
                if (!fastRenderer.isAccessible()) {
                    fastRenderer.setAccessible(true);
                }

                fastRenderer.setBoolean(Minecraft.getMinecraft().gameSettings, false);
            }
        } catch (IllegalAccessException illegalaccessexception) {
            ;
        }

    }

    @NotNull
    public IIChatComponent jsonToComponent(@NotNull String toString) {
        Intrinsics.checkParameterIsNotNull(toString, "toString");
        ITextComponent itextcomponent = Serializer.jsonToComponent(toString);

        if (itextcomponent == null) {
            Intrinsics.throwNpe();
        }

        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "ITextComponent.Serialize…onToComponent(toString)!!");
        ITextComponent $this$wrap$iv = itextcomponent;
        boolean $i$f$wrap = false;

        return (IIChatComponent) (new IChatComponentImpl($this$wrap$iv));
    }

    public void setActiveTextureLightMapTexUnit() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    }

    @Nullable
    public IItem getItemById(int id) {
        Item item = Item.getItemById(id);

        Intrinsics.checkExpressionValueIsNotNull(item, "Item.getItemById(id)");
        Item $this$wrap$iv = item;
        boolean $i$f$wrap = false;

        return (IItem) (new ItemImpl($this$wrap$iv));
    }

    public void setActiveTextureDefaultTexUnit() {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public boolean canAddItemToSlot(@NotNull ISlot slotIn, @NotNull IItemStack stack, boolean stackSizeMatters) {
        Intrinsics.checkParameterIsNotNull(slotIn, "slotIn");
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        boolean $i$f$unwrap = false;
        Slot slot = ((SlotImpl) slotIn).getWrapped();

        $i$f$unwrap = false;
        ItemStack itemstack = ((ItemStackImpl) stack).getWrapped();

        return Container.canAddItemToSlot(slot, itemstack, stackSizeMatters);
    }

    static {
        ExtractedFunctionsImpl extractedfunctionsimpl = new ExtractedFunctionsImpl();

        INSTANCE = extractedfunctionsimpl;

        try {
            Field declaredField = GameSettings.class.getDeclaredField("ofFastRender");

            ExtractedFunctionsImpl.fastRenderField = declaredField;
            Intrinsics.checkExpressionValueIsNotNull(declaredField, "declaredField");
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
        } catch (NoSuchFieldException nosuchfieldexception) {
            ;
        }

    }
}
