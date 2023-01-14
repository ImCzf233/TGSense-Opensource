package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.text.SimpleDateFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.Tenacity.utils.render.RoundedUtil;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.Palette;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
    name = "ModuleInfos",
    description = "look your info",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u00107\u001a\u000208J\u0012\u00109\u001a\u00020:2\b\u0010;\u001a\u0004\u0018\u00010<H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0007\"\u0004\b\u001b\u0010\tR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0010R\u0011\u0010\"\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0010R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010&\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u0007\"\u0004\b(\u0010\tR\u0011\u0010)\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0010R\u0011\u0010+\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0010R\u000e\u0010-\u001a\u00020.X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020.X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00102\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0007\"\u0004\b4\u0010\tR\u000e\u00105\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006="},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/ModuleInfoS;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "GameInfoRows", "", "GamesPlayed", "getGamesPlayed", "()I", "setGamesPlayed", "(I)V", "KD", "getKD", "setKD", "a", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getA", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "b", "getB", "b2", "getB2", "bgalphaValue", "bgblueValue", "bggreenValue", "bgredValue", "dead", "getDead", "setDead", "fontOffsetValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "g", "getG", "g2", "getG2", "infomode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "killed", "getKilled", "setKilled", "r", "getR", "r2", "getR2", "reverseValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "target", "Lnet/minecraft/entity/Entity;", "testboolean", "win", "getWin", "setWin", "xValue", "yValue", "calculateBPS", "", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "LiquidBounce"}
)
public final class ModuleInfoS extends Module {

    private final ListValue infomode = new ListValue("mode", new String[] { "Moon3.3", "TGSense"}, "Moon3.3");
    private final FloatValue xValue = new FloatValue("x", 100.0F, 0.0F, 800.0F);
    private final FloatValue yValue = new FloatValue("y", 100.0F, 0.0F, 800.0F);
    @NotNull
    private final IntegerValue r = new IntegerValue("Red", 229, 0, 255);
    @NotNull
    private final IntegerValue g = new IntegerValue("Green", 100, 0, 255);
    @NotNull
    private final IntegerValue b = new IntegerValue("Blue", 173, 0, 255);
    @NotNull
    private final IntegerValue r2 = new IntegerValue("RectR", 109, 0, 255);
    @NotNull
    private final IntegerValue g2 = new IntegerValue("RectG", 255, 0, 255);
    @NotNull
    private final IntegerValue b2 = new IntegerValue("RectB", 255, 0, 255);
    @NotNull
    private final IntegerValue a = new IntegerValue("Alpha", 100, 0, 255);
    private final IntegerValue bgredValue = new IntegerValue("Background-Red", 255, 0, 255);
    private final IntegerValue bggreenValue = new IntegerValue("Background-Green", 255, 0, 255);
    private final IntegerValue bgblueValue = new IntegerValue("Background-Blue", 255, 0, 255);
    private final IntegerValue bgalphaValue = new IntegerValue("Background-Alpha", 120, 0, 255);
    private final FloatValue fontOffsetValue = new FloatValue("Font-Offset", 0.0F, 3.0F, -3.0F);
    private final BoolValue reverseValue = new BoolValue("Reverse", false);
    private final FontValue fontValue;
    private int GameInfoRows;
    private final BoolValue testboolean;
    private int win;
    private int killed;
    private int dead;
    private int KD;
    private int GamesPlayed;
    private final Entity target;

    @NotNull
    public final IntegerValue getR() {
        return this.r;
    }

    @NotNull
    public final IntegerValue getG() {
        return this.g;
    }

    @NotNull
    public final IntegerValue getB() {
        return this.b;
    }

    @NotNull
    public final IntegerValue getR2() {
        return this.r2;
    }

    @NotNull
    public final IntegerValue getG2() {
        return this.g2;
    }

    @NotNull
    public final IntegerValue getB2() {
        return this.b2;
    }

