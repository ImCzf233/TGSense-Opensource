package net.ccbluex.liquidbounce.injection.backend;

import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.WEnumPlayerModelParts;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding;
import net.ccbluex.liquidbounce.api.util.WrappedSet;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt$WhenMappings;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EnumPlayerModelParts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u00109\u001a\u00020\f2\b\u0010:\u001a\u0004\u0018\u00010;H\u0096\u0002J\u0010\u0010<\u001a\u00020\f2\u0006\u0010=\u001a\u00020\u0019H\u0016J\u0018\u0010>\u001a\u00020?2\u0006\u0010,\u001a\u00020.2\u0006\u0010@\u001a\u00020\fH\u0016R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R$\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u00128V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001bR\u0014\u0010\u001e\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001bR\u0014\u0010 \u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u001bR\u0014\u0010\"\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u001bR\u0014\u0010$\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u001bR\u0014\u0010&\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\'\u0010\u001bR\u0014\u0010(\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u001bR\u0014\u0010*\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\u001bR\u001a\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u0014\u00101\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u0010\u0015R\u0014\u00103\u001a\u0002048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b5\u00106R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b7\u00108¨\u0006A"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/GameSettingsImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "wrapped", "Lnet/minecraft/client/settings/GameSettings;", "(Lnet/minecraft/client/settings/GameSettings;)V", "value", "Lnet/minecraft/client/util/ITooltipFlag;", "advancedItemTooltips", "getAdvancedItemTooltips", "()Lnet/minecraft/client/util/ITooltipFlag;", "setAdvancedItemTooltips", "(Lnet/minecraft/client/util/ITooltipFlag;)V", "", "entityShadows", "getEntityShadows", "()Z", "setEntityShadows", "(Z)V", "", "gammaSetting", "getGammaSetting", "()F", "setGammaSetting", "(F)V", "keyBindAttack", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "getKeyBindAttack", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "keyBindBack", "getKeyBindBack", "keyBindForward", "getKeyBindForward", "keyBindJump", "getKeyBindJump", "keyBindLeft", "getKeyBindLeft", "keyBindRight", "getKeyBindRight", "keyBindSneak", "getKeyBindSneak", "keyBindSprint", "getKeyBindSprint", "keyBindUseItem", "getKeyBindUseItem", "modelParts", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/WEnumPlayerModelParts;", "getModelParts", "()Ljava/util/Set;", "mouseSensitivity", "getMouseSensitivity", "thirdPersonView", "", "getThirdPersonView", "()I", "getWrapped", "()Lnet/minecraft/client/settings/GameSettings;", "equals", "other", "", "isKeyDown", "key", "setModelPartEnabled", "", "enabled", "LiquidBounce"}
)
public final class GameSettingsImpl implements IGameSettings {

    @NotNull
    private final GameSettings wrapped;

    public boolean getEntityShadows() {
        return this.wrapped.entityShadows;
    }

    public void setEntityShadows(boolean value) {
        this.wrapped.entityShadows = value;
    }

    @NotNull
    public ITooltipFlag getAdvancedItemTooltips() {
        return this.getAdvancedItemTooltips();
    }

    public void setAdvancedItemTooltips(@NotNull ITooltipFlag value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.getAdvancedItemTooltips();
    }

    public float getGammaSetting() {
        return this.wrapped.gammaSetting;
    }

    public void setGammaSetting(float value) {
        this.wrapped.gammaSetting = value;
    }

    @NotNull
    public Set getModelParts() {
        Set set = this.wrapped.getModelParts();

        Intrinsics.checkExpressionValueIsNotNull(set, "wrapped.modelParts");
        return (Set) (new WrappedSet(set, (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    public float getMouseSensitivity() {
        return this.wrapped.mouseSensitivity;
    }

    public int getThirdPersonView() {
        return this.wrapped.thirdPersonView;
    }

    @NotNull
    public IKeyBinding getKeyBindAttack() {
        KeyBinding keybinding = this.wrapped.keyBindAttack;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.keyBindAttack, "wrapped.keyBindAttack");
        KeyBinding $this$wrap$iv = keybinding;
        boolean $i$f$wrap = false;

        return (IKeyBinding) (new KeyBindingImpl($this$wrap$iv));
    }

    @NotNull
    public IKeyBinding getKeyBindUseItem() {
        KeyBinding keybinding = this.wrapped.keyBindUseItem;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.keyBindUseItem, "wrapped.keyBindUseItem");
        KeyBinding $this$wrap$iv = keybinding;
        boolean $i$f$wrap = false;

        return (IKeyBinding) (new KeyBindingImpl($this$wrap$iv));
    }

    @NotNull
    public IKeyBinding getKeyBindJump() {
        KeyBinding keybinding = this.wrapped.keyBindJump;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.keyBindJump, "wrapped.keyBindJump");
        KeyBinding $this$wrap$iv = keybinding;
        boolean $i$f$wrap = false;

        return (IKeyBinding) (new KeyBindingImpl($this$wrap$iv));
    }

    @NotNull
    public IKeyBinding getKeyBindSneak() {
        KeyBinding keybinding = this.wrapped.keyBindSneak;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.keyBindSneak, "wrapped.keyBindSneak");
        KeyBinding $this$wrap$iv = keybinding;
        boolean $i$f$wrap = false;

        return (IKeyBinding) (new KeyBindingImpl($this$wrap$iv));
    }

