package me.Skid.Modules.Render;

import java.awt.Color;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.utils.BlurUtils;
import me.Skid.utils.ColorUtils1;
import me.Skid.utils.Render.Translate;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "JelloTabGui",
    description = "Toggles visibility of the HUD.",
    category = ModuleCategory.RENDER,
    array = false
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\'\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001:\u0004§\u0001¨\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0002J\n\u0010\u0090\u0001\u001a\u00030\u008f\u0001H\u0002J\n\u0010\u0091\u0001\u001a\u00030\u008f\u0001H\u0002J\n\u0010\u0092\u0001\u001a\u00030\u008f\u0001H\u0002J\u0013\u0010\u0093\u0001\u001a\u00030\u008f\u00012\u0007\u0010\u0094\u0001\u001a\u00020\u0011H\u0002J\u0014\u0010\u0095\u0001\u001a\u00030\u008f\u00012\b\u0010\u0096\u0001\u001a\u00030\u0097\u0001H\u0007J\u0014\u0010\u0098\u0001\u001a\u00030\u008f\u00012\b\u0010\u0096\u0001\u001a\u00030\u0099\u0001H\u0007J\u0014\u0010\u009a\u0001\u001a\u00030\u008f\u00012\b\u0010\u0096\u0001\u001a\u00030\u009b\u0001H\u0007J\u0014\u0010\u009c\u0001\u001a\u00030\u008f\u00012\b\u0010\u009d\u0001\u001a\u00030\u009e\u0001H\u0002J\u0014\u0010\u009f\u0001\u001a\u00030\u008f\u00012\b\u0010 \u0001\u001a\u00030¡\u0001H\u0007J\u001b\u0010¢\u0001\u001a\u00020\u00112\b\u0010£\u0001\u001a\u00030¤\u00012\b\u0010¥\u0001\u001a\u00030¤\u0001J\n\u0010¦\u0001\u001a\u00030\u008f\u0001H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R\u001a\u0010\u0019\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0013\"\u0004\b\u001b\u0010\u0015R\u001a\u0010\u001c\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0013\"\u0004\b\u001e\u0010\u0015R\u001a\u0010\u001f\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0013\"\u0004\b!\u0010\u0015R\u001a\u0010\"\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0013\"\u0004\b$\u0010\u0015R\u001a\u0010%\u001a\u00020&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010(\"\u0004\b)\u0010*R\u0011\u0010+\u001a\u00020,¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u001a\u0010/\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u0006\"\u0004\b1\u0010\bR\u001a\u00102\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0013\"\u0004\b4\u0010\u0015R\u001a\u00105\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u0013\"\u0004\b7\u0010\u0015R\u001a\u00108\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u0013\"\u0004\b:\u0010\u0015R\u001a\u0010;\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\u0013\"\u0004\b=\u0010\u0015R\u0011\u0010>\u001a\u00020?¢\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u0014\u0010B\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\bC\u0010\u0006R\u001a\u0010D\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\u0013\"\u0004\bF\u0010\u0015R\u001a\u0010G\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010\u0013\"\u0004\bI\u0010\u0015R\u001a\u0010J\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010\u0013\"\u0004\bL\u0010\u0015R\u001a\u0010M\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010\u0013\"\u0004\bO\u0010\u0015R\u001a\u0010P\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010\u0013\"\u0004\bR\u0010\u0015R\u001a\u0010S\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010\u0013\"\u0004\bU\u0010\u0015R\u0011\u0010V\u001a\u00020,¢\u0006\b\n\u0000\u001a\u0004\bW\u0010.R\u001a\u0010X\u001a\u00020YX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]R\u0011\u0010^\u001a\u00020?¢\u0006\b\n\u0000\u001a\u0004\b_\u0010AR\u001a\u0010`\u001a\u00020aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR \u0010f\u001a\b\u0012\u0004\u0012\u00020\u00010gX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bh\u0010\r\"\u0004\bi\u0010\u000fR\u001a\u0010j\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bk\u0010\u0013\"\u0004\bl\u0010\u0015R\u001a\u0010m\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010\u0013\"\u0004\bo\u0010\u0015R\u0011\u0010p\u001a\u00020?¢\u0006\b\n\u0000\u001a\u0004\bq\u0010AR\u001a\u0010r\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bs\u0010\u0013\"\u0004\bt\u0010\u0015R\u001a\u0010u\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bv\u0010\u0013\"\u0004\bw\u0010\u0015R\u001a\u0010x\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\by\u0010\u0013\"\u0004\bz\u0010\u0015R\u001a\u0010{\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b|\u0010\u0013\"\u0004\b}\u0010\u0015R\u001b\u0010~\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000f\n\u0000\u001a\u0004\b\u007f\u0010\u0013\"\u0005\b\u0080\u0001\u0010\u0015R\u001d\u0010\u0081\u0001\u001a\u00020\u0011X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0082\u0001\u0010\u0013\"\u0005\b\u0083\u0001\u0010\u0015R\u001d\u0010\u0084\u0001\u001a\u00020&X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0085\u0001\u0010(\"\u0005\b\u0086\u0001\u0010*R\u001d\u0010\u0087\u0001\u001a\u00020\u0004X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0088\u0001\u0010\u0006\"\u0005\b\u0089\u0001\u0010\bR\u0016\u0010\u008a\u0001\u001a\u00020\u0004X\u0086D¢\u0006\t\n\u0000\u001a\u0005\b\u008b\u0001\u0010\u0006R\u0016\u0010\u008c\u0001\u001a\u00020\u0004X\u0086D¢\u0006\t\n\u0000\u001a\u0005\b\u008d\u0001\u0010\u0006¨\u0006©\u0001"},
    d2 = { "Lme/Skid/Modules/Render/JelloTabGui;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "ModulePositonY", "", "getModulePositonY", "()F", "setModulePositonY", "(F)V", "Modulecategory", "", "Lme/Skid/Modules/Render/JelloTabGui$AnimaitonCategory;", "getModulecategory", "()Ljava/util/List;", "setModulecategory", "(Ljava/util/List;)V", "bB", "", "getBB", "()I", "setBB", "(I)V", "bBlue", "getBBlue", "setBBlue", "bG", "getBG", "setBG", "bGreen", "getBGreen", "setBGreen", "bR", "getBR", "setBR", "bRed", "getBRed", "setBRed", "bottom", "Ljava/awt/Color;", "getBottom", "()Ljava/awt/Color;", "setBottom", "(Ljava/awt/Color;)V", "categoryAnimaiton", "Lme/Skid/utils/Render/Translate;", "getCategoryAnimaiton", "()Lme/Skid/utils/Render/Translate;", "categoryPositonY", "getCategoryPositonY", "setCategoryPositonY", "colorBottom", "getColorBottom", "setColorBottom", "colorBottomRight", "getColorBottomRight", "setColorBottomRight", "colorTop", "getColorTop", "setColorTop", "colorTopRight", "getColorTopRight", "setColorTopRight", "gradientBackground", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getGradientBackground", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "height", "getHeight", "lastbBlue", "getLastbBlue", "setLastbBlue", "lastbGreen", "getLastbGreen", "setLastbGreen", "lastbRed", "getLastbRed", "setLastbRed", "lasttBlue", "getLasttBlue", "setLasttBlue", "lasttGreen", "getLasttGreen", "setLasttGreen", "lasttRed", "getLasttRed", "setLasttRed", "moduleAnimaiton", "getModuleAnimaiton", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getMsTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "setMsTimer", "(Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;)V", "notselectrect", "getNotselectrect", "openModuleGui", "", "getOpenModuleGui", "()Z", "setOpenModuleGui", "(Z)V", "selecteModule", "", "getSelecteModule", "setSelecteModule", "selecteModuleindex", "getSelecteModuleindex", "setSelecteModuleindex", "selectedCategory", "getSelectedCategory", "setSelectedCategory", "selectgradientBackground", "getSelectgradientBackground", "tB", "getTB", "setTB", "tBlue", "getTBlue", "setTBlue", "tG", "getTG", "setTG", "tGreen", "getTGreen", "setTGreen", "tR", "getTR", "setTR", "tRed", "getTRed", "setTRed", "top", "getTop", "setTop", "width", "getWidth", "setWidth", "x", "getX", "y", "getY", "disabler", "", "disablerScissorBox", "enabler", "enablerScissorBox", "handleKey", "keyCode", "keyevent", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "parseAction", "action", "Lme/Skid/Modules/Render/JelloTabGui$Action;", "rendertabGui", "evnet", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "smoothAnimation", "current", "", "last", "updateBackGound", "Action", "AnimaitonCategory", "LiquidBounce"}
)
public final class JelloTabGui extends Module {

