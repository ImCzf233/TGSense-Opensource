package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import me.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.EntityUtils2;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "BetterHotBar",
    description = "me.Skid",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0006J\u000e\u0010 \u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0006J\u0010\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020#H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/BetterHotBar;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "AlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "Black", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "BlueValue", "GreenValue", "Lite", "RedValue", "bluecat", "colorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "crygirl", "fontvalue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "girlvalue", "happygirl", "liuli", "modeValue", "move0", "move1", "move2", "offsetValue", "rainbowIndex", "rainbowSpeed", "calculateBPS", "", "drawgirl", "", "resource", "drawhud", "onRender", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "LiquidBounce"}
)
public final class BetterHotBar extends Module {

    private final ListValue modeValue = new ListValue("bar", new String[] { "Black", "Lite", "Off"}, "Off");
    private final BoolValue fontvalue = new BoolValue("Info", true);
    private final ListValue girlvalue = new ListValue("girl", new String[] { "Crygirl", "Happygirl", "Move0", "Move1", "Move2", "Bluecat", "Liuli", "Off"}, "Off");
    private final ListValue colorModeValue = new ListValue("InfoColor", new String[] { "Custom", "GodLightSync", "novo", "rainbow", "skyrainbow", "anptherrainbow"}, "Custom");
    private final IntegerValue RedValue = new IntegerValue("R", 255, 0, 255);
    private final IntegerValue GreenValue = new IntegerValue("G", 255, 0, 255);
    private final IntegerValue BlueValue = new IntegerValue("B", 255, 0, 255);
    private final IntegerValue AlphaValue = new IntegerValue("A", 255, 0, 255);
    private final IntegerValue rainbowSpeed = new IntegerValue("RainbowSpeed", 10, 1, 10);
    private final IntegerValue rainbowIndex = new IntegerValue("RainbowIndex", 1, 1, 20);
    private final IntegerValue offsetValue = new IntegerValue("Y-Offset", 36, -50, 100);
    private IResourceLocation Black;
    private IResourceLocation Lite;
    private IResourceLocation crygirl;
    private IResourceLocation happygirl;
    private IResourceLocation bluecat;
    private IResourceLocation liuli;
    private IResourceLocation move0;
    private IResourceLocation move1;
    private IResourceLocation move2;

    @EventTarget
    public final void onRender(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String s = (String) this.colorModeValue.get();
        boolean flag = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            int i;
            label83: {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case 3387450:
                    if (s.equals("novo")) {
                        i = ColorUtils.novoRainbow$default(ColorUtils.INSTANCE, 40, 0.0F, 0.0F, 0, 0, 0.0F, 0.0F, 126, (Object) null).getRGB();
                        break label83;
                    }
                    break;

                case 151913845:
                    if (s.equals("skyrainbow")) {
                        Color color = RenderUtils.skyRainbow(((Number) this.rainbowIndex.get()).intValue(), 1.0F, 1.0F);

                        Intrinsics.checkExpressionValueIsNotNull(color, "me.utils.render.RenderUt…inbowIndex.get(), 1F, 1F)");
                        i = color.getRGB();
                        break label83;
                    }
                    break;

                case 404830547:
                    if (s.equals("anotherrainbow")) {
                        i = ColorUtils.fade(new Color(((Number) this.RedValue.get()).intValue(), ((Number) this.GreenValue.get()).intValue(), ((Number) this.BlueValue.get()).intValue(), ((Number) this.AlphaValue.get()).intValue()), 100, ((Number) this.rainbowIndex.get()).intValue()).getRGB();
                        break label83;
                    }
                    break;

                case 973576630:
                    if (s.equals("rainbow")) {
                        i = ColorUtils.hslRainbow$default(ColorUtils.INSTANCE, ((Number) this.rainbowIndex.get()).intValue(), 0.0F, 0.0F, 100 * ((Number) this.rainbowSpeed.get()).intValue(), 0, 22, (Object) null).getRGB();
                        break label83;
                    }
                    break;

                case 1027608501:
                    if (s.equals("godlightsync")) {
                        i = ColorUtils.INSTANCE.GodLight(40).getRGB();
                        break label83;
                    }
                }

                i = (new Color(((Number) this.RedValue.get()).intValue(), ((Number) this.GreenValue.get()).intValue(), ((Number) this.BlueValue.get()).intValue(), 1)).getRGB();
            }

