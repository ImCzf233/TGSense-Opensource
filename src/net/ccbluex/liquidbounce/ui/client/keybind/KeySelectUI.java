package net.ccbluex.liquidbounce.ui.client.keybind;

import java.awt.Color;
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
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.ui.client.other.PopUI;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\f\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0002J\u0018\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0016J\b\u0010\u0018\u001a\u00020\u0013H\u0016J\u0018\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0011H\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0016J \u0010\u0010\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u0011H\u0016J\b\u0010\u001f\u001a\u00020\u0013H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeySelectUI;", "Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "info", "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "(Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;)V", "height", "", "getInfo", "()Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "maxStroll", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "singleHeight", "str", "", "stroll", "", "apply", "", "module", "click", "mouseX", "mouseY", "close", "key", "typedChar", "", "keyCode", "render", "wheel", "update", "LiquidBounce"}
)
public final class KeySelectUI extends PopUI {

    private String str;
    private List modules;
    private final float singleHeight;
    private int stroll;
    private float maxStroll;
    private final float height;
    @NotNull
    private final KeyInfo info;

    public void render() {
        float yOffset = this.height - (float) this.stroll + 5.0F;
        boolean $i$a$-ifEmpty-KeySelectUI$render$1;
        IFontRenderer ifontrenderer;
        String s;
        IFontRenderer ifontrenderer1;
        float f;
        Color color;

        if (StringsKt.startsWith$default(this.str, ".", false, 2, (Object) null)) {
            ifontrenderer1 = Fonts.font35;
            f = this.singleHeight + yOffset;
            color = Color.BLACK;
            Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
            ifontrenderer1.drawString("Add", 8.0F, f, color.getRGB(), false);
        } else {
            for (Iterator iterator = this.modules.iterator(); iterator.hasNext(); yOffset += this.singleHeight) {
                Module module = (Module) iterator.next();

                if (yOffset > this.height - this.singleHeight && yOffset - this.singleHeight < (float) 190) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0F, yOffset, 0.0F);
                    String name = module.getName();
                    CharSequence charsequence = (CharSequence) this.str;

                    ifontrenderer = Fonts.font35;
                    $i$a$-ifEmpty-KeySelectUI$render$1 = false;
                    boolean flag = charsequence.length() > 0;

                    ifontrenderer1 = ifontrenderer;
                    if (flag) {
                        StringBuilder stringbuilder = (new StringBuilder()).append("§0");
                        byte $i$a$-ifEmpty-KeySelectUI$render$11 = 0;
                        int i = this.str.length();
                        StringBuilder stringbuilder1 = stringbuilder;
                        boolean flag1 = false;

                        if (name == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        String s1 = name.substring($i$a$-ifEmpty-KeySelectUI$render$11, i);

                        Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        String s2 = s1;

                        stringbuilder = stringbuilder1.append(s2).append("§7");
                        int $i$a$-ifEmpty-KeySelectUI$render$12 = this.str.length();

                        i = name.length();
                        stringbuilder1 = stringbuilder;
                        flag1 = false;
                        if (name == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        s1 = name.substring($i$a$-ifEmpty-KeySelectUI$render$12, i);
                        Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        s2 = s1;
                        ifontrenderer1 = ifontrenderer;
                        s = stringbuilder1.append(s2).toString();
                    } else {
                        s = "§0" + name;
                    }

                    f = this.singleHeight * 0.5F;
                    color = Color.BLACK;
                    Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
                    ifontrenderer1.drawString(s, 8.0F, f, color.getRGB(), false);
                    GL11.glPopMatrix();
                }
            }
        }

        float f1 = 8.0F + (float) Fonts.font40.getFontHeight();
        float f2 = (float) this.getBaseWidth();

        f = this.height + 5.0F;
        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        RenderUtils.drawRect(0.0F, f1, f2, f, color.getRGB());
        f1 = (float) this.getBaseHeight() - this.singleHeight;
        f2 = (float) this.getBaseWidth();
        f = (float) this.getBaseHeight();
        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        RenderUtils.drawRect(0.0F, f1, f2, f, color.getRGB());
        CharSequence module1 = (CharSequence) this.str;

        ifontrenderer = Fonts.font35;
        boolean flag2 = false;
        boolean flag3 = false;
        Object object;

        if (module1.length() == 0) {
            $i$a$-ifEmpty-KeySelectUI$render$1 = false;
            object = "Search";
        } else {
            object = module1;
        }

        Object object1 = object;

        s = (String) object1;
        f = 8.0F + (float) Fonts.font40.getFontHeight() + 4.0F;
        color = Color.LIGHT_GRAY;
        Intrinsics.checkExpressionValueIsNotNull(Color.LIGHT_GRAY, "Color.LIGHT_GRAY");
        ifontrenderer.drawString(s, 8.0F, f, color.getRGB(), false);
        f1 = this.height + 2.0F;
        f2 = (float) this.getBaseWidth() - 8.0F;
        f = this.height + 3.0F;
        color = Color.LIGHT_GRAY;
        Intrinsics.checkExpressionValueIsNotNull(Color.LIGHT_GRAY, "Color.LIGHT_GRAY");
        RenderUtils.drawRect(8.0F, f1, f2, f, color.getRGB());
    }

