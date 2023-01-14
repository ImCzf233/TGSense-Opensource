package net.ccbluex.liquidbounce.features.module;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.injection.backend.Backend;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.Translate;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0016\u0010`\u001a\b\u0012\u0002\b\u0003\u0018\u00010]2\u0006\u0010a\u001a\u00020\u001eH\u0016J\b\u0010b\u001a\u00020\u0005H\u0016J\b\u0010c\u001a\u00020dH\u0016J\b\u0010e\u001a\u00020dH\u0016J\u0010\u0010f\u001a\u00020d2\u0006\u0010P\u001a\u00020\u0005H\u0016J\u0006\u0010g\u001a\u00020dR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001a\u0010\u0013\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u000fR\u000e\u0010\u0016\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u001e8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010 \"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\r\"\u0004\b\'\u0010\u000fR\u001a\u0010(\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\r\"\u0004\b*\u0010\u000fR\u001a\u0010+\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0007\"\u0004\b-\u0010\tR\u001a\u0010.\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0007\"\u0004\b0\u0010\tR\u0011\u00101\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\rR\u001a\u00103\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0007\"\u0004\b4\u0010\tR\u001a\u00105\u001a\u000206X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u0011\u0010;\u001a\u00020<¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u001a\u0010?\u001a\u00020\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010 \"\u0004\bA\u0010$R$\u0010B\u001a\u00020\u00052\u0006\u00105\u001a\u00020\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010\u0007\"\u0004\bD\u0010\tR\u001a\u0010E\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010\r\"\u0004\bG\u0010\u000fR\u0012\u0010H\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010I\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010\r\"\u0004\bK\u0010\u000fR\u001a\u0010L\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010\r\"\u0004\bN\u0010\u000fR$\u0010P\u001a\u00020\u00052\u0006\u0010O\u001a\u00020\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010\u0007\"\u0004\bR\u0010\tR\u0011\u0010S\u001a\u00020T¢\u0006\b\n\u0000\u001a\u0004\bU\u0010VR\u0016\u0010W\u001a\u0004\u0018\u00010\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bX\u0010 R\u0011\u0010Y\u001a\u00020\u001e8F¢\u0006\u0006\u001a\u0004\bZ\u0010 R\u001e\u0010[\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030]0\\8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b^\u0010_¨\u0006h"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/Module;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "BreakName", "", "getBreakName", "()Z", "setBreakName", "(Z)V", "animation", "", "getAnimation", "()F", "setAnimation", "(F)V", "array", "getArray", "setArray", "arrayY", "getArrayY", "setArrayY", "canEnable", "category", "Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "getCategory", "()Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "setCategory", "(Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;)V", "colorlessTagName", "", "getColorlessTagName", "()Ljava/lang/String;", "description", "getDescription", "setDescription", "(Ljava/lang/String;)V", "higt", "getHigt", "setHigt", "hoverOpacity", "getHoverOpacity", "setHoverOpacity", "hovered", "getHovered", "setHovered", "hovvv", "getHovvv", "setHovvv", "hue", "getHue", "isSupported", "setSupported", "keyBind", "", "getKeyBind", "()I", "setKeyBind", "(I)V", "moduleTranslate", "Lme/Skid/Jello/trans/Translate;", "getModuleTranslate", "()Lme/Skid/Jello/trans/Translate;", "name", "getName", "setName", "openList", "getOpenList", "setOpenList", "rectHoved", "getRectHoved", "setRectHoved", "showSettings", "slide", "getSlide", "setSlide", "slideStep", "getSlideStep", "setSlideStep", "value", "state", "getState", "setState", "tab", "Lnet/ccbluex/liquidbounce/utils/render/Translate;", "getTab", "()Lnet/ccbluex/liquidbounce/utils/render/Translate;", "tag", "getTag", "tagName", "getTagName", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "()Ljava/util/List;", "getValue", "valueName", "handleEvents", "onDisable", "", "onEnable", "onToggle", "toggle", "LiquidBounce"}
)
public class Module extends MinecraftInstance implements Listenable {

    private boolean isSupported;
    private float animation;
    private float hoverOpacity;
    private boolean hovered;
    @JvmField
    public boolean showSettings;
    private boolean hovvv;
    private float arrayY;
    private float rectHoved;
    @NotNull
    private final Translate tab = new Translate(0.0F, 0.0F);
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private ModuleCategory category;
    @NotNull
    private final me.Skid.Jello.trans.Translate moduleTranslate = new me.Skid.Jello.trans.Translate(0.0F, 0.0F);
    private int keyBind;
    private boolean openList;
    private boolean array = true;
    private final boolean canEnable;
    private float slideStep;
    private boolean state;
    private final float hue;
    private float slide;
    private boolean BreakName;
    private float higt;

    public final boolean isSupported() {
        return this.isSupported;
    }

    public final void setSupported(boolean <set-?>) {
        this.isSupported = <set-?>;
    }

    public final float getAnimation() {
        return this.animation;
    }

    public final void setAnimation(float <set-?>) {
        this.animation = <set-?>;
    }

    public final float getHoverOpacity() {
        return this.hoverOpacity;
    }

    public final void setHoverOpacity(float <set-?>) {
        this.hoverOpacity = <set-?>;
    }

    public final boolean getHovered() {
        return this.hovered;
    }

    public final void setHovered(boolean <set-?>) {
        this.hovered = <set-?>;
    }

    public final boolean getHovvv() {
        return this.hovvv;
    }

    public final void setHovvv(boolean <set-?>) {
        this.hovvv = <set-?>;
    }

    public final float getArrayY() {
        return this.arrayY;
    }

    public final void setArrayY(float <set-?>) {
        this.arrayY = <set-?>;
    }

    public final float getRectHoved() {
        return this.rectHoved;
    }

    public final void setRectHoved(float <set-?>) {
        this.rectHoved = <set-?>;
    }

    @NotNull
    public final Translate getTab() {
        return this.tab;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.name = <set-?>;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(@NotNull String <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.description = <set-?>;
    }

    @NotNull
    public final ModuleCategory getCategory() {
        return this.category;
    }

    public final void setCategory(@NotNull ModuleCategory <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.category = <set-?>;
    }

    @NotNull
    public final me.Skid.Jello.trans.Translate getModuleTranslate() {
        return this.moduleTranslate;
    }

    public final int getKeyBind() {
        return this.keyBind;
    }

    public final void setKeyBind(int <set-?>) {
        this.keyBind = <set-?>;
    }

    public final boolean getOpenList() {
        return this.openList;
    }

    public final void setOpenList(boolean keyBind) {
        this.openList = keyBind;
        if (!LiquidBounce.INSTANCE.isStarting()) {
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
        }

    }

    public final boolean getArray() {
        return this.array;
    }

    public final void setArray(boolean array) {
        this.array = array;
        if (!LiquidBounce.INSTANCE.isStarting()) {
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
        }

    }

    public final float getSlideStep() {
        return this.slideStep;
    }

    public final void setSlideStep(float <set-?>) {
        this.slideStep = <set-?>;
    }

    public final boolean getState() {
        return this.state;
    }

    public final void setState(boolean value) {
        if (this.state != value) {
            this.onToggle(value);
            if (!LiquidBounce.INSTANCE.isStarting()) {
                switch (LiquidBounce.INSTANCE.getModuleManager().getToggleSoundMode()) {
                case 2:
                    (value ? LiquidBounce.INSTANCE.getTipSoundManager().getEnableSound() : LiquidBounce.INSTANCE.getTipSoundManager().getDisableSound()).asyncPlay();

                default:
                    LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Module", (value ? "Enabled " : "Disabled ") + this.name, value ? NotifyType.SUCCESS : NotifyType.ERROR, 0, 0, 24, (DefaultConstructorMarker) null));
                }
            }

            if (value) {
                this.onEnable();
                if (this.canEnable) {
                    this.state = true;
                }
            } else {
                this.onDisable();
                this.state = false;
            }

            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
        }
    }

    public final float getHue() {
        return this.hue;
    }

    public final float getSlide() {
        return this.slide;
    }

    public final void setSlide(float <set-?>) {
        this.slide = <set-?>;
    }

    public final boolean getBreakName() {
        return this.BreakName;
    }

    public final void setBreakName(boolean <set-?>) {
        this.BreakName = <set-?>;
    }

    public final float getHigt() {
        return this.higt;
    }

    public final void setHigt(float <set-?>) {
        this.higt = <set-?>;
    }

    @Nullable
    public String getTag() {
        return null;
    }

    @NotNull
    public final String getTagName() {
        return this.name + (this.getTag() == null ? "" : " §7" + this.getTag());
    }

    @NotNull
    public final String getColorlessTagName() {
        return this.name + (this.getTag() == null ? "" : " " + ColorUtils.stripColor(this.getTag()));
    }

    public final void toggle() {
        this.setState(!this.state);
    }

    public void onToggle(boolean state) {}

    public void onEnable() {}

    public void onDisable() {}

    @Nullable
    public Value getValue(@NotNull String valueName) {
        Intrinsics.checkParameterIsNotNull(valueName, "valueName");
        Iterable iterable = (Iterable) this.getValues();
        boolean flag = false;
        boolean flag1 = false;
        Iterator iterator = iterable.iterator();

        Object object;

        while (true) {
            if (iterator.hasNext()) {
                Object object1 = iterator.next();
                Value it = (Value) object1;
                boolean $i$a$-find-Module$getValue$1 = false;

                if (!StringsKt.equals(it.getName(), valueName, true)) {
                    continue;
                }

                object = object1;
                break;
            }

            object = null;
            break;
        }

        return (Value) object;
    }

    @NotNull
    public List getValues() {
        Field[] afield = this.getClass().getDeclaredFields();

        Intrinsics.checkExpressionValueIsNotNull(afield, "javaClass.declaredFields");
        Field[] $this$filter$iv = afield;
        boolean $i$f$filter = false;
        Collection destination$iv$iv = (Collection) (new ArrayList($this$filter$iv.length));
        boolean $i$f$filterTo = false;
        Field[] afield1 = $this$filter$iv;
        int element$iv$iv = $this$filter$iv.length;

        for (int it = 0; it < element$iv$iv; ++it) {
            Field $i$a$-filter-Module$values$2 = afield1[it];
            boolean $i$a$-map-Module$values$1 = false;

            Intrinsics.checkExpressionValueIsNotNull($i$a$-filter-Module$values$2, "valueField");
            $i$a$-filter-Module$values$2.setAccessible(true);
            Object object = $i$a$-filter-Module$values$2.get(this);

            destination$iv$iv.add(object);
        }

        Iterable iterable = (Iterable) ((List) destination$iv$iv);

        $i$f$filter = false;
        destination$iv$iv = (Collection) (new ArrayList());
        $i$f$filterTo = false;
        Iterator iterator = iterable.iterator();

        Object object1;

        while (iterator.hasNext()) {
            object1 = iterator.next();
            if (object1 instanceof Value) {
                destination$iv$iv.add(object1);
            }
        }

        iterable = (Iterable) ((List) destination$iv$iv);
        $i$f$filter = false;
        destination$iv$iv = (Collection) (new ArrayList());
        $i$f$filterTo = false;
        iterator = iterable.iterator();

        while (iterator.hasNext()) {
            object1 = iterator.next();
            Value value = (Value) object1;
            boolean flag = false;

            if (value.isSupported()) {
                destination$iv$iv.add(object1);
            }
        }

        return (List) destination$iv$iv;
    }

    public boolean handleEvents() {
        return this.state;
    }

    public Module() {
        Annotation annotation = this.getClass().getAnnotation(ModuleInfo.class);

        if (annotation == null) {
            Intrinsics.throwNpe();
        }

        ModuleInfo moduleInfo = (ModuleInfo) annotation;

        this.name = moduleInfo.name();
        this.description = moduleInfo.description();
        this.category = moduleInfo.category();
        this.keyBind = moduleInfo.keyBind();
        this.setArray(moduleInfo.array());
        this.canEnable = moduleInfo.canEnable();
        this.isSupported = ArraysKt.contains(moduleInfo.supportedVersions(), Backend.INSTANCE.getREPRESENTED_BACKEND_VERSION());
        this.hue = (float) Math.random();
    }
}
