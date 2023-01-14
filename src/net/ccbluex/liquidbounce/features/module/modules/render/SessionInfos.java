package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.Skid.Tenacity.utils.render.RoundedUtil;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Text;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.InfosUtils.Recorder;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.render.Palette;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "Sessioninfo",
    description = "look your info",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010&\u001a\u00020\'J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020)2\u0006\u0010*\u001a\u00020-H\u0003J\u0012\u0010.\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010/H\u0007J\u0010\u00100\u001a\u00020)2\u0006\u0010*\u001a\u000201H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\bR\u0011\u0010\u0017\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\bR\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\bR\u0011\u0010\u001d\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\bR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/SessionInfos;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "GameInfoRows", "", "a", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getA", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "b", "getB", "b2", "getB2", "bgalphaValue", "bgblueValue", "bggreenValue", "bgredValue", "fontOffsetValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "g", "getG", "g2", "getG2", "infomode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "r", "getR", "r2", "getR2", "reverseValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "target", "Lnet/minecraft/entity/Entity;", "testboolean", "xValue", "yValue", "calculateBPS", "", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class SessionInfos extends Module {

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

    @EventTarget
    public final void onRender2D(@Nullable Render2DEvent event) {
        new SimpleDateFormat("HH:mm:ss");
        boolean reverse = ((Boolean) this.reverseValue.get()).booleanValue();
        IFontRenderer font = (IFontRenderer) this.fontValue.get();
        float fontOffset = ((Number) this.fontOffsetValue.get()).floatValue();
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();
        int[] aint = new int[] { 1};

        if (Intrinsics.areEqual((String) this.infomode.get(), "Moon3.3")) {
            SimpleDateFormat DateH = new SimpleDateFormat("H");
            SimpleDateFormat DateM = new SimpleDateFormat("m");
            SimpleDateFormat DateS = new SimpleDateFormat("ss");

            RoundedUtil.drawRoundOutline(((Number) this.xValue.get()).floatValue(), ((Number) this.yValue.get()).floatValue(), 131.0F, 67.0F, 4.3F, 0.4F, new Color(((Number) this.r.get()).intValue(), ((Number) this.g.get()).intValue(), ((Number) this.b.get()).intValue(), ((Number) this.a.get()).intValue()), Palette.fade1(new Color(((Number) this.r2.get()).intValue(), ((Number) this.g2.get()).intValue(), ((Number) this.b2.get()).intValue())));
            RoundedUtil.drawRoundOutline(((Number) this.xValue.get()).floatValue(), ((Number) this.yValue.get()).floatValue(), 131.0F, 67.0F, 4.3F, 1.7F, new Color(0, 0, 0, 0), new Color(((Number) this.r2.get()).intValue(), ((Number) this.g2.get()).intValue(), ((Number) this.b2.get()).intValue(), 90));
            IFontRenderer ifontrenderer = Fonts.fontSFUI40;
            float f = ((Number) this.xValue.get()).floatValue() + 20.0F;
            float f1 = ((Number) this.yValue.get()).floatValue() + 6.8F;
            Color color = Color.WHITE;

            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString("Session Information", f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            String s = "Session time: " + DateH.format(new Date(System.currentTimeMillis() - Recorder.INSTANCE.getStartTime() - 28800000L)) + "h " + DateM.format(new Date(System.currentTimeMillis() - Recorder.INSTANCE.getStartTime() - 28800000L)) + "m " + DateS.format(new Date(System.currentTimeMillis() - Recorder.INSTANCE.getStartTime() - 28800000L)) + "s";

            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 19.0F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            s = "Kills: " + Recorder.INSTANCE.getKillCounts();
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 28.7F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            s = "Deaths: " + Recorder.INSTANCE.getDead();
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 37.8F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            s = "K/D: " + Recorder.INSTANCE.getKD();
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 46.9F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
            ifontrenderer = Fonts.bold35;
            s = "Games played: " + Recorder.INSTANCE.getGamesPlayed();
            f = ((Number) this.xValue.get()).floatValue() + 4.0F;
            f1 = ((Number) this.yValue.get()).floatValue() + 55.1F;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, f, f1, color.getRGB());
        }

        if (Intrinsics.areEqual((String) this.infomode.get(), "TGSense")) {
            RenderUtils.drawRoundedRect(0.0F, (float) this.GameInfoRows * 18.0F + (float) 12, 176.0F, (float) this.GameInfoRows * 18.0F + 25.0F, 5.0F, (new Color(((Number) this.r.get()).intValue(), ((Number) this.g.get()).intValue(), ((Number) this.b.get()).intValue(), ((Number) this.a.get()).intValue())).getRGB());
            font.drawStringWithShadow("GameInfo", 7, this.GameInfoRows * 18 + 16, (new Color(255, 255, 255, 255)).getRGB());
            RenderUtils.drawRoundedRect(0.0F, (float) this.GameInfoRows * 18.0F + 30.0F, 176.0F, 85.0F, 5.0F, (new Color(((Number) this.bgredValue.get()).intValue(), ((Number) this.bggreenValue.get()).intValue(), ((Number) this.bgblueValue.get()).intValue(), ((Number) this.bgalphaValue.get()).intValue())).getRGB());
            font.drawStringWithShadow("FPS: " + MinecraftInstance.mc.getDebugFPS(), 7, this.GameInfoRows * 18 + 28 + 5, (new Color(255, 255, 255, 255)).getRGB());
            font.drawStringWithShadow("BPS: " + this.calculateBPS(), 7, this.GameInfoRows * 18 + 38 + 5, (new Color(255, 255, 255, 255)).getRGB());
            font.drawStringWithShadow("Ping: " + String.valueOf(EntityUtils.INSTANCE.getPing((EntityPlayer) MinecraftInstance.mc2.player)) + "ms", 7, this.GameInfoRows * 18 + 48 + 5, (new Color(255, 255, 255, 255)).getRGB());
            if (thePlayer != null) {
                font.drawStringWithShadow("X: " + Text.Companion.getDECIMAL_FORMAT().format(thePlayer.getPosX()) + " " + "Y: " + Text.Companion.getDECIMAL_FORMAT().format(thePlayer.getPosY()) + " " + "Z: " + Text.Companion.getDECIMAL_FORMAT().format(thePlayer.getPosZ()), 7, this.GameInfoRows * 18 + 58 + 5, (new Color(255, 255, 255, 255)).getRGB());
            }

            font.drawStringWithShadow("ServerIP: " + ServerUtils.getRemoteIp(), 7, this.GameInfoRows * 18 + 68 + 5, (new Color(255, 255, 255, 255)).getRGB());
        }

    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        Recorder.INSTANCE.setSyncEntity((IEntityLivingBase) event.getTargetEntity());
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityLivingBase ientitylivingbase = Recorder.INSTANCE.getSyncEntity();

        if (ientitylivingbase == null) {
            Intrinsics.throwNpe();
        }

        if (ientitylivingbase.isDead()) {
            Recorder recorder = Recorder.INSTANCE;

            Recorder.INSTANCE.setKillCounts(Recorder.INSTANCE.getKillCounts() + 1);
            recorder.getKillCounts();
            Recorder.INSTANCE.setSyncEntity((IEntityLivingBase) null);
        }

    }

    @EventTarget
    private final void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C00Handshake) {
            Recorder.INSTANCE.setStartTime(System.currentTimeMillis());
        }

        IPacket ipacket = event.getPacket();

        if (ipacket == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketChat");
        } else {
            ITextComponent itextcomponent = ((SPacketChat) ipacket).getChatComponent();

            Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "(event.packet as SPacketChat).chatComponent");
            String message = itextcomponent.getUnformattedText();
            IPacket packet = event.getPacket();
            int i;

            if (packet instanceof SPacketTitle) {
                itextcomponent = ((SPacketTitle) packet).getMessage();
                if (itextcomponent == null) {
                    return;
                }

                String KD = itextcomponent.getFormattedText();

                Intrinsics.checkExpressionValueIsNotNull(KD, "title");
                if (StringsKt.startsWith$default(KD, "游戏�?�?", false, 2, (Object) null)) {
                    i = Recorder.INSTANCE.getGamesPlayed() + 1;
                }

                if (StringsKt.startsWith$default(KD, "恭喜", false, 2, (Object) null)) {
                    Recorder.INSTANCE.setWin(Recorder.INSTANCE.getWin() + 1);
                }
            }

            Intrinsics.checkExpressionValueIsNotNull(message, "message");
            if (StringsKt.contains$default((CharSequence) message, (CharSequence) "Reason", false, 2, (Object) null)) {
                Recorder.INSTANCE.setBan(Recorder.INSTANCE.getBan() + 1);
            }

            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.isDead()) {
                Recorder.INSTANCE.setDead(Recorder.INSTANCE.getDead() + 1);
            }

            int KD1 = Recorder.INSTANCE.getKillCounts() / Recorder.INSTANCE.getDead();

            if (Recorder.INSTANCE.getDead() == 0) {
                i = KD1 + 1;
            }

        }
    }

    public SessionInfos() {
        IFontRenderer ifontrenderer = Fonts.font35;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font35, "Fonts.font35");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.testboolean = new BoolValue("1230", true);
    }
}