    @NotNull
    public IKeyBinding getKeyBindForward() {
        KeyBinding keybinding = this.wrapped.keyBindForward;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.keyBindForward, "wrapped.keyBindForward");
        KeyBinding $this$wrap$iv = keybinding;
        boolean $i$f$wrap = false;

        return (IKeyBinding) (new KeyBindingImpl($this$wrap$iv));
    }

    @NotNull
    public IKeyBinding getKeyBindBack() {
        KeyBinding keybinding = this.wrapped.keyBindBack;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.keyBindBack, "wrapped.keyBindBack");
        KeyBinding $this$wrap$iv = keybinding;
        boolean $i$f$wrap = false;

        return (IKeyBinding) (new KeyBindingImpl($this$wrap$iv));
    }

    @NotNull
    public IKeyBinding getKeyBindRight() {
        KeyBinding keybinding = this.wrapped.keyBindRight;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.keyBindRight, "wrapped.keyBindRight");
        KeyBinding $this$wrap$iv = keybinding;
        boolean $i$f$wrap = false;

        return (IKeyBinding) (new KeyBindingImpl($this$wrap$iv));
    }

    @NotNull
    public IKeyBinding getKeyBindLeft() {
        KeyBinding keybinding = this.wrapped.keyBindLeft;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.keyBindLeft, "wrapped.keyBindLeft");
        KeyBinding $this$wrap$iv = keybinding;
        boolean $i$f$wrap = false;

        return (IKeyBinding) (new KeyBindingImpl($this$wrap$iv));
    }

    @NotNull
    public IKeyBinding getKeyBindSprint() {
        KeyBinding keybinding = this.wrapped.keyBindSprint;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.keyBindSprint, "wrapped.keyBindSprint");
        KeyBinding $this$wrap$iv = keybinding;
        boolean $i$f$wrap = false;

        return (IKeyBinding) (new KeyBindingImpl($this$wrap$iv));
    }

    public boolean isKeyDown(@NotNull IKeyBinding key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        boolean $i$f$unwrap = false;

        return GameSettings.isKeyDown(((KeyBindingImpl) key).getWrapped());
    }

    public void setModelPartEnabled(@NotNull WEnumPlayerModelParts modelParts, boolean enabled) {
        Intrinsics.checkParameterIsNotNull(modelParts, "modelParts");
        GameSettings gamesettings = this.wrapped;
        boolean $i$f$unwrap = false;
        EnumPlayerModelParts enumplayermodelparts;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$1[modelParts.ordinal()]) {
        case 1:
            enumplayermodelparts = EnumPlayerModelParts.CAPE;
            break;

        case 2:
            enumplayermodelparts = EnumPlayerModelParts.JACKET;
            break;

        case 3:
            enumplayermodelparts = EnumPlayerModelParts.LEFT_SLEEVE;
            break;

        case 4:
            enumplayermodelparts = EnumPlayerModelParts.RIGHT_SLEEVE;
            break;

        case 5:
            enumplayermodelparts = EnumPlayerModelParts.LEFT_PANTS_LEG;
            break;

        case 6:
            enumplayermodelparts = EnumPlayerModelParts.RIGHT_PANTS_LEG;
            break;

        case 7:
            enumplayermodelparts = EnumPlayerModelParts.HAT;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        EnumPlayerModelParts enumplayermodelparts1 = enumplayermodelparts;

        gamesettings.setModelPartEnabled(enumplayermodelparts1, enabled);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof GameSettingsImpl && Intrinsics.areEqual(((GameSettingsImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final GameSettings getWrapped() {
        return this.wrapped;
    }

    public GameSettingsImpl(@NotNull GameSettings wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
