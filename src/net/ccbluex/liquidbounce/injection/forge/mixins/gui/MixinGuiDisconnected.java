package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService.EnumAltService;
import com.thealtening.api.TheAltening;
import com.thealtening.api.data.AccountData;
import java.net.Proxy;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.AutoReconnect;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.util.Session;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.client.config.GuiSlider.ISlider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ GuiDisconnected.class})
public abstract class MixinGuiDisconnected extends MixinGuiScreen {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0");
    @Shadow
    private int textHeight;
    private GuiButton reconnectButton;
    private GuiSlider autoReconnectDelaySlider;
    private GuiButton forgeBypassButton;
    private int reconnectTimer;

    @Inject(
        method = { "initGui"},
        at = {             @At("RETURN")}
    )
    private void initGui(CallbackInfo callbackInfo) {
        this.reconnectTimer = 0;
        this.buttonList.add(this.reconnectButton = new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 22, 98, 20, "Reconnect"));
        this.drawReconnectDelaySlider();
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 44, 98, 20, GuiTheAltening.Companion.getApiKey().isEmpty() ? "Random alt" : "New TheAltening alt"));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 44, 98, 20, "Random username"));
        this.buttonList.add(this.forgeBypassButton = new GuiButton(5, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 66, "Bypass AntiForge: " + (AntiForge.enabled ? "On" : "Off")));
        this.updateSliderText();
    }

    @Inject(
        method = { "actionPerformed"},
        at = {             @At("HEAD")}
    )
    private void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
        switch (button.id) {
        case 1:
            ServerUtils.connectToLastServer();

        case 2:
        default:
            break;

        case 3:
            if (!GuiTheAltening.Companion.getApiKey().isEmpty()) {
                String accounts = GuiTheAltening.Companion.getApiKey();
                TheAltening minecraftAccount = new TheAltening(accounts);

                try {
                    AccountData throwable = minecraftAccount.getAccountData();

                    GuiAltManager.altService.switchService(EnumAltService.THEALTENING);
                    YggdrasilUserAuthentication yggdrasilUserAuthentication = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);

                    yggdrasilUserAuthentication.setUsername(throwable.getToken());
                    yggdrasilUserAuthentication.setPassword("TGSense");
                    yggdrasilUserAuthentication.logIn();
                    this.mc.session = new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
                    LiquidBounce.eventManager.callEvent(new SessionEvent());
                    ServerUtils.connectToLastServer();
                    break;
                } catch (Throwable throwable) {
                    ClientUtils.getLogger().error("Failed to login into random account from TheAltening.", throwable);
                }
            }

            List accounts1 = LiquidBounce.fileManager.accountsConfig.getAccounts();

            if (!accounts1.isEmpty()) {
                MinecraftAccount minecraftAccount1 = (MinecraftAccount) accounts1.get((new Random()).nextInt(accounts1.size()));

                GuiAltManager.login(minecraftAccount1);
                ServerUtils.connectToLastServer();
            }
            break;

        case 4:
            LoginUtils.loginCracked(RandomUtils.randomString(RandomUtils.nextInt(5, 16)));
            ServerUtils.connectToLastServer();
            break;

        case 5:
            AntiForge.enabled = !AntiForge.enabled;
            this.forgeBypassButton.displayString = "Bypass AntiForge: " + (AntiForge.enabled ? "On" : "Off");
            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
        }

    }

    public void updateScreen() {
        if (AutoReconnect.INSTANCE.isEnabled()) {
            ++this.reconnectTimer;
            if (this.reconnectTimer > AutoReconnect.INSTANCE.getDelay() / 50) {
                ServerUtils.connectToLastServer();
            }
        }

    }

    @Inject(
        method = { "drawScreen"},
        at = {             @At("RETURN")}
    )
    private void drawScreen(CallbackInfo callbackInfo) {
        if (AutoReconnect.INSTANCE.isEnabled()) {
            this.updateReconnectButton();
        }

    }

    private void drawReconnectDelaySlider() {
        this.buttonList.add(this.autoReconnectDelaySlider = new GuiSlider(2, this.width / 2 + 2, this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 22, 98, 20, "AutoReconnect: ", "ms", 1000.0D, 60000.0D, (double) AutoReconnect.INSTANCE.getDelay(), false, true, (guiSlider) -> {
            // $FF: Couldn't be decompiled
        }));
    }

    private void updateSliderText() {
        if (this.autoReconnectDelaySlider != null) {
            if (!AutoReconnect.INSTANCE.isEnabled()) {
                this.autoReconnectDelaySlider.displayString = "AutoReconnect: Off";
            } else {
                this.autoReconnectDelaySlider.displayString = "AutoReconnect: " + MixinGuiDisconnected.DECIMAL_FORMAT.format((double) AutoReconnect.INSTANCE.getDelay() / 1000.0D) + "s";
            }

        }
    }

    private void updateReconnectButton() {
        if (this.reconnectButton != null) {
            this.reconnectButton.displayString = "Reconnect" + (AutoReconnect.INSTANCE.isEnabled() ? " (" + (AutoReconnect.INSTANCE.getDelay() / 1000 - this.reconnectTimer / 20) + ")" : "");
        }

    }
}
