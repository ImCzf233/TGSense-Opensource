package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.minecraft.util.TabCompleter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ TabCompleter.class})
public abstract class MixinTabCompleter {

    @Shadow
    protected List completions;
    @Shadow
    protected boolean requestedCompletions;
    @Shadow
    protected boolean didComplete;

    @Shadow
    public abstract void setCompletions(String... astring);

    @Inject(
        method = { "complete"},
        at = {             @At("HEAD")}
    )
    private void complete(CallbackInfo ci) {
        this.completions.sort(Comparator.comparing((s) -> {
            return Boolean.valueOf(!LiquidBounce.fileManager.friendsConfig.isFriend(s));
        }));
    }

    @Inject(
        method = { "requestCompletions"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void handleClientCommandCompletion(String prefix, CallbackInfo callbackInfo) {
        if (LiquidBounce.commandManager.autoComplete(prefix)) {
            this.requestedCompletions = true;
            String[] latestAutoComplete = LiquidBounce.commandManager.getLatestAutoComplete();

            if (prefix.toLowerCase().endsWith(latestAutoComplete[latestAutoComplete.length - 1].toLowerCase())) {
                return;
            }

            this.setCompletions(latestAutoComplete);
            callbackInfo.cancel();
        }

    }

    @Inject(
        method = { "setCompletions"},
        at = {             @At(
                value = "INVOKE",
                target = "Lnet/minecraft/util/TabCompleter;complete()V",
                shift = Shift.BEFORE
            )},
        cancellable = true
    )
    private void onAutocompleteResponse(String[] autoCompleteResponse, CallbackInfo callbackInfo) {
        if (LiquidBounce.commandManager.getLatestAutoComplete().length != 0) {
            callbackInfo.cancel();
        }

    }
}
