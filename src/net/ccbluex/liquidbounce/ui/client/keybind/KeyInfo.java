package net.ccbluex.liquidbounce.ui.client.keybind;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import me.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.ui.client.other.PopUI;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B7\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bB=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n¢\u0006\u0002\u0010\rJ\u0016\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00032\u0006\u0010,\u001a\u00020\u0003J\u0006\u0010-\u001a\u00020*J\u0006\u0010.\u001a\u00020*J\u001e\u0010%\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00032\u0006\u0010,\u001a\u00020\u00032\u0006\u0010/\u001a\u00020\bJ\u0006\u00100\u001a\u00020*R\u000e\u0010\u000e\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u001e\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u001d0\u001cj\b\u0012\u0004\u0012\u00020\u001d`\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010 \u001a\u0012\u0012\u0004\u0012\u00020!0\u001cj\b\u0012\u0004\u0012\u00020!`\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0014R\u000e\u0010$\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0014¨\u00061"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "posX", "", "posY", "width", "height", "key", "", "keyName", "", "(FFFFILjava/lang/String;)V", "keyDisplayName", "(FFFFILjava/lang/String;Ljava/lang/String;)V", "baseTabHeight", "baseTabWidth", "direction", "", "hasKeyBind", "getHeight", "()F", "getKey", "()I", "keyColor", "getKeyDisplayName", "()Ljava/lang/String;", "getKeyName", "macros", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/features/macro/Macro;", "Lkotlin/collections/ArrayList;", "maxStroll", "modules", "Lnet/ccbluex/liquidbounce/features/module/Module;", "getPosX", "getPosY", "shadowColor", "stroll", "unusedColor", "usedColor", "getWidth", "click", "", "mouseX", "mouseY", "render", "renderTab", "wheel", "update", "LiquidBounce"}
)
public final class KeyInfo extends MinecraftInstance {

    private final int keyColor;
    private final int shadowColor;
    private final int unusedColor;
    private final int usedColor;
    private final int baseTabHeight;
    private final int baseTabWidth;
    private final boolean direction;
    private ArrayList modules;
    private ArrayList macros;
    private boolean hasKeyBind;
    private int stroll;
    private int maxStroll;
    private final float posX;
    private final float posY;
    private final float width;
    private final float height;
    private final int key;
    @NotNull
    private final String keyName;
    @NotNull
    private final String keyDisplayName;

    public final void render() {
        GL11.glPushMatrix();
        GL11.glTranslatef(this.posX, this.posY, 0.0F);
        RenderUtils.drawRoundedRect(0.0F, 0.0F, this.width, this.height, 10.0F, this.keyColor);
        (this.hasKeyBind ? Fonts.font40 : Fonts.font40).drawCenteredString(this.keyName, this.width * 0.5F, this.height * 0.9F * 0.5F - (float) Fonts.font35.getFontHeight() * 0.5F + 3.0F, this.hasKeyBind ? this.usedColor : this.unusedColor, false);
        GL11.glPopMatrix();
    }

