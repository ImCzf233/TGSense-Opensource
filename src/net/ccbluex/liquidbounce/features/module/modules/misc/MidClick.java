package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;

@ModuleInfo(
    name = "MidClick",
    description = "Allows you to add a player as a friend by right clicking him.",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/MidClick;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "wasDown", "", "onRender", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "LiquidBounce"}
)
public final class MidClick extends Module {

    private boolean wasDown;

    @EventTarget
    public final void onRender(@Nullable Render2DEvent event) {
        if (MinecraftInstance.mc.getCurrentScreen() == null) {
            if (!this.wasDown && Mouse.isButtonDown(2)) {
                IMovingObjectPosition imovingobjectposition = MinecraftInstance.mc.getObjectMouseOver();

                if (imovingobjectposition == null) {
                    Intrinsics.throwNpe();
                }

                IEntity entity = imovingobjectposition.getEntityHit();

                if (MinecraftInstance.classProvider.isEntityPlayer(entity)) {
                    if (entity == null) {
                        Intrinsics.throwNpe();
                    }

                    String playerName = ColorUtils.stripColor(entity.getName());
                    FriendsConfig friendsConfig = LiquidBounce.INSTANCE.getFileManager().friendsConfig;

                    if (!friendsConfig.isFriend(playerName)) {
                        friendsConfig.addFriend(playerName);
                        LiquidBounce.INSTANCE.getFileManager().saveConfig((FileConfig) friendsConfig);
                        ClientUtils.displayChatMessage("§a§l" + playerName + "§c was added to your friends.");
                    } else {
                        friendsConfig.removeFriend(playerName);
                        LiquidBounce.INSTANCE.getFileManager().saveConfig((FileConfig) friendsConfig);
                        ClientUtils.displayChatMessage("§a§l" + playerName + "§c was removed from your friends.");
                    }
                } else {
                    ClientUtils.displayChatMessage("§c§lError: §aYou need to select a player.");
                }
            }

            this.wasDown = Mouse.isButtonDown(2);
        }
    }
}
