package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.Wrapper;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/WrapperImpl;", "Lnet/ccbluex/liquidbounce/api/Wrapper;", "()V", "classProvider", "Lnet/ccbluex/liquidbounce/api/IClassProvider;", "getClassProvider", "()Lnet/ccbluex/liquidbounce/api/IClassProvider;", "functions", "Lnet/ccbluex/liquidbounce/api/IExtractedFunctions;", "getFunctions", "()Lnet/ccbluex/liquidbounce/api/IExtractedFunctions;", "minecraft", "Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "getMinecraft", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "LiquidBounce"}
)
public final class WrapperImpl implements Wrapper {

    @NotNull
    private static final IClassProvider classProvider;
    @NotNull
    private static final IExtractedFunctions functions;
    public static final WrapperImpl INSTANCE;

    @NotNull
    public IClassProvider getClassProvider() {
        return WrapperImpl.classProvider;
    }

    @NotNull
    public IMinecraft getMinecraft() {
        Minecraft minecraft = Minecraft.getMinecraft();

        Intrinsics.checkExpressionValueIsNotNull(minecraft, "Minecraft.getMinecraft()");
        return (IMinecraft) (new MinecraftImpl(minecraft));
    }

    @NotNull
    public IExtractedFunctions getFunctions() {
        return WrapperImpl.functions;
    }

    static {
        WrapperImpl wrapperimpl = new WrapperImpl();

        INSTANCE = wrapperimpl;
        classProvider = (IClassProvider) ClassProviderImpl.INSTANCE;
        functions = (IExtractedFunctions) ExtractedFunctionsImpl.INSTANCE;
    }
}
