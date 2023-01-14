package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.tileentity.ITileEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b\u0010\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010#\u001a\u00020\u00062\b\u0010$\u001a\u0004\u0018\u00010%H\u0096\u0002J0\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00142\u0006\u0010+\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u00142\u0006\u0010-\u001a\u00020\fH\u0016J \u0010.\u001a\u00020\u00062\u0006\u0010(\u001a\u00020/2\u0006\u00100\u001a\u00020\f2\u0006\u00101\u001a\u00020\u0006H\u0016J8\u00102\u001a\u00020\u00062\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00142\u0006\u00106\u001a\u00020\u00142\u0006\u00107\u001a\u00020\u00142\u0006\u00108\u001a\u00020\f2\u0006\u00109\u001a\u00020\fH\u0016R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR$\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u000e\"\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016R\u0014\u0010\u0019\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0016R\u0014\u0010\u001b\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0016R\u0014\u0010\u001d\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0016R\u0014\u0010\u001f\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"¨\u0006:"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/RenderManagerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/renderer/entity/IRenderManager;", "wrapped", "Lnet/minecraft/client/renderer/entity/RenderManager;", "(Lnet/minecraft/client/renderer/entity/RenderManager;)V", "value", "", "isRenderShadow", "()Z", "setRenderShadow", "(Z)V", "playerViewX", "", "getPlayerViewX", "()F", "playerViewY", "getPlayerViewY", "setPlayerViewY", "(F)V", "renderPosX", "", "getRenderPosX", "()D", "renderPosY", "getRenderPosY", "renderPosZ", "getRenderPosZ", "viewerPosX", "getViewerPosX", "viewerPosY", "getViewerPosY", "viewerPosZ", "getViewerPosZ", "getWrapped", "()Lnet/minecraft/client/renderer/entity/RenderManager;", "equals", "other", "", "renderEntityAt", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/tileentity/ITileEntity;", "x", "y", "z", "partialTicks", "renderEntityStatic", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "renderPartialTicks", "hideDebugBox", "renderEntityWithPosYaw", "entityLivingBase", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "d", "d1", "d2", "fl", "fl1", "LiquidBounce"}
)
public final class RenderManagerImpl implements IRenderManager {

    @NotNull
    private final RenderManager wrapped;

    public boolean isRenderShadow() {
        return this.wrapped.isRenderShadow();
    }

    public void setRenderShadow(boolean value) {
        this.wrapped.setRenderShadow(value);
    }

    public double getViewerPosX() {
        return this.wrapped.viewerPosX;
    }

    public double getViewerPosY() {
        return this.wrapped.viewerPosY;
    }

    public double getViewerPosZ() {
        return this.wrapped.viewerPosZ;
    }

    public float getPlayerViewX() {
        return this.wrapped.playerViewX;
    }

    public float getPlayerViewY() {
        return this.wrapped.playerViewY;
    }

    public void setPlayerViewY(float value) {
        this.wrapped.setPlayerViewY(value);
    }

    public double getRenderPosX() {
        return this.wrapped.renderPosX;
    }

    public double getRenderPosY() {
        return this.wrapped.renderPosY;
    }

    public double getRenderPosZ() {
        return this.wrapped.renderPosZ;
    }

    public boolean renderEntityStatic(@NotNull IEntity entity, float renderPartialTicks, boolean hideDebugBox) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        RenderManager rendermanager = this.wrapped;
        boolean $i$f$unwrap = false;
        Entity entity = ((EntityImpl) entity).getWrapped();

        rendermanager.renderEntityStatic(entity, renderPartialTicks, hideDebugBox);
        return true;
    }

    public void renderEntityAt(@NotNull ITileEntity entity, double x, double y, double z, float partialTicks) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
        boolean $i$f$unwrap = false;
        TileEntity tileentity = ((TileEntityImpl) entity).getWrapped();

        tileentityrendererdispatcher.render(tileentity, x, y, z, partialTicks);
    }

    public boolean renderEntityWithPosYaw(@NotNull IEntityLivingBase entityLivingBase, double d, double d1, double d2, float fl, float fl1) {
        Intrinsics.checkParameterIsNotNull(entityLivingBase, "entityLivingBase");
        RenderManager rendermanager = this.wrapped;
        boolean $i$f$unwrap = false;
        EntityLivingBase entitylivingbase = (EntityLivingBase) ((EntityLivingBaseImpl) entityLivingBase).getWrapped();

        rendermanager.renderEntity((Entity) entitylivingbase, d, d1, d2, fl, fl1, true);
        return true;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof RenderManagerImpl && Intrinsics.areEqual(((RenderManagerImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final RenderManager getWrapped() {
        return this.wrapped;
    }

    public RenderManagerImpl(@NotNull RenderManager wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
