package me.Skid.ui.Client;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.ranges.RangesKt;
import me.Skid.utils.DrawUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.features.special.ClientRichPresence;
import net.ccbluex.liquidbounce.injection.backend.ClassProviderImpl;
import net.ccbluex.liquidbounce.ui.client.GuiBackground;
import net.ccbluex.liquidbounce.ui.client.GuiScripts;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\t\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\f\n\u0002\b\r\u0018\u0000 G2\u00020\u00012\u00020\u0002:\u0004GHIJB\u0005¢\u0006\u0002\u0010\u0003J \u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002032\u0006\u00105\u001a\u00020\u000bH\u0016J\b\u00106\u001a\u000201H\u0016J6\u00107\u001a\u00020\u00052\u0006\u00108\u001a\u00020\u000b2\u0006\u00109\u001a\u00020\u000b2\u0006\u0010:\u001a\u00020\u000b2\u0006\u0010;\u001a\u00020\u000b2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000203J\u0018\u0010<\u001a\u0002012\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u000203H\u0014J \u0010@\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002032\u0006\u0010A\u001a\u000203H\u0014J\u001e\u0010B\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002032\u0006\u0010C\u001a\u00020\u000bJ\u001e\u0010D\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002032\u0006\u00105\u001a\u00020\u000bJ\u0006\u0010E\u001a\u000201J\u0006\u0010F\u001a\u000201R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0007\"\u0004\b\u0013\u0010\tR\u001a\u0010\u0014\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0007\"\u0004\b\u0016\u0010\tR\u001a\u0010\u0017\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0019\"\u0004\b$\u0010\u001bR\u0011\u0010%\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0010R\u001a\u0010\'\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0019\"\u0004\b)\u0010\u001bR\u001a\u0010*\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0019\"\u0004\b,\u0010\u001bR\u001a\u0010-\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0019\"\u0004\b/\u0010\u001b¨\u0006K"},
    d2 = { "Lme/Skid/ui/Client/LBplusMainMenu;", "Lnet/minecraft/client/gui/GuiScreen;", "Lnet/minecraft/client/gui/GuiYesNoCallback;", "()V", "alrUpdate", "", "getAlrUpdate", "()Z", "setAlrUpdate", "(Z)V", "animatedMouseX", "", "animatedMouseY", "darkIcon", "Lnet/minecraft/util/ResourceLocation;", "getDarkIcon", "()Lnet/minecraft/util/ResourceLocation;", "extendedBackgroundMode", "getExtendedBackgroundMode", "setExtendedBackgroundMode", "extendedModMode", "getExtendedModMode", "setExtendedModMode", "fade", "getFade", "()F", "setFade", "(F)V", "lastAnimTick", "", "getLastAnimTick", "()J", "setLastAnimTick", "(J)V", "lastXPos", "getLastXPos", "setLastXPos", "lightIcon", "getLightIcon", "slideX", "getSlideX", "setSlideX", "sliderDarkX", "getSliderDarkX", "setSliderDarkX", "sliderX", "getSliderX", "setSliderX", "drawScreen", "", "mouseX", "", "mouseY", "partialTicks", "initGui", "isMouseHover", "x", "y", "x2", "y2", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "moveMouseEffect", "strength", "renderBar", "renderDarkModeButton", "renderSwitchButton", "Companion", "ExtendedBackgroundButton", "ExtendedImageButton", "ImageButton", "LiquidBounce"}
)
public final class LBplusMainMenu extends GuiScreen implements GuiYesNoCallback {

    @NotNull
    private final ResourceLocation darkIcon = new ResourceLocation("liquidbounce+/menu/dark.png");
    @NotNull
    private final ResourceLocation lightIcon = new ResourceLocation("liquidbounce+/menu/light.png");
    private float slideX;
    private float fade;
    private float sliderX;
    private float sliderDarkX;
    private long lastAnimTick;
    private boolean alrUpdate;
    private float lastXPos;
    private float animatedMouseX;
    private float animatedMouseY;
    private boolean extendedModMode;
    private boolean extendedBackgroundMode;
    private static boolean useParallax = true;
    public static final LBplusMainMenu.Companion Companion = new LBplusMainMenu.Companion((DefaultConstructorMarker) null);

