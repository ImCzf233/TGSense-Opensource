package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.Tenacity.utils.render.RoundedUtil;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Text;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.Palette;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "PlayerInfo",
    description = "look your info",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u00102\u001a\u000203J\u0012\u00104\u001a\u0002052\b\u00106\u001a\u0004\u0018\u000107H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0007\"\u0004\b\u0017\u0010\tR\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010R\u0011\u0010\u001c\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0010R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010 \u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0007\"\u0004\b\"\u0010\tR\u0011\u0010#\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0010R\u0011\u0010%\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0010R\u000e\u0010\'\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010*X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010,\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0007\"\u0004\b.\u0010\tR\u000e\u0010/\u001a\u000200X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u000200X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/PlayerInfo;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "GameInfoRows", "", "GamesPlayed", "getGamesPlayed", "()I", "setGamesPlayed", "(I)V", "KD", "getKD", "setKD", "a", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getA", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "b", "getB", "b2", "getB2", "dead", "getDead", "setDead", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "g", "getG", "g2", "getG2", "infomode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "killed", "getKilled", "setKilled", "r", "getR", "r2", "getR2", "reverseValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "target", "Lnet/minecraft/entity/Entity;", "testboolean", "win", "getWin", "setWin", "xValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "yValue", "calculateBPS", "", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "LiquidBounce"}
)
public final class PlayerInfo extends Module {

    private final ListValue infomode = new ListValue("mode", new String[] { "Moon3.3"}, "Moon3.3");
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
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();
        int[] aint = new int[] { 1};

        if (Intrinsics.areEqual((String) this.infomode.get(), "Moon3.3")) {
            new SimpleDateFormat("H");
            new SimpleDateFormat("m");
            new SimpleDateFormat("ss");
            RoundedUtil.drawRoundOutline(((Number) this.xValue.get()).floatValue(), ((Number) this.yValue.get()).floatValue(), 131.0F, 67.0F, 4.3F, 0.4F, new Color(((Number) this.r.get()).intValue(), ((Number) this.g.get()).intValue(), ((Number) this.b.get()).intValue(), ((Number) this.a.get()).intValue()), Palette.fade1(new Color(((Number) this.r2.get()).intValue(), ((Number) this.g2.get()).intValue(), ((Number) this.b2.get()).intValue())));
            RoundedUtil.drawRoundOutline(((Number) this.xValue.get()).floatValue(), ((Number) this.yValue.get()).floatValue(), 131.0F, 67.0F, 4.3F, 1.7F, new Color(0, 0, 0, 0), new Color(((Number) this.r2.get()).intValue(), ((Number) this.g2.get()).intValue(), ((Number) this.b2.get()).intValue(), 90));
            IFontRenderer ifontrenderer = Fonts.fontSFUI40;
            float f = ((Number) this.xValue.get()).floatValue() + 20.0F;
            float f1 = ((Number) this.yValue.get()).floatValue() + 6.8F;
            Color color = Color.WHITE;

            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString("Player Info", f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            String s = "World FPS: " + Minecraft.getDebugFPS();

            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 19.0F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 28.7F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString("Banned: 0", f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            s = "Ping: " + String.valueOf(EntityUtils.INSTANCE.getPing((EntityPlayer) MinecraftInstance.mc2.player));
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 37.8F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            StringBuilder stringbuilder = (new StringBuilder()).append("X:");
            DecimalFormat decimalformat = Text.Companion.getDECIMAL_FORMAT();
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            stringbuilder = stringbuilder.append(decimalformat.format(ientityplayersp.getPosX())).append(" ").append("Y:");
            decimalformat = Text.Companion.getDECIMAL_FORMAT();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            stringbuilder = stringbuilder.append(decimalformat.format(ientityplayersp.getPosY())).append(" ").append("Z:");
            decimalformat = Text.Companion.getDECIMAL_FORMAT();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            s = stringbuilder.append(decimalformat.format(ientityplayersp.getPosZ())).toString();
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 48.0F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
        }

    }

    public PlayerInfo() {
        IFontRenderer ifontrenderer = Fonts.font35;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font35, "Fonts.font35");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.testboolean = new BoolValue("1230", true);
    }
}