    @NotNull
    private final BoolValue notselectrect = new BoolValue("SelectNotDrawRect", false);
    @NotNull
    private final BoolValue selectgradientBackground = new BoolValue("SelectGradientRect", false);
    @NotNull
    private final BoolValue gradientBackground = new BoolValue("GradientRect", true);
    private final float x = 5.0F;
    private final float y = 50.0F;
    private final float height = 78.5F;
    private float width = 75.0F;
    private boolean openModuleGui;
    private int selectedCategory;
    private int selecteModuleindex;
    @NotNull
    private List selecteModule = CollectionsKt.emptyList();
    @NotNull
    private List Modulecategory;
    @NotNull
    private final Translate categoryAnimaiton;
    @NotNull
    private final Translate moduleAnimaiton;
    private float categoryPositonY;
    private float ModulePositonY;
    @NotNull
    private MSTimer msTimer;
    @NotNull
    private Color top;
    @NotNull
    private Color bottom;
    private int tRed;
    private int tGreen;
    private int tBlue;
    private int lasttRed;
    private int lasttGreen;
    private int lasttBlue;
    private int bRed;
    private int bGreen;
    private int bBlue;
    private int lastbRed;
    private int lastbGreen;
    private int lastbBlue;
    private int colorTop;
    private int colorTopRight;
    private int colorBottom;
    private int colorBottomRight;
    private int tR;
    private int tG;
    private int tB;
    private int bR;
    private int bG;
    private int bB;

