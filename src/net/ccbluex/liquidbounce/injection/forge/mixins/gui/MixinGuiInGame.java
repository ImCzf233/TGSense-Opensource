package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.features.module.modules.render.NoScoreboard;
import net.ccbluex.liquidbounce.ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
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
@Mixin({ GuiIngame.class})
public abstract class MixinGuiInGame extends MixinGui {

    @Shadow
    @Final
    protected static ResourceLocation WIDGETS_TEX_PATH;
    @Shadow
    @Final
    protected Minecraft mc;

    @Shadow
    protected abstract void renderHotbarItem(int i, int j, float f, EntityPlayer entityplayer, ItemStack itemstack);

    @Inject(
        method = { "renderScoreboard"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void renderScoreboard(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(HUD.class).getState() || NoScoreboard.INSTANCE.getState()) {
            callbackInfo.cancel();
        }

    }

    @Inject(
        method = { "renderHotbar"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void renderTooltip(ScaledResolution sr, float partialTicks, CallbackInfo callbackInfo) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);

        if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer && hud.getState() && ((Boolean) hud.getBlackHotbarValue().get()).booleanValue()) {
            EntityPlayer entityPlayer = (EntityPlayer) this.mc.getRenderViewEntity();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            ItemStack offHandItemStack = entityPlayer.getHeldItemOffhand();
            EnumHandSide enumhandside = entityPlayer.getPrimaryHand().opposite();
            int middleScreen = sr.getScaledWidth() / 2;
            float f = this.zLevel;
            boolean j = true;
            boolean k = true;

            this.zLevel = -90.0F;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderUtils.originalRoundedRect((float) (middleScreen - 91), (float) (sr.getScaledHeight() - 24), (float) (middleScreen + 90), (float) sr.getScaledHeight(), 5.0F, Integer.MIN_VALUE);
            RenderUtils.originalRoundedRect((float) (middleScreen - 91 - 1 + entityPlayer.inventory.currentItem * 20 + 1), (float) (sr.getScaledHeight() - 24), (float) (middleScreen - 91 - 1 + entityPlayer.inventory.currentItem * 20 + 22), (float) (sr.getScaledHeight() - 22 - 1 + 24), 5.0F, Integer.MAX_VALUE);
            this.mc.getTextureManager().bindTexture(MixinGuiInGame.WIDGETS_TEX_PATH);
            int f1;
            int i2;

            if (!offHandItemStack.isEmpty()) {
                if (enumhandside == EnumHandSide.LEFT) {
                    f1 = middleScreen - 91 - 29;
                } else {
                    f1 = middleScreen + 91;
                }

                i2 = sr.getScaledHeight() - 23;
                RenderUtils.originalRoundedRect((float) f1, (float) i2, (float) (f1 + 29), (float) (i2 + 24), 5.0F, Integer.MIN_VALUE);
            }

            this.zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
            RenderHelper.enableGUIStandardItemLighting();

            int j2;

            for (f1 = 0; f1 < 9; ++f1) {
                i2 = middleScreen - 90 + f1 * 20 + 2;
                j2 = sr.getScaledHeight() - 16 - 3;
                this.renderHotbarItem(i2, j2, partialTicks, entityPlayer, (ItemStack) entityPlayer.inventory.mainInventory.get(f1));
            }

            if (!offHandItemStack.isEmpty()) {
                f1 = sr.getScaledHeight() - 16 - 3;
                if (enumhandside == EnumHandSide.LEFT) {
                    this.renderHotbarItem(middleScreen - 91 - 26, f1, partialTicks, entityPlayer, offHandItemStack);
                } else {
                    this.renderHotbarItem(middleScreen + 91 + 10, f1, partialTicks, entityPlayer, offHandItemStack);
                }
            }

            if (this.mc.gameSettings.attackIndicator == 2) {
                float f = this.mc.player.getCooledAttackStrength(0.0F);

                if (f < 1.0F) {
                    i2 = sr.getScaledHeight() - 20;
                    j2 = middleScreen + 91 + 6;
                    if (enumhandside == EnumHandSide.RIGHT) {
                        j2 = middleScreen - 91 - 22;
                    }

                    this.mc.getTextureManager().bindTexture(Gui.ICONS);
                    int k1 = (int) (f * 19.0F);

                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    this.drawTexturedModalRect(j2, i2, 0, 94, 18, 18);
                    this.drawTexturedModalRect(j2, i2 + 18 - k1, 18, 112 - k1, 18, k1);
                }
            }

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            this.callRender2DEvent(partialTicks);
            callbackInfo.cancel();
        }

    }

    @Inject(
        method = { "renderHotbar"},
        at = {             @At("RETURN")}
    )
    private void renderTooltipPost(ScaledResolution sr, float partialTicks, CallbackInfo callbackInfo) {
        this.callRender2DEvent(partialTicks);
    }

    private void callRender2DEvent(float partialTicks) {
        if (!ClassUtils.hasClass("net.labymod.api.LabyModAPI")) {
            LiquidBounce.eventManager.callEvent(new Render2DEvent(partialTicks));
            AWTFontRenderer.Companion.garbageCollectionTick();
        }

    }

    @Overwrite
    protected void renderPotionEffects(ScaledResolution p_renderPotionEffects_1_) {}

    @Inject(
        method = { "renderPumpkinOverlay"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void renderPumpkinOverlay(CallbackInfo callbackInfo) {
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);

        if (antiBlind.getState() && ((Boolean) antiBlind.getPumpkinEffect().get()).booleanValue()) {
            callbackInfo.cancel();
        }

    }
}
