package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.net.URI;
import java.net.URISyntaxException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketResourcePackStatus;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketResourcePackSend;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "ResourcePackSpoof",
    description = "Prevents servers from forcing you to download their resource pack.",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/ResourcePackSpoof;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"}
)
public final class ResourcePackSpoof extends Module {

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isSPacketResourcePackSend(event.getPacket())) {
            ISPacketResourcePackSend packet = event.getPacket().asSPacketResourcePackSend();
            String url = packet.getUrl();
            String hash = packet.getHash();

            try {
                String e = (new URI(url)).getScheme();
                boolean isLevelProtocol = Intrinsics.areEqual("level", e);

                if (Intrinsics.areEqual("http", e) ^ true && Intrinsics.areEqual("https", e) ^ true && !isLevelProtocol) {
                    throw (Throwable) (new URISyntaxException(url, "Wrong protocol"));
                }

                if (isLevelProtocol && (StringsKt.contains$default((CharSequence) url, (CharSequence) "..", false, 2, (Object) null) || !StringsKt.endsWith$default(url, "/resources.zip", false, 2, (Object) null))) {
                    throw (Throwable) (new URISyntaxException(url, "Invalid levelstorage resourcepack path"));
                }

                MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createICPacketResourcePackStatus(packet.getHash(), ICPacketResourcePackStatus.WAction.ACCEPTED));
                MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createICPacketResourcePackStatus(packet.getHash(), ICPacketResourcePackStatus.WAction.SUCCESSFULLY_LOADED));
            } catch (URISyntaxException urisyntaxexception) {
                ClientUtils.getLogger().error("Failed to handle resource pack", (Throwable) urisyntaxexception);
                MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createICPacketResourcePackStatus(hash, ICPacketResourcePackStatus.WAction.FAILED_DOWNLOAD));
            }
        }

    }
}