    @NotNull
    public final BoolValue getNotselectrect() {
        return this.notselectrect;
    }

    @NotNull
    public final BoolValue getSelectgradientBackground() {
        return this.selectgradientBackground;
    }

    @NotNull
    public final BoolValue getGradientBackground() {
        return this.gradientBackground;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final float getHeight() {
        return this.height;
    }

    public final float getWidth() {
        return this.width;
    }

    public final void setWidth(float <set-?>) {
        this.width = <set-?>;
    }

    public final boolean getOpenModuleGui() {
        return this.openModuleGui;
    }

    public final void setOpenModuleGui(boolean <set-?>) {
        this.openModuleGui = <set-?>;
    }

    public final int getSelectedCategory() {
        return this.selectedCategory;
    }

    public final void setSelectedCategory(int <set-?>) {
        this.selectedCategory = <set-?>;
    }

    public final int getSelecteModuleindex() {
        return this.selecteModuleindex;
    }

    public final void setSelecteModuleindex(int <set-?>) {
        this.selecteModuleindex = <set-?>;
    }

    @NotNull
    public final List getSelecteModule() {
        return this.selecteModule;
    }

    public final void setSelecteModule(@NotNull List <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.selecteModule = <set-?>;
    }

    @NotNull
    public final List getModulecategory() {
        return this.Modulecategory;
    }

    public final void setModulecategory(@NotNull List <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.Modulecategory = <set-?>;
    }

    @NotNull
    public final Translate getCategoryAnimaiton() {
        return this.categoryAnimaiton;
    }

    @NotNull
    public final Translate getModuleAnimaiton() {
        return this.moduleAnimaiton;
    }

    public final float getCategoryPositonY() {
        return this.categoryPositonY;
    }

    public final void setCategoryPositonY(float <set-?>) {
        this.categoryPositonY = <set-?>;
    }

    public final float getModulePositonY() {
        return this.ModulePositonY;
    }

    public final void setModulePositonY(float <set-?>) {
        this.ModulePositonY = <set-?>;
    }

    @NotNull
    public final MSTimer getMsTimer() {
        return this.msTimer;
    }

    public final void setMsTimer(@NotNull MSTimer <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.msTimer = <set-?>;
    }

    @NotNull
    public final Color getTop() {
        return this.top;
    }

    public final void setTop(@NotNull Color <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.top = <set-?>;
    }

    @NotNull
    public final Color getBottom() {
        return this.bottom;
    }

    public final void setBottom(@NotNull Color <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.bottom = <set-?>;
    }

    public final int getTRed() {
        return this.tRed;
    }

    public final void setTRed(int <set-?>) {
        this.tRed = <set-?>;
    }

    public final int getTGreen() {
        return this.tGreen;
    }

    public final void setTGreen(int <set-?>) {
        this.tGreen = <set-?>;
    }

    public final int getTBlue() {
        return this.tBlue;
    }

    public final void setTBlue(int <set-?>) {
        this.tBlue = <set-?>;
    }

    public final int getLasttRed() {
        return this.lasttRed;
    }

    public final void setLasttRed(int <set-?>) {
        this.lasttRed = <set-?>;
    }

    public final int getLasttGreen() {
        return this.lasttGreen;
    }

    public final void setLasttGreen(int <set-?>) {
        this.lasttGreen = <set-?>;
    }

    public final int getLasttBlue() {
        return this.lasttBlue;
    }

    public final void setLasttBlue(int <set-?>) {
        this.lasttBlue = <set-?>;
    }

    public final int getBRed() {
        return this.bRed;
    }

    public final void setBRed(int <set-?>) {
        this.bRed = <set-?>;
    }

    public final int getBGreen() {
        return this.bGreen;
    }

    public final void setBGreen(int <set-?>) {
        this.bGreen = <set-?>;
    }

    public final int getBBlue() {
        return this.bBlue;
    }

    public final void setBBlue(int <set-?>) {
        this.bBlue = <set-?>;
    }

    public final int getLastbRed() {
        return this.lastbRed;
    }

    public final void setLastbRed(int <set-?>) {
        this.lastbRed = <set-?>;
    }

    public final int getLastbGreen() {
        return this.lastbGreen;
    }

    public final void setLastbGreen(int <set-?>) {
        this.lastbGreen = <set-?>;
    }

    public final int getLastbBlue() {
        return this.lastbBlue;
    }

    public final void setLastbBlue(int <set-?>) {
        this.lastbBlue = <set-?>;
    }

    public final int getColorTop() {
        return this.colorTop;
    }

    public final void setColorTop(int <set-?>) {
        this.colorTop = <set-?>;
    }

    public final int getColorTopRight() {
        return this.colorTopRight;
    }

    public final void setColorTopRight(int <set-?>) {
        this.colorTopRight = <set-?>;
    }

    public final int getColorBottom() {
        return this.colorBottom;
    }

    public final void setColorBottom(int <set-?>) {
        this.colorBottom = <set-?>;
    }

    public final int getColorBottomRight() {
        return this.colorBottomRight;
    }

    public final void setColorBottomRight(int <set-?>) {
        this.colorBottomRight = <set-?>;
    }

    @EventTarget
    public final void onTick(@NotNull TickEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.lasttRed = this.tRed;
        this.lasttGreen = this.tGreen;
        this.lasttBlue = this.tBlue;
        this.lastbRed = this.bRed;
        this.lastbGreen = this.bGreen;
        this.lastbBlue = this.bBlue;
        Color top = ColorUtils1.blend(ColorUtils1.colorFromInt(this.colorTop), ColorUtils1.colorFromInt(this.colorTopRight));
        Color bottom = ColorUtils1.blend(ColorUtils1.colorFromInt(this.colorBottom), ColorUtils1.colorFromInt(this.colorBottomRight));

        this.bRed += (int) ((double) ((bottom.getRed() - this.bRed) / 5) + 0.1D);
        this.bGreen += (int) ((double) ((bottom.getGreen() - this.bGreen) / 5) + 0.1D);
        this.bBlue += (int) ((double) ((bottom.getBlue() - this.bBlue) / 5) + 0.1D);
        this.tRed += (int) ((double) ((top.getRed() - this.tRed) / 5) + 0.1D);
        this.tGreen += (int) ((double) ((top.getGreen() - this.tGreen) / 5) + 0.1D);
        this.tBlue += (int) ((double) ((top.getBlue() - this.tBlue) / 5) + 0.1D);
        this.tRed = Math.min(this.tRed, 255);
        this.tGreen = Math.min(this.tGreen, 255);
        this.tBlue = Math.min(this.tBlue, 255);
        this.tRed = Math.max(this.tRed, 0);
        this.tGreen = Math.max(this.tGreen, 0);
        this.tBlue = Math.max(this.tBlue, 0);
        this.bRed = Math.min(this.bRed, 255);
        this.bGreen = Math.min(this.bGreen, 255);
        this.bBlue = Math.min(this.bBlue, 255);
        this.bRed = Math.max(this.bRed, 0);
        this.bGreen = Math.max(this.bGreen, 0);
        this.bBlue = Math.max(this.bBlue, 0);
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        if (this.msTimer.hasTimePassed(50L)) {
            short width = 0;
            short height = 0;
            IntBuffer pixelBuffer = (IntBuffer) null;
            int[] pixelValues = (int[]) null;

            if (OpenGlHelper.isFramebufferEnabled()) {
                width = 180;
                height = 280;
            }

            int i = width * height;

            pixelBuffer = BufferUtils.createIntBuffer(i);
            pixelValues = new int[i];
            GL11.glPixelStorei(3333, 1);
            GL11.glPixelStorei(3317, 1);
            if (pixelBuffer == null) {
                Intrinsics.throwNpe();
            }

            pixelBuffer.clear();
            GL11.glReadPixels(0, sr.getScaledHeight() - (height - sr.getScaledHeight()), width, height, '�?', '�?', pixelBuffer);
            pixelBuffer.get(pixelValues);
            if (!(MinecraftInstance.mc.getCurrentScreen() instanceof GuiGameOver) && MinecraftInstance.mc2.gameSettings.guiScale == 2 && pixelValues.length > 1000) {
                this.colorTop = pixelValues[45 * sr.getScaleFactor() * width + 10];
                this.colorTopRight = pixelValues[45 * sr.getScaleFactor() * width + 130];
                this.colorBottom = pixelValues[122 * sr.getScaleFactor() * width + 10];
                this.colorBottomRight = pixelValues[122 * sr.getScaleFactor() * width + 130];
            }

            width = 0;
            height = 0;
            pixelBuffer = (IntBuffer) null;
            pixelValues = (int[]) null;
            if (OpenGlHelper.isFramebufferEnabled()) {
                width = 280;
                height = 150;
            }

            i = width * height;
            pixelBuffer = BufferUtils.createIntBuffer(i);
            pixelValues = new int[i];
            GL11.glPixelStorei(3333, 1);
            GL11.glPixelStorei(3317, 1);
            if (pixelBuffer == null) {
                Intrinsics.throwNpe();
            }

            pixelBuffer.clear();
            GL11.glReadPixels(sr.getScaledWidth() - (width - sr.getScaledWidth()), sr.getScaledHeight() - (height - sr.getScaledHeight()), width, height, '�?', '�?', pixelBuffer);
            pixelBuffer.get(pixelValues);
            this.msTimer.reset();
        }

    }

    public final int getTR() {
        return this.tR;
    }

    public final void setTR(int <set-?>) {
        this.tR = <set-?>;
    }

    public final int getTG() {
        return this.tG;
    }

    public final void setTG(int <set-?>) {
        this.tG = <set-?>;
    }

    public final int getTB() {
        return this.tB;
    }

    public final void setTB(int <set-?>) {
        this.tB = <set-?>;
    }

    public final int getBR() {
        return this.bR;
    }

    public final void setBR(int <set-?>) {
        this.bR = <set-?>;
    }

    public final int getBG() {
        return this.bG;
    }

    public final void setBG(int <set-?>) {
        this.bG = <set-?>;
    }

    public final int getBB() {
        return this.bB;
    }

    public final void setBB(int <set-?>) {
        this.bB = <set-?>;
    }

    @EventTarget
    public final void rendertabGui(@NotNull Render2DEvent evnet) {
        Intrinsics.checkParameterIsNotNull(evnet, "evnet");
        this.tR = this.smoothAnimation((double) this.tRed, (double) this.lasttRed);
        this.tG = this.smoothAnimation((double) this.tGreen, (double) this.lasttGreen);
        this.tB = this.smoothAnimation((double) this.tBlue, (double) this.lasttBlue);
        this.bR = this.smoothAnimation((double) this.bRed, (double) this.lastbRed);
        this.bG = this.smoothAnimation((double) this.bGreen, (double) this.lastbGreen);
        this.bB = this.smoothAnimation((double) this.bBlue, (double) this.lastbBlue);
        if (((Boolean) this.gradientBackground.get()).booleanValue()) {
            RenderUtils.R2DUtils.drawGradientRect(5.0F, 50.0F, 80.0F, 127.5F, (new Color(this.tR, this.tG, this.tB, 255)).getRGB(), (new Color(this.bR, this.bG, this.bB, 255)).getRGB());
        }

        this.updateBackGound();
        this.enabler();
        this.enablerScissorBox();
        this.categoryAnimaiton.translate(0.0F, (float) this.selectedCategory * 15.0F, 2.0D);
        float categorysupery = this.categoryAnimaiton.getY() - 60.0F > (float) 0 ? this.categoryAnimaiton.getY() - 60.0F : 0.0F;

        if (!((Boolean) this.notselectrect.get()).booleanValue()) {
            RenderUtils.drawRect(this.x, this.y + this.categoryAnimaiton.getY() - categorysupery, this.width + (float) 5, this.y + this.categoryAnimaiton.getY() + 17.0F - categorysupery, new Color(55, 55, 55, 50));
        }

        if (((Boolean) this.selectgradientBackground.get()).booleanValue()) {
            RenderUtils.R2DUtils.drawGradientRect(this.x, this.y + this.categoryAnimaiton.getY() - categorysupery, this.width + (float) 5, this.y + this.categoryAnimaiton.getY() + 17.0F - categorysupery, (new Color(this.tR, this.tG, this.tB, 255)).getRGB(), (new Color(this.bR, this.bG, this.bB, 255)).getRGB());
        }

        this.categoryPositonY = 0.0F;
        Iterable modulesupery = (Iterable) this.Modulecategory;
        boolean positiony = false;
        int $this$forEachIndexed$iv = 0;

        for (Iterator $i$f$forEachIndexed = modulesupery.iterator(); $i$f$forEachIndexed.hasNext(); this.categoryPositonY += 15.0F) {
            Object index$iv = $i$f$forEachIndexed.next();
            int i = $this$forEachIndexed$iv++;
            boolean item$iv = false;

            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }

            JelloTabGui.AnimaitonCategory category = (JelloTabGui.AnimaitonCategory) index$iv;
            boolean module = false;

            category.getAnimation().translate(this.selectedCategory == i ? 15.0F : 5.0F, 0.0F, 2.0D);
            IFontRenderer index = Fonts.fontSFUI35;

            index.drawString(category.getDisplayname(), this.x + category.getAnimation().getX(), this.y + 5.0F + this.categoryPositonY - categorysupery, -1);
        }

        this.disablerScissorBox();
        this.disabler();
        this.enabler();
        this.moduleAnimaiton.translate(0.0F, (float) this.selecteModuleindex * 15.0F, 2.0D);
        float f = this.moduleAnimaiton.getY() - 150.0F > (float) 0 ? this.moduleAnimaiton.getY() - 150.0F : 0.0F;
        float f1 = this.ModulePositonY >= (float) 165 ? 165.0F : this.ModulePositonY;

        if (this.openModuleGui) {
            if (((Boolean) this.gradientBackground.get()).booleanValue()) {
                RenderUtils.R2DUtils.drawGradientRect(90.0F, 50.0F, 170.0F, 216.67F, (new Color(this.tR, this.tG, this.tB, 255)).getRGB(), (new Color(this.bR, this.bG, this.bB, 255)).getRGB());
            }

            RenderUtils.drawShadow(this.x + this.width + (float) 10, this.y, this.width + (float) 5, f1 + 2.0F);
            BlurUtils.blurArea(this.x + this.width + 90.0F, this.y, this.width + (float) 15, f1 + 52.5F, 10.0F);
            if (!((Boolean) this.notselectrect.get()).booleanValue()) {
                RenderUtils.drawRect(this.x + this.width + (float) 10, this.y + this.moduleAnimaiton.getY() - f, this.x + this.width + this.width + (float) 15, this.y + this.moduleAnimaiton.getY() - f + 17.0F, new Color(55, 55, 55, 50));
            }

            if (((Boolean) this.selectgradientBackground.get()).booleanValue()) {
                RenderUtils.R2DUtils.drawGradientRect(this.x + this.width + (float) 10, this.y + this.moduleAnimaiton.getY() - f, this.x + this.width + this.width + (float) 15, this.y + this.moduleAnimaiton.getY() - f + 17.0F, (new Color(this.tR, this.tG, this.tB, 255)).getRGB(), (new Color(this.bR, this.bG, this.bB, 255)).getRGB());
            }
        }

        RenderUtils.makeScissorBox(this.x + this.width + (float) 10, this.y, this.x + this.width + this.width + (float) 15, this.y + f1);
        GL11.glEnable(3089);
        this.ModulePositonY = 0.0F;
        Iterable iterable = (Iterable) this.selecteModule;
        boolean flag = false;
        int j = 0;

        for (Iterator iterator = iterable.iterator(); iterator.hasNext(); this.ModulePositonY += 15.0F) {
            Object object = iterator.next();
            int k = j++;
            boolean flag1 = false;

            if (k < 0) {
                CollectionsKt.throwIndexOverflow();
            }

            Module module = (Module) object;
            boolean $i$a$-forEachIndexed-JelloTabGui$rendertabGui$2 = false;

            module.getTab().translate(this.selecteModuleindex == k ? 15.0F : 5.0F, 0.0F, 2.0D);
            IFontRenderer font = module.getState() ? Fonts.fontSFUI35 : Fonts.fontSFUI35;

            font.drawString(module.getName(), this.x + this.width + (float) 10 + module.getTab().getX(), this.y + 5.0F + this.ModulePositonY - f, -1);
        }

        GL11.glDisable(3089);
        this.disabler();
    }

