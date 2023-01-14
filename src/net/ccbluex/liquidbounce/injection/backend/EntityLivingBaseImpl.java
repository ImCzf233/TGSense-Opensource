package net.ccbluex.liquidbounce.injection.backend;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.entity.IEnumCreatureAttribute;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumHand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000h\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\tH\u0016J\u0010\u0010?\u001a\u00020\u001f2\u0006\u0010@\u001a\u00020AH\u0016J\u0012\u0010B\u001a\u0004\u0018\u00010\t2\u0006\u0010C\u001a\u00020DH\u0016J\u0012\u0010E\u001a\u0004\u0018\u00010F2\u0006\u0010G\u001a\u00020\u001bH\u0016J\u0010\u0010H\u001a\u00020\u001f2\u0006\u0010C\u001a\u00020DH\u0016J\u0010\u0010I\u001a\u00020=2\u0006\u0010J\u001a\u00020\u001bH\u0016J\b\u0010K\u001a\u00020=H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR$\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u0010\"\u0004\b\u0019\u0010\u0012R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010 R\u0014\u0010!\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010 R$\u0010\"\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b#\u0010\u0010\"\u0004\b$\u0010\u0012R\u0014\u0010%\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\u0010R\u0014\u0010\'\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\u001dR\u0014\u0010)\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010\u0010R\u0014\u0010+\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\u0010R$\u0010-\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b.\u0010\u0010\"\u0004\b/\u0010\u0012R$\u00100\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b1\u0010\u0010\"\u0004\b2\u0010\u0012R$\u00103\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b4\u0010\u0010\"\u0004\b5\u0010\u0012R\u0016\u00106\u001a\u0004\u0018\u0001078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0014\u0010:\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b;\u0010\u001d¨\u0006L"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/EntityLivingBaseImpl;", "T", "Lnet/minecraft/entity/EntityLivingBase;", "Lnet/ccbluex/liquidbounce/injection/backend/EntityImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "wrapped", "(Lnet/minecraft/entity/EntityLivingBase;)V", "activePotionEffects", "", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "getActivePotionEffects", "()Ljava/util/Collection;", "value", "", "cameraPitch", "getCameraPitch", "()F", "setCameraPitch", "(F)V", "creatureAttribute", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/IEnumCreatureAttribute;", "getCreatureAttribute", "()Lnet/ccbluex/liquidbounce/api/minecraft/entity/IEnumCreatureAttribute;", "health", "getHealth", "setHealth", "hurtTime", "", "getHurtTime", "()I", "isOnLadder", "", "()Z", "isSwingInProgress", "jumpMovementFactor", "getJumpMovementFactor", "setJumpMovementFactor", "maxHealth", "getMaxHealth", "maxHurtTime", "getMaxHurtTime", "moveForward", "getMoveForward", "moveStrafing", "getMoveStrafing", "prevRotationYawHead", "getPrevRotationYawHead", "setPrevRotationYawHead", "renderYawOffset", "getRenderYawOffset", "setRenderYawOffset", "rotationYawHead", "getRotationYawHead", "setRotationYawHead", "team", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "getTeam", "()Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "totalArmorValue", "getTotalArmorValue", "addPotionEffect", "", "effect", "canEntityBeSeen", "it", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getActivePotionEffect", "potion", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotion;", "getEquipmentInSlot", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "index", "isPotionActive", "removePotionEffectClient", "id", "swingItem", "LiquidBounce"}
)
public class EntityLivingBaseImpl extends EntityImpl implements IEntityLivingBase {

    public int getTotalArmorValue() {
        return ((EntityLivingBase) this.getWrapped()).getTotalArmorValue();
    }

    public float getMaxHealth() {
        return ((EntityLivingBase) this.getWrapped()).getMaxHealth();
    }

    public float getPrevRotationYawHead() {
        return ((EntityLivingBase) this.getWrapped()).prevRotationYawHead;
    }

    public void setPrevRotationYawHead(float value) {
        ((EntityLivingBase) this.getWrapped()).prevRotationYawHead = value;
    }

    public float getRenderYawOffset() {
        return ((EntityLivingBase) this.getWrapped()).renderYawOffset;
    }

    public void setRenderYawOffset(float value) {
        ((EntityLivingBase) this.getWrapped()).renderYawOffset = value;
    }

