package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/FriendCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class FriendCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            FriendsConfig friendsConfig = LiquidBounce.INSTANCE.getFileManager().friendsConfig;
            String friend2;

            if (StringsKt.equals(args[1], "add", true)) {
                if (args.length > 2) {
                    friend2 = args[2];
                    CharSequence charsequence = (CharSequence) friend2;
                    boolean flag = false;

                    if (charsequence.length() == 0) {
                        this.chat("The name is empty.");
                        return;
                    }

                    if (args.length > 3 ? friendsConfig.addFriend(friend2, StringUtils.toCompleteString(args, 3)) : friendsConfig.addFriend(friend2)) {
                        LiquidBounce.INSTANCE.getFileManager().saveConfig((FileConfig) friendsConfig);
                        this.chat("§a§l" + friend2 + "§3 was added to your friend list.");
                        this.playEdit();
                    } else {
                        this.chat("The name is already in the list.");
                    }

                    return;
                }

                this.chatSyntax("friend add <name> [alias]");
                return;
            }

            if (StringsKt.equals(args[1], "remove", true)) {
                if (args.length > 2) {
                    friend2 = args[2];
                    if (friendsConfig.removeFriend(friend2)) {
                        LiquidBounce.INSTANCE.getFileManager().saveConfig((FileConfig) friendsConfig);
                        this.chat("§a§l" + friend2 + "§3 was removed from your friend list.");
                        this.playEdit();
                    } else {
                        this.chat("This name is not in the list.");
                    }

                    return;
                }

                this.chatSyntax("friend remove <name>");
                return;
            }

            if (StringsKt.equals(args[1], "clear", true)) {
                Intrinsics.checkExpressionValueIsNotNull(friendsConfig, "friendsConfig");
                int friend1 = friendsConfig.getFriends().size();

                friendsConfig.clearFriends();
                LiquidBounce.INSTANCE.getFileManager().saveConfig((FileConfig) friendsConfig);
                this.chat("Removed " + friend1 + " friend(s).");
                return;
            }

            if (StringsKt.equals(args[1], "list", true)) {
                this.chat("Your Friends:");
                Intrinsics.checkExpressionValueIsNotNull(friendsConfig, "friendsConfig");
                Iterator iterator = friendsConfig.getFriends().iterator();

                while (iterator.hasNext()) {
                    FriendsConfig.Friend friend = (FriendsConfig.Friend) iterator.next();
                    StringBuilder stringbuilder = (new StringBuilder()).append("§7> §a§l");

                    Intrinsics.checkExpressionValueIsNotNull(friend, "friend");
                    this.chat(stringbuilder.append(friend.getPlayerName()).append(" §c(§7§l").append(friend.getAlias()).append("§c)").toString());
                }

                this.chat("You have §c" + friendsConfig.getFriends().size() + "§3 friends.");
                return;
            }
        }

        this.chatSyntax("friend <add/remove/list/clear>");
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean $this$filter$iv1 = false;

        if (args.length == 0) {
            return CollectionsKt.emptyList();
        } else {
            List list;

            switch (args.length) {
            case 1:
                Iterable $this$filter$iv2 = (Iterable) CollectionsKt.listOf(new String[] { "add", "remove", "list", "clear"});

                $this$filter$iv1 = false;
                Collection $this$filterTo$iv$iv = (Collection) (new ArrayList());
                boolean destination$iv$iv1 = false;
                Iterator $i$f$filterTo1 = $this$filter$iv2.iterator();

                while ($i$f$filterTo1.hasNext()) {
                    Object element$iv$iv2 = $i$f$filterTo1.next();
                    String element$iv$iv3 = (String) element$iv$iv2;
                    boolean it3 = false;

                    if (StringsKt.startsWith(element$iv$iv3, args[0], true)) {
                        $this$filterTo$iv$iv.add(element$iv$iv2);
                    }
                }

                list = (List) $this$filterTo$iv$iv;
                break;

            case 2:
                String $this$filter$iv = args[0];

                $this$filter$iv1 = false;
                if ($this$filter$iv == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s = $this$filter$iv.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                $this$filter$iv = s;
                boolean $i$f$filter;
                Collection destination$iv$iv;
                boolean $i$f$filterTo;
                Iterator element$iv$iv;
                Object element$iv$iv1;
                boolean $i$a$-filter-FriendCommand$tabComplete$5;
                String s1;
                Iterable $this$filter$iv3;

                switch ($this$filter$iv.hashCode()) {
                case -934610812:
                    if ($this$filter$iv.equals("remove")) {
                        FriendsConfig friendsconfig = LiquidBounce.INSTANCE.getFileManager().friendsConfig;

                        Intrinsics.checkExpressionValueIsNotNull(friendsconfig, "LiquidBounce.fileManager.friendsConfig");
                        list = friendsconfig.getFriends();
                        Intrinsics.checkExpressionValueIsNotNull(list, "LiquidBounce.fileManager.friendsConfig.friends");
                        $this$filter$iv3 = (Iterable) list;
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv3, 10)));
                        $i$f$filterTo = false;
                        element$iv$iv = $this$filter$iv3.iterator();

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            FriendsConfig.Friend it1 = (FriendsConfig.Friend) element$iv$iv1;

                            $i$a$-filter-FriendCommand$tabComplete$5 = false;
                            Intrinsics.checkExpressionValueIsNotNull(it1, "it");
                            s1 = it1.getPlayerName();
                            destination$iv$iv.add(s1);
                        }

                        $this$filter$iv3 = (Iterable) ((List) destination$iv$iv);
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList());
                        $i$f$filterTo = false;
                        element$iv$iv = $this$filter$iv3.iterator();

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            String it2 = (String) element$iv$iv1;

                            $i$a$-filter-FriendCommand$tabComplete$5 = false;
                            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                            if (StringsKt.startsWith(it2, args[1], true)) {
                                destination$iv$iv.add(element$iv$iv1);
                            }
                        }

                        return (List) destination$iv$iv;
                    }
                    break;

                case 96417:
                    if ($this$filter$iv.equals("add")) {
                        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                        if (iworldclient == null) {
                            Intrinsics.throwNpe();
                        }

                        $this$filter$iv3 = (Iterable) iworldclient.getPlayerEntities();
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList());
                        $i$f$filterTo = false;
                        element$iv$iv = $this$filter$iv3.iterator();

                        IEntityPlayer it;

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            it = (IEntityPlayer) element$iv$iv1;
                            $i$a$-filter-FriendCommand$tabComplete$5 = false;
                            s = it.getName();
                            if (s != null ? StringsKt.startsWith(s, args[1], true) : false) {
                                destination$iv$iv.add(element$iv$iv1);
                            }
                        }

                        $this$filter$iv3 = (Iterable) ((List) destination$iv$iv);
                        $i$f$filter = false;
                        destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv3, 10)));
                        $i$f$filterTo = false;
                        element$iv$iv = $this$filter$iv3.iterator();

                        while (element$iv$iv.hasNext()) {
                            element$iv$iv1 = element$iv$iv.next();
                            it = (IEntityPlayer) element$iv$iv1;
                            $i$a$-filter-FriendCommand$tabComplete$5 = false;
                            s = it.getName();
                            if (s == null) {
                                Intrinsics.throwNpe();
                            }

                            s1 = s;
                            destination$iv$iv.add(s1);
                        }

                        return (List) destination$iv$iv;
                    }
                }

                return CollectionsKt.emptyList();

            default:
                list = CollectionsKt.emptyList();
            }

            return list;
        }
    }

    public FriendCommand() {
        super("friend", new String[] { "friends"});
    }
}