    public void key(char typedChar, int keyCode) {
        boolean flag;

        if (keyCode == 14) {
            CharSequence charsequence = (CharSequence) this.str;

            flag = false;
            if (charsequence.length() > 0) {
                String s = this.str;
                byte b0 = 0;
                int i = this.str.length() - 1;
                boolean flag1 = false;

                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s1 = s.substring(b0, i);

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                String s2 = s1;

                this.str = s2;
                this.update();
            }

        } else if (keyCode == 28) {
            if (StringsKt.startsWith$default(this.str, ".", false, 2, (Object) null)) {
                LiquidBounce.INSTANCE.getMacroManager().getMacros().add(new Macro(this.info.getKey(), this.str));
                LiquidBounce.INSTANCE.getKeyBindManager().updateAllKeys();
                this.close();
            } else {
                Collection collection = (Collection) this.modules;

                flag = false;
                if (!collection.isEmpty()) {
                    this.apply((Module) this.modules.get(0));
                }
            }

        } else {
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                String s3 = this.str;

                this.str = s3 + typedChar;
                this.update();
            }

        }
    }

    public void stroll(float mouseX, float mouseY, int wheel) {
        int afterStroll = this.stroll - wheel / 10;

        if (afterStroll > 0 && (float) afterStroll < this.maxStroll - (float) 100) {
            this.stroll = afterStroll;
        }

    }

    public void click(float mouseX, float mouseY) {
        if (mouseX >= (float) 8 && mouseX <= (float) (this.getBaseWidth() - 8) && mouseY >= this.height && mouseY <= (float) this.getBaseHeight() - this.singleHeight) {
            float yOffset = this.height - (float) this.stroll + 2.0F;

            for (Iterator iterator = this.modules.iterator(); iterator.hasNext(); yOffset += this.singleHeight) {
                Module module = (Module) iterator.next();

                if (mouseY > yOffset && mouseY < yOffset + this.singleHeight) {
                    this.apply(module);
                    break;
                }
            }

        }
    }

    private final void apply(Module module) {
        module.setKeyBind(this.info.getKey());
        LiquidBounce.INSTANCE.getKeyBindManager().updateAllKeys();
        this.close();
    }

    public void close() {
        LiquidBounce.INSTANCE.getKeyBindManager().setPopUI((PopUI) null);
    }

    private final void update() {
        CharSequence $this$filter$iv = (CharSequence) this.str;
        boolean $i$f$filter = false;
        boolean flag = $this$filter$iv.length() > 0;
        KeySelectUI keyselectui = this;
        List list;

        if (flag) {
            Iterable $this$filter$iv1 = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();

            $i$f$filter = false;
            Collection destination$iv$iv = (Collection) (new ArrayList());
            boolean $i$f$filterTo = false;
            Iterator iterator = $this$filter$iv1.iterator();

            while (iterator.hasNext()) {
                Object element$iv$iv = iterator.next();
                Module it = (Module) element$iv$iv;
                boolean $i$a$-filter-KeySelectUI$update$1 = false;

                if (StringsKt.startsWith(it.getName(), this.str, true)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }

            List list1 = (List) destination$iv$iv;

            keyselectui = this;
            list = list1;
        } else {
            list = CollectionsKt.toList((Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules());
        }

        keyselectui.modules = list;
        this.maxStroll = (float) this.modules.size() * this.singleHeight;
        this.stroll = 0;
    }

    @NotNull
    public final KeyInfo getInfo() {
        return this.info;
    }

    public KeySelectUI(@NotNull KeyInfo info) {
        Intrinsics.checkParameterIsNotNull(info, "info");
        super("Select");
        this.info = info;
        this.str = "";
        this.modules = CollectionsKt.toList((Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules());
        this.singleHeight = 4.0F + (float) Fonts.font35.getFontHeight();
        this.maxStroll = (float) this.modules.size() * this.singleHeight;
        this.height = 8.0F + (float) Fonts.font40.getFontHeight() + (float) Fonts.font35.getFontHeight() + 0.5F;
    }
}