    @NotNull
    public final ResourceLocation getDarkIcon() {
        return this.darkIcon;
    }

    @NotNull
    public final ResourceLocation getLightIcon() {
        return this.lightIcon;
    }

    public final float getSlideX() {
        return this.slideX;
    }

    public final void setSlideX(float <set-?>) {
        this.slideX = <set-?>;
    }

    public final float getFade() {
        return this.fade;
    }

    public final void setFade(float <set-?>) {
        this.fade = <set-?>;
    }

    public final float getSliderX() {
        return this.sliderX;
    }

    public final void setSliderX(float <set-?>) {
        this.sliderX = <set-?>;
    }

    public final float getSliderDarkX() {
        return this.sliderDarkX;
    }

    public final void setSliderDarkX(float <set-?>) {
        this.sliderDarkX = <set-?>;
    }

    public final long getLastAnimTick() {
        return this.lastAnimTick;
    }

    public final void setLastAnimTick(long <set-?>) {
        this.lastAnimTick = <set-?>;
    }

    public final boolean getAlrUpdate() {
        return this.alrUpdate;
    }

    public final void setAlrUpdate(boolean <set-?>) {
        this.alrUpdate = <set-?>;
    }

    public final float getLastXPos() {
        return this.lastXPos;
    }

    public final void setLastXPos(float <set-?>) {
        this.lastXPos = <set-?>;
    }

    public final boolean getExtendedModMode() {
        return this.extendedModMode;
    }

    public final void setExtendedModMode(boolean <set-?>) {
        this.extendedModMode = <set-?>;
    }

    public final boolean getExtendedBackgroundMode() {
        return this.extendedBackgroundMode;
    }

    public final void setExtendedBackgroundMode(boolean <set-?>) {
        this.extendedBackgroundMode = <set-?>;
    }

    public void initGui() {
        this.animatedMouseX = 0.0F;
        this.animatedMouseY = 0.0F;
        this.slideX = 0.0F;
        this.fade = 0.0F;
        this.sliderX = 0.0F;
        this.sliderDarkX = 0.0F;
        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = this.mc;

        Intrinsics.checkExpressionValueIsNotNull(this.mc, "mc");
        minecraft.getTextureManager().bindTexture(new ResourceLocation("langya/jellomenu.png"));
        DrawUtils.drawModalRectWithCustomSizedTexture(-this.animatedMouseX / 1.43F, -this.animatedMouseY / 10.15F, 0.0F, 0.0F, (double) this.width * 1.7D, (double) this.height * 1.1D, (double) this.width * 1.7D, (double) this.height * 1.1D);
        if (!this.alrUpdate) {
            this.lastAnimTick = System.currentTimeMillis();
            this.alrUpdate = true;
        }

        String creditInfo = "Copyright Mojang AB. Do not distribute!";

        GL11.glPushMatrix();
        this.renderSwitchButton();
        this.renderDarkModeButton();
        Fonts.font40.drawStringWithShadow("TGSense build 230116 | Liquidbounce.net", 2, this.height - 12, -1);
        Fonts.font40.drawStringWithShadow(creditInfo, this.width - 3 - Fonts.font40.getStringWidth(creditInfo), this.height - 12, -1);
        if (LBplusMainMenu.useParallax) {
            this.moveMouseEffect(mouseX, mouseY, 10.0F);
        }

        GlStateManager.disableAlpha();
        GlStateManager.enableAlpha();
        this.renderBar(mouseX, mouseY, partialTicks);
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!LiquidBounce.INSTANCE.getMainMenuPrep()) {
            float animProgress = RangesKt.coerceIn((float) (System.currentTimeMillis() - this.lastAnimTick) / 1500.0F, 0.0F, 1.0F);

            RenderUtils.drawRect(0.0F, 0.0F, (float) this.width, (float) this.height, new Color(0.0F, 0.0F, 0.0F, 1.0F - animProgress));
            if (animProgress >= 1.0F) {
                LiquidBounce.INSTANCE.setMainMenuPrep(true);
            }
        }

