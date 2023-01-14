package me.Skid.ui.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@ElementInfo(
    name = "KeyStrokes"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "},
    d2 = { "Lme/Skid/ui/elements/KeyStrokes;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "animSpeedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backGroundAlphaValue", "backGroundBlueValue", "backGroundGreenValue", "backGroundRedValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "highLightPercent", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "juulkeys", "Ljava/util/ArrayList;", "Lme/Skid/ui/elements/KeyStroke;", "Lkotlin/collections/ArrayList;", "keyStyleValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getKeyStyleValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "keys", "outline", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "outlineBoldValue", "outlineRainbow", "textAlphaValue", "textBlueValue", "textGreenValue", "textRedValue", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"}
)
public final class KeyStrokes extends Element {

    private final ArrayList keys = new ArrayList();
    private final ArrayList juulkeys = new ArrayList();
    private final IntegerValue backGroundRedValue = new IntegerValue("BackGroundRed", 0, 0, 255);
    private final IntegerValue backGroundGreenValue = new IntegerValue("BackGroundGreen", 0, 0, 255);
    private final IntegerValue backGroundBlueValue = new IntegerValue("BackGroundBlue", 0, 0, 255);
    private final IntegerValue backGroundAlphaValue = new IntegerValue("BackGroundAlpha", 170, 0, 255);
    private final IntegerValue textRedValue = new IntegerValue("TextRed", 255, 0, 255);
    private final IntegerValue textGreenValue = new IntegerValue("TextGreen", 255, 0, 255);
    private final IntegerValue textBlueValue = new IntegerValue("TextBlue", 255, 0, 255);
    private final IntegerValue textAlphaValue = new IntegerValue("TextAlpha", 255, 0, 255);
    private final FloatValue highLightPercent = new FloatValue("HighLightPercent", 0.5F, 0.0F, 1.0F);
    private final IntegerValue animSpeedValue = new IntegerValue("AnimationSpeed", 300, 0, 700);
    private final BoolValue outline = new BoolValue("Outline", false);
    private final IntegerValue outlineBoldValue = new IntegerValue("OutlineBold", 1, 0, 5);
    private final BoolValue outlineRainbow = new BoolValue("OutLineRainbow", false);
    private final FontValue fontValue;
    @NotNull
    private final ListValue keyStyleValue;

    @NotNull
    public final ListValue getKeyStyleValue() {
        return this.keyStyleValue;
    }

    @NotNull
    public Border drawElement() {
        Color backGroundColor = new Color(((Number) this.backGroundRedValue.get()).intValue(), ((Number) this.backGroundGreenValue.get()).intValue(), ((Number) this.backGroundBlueValue.get()).intValue(), ((Number) this.backGroundAlphaValue.get()).intValue());
        Color textColor = ((Boolean) this.outlineRainbow.get()).booleanValue() ? ColorUtils.rainbowWithAlpha(((Number) this.textAlphaValue.get()).intValue()) : new Color(((Number) this.textRedValue.get()).intValue(), ((Number) this.textGreenValue.get()).intValue(), ((Number) this.textBlueValue.get()).intValue(), ((Number) this.textAlphaValue.get()).intValue());
        KeyStroke fontRenderer;
        Iterator juulLeft;

        if (((String) this.keyStyleValue.get()).equals("Custom")) {
            juulLeft = this.keys.iterator();

            while (juulLeft.hasNext()) {
                fontRenderer = (KeyStroke) juulLeft.next();
                fontRenderer.renderCustom(((Number) this.animSpeedValue.get()).intValue(), backGroundColor, textColor, ((Number) this.highLightPercent.get()).floatValue(), ((Boolean) this.outline.get()).booleanValue(), ((Number) this.outlineBoldValue.get()).intValue(), (IFontRenderer) this.fontValue.get(), (float) this.getRenderX(), (float) this.getRenderY(), this.getScale());
            }
        }

        if (((String) this.keyStyleValue.get()).equals("Jello")) {
            RenderUtils.drawImage2(new ResourceLocation("langya/keystrokes.png"), -3.5F, -3.5F, 54, 54);
            juulLeft = this.keys.iterator();

            while (juulLeft.hasNext()) {
                fontRenderer = (KeyStroke) juulLeft.next();
                fontRenderer.renderJelloIndicator(((Number) this.animSpeedValue.get()).intValue(), backGroundColor, textColor, ((Number) this.highLightPercent.get()).floatValue(), (float) this.getRenderX(), (float) this.getRenderY(), this.getScale());
            }
        }

        if (((String) this.keyStyleValue.get()).equals("Juul")) {
            juulLeft = this.juulkeys.iterator();

            while (juulLeft.hasNext()) {
                fontRenderer = (KeyStroke) juulLeft.next();
                fontRenderer.renderJuul(((Number) this.animSpeedValue.get()).intValue(), backGroundColor, textColor, ((Number) this.highLightPercent.get()).floatValue(), ((Boolean) this.outline.get()).booleanValue(), ((Number) this.outlineBoldValue.get()).intValue(), (IFontRenderer) this.fontValue.get(), (float) this.getRenderX(), (float) this.getRenderY(), this.getScale());
            }

            IFontRenderer fontRenderer1 = (IFontRenderer) this.fontValue.get();

            RenderUtils.drawRoundedCornerRect(0.0F, 32.0F, 23.0F, 47.0F, 4.0F, MinecraftInstance.mc.getGameSettings().getKeyBindAttack().isKeyDown() ? (new Color(65, 65, 75, 255)).getRGB() : (new Color(95, 95, 105, 255)).getRGB());
            RenderUtils.drawRoundedCornerRect(24.0F, 32.0F, 47.0F, 47.0F, 4.0F, MinecraftInstance.mc.getGameSettings().getKeyBindUseItem().isKeyDown() ? (new Color(65, 65, 75, 255)).getRGB() : (new Color(95, 95, 105, 255)).getRGB());
            String juulLeft1 = (float) CPSCounter.getCPS(CPSCounter.MouseButton.LEFT) != 0.0F ? CPSCounter.getCPS(CPSCounter.MouseButton.LEFT) + " cps" : "Left";
            String juulRight = (float) CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT) != 0.0F ? CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT) + "CPS" : "Right";

