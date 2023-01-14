package net.ccbluex.liquidbounce.injection.forge.mixins.resources;

import com.mojang.authlib.GameProfile;
import java.util.HashMap;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ SkinManager.class})
public class MixinSkinManager {

    @Inject(
        method = { "loadSkinFromCache"},
        cancellable = true,
        at = {             @At("HEAD")}
    )
    private void injectSkinProtect(GameProfile gameProfile, CallbackInfoReturnable cir) {
        if (gameProfile != null) {
            NameProtect nameProtect = (NameProtect) LiquidBounce.moduleManager.getModule(NameProtect.class);

            if (nameProtect.getState() && ((Boolean) nameProtect.skinProtectValue.get()).booleanValue() && (((Boolean) nameProtect.allPlayersValue.get()).booleanValue() || Objects.equals(gameProfile.getId(), Minecraft.getMinecraft().getSession().getProfile().getId()))) {
                cir.setReturnValue(new HashMap());
                cir.cancel();
            }

        }
    }
}