        this.animatedMouseX = (float) mouseX;
        this.animatedMouseY = (float) mouseY;
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (LiquidBounce.INSTANCE.getMainMenuPrep() && mouseButton == 0) {
            if (this.isMouseHover(2.0F, (float) this.height - 26.0F, 28.0F, (float) this.height - 16.0F, mouseX, mouseY)) {
                LBplusMainMenu.useParallax = !LBplusMainMenu.useParallax;
            }

            if (this.isMouseHover(2.0F, (float) this.height - 38.0F, 28.0F, (float) this.height - 28.0F, mouseX, mouseY)) {
                LiquidBounce.INSTANCE.setDarkMode(!LiquidBounce.INSTANCE.getDarkMode());
            }

            float staticX = (float) this.width / 2.0F - 120.0F;
            float staticY = (float) this.height / 2.0F + 20.0F;
            int index = 0;
            Enum[] aenum = this.extendedModMode ? (Enum[]) LBplusMainMenu.ExtendedImageButton.values() : (Enum[]) LBplusMainMenu.ImageButton.values();
            int i = aenum.length;

            for (int j = 0; j < i; ++j) {
                Enum oenum = aenum[j];

                if (this.isMouseHover(staticX + 40.0F * (float) index, staticY, staticX + 40.0F * (float) (index + 1), staticY + 20.0F, mouseX, mouseY)) {
                    switch (index) {
                    case 0:
                        if (this.extendedBackgroundMode) {
                            this.extendedBackgroundMode = false;
                        } else if (this.extendedModMode) {
                            this.extendedModMode = false;
                        } else {
                            this.mc.displayGuiScreen((GuiScreen) (new GuiWorldSelection((GuiScreen) this)));
                        }
                        break;

                    case 1:
                        if (this.extendedBackgroundMode) {
                            GuiBackground.Companion.setEnabled(!GuiBackground.Companion.getEnabled());
                        } else if (this.extendedModMode) {
                            this.mc.displayGuiScreen((GuiScreen) (new GuiModList((GuiScreen) this)));
                        } else {
                            this.mc.displayGuiScreen((GuiScreen) (new GuiMultiplayer((GuiScreen) this)));
                        }
                        break;

                    case 2:
                        if (this.extendedBackgroundMode) {
                            GuiBackground.Companion.setParticles(!GuiBackground.Companion.getParticles());
                        } else if (this.extendedModMode) {
                            MinecraftInstance.mc.displayGuiScreen(ClassProviderImpl.INSTANCE.wrapGuiScreen((WrappedGuiScreen) (new GuiScripts())));
                        } else {
                            MinecraftInstance.mc.displayGuiScreen(ClassProviderImpl.INSTANCE.wrapGuiScreen((WrappedGuiScreen) (new GuiAltManager())));
                        }
                        break;

                    case 3:
                        if (this.extendedBackgroundMode) {
                            File file = MiscUtils.openFileChooser();

                            if (file == null) {
                                return;
                            }

                            File rpc = file;

                            if (rpc.isDirectory()) {
                                return;
                            }

                            try {
                                Files.copy(rpc.toPath(), (OutputStream) (new FileOutputStream(LiquidBounce.INSTANCE.getFileManager().backgroundFile)));
                                BufferedImage state = ImageIO.read((InputStream) (new FileInputStream(LiquidBounce.INSTANCE.getFileManager().backgroundFile)));

                                LiquidBounce.INSTANCE.setBackground2(new ResourceLocation("langya/jellomenu.png"));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                MiscUtils.showErrorPopup("Error", "Exception class: " + exception.getClass().getName() + "\nMessage: " + exception.getMessage());
                                LiquidBounce.INSTANCE.getFileManager().backgroundFile.delete();
                            }
                        } else if (this.extendedModMode) {
                            final ClientRichPresence clientrichpresence = LiquidBounce.INSTANCE.getClientRichPresence();
                            boolean flag = !clientrichpresence.getShowRichPresenceValue();
                            boolean flag1;

                            if (!flag) {
                                clientrichpresence.shutdown();
                                flag1 = false;
                            } else {
                                if (!flag) {
                                    throw new NoWhenBranchMatchedException();
                                }

                                final BooleanRef value = new BooleanRef();

                                value.element = true;
                                ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) (new Function0(0) {
                                    public final void invoke() {
                                        BooleanRef booleanref = value;

                                        boolean flag;
                                        BooleanRef booleanref1;

                                        try {
                                            booleanref1 = booleanref;
                                            clientrichpresence.setup();
                                            flag = true;
                                        } catch (Throwable throwable) {
                                            booleanref1 = booleanref;
                                            ClientUtils.getLogger().error("Failed to setup Discord RPC.", throwable);
                                            flag = false;
                                        }

                                        booleanref1.element = flag;
                                    }
                                }), 31, (Object) null);
                                flag1 = value.element;
                            }

                            clientrichpresence.setShowRichPresenceValue(flag1);
                        } else {
                            this.mc.displayGuiScreen((GuiScreen) (new GuiOptions((GuiScreen) this, this.mc.gameSettings)));
                        }
                        break;

                    case 4:
                        if (this.extendedBackgroundMode) {
                            LiquidBounce.INSTANCE.setBackground((IResourceLocation) null);
                            LiquidBounce.INSTANCE.getFileManager().backgroundFile.delete();
                        } else if (this.extendedModMode) {
                            this.extendedBackgroundMode = true;
                        } else {
                            this.extendedModMode = true;
                        }
                        break;

                    case 5:
                        this.mc.shutdown();
                    }
                }

