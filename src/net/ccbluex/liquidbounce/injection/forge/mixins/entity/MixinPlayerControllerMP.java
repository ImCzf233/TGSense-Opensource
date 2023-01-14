package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.ccbluex.liquidbounce.injection.backend.EntityImplKt;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({ PlayerControllerMP.class})
public class MixinPlayerControllerMP {

    @Inject(
        method = { "attackEntity"},
        at = {             @At(
                value = "INVOKE",
                target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;syncCurrentPlayItem()V"
            )}
    )
    private void attackEntity(EntityPlayer entityPlayer, Entity targetEntity, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new AttackEvent(EntityImplKt.wrap(targetEntity)));
    }

    @Inject(
        method = { "getIsHittingBlock"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void getIsHittingBlock(CallbackInfoReturnable callbackInfoReturnable) {
        if (LiquidBounce.moduleManager.getModule(AbortBreaking.class).getState()) {
            callbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
        }

    }

    @Inject(
        method = { "windowClick"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void windowClick(int windowId, int slotId, int mouseButton, ClickType type, EntityPlayer player, CallbackInfoReturnable callbackInfo) {
        ClickWindowEvent event = new ClickWindowEvent(windowId, slotId, mouseButton, BackendExtentionsKt.toInt(type));

        LiquidBounce.eventManager.callEvent(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }

    }
}
