package net.ccbluex.liquidbounce.injection.backend;

import java.io.File;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.audio.ISoundHandler;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.ITextureManager;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.IRenderGlobal;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings;
import net.ccbluex.liquidbounce.api.minecraft.client.shader.IFramebuffer;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.ISession;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.math.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010p\u001a\u00020q2\b\u0010r\u001a\u0004\u0018\u00010\u0006H\u0016J\u0013\u0010s\u001a\u00020*2\b\u0010t\u001a\u0004\u0018\u00010uH\u0096\u0002J\b\u0010v\u001a\u00020qH\u0016J\b\u0010w\u001a\u00020qH\u0016J\b\u0010x\u001a\u00020qH\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0014R\u0014\u0010\u0017\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0014R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\'\u0010(R\u0014\u0010)\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010+R\u0014\u0010,\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010+R\u0014\u0010-\u001a\u00020.8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u0014\u00101\u001a\u0002028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u0016\u00105\u001a\u0004\u0018\u0001068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0014\u00109\u001a\u00020:8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b;\u0010<R\u0014\u0010=\u001a\u00020>8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b?\u0010@R\u0014\u0010A\u001a\u00020B8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bC\u0010DR\u0014\u0010E\u001a\u00020F8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bG\u0010HR(\u0010K\u001a\u0004\u0018\u00010J2\b\u0010I\u001a\u0004\u0018\u00010J8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bL\u0010M\"\u0004\bN\u0010OR$\u0010P\u001a\u00020\u00122\u0006\u0010I\u001a\u00020\u00128V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bQ\u0010\u0014\"\u0004\bR\u0010SR$\u0010U\u001a\u00020T2\u0006\u0010I\u001a\u00020T8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u0014\u0010Z\u001a\u00020[8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\\\u0010]R\u0014\u0010^\u001a\u00020_8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b`\u0010aR\u0016\u0010b\u001a\u0004\u0018\u00010c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bd\u0010eR\u0016\u0010f\u001a\u0004\u0018\u00010g8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bh\u0010iR\u0014\u0010j\u001a\u00020k8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bl\u0010mR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\bn\u0010o¨\u0006y"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/MinecraftImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "wrapped", "Lnet/minecraft/client/Minecraft;", "(Lnet/minecraft/client/Minecraft;)V", "currentScreen", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "getCurrentScreen", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "currentServerData", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IServerData;", "getCurrentServerData", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IServerData;", "dataDir", "Ljava/io/File;", "getDataDir", "()Ljava/io/File;", "debugFPS", "", "getDebugFPS", "()I", "displayHeight", "getDisplayHeight", "displayWidth", "getDisplayWidth", "entityRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IEntityRenderer;", "getEntityRenderer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IEntityRenderer;", "fontRendererObj", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "getFontRendererObj", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "framebuffer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IFramebuffer;", "getFramebuffer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IFramebuffer;", "gameSettings", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "getGameSettings", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "isFullScreen", "", "()Z", "isIntegratedServerRunning", "netHandler", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "getNetHandler", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "netHandler2", "Lnet/minecraft/network/play/INetHandlerPlayClient;", "getNetHandler2", "()Lnet/minecraft/network/play/INetHandlerPlayClient;", "objectMouseOver", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "getObjectMouseOver", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "playerController", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IPlayerControllerMP;", "getPlayerController", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IPlayerControllerMP;", "renderGlobal", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IRenderGlobal;", "getRenderGlobal", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IRenderGlobal;", "renderItem", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/entity/IRenderItem;", "getRenderItem", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/entity/IRenderItem;", "renderManager", "Lnet/ccbluex/liquidbounce/api/minecraft/renderer/entity/IRenderManager;", "getRenderManager", "()Lnet/ccbluex/liquidbounce/api/minecraft/renderer/entity/IRenderManager;", "value", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "renderViewEntity", "getRenderViewEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "setRenderViewEntity", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;)V", "rightClickDelayTimer", "getRightClickDelayTimer", "setRightClickDelayTimer", "(I)V", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;", "session", "getSession", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;", "setSession", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;)V", "soundHandler", "Lnet/ccbluex/liquidbounce/api/minecraft/client/audio/ISoundHandler;", "getSoundHandler", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/audio/ISoundHandler;", "textureManager", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/ITextureManager;", "getTextureManager", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/ITextureManager;", "thePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "getThePlayer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "theWorld", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "getTheWorld", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "timer", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ITimer;", "getTimer", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/ITimer;", "getWrapped", "()Lnet/minecraft/client/Minecraft;", "displayGuiScreen", "", "screen", "equals", "other", "", "rightClickMouse", "shutdown", "toggleFullscreen", "LiquidBounce"}
)
public final class MinecraftImpl implements IMinecraft {

