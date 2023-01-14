package net.ccbluex.liquidbounce.features.command.commands;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001b\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\tH\u0016¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\rH\u0016J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0018H\u0007J!\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\u001a2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\tH\u0016¢\u0006\u0002\u0010\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/TacoCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "image", "", "running", "", "tacoTextures", "", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "[Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "toggle", "", "execute", "", "args", "", "([Ljava/lang/String;)V", "handleEvents", "onRender2D", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class TacoCommand extends Command implements Listenable {

    private boolean toggle;
    private int image;
    private float running;
    private final IResourceLocation[] tacoTextures;

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        this.toggle = !this.toggle;
        ClientUtils.displayChatMessage(this.toggle ? "§aTACO TACO TACO. :)" : "§cYou made the little taco sad! :(");
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.toggle) {
            this.running += 0.15F * (float) RenderUtils.deltaTime;
            IClassProvider iclassprovider = MinecraftInstance.classProvider;
            IMinecraft iminecraft = MinecraftInstance.mc;

            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
            IScaledResolution scaledResolution = iclassprovider.createScaledResolution(iminecraft);

            RenderUtils.drawImage(this.tacoTextures[this.image], (int) this.running, scaledResolution.getScaledHeight() - 60, 64, 32);
            if ((float) scaledResolution.getScaledWidth() <= this.running) {
                this.running = -64.0F;
            }

        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (!this.toggle) {
            this.image = 0;
        } else {
            int i = this.image++;

            if (this.image >= this.tacoTextures.length) {
                this.image = 0;
            }

        }
    }

    public boolean handleEvents() {
        return true;
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        return CollectionsKt.listOf("TACO");
    }

    public TacoCommand() {
        super("taco", new String[0]);
        this.tacoTextures = new IResourceLocation[] { MinecraftInstance.classProvider.createResourceLocation("langya/taco/1.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/2.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/3.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/4.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/5.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/6.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/7.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/8.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/9.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/10.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/11.png"), MinecraftInstance.classProvider.createResourceLocation("langya/taco/12.png")};
        LiquidBounce.INSTANCE.getEventManager().registerListener((Listenable) this);
    }
}
