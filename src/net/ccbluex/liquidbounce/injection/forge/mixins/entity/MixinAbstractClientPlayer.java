package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.cape.CapeInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.render.Cape;
import net.ccbluex.liquidbounce.features.module.modules.render.NoFOV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.init.Items;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({ AbstractClientPlayer.class})
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer {

    private CapeInfo capeInfo;

    @Inject(
        method = { "getLocationCape"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void getCape(CallbackInfoReturnable callbackInfoReturnable) {
        Cape capeMod = (Cape) LiquidBounce.moduleManager.getModule(Cape.class);

        if (capeMod.getState() && Objects.equals(this.getGameProfile().getName(), Minecraft.getMinecraft().player.getGameProfile().getName())) {
            callbackInfoReturnable.setReturnValue(capeMod.getCapeLocation((String) capeMod.getStyleValue().get()));
        }

    }

    @Inject(
        method = { "getFovModifier"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void getFovModifier(CallbackInfoReturnable callbackInfoReturnable) {
        NoFOV fovModule = (NoFOV) LiquidBounce.moduleManager.getModule(NoFOV.class);

        if (((NoFOV) Objects.requireNonNull(fovModule)).getState()) {
            float newFOV = ((Float) fovModule.getFovValue().get()).floatValue();

            if (!this.isHandActive()) {
                callbackInfoReturnable.setReturnValue(Float.valueOf(newFOV));
                return;
            }

            if (this.getActiveItemStack().getItem() != Items.BOW) {
                callbackInfoReturnable.setReturnValue(Float.valueOf(newFOV));
                return;
            }

            int i = this.getItemInUseCount();
            float f1 = (float) i / 20.0F;

            f1 = f1 > 1.0F ? 1.0F : f1 * f1;
            newFOV *= 1.0F - f1 * 0.15F;
            callbackInfoReturnable.setReturnValue(Float.valueOf(newFOV));
        }

    }

    @Inject(
        method = { "getLocationSkin()Lnet/minecraft/util/ResourceLocation;"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void getSkin(CallbackInfoReturnable callbackInfoReturnable) {
        NameProtect nameProtect = (NameProtect) LiquidBounce.moduleManager.getModule(NameProtect.class);

        if (((NameProtect) Objects.requireNonNull(nameProtect)).getState() && ((Boolean) nameProtect.skinProtectValue.get()).booleanValue()) {
            if (!((Boolean) nameProtect.allPlayersValue.get()).booleanValue() && !Objects.equals(this.getGameProfile().getName(), Minecraft.getMinecraft().player.getGameProfile().getName())) {
                return;
            }

            callbackInfoReturnable.setReturnValue(DefaultPlayerSkin.getDefaultSkin(this.getUniqueID()));
        }

    }
}
