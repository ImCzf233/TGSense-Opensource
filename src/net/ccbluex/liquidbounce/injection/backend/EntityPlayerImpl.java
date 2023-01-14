package net.ccbluex.liquidbounce.injection.backend;

import com.mojang.authlib.GameProfile;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IPlayerCapabilities;
import net.ccbluex.liquidbounce.api.minecraft.inventory.IContainer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.stats.IStatBase;
import net.ccbluex.liquidbounce.api.minecraft.util.IFoodStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.util.FoodStats;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0006\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\u0010\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020\u0017H\u0016J\u0018\u0010R\u001a\u00020P2\u0006\u0010S\u001a\u00020\b2\u0006\u0010T\u001a\u00020\bH\u0016J\u0010\u0010U\u001a\u00020\b2\u0006\u0010V\u001a\u00020\bH\u0016J\b\u0010W\u001a\u00020PH\u0016J\u0010\u0010X\u001a\u00020P2\u0006\u0010Q\u001a\u00020\u0017H\u0016J\u0010\u0010Y\u001a\u00020P2\u0006\u0010Q\u001a\u00020ZH\u0016J\b\u0010[\u001a\u00020PH\u0016J\b\u0010\\\u001a\u00020PH\u0016J\u0010\u0010]\u001a\u00020P2\u0006\u0010^\u001a\u00020_H\u0016R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0016\u0010\"\u001a\u0004\u0018\u00010#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020\'8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020+8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u00100R\u0014\u00101\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u00100R\u0014\u00102\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u00100R\u0016\u00103\u001a\u0004\u0018\u00010#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u0010%R$\u00106\u001a\u0002052\u0006\u0010\u0007\u001a\u0002058V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u0014\u0010;\u001a\u0002058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u00108R\u0016\u0010=\u001a\u0004\u0018\u00010+8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b>\u0010-R\u0014\u0010?\u001a\u00020@8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u0010BR$\u0010C\u001a\u0002052\u0006\u0010\u0007\u001a\u0002058V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bD\u00108\"\u0004\bE\u0010:R$\u0010F\u001a\u00020/2\u0006\u0010\u0007\u001a\u00020/8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bG\u00100\"\u0004\bH\u0010IR\u0014\u0010J\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u00100R$\u0010L\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bM\u0010\u000b\"\u0004\bN\u0010\r¨\u0006`"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/EntityPlayerImpl;", "T", "Lnet/minecraft/entity/player/EntityPlayer;", "Lnet/ccbluex/liquidbounce/injection/backend/EntityLivingBaseImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "wrapped", "(Lnet/minecraft/entity/player/EntityPlayer;)V", "value", "", "cameraYaw", "getCameraYaw", "()F", "setCameraYaw", "(F)V", "capabilities", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IPlayerCapabilities;", "getCapabilities", "()Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IPlayerCapabilities;", "displayNameString", "", "getDisplayNameString", "()Ljava/lang/String;", "fishEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getFishEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "foodStats", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IFoodStats;", "getFoodStats", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IFoodStats;", "gameProfile", "Lcom/mojang/authlib/GameProfile;", "getGameProfile", "()Lcom/mojang/authlib/GameProfile;", "heldItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "getHeldItem", "()Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "inventory", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IInventoryPlayer;", "getInventory", "()Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IInventoryPlayer;", "inventoryContainer", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/IContainer;", "getInventoryContainer", "()Lnet/ccbluex/liquidbounce/api/minecraft/inventory/IContainer;", "isBlocking", "", "()Z", "isPlayerSleeping", "isUsingItem", "itemInUse", "getItemInUse", "", "itemInUseCount", "getItemInUseCount", "()I", "setItemInUseCount", "(I)V", "itemInUseDuration", "getItemInUseDuration", "openContainer", "getOpenContainer", "prevChasingPosY", "", "getPrevChasingPosY", "()D", "sleepTimer", "getSleepTimer", "setSleepTimer", "sleeping", "getSleeping", "setSleeping", "(Z)V", "spectator", "getSpectator", "speedInAir", "getSpeedInAir", "setSpeedInAir", "attackTargetEntityWithCurrentItem", "", "entity", "fall", "distance", "damageMultiplier", "getCooledAttackStrength", "adjustTicks", "jump", "onCriticalHit", "onEnchantmentCritical", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "resetCooldown", "stopUsingItem", "triggerAchievement", "stat", "Lnet/ccbluex/liquidbounce/api/minecraft/stats/IStatBase;", "LiquidBounce"}
)
public class EntityPlayerImpl extends EntityLivingBaseImpl implements IEntityPlayer {

