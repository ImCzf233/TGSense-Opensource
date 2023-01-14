package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import me.utils.Debug;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.ItemShow;
import net.ccbluex.liquidbounce.features.module.modules.render.OldHitting;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({ ItemRenderer.class})
public abstract class MixinItemRenderer {

    private float delay;
    private MSTimer rotateTimer = new MSTimer();
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private ItemStack itemStackOffHand;
    @Shadow
    private ItemStack itemStackMainHand;

    @Shadow
    protected abstract void renderMapFirstPerson(float f, float f1, float f2);

    @Shadow
    protected abstract void transformFirstPerson(EnumHandSide enumhandside, float f);

    @Shadow
    protected abstract void transformEatFirstPerson(float f, EnumHandSide enumhandside, ItemStack itemstack);

    @Shadow
    protected abstract void renderArmFirstPerson(float f, float f1, EnumHandSide enumhandside);

    @Shadow
    protected abstract void renderMapFirstPersonSide(float f, EnumHandSide enumhandside, float f1, ItemStack itemstack);

    @Shadow
    protected abstract void transformSideFirstPerson(EnumHandSide enumhandside, float f);

    @Shadow
    public abstract void renderItemSide(EntityLivingBase entitylivingbase, ItemStack itemstack, TransformType transformtype, boolean flag);

    @Overwrite
    public void renderItemInFirstPerson(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_, EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_) {
        boolean flag = hand == EnumHand.MAIN_HAND;
        EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();

        GlStateManager.pushMatrix();
        EntityPlayerSP abstractclientplayer = this.mc.player;

        abstractclientplayer.getSwingProgress(p_187457_5_);
        OldHitting ot = (OldHitting) LiquidBounce.moduleManager.getModule(OldHitting.class);
        KillAura aura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);

        if (ot.getState()) {
            this.doItemRenderGLTranslate();
            this.doItemRenderGLScale();
        }

        if (stack.isEmpty()) {
            if (flag && !player.isInvisible()) {
                this.renderArmFirstPerson(p_187457_7_, p_187457_5_, enumhandside);
            }
        } else if (stack.getItem() == Items.FILLED_MAP) {
            if (flag && this.itemStackOffHand.isEmpty()) {
                this.renderMapFirstPerson(p_187457_3_, p_187457_7_, p_187457_5_);
            } else {
                this.renderMapFirstPersonSide(p_187457_7_, enumhandside, p_187457_5_, stack);
            }
        } else {
            boolean flag1 = enumhandside == EnumHandSide.RIGHT;
            float f1;
            float f21;

            if (player.isHandActive() && player.getItemInUseCount() > 0 && player.getActiveHand() == hand) {
                int f2 = flag1 ? 1 : -1;

                switch (stack.getItemUseAction()) {
                case NONE:
                    this.transformSideFirstPerson(enumhandside, p_187457_7_);
                    if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                        this.rotateItemAnim();
                    }
                    break;

                case EAT:
                case DRINK:
                    this.transformEatFirstPerson(p_187457_2_, enumhandside, stack);
                    this.transformSideFirstPerson(enumhandside, p_187457_7_);
                    if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                        this.rotateItemAnim();
                    }
                    break;

                case BLOCK:
                    this.transformSideFirstPerson(enumhandside, p_187457_7_);
                    break;

                case BOW:
                    this.transformSideFirstPerson(enumhandside, p_187457_7_);
                    GlStateManager.translate((float) f2 * -0.2785682F, 0.18344387F, 0.15731531F);
                    GlStateManager.rotate(-13.935F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate((float) f2 * 35.3F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate((float) f2 * -9.785F, 0.0F, 0.0F, 1.0F);
                    f1 = (float) stack.getMaxItemUseDuration() - ((float) this.mc.player.getItemInUseCount() - p_187457_2_ + 1.0F);
                    f21 = f1 / 20.0F;
                    f21 = (f21 * f21 + f21 * 2.0F) / 3.0F;
                    if (f21 > 1.0F) {
                        f21 = 1.0F;
                    }

                    if (f21 > 0.1F) {
                        float i2 = MathHelper.sin((f1 - 0.1F) * 1.3F);
                        float f3 = f21 - 0.1F;
                        float f4 = i2 * f3;

                        GlStateManager.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                    }

                    GlStateManager.translate(f21 * 0.0F, f21 * 0.0F, f21 * 0.04F);
                    GlStateManager.scale(1.0F, 1.0F, 1.0F + f21 * 0.2F);
                    GlStateManager.rotate((float) f2 * 45.0F, 0.0F, -1.0F, 0.0F);
                    if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                        this.rotateItemAnim();
                    }
                }
            } else if (ot.getState()) {
                if (!aura.getBlockingStatus() && (!this.mc.gameSettings.keyBindUseItem.pressed || ((Boolean) ot.getOnlySword().get()).booleanValue() && !(this.itemStackMainHand.getItem() instanceof ItemSword))) {
                    float f1 = -0.4F * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * 3.1415927F);

                    f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * 6.2831855F);
                    f21 = -0.2F * MathHelper.sin(p_187457_5_ * 3.1415927F);
                    int i1 = flag1 ? 1 : -1;