    @NotNull
    private final Minecraft wrapped;

    @NotNull
    public IFramebuffer getFramebuffer() {
        Framebuffer framebuffer = this.wrapped.getFramebuffer();

        Intrinsics.checkExpressionValueIsNotNull(framebuffer, "wrapped.framebuffer");
        Framebuffer $this$wrap$iv = framebuffer;
        boolean $i$f$wrap = false;

        return (IFramebuffer) (new FramebufferImpl($this$wrap$iv));
    }

    public boolean isFullScreen() {
        return this.wrapped.isFullScreen();
    }

    @NotNull
    public File getDataDir() {
        File file = this.wrapped.gameDir;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.gameDir, "wrapped.mcDataDir");
        return file;
    }

    public int getDebugFPS() {
        return Minecraft.getDebugFPS();
    }

    @NotNull
    public IRenderGlobal getRenderGlobal() {
        RenderGlobal renderglobal = this.wrapped.renderGlobal;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.renderGlobal, "wrapped.renderGlobal");
        RenderGlobal $this$wrap$iv = renderglobal;
        boolean $i$f$wrap = false;

        return (IRenderGlobal) (new RenderGlobalImpl($this$wrap$iv));
    }

    @NotNull
    public IRenderItem getRenderItem() {
        RenderItem renderitem = this.wrapped.getRenderItem();

        Intrinsics.checkExpressionValueIsNotNull(renderitem, "wrapped.renderItem");
        RenderItem $this$wrap$iv = renderitem;
        boolean $i$f$wrap = false;

        return (IRenderItem) (new RenderItemImpl($this$wrap$iv));
    }

    public int getDisplayWidth() {
        return this.wrapped.displayWidth;
    }

    public int getDisplayHeight() {
        return this.wrapped.displayHeight;
    }

    @NotNull
    public IEntityRenderer getEntityRenderer() {
        EntityRenderer entityrenderer = this.wrapped.entityRenderer;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.entityRenderer, "wrapped.entityRenderer");
        EntityRenderer $this$wrap$iv = entityrenderer;
        boolean $i$f$wrap = false;

        return (IEntityRenderer) (new EntityRendererImpl($this$wrap$iv));
    }

    public int getRightClickDelayTimer() {
        return this.wrapped.rightClickDelayTimer;
    }

    public void setRightClickDelayTimer(int value) {
        this.wrapped.rightClickDelayTimer = value;
    }

    @NotNull
    public ISession getSession() {
        Session session = this.wrapped.session;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.session, "wrapped.session");
        Session $this$wrap$iv = session;
        boolean $i$f$wrap = false;

        return (ISession) (new SessionImpl($this$wrap$iv));
    }

    public void setSession(@NotNull ISession value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        Minecraft minecraft = this.wrapped;
        boolean $i$f$unwrap = false;
        Session session = ((SessionImpl) value).getWrapped();

        minecraft.session = session;
    }

    @NotNull
    public ISoundHandler getSoundHandler() {
        SoundHandler soundhandler = this.wrapped.getSoundHandler();

        Intrinsics.checkExpressionValueIsNotNull(soundhandler, "wrapped.soundHandler");
        SoundHandler $this$wrap$iv = soundhandler;
        boolean $i$f$wrap = false;

        return (ISoundHandler) (new SoundHandlerImpl($this$wrap$iv));
    }

    @Nullable
    public IMovingObjectPosition getObjectMouseOver() {
        RayTraceResult raytraceresult = this.wrapped.objectMouseOver;
        IMovingObjectPosition imovingobjectposition;

        if (this.wrapped.objectMouseOver != null) {
            RayTraceResult $this$wrap$iv = raytraceresult;
            boolean $i$f$wrap = false;

            imovingobjectposition = (IMovingObjectPosition) (new MovingObjectPositionImpl($this$wrap$iv));
        } else {
            imovingobjectposition = null;
        }

        return imovingobjectposition;
    }

    @NotNull
    public ITimer getTimer() {
        Timer timer = this.wrapped.timer;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.timer, "wrapped.timer");
        Timer $this$wrap$iv = timer;
        boolean $i$f$wrap = false;

        return (ITimer) (new TimerImpl($this$wrap$iv));
    }

    @NotNull
    public IRenderManager getRenderManager() {
        RenderManager rendermanager = this.wrapped.getRenderManager();

        Intrinsics.checkExpressionValueIsNotNull(rendermanager, "wrapped.renderManager");
        RenderManager $this$wrap$iv = rendermanager;
        boolean $i$f$wrap = false;

        return (IRenderManager) (new RenderManagerImpl($this$wrap$iv));
    }

    @NotNull
    public IPlayerControllerMP getPlayerController() {
        PlayerControllerMP playercontrollermp = this.wrapped.playerController;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.playerController, "wrapped.playerController");
        PlayerControllerMP $this$wrap$iv = playercontrollermp;
        boolean $i$f$wrap = false;

        return (IPlayerControllerMP) (new PlayerControllerMPImpl($this$wrap$iv));
    }

    @Nullable
    public IGuiScreen getCurrentScreen() {
        GuiScreen guiscreen = this.wrapped.currentScreen;
        IGuiScreen iguiscreen;

        if (this.wrapped.currentScreen != null) {
            GuiScreen $this$wrap$iv = guiscreen;
            boolean $i$f$wrap = false;

            iguiscreen = (IGuiScreen) (new GuiScreenImpl($this$wrap$iv));
        } else {
            iguiscreen = null;
        }

        return iguiscreen;
    }

    @Nullable
    public IEntity getRenderViewEntity() {
        Entity entity = this.wrapped.getRenderViewEntity();
        IEntity ientity;

        if (entity != null) {
            Entity $this$wrap$iv = entity;
            boolean $i$f$wrap = false;

            ientity = (IEntity) (new EntityImpl($this$wrap$iv));
        } else {
            ientity = null;
        }

        return ientity;
    }

    public void setRenderViewEntity(@Nullable IEntity value) {
        Minecraft minecraft = this.wrapped;
        Entity entity;

        if (value != null) {
            Minecraft minecraft1 = minecraft;
            boolean $i$f$unwrap = false;

            if (value == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.EntityImpl<*>");
            }

            Entity entity1 = ((EntityImpl) value).getWrapped();

            minecraft = minecraft1;
            entity = entity1;
        } else {
            entity = null;
        }

        minecraft.setRenderViewEntity(entity);
    }

    @NotNull
    public IINetHandlerPlayClient getNetHandler() {
        NetHandlerPlayClient nethandlerplayclient = this.wrapped.getConnection();

        if (nethandlerplayclient == null) {
            Intrinsics.throwNpe();
        }

        Intrinsics.checkExpressionValueIsNotNull(nethandlerplayclient, "wrapped.connection!!");
        NetHandlerPlayClient $this$wrap$iv = nethandlerplayclient;
        boolean $i$f$wrap = false;

        return (IINetHandlerPlayClient) (new INetHandlerPlayClientImpl($this$wrap$iv));
    }

    @NotNull
    public INetHandlerPlayClient getNetHandler2() {
        NetHandlerPlayClient nethandlerplayclient = this.wrapped.getConnection();

        if (nethandlerplayclient == null) {
            Intrinsics.throwNpe();
        }

        NetHandlerPlayClient nethandlerplayclient1 = nethandlerplayclient;
        boolean flag = false;
        boolean flag1 = false;
        boolean $i$a$-also-MinecraftImpl$netHandler2$1 = false;
        boolean $i$f$unwrap = false;

        if (this == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.MinecraftImpl");
        } else {
            ((MinecraftImpl) this).getWrapped();
            Intrinsics.checkExpressionValueIsNotNull(nethandlerplayclient1, "wrapped.connection!!.also { unwrap() }");
            return (INetHandlerPlayClient) nethandlerplayclient1;
        }
    }

    @Nullable
    public IWorldClient getTheWorld() {
        WorldClient worldclient = this.wrapped.world;
        IWorldClient iworldclient;

        if (this.wrapped.world != null) {
            WorldClient $this$wrap$iv = worldclient;
            boolean $i$f$wrap = false;

            iworldclient = (IWorldClient) (new WorldClientImpl($this$wrap$iv));
        } else {
            iworldclient = null;
        }

        return iworldclient;
    }

    @Nullable
    public IEntityPlayerSP getThePlayer() {
        EntityPlayerSP entityplayersp = this.wrapped.player;
        IEntityPlayerSP ientityplayersp;

        if (this.wrapped.player != null) {
            EntityPlayerSP $this$wrap$iv = entityplayersp;
            boolean $i$f$wrap = false;

            ientityplayersp = (IEntityPlayerSP) (new EntityPlayerSPImpl($this$wrap$iv));
        } else {
            ientityplayersp = null;
        }

        return ientityplayersp;
    }

    @NotNull
    public ITextureManager getTextureManager() {
        TextureManager texturemanager = this.wrapped.getTextureManager();

        Intrinsics.checkExpressionValueIsNotNull(texturemanager, "wrapped.textureManager");
        TextureManager $this$wrap$iv = texturemanager;
        boolean $i$f$wrap = false;

        return (ITextureManager) (new TextureManagerImpl($this$wrap$iv));
    }

    public boolean isIntegratedServerRunning() {
        return this.wrapped.isIntegratedServerRunning();
    }

    @Nullable
    public IServerData getCurrentServerData() {
        ServerData serverdata = this.wrapped.getCurrentServerData();
        IServerData iserverdata;

        if (serverdata != null) {
            ServerData $this$wrap$iv = serverdata;
            boolean $i$f$wrap = false;

            iserverdata = (IServerData) (new ServerDataImpl($this$wrap$iv));
        } else {
            iserverdata = null;
        }

        return iserverdata;
    }

    @NotNull
    public IGameSettings getGameSettings() {
        GameSettings gamesettings = this.wrapped.gameSettings;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.gameSettings, "wrapped.gameSettings");
        return (IGameSettings) (new GameSettingsImpl(gamesettings));
    }

    @NotNull
    public IFontRenderer getFontRendererObj() {
        FontRenderer fontrenderer = this.wrapped.fontRenderer;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.fontRenderer, "wrapped.fontRenderer");
        FontRenderer $this$wrap$iv = fontrenderer;
        boolean $i$f$wrap = false;

        return (IFontRenderer) (new FontRendererImpl($this$wrap$iv));
    }

    public void displayGuiScreen(@Nullable IGuiScreen screen) {
        Minecraft minecraft = this.wrapped;
        GuiScreen guiscreen;

        if (screen != null) {
            Minecraft minecraft1 = minecraft;
            boolean $i$f$unwrap = false;

            if (screen == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.GuiScreenImpl<*>");
            }

            GuiScreen guiscreen1 = (GuiScreen) ((GuiScreenImpl) screen).getWrapped();

            minecraft = minecraft1;
            guiscreen = guiscreen1;
        } else {
            guiscreen = null;
        }

        minecraft.displayGuiScreen(guiscreen);
    }

    public void rightClickMouse() {
        this.wrapped.rightClickMouse();
    }

    public void shutdown() {
        this.wrapped.shutdown();
    }

    public void toggleFullscreen() {
        this.wrapped.toggleFullscreen();
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof MinecraftImpl && Intrinsics.areEqual(((MinecraftImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Minecraft getWrapped() {
        return this.wrapped;
    }

    public MinecraftImpl(@NotNull Minecraft wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