    @NotNull
    public Collection getActivePotionEffects() {
        Collection collection = ((EntityLivingBase) this.getWrapped()).getActivePotionEffects();

        Intrinsics.checkExpressionValueIsNotNull(collection, "wrapped.activePotionEffects");
        return (Collection) (new WrappedCollection(collection, (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    public boolean isSwingInProgress() {
        return ((EntityLivingBase) this.getWrapped()).isSwingInProgress;
    }

    public float getCameraPitch() {
        return ((EntityLivingBase) this.getWrapped()).cameraPitch;
    }

    public void setCameraPitch(float value) {
        ((EntityLivingBase) this.getWrapped()).cameraPitch = value;
    }

    @Nullable
    public ITeam getTeam() {
        Team team = ((EntityLivingBase) this.getWrapped()).getTeam();
        ITeam iteam;

        if (team != null) {
            Team $this$wrap$iv = team;
            boolean $i$f$wrap = false;

            iteam = (ITeam) (new TeamImpl($this$wrap$iv));
        } else {
            iteam = null;
        }

        return iteam;
    }

    @NotNull
    public IEnumCreatureAttribute getCreatureAttribute() {
        EnumCreatureAttribute enumcreatureattribute = ((EntityLivingBase) this.getWrapped()).getCreatureAttribute();

        Intrinsics.checkExpressionValueIsNotNull(enumcreatureattribute, "wrapped.creatureAttribute");
        EnumCreatureAttribute $this$wrap$iv = enumcreatureattribute;
        boolean $i$f$wrap = false;

        return (IEnumCreatureAttribute) (new EnumCreatureAttributeImpl($this$wrap$iv));
    }

    public int getHurtTime() {
        return ((EntityLivingBase) this.getWrapped()).hurtTime;
    }

    public int getMaxHurtTime() {
        return ((EntityLivingBase) this.getWrapped()).maxHurtTime;
    }

    public boolean isOnLadder() {
        return ((EntityLivingBase) this.getWrapped()).isOnLadder();
    }

    public float getJumpMovementFactor() {
        return ((EntityLivingBase) this.getWrapped()).jumpMovementFactor;
    }

    public void setJumpMovementFactor(float value) {
        ((EntityLivingBase) this.getWrapped()).jumpMovementFactor = value;
    }

    public float getMoveStrafing() {
        return ((EntityLivingBase) this.getWrapped()).moveStrafing;
    }

    public float getMoveForward() {
        return ((EntityLivingBase) this.getWrapped()).moveForward;
    }

    public float getHealth() {
        return ((EntityLivingBase) this.getWrapped()).getHealth();
    }

    public void setHealth(float value) {
        ((EntityLivingBase) this.getWrapped()).setHealth(value);
    }

    public float getRotationYawHead() {
        return ((EntityLivingBase) this.getWrapped()).rotationYawHead;
    }

    public void setRotationYawHead(float value) {
        ((EntityLivingBase) this.getWrapped()).rotationYawHead = value;
    }

    public boolean canEntityBeSeen(@NotNull IEntity it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.getWrapped();
        boolean $i$f$unwrap = false;
        Entity entity = ((EntityImpl) it).getWrapped();

        return entitylivingbase.canEntityBeSeen(entity);
    }

    public boolean isPotionActive(@NotNull IPotion potion) {
        Intrinsics.checkParameterIsNotNull(potion, "potion");
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.getWrapped();
        boolean $i$f$unwrap = false;
        Potion potion = ((PotionImpl) potion).getWrapped();

        return entitylivingbase.isPotionActive(potion);
    }

    public void swingItem() {
        ((EntityLivingBase) this.getWrapped()).swingArm(EnumHand.MAIN_HAND);
    }

    @Nullable
    public IPotionEffect getActivePotionEffect(@NotNull IPotion potion) {
        Intrinsics.checkParameterIsNotNull(potion, "potion");
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.getWrapped();
        boolean $i$f$wrap = false;
        Potion potion = ((PotionImpl) potion).getWrapped();
        PotionEffect potioneffect = entitylivingbase.getActivePotionEffect(potion);
        IPotionEffect ipotioneffect;

        if (potioneffect != null) {
            PotionEffect $this$wrap$iv = potioneffect;

            $i$f$wrap = false;
            ipotioneffect = (IPotionEffect) (new PotionEffectImpl($this$wrap$iv));
        } else {
            ipotioneffect = null;
        }

        return ipotioneffect;
    }

    public void removePotionEffectClient(int id) {
        ((EntityLivingBase) this.getWrapped()).removeActivePotionEffect(Potion.getPotionById(id));
    }

    public void addPotionEffect(@NotNull IPotionEffect effect) {
        Intrinsics.checkParameterIsNotNull(effect, "effect");
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.getWrapped();
        boolean $i$f$unwrap = false;
        PotionEffect potioneffect = ((PotionEffectImpl) effect).getWrapped();

        entitylivingbase.addPotionEffect(potioneffect);
    }

    @Nullable
    public IItemStack getEquipmentInSlot(int index) {
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.getWrapped();
        boolean $i$f$wrap = false;
        EntityEquipmentSlot entityequipmentslot;

        switch (index) {
        case 0:
            entityequipmentslot = EntityEquipmentSlot.FEET;
            break;

        case 1:
            entityequipmentslot = EntityEquipmentSlot.LEGS;
            break;

        case 2:
            entityequipmentslot = EntityEquipmentSlot.CHEST;
            break;

        case 3:
            entityequipmentslot = EntityEquipmentSlot.HEAD;
            break;

        case 4:
            entityequipmentslot = EntityEquipmentSlot.MAINHAND;
            break;

        case 5:
            entityequipmentslot = EntityEquipmentSlot.OFFHAND;
            break;

        default:
            throw (Throwable) (new IllegalArgumentException("Invalid armorType " + index));
        }

        EntityEquipmentSlot entityequipmentslot1 = entityequipmentslot;
        ItemStack itemstack = entitylivingbase.getItemStackFromSlot(entityequipmentslot1);

        Intrinsics.checkExpressionValueIsNotNull(itemstack, "wrapped.getItemStackFrom�?.toEntityEquipmentSlot())");
        ItemStack $this$wrap$iv = itemstack;

        $i$f$wrap = false;
        return (IItemStack) (new ItemStackImpl($this$wrap$iv));
    }

    public EntityLivingBaseImpl(@NotNull EntityLivingBase wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Entity) wrapped);
    }
}