    @NotNull
    public final IntegerValue getA() {
        return this.a;
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

    public final int getWin() {
        return this.win;
    }

    public final void setWin(int <set-?>) {
        this.win = <set-?>;
    }

    public final int getKilled() {
        return this.killed;
    }

    public final void setKilled(int <set-?>) {
        this.killed = <set-?>;
    }

    public final int getDead() {
        return this.dead;
    }

    public final void setDead(int <set-?>) {
        this.dead = <set-?>;
    }

    public final int getKD() {
        return this.KD;
    }

    public final void setKD(int <set-?>) {
        this.KD = <set-?>;
    }

    public final int getGamesPlayed() {
        return this.GamesPlayed;
    }

    public final void setGamesPlayed(int <set-?>) {
        this.GamesPlayed = <set-?>;
    }

    @EventTarget
    public final void onRender2D(@Nullable Render2DEvent event) {
        new SimpleDateFormat("HH:mm:ss");
        boolean reverse = ((Boolean) this.reverseValue.get()).booleanValue();
        IFontRenderer font = (IFontRenderer) this.fontValue.get();
        float fontOffset = ((Number) this.fontOffsetValue.get()).floatValue();
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();
        int[] aint = new int[] { 1};

        if (Intrinsics.areEqual((String) this.infomode.get(), "Moon3.3")) {
            new SimpleDateFormat("H");
            new SimpleDateFormat("m");
            new SimpleDateFormat("ss");
            RoundedUtil.drawRoundOutline(((Number) this.xValue.get()).floatValue(), ((Number) this.yValue.get()).floatValue(), 70.0F, 76.2F, 4.3F, 0.4F, new Color(((Number) this.r.get()).intValue(), ((Number) this.g.get()).intValue(), ((Number) this.b.get()).intValue(), ((Number) this.a.get()).intValue()), Palette.fade1(new Color(((Number) this.r2.get()).intValue(), ((Number) this.g2.get()).intValue(), ((Number) this.b2.get()).intValue())));
            RoundedUtil.drawRoundOutline(((Number) this.xValue.get()).floatValue(), ((Number) this.yValue.get()).floatValue(), 70.0F, 76.2F, 4.3F, 1.7F, new Color(0, 0, 0, 0), new Color(((Number) this.r2.get()).intValue(), ((Number) this.g2.get()).intValue(), ((Number) this.b2.get()).intValue(), 90));
            KillAura killaura = (KillAura) LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
            IFontRenderer ifontrenderer = Fonts.fontSFUI40;
            float f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            float f1 = ((Number) this.yValue.get()).floatValue() + 6.8F;
            Color color = Color.WHITE;

            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString("KeyBind Info", f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            StringBuilder stringbuilder = (new StringBuilder()).append("Killaura: ");

            if (killaura == null) {
                Intrinsics.throwNpe();
            }

            String s = stringbuilder.append(Keyboard.getKeyName(killaura.getKeyBind())).toString();

            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 19.0F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            Criticals Criticals = (Criticals) LiquidBounce.INSTANCE.getModuleManager().getModule(Criticals.class);
            Scaffold Scaffold = (Scaffold) LiquidBounce.INSTANCE.getModuleManager().getModule(Scaffold.class);
            Velocity Velocity = (Velocity) LiquidBounce.INSTANCE.getModuleManager().getModule(Velocity.class);
            Blink Blink = (Blink) LiquidBounce.INSTANCE.getModuleManager().getModule(Blink.class);
            NoFall NoFall = (NoFall) LiquidBounce.INSTANCE.getModuleManager().getModule(NoFall.class);

            ifontrenderer = Fonts.bold35;
            stringbuilder = (new StringBuilder()).append("Criticals: ");
            if (Criticals == null) {
                Intrinsics.throwNpe();
            }

            s = stringbuilder.append(Keyboard.getKeyName(Criticals.getKeyBind())).toString();
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 28.7F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            stringbuilder = (new StringBuilder()).append("Scaffold: ");
            if (Scaffold == null) {
                Intrinsics.throwNpe();
            }

            s = stringbuilder.append(Keyboard.getKeyName(Scaffold.getKeyBind())).toString();
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 37.8F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            stringbuilder = (new StringBuilder()).append("Velocity: ");
            if (Velocity == null) {
                Intrinsics.throwNpe();
            }

            s = stringbuilder.append(Keyboard.getKeyName(Velocity.getKeyBind())).toString();
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 46.9F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            stringbuilder = (new StringBuilder()).append("Blink: ");
            if (Blink == null) {
                Intrinsics.throwNpe();
            }

            s = stringbuilder.append(Keyboard.getKeyName(Blink.getKeyBind())).toString();
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 55.1F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            stringbuilder = (new StringBuilder()).append("Nofall: ");
            if (NoFall == null) {
                Intrinsics.throwNpe();
            }

            s = stringbuilder.append(Keyboard.getKeyName(NoFall.getKeyBind())).toString();
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 64.2F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
        }

    }

    public ModuleInfoS() {
        IFontRenderer ifontrenderer = Fonts.font35;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font35, "Fonts.font35");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.testboolean = new BoolValue("1230", true);
    }
}
