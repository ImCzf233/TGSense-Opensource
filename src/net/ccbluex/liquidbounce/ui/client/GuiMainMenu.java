package net.ccbluex.liquidbounce.ui.client;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.Tenacity.utils.render.RoundedUtil;
import me.Skid.utils.MenuShader;
import me.Skid.utils.ZoomUtil;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.font.CFontRenderer;
import net.ccbluex.liquidbounce.font.FontLoaders;
import net.ccbluex.liquidbounce.injection.backend.ClassProviderImpl;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.blur.BlurBuffer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u00032\u0006\u0010/\u001a\u00020*H\u0016J\b\u00100\u001a\u00020,H\u0016J \u00101\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u00032\u0006\u00102\u001a\u00020\u0003H\u0014R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0014\u001a\n \u0016*\u0004\u0018\u00010\u00150\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R\u000e\u0010)\u001a\u00020*X\u0082D¢\u0006\u0002\n\u0000¨\u00063"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/GuiMainMenu;", "Lnet/minecraft/client/gui/GuiScreen;", "pass", "", "(I)V", "LogoZoom", "Lme/Skid/utils/ZoomUtil;", "altsButton", "Lnet/minecraft/util/ResourceLocation;", "altsZoom", "arrayList", "Ljava/util/ArrayList;", "Lme/Skid/ui/Button;", "Lkotlin/collections/ArrayList;", "getArrayList", "()Ljava/util/ArrayList;", "setArrayList", "(Ljava/util/ArrayList;)V", "backgroundShader", "Lme/Skid/utils/MenuShader;", "mc", "Lnet/minecraft/client/Minecraft;", "kotlin.jvm.PlatformType", "getMc", "()Lnet/minecraft/client/Minecraft;", "setMc", "(Lnet/minecraft/client/Minecraft;)V", "multiplayerButton", "multiplayerZoom", "quitButton", "quitZoom", "settingsButton", "settingsZoom", "singleplayerButton", "singleplayerZoom", "sr", "Lnet/minecraft/client/gui/ScaledResolution;", "getSr", "()Lnet/minecraft/client/gui/ScaledResolution;", "setSr", "(Lnet/minecraft/client/gui/ScaledResolution;)V", "zoomValue", "", "drawScreen", "", "mouseX", "mouseY", "partialTicks", "initGui", "mouseClicked", "mouseButton", "LiquidBounce"}
)
public final class GuiMainMenu extends GuiScreen {

    private Minecraft mc = Minecraft.getMinecraft();
    @Nullable
    private ScaledResolution sr;
    @NotNull
    private ArrayList arrayList = new ArrayList();
    private final ResourceLocation singleplayerButton;
    private final ResourceLocation multiplayerButton;
    private final ResourceLocation altsButton;
    private final ResourceLocation settingsButton;
    private final ResourceLocation quitButton;
    private final MenuShader backgroundShader;
    private final float zoomValue = 0.298F;
    private ZoomUtil LogoZoom;
    private ZoomUtil singleplayerZoom;
    private ZoomUtil multiplayerZoom;
    private ZoomUtil altsZoom;
    private ZoomUtil settingsZoom;
    private ZoomUtil quitZoom;

    public final Minecraft getMc() {
        return this.mc;
    }

    public final void setMc(Minecraft <set-?>) {
        this.mc = <set-?>;
    }

    @Nullable
    public final ScaledResolution getSr() {
        return this.sr;
    }

    public final void setSr(@Nullable ScaledResolution <set-?>) {
        this.sr = <set-?>;
    }

    @NotNull
    public final ArrayList getArrayList() {
        return this.arrayList;
    }

    public final void setArrayList(@NotNull ArrayList <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.arrayList = <set-?>;
    }