    private final void updateBackGound() {
        RenderUtils.drawShadow(this.x, this.y, this.width, this.height - (float) 1);
        BlurUtils.blurArea(this.x, this.y, this.width + 4.9F, this.height + 49.5F, 10.0F);
    }

    private final void enablerScissorBox() {
        RenderUtils.makeScissorBox(this.x, this.y, this.x + this.width, this.y + this.height);
        GL11.glEnable(3089);
    }

    private final void disablerScissorBox() {
        GL11.glDisable(3089);
    }

    private final void enabler() {
        GlStateManager.pushMatrix();
    }

    private final void disabler() {
        GlStateManager.popMatrix();
    }

    @EventTarget
    public final void keyevent(@NotNull KeyEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        int key = event.getKey();

        this.handleKey(key);
    }

    private final void handleKey(int keyCode) {
        switch (keyCode) {
        case 28:
            this.parseAction(JelloTabGui.Action.TOGGLE);
            break;

        case 200:
            this.parseAction(JelloTabGui.Action.UP);
            break;

        case 203:
            this.parseAction(JelloTabGui.Action.LEFT);
            break;

        case 205:
            this.parseAction(JelloTabGui.Action.RIGHT);
            break;

        case 208:
            this.parseAction(JelloTabGui.Action.DOWN);
        }

    }