    @NotNull
    public GameProfile getGameProfile() {
        GameProfile gameprofile = ((EntityPlayer) this.getWrapped()).getGameProfile();

        Intrinsics.checkExpressionValueIsNotNull(gameprofile, "wrapped.gameProfile");
        return gameprofile;
    }

    @Nullable
    public IEntity getFishEntity() {
        EntityFishHook entityfishhook = ((EntityPlayer) this.getWrapped()).fishEntity;
        IEntity ientity;

        if (entityfishhook != null) {
            Entity $this$wrap$iv = (Entity) entityfishhook;
            boolean $i$f$wrap = false;

            ientity = (IEntity) (new EntityImpl($this$wrap$iv));
        } else {
            ientity = null;
        }

        return ientity;
    }

    @NotNull
    public IFoodStats getFoodStats() {
        FoodStats foodstats = ((EntityPlayer) this.getWrapped()).getFoodStats();

        Intrinsics.checkExpressionValueIsNotNull(foodstats, "wrapped.foodStats");
        FoodStats $this$wrap$iv = foodstats;
        boolean $i$f$wrap = false;

        return (IFoodStats) (new FoodStatsImpl($this$wrap$iv));
    }

    public double getPrevChasingPosY() {
        return ((EntityPlayer) this.getWrapped()).prevChasingPosY;
    }

    public int getSleepTimer() {
        return ((EntityPlayer) this.getWrapped()).sleepTimer;
    }

    public void setSleepTimer(int value) {
        ((EntityPlayer) this.getWrapped()).sleepTimer = value;
    }

    public boolean getSleeping() {
        return ((EntityPlayer) this.getWrapped()).sleeping;
    }

    public void setSleeping(boolean value) {
        ((EntityPlayer) this.getWrapped()).sleeping = value;
    }

    public boolean isPlayerSleeping() {
        return ((EntityPlayer) this.getWrapped()).isPlayerSleeping();
    }

    public float getSpeedInAir() {
        return ((EntityPlayer) this.getWrapped()).speedInAir;
    }

    public void setSpeedInAir(float value) {
        ((EntityPlayer) this.getWrapped()).speedInAir = value;
    }

    public float getCameraYaw() {
        return ((EntityPlayer) this.getWrapped()).cameraYaw;
    }

    public void setCameraYaw(float value) {
        ((EntityPlayer) this.getWrapped()).cameraYaw = value;
    }

    public boolean isBlocking() {
        return ((EntityPlayer) this.getWrapped()).isActiveItemStackBlocking();
    }

    public int getItemInUseCount() {
        return ((EntityPlayer) this.getWrapped()).getItemInUseCount();
    }

    public void setItemInUseCount(int value) {
        ((EntityPlayer) this.getWrapped()).activeItemStackUseCount = value;
    }

    @Nullable
    public IItemStack getItemInUse() {
        ItemStack itemstack = ((EntityPlayer) this.getWrapped()).getActiveItemStack();
        IItemStack iitemstack;

        if (itemstack != null) {
            ItemStack $this$wrap$iv = itemstack;
            boolean $i$f$wrap = false;

            iitemstack = (IItemStack) (new ItemStackImpl($this$wrap$iv));
        } else {
            iitemstack = null;
        }

        return iitemstack;
    }

    @NotNull
    public IPlayerCapabilities getCapabilities() {
        PlayerCapabilities playercapabilities = ((EntityPlayer) this.getWrapped()).capabilities;

        Intrinsics.checkExpressionValueIsNotNull(playercapabilities, "wrapped.capabilities");
        PlayerCapabilities $this$wrap$iv = playercapabilities;
        boolean $i$f$wrap = false;

        return (IPlayerCapabilities) (new PlayerCapabilitiesImpl($this$wrap$iv));
    }

