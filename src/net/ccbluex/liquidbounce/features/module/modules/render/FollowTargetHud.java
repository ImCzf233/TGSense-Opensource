package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "FollowTargetHud",
    description = "跟随目标显示",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/FollowTargetHud;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "entityKeep", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "jelloAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "jelloColorValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "scaleValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "targetTicks", "", "translateX", "translateY", "xChange", "", "getPlayerName", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "renderNameTag", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "tag", "LiquidBounce"}
)
public final class FollowTargetHud extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Juul", "Jello", "Material", "Arris", "FDP"}, "Juul");
    private final FontValue fontValue;
    private final BoolValue jelloColorValue;
    private final IntegerValue jelloAlphaValue;
    private final FloatValue scaleValue;
    private final FloatValue translateY;
    private final FloatValue translateX;
    private float xChange;
    private int targetTicks;
    private String entityKeep;

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getThePlayer() != null) {
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            Iterator iterator = iworldclient.getLoadedEntityList().iterator();

            while (iterator.hasNext()) {
                IEntity entity = (IEntity) iterator.next();

                if (EntityUtils.isSelected(entity, false)) {
                    IEntityLivingBase ientitylivingbase = entity.asEntityLivingBase();
                    IIChatComponent iichatcomponent = entity.getDisplayName();

                    if (iichatcomponent != null) {
                        this.renderNameTag(ientitylivingbase, iichatcomponent.getUnformattedText());
                    }
                }
            }

        }
    }

    private final String getPlayerName(EntityLivingBase entity) {
        ITextComponent itextcomponent = entity.getDisplayName();

        Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "entity.displayName");
        String name = itextcomponent.getFormattedText();

        Intrinsics.checkExpressionValueIsNotNull(name, "name");
        return name;
    }

    private final void renderNameTag(IEntityLivingBase entity, String tag) {
        this.xChange = ((Number) this.translateX.get()).floatValue() * (float) 20;
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);

        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        } else {
            KillAura killAura = (KillAura) module;

            if (!(Intrinsics.areEqual(entity, killAura.getTarget()) ^ true)) {
                int fontRenderer;

                if (Intrinsics.areEqual(entity, killAura.getTarget())) {
                    this.entityKeep = String.valueOf(entity.getName());
                    fontRenderer = this.targetTicks++;
                    if (this.targetTicks >= 5) {
                        this.targetTicks = 4;
                    }
                } else if (killAura.getTarget() == null) {
                    fontRenderer = this.targetTicks;
                    this.targetTicks += -1;
                    if (this.targetTicks <= -1) {
                        this.targetTicks = 0;
                        this.entityKeep = "dg636 top";
                    }
                }

                if (this.targetTicks != 0) {
                    IFontRenderer ifontrenderer = (IFontRenderer) this.fontValue.get();
                    IFontRenderer font = Fonts.font30;

                    GL11.glPushMatrix();
                    IRenderManager renderManager = MinecraftInstance.mc.getRenderManager();
                    ITimer timer = MinecraftInstance.mc.getTimer();

                    GL11.glTranslated(entity.getLastTickPosX() + (entity.getPosX() - entity.getLastTickPosX()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosX(), entity.getLastTickPosY() + (entity.getPosY() - entity.getLastTickPosY()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosY() + (double) entity.getEyeHeight() + (double) ((Number) this.translateY.get()).floatValue(), entity.getLastTickPosZ() + (entity.getPosZ() - entity.getLastTickPosZ()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosZ());
                    GL11.glRotatef(-MinecraftInstance.mc.getRenderManager().getPlayerViewY(), 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(MinecraftInstance.mc.getRenderManager().getPlayerViewX(), 1.0F, 0.0F, 0.0F);
                    IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    float distance = ientityplayersp.getDistanceToEntity((IEntity) entity) / 4.0F;

                    if (distance < 1.0F) {
                        distance = 1.0F;
                    }

                    float scale = distance / 150.0F * ((Number) this.scaleValue.get()).floatValue();

                    RenderUtils.disableGlCap(new int[] { 2896, 2929});
                    RenderUtils.enableGlCap(3042);
                    GL11.glBlendFunc(770, 771);
                    IIChatComponent iichatcomponent = entity.getDisplayName();

                    if (iichatcomponent == null) {
                        Intrinsics.throwNpe();
                    }

                    String name = iichatcomponent.getUnformattedText();
                    float healthPercent = entity.getHealth() / entity.getMaxHealth();

                    if (healthPercent > (float) 1) {
                        healthPercent = 1.0F;
                    }

                    String s = (String) this.modeValue.get();
                    boolean hpBarColor = false;

                    if (s == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    } else {
                        String s1 = s.toLowerCase();

                        Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                        s = s1;
                        String name1;
                        float f;
                        float f1;
                        String s2;
                        int i;
                        int j;
                        Color color;

                        switch (s.hashCode()) {
                        case 69458:
                            if (s.equals("FDP")) {
                                GL11.glScalef(-scale * (float) 2, -scale * (float) 2, scale * (float) 2);
                                me.utils.render.RenderUtils.drawRoundedCornerRect(-70.0F, 0.0F, 70.0F, 40.0F, 5.0F, (new Color(0, 0, 0, 95)).getRGB());
                                i = -30 + (int) this.xChange;
                                color = Color.WHITE;
                                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                ifontrenderer.drawString(name, i, 5, color.getRGB());
                                s2 = "Health " + (int) entity.getHealth();
                                i = -30 + (int) this.xChange;
                                j = 5 + font.getFontHeight();
                                color = Color.WHITE;
                                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                ifontrenderer.drawString(s2, i, j, color.getRGB());
                                me.utils.render.RenderUtils.drawRoundedCornerRect(-30.0F + this.xChange, (float) (5 + font.getFontHeight() * 2), -30.0F + this.xChange + healthPercent * 95.0F, 37.0F, 3.0F, ColorUtils.rainbow().getRGB());
                            }
                            break;

                        case 3274018:
                            if (s.equals("juul")) {
                                GL11.glScalef(-scale * (float) 2, -scale * (float) 2, scale * (float) 2);
                                me.utils.render.RenderUtils.drawRoundedCornerRect(-120.0F + this.xChange, -16.0F, -50.0F + this.xChange, 10.0F, 5.0F, (new Color(64, 64, 64, 255)).getRGB());
                                me.utils.render.RenderUtils.drawRoundedCornerRect(-110.0F + this.xChange, 0.0F, -20.0F + this.xChange, 35.0F, 5.0F, (new Color(96, 96, 96, 255)).getRGB());
                                i = -105 + (int) this.xChange;
                                color = Color.WHITE;
                                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                ifontrenderer.drawString("Attacking", i, -13, color.getRGB());
                                i = -106 + (int) this.xChange;
                                color = Color.WHITE;
                                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                ifontrenderer.drawString(tag, i, 10, color.getRGB());
                                String s3 = (float) ((int) (entity.getHealth() * 10.0F)) * 0.1F + " / 20";

                                i = -25 - ifontrenderer.getStringWidth(s3) + (int) this.xChange;
                                color = Color.WHITE;
                                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                ifontrenderer.drawString(s3, i, 22, color.getRGB());
                                StringBuilder stringbuilder = (new StringBuilder()).append("�?");
                                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                                if (ientityplayersp1 == null) {
                                    Intrinsics.throwNpe();
                                }

                                name1 = stringbuilder.append(String.valueOf((float) ((int) (ientityplayersp1.getDistanceToEntity((IEntity) entity) * 10.0F)) * 0.1F)).toString();
                                i = -25 - ifontrenderer.getStringWidth(name1) + (int) this.xChange;
                                color = Color.WHITE;
                                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                ifontrenderer.drawString(name1, i, 10, color.getRGB());
                                me.utils.render.RenderUtils.drawRoundedCornerRect(-104.0F + this.xChange, 22.0F, -50.0F + this.xChange, 30.0F, 1.0F, (new Color(64, 64, 64, 255)).getRGB());
                                f = -104.0F + this.xChange;
                                f1 = -104.0F + healthPercent * (float) 54 + this.xChange;
                                Color color1 = Color.WHITE;

                                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                me.utils.render.RenderUtils.drawRoundedCornerRect(f, 22.0F, f1, 30.0F, 1.0F, color1.getRGB());
                            }
                            break;

                        case 93090635:
                            if (s.equals("arris")) {
                                GL11.glScalef(-scale * (float) 2, -scale * (float) 2, scale * (float) 2);
                                int k = RangesKt.coerceAtLeast(font.getStringWidth(entity.getName() + "  " + healthPercent + " hp"), 75);

                                me.utils.render.RenderUtils.drawRoundedCornerRect(this.xChange, 0.0F, 45.0F + (float) k + this.xChange, 40.0F, 7.0F, (new Color(0, 0, 0, 110)).getRGB());
                                s2 = String.valueOf(entity.getName());
                                i = 40 + (int) this.xChange;
                                color = Color.WHITE;
                                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                font.drawString(s2, i, 5, color.getRGB());
                                String s4 = healthPercent + " hp";
                                boolean flag = false;
                                boolean flag1 = false;
                                boolean $i$a$-also-FollowTargetHud$renderNameTag$1 = false;

                                i = 40 + k - font.getStringWidth(s4) + (int) this.xChange;
                                color = Color.LIGHT_GRAY;
                                Intrinsics.checkExpressionValueIsNotNull(Color.LIGHT_GRAY, "Color.LIGHT_GRAY");
                                font.drawString(s4, i, 5, color.getRGB());
                                Unit unit = Unit.INSTANCE;
                                float f2 = (float) (5 + font.getFontHeight()) + 3.0F;

                                f = 40.0F + this.xChange;
                                f1 = (float) 40 + this.xChange + healthPercent * (float) k;
                                float f3 = f2 + (float) 4;

                                color = Color.GREEN;
                                Intrinsics.checkExpressionValueIsNotNull(Color.GREEN, "Color.GREEN");
                                RenderUtils.drawRect(f, f2, f1, f3, color.getRGB());
                            }
                            break;

                        case 101009364:
                            if (s.equals("jello")) {
                                Color color2 = new Color(255, 255, 255, ((Number) this.jelloAlphaValue.get()).intValue());

                                iichatcomponent = entity.getDisplayName();
                                if (iichatcomponent == null) {
                                    Intrinsics.throwNpe();
                                }

                                name1 = iichatcomponent.getUnformattedText();
                                if (((Boolean) this.jelloColorValue.get()).booleanValue() && StringsKt.startsWith$default(name1, "§", false, 2, (Object) null)) {
                                    byte width = 1;
                                    byte maxWidth = 2;
                                    boolean healthPercent1 = false;

                                    if (name1 == null) {
                                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                    }

                                    s1 = name1.substring(width, maxWidth);
                                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                                    color2 = ColorUtils.colorCode(s1, ((Number) this.jelloAlphaValue.get()).intValue());
                                }

                                Color bgColor = new Color(50, 50, 50, ((Number) this.jelloAlphaValue.get()).intValue());
                                int l = ifontrenderer.getStringWidth(tag);
                                float f4 = (float) l + 4.0F - ((float) (-l) - 4.0F);
                                float f5 = entity.getHealth() / entity.getMaxHealth();

                                GL11.glScalef(-scale * (float) 2, -scale * (float) 2, scale * (float) 2);
                                RenderUtils.drawRect(this.xChange, (float) (-ifontrenderer.getFontHeight()) * 3.0F, (float) l + 8.0F + this.xChange, -3.0F, bgColor);
                                if (f5 > (float) 1) {
                                    f5 = 1.0F;
                                }

                                RenderUtils.drawRect(this.xChange, -3.0F, f4 * f5 + this.xChange, 1.0F, color2);
                                RenderUtils.drawRect(f4 * f5 + this.xChange, -3.0F, (float) l + 8.0F + this.xChange, 1.0F, bgColor);
                                i = 4 + (int) this.xChange;
                                j = -ifontrenderer.getFontHeight() * 2 - 4;
                                color = Color.WHITE;
                                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                ifontrenderer.drawString(tag, i, j, color.getRGB());
                                GL11.glScalef(0.5F, 0.5F, 0.5F);
                                s2 = "Health: " + (int) entity.getHealth();
                                i = 4 + (int) this.xChange;
                                j = -ifontrenderer.getFontHeight() * 2;
                                color = Color.WHITE;
                                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                ifontrenderer.drawString(s2, i, j, color.getRGB());
                            }
                            break;

                        case 299066663:
                            if (s.equals("material")) {
                                GL11.glScalef(-scale * (float) 2, -scale * (float) 2, scale * (float) 2);
                                me.utils.render.RenderUtils.drawRoundedCornerRect(-40.0F + this.xChange, 0.0F, 40.0F + this.xChange, 30.0F, 5.0F, (new Color(72, 72, 72, 220)).getRGB());
                                me.utils.render.RenderUtils.drawRoundedCornerRect(-35.0F + this.xChange, 7.0F, -35.0F + healthPercent * (float) 70 + this.xChange, 12.0F, 2.0F, (new Color(10, 250, 10, 255)).getRGB());
                            }
                        }

                        RenderUtils.resetCaps();
                        GlStateManager.resetColor();
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }

    public FollowTargetHud() {
        IFontRenderer ifontrenderer = Fonts.font30;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font30, "Fonts.font30");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.jelloColorValue = new BoolValue("JelloHPColor", true);
        this.jelloAlphaValue = new IntegerValue("JelloAlpha", 170, 0, 255);
        this.scaleValue = new FloatValue("Scale", 1.0F, 1.0F, 4.0F);
        this.translateY = new FloatValue("TanslateY", 0.55F, -2.0F, 2.0F);
        this.translateX = new FloatValue("TranslateX", 0.0F, -2.0F, 2.0F);
        this.xChange = ((Number) this.translateX.get()).floatValue() * (float) 20;
        this.entityKeep = "yes";
    }
}
