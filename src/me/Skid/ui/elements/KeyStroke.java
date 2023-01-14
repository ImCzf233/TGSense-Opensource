package me.Skid.ui.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.utils.shadowRenderUtils;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.settings.KeyBinding;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0006\u0010\u001d\u001a\u00020\u0000J\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u0013JV\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020&2\u0006\u0010-\u001a\u00020&J>\u0010.\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020&2\u0006\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020&2\u0006\u0010-\u001a\u00020&JV\u0010/\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020&2\u0006\u0010-\u001a\u00020&R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000f¨\u00060"},
    d2 = { "Lme/Skid/ui/elements/KeyStroke;", "", "key", "Lnet/minecraft/client/settings/KeyBinding;", "posX", "", "posY", "width", "height", "(Lnet/minecraft/client/settings/KeyBinding;IIII)V", "animations", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "getHeight", "()I", "getKey", "()Lnet/minecraft/client/settings/KeyBinding;", "keyName", "", "getKeyName", "()Ljava/lang/String;", "setKeyName", "(Ljava/lang/String;)V", "lastClick", "", "getPosX", "getPosY", "getWidth", "initKeyName", "name", "renderCustom", "", "speed", "bgColor", "Ljava/awt/Color;", "textColor", "highLightPct", "", "outline", "outlineBold", "font", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "renderX", "renderY", "scale", "renderJelloIndicator", "renderJuul", "LiquidBounce"}
)
public final class KeyStroke {

    @NotNull
    private String keyName;
    private boolean lastClick;
    private final ArrayList animations;
    @NotNull
    private final KeyBinding key;
    private final int posX;
    private final int posY;
    private final int width;
    private final int height;

    @NotNull
    public final String getKeyName() {
        return this.keyName;
    }

    public final void setKeyName(@NotNull String <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.keyName = <set-?>;
    }