    public final int smoothAnimation(double current, double last) {
        return (int) (current * (double) MinecraftInstance.mc.getTimer().getRenderPartialTicks() + last * (double) (1.0F - MinecraftInstance.mc.getTimer().getRenderPartialTicks()));
    }

    private final void parseAction(JelloTabGui.Action action) {
        List list;
        int i;

        switch (JelloTabGui$WhenMappings.$EnumSwitchMapping$0[action.ordinal()]) {
        case 1:
            if (this.selectedCategory > 0 && !this.openModuleGui) {
                i = this.selectedCategory;
                this.selectedCategory += -1;
            }

            if (this.selecteModuleindex > 0) {
                i = this.selecteModuleindex;
                this.selecteModuleindex += -1;
            }
            break;

        case 2:
            if (this.selectedCategory < CollectionsKt.getLastIndex(this.Modulecategory) && !this.openModuleGui) {
                i = this.selectedCategory++;
            }

            if (this.selecteModuleindex < CollectionsKt.getLastIndex(this.selecteModule)) {
                i = this.selecteModuleindex++;
            }
            break;

        case 3:
            if (this.openModuleGui) {
                this.openModuleGui = false;
                this.selecteModuleindex = 0;
                boolean flag = false;

                list = CollectionsKt.emptyList();
                this.selecteModule = list;
            }
            break;

        case 4:
            if (!this.openModuleGui) {
                this.openModuleGui = true;
                Iterable iterable = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();
                boolean $i$f$sortedBy = false;
                Collection destination$iv$iv = (Collection) (new ArrayList());
                boolean $i$f$filterTo = false;
                Iterator iterator = iterable.iterator();

                while (iterator.hasNext()) {
                    Object element$iv$iv = iterator.next();
                    Module it = (Module) element$iv$iv;
                    boolean $i$a$-filter-JelloTabGui$parseAction$1 = false;

                    if (it.getCategory() == ModuleCategory.values()[this.selectedCategory]) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }

                list = (List) destination$iv$iv;
                iterable = (Iterable) list;
                $i$f$sortedBy = false;
                boolean flag1 = false;
                Comparator comparator = (Comparator) (new JelloTabGui$parseAction$$inlined$sortedBy$1());

                list = CollectionsKt.sortedWith(iterable, comparator);
                this.selecteModule = list;
            }
            break;

        case 5:
            if (this.openModuleGui) {
                Module selecetd = (Module) this.selecteModule.get(this.selecteModuleindex);

                selecetd.toggle();
            }
        }

    }

