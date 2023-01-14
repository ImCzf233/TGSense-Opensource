package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.mojang.authlib.GameProfile;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ NetworkPlayerInfo.class})
public class MixinNetworkPlayerInfo {

    @Shadow
    @Final
    private GameProfile gameProfile;

    @Inject(
        method = { "getLocationSkin"},
        cancellable = true,
        at = {             @At("HEAD")}
    )
    private void injectSkinProtect(CallbackInfoReturnable cir) {
        NameProtect nameProtect = (NameProtect) LiquidBounce.moduleManager.getModule(NameProtect.class);

        if (nameProtect.getState() && ((Boolean) nameProtect.skinProtectValue.get()).booleanValue() && (((Boolean) nameProtect.allPlayersValue.get()).booleanValue() || Objects.equals(this.gameProfile.getId(), Minecraft.getMinecraft().getSession().getProfile().getId()))) {
            cir.setReturnValue(DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
            cir.cancel();
        }

    }
}
