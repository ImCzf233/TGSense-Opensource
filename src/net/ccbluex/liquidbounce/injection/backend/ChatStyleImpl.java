package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.event.IClickEvent;
import net.ccbluex.liquidbounce.api.minecraft.util.IChatStyle;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt$WhenMappings;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u001a\u001a\u00020\u00122\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0096\u0002R(\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR(\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u0005\u001a\u0004\u0018\u00010\f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R$\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u00128V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001d"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ChatStyleImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IChatStyle;", "wrapped", "Lnet/minecraft/util/text/Style;", "(Lnet/minecraft/util/text/Style;)V", "value", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;", "chatClickEvent", "getChatClickEvent", "()Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;", "setChatClickEvent", "(Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;)V", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "color", "getColor", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "setColor", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;)V", "", "underlined", "getUnderlined", "()Z", "setUnderlined", "(Z)V", "getWrapped", "()Lnet/minecraft/util/text/Style;", "equals", "other", "", "LiquidBounce"}
)
public final class ChatStyleImpl implements IChatStyle {

    @NotNull
    private final Style wrapped;

    @Nullable
    public IClickEvent getChatClickEvent() {
        ClickEvent clickevent = this.wrapped.getClickEvent();
        IClickEvent iclickevent;

        if (clickevent != null) {
            ClickEvent $this$wrap$iv = clickevent;
            boolean $i$f$wrap = false;

            iclickevent = (IClickEvent) (new ClickEventImpl($this$wrap$iv));
        } else {
            iclickevent = null;
        }

        return iclickevent;
    }

    public void setChatClickEvent(@Nullable IClickEvent value) {
        Style style = this.wrapped;
        ClickEvent clickevent;

        if (value != null) {
            Style style1 = style;
            boolean $i$f$unwrap = false;

            if (value == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.ClickEventImpl");
            }

            ClickEvent clickevent1 = ((ClickEventImpl) value).getWrapped();

            style = style1;
            clickevent = clickevent1;
        } else {
            clickevent = null;
        }

        style.setClickEvent(clickevent);
    }

    public boolean getUnderlined() {
        return this.wrapped.getUnderlined();
    }

    public void setUnderlined(boolean value) {
        this.wrapped.setUnderlined(Boolean.valueOf(value));
    }

    @Nullable
    public WEnumChatFormatting getColor() {
        TextFormatting textformatting = this.wrapped.getColor();
        WEnumChatFormatting wenumchatformatting;

        if (textformatting != null) {
            TextFormatting $this$wrap$iv = textformatting;
            boolean $i$f$wrap = false;

            switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$3[$this$wrap$iv.ordinal()]) {
            case 1:
                wenumchatformatting = WEnumChatFormatting.BLACK;
                break;

            case 2:
                wenumchatformatting = WEnumChatFormatting.DARK_BLUE;
                break;

            case 3:
                wenumchatformatting = WEnumChatFormatting.DARK_GREEN;
                break;

            case 4:
                wenumchatformatting = WEnumChatFormatting.DARK_AQUA;
                break;

            case 5:
                wenumchatformatting = WEnumChatFormatting.DARK_RED;
                break;

            case 6:
                wenumchatformatting = WEnumChatFormatting.DARK_PURPLE;
                break;

            case 7:
                wenumchatformatting = WEnumChatFormatting.GOLD;
                break;

            case 8:
                wenumchatformatting = WEnumChatFormatting.GRAY;
                break;

            case 9:
                wenumchatformatting = WEnumChatFormatting.DARK_GRAY;
                break;

            case 10:
                wenumchatformatting = WEnumChatFormatting.BLUE;
                break;

            case 11:
                wenumchatformatting = WEnumChatFormatting.GREEN;
                break;

            case 12:
                wenumchatformatting = WEnumChatFormatting.AQUA;
                break;

            case 13:
                wenumchatformatting = WEnumChatFormatting.RED;
                break;

            case 14:
                wenumchatformatting = WEnumChatFormatting.LIGHT_PURPLE;
                break;

            case 15:
                wenumchatformatting = WEnumChatFormatting.YELLOW;
                break;

            case 16:
                wenumchatformatting = WEnumChatFormatting.WHITE;
                break;

            case 17:
                wenumchatformatting = WEnumChatFormatting.OBFUSCATED;
                break;

            case 18:
                wenumchatformatting = WEnumChatFormatting.BOLD;
                break;

            case 19:
                wenumchatformatting = WEnumChatFormatting.STRIKETHROUGH;
                break;

            case 20:
                wenumchatformatting = WEnumChatFormatting.UNDERLINE;
                break;

            case 21:
                wenumchatformatting = WEnumChatFormatting.ITALIC;
                break;

            case 22:
                wenumchatformatting = WEnumChatFormatting.RESET;
                break;

            default:
                throw new NoWhenBranchMatchedException();
            }
        } else {
            wenumchatformatting = null;
        }

        return wenumchatformatting;
    }

    public void setColor(@Nullable WEnumChatFormatting value) {
        Style style = this.wrapped;
        TextFormatting textformatting;

        if (value != null) {
            Style style1 = style;
            boolean $i$f$unwrap = false;
            TextFormatting textformatting1;

            switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$4[value.ordinal()]) {
            case 1:
                textformatting1 = TextFormatting.BLACK;
                break;

            case 2:
                textformatting1 = TextFormatting.DARK_BLUE;
                break;

            case 3:
                textformatting1 = TextFormatting.DARK_GREEN;
                break;

            case 4:
                textformatting1 = TextFormatting.DARK_AQUA;
                break;

            case 5:
                textformatting1 = TextFormatting.DARK_RED;
                break;

            case 6:
                textformatting1 = TextFormatting.DARK_PURPLE;
                break;

            case 7:
                textformatting1 = TextFormatting.GOLD;
                break;

            case 8:
                textformatting1 = TextFormatting.GRAY;
                break;

            case 9:
                textformatting1 = TextFormatting.DARK_GRAY;
                break;

            case 10:
                textformatting1 = TextFormatting.BLUE;
                break;

            case 11:
                textformatting1 = TextFormatting.GREEN;
                break;

            case 12:
                textformatting1 = TextFormatting.AQUA;
                break;

            case 13:
                textformatting1 = TextFormatting.RED;
                break;

            case 14:
                textformatting1 = TextFormatting.LIGHT_PURPLE;
                break;

            case 15:
                textformatting1 = TextFormatting.YELLOW;
                break;

            case 16:
                textformatting1 = TextFormatting.WHITE;
                break;

            case 17:
                textformatting1 = TextFormatting.OBFUSCATED;
                break;

            case 18:
                textformatting1 = TextFormatting.BOLD;
                break;

            case 19:
                textformatting1 = TextFormatting.STRIKETHROUGH;
                break;

            case 20:
                textformatting1 = TextFormatting.UNDERLINE;
                break;

            case 21:
                textformatting1 = TextFormatting.ITALIC;
                break;

            case 22:
                textformatting1 = TextFormatting.RESET;
                break;

            default:
                throw new NoWhenBranchMatchedException();
            }

            TextFormatting textformatting2 = textformatting1;

            style = style1;
            textformatting = textformatting2;
        } else {
            textformatting = null;
        }

        style.setColor(textformatting);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ChatStyleImpl && Intrinsics.areEqual(((ChatStyleImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Style getWrapped() {
        return this.wrapped;
    }

    public ChatStyleImpl(@NotNull Style wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}
