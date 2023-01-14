package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.utils.OutlineUtils;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.tileentity.ITileEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "StorageESP",
    description = "Allows you to see chests, dispensers, etc. through walls.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/StorageESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chestValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "dispenserValue", "enderChestValue", "furnaceValue", "hopperValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "shulkerBoxValue", "getColor", "Ljava/awt/Color;", "tileEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/tileentity/ITileEntity;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"}
)
public final class StorageESP extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Box", "OtherBox", "Outline", "ShaderOutline", "ShaderGlow", "2D", "WireFrame"}, "Outline");
    private final BoolValue chestValue = new BoolValue("Chest", true);
    private final BoolValue enderChestValue = new BoolValue("EnderChest", true);
    private final BoolValue furnaceValue = new BoolValue("Furnace", true);
    private final BoolValue dispenserValue = new BoolValue("Dispenser", true);
    private final BoolValue hopperValue = new BoolValue("Hopper", true);
    private final BoolValue shulkerBoxValue = new BoolValue("ShulkerBox", true);

    private final Color getColor(ITileEntity tileEntity) {
        return ((Boolean) this.chestValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityChest(tileEntity) && !ChestAura.INSTANCE.getClickedBlocks().contains(tileEntity.getPos()) ? new Color(0, 66, 255) : (((Boolean) this.enderChestValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityEnderChest(tileEntity) && !ChestAura.INSTANCE.getClickedBlocks().contains(tileEntity.getPos()) ? Color.MAGENTA : (((Boolean) this.furnaceValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityFurnace(tileEntity) ? Color.BLACK : (((Boolean) this.dispenserValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityDispenser(tileEntity) ? Color.BLACK : (((Boolean) this.hopperValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityHopper(tileEntity) ? Color.GRAY : (((Boolean) this.shulkerBoxValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityShulkerBox(tileEntity) ? (new Color(110, 77, 110)).brighter() : null)))));
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");

        try {
            String mode = (String) this.modeValue.get();

            if (StringsKt.equals(mode, "outline", true)) {
                ClientUtils.disableFastRender();
                OutlineUtils.checkSetupFBO();
            }

            float gamma = MinecraftInstance.mc.getGameSettings().getGammaSetting();

            MinecraftInstance.mc.getGameSettings().setGammaSetting(100000.0F);
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            Iterator iterator = iworldclient.getLoadedTileEntityList().iterator();

            String s;
            WBlockPos wblockpos;
            int i;
            Color color;

            while (iterator.hasNext()) {
                ITileEntity entity = (ITileEntity) iterator.next();
                Color color1 = this.getColor(entity);

                if (color1 != null) {
                    Color color = color1;

                    if (!MinecraftInstance.classProvider.isTileEntityChest(entity) && !MinecraftInstance.classProvider.isTileEntityEnderChest(entity)) {
                        RenderUtils.drawBlockBox(entity.getPos(), color, !StringsKt.equals(mode, "otherbox", true));
                    } else {
                        boolean flag = false;

                        if (mode == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        s = mode.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                        String entityShadow = s;

                        switch (entityShadow.hashCode()) {
                        case -1171135301:
                            if (!entityShadow.equals("otherbox")) {
                                continue;
                            }
                            break;

                        case -1106245566:
                            if (entityShadow.equals("outline")) {
                                RenderUtils.glColor(color);
                                OutlineUtils.renderOne(3.0F);
                                MinecraftInstance.functions.renderTileEntity(entity, event.getPartialTicks(), -1);
                                OutlineUtils.renderTwo();
                                MinecraftInstance.functions.renderTileEntity(entity, event.getPartialTicks(), -1);
                                OutlineUtils.renderThree();
                                MinecraftInstance.functions.renderTileEntity(entity, event.getPartialTicks(), -1);
                                OutlineUtils.renderFour(color);
                                MinecraftInstance.functions.renderTileEntity(entity, event.getPartialTicks(), -1);
                                OutlineUtils.renderFive();
                                OutlineUtils.setColor(Color.WHITE);
                            }
                            continue;

                        case -941784056:
                            if (entityShadow.equals("wireframe")) {
                                GL11.glPushMatrix();
                                GL11.glPushAttrib(1048575);
                                GL11.glPolygonMode(1032, 6913);
                                GL11.glDisable(3553);
                                GL11.glDisable(2896);
                                GL11.glDisable(2929);
                                GL11.glEnable(2848);
                                GL11.glEnable(3042);
                                GL11.glBlendFunc(770, 771);
                                RenderUtils.glColor(color);
                                GL11.glLineWidth(1.5F);
                                MinecraftInstance.functions.renderTileEntity(entity, event.getPartialTicks(), -1);
                                GL11.glPopAttrib();
                                GL11.glPopMatrix();
                            }
                            continue;

                        case 1650:
                            if (entityShadow.equals("2d")) {
                                wblockpos = entity.getPos();
                                i = color.getRGB();
                                color = Color.BLACK;
                                Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
                                RenderUtils.draw2D(wblockpos, i, color.getRGB());
                            }
                            continue;

                        case 97739:
                            if (!entityShadow.equals("box")) {
                                continue;
                            }
                            break;

                        default:
                            continue;
                        }

                        RenderUtils.drawBlockBox(entity.getPos(), color, !StringsKt.equals(mode, "otherbox", true));
                    }
                }
            }

            iworldclient = MinecraftInstance.mc.getTheWorld();
            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            iterator = iworldclient.getLoadedEntityList().iterator();

            while (iterator.hasNext()) {
                IEntity entity1 = (IEntity) iterator.next();

                if (MinecraftInstance.classProvider.isEntityMinecartChest(entity1)) {
                    boolean entityShadow1 = false;

                    if (mode == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s = mode.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                    String color1 = s;

                    switch (color1.hashCode()) {
                    case -1171135301:
                        if (!color1.equals("otherbox")) {
                            continue;
                        }
                        break;

                    case -1106245566:
                        if (color1.equals("outline")) {
                            entityShadow1 = MinecraftInstance.mc.getGameSettings().getEntityShadows();
                            MinecraftInstance.mc.getGameSettings().setEntityShadows(false);
                            RenderUtils.glColor(new Color(0, 66, 255));
                            OutlineUtils.renderOne(3.0F);
                            MinecraftInstance.mc.getRenderManager().renderEntityStatic(entity1, MinecraftInstance.mc.getTimer().getRenderPartialTicks(), true);
                            OutlineUtils.renderTwo();
                            MinecraftInstance.mc.getRenderManager().renderEntityStatic(entity1, MinecraftInstance.mc.getTimer().getRenderPartialTicks(), true);
                            OutlineUtils.renderThree();
                            MinecraftInstance.mc.getRenderManager().renderEntityStatic(entity1, MinecraftInstance.mc.getTimer().getRenderPartialTicks(), true);
                            OutlineUtils.renderFour(new Color(0, 66, 255));
                            MinecraftInstance.mc.getRenderManager().renderEntityStatic(entity1, MinecraftInstance.mc.getTimer().getRenderPartialTicks(), true);
                            OutlineUtils.renderFive();
                            OutlineUtils.setColor(Color.WHITE);
                            MinecraftInstance.mc.getGameSettings().setEntityShadows(entityShadow1);
                        }
                        continue;

                    case -941784056:
                        if (color1.equals("wireframe")) {
                            entityShadow1 = MinecraftInstance.mc.getGameSettings().getEntityShadows();
                            MinecraftInstance.mc.getGameSettings().setEntityShadows(false);
                            GL11.glPushMatrix();
                            GL11.glPushAttrib(1048575);
                            GL11.glPolygonMode(1032, 6913);
                            GL11.glDisable(3553);
                            GL11.glDisable(2896);
                            GL11.glDisable(2929);
                            GL11.glEnable(2848);
                            GL11.glEnable(3042);
                            GL11.glBlendFunc(770, 771);
                            RenderUtils.glColor(new Color(0, 66, 255));
                            MinecraftInstance.mc.getRenderManager().renderEntityStatic(entity1, MinecraftInstance.mc.getTimer().getRenderPartialTicks(), true);
                            RenderUtils.glColor(new Color(0, 66, 255));
                            GL11.glLineWidth(1.5F);
                            MinecraftInstance.mc.getRenderManager().renderEntityStatic(entity1, MinecraftInstance.mc.getTimer().getRenderPartialTicks(), true);
                            GL11.glPopAttrib();
                            GL11.glPopMatrix();
                            MinecraftInstance.mc.getGameSettings().setEntityShadows(entityShadow1);
                        }
                        continue;

                    case 1650:
                        if (color1.equals("2d")) {
                            wblockpos = entity1.getPosition();
                            i = (new Color(0, 66, 255)).getRGB();
                            color = Color.BLACK;
                            Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
                            RenderUtils.draw2D(wblockpos, i, color.getRGB());
                        }
                        continue;

                    case 97739:
                        if (!color1.equals("box")) {
                            continue;
                        }
                        break;

                    default:
                        continue;
                    }

                    RenderUtils.drawEntityBox(entity1, new Color(0, 66, 255), !StringsKt.equals(mode, "otherbox", true));
                }
            }

            RenderUtils.glColor(new Color(255, 255, 255, 255));
            MinecraftInstance.mc.getGameSettings().setGammaSetting(gamma);
        } catch (Exception exception) {
            ;
        }

    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String mode = (String) this.modeValue.get();
        FramebufferShader framebuffershader = StringsKt.equals(mode, "shaderoutline", true) ? (FramebufferShader) OutlineShader.OUTLINE_SHADER : (FramebufferShader) (StringsKt.equals(mode, "shaderglow", true) ? GlowShader.GLOW_SHADER : null);

        if (framebuffershader != null) {
            FramebufferShader shader = framebuffershader;
            float partialTicks = event.getPartialTicks();
            IRenderManager renderManager = MinecraftInstance.mc.getRenderManager();

            shader.startDraw(event.getPartialTicks());

            try {
                HashMap ex = new HashMap();
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                Iterator iterator = iworldclient.getLoadedTileEntityList().iterator();

                Color color;

                while (iterator.hasNext()) {
                    ITileEntity tileEntity = (ITileEntity) iterator.next();
                    Color color = this.getColor(tileEntity);

                    if (color != null) {
                        color = color;
                        if (!ex.containsKey(color)) {
                            ex.put(color, new ArrayList());
                        }

                        Object object = ex.get(color);

                        if (object == null) {
                            Intrinsics.throwNpe();
                        }

                        ((ArrayList) object).add(tileEntity);
                    }
                }

                Map color1 = (Map) ex;
                boolean arr = false;

                iterator = color1.entrySet().iterator();

                while (iterator.hasNext()) {
                    Entry tileEntity1 = (Entry) iterator.next();
                    boolean flag = false;

                    color = (Color) tileEntity1.getKey();
                    flag = false;
                    ArrayList arr1 = (ArrayList) tileEntity1.getValue();

                    shader.startDraw(partialTicks);
                    Iterator iterator1 = arr1.iterator();

                    while (iterator1.hasNext()) {
                        ITileEntity entity = (ITileEntity) iterator1.next();
                        IRenderManager irendermanager = MinecraftInstance.mc.getRenderManager();

                        Intrinsics.checkExpressionValueIsNotNull(entity, "entity");
                        irendermanager.renderEntityAt(entity, (double) entity.getPos().getX() - renderManager.getRenderPosX(), (double) entity.getPos().getY() - renderManager.getRenderPosY(), (double) entity.getPos().getZ() - renderManager.getRenderPosZ(), partialTicks);
                    }

                    shader.stopDraw(color, StringsKt.equals(mode, "shaderglow", true) ? 2.5F : 1.5F, 1.0F);
                }
            } catch (Exception exception) {
                ClientUtils.getLogger().error("An error occurred while rendering all storages for shader esp", (Throwable) exception);
            }

            shader.stopDraw(new Color(0, 66, 255), StringsKt.equals(mode, "shaderglow", true) ? 2.5F : 1.5F, 1.0F);
        }
    }
}
