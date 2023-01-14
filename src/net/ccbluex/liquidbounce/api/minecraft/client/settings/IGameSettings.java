package net.ccbluex.liquidbounce.api.minecraft.client.settings;

import java.util.Set;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.WEnumPlayerModelParts;
import net.minecraft.client.util.ITooltipFlag;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u00101\u001a\u00020\u00072\u0006\u00102\u001a\u00020\u0013H&J\u0018\u00103\u001a\u0002042\u0006\u0010&\u001a\u00020(2\u0006\u00105\u001a\u00020\u0007H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\u00020\u0007X¦\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\u00020\rX¦\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u0012\u0010\u0018\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0015R\u0012\u0010\u001a\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0015R\u0012\u0010\u001c\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0015R\u0012\u0010\u001e\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0015R\u0012\u0010 \u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0015R\u0012\u0010\"\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0015R\u0012\u0010$\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u0015R\u0018\u0010&\u001a\b\u0012\u0004\u0012\u00020(0\'X¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0012\u0010+\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\u000fR\u0012\u0010-\u001a\u00020.X¦\u0004¢\u0006\u0006\u001a\u0004\b/\u00100¨\u00066"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "", "advancedItemTooltips", "Lnet/minecraft/client/util/ITooltipFlag;", "getAdvancedItemTooltips", "()Lnet/minecraft/client/util/ITooltipFlag;", "entityShadows", "", "getEntityShadows", "()Z", "setEntityShadows", "(Z)V", "gammaSetting", "", "getGammaSetting", "()F", "setGammaSetting", "(F)V", "keyBindAttack", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "getKeyBindAttack", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "keyBindBack", "getKeyBindBack", "keyBindForward", "getKeyBindForward", "keyBindJump", "getKeyBindJump", "keyBindLeft", "getKeyBindLeft", "keyBindRight", "getKeyBindRight", "keyBindSneak", "getKeyBindSneak", "keyBindSprint", "getKeyBindSprint", "keyBindUseItem", "getKeyBindUseItem", "modelParts", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/WEnumPlayerModelParts;", "getModelParts", "()Ljava/util/Set;", "mouseSensitivity", "getMouseSensitivity", "thirdPersonView", "", "getThirdPersonView", "()I", "isKeyDown", "key", "setModelPartEnabled", "", "enabled", "LiquidBounce"}
)
public interface IGameSettings {

    @NotNull
    ITooltipFlag getAdvancedItemTooltips();

    boolean getEntityShadows();

    void setEntityShadows(boolean flag);

    float getGammaSetting();

    void setGammaSetting(float f);

    int getThirdPersonView();

    @NotNull
    Set getModelParts();

    float getMouseSensitivity();

    @NotNull
    IKeyBinding getKeyBindAttack();

    @NotNull
    IKeyBinding getKeyBindUseItem();

    @NotNull
    IKeyBinding getKeyBindJump();

    @NotNull
    IKeyBinding getKeyBindSneak();

    @NotNull
    IKeyBinding getKeyBindForward();

    @NotNull
    IKeyBinding getKeyBindBack();

    @NotNull
    IKeyBinding getKeyBindRight();

    @NotNull
    IKeyBinding getKeyBindLeft();

    @NotNull
    IKeyBinding getKeyBindSprint();

    boolean isKeyDown(@NotNull IKeyBinding ikeybinding);

    void setModelPartEnabled(@NotNull WEnumPlayerModelParts wenumplayermodelparts, boolean flag);
}