    public void initGui() {
        this.sr = new ScaledResolution(Minecraft.getMinecraft());
        this.arrayList.clear();
        ZoomUtil zoomutil = new ZoomUtil;
        ScaledResolution scaledresolution = this.sr;

        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        float f = (float) scaledresolution.getScaledWidth() / 2.0F + (float) 5;
        ScaledResolution scaledresolution1 = this.sr;

        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.<init>(f, (float) scaledresolution1.getScaledHeight() / 2.0F - (float) 105, (float) 114, 24.0F, 12L, this.zoomValue, 6.0F);
        this.LogoZoom = zoomutil;
        zoomutil = new ZoomUtil;
        scaledresolution = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        f = (float) scaledresolution.getScaledWidth() / 2.0F - (float) 12 - (float) 40;
        scaledresolution1 = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.<init>(f, (float) scaledresolution1.getScaledHeight() / 2.0F - (float) 60, (float) 114, 24.0F, 12L, this.zoomValue, 6.0F);
        this.singleplayerZoom = zoomutil;
        zoomutil = new ZoomUtil;
        scaledresolution = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        f = (float) scaledresolution.getScaledWidth() / 2.0F - (float) 12 - (float) 40;
        scaledresolution1 = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.<init>(f, (float) scaledresolution1.getScaledHeight() / 2.0F - (float) 30, (float) 114, 24.0F, 12L, this.zoomValue, 6.0F);
        this.multiplayerZoom = zoomutil;
        zoomutil = new ZoomUtil;
        scaledresolution = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        f = (float) scaledresolution.getScaledWidth() / 2.0F - (float) 12 - (float) 40;
        scaledresolution1 = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.<init>(f, (float) scaledresolution1.getScaledHeight() / 2.0F, (float) 114, 24.0F, 12L, this.zoomValue, 6.0F);
        this.altsZoom = zoomutil;
        zoomutil = new ZoomUtil;
        scaledresolution = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        f = (float) scaledresolution.getScaledWidth() / 2.0F - (float) 12 - (float) 40;
        scaledresolution1 = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.<init>(f, (float) scaledresolution1.getScaledHeight() / 2.0F + (float) 30, (float) 114, 24.0F, 12L, this.zoomValue, 6.0F);
        this.settingsZoom = zoomutil;
        zoomutil = new ZoomUtil;
        scaledresolution = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        f = (float) scaledresolution.getScaledWidth() / 2.0F - (float) 12 - (float) 40;
        scaledresolution1 = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.<init>(f, (float) scaledresolution1.getScaledHeight() / 2.0F + (float) 60, (float) 114, 24.0F, 12L, this.zoomValue, 6.0F);
        this.quitZoom = zoomutil;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.backgroundShader.render(this.sr);
        ZoomUtil zoomutil = this.singleplayerZoom;

        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.update(mouseX, mouseY);
        zoomutil = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.update(mouseX, mouseY);
        zoomutil = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.update(mouseX, mouseY);
        zoomutil = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.update(mouseX, mouseY);
        zoomutil = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        zoomutil.update(mouseX, mouseY);
        Fonts.jellolight21.drawString("©TGSense made by C07", 4.0F, (float) this.height - 12.0F, (new Color(255, 255, 255, 255)).getRGB(), false);
        zoomutil = this.singleplayerZoom;
        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        float f = zoomutil.getX();
        ZoomUtil zoomutil1 = this.singleplayerZoom;

        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        float f1 = zoomutil1.getY();
        ZoomUtil zoomutil2 = this.singleplayerZoom;

        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        float f2 = zoomutil2.getWidth();
        ZoomUtil zoomutil3 = this.singleplayerZoom;

        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        RoundedUtil.drawRound(f, f1, f2, zoomutil3.getHeight(), 8.0F, new Color(23, 23, 23, 40));
        zoomutil = this.singleplayerZoom;
        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.singleplayerZoom;
        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.singleplayerZoom;
        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.singleplayerZoom;
        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        BlurBuffer.blurRoundArea(f, f1, f2, zoomutil3.getHeight(), 8.0F);
        CFontRenderer cfontrenderer = FontLoaders.C18;
        ScaledResolution scaledresolution = this.sr;

        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        f2 = (float) scaledresolution.getScaledWidth() / 2.0F + 5.0F;
        ScaledResolution scaledresolution1 = this.sr;

        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        cfontrenderer.drawCenteredStringWithShadow("SinglePlayer", f2, (float) scaledresolution1.getScaledHeight() / 2.0F - (float) 55, -1);
        zoomutil = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        RoundedUtil.drawRound(f, f1, f2, zoomutil3.getHeight(), 8.0F, new Color(23, 23, 23, 40));
        zoomutil = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        BlurBuffer.blurRoundArea(f, f1, f2, zoomutil3.getHeight(), 8.0F);
        cfontrenderer = FontLoaders.C18;
        scaledresolution = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        f2 = (float) scaledresolution.getScaledWidth() / 2.0F + 5.0F;
        scaledresolution1 = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        cfontrenderer.drawCenteredStringWithShadow("MultiPlayer", f2, (float) scaledresolution1.getScaledHeight() / 2.0F - (float) 25, -1);
        zoomutil = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        RoundedUtil.drawRound(f, f1, f2, zoomutil3.getHeight(), 8.0F, new Color(23, 23, 23, 40));
        zoomutil = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        BlurBuffer.blurRoundArea(f, f1, f2, zoomutil3.getHeight(), 8.0F);
        cfontrenderer = FontLoaders.C18;
        scaledresolution = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        f2 = (float) scaledresolution.getScaledWidth() / 2.0F + 5.0F;
        scaledresolution1 = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        cfontrenderer.drawCenteredStringWithShadow("Alt Manager", f2, (float) scaledresolution1.getScaledHeight() / 2.0F + (float) 5, -1);
        zoomutil = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        RoundedUtil.drawRound(f, f1, f2, zoomutil3.getHeight(), 8.0F, new Color(23, 23, 23, 40));
        zoomutil = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        BlurBuffer.blurRoundArea(f, f1, f2, zoomutil3.getHeight(), 8.0F);
        cfontrenderer = FontLoaders.C18;
        scaledresolution = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        f2 = (float) scaledresolution.getScaledWidth() / 2.0F + 5.0F;
        scaledresolution1 = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        cfontrenderer.drawCenteredStringWithShadow("Options", f2, (float) scaledresolution1.getScaledHeight() / 2.0F + (float) 35, -1);
        zoomutil = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        RoundedUtil.drawRound(f, f1, f2, zoomutil3.getHeight(), 8.0F, new Color(23, 23, 23, 40));
        zoomutil = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        BlurBuffer.blurRoundArea(f, f1, f2, zoomutil3.getHeight(), 8.0F);
        cfontrenderer = FontLoaders.C18;
        scaledresolution = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        f2 = (float) scaledresolution.getScaledWidth() / 2.0F + 5.0F;
        scaledresolution1 = this.sr;
        if (this.sr == null) {
            Intrinsics.throwNpe();
        }

        cfontrenderer.drawCenteredStringWithShadow("Quit Game", f2, (float) scaledresolution1.getScaledHeight() / 2.0F + (float) 65, -1);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ZoomUtil zoomutil = this.LogoZoom;

        if (this.LogoZoom == null) {
            Intrinsics.throwNpe();
        }

        float f = zoomutil.getX();
        ZoomUtil zoomutil1 = this.LogoZoom;

        if (this.LogoZoom == null) {
            Intrinsics.throwNpe();
        }

        float f1 = zoomutil1.getY();
        ZoomUtil zoomutil2 = this.LogoZoom;

        if (this.LogoZoom == null) {
            Intrinsics.throwNpe();
        }

        float f2 = zoomutil2.getWidth();
        ZoomUtil zoomutil3 = this.LogoZoom;

        if (this.LogoZoom == null) {
            Intrinsics.throwNpe();
        }

        if (RenderUtils.isHovered(f, f1, f2, zoomutil3.getHeight(), mouseX, mouseY)) {
            try {
                Desktop.getDesktop().browse(new URI("web.badlife.icu"));
            } catch (URISyntaxException urisyntaxexception) {
                urisyntaxexception.printStackTrace();
            }
        }

        zoomutil = this.singleplayerZoom;
        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.singleplayerZoom;
        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.singleplayerZoom;
        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.singleplayerZoom;
        if (this.singleplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        if (RenderUtils.isHovered(f, f1, f2, zoomutil3.getHeight(), mouseX, mouseY)) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen) (new GuiWorldSelection((GuiScreen) this)));
        }

        zoomutil = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.multiplayerZoom;
        if (this.multiplayerZoom == null) {
            Intrinsics.throwNpe();
        }

        if (RenderUtils.isHovered(f, f1, f2, zoomutil3.getHeight(), mouseX, mouseY)) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen) (new GuiMultiplayer((GuiScreen) this)));
        }

        zoomutil = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.altsZoom;
        if (this.altsZoom == null) {
            Intrinsics.throwNpe();
        }

        if (RenderUtils.isHovered(f, f1, f2, zoomutil3.getHeight(), mouseX, mouseY)) {
            MinecraftInstance.mc.displayGuiScreen(ClassProviderImpl.INSTANCE.wrapGuiScreen((WrappedGuiScreen) (new GuiAltManager())));
        }

        zoomutil = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.settingsZoom;
        if (this.settingsZoom == null) {
            Intrinsics.throwNpe();
        }

        if (RenderUtils.isHovered(f, f1, f2, zoomutil3.getHeight(), mouseX, mouseY)) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen) (new GuiOptions((GuiScreen) this, Minecraft.getMinecraft().gameSettings)));
        }

        zoomutil = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        f = zoomutil.getX();
        zoomutil1 = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        f1 = zoomutil1.getY();
        zoomutil2 = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        f2 = zoomutil2.getWidth();
        zoomutil3 = this.quitZoom;
        if (this.quitZoom == null) {
            Intrinsics.throwNpe();
        }

        if (RenderUtils.isHovered(f, f1, f2, zoomutil3.getHeight(), mouseX, mouseY)) {
            Minecraft.getMinecraft().shutdown();
        }

    }

    public GuiMainMenu(int pass) {
        this.backgroundShader = new MenuShader(pass);
        this.singleplayerButton = new ResourceLocation("novo/gui/mainmenu/singleplayer.png");
        this.multiplayerButton = new ResourceLocation("novo/gui/mainmenu/multiplayer.png");
        this.altsButton = new ResourceLocation("novo/gui/mainmenu/alts.png");
        this.settingsButton = new ResourceLocation("novo/gui/mainmenu/settings.png");
        this.quitButton = new ResourceLocation("novo/gui/mainmenu/quit.png");
    }
}
