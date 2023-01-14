package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import java.awt.Color;
import me.utils.OutlineUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.features.module.modules.render.NameTag;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.ccbluex.liquidbounce.injection.backend.EntityLivingBaseImplKt;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({ RenderLivingBase.class})
public abstract class MixinRendererLivingEntity extends MixinRender {

    @Shadow
    protected ModelBase mainModel;

    @Inject(
        method = { "doRender"},
        at = {             @At("HEAD")}
    )
    private void injectChamsPre(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);

        if (chams.getState() && ((Boolean) chams.getTargetsValue().get()).booleanValue() && EntityUtils.isSelected(EntityLivingBaseImplKt.wrap(entity), false)) {
            GL11.glEnable('è€?');
            GL11.glPolygonOffset(1.0F, -1000000.0F);
        }

    }

    @Inject(
        method = { "doRender"},
        at = {             @At("RETURN")}
    )
    private void injectChamsPost(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);

        if (chams.getState() && ((Boolean) chams.getTargetsValue().get()).booleanValue() && EntityUtils.isSelected(EntityLivingBaseImplKt.wrap(entity), false)) {
            GL11.glPolygonOffset(1.0F, 1000000.0F);
            GL11.glDisable('è€?');
        }

    }

    @Inject(
        method = { "canRenderName"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void canRenderName(EntityLivingBase entity, CallbackInfoReturnable callbackInfoReturnable) {
        if (!ESP.renderNameTags || LiquidBounce.moduleManager.getModule(NameTag.class).getState() && EntityUtils.isSelected(EntityLivingBaseImplKt.wrap(entity), false)) {
            callbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
        }

    }

    @Overwrite
    protected void renderModel(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean visible = !entitylivingbaseIn.isInvisible();
        TrueSight trueSight = (TrueSight) LiquidBounce.moduleManager.getModule(TrueSight.class);
        boolean semiVisible = !visible && (!entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player) || trueSight.getState() && ((Boolean) trueSight.getEntitiesValue().get()).booleanValue());

        if (visible || semiVisible) {
            if (!this.bindEntityTexture(entitylivingbaseIn)) {
                return;
            }

            if (semiVisible) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
                GlStateManager.depthMask(false);
                GL11.glEnable(3042);
                GlStateManager.blendFunc(770, 771);
                GlStateManager.alphaFunc(516, 0.003921569F);
            }

            ESP esp = (ESP) LiquidBounce.moduleManager.getModule(ESP.class);

            if (esp.getState() && EntityUtils.isSelected(EntityLivingBaseImplKt.wrap(entitylivingbaseIn), false)) {
                Minecraft mc = Minecraft.getMinecraft();
                boolean fancyGraphics = mc.gameSettings.fancyGraphics;

                mc.gameSettings.fancyGraphics = false;
                float gamma = mc.gameSettings.gammaSetting;

                mc.gameSettings.gammaSetting = 100000.0F;
                String s = ((String) esp.modeValue.get()).toLowerCase();
                byte b0 = -1;

                switch (s.hashCode()) {
                case -1106245566:
                    if (s.equals("outline")) {
                        b0 = 1;
                    }
                    break;

                case -941784056:
                    if (s.equals("wireframe")) {
                        b0 = 0;
                    }
                }

                switch (b0) {
                case 0:
                    GL11.glPushMatrix();
                    GL11.glPushAttrib(1048575);
                    GL11.glPolygonMode(1032, 6913);
                    GL11.glDisable(3553);
                    GL11.glDisable(2896);
                    GL11.glDisable(2929);
                    GL11.glEnable(2848);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    RenderUtils.glColor(esp.getColor(EntityLivingBaseImplKt.wrap(entitylivingbaseIn)));
                    GL11.glLineWidth(((Float) esp.wireframeWidth.get()).floatValue());
                    this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();
                    break;

                case 1:
                    ClientUtils.disableFastRender();
                    GlStateManager.resetColor();
                    Color color = esp.getColor(EntityLivingBaseImplKt.wrap(entitylivingbaseIn));

                    OutlineUtils.setColor(color);
                    OutlineUtils.renderOne(((Float) esp.outlineWidth.get()).floatValue());
                    this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    OutlineUtils.setColor(color);
                    OutlineUtils.renderTwo();
                    this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    OutlineUtils.setColor(color);
                    OutlineUtils.renderThree();
                    this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    OutlineUtils.setColor(color);
                    OutlineUtils.renderFour(color);
                    this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    OutlineUtils.setColor(color);
                    OutlineUtils.renderFive();
                    OutlineUtils.setColor(Color.WHITE);
                }

                mc.gameSettings.fancyGraphics = fancyGraphics;
                mc.gameSettings.gammaSetting = gamma;
            }

            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            if (semiVisible) {
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
            }
        }

    }
}