    public JelloTabGui() {
        boolean index = false;
        List list = (List) (new ArrayList());

        this.Modulecategory = list;
        this.categoryAnimaiton = new Translate(0.0F, 0.0F);
        this.moduleAnimaiton = new Translate(0.0F, 0.0F);
        this.msTimer = new MSTimer();
        this.setState(true);
        int i = 0;
        int j = ArraysKt.getLastIndex(ModuleCategory.values());

        if (i <= j) {
            while (true) {
                JelloTabGui.AnimaitonCategory animationcategory = new JelloTabGui.AnimaitonCategory(ModuleCategory.values()[i].getDisplayName(), new Translate(0.0F, 0.0F));

                this.Modulecategory.add(animationcategory);
                if (i == j) {
                    break;
                }

                ++i;
            }
        }

        this.top = new Color(255, 255, 255, 255);
        this.bottom = new Color(255, 255, 255, 255);
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"},
        d2 = { "Lme/Skid/Modules/Render/JelloTabGui$Action;", "", "(Ljava/lang/String;I)V", "UP", "DOWN", "LEFT", "RIGHT", "TOGGLE", "LiquidBounce"}
    )
    public static enum Action {

        UP, DOWN, LEFT, RIGHT, TOGGLE;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"},
        d2 = { "Lme/Skid/Modules/Render/JelloTabGui$AnimaitonCategory;", "", "displayname", "", "animation", "Lme/Skid/utils/Render/Translate;", "(Ljava/lang/String;Lme/Skid/utils/Render/Translate;)V", "getAnimation", "()Lme/Skid/utils/Render/Translate;", "setAnimation", "(Lme/Skid/utils/Render/Translate;)V", "getDisplayname", "()Ljava/lang/String;", "setDisplayname", "(Ljava/lang/String;)V", "LiquidBounce"}
    )
    public static final class AnimaitonCategory {

        @NotNull
        private String displayname;
        @NotNull
        private Translate animation;

        @NotNull
        public final String getDisplayname() {
            return this.displayname;
        }

        public final void setDisplayname(@NotNull String <set-?>) {
            Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
            this.displayname = <set-?>;
        }

        @NotNull
        public final Translate getAnimation() {
            return this.animation;
        }

        public final void setAnimation(@NotNull Translate <set-?>) {
            Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
            this.animation = <set-?>;
        }

        public AnimaitonCategory(@NotNull String displayname, @NotNull Translate animation) {
            Intrinsics.checkParameterIsNotNull(displayname, "displayname");
            Intrinsics.checkParameterIsNotNull(animation, "animation");
            super();
            this.displayname = displayname;
            this.animation = animation;
        }
    }
}