    @Nullable
    public IItemStack getHeldItem() {
        ItemStack itemstack = ((EntityPlayer) this.getWrapped()).getHeldItemMainhand();
        IItemStack iitemstack;

        if (itemstack != null) {
            ItemStack $this$wrap$iv = itemstack;
            boolean $i$f$wrap = false;

            iitemstack = (IItemStack) (new ItemStackImpl($this$wrap$iv));
        } else {
            iitemstack = null;
        }

        return iitemstack;
    }

    public boolean isUsingItem() {
        return ((EntityPlayer) this.getWrapped()).isHandActive();
    }

    @NotNull
    public IContainer getInventoryContainer() {
        Container container = ((EntityPlayer) this.getWrapped()).inventoryContainer;

        Intrinsics.checkExpressionValueIsNotNull(container, "wrapped.inventoryContainer");
        Container $this$wrap$iv = container;
        boolean $i$f$wrap = false;

        return (IContainer) (new ContainerImpl($this$wrap$iv));
    }

    @NotNull
    public IInventoryPlayer getInventory() {
        InventoryPlayer inventoryplayer = ((EntityPlayer) this.getWrapped()).inventory;

        Intrinsics.checkExpressionValueIsNotNull(inventoryplayer, "wrapped.inventory");
        InventoryPlayer $this$wrap$iv = inventoryplayer;
        boolean $i$f$wrap = false;

        return (IInventoryPlayer) (new InventoryPlayerImpl($this$wrap$iv));
    }

    @Nullable
    public IContainer getOpenContainer() {
        Container container = ((EntityPlayer) this.getWrapped()).openContainer;
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

    public int getItemInUseDuration() {
        return ((EntityPlayer) this.getWrapped()).getItemInUseMaxCount();
    }

    @NotNull
    public String getDisplayNameString() {
        String s = ((EntityPlayer) this.getWrapped()).getDisplayNameString();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.displayNameString");
        return s;
    }

    public boolean getSpectator() {
        return ((EntityPlayer) this.getWrapped()).isSpectator();
    }

    public void stopUsingItem() {
        ((EntityPlayer) this.getWrapped()).stopActiveHand();
    }

    public void onCriticalHit(@NotNull IEntity entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        EntityPlayer entityplayer = (EntityPlayer) this.getWrapped();
        boolean $i$f$unwrap = false;
        Entity entity = ((EntityImpl) entity).getWrapped();

        entityplayer.onCriticalHit(entity);
    }

    public void onEnchantmentCritical(@NotNull IEntityLivingBase entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        EntityPlayer entityplayer = (EntityPlayer) this.getWrapped();
        boolean $i$f$unwrap = false;
        EntityLivingBase entitylivingbase = (EntityLivingBase) ((EntityLivingBaseImpl) entity).getWrapped();

        entityplayer.onEnchantmentCritical((Entity) entitylivingbase);
    }

    public void attackTargetEntityWithCurrentItem(@NotNull IEntity entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        EntityPlayer entityplayer = (EntityPlayer) this.getWrapped();
        boolean $i$f$unwrap = false;
        Entity entity = ((EntityImpl) entity).getWrapped();

        entityplayer.attackTargetEntityWithCurrentItem(entity);
    }

    public void fall(float distance, float damageMultiplier) {
        ((EntityPlayer) this.getWrapped()).fall(distance, damageMultiplier);
    }

    public void triggerAchievement(@NotNull IStatBase stat) {
        Intrinsics.checkParameterIsNotNull(stat, "stat");
        EntityPlayer entityplayer = (EntityPlayer) this.getWrapped();
        boolean $i$f$unwrap = false;
        StatBase statbase = ((StatBaseImpl) stat).getWrapped();

        entityplayer.addStat(statbase);
    }

    public void jump() {
        ((EntityPlayer) this.getWrapped()).jump();
    }

    public float getCooledAttackStrength(float adjustTicks) {
        return ((EntityPlayer) this.getWrapped()).getCooledAttackStrength(adjustTicks);
    }

    public void resetCooldown() {
        ((EntityPlayer) this.getWrapped()).resetCooldown();
    }

    public EntityPlayerImpl(@NotNull EntityPlayer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((EntityLivingBase) wrapped);
    }
}
