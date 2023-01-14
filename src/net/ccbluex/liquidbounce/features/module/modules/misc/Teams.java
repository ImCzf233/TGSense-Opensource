package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "Teams",
    description = "Prevents Killaura from attacking team mates.",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/Teams;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorColorValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorValue", "gommeSWValue", "scoreboardValue", "tag", "", "getTag", "()Ljava/lang/String;", "isInYourTeam", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "LiquidBounce"}
)
public final class Teams extends Module {

    private final BoolValue scoreboardValue = new BoolValue("ScoreboardTeam", true);
    private final BoolValue colorValue = new BoolValue("Color", true);
    private final BoolValue gommeSWValue = new BoolValue("GommeSW", false);
    private final BoolValue armorColorValue = new BoolValue("ArmorColor", false);

    public final boolean isInYourTeam(@NotNull IEntityLivingBase entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (((Boolean) this.scoreboardValue.get()).booleanValue() && thePlayer.getTeam() != null && entity.getTeam() != null) {
                ITeam iteam = thePlayer.getTeam();

                if (iteam == null) {
                    Intrinsics.throwNpe();
                }

                ITeam iteam1 = entity.getTeam();

                if (iteam1 == null) {
                    Intrinsics.throwNpe();
                }

                if (iteam.isSameTeam(iteam1)) {
                    return true;
                }
            }

            IIChatComponent displayName = thePlayer.getDisplayName();

            if (((Boolean) this.armorColorValue.get()).booleanValue()) {
                IEntityPlayer targetName = entity.asEntityPlayer();

                if (thePlayer.getInventory().getArmorInventory().get(3) != null && targetName.getInventory().getArmorInventory().get(3) != null) {
                    IItemStack clientName = (IItemStack) thePlayer.getInventory().getArmorInventory().get(3);

                    if (clientName == null) {
                        Intrinsics.throwNpe();
                    }

                    IItem iitem = clientName.getItem();

                    if (iitem == null) {
                        Intrinsics.throwNpe();
                    }

                    IItemArmor myItemArmor = iitem.asItemArmor();
                    IItemStack entityHead = (IItemStack) targetName.getInventory().getArmorInventory().get(3);

                    iitem = clientName.getItem();
                    if (iitem == null) {
                        Intrinsics.throwNpe();
                    }

                    IItemArmor entityItemArmor = iitem.asItemArmor();
                    int i = myItemArmor.getColor(clientName);

                    if (entityHead == null) {
                        Intrinsics.throwNpe();
                    }

                    if (i == entityItemArmor.getColor(entityHead)) {
                        return true;
                    }
                }
            }

            String targetName1;
            String clientName1;
            IIChatComponent iichatcomponent;

            if (((Boolean) this.gommeSWValue.get()).booleanValue() && displayName != null && entity.getDisplayName() != null) {
                iichatcomponent = entity.getDisplayName();
                if (iichatcomponent == null) {
                    Intrinsics.throwNpe();
                }

                targetName1 = StringsKt.replace$default(iichatcomponent.getFormattedText(), "§r", "", false, 4, (Object) null);
                clientName1 = StringsKt.replace$default(displayName.getFormattedText(), "§r", "", false, 4, (Object) null);
                if (StringsKt.startsWith$default(targetName1, "T", false, 2, (Object) null) && StringsKt.startsWith$default(clientName1, "T", false, 2, (Object) null)) {
                    char myItemArmor1 = targetName1.charAt(1);
                    boolean entityHead1 = false;

                    if (Character.isDigit(myItemArmor1)) {
                        myItemArmor1 = clientName1.charAt(1);
                        entityHead1 = false;
                        if (Character.isDigit(myItemArmor1)) {
                            return targetName1.charAt(1) == clientName1.charAt(1);
                        }
                    }
                }
            }

            if (((Boolean) this.colorValue.get()).booleanValue() && displayName != null && entity.getDisplayName() != null) {
                iichatcomponent = entity.getDisplayName();
                if (iichatcomponent == null) {
                    Intrinsics.throwNpe();
                }

                targetName1 = StringsKt.replace$default(iichatcomponent.getFormattedText(), "§r", "", false, 4, (Object) null);
                clientName1 = StringsKt.replace$default(displayName.getFormattedText(), "§r", "", false, 4, (Object) null);
                return StringsKt.startsWith$default(targetName1, "" + '§' + clientName1.charAt(1), false, 2, (Object) null);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Nullable
    public String getTag() {
        return "HuaYuTing";
    }
}
