package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "NameTags",
    description = "Changes the scale of the nametags so you can always read them.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0018\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/NameTag;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "borderValue", "botValue", "clearNamesValue", "distanceValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "healthValue", "pingValue", "scaleValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "renderNameTag", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "tag", "", "LiquidBounce"}
)
public final class NameTag extends Module {

    private final BoolValue healthValue = new BoolValue("Health", true);
    private final BoolValue pingValue = new BoolValue("Ping", true);
    private final BoolValue distanceValue = new BoolValue("Distance", false);
    private final BoolValue armorValue = new BoolValue("Armor", true);
    private final BoolValue clearNamesValue = new BoolValue("ClearNames", false);
    private final FontValue fontValue;
    private final BoolValue borderValue;
    private final FloatValue scaleValue;
    private final BoolValue botValue;

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        GL11.glPushAttrib(8192);
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        Iterator iterator = iworldclient.getLoadedEntityList().iterator();

        while (iterator.hasNext()) {
            IEntity entity = (IEntity) iterator.next();

            if (EntityUtils.isSelected(entity, false) && (!AntiBot.isBot(entity.asEntityLivingBase()) || ((Boolean) this.botValue.get()).booleanValue())) {
                IEntityLivingBase ientitylivingbase = entity.asEntityLivingBase();
                String s;
                IIChatComponent iichatcomponent;

                if (((Boolean) this.clearNamesValue.get()).booleanValue()) {
                    iichatcomponent = entity.getDisplayName();
                    s = ColorUtils.stripColor(iichatcomponent != null ? iichatcomponent.getUnformattedText() : null);
                    if (s == null) {
                        continue;
                    }
                } else {
                    iichatcomponent = entity.getDisplayName();
                    if (iichatcomponent == null) {
                        continue;
                    }

                    s = iichatcomponent.getUnformattedText();
                }

                this.renderNameTag(ientitylivingbase, s);
            }
        }

        GL11.glPopMatrix();
        GL11.glPopAttrib();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private final void renderNameTag(IEntityLivingBase entity, String tag) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            IFontRenderer fontRenderer = (IFontRenderer) this.fontValue.get();
            boolean bot = AntiBot.isBot(entity);
            String nameColor = bot ? "§3" : (entity.isInvisible() ? "§6" : (entity.isSneaking() ? "§4" : "§7"));
            int ping = MinecraftInstance.classProvider.isEntityPlayer(entity) ? PlayerExtensionKt.getPing(entity.asEntityPlayer()) : 0;
            String distanceText = ((Boolean) this.distanceValue.get()).booleanValue() ? "§7" + MathKt.roundToInt(thePlayer.getDistanceToEntity((IEntity) entity)) + "m " : "";
            String pingText = ((Boolean) this.pingValue.get()).booleanValue() && MinecraftInstance.classProvider.isEntityPlayer(entity) ? (ping > 200 ? "§c" : (ping > 100 ? "§e" : "§a")) + ping + "ms §7" : "";
            String healthText = ((Boolean) this.healthValue.get()).booleanValue() ? "§7§c " + (int) entity.getHealth() + " HP" : "";
            String botText = bot ? " §c§lBot" : "";
            String text = distanceText + pingText + nameColor + tag + healthText + botText;

            GL11.glPushMatrix();
            ITimer timer = MinecraftInstance.mc.getTimer();
            IRenderManager renderManager = MinecraftInstance.mc.getRenderManager();

            GL11.glTranslated(entity.getLastTickPosX() + (entity.getPosX() - entity.getLastTickPosX()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosX(), entity.getLastTickPosY() + (entity.getPosY() - entity.getLastTickPosY()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosY() + (double) entity.getEyeHeight() + 0.55D, entity.getLastTickPosZ() + (entity.getPosZ() - entity.getLastTickPosZ()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosZ());
            GL11.glRotatef(-MinecraftInstance.mc.getRenderManager().getPlayerViewY(), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(MinecraftInstance.mc.getRenderManager().getPlayerViewX(), 1.0F, 0.0F, 0.0F);
            float distance = thePlayer.getDistanceToEntity((IEntity) entity) * 0.25F;

            if (distance < 1.0F) {
                distance = 1.0F;
            }

            float scale = distance / 100.0F * ((Number) this.scaleValue.get()).floatValue();

            GL11.glScalef(-scale, -scale, scale);
            AWTFontRenderer.Companion.setAssumeNonVolatile(true);
            float width = (float) fontRenderer.getStringWidth(text) * 0.5F;

            GL11.glDisable(3553);
            GL11.glEnable(3042);
            if (((Boolean) this.borderValue.get()).booleanValue()) {
                RenderUtils.quickDrawBorderedRect(-width - 2.0F, -2.0F, width + 4.0F, (float) fontRenderer.getFontHeight() + 2.0F, 2.0F, (new Color(255, 255, 255, 90)).getRGB(), Integer.MIN_VALUE);
            } else {
                RenderUtils.quickDrawRect(-width - 2.0F, -2.0F, width + 4.0F, (float) fontRenderer.getFontHeight() + 2.0F, Integer.MIN_VALUE);
            }

            GL11.glEnable(3553);
            fontRenderer.drawString(text, 1.0F + -width, Intrinsics.areEqual(fontRenderer, Fonts.minecraftFont) ? 1.0F : 1.5F, 16777215, true);
            AWTFontRenderer.Companion.setAssumeNonVolatile(false);
            if (((Boolean) this.armorValue.get()).booleanValue() && MinecraftInstance.classProvider.isEntityPlayer(entity)) {
                MinecraftInstance.mc.getRenderItem().setZLevel(-147.0F);
                int[] indices = new int[] { 0, 1, 2, 3, 5, 4};
                int[] aint = indices;
                int i = indices.length;

                for (int j = 0; j < i; ++j) {
                    int index = aint[j];
                    IItemStack iitemstack = entity.getEquipmentInSlot(index);

                    if (iitemstack != null) {
                        IItemStack equipmentInSlot = iitemstack;

                        MinecraftInstance.mc.getRenderItem().renderItemAndEffectIntoGUI(equipmentInSlot, -50 + index * 20, -22);
                    }
                }

                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                GlStateManager.enableTexture2D();
            }

            GL11.glPopMatrix();
        }
    }

    public NameTag() {
        IFontRenderer ifontrenderer = Fonts.font40;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font40, "Fonts.font40");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.borderValue = new BoolValue("Border", true);
        this.scaleValue = new FloatValue("Scale", 1.0F, 1.0F, 4.0F);
        this.botValue = new BoolValue("Bots", true);
    }
}