                    GlStateManager.translate((float) i1 * f1, f1, f21);
                    this.doItemRenderGLTranslate();
                    this.doItemRenderGLScale();
                    this.transformSideFirstPerson(enumhandside, p_187457_7_);
                    this.transformFirstPerson(enumhandside, p_187457_5_);
                    if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                        this.rotateItemAnim();
                    }
                } else {
                    String f = (String) ot.getModeValue().get();
                    byte f2 = -1;

                    switch (f.hashCode()) {
                    case -1051760540:
                        if (f.equals("WindMill")) {
                            f2 = 6;
                        }
                        break;

                    case 2499386:
                        if (f.equals("Push")) {
                            f2 = 5;
                        }
                        break;

                    case 77381960:
                        if (f.equals("Pride")) {
                            f2 = 0;
                        }
                        break;

                    case 78845737:
                        if (f.equals("Remix")) {
                            f2 = 3;
                        }
                        break;

                    case 109526449:
                        if (f.equals("slide")) {
                            f2 = 2;
                        }
                        break;

                    case 281302713:
                        if (f.equals("SideDown")) {
                            f2 = 4;
                        }
                        break;

                    case 1897755483:
                        if (f.equals("Vanilla")) {
                            f2 = 1;
                        }
                    }

                    switch (f2) {
                    case 0:
                        Debug.Pride(enumhandside, p_187457_7_, p_187457_5_);
                        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                            this.rotateItemAnim();
                        }
                        break;

                    case 1:
                        Debug.Vanilla(enumhandside, p_187457_7_, p_187457_5_);
                        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                            this.rotateItemAnim();
                        }
                        break;

                    case 2:
                        Debug.slide2(enumhandside, p_187457_7_, p_187457_5_);
                        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                            this.rotateItemAnim();
                        }
                        break;

                    case 3:
                        Debug.Remix(enumhandside, p_187457_7_, p_187457_5_);
                        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                            this.rotateItemAnim();
                        }

                    case 4:
                        Debug.transformSideFirstPersonBlock(enumhandside, p_187457_7_, p_187457_5_);
                        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                            this.rotateItemAnim();
                        }
                        break;

                    case 5:
                        Debug.Push(enumhandside, p_187457_7_, p_187457_5_);
                        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                            this.rotateItemAnim();
                        }
                        break;

                    case 6:
                        double i = Math.sin((double) (p_187457_7_ * p_187457_7_) * 3.141592653589793D);

                        Debug.WindMill(enumhandside, 0.0F, 0.0F);
                        GlStateManager.rotate((float) (Math.sqrt(i) * 10.0D * 40.0D), 1.0F, -0.0F, 2.0F);
                        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState()) {
                            this.rotateItemAnim();
                        }
                    }
                }
            } else {
                this.transformSideFirstPerson(enumhandside, p_187457_7_);
                this.transformFirstPerson(enumhandside, p_187457_5_);
            }

            if (ot.getState()) {
                if (stack.getItem() instanceof ItemShield && this.itemStackMainHand.getItem() instanceof ItemSword) {
                    GlStateManager.popMatrix();
                    return;
                }

                this.renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
            } else {
                this.renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
            }
        }

        GlStateManager.popMatrix();
    }

    private void rotateItemAnim() {
        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState() && ((String) ItemShow.transformFirstPersonRotate.get()).equalsIgnoreCase("Rotate1")) {
            GlStateManager.rotate(this.delay, 0.0F, 1.0F, 0.0F);
        }

        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState() && ((String) ItemShow.transformFirstPersonRotate.get()).equalsIgnoreCase("Rotate2")) {
            GlStateManager.rotate(this.delay, 1.0F, 1.0F, 0.0F);
        }

        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState() && ((String) ItemShow.transformFirstPersonRotate.get()).equalsIgnoreCase("Rotate3")) {
            GlStateManager.rotate(this.delay, 360.0F, -360.0F, -200.0F);
        }

        if (LiquidBounce.moduleManager.getModule(ItemShow.class).getState() && ((String) ItemShow.transformFirstPersonRotate.get()).equalsIgnoreCase("Custom")) {
            GlStateManager.rotate(this.delay, ((Float) ItemShow.customRotate1.get()).floatValue(), ((Float) ItemShow.customRotate2.get()).floatValue(), ((Float) ItemShow.customRotate3.get()).floatValue());
        }

        if (this.rotateTimer.hasTimePassed(1L)) {
            ++this.delay;
            this.delay += ((Float) ItemShow.SpeedRotate.get()).floatValue();
            this.rotateTimer.reset();
        }

        if (this.delay > 360.0F) {
            this.delay = 0.0F;
        }

    }

    @Inject(
        method = { "renderFireInFirstPerson"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void renderFireInFirstPerson(CallbackInfo callbackInfo) {
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);

        if (antiBlind.getState() && ((Boolean) antiBlind.getFireEffect().get()).booleanValue()) {
            callbackInfo.cancel();
        }

    }

    private void doItemRenderGLTranslate() {
        GlStateManager.translate(((Float) Animations.itemPosX.get()).floatValue(), ((Float) Animations.itemPosY.get()).floatValue(), ((Float) Animations.itemPosZ.get()).floatValue());
    }

    private void doItemRenderGLScale() {
        GlStateManager.scale(((Float) Animations.Scale.get()).floatValue(), ((Float) Animations.Scale.get()).floatValue(), ((Float) Animations.Scale.get()).floatValue());
    }
}