            Fonts.regular28.drawString(juulLeft1, 15.5F - (float) fontRenderer1.getStringWidth(juulLeft1) / 2.0F + 1.0F, 39.5F - (float) fontRenderer1.getFontHeight() / 2.0F + 2.0F, textColor.getRGB());
            Fonts.regular28.drawString(juulRight, 39.5F - (float) fontRenderer1.getStringWidth(juulRight) / 2.0F + 1.0F, 39.5F - (float) fontRenderer1.getFontHeight() / 2.0F + 2.0F, textColor.getRGB());
        }

        return new Border(0.0F, 0.0F, 47.0F, 47.0F);
    }

    public KeyStrokes() {
        super(5.0D, 25.0D, 1.5F, Side.Companion.default());
        IFontRenderer ifontrenderer = Fonts.font35;

        Intrinsics.checkExpressionValueIsNotNull(Fonts.font35, "Fonts.font35");
        this.fontValue = new FontValue("Font", ifontrenderer);
        this.keyStyleValue = new ListValue("Mode", new String[] { "Custom", "Jello", "Juul"}, "Jello");
        ArrayList arraylist = this.keys;
        KeyBinding keybinding = MinecraftInstance.mc2.gameSettings.keyBindForward;

        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.gameSettings.keyBindForward, "mc2.gameSettings.keyBindForward");
        arraylist.add((new KeyStroke(keybinding, 16, 0, 15, 15)).initKeyName());
        arraylist = this.keys;
        keybinding = MinecraftInstance.mc2.gameSettings.keyBindLeft;
        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.gameSettings.keyBindLeft, "mc2.gameSettings.keyBindLeft");
        arraylist.add((new KeyStroke(keybinding, 0, 16, 15, 15)).initKeyName());
        arraylist = this.keys;
        keybinding = MinecraftInstance.mc2.gameSettings.keyBindBack;
        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.gameSettings.keyBindBack, "mc2.gameSettings.keyBindBack");
        arraylist.add((new KeyStroke(keybinding, 16, 16, 15, 15)).initKeyName());
        arraylist = this.keys;
        keybinding = MinecraftInstance.mc2.gameSettings.keyBindRight;
        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.gameSettings.keyBindRight, "mc2.gameSettings.keyBindRight");
        arraylist.add((new KeyStroke(keybinding, 32, 16, 15, 15)).initKeyName());
        arraylist = this.keys;
        keybinding = MinecraftInstance.mc2.gameSettings.keyBindAttack;
        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.gameSettings.keyBindAttack, "mc2.gameSettings.keyBindAttack");
        arraylist.add((new KeyStroke(keybinding, 0, 32, 23, 15)).initKeyName("L"));
        arraylist = this.keys;
        keybinding = MinecraftInstance.mc2.gameSettings.keyBindUseItem;
        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.gameSettings.keyBindUseItem, "mc2.gameSettings.keyBindUseItem");
        arraylist.add((new KeyStroke(keybinding, 24, 32, 23, 15)).initKeyName("R"));
        arraylist = this.juulkeys;
        keybinding = MinecraftInstance.mc2.gameSettings.keyBindForward;
        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.gameSettings.keyBindForward, "mc2.gameSettings.keyBindForward");
        arraylist.add((new KeyStroke(keybinding, 16, 0, 15, 15)).initKeyName());
        arraylist = this.juulkeys;
        keybinding = MinecraftInstance.mc2.gameSettings.keyBindLeft;
        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.gameSettings.keyBindLeft, "mc2.gameSettings.keyBindLeft");
        arraylist.add((new KeyStroke(keybinding, 0, 16, 15, 15)).initKeyName());
        arraylist = this.juulkeys;
        keybinding = MinecraftInstance.mc2.gameSettings.keyBindBack;
        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.gameSettings.keyBindBack, "mc2.gameSettings.keyBindBack");
        arraylist.add((new KeyStroke(keybinding, 16, 16, 15, 15)).initKeyName());
        arraylist = this.juulkeys;
        keybinding = MinecraftInstance.mc2.gameSettings.keyBindRight;
        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.gameSettings.keyBindRight, "mc2.gameSettings.keyBindRight");
        arraylist.add((new KeyStroke(keybinding, 32, 16, 15, 15)).initKeyName());
    }
}
