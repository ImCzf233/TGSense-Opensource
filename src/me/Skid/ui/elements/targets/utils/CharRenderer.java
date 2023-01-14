package me.Skid.ui.elements.targets.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.utils.AnimationUtils;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JV\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020!R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\""},
    d2 = { "Lme/Skid/ui/elements/targets/utils/CharRenderer;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "small", "", "(Z)V", "deFormat", "Ljava/text/DecimalFormat;", "moveX", "", "getMoveX", "()[F", "setMoveX", "([F)V", "moveY", "getMoveY", "setMoveY", "numberList", "", "", "getSmall", "()Z", "renderChar", "", "number", "orgX", "orgY", "initX", "initY", "scaleX", "scaleY", "shadow", "fontSpeed", "color", "", "LiquidBounce"}
)
public final class CharRenderer extends MinecraftInstance {

    @NotNull
    private float[] moveY;
    @NotNull
    private float[] moveX;
    private final List numberList;
    private final DecimalFormat deFormat;
    private final boolean small;

    @NotNull
    public final float[] getMoveY() {
        return this.moveY;
    }

    public final void setMoveY(@NotNull float[] <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.moveY = <set-?>;
    }

    @NotNull
    public final float[] getMoveX() {
        return this.moveX;
    }

    public final void setMoveX(@NotNull float[] <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.moveX = <set-?>;
    }

    public final float renderChar(float number, float orgX, float orgY, float initX, float initY, float scaleX, float scaleY, boolean shadow, float fontSpeed, int color) {
        String reFormat = this.deFormat.format((double) number);
        IFontRenderer fontRend = Fonts.font40;
        int delta = RenderUtils.deltaTime;
        IClassProvider iclassprovider = MinecraftInstance.classProvider;
        IMinecraft iminecraft = MinecraftInstance.mc;

        Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
        IScaledResolution scaledRes = iclassprovider.createScaledResolution(iminecraft);
        int indexX = 0;
        int indexY = 0;
        float animX = 0.0F;
        float cutY = initY + (float) fontRend.getFontHeight() * 0.75F;

        GL11.glEnable(3089);
        RenderUtils.makeScissorBox(0.0F, orgY + initY - 4.0F * scaleY, (float) scaledRes.getScaledWidth(), orgY + cutY - 4.0F * scaleY);
        Intrinsics.checkExpressionValueIsNotNull(reFormat, "reFormat");
        boolean pos = false;

        if (reFormat == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            char[] achar = reFormat.toCharArray();

            Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
            char[] achar1 = achar;
            int i = achar1.length;

            for (int j = 0; j < i; ++j) {
                char char = achar1[j];

                this.moveX[indexX] = AnimationUtils.animate(animX, this.moveX[indexX], fontSpeed * 0.025F * (float) delta);
                animX = this.moveX[indexX];
                int k = this.numberList.indexOf(String.valueOf(char));
                float expectAnim = ((float) fontRend.getFontHeight() + 2.0F) * (float) k;
                float expectAnimMin = ((float) fontRend.getFontHeight() + 2.0F) * (float) (k - 2);
                float expectAnimMax = ((float) fontRend.getFontHeight() + 2.0F) * (float) (k + 2);

                if (k >= 0) {
                    this.moveY[indexY] = AnimationUtils.animate(expectAnim, this.moveY[indexY], fontSpeed * 0.02F * (float) delta);
                    GL11.glTranslatef(0.0F, initY - this.moveY[indexY], 0.0F);
                    Iterable $this$forEachIndexed$iv = (Iterable) this.numberList;
                    boolean $i$f$forEachIndexed = false;
                    int index$iv = 0;
                    Iterator iterator = $this$forEachIndexed$iv.iterator();

                    while (iterator.hasNext()) {
                        Object item$iv = iterator.next();
                        int l = index$iv++;
                        boolean flag = false;

                        if (l < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }

                        String num = (String) item$iv;
                        boolean $i$a$-forEachIndexed-CharRenderer$renderChar$1 = false;

                        if (((float) fontRend.getFontHeight() + 2.0F) * (float) l >= expectAnimMin && ((float) fontRend.getFontHeight() + 2.0F) * (float) l <= expectAnimMax) {
                            fontRend.drawString(num, initX + this.moveX[indexX], ((float) fontRend.getFontHeight() + 2.0F) * (float) l, color, shadow);
                        }
                    }

                    GL11.glTranslatef(0.0F, -initY + this.moveY[indexY], 0.0F);
                } else {
                    this.moveY[indexY] = 0.0F;
                    fontRend.drawString(String.valueOf(char), initX + this.moveX[indexX], initY, color, shadow);
                }

                animX += (float) fontRend.getStringWidth(String.valueOf(char));
                ++indexX;
                ++indexY;
            }

            GL11.glDisable(3089);
            return animX;
        }
    }

    public final boolean getSmall() {
        return this.small;
    }

    public CharRenderer(boolean small) {
        this.small = small;
        this.moveY = new float[20];
        this.moveX = new float[20];
        this.numberList = CollectionsKt.listOf(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."});
        this.deFormat = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.ENGLISH));
        int i = 0;

        for (byte b0 = 19; i <= b0; ++i) {
            this.moveX[i] = 0.0F;
            this.moveY[i] = 0.0F;
        }

    }
}
