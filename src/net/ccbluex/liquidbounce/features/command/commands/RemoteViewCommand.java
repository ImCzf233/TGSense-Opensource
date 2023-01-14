package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/RemoteViewCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class RemoteViewCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length < 2) {
            if (Intrinsics.areEqual(MinecraftInstance.mc.getRenderViewEntity(), MinecraftInstance.mc.getThePlayer()) ^ true) {
                MinecraftInstance.mc.setRenderViewEntity((IEntity) MinecraftInstance.mc.getThePlayer());
            } else {
                this.chatSyntax("remoteview <username>");
            }
        } else {
            String targetName = args[1];
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            Iterator iterator = iworldclient.getLoadedEntityList().iterator();

            while (iterator.hasNext()) {
                IEntity entity = (IEntity) iterator.next();

                if (Intrinsics.areEqual(targetName, entity.getName())) {
                    MinecraftInstance.mc.setRenderViewEntity(entity);
                    this.chat("Now viewing perspective of §8" + entity.getName() + "§3.");
                    this.chat("Execute §8" + LiquidBounce.INSTANCE.getCommandManager().getPrefix() + "remoteview §3again to go back to yours.");
                    break;
                }
            }

        }
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean $i$f$map = false;

        if (args.length == 0) {
            return CollectionsKt.emptyList();
        } else {
            switch (args.length) {
            case 1:
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                Iterable $this$map$iv = (Iterable) iworldclient.getPlayerEntities();

                $i$f$map = false;
                Collection destination$iv$iv = (Collection) (new ArrayList());
                boolean $i$f$mapTo = false;
                Iterator iterator = $this$map$iv.iterator();

                Object item$iv$iv;
                IEntityPlayer it;
                boolean $i$a$-map-RemoteViewCommand$tabComplete$2;
                String s;

                while (iterator.hasNext()) {
                    boolean flag;
                    label50: {
                        item$iv$iv = iterator.next();
                        it = (IEntityPlayer) item$iv$iv;
                        $i$a$-map-RemoteViewCommand$tabComplete$2 = false;
                        if (it.getName() != null) {
                            s = it.getName();
                            if (s == null) {
                                Intrinsics.throwNpe();
                            }

                            if (StringsKt.startsWith(s, args[0], true)) {
                                flag = true;
                                break label50;
                            }
                        }

                        flag = false;
                    }

                    if (flag) {
                        destination$iv$iv.add(item$iv$iv);
                    }
                }

                $this$map$iv = (Iterable) ((List) destination$iv$iv);
                $i$f$map = false;
                destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
                $i$f$mapTo = false;
                iterator = $this$map$iv.iterator();

                while (iterator.hasNext()) {
                    item$iv$iv = iterator.next();
                    it = (IEntityPlayer) item$iv$iv;
                    $i$a$-map-RemoteViewCommand$tabComplete$2 = false;
                    s = it.getName();
                    if (s == null) {
                        Intrinsics.throwNpe();
                    }

                    String s1 = s;

                    destination$iv$iv.add(s1);
                }

                return (List) destination$iv$iv;

            default:
                return CollectionsKt.emptyList();
            }
        }
    }

    public RemoteViewCommand() {
        super("remoteview", new String[] { "rv"});
    }
}