    public final void renderCustom(int speed, @NotNull Color bgColor, @NotNull Color textColor, float highLightPct, boolean outline, int outlineBold, @NotNull IFontRenderer font, float renderX, float renderY, float scale) {
        Intrinsics.checkParameterIsNotNull(bgColor, "bgColor");
        Intrinsics.checkParameterIsNotNull(textColor, "textColor");
        Intrinsics.checkParameterIsNotNull(font, "font");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) this.posX, (float) this.posY, 0.0F);
        shadowRenderUtils.drawShadowWithCustomAlpha(0.0F, 0.0F, (float) this.width, (float) this.height, 240.0F);
        Color highLightColor = new Color(255 - (int) ((float) (255 - bgColor.getRed()) * highLightPct), 255 - (int) ((float) (255 - bgColor.getBlue()) * highLightPct), 255 - (int) ((float) (255 - bgColor.getGreen()) * highLightPct));
        float clickAlpha = (float) 255 - (float) (255 - bgColor.getAlpha()) * highLightPct;
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        long nowTime = System.currentTimeMillis();
        Color rectColor = this.lastClick && this.animations.isEmpty() ? ColorUtils.INSTANCE.reAlpha(highLightColor, (int) clickAlpha) : bgColor;

        RenderUtils.drawRect(0.0F, 0.0F, (float) this.width, (float) this.height, rectColor);
        ArrayList removeAble = new ArrayList();
        Iterator iterator = this.animations.iterator();

        Long time;

        while (iterator.hasNext()) {
            time = (Long) iterator.next();
            Intrinsics.checkExpressionValueIsNotNull(time, "time");
            float pct = (float) (nowTime - time.longValue()) / (float) speed;

            if (pct > (float) 1) {
                removeAble.add(time);
            } else {
                RenderUtils.drawLimitedCircle(0.0F, 0.0F, (float) this.width, (float) this.height, centerX, centerY, (float) this.width * 0.7F * pct, new Color(255 - (int) ((float) (255 - highLightColor.getRed()) * pct), 255 - (int) ((float) (255 - highLightColor.getGreen()) * pct), 255 - (int) ((float) (255 - highLightColor.getBlue()) * pct), 255 - (int) (((float) 255 - clickAlpha) * pct)));
            }
        }

        iterator = removeAble.iterator();

        while (iterator.hasNext()) {
            time = (Long) iterator.next();
            this.animations.remove(time);
            removeAble.remove(time);
        }

        if (!this.lastClick && this.key.isKeyDown()) {
            this.animations.add(Long.valueOf(nowTime));
        }

        if (this.key.isKeyDown() && this.animations.isEmpty()) {
            RenderUtils.drawRect(0.0F, 0.0F, (float) this.width, (float) this.height, ColorUtils.INSTANCE.reAlpha(highLightColor, (int) clickAlpha));
        }

        this.lastClick = this.key.isKeyDown();
        font.drawString(this.keyName, centerX - font.getStringWidth(this.keyName) / 2 + 1, centerY - font.getFontHeight() / 2 + 1, textColor.getRGB());
        if (outline) {
            RenderUtils.drawRect(0.0F, 0.0F, (float) outlineBold, (float) this.height, textColor.getRGB());
            RenderUtils.drawRect((float) (this.width - outlineBold), 0.0F, (float) this.width, (float) this.height, textColor.getRGB());
            RenderUtils.drawRect((float) outlineBold, 0.0F, (float) (this.width - outlineBold), (float) outlineBold, textColor.getRGB());
            RenderUtils.drawRect((float) outlineBold, (float) (this.height - outlineBold), (float) (this.width - outlineBold), (float) this.height, textColor.getRGB());
        }

        GL11.glPopMatrix();
    }

    public final void renderJuul(int speed, @NotNull Color bgColor, @NotNull Color textColor, float highLightPct, boolean outline, int outlineBold, @NotNull IFontRenderer font, float renderX, float renderY, float scale) {
        Intrinsics.checkParameterIsNotNull(bgColor, "bgColor");
        Intrinsics.checkParameterIsNotNull(textColor, "textColor");
        Intrinsics.checkParameterIsNotNull(font, "font");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) this.posX, (float) this.posY, 0.0F);
        long nowTime = System.currentTimeMillis();
        Color rectColor = this.lastClick ? new Color(65, 65, 65, 255) : new Color(95, 95, 95, 255);

        RenderUtils.drawRoundedCornerRect(0.0F, 0.0F, (float) this.width, (float) this.height, 3.0F, rectColor.getRGB());
        this.lastClick = this.key.isKeyDown();
        Fonts.regular28.drawString(this.keyName, this.width / 2 - font.getStringWidth(this.keyName) / 2 + 1, this.height / 2 - font.getFontHeight() / 2 + 2, textColor.getRGB());
        GL11.glPopMatrix();
    }

    public final void renderJelloIndicator(int speed, @NotNull Color bgColor, @NotNull Color textColor, float highLightPct, float renderX, float renderY, float scale) {
        Intrinsics.checkParameterIsNotNull(bgColor, "bgColor");
        Intrinsics.checkParameterIsNotNull(textColor, "textColor");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) this.posX, (float) this.posY, 0.0F);
        Color highLightColor = new Color(255 - (int) ((float) (255 - bgColor.getRed()) * highLightPct), 255 - (int) ((float) (255 - bgColor.getBlue()) * highLightPct), 255 - (int) ((float) (255 - bgColor.getGreen()) * highLightPct));
        float clickAlpha = (float) 255 - (float) (255 - bgColor.getAlpha()) * highLightPct;
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        long nowTime = System.currentTimeMillis();
        Color rectColor = this.lastClick && this.animations.isEmpty() ? ColorUtils.INSTANCE.reAlpha(highLightColor, (int) clickAlpha) : new Color(0.0F, 0.0F, 0.0F, 0.0F);

        RenderUtils.drawRect(0.0F, 0.0F, (float) this.width, (float) this.height, rectColor);
        ArrayList removeAble = new ArrayList();
        Iterator iterator = this.animations.iterator();

        Long time;

        while (iterator.hasNext()) {
            time = (Long) iterator.next();
            Intrinsics.checkExpressionValueIsNotNull(time, "time");
            float pct = (float) (nowTime - time.longValue()) / (float) speed;

            if (pct > (float) 1) {
                removeAble.add(time);
            } else {
                RenderUtils.drawLimitedCircle(0.0F, 0.0F, (float) this.width, (float) this.height, centerX, centerY, (float) this.width * 0.7F * pct, new Color(255 - (int) ((float) (255 - highLightColor.getRed()) * pct), 255 - (int) ((float) (255 - highLightColor.getGreen()) * pct), 255 - (int) ((float) (255 - highLightColor.getBlue()) * pct), 255 - (int) (((float) 255 - clickAlpha) * pct)));
            }
        }

        iterator = removeAble.iterator();

        while (iterator.hasNext()) {
            time = (Long) iterator.next();
            this.animations.remove(time);
        }

        if (!this.lastClick && this.key.isKeyDown()) {
            this.animations.add(Long.valueOf(nowTime));
        }

        this.lastClick = this.key.isKeyDown();
        GL11.glPopMatrix();
    }

    @NotNull
    public final KeyStroke initKeyName() {
        String s = Keyboard.getKeyName(this.key.getKeyCode());

        Intrinsics.checkExpressionValueIsNotNull(s, "Keyboard.getKeyName(key.keyCode)");
        this.keyName = s;
        return this;
    }

    @NotNull
    public final KeyStroke initKeyName(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.keyName = name;
        return this;
    }

    @NotNull
    public final KeyBinding getKey() {
        return this.key;
    }

    public final int getPosX() {
        return this.posX;
    }

    public final int getPosY() {
        return this.posY;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public KeyStroke(@NotNull KeyBinding key, int posX, int posY, int width, int height) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        super();
        this.key = key;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.keyName = "KEY";
        this.animations = new ArrayList();
    }
}