            int TextColor = i;

            s = (String) this.modeValue.get();
            switch (s.hashCode()) {
            case 2368718:
                if (s.equals("Lite")) {
                    this.drawhud(this.Lite);
                }
                break;

            case 64266207:
                if (s.equals("Black")) {
                    this.drawhud(this.Black);
                }
            }

            s = (String) this.girlvalue.get();
            switch (s.hashCode()) {
            case -1583120538:
                if (s.equals("Crygirl")) {
                    this.drawgirl(this.crygirl);
                }
                break;

            case -96243364:
                if (s.equals("Happygirl")) {
                    this.drawgirl(this.happygirl);
                }
                break;

            case 73431541:
                if (s.equals("Liuli")) {
                    this.drawgirl(this.liuli);
                }
                break;

            case 74534495:
                if (s.equals("Move0")) {
                    this.drawgirl(this.move0);
                }
                break;

            case 74534496:
                if (s.equals("Move1")) {
                    this.drawgirl(this.move1);
                }
                break;

            case 74534497:
                if (s.equals("Move2")) {
                    this.drawgirl(this.move2);
                }
                break;

            case 1648808220:
                if (s.equals("Bluecat")) {
                    this.drawgirl(this.bluecat);
                }
            }

            if (((Boolean) this.fontvalue.get()).booleanValue()) {
                Fonts.com35.drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 5, 495 + ((Number) this.offsetValue.get()).intValue(), TextColor);
                Fonts.com35.drawStringWithShadow("Ping:" + EntityUtils2.getPing((IEntityPlayer) MinecraftInstance.mc.getThePlayer()), 5, 520 + ((Number) this.offsetValue.get()).intValue(), TextColor);
                Fonts.com35.drawStringWithShadow("BPS:" + this.calculateBPS(), 5, 540 + ((Number) this.offsetValue.get()).intValue(), TextColor);
            }

        }
    }

    public final double calculateBPS() {
        if (MinecraftInstance.mc.getThePlayer() != null) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            double d0 = ientityplayersp.getPosX();
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            d0 -= ientityplayersp1.getPrevPosX();
            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            double d1 = ientityplayersp1.getPosZ();
            IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            double bps = Math.hypot(d0, d1 - ientityplayersp2.getPrevPosZ()) * (double) MinecraftInstance.mc.getTimer().getTimerSpeed() * (double) 20;

            return (double) Math.round(bps * 100.0D) / 100.0D;
        } else {
            return 0.0D;
        }
    }

    public final void drawhud(@NotNull IResourceLocation resource) {
        Intrinsics.checkParameterIsNotNull(resource, "resource");
        net.ccbluex.liquidbounce.utils.render.RenderUtils.drawImage(resource, -8, 487 + ((Number) this.offsetValue.get()).intValue(), 1920, 45);
    }

    public final void drawgirl(@NotNull IResourceLocation resource) {
        Intrinsics.checkParameterIsNotNull(resource, "resource");
        net.ccbluex.liquidbounce.utils.render.RenderUtils.drawImage(resource, 570, 415 + ((Number) this.offsetValue.get()).intValue(), 100, 100);
    }

    public BetterHotBar() {
        this.Black = MinecraftInstance.classProvider.createResourceLocation("langya/hud/betterhud1.png");
        this.Lite = MinecraftInstance.classProvider.createResourceLocation("langya/hud/betterhud2.png");
        this.crygirl = MinecraftInstance.classProvider.createResourceLocation("langya/girls/crygirl.png");
        this.happygirl = MinecraftInstance.classProvider.createResourceLocation("langya/girls/happygirl.png");
        this.bluecat = MinecraftInstance.classProvider.createResourceLocation("langya/girls/bluecat.png");
        this.liuli = MinecraftInstance.classProvider.createResourceLocation("langya/girls/liuli.png");
        this.move0 = MinecraftInstance.classProvider.createResourceLocation("langya/girls/0.png");
        this.move1 = MinecraftInstance.classProvider.createResourceLocation("langya/girls/1.png");
        this.move2 = MinecraftInstance.classProvider.createResourceLocation("langya/girls/2.png");
    }
}