                ++index;
            }

            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public final void moveMouseEffect(int mouseX, int mouseY, float strength) {
        int mX = mouseX - this.width / 2;
        int mY = mouseY - this.height / 2;
        float xDelta = (float) mX / (float) (this.width / 2);
        float yDelta = (float) mY / (float) (this.height / 2);

        GL11.glTranslatef(xDelta * strength, yDelta * strength, 0.0F);
    }

    public final void renderSwitchButton() {
        this.sliderX = RangesKt.coerceIn(this.sliderX + (LBplusMainMenu.useParallax ? 2.0F : -2.0F), 0.0F, 12.0F);
        Fonts.font40.drawStringWithShadow("Ani", 28, this.height - 25, -1);
        RenderUtils.drawRoundedRect(4.0F, (float) this.height - 24.0F, 22.0F, (float) this.height - 18.0F, 3, LBplusMainMenu.useParallax ? (new Color(0, 111, 255, 255)).getRGB() : (LiquidBounce.INSTANCE.getDarkMode() ? new Color(70, 70, 70, 255) : new Color(140, 140, 140, 255)).getRGB());
        float f = 2.0F + this.sliderX;
        float f1 = (float) this.height - 26.0F;
        float f2 = 12.0F + this.sliderX;
        float f3 = (float) this.height - 16.0F;
        Color color = Color.white;

        Intrinsics.checkExpressionValueIsNotNull(Color.white, "Color.white");
        RenderUtils.drawRoundedRect(f, f1, f2, f3, 5, color.getRGB());
    }

    public final void renderDarkModeButton() {
        this.sliderDarkX = RangesKt.coerceIn(this.sliderDarkX + (LiquidBounce.INSTANCE.getDarkMode() ? 2.0F : -2.0F), 0.0F, 12.0F);
        GlStateManager.disableAlpha();
        RenderUtils.drawImage3(this.darkIcon, 28.0F, (float) this.height - 40.0F, 14, 14, 1.0F, 1.0F, 1.0F, this.sliderDarkX / 12.0F);
        RenderUtils.drawImage3(this.lightIcon, 28.0F, (float) this.height - 40.0F, 14, 14, 1.0F, 1.0F, 1.0F, 1.0F - this.sliderDarkX / 12.0F);
        GlStateManager.enableAlpha();
        RenderUtils.drawRoundedRect(4.0F, (float) this.height - 36.0F, 22.0F, (float) this.height - 30.0F, 3, (LiquidBounce.INSTANCE.getDarkMode() ? new Color(70, 70, 70, 255) : new Color(140, 140, 140, 255)).getRGB());
        float f = 2.0F + this.sliderDarkX;
        float f1 = (float) this.height - 38.0F;
        float f2 = 12.0F + this.sliderDarkX;
        float f3 = (float) this.height - 28.0F;
        Color color = Color.white;

        Intrinsics.checkExpressionValueIsNotNull(Color.white, "Color.white");
        RenderUtils.drawRoundedRect(f, f1, f2, f3, 5, color.getRGB());
    }

    public final void renderBar(int mouseX, int mouseY, float partialTicks) {
        float staticX = (float) this.width / 2.0F - 120.0F;
        float staticY = (float) this.height / 2.0F + 20.0F;

        RenderUtils.drawRoundedRect(staticX, staticY, staticX + 240.0F, staticY + 20.0F, 10, (LiquidBounce.INSTANCE.getDarkMode() ? new Color(0, 0, 0, 100) : new Color(255, 255, 255, 100)).getRGB());
        int index = 0;
        boolean shouldAnimate = false;
        String displayString = (String) null;
        float moveX = 0.0F;
        LBplusMainMenu.ExtendedBackgroundButton i;
        int i;
        LBplusMainMenu.ExtendedBackgroundButton[] albplusmainmenu_extendedbackgroundbutton;
        int j;
        LBplusMainMenu.ExtendedImageButton lbplusmainmenu_extendedimagebutton;
        LBplusMainMenu.ImageButton lbplusmainmenu_imagebutton;
        LBplusMainMenu.ExtendedImageButton[] albplusmainmenu_extendedimagebutton;
        LBplusMainMenu.ImageButton[] albplusmainmenu_imagebutton;

        if (this.extendedModMode) {
            if (this.extendedBackgroundMode) {
                albplusmainmenu_extendedbackgroundbutton = LBplusMainMenu.ExtendedBackgroundButton.values();
                j = albplusmainmenu_extendedbackgroundbutton.length;

                for (i = 0; i < j; ++i) {
                    i = albplusmainmenu_extendedbackgroundbutton[i];
                    if (this.isMouseHover(staticX + 40.0F * (float) index, staticY, staticX + 40.0F * (float) (index + 1), staticY + 20.0F, mouseX, mouseY)) {
                        shouldAnimate = true;
                        displayString = i == LBplusMainMenu.ExtendedBackgroundButton.Enabled ? "Custom background: " + (GuiBackground.Companion.getEnabled() ? "§aON" : "§cOFF") : (i == LBplusMainMenu.ExtendedBackgroundButton.Particles ? i.getButtonName() + ": " + (GuiBackground.Companion.getParticles() ? "§aON" : "§cOFF") : i.getButtonName());
                        moveX = staticX + 40.0F * (float) index;
                    }

                    ++index;
                }
            } else {
                albplusmainmenu_extendedimagebutton = LBplusMainMenu.ExtendedImageButton.values();
                j = albplusmainmenu_extendedimagebutton.length;

                for (i = 0; i < j; ++i) {
                    lbplusmainmenu_extendedimagebutton = albplusmainmenu_extendedimagebutton[i];
                    if (this.isMouseHover(staticX + 40.0F * (float) index, staticY, staticX + 40.0F * (float) (index + 1), staticY + 20.0F, mouseX, mouseY)) {
                        shouldAnimate = true;
                        displayString = lbplusmainmenu_extendedimagebutton == LBplusMainMenu.ExtendedImageButton.DiscordRPC ? lbplusmainmenu_extendedimagebutton.getButtonName() + ": " + (LiquidBounce.INSTANCE.getClientRichPresence().getShowRichPresenceValue() ? "§aON" : "§cOFF") : lbplusmainmenu_extendedimagebutton.getButtonName();
                        moveX = staticX + 40.0F * (float) index;
                    }

                    ++index;
                }
            }
        } else {
            albplusmainmenu_imagebutton = LBplusMainMenu.ImageButton.values();
            j = albplusmainmenu_imagebutton.length;

            for (i = 0; i < j; ++i) {
                lbplusmainmenu_imagebutton = albplusmainmenu_imagebutton[i];
                if (this.isMouseHover(staticX + 40.0F * (float) index, staticY, staticX + 40.0F * (float) (index + 1), staticY + 20.0F, mouseX, mouseY)) {
                    shouldAnimate = true;
                    displayString = lbplusmainmenu_imagebutton.getButtonName();
                    moveX = staticX + 40.0F * (float) index;
                }

                ++index;
            }
        }

        if (displayString != null) {
            Fonts.font35.drawCenteredString(displayString, (float) this.width / 2.0F, staticY + 30.0F, -1);
        } else {
            Fonts.font35.drawCenteredString("Welcome TGSense!", (float) this.width / 2.0F, staticY + 30.0F, -1);
        }

        if (shouldAnimate) {
            if (this.fade == 0.0F) {
                this.slideX = moveX;
            } else {
                this.slideX = AnimationUtils.animate(moveX, this.slideX, 0.5F * (1.0F - partialTicks));
            }

            this.lastXPos = moveX;
            this.fade += 10.0F;
            if (this.fade >= 100.0F) {
                this.fade = 100.0F;
            }
        } else {
            this.fade -= 10.0F;
            if (this.fade <= 0.0F) {
                this.fade = 0.0F;
            }

            this.slideX = AnimationUtils.animate(this.lastXPos, this.slideX, 0.5F * (1.0F - partialTicks));
        }

        if (this.fade != 0.0F) {
            RenderUtils.drawRoundedRect(this.slideX, staticY, this.slideX + 40.0F, staticY + 20.0F, 10, (LiquidBounce.INSTANCE.getDarkMode() ? new Color(0.0F, 0.0F, 0.0F, this.fade / 100.0F * 0.6F) : new Color(1.0F, 1.0F, 1.0F, this.fade / 100.0F * 0.6F)).getRGB());
        }

        index = 0;
        GlStateManager.disableAlpha();
        if (this.extendedModMode) {
            if (this.extendedBackgroundMode) {
                albplusmainmenu_extendedbackgroundbutton = LBplusMainMenu.ExtendedBackgroundButton.values();
                j = albplusmainmenu_extendedbackgroundbutton.length;

                for (i = 0; i < j; ++i) {
                    i = albplusmainmenu_extendedbackgroundbutton[i];
                    if (LiquidBounce.INSTANCE.getDarkMode()) {
                        RenderUtils.drawImage2(i.getTexture(), staticX + 40.0F * (float) index + 11.0F, staticY + 1.0F, 18, 18);
                    } else {
                        RenderUtils.drawImage3(i.getTexture(), staticX + 40.0F * (float) index + 11.0F, staticY + 1.0F, 18, 18, 0.0F, 0.0F, 0.0F, 1.0F);
                    }

                    ++index;
                }
            } else {
                albplusmainmenu_extendedimagebutton = LBplusMainMenu.ExtendedImageButton.values();
                j = albplusmainmenu_extendedimagebutton.length;

                for (i = 0; i < j; ++i) {
                    lbplusmainmenu_extendedimagebutton = albplusmainmenu_extendedimagebutton[i];
                    if (LiquidBounce.INSTANCE.getDarkMode()) {
                        RenderUtils.drawImage2(lbplusmainmenu_extendedimagebutton.getTexture(), staticX + 40.0F * (float) index + 11.0F, staticY + 1.0F, 18, 18);
                    } else {
                        RenderUtils.drawImage3(lbplusmainmenu_extendedimagebutton.getTexture(), staticX + 40.0F * (float) index + 11.0F, staticY + 1.0F, 18, 18, 0.0F, 0.0F, 0.0F, 1.0F);
                    }

                    ++index;
                }
            }
        } else {
            albplusmainmenu_imagebutton = LBplusMainMenu.ImageButton.values();
            j = albplusmainmenu_imagebutton.length;

            for (i = 0; i < j; ++i) {
                lbplusmainmenu_imagebutton = albplusmainmenu_imagebutton[i];
                if (LiquidBounce.INSTANCE.getDarkMode()) {
                    RenderUtils.drawImage2(lbplusmainmenu_imagebutton.getTexture(), staticX + 40.0F * (float) index + 11.0F, staticY + 1.0F, 18, 18);
                } else {
                    RenderUtils.drawImage3(lbplusmainmenu_imagebutton.getTexture(), staticX + 40.0F * (float) index + 11.0F, staticY + 1.0F, 18, 18, 0.0F, 0.0F, 0.0F, 1.0F);
                }

                ++index;
            }
        }

        GlStateManager.enableAlpha();
    }

    public final boolean isMouseHover(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float) mouseX >= x && (float) mouseX < x2 && (float) mouseY >= y && (float) mouseY < y2;
    }

    protected void keyTyped(char typedChar, int keyCode) {}

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"},
        d2 = { "Lme/Skid/ui/Client/LBplusMainMenu$ImageButton;", "", "buttonName", "", "texture", "Lnet/minecraft/util/ResourceLocation;", "(Ljava/lang/String;ILjava/lang/String;Lnet/minecraft/util/ResourceLocation;)V", "getButtonName", "()Ljava/lang/String;", "getTexture", "()Lnet/minecraft/util/ResourceLocation;", "Single", "Multi", "Alts", "Settings", "Mods", "Exit", "LiquidBounce"}
    )
    public static enum ImageButton {

        Single, Multi, Alts, Settings, Mods, Exit;

        @NotNull
        private final String buttonName;
        @NotNull
        private final ResourceLocation texture;

        @NotNull
        public final String getButtonName() {
            return this.buttonName;
        }

        @NotNull
        public final ResourceLocation getTexture() {
            return this.texture;
        }

        private ImageButton(String buttonName, ResourceLocation texture) {
            this.buttonName = buttonName;
            this.texture = texture;
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"},
        d2 = { "Lme/Skid/ui/Client/LBplusMainMenu$ExtendedImageButton;", "", "buttonName", "", "texture", "Lnet/minecraft/util/ResourceLocation;", "(Ljava/lang/String;ILjava/lang/String;Lnet/minecraft/util/ResourceLocation;)V", "getButtonName", "()Ljava/lang/String;", "getTexture", "()Lnet/minecraft/util/ResourceLocation;", "Back", "Mods", "Scripts", "DiscordRPC", "Background", "Exit", "LiquidBounce"}
    )
    public static enum ExtendedImageButton {

        Back, Mods, Scripts, DiscordRPC, Background, Exit;

        @NotNull
        private final String buttonName;
        @NotNull
        private final ResourceLocation texture;

        @NotNull
        public final String getButtonName() {
            return this.buttonName;
        }

        @NotNull
        public final ResourceLocation getTexture() {
            return this.texture;
        }

        private ExtendedImageButton(String buttonName, ResourceLocation texture) {
            this.buttonName = buttonName;
            this.texture = texture;
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"},
        d2 = { "Lme/Skid/ui/Client/LBplusMainMenu$ExtendedBackgroundButton;", "", "buttonName", "", "texture", "Lnet/minecraft/util/ResourceLocation;", "(Ljava/lang/String;ILjava/lang/String;Lnet/minecraft/util/ResourceLocation;)V", "getButtonName", "()Ljava/lang/String;", "getTexture", "()Lnet/minecraft/util/ResourceLocation;", "Back", "Enabled", "Particles", "Change", "Reset", "Exit", "LiquidBounce"}
    )
    public static enum ExtendedBackgroundButton {

        Back, Enabled, Particles, Change, Reset, Exit;

        @NotNull
        private final String buttonName;
        @NotNull
        private final ResourceLocation texture;

        @NotNull
        public final String getButtonName() {
            return this.buttonName;
        }

        @NotNull
        public final ResourceLocation getTexture() {
            return this.texture;
        }

        private ExtendedBackgroundButton(String buttonName, ResourceLocation texture) {
            this.buttonName = buttonName;
            this.texture = texture;
        }
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"},
        d2 = { "Lme/Skid/ui/Client/LBplusMainMenu$Companion;", "", "()V", "useParallax", "", "getUseParallax", "()Z", "setUseParallax", "(Z)V", "LiquidBounce"}
    )
    public static final class Companion {

        public final boolean getUseParallax() {
            return LBplusMainMenu.useParallax;
        }

        public final void setUseParallax(boolean <set-?>) {
            LBplusMainMenu.useParallax = <set-?>;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