    public final void renderTab() {
        GL11.glPushMatrix();
        GL11.glTranslatef(this.posX + this.width * 0.5F - (float) this.baseTabWidth * 0.5F, this.direction ? this.posY - (float) this.baseTabHeight : this.posY + this.height, 0.0F);
        float f = (float) this.baseTabWidth;
        float f1 = (float) this.baseTabHeight;
        Color color = Color.WHITE;

        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        RenderUtils.drawRect(0.0F, 0.0F, f, f1, color.getRGB());
        float fontHeight = 10.0F - (float) Fonts.font40.getFontHeight() * 0.5F;
        float yOffset = 12.0F + (float) Fonts.font40.getFontHeight() + 10.0F - (float) this.stroll;

        Iterator iterator;
        IFontRenderer ifontrenderer;
        String s;

        for (iterator = this.modules.iterator(); iterator.hasNext(); yOffset += (float) 20) {
            Module macro = (Module) iterator.next();

            if (yOffset > (float) 0 && yOffset - (float) 20 < (float) 100) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0F, yOffset, 0.0F);
                ifontrenderer = Fonts.font40;
                s = macro.getName();
                color = Color.DARK_GRAY;
                Intrinsics.checkExpressionValueIsNotNull(Color.DARK_GRAY, "Color.DARK_GRAY");
                ifontrenderer.drawString(s, 12.0F, fontHeight, color.getRGB(), false);
                ifontrenderer = Fonts.font40;
                f = (float) this.baseTabWidth - 12.0F - (float) Fonts.font40.getStringWidth("-");
                color = Color.RED;
                Intrinsics.checkExpressionValueIsNotNull(Color.RED, "Color.RED");
                ifontrenderer.drawString("-", f, fontHeight, color.getRGB(), false);
                GL11.glPopMatrix();
            }
        }

        for (iterator = this.macros.iterator(); iterator.hasNext(); yOffset += (float) 20) {
            Macro macro1 = (Macro) iterator.next();

            if (yOffset > (float) 0 && yOffset - (float) 20 < (float) 100) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0F, yOffset, 0.0F);
                ifontrenderer = Fonts.font40;
                s = macro1.getCommand();
                color = Color.DARK_GRAY;
                Intrinsics.checkExpressionValueIsNotNull(Color.DARK_GRAY, "Color.DARK_GRAY");
                ifontrenderer.drawString(s, 12.0F, fontHeight, color.getRGB(), false);
                ifontrenderer = Fonts.font40;
                f = (float) this.baseTabWidth - 12.0F - (float) Fonts.font40.getStringWidth("-");
                color = Color.RED;
                Intrinsics.checkExpressionValueIsNotNull(Color.RED, "Color.RED");
                ifontrenderer.drawString("-", f, fontHeight, color.getRGB(), false);
                GL11.glPopMatrix();
            }
        }

        f = (float) this.baseTabWidth;
        f1 = 12.0F + (float) Fonts.font40.getFontHeight() + 10.0F;
        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        RenderUtils.drawRect(0.0F, 0.0F, f, f1, color.getRGB());
        float f2 = (float) this.baseTabHeight - 22.0F - (float) Fonts.font40.getFontHeight();

        f = (float) this.baseTabWidth;
        f1 = (float) this.baseTabHeight;
        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        RenderUtils.drawRect(0.0F, f2, f, f1, color.getRGB());
        RenderUtils.drawShadowWithCustomAlpha(0.0F, 0.0F, (float) this.baseTabWidth, (float) this.baseTabHeight, 200.0F);
        ifontrenderer = Fonts.font40;
        color = Color.BLACK;
        Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
        ifontrenderer.drawString("Bind", 12.0F, 12.0F, color.getRGB(), false);
        Fonts.font40.drawString("Add", (float) this.baseTabWidth - 12.0F - (float) Fonts.font40.getStringWidth("Add"), (float) this.baseTabHeight - 12.0F - (float) Fonts.font40.getFontHeight(), (new Color(0, 191, 255)).getRGB(), false);
        GL11.glPopMatrix();
    }

    public final void stroll(float mouseX, float mouseY, int wheel) {
        float scaledMouseX = mouseX - (this.posX + this.width * 0.5F - (float) this.baseTabWidth * 0.5F);
        float scaledMouseY = mouseY - (this.direction ? this.posY - (float) this.baseTabHeight : this.posY + this.height);

        if (scaledMouseX >= (float) 0 && scaledMouseY >= (float) 0 && scaledMouseX <= (float) this.baseTabWidth && scaledMouseY <= (float) this.baseTabHeight) {
            int afterStroll = this.stroll - wheel / 40;

            if (afterStroll > 0 && afterStroll < this.maxStroll - 150) {
                this.stroll = afterStroll;
            }

        }
    }

    public final void update() {
        List list = LiquidBounce.INSTANCE.getModuleManager().getKeyBind(this.key);

        if (list == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<net.ccbluex.liquidbounce.features.module.Module> /* = java.util.ArrayList<net.ccbluex.liquidbounce.features.module.Module> */");
        } else {
            this.modules = (ArrayList) list;
            Iterable $this$filter$iv = (Iterable) LiquidBounce.INSTANCE.getMacroManager().getMacros();
            boolean $i$f$filter = false;
            Collection destination$iv$iv = (Collection) (new ArrayList());
            boolean $i$f$filterTo = false;
            Iterator iterator = $this$filter$iv.iterator();

            while (iterator.hasNext()) {
                Object element$iv$iv = iterator.next();
                Macro it = (Macro) element$iv$iv;
                boolean $i$a$-filter-KeyInfo$update$1 = false;

                if (it.getKey() == this.key) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }

            List list1 = (List) destination$iv$iv;

            this.macros = (ArrayList) list1;
            this.hasKeyBind = this.modules.size() + this.macros.size() > 0;
            this.stroll = 0;
            this.maxStroll = this.modules.size() * 30 + this.macros.size() * 30;
        }
    }

    public final void click(float mouseX, float mouseY) {
        KeyBindManager keyBindMgr = LiquidBounce.INSTANCE.getKeyBindManager();

        if (keyBindMgr.getNowDisplayKey() == null) {
            keyBindMgr.setNowDisplayKey((KeyInfo) this);
        } else {
            float scaledMouseX = mouseX - (this.posX + this.width * 0.5F - (float) this.baseTabWidth * 0.5F);
            float scaledMouseY = mouseY - (this.direction ? this.posY - (float) this.baseTabHeight : this.posY + this.height);

            if (scaledMouseX < (float) 0 || scaledMouseY < (float) 0 || scaledMouseX > (float) this.baseTabWidth || scaledMouseY > (float) this.baseTabHeight) {
                keyBindMgr.setNowDisplayKey((KeyInfo) null);
                return;
            }

            if (scaledMouseY > 22.0F + (float) Fonts.font40.getFontHeight() && scaledMouseX > (float) this.baseTabWidth - 12.0F - (float) Fonts.font40.getStringWidth("%ui.keybind.add%")) {
                if (scaledMouseY > (float) this.baseTabHeight - 22.0F - (float) Fonts.font40.getFontHeight()) {
                    keyBindMgr.setPopUI((PopUI) (new KeySelectUI(this)));
                } else {
                    float yOffset = 12.0F + (float) Fonts.font40.getFontHeight() + 10.0F - (float) this.stroll;

                    Iterator iterator;

                    for (iterator = this.modules.iterator(); iterator.hasNext(); yOffset += (float) 20) {
                        Module macro = (Module) iterator.next();

                        if (scaledMouseY > yOffset + (float) 5 && scaledMouseY < yOffset + (float) 15) {
                            macro.setKeyBind(0);
                            this.update();
                            break;
                        }
                    }

                    for (iterator = this.macros.iterator(); iterator.hasNext(); yOffset += (float) 20) {
                        Macro macro1 = (Macro) iterator.next();

                        if (scaledMouseY > yOffset + (float) 5 && scaledMouseY < yOffset + (float) 15) {
                            LiquidBounce.INSTANCE.getMacroManager().getMacros().remove(macro1);
                            this.update();
                            break;
                        }
                    }
                }
            }
        }

    }

    public final float getPosX() {
        return this.posX;
    }

    public final float getPosY() {
        return this.posY;
    }

    public final float getWidth() {
        return this.width;
    }

    public final float getHeight() {
        return this.height;
    }

    public final int getKey() {
        return this.key;
    }

    @NotNull
    public final String getKeyName() {
        return this.keyName;
    }

    @NotNull
    public final String getKeyDisplayName() {
        return this.keyDisplayName;
    }

    public KeyInfo(float posX, float posY, float width, float height, int key, @NotNull String keyName, @NotNull String keyDisplayName) {
        Intrinsics.checkParameterIsNotNull(keyName, "keyName");
        Intrinsics.checkParameterIsNotNull(keyDisplayName, "keyDisplayName");
        super();
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.key = key;
        this.keyName = keyName;
        this.keyDisplayName = keyDisplayName;
        this.keyColor = (new Color(240, 240, 240)).getRGB();
        this.shadowColor = (new Color(210, 210, 210)).getRGB();
        this.unusedColor = (new Color(200, 200, 200)).getRGB();
        this.usedColor = (new Color(0, 0, 0)).getRGB();
        this.baseTabHeight = 150;
        this.baseTabWidth = 100;
        this.direction = this.posY >= (float) 100;
        this.modules = new ArrayList();
        this.macros = new ArrayList();
    }

    public KeyInfo(float posX, float posY, float width, float height, int key, @NotNull String keyName) {
        Intrinsics.checkParameterIsNotNull(keyName, "keyName");
        this(posX, posY, width, height, key, keyName, keyName);
    }
}
