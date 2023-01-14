package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "ItemESP",
    description = "Allows you to see items through walls.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0012\u0010\u0013\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0014H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/ItemESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "shaderRadiusValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getColor", "Ljava/awt/Color;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"}
)
public final class ItemESP extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Box", "OtherBox", "ShaderOutline", "ShaderGlow"}, "Box");
    private final FloatValue shaderRadiusValue = new FloatValue("ShaderRadius", 2.0F, 0.5F, 5.0F);
    private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 0, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", true);

    private final Color getColor() {
        return ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue());
    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        Color color = this.getColor();
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        Iterator iterator = iworldclient.getLoadedEntityList().iterator();

        while (iterator.hasNext()) {
            IEntity entity = (IEntity) iterator.next();

            if (MinecraftInstance.classProvider.isEntityItem(entity) || MinecraftInstance.classProvider.isEntityArrow(entity)) {
                String s = (String) this.modeValue.get();
                boolean flag = false;

                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case -1171135301:
                    if (s.equals("otherbox")) {
                        RenderUtils.drawEntityBox(entity, color, false);
                    }
                    break;

                case 97739:
                    if (s.equals("box")) {
                        RenderUtils.drawEntityBox(entity, color, true);
                    }
                }
            }
        }

    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        FramebufferShader framebuffershader = StringsKt.equals((String) this.modeValue.get(), "shaderoutline", true) ? (FramebufferShader) OutlineShader.OUTLINE_SHADER : (FramebufferShader) (StringsKt.equals((String) this.modeValue.get(), "shaderglow", true) ? GlowShader.GLOW_SHADER : null);

        if (framebuffershader != null) {
            FramebufferShader shader = framebuffershader;
            float partialTicks = event.getPartialTicks();

            shader.startDraw(partialTicks);

            try {
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                Iterator iterator = iworldclient.getLoadedEntityList().iterator();

                while (iterator.hasNext()) {
                    IEntity ex = (IEntity) iterator.next();

                    if (MinecraftInstance.classProvider.isEntityItem(ex) || MinecraftInstance.classProvider.isEntityArrow(ex)) {
                        MinecraftInstance.mc.getRenderManager().renderEntityStatic(ex, event.getPartialTicks(), true);
                    }
                }
            } catch (Exception exception) {
                ClientUtils.getLogger().error("An error occurred while rendering all item entities for shader esp", (Throwable) exception);
            }

            shader.stopDraw(this.getColor(), ((Number) this.shaderRadiusValue.get()).floatValue(), 1.0F);
        }
    }
}
