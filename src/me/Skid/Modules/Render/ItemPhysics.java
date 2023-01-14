package me.Skid.Modules.Render;

import java.util.Random;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "ItemPhysics",
    description = "物理掉落",
    category = ModuleCategory.RENDER
)
public class ItemPhysics extends Module {

    private final Random random = new Random();

    public static void handleCameraTransforms(IBakedModel model, TransformType cameraTransformType) {
        model.getItemCameraTransforms().applyTransform(cameraTransformType);
    }

    public ResourceLocation getEntityTexture() {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    public void setPositionAndRotation2(EntityItem item, double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        item.setPosition(x, y, z);
    }

    public void doRender(TextureManager renderer, Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        double rotation = 2.0D;
        EntityItem item = (EntityItem) entity;
        ItemStack itemstack = item.getItem();
        int i;

        if (itemstack != null && itemstack.getItem() != null) {
            i = Item.getIdFromItem(itemstack.getItem()) + itemstack.getMetadata();
        } else {
            i = 187;
        }

        this.random.setSeed((long) i);
        renderer.bindTexture(this.getEntityTexture());
        renderer.getTexture(this.getEntityTexture()).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        IBakedModel ibakedmodel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(itemstack);
        boolean flag2 = ibakedmodel.isGui3d();
        boolean is3D = ibakedmodel.isGui3d();
        int j = this.getModelCount(itemstack);

        GlStateManager.translate((float) x, (float) y, (float) z);
        if (ibakedmodel.isGui3d()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }

        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(item.rotationYaw, 0.0F, 0.0F, 1.0F);
        if (is3D) {
            GlStateManager.translate(0.0D, 0.0D, -0.08D);
        } else {
            GlStateManager.translate(0.0D, 0.0D, -0.04D);
        }

        if (is3D || ItemPhysics.mc2.getRenderManager().options != null) {
            if (is3D) {
                if (!item.onGround) {
                    item.rotationPitch += (float) rotation;
                }
            } else if (!Double.isNaN(item.posX) && !Double.isNaN(item.posY) && !Double.isNaN(item.posZ) && item.world != null) {
                if (item.onGround) {
                    item.rotationPitch = 0.0F;
                } else {
                    rotation *= 2.0D;
                    item.rotationPitch += (float) rotation;
                }
            }

            GlStateManager.rotate(item.rotationPitch, 1.0F, 0.0F, 0.0F);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        for (int k = 0; k < j; ++k) {
            if (flag2) {
                GlStateManager.pushMatrix();
                if (k > 0) {
                    float f4 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f5 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f6 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;

                    GlStateManager.translate(f4, f5, f6);
                }

                handleCameraTransforms(ibakedmodel, TransformType.GROUND);
                ItemPhysics.mc2.getRenderItem().renderItem(itemstack, ibakedmodel);
                GlStateManager.popMatrix();
            } else {
                GlStateManager.pushMatrix();
                if (k > 0) {
                    ;
                }

                handleCameraTransforms(ibakedmodel, TransformType.GROUND);
                Minecraft.getMinecraft().getRenderItem().renderItem(itemstack, ibakedmodel);
                GlStateManager.popMatrix();
                GlStateManager.translate(0.0F, 0.0F, 0.05375F);
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        renderer.bindTexture(this.getEntityTexture());
        renderer.getTexture(this.getEntityTexture()).restoreLastBlurMipmap();
    }

    public int getModelCount(ItemStack stack) {
        byte i = 1;

        if (stack.stackSize > 48) {
            i = 5;
        } else if (stack.stackSize > 32) {
            i = 4;
        } else if (stack.stackSize > 16) {
            i = 3;
        } else if (stack.stackSize > 1) {
            i = 2;
        }

        return i;
    }

    static enum PhysicModes {

        Realistic, Alpha, Simple;
    }
}
