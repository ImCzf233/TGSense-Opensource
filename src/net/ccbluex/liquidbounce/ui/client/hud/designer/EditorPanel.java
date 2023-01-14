package net.ccbluex.liquidbounce.ui.client.hud.designer;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.ui.client.hud.HUD;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0010\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u0018\u0010%\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u0018\u0010&\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u001e\u0010\'\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005J\u0018\u0010)\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u000e\u0010\u001a\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0016\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0016\"\u0004\b!\u0010\u001f¨\u0006*"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "hudDesigner", "Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;", "x", "", "y", "(Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;II)V", "create", "", "getCreate", "()Z", "setCreate", "(Z)V", "currentElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "drag", "dragX", "dragY", "<set-?>", "height", "getHeight", "()I", "mouseDown", "realHeight", "getRealHeight", "scroll", "width", "getWidth", "getX", "setX", "(I)V", "getY", "setY", "", "mouseX", "mouseY", "drawCreate", "drawEditor", "drawPanel", "wheel", "drawSelection", "LiquidBounce"}
)
public final class EditorPanel extends MinecraftInstance {

    private int width;
    private int height;
    private int realHeight;
    private boolean drag;
    private int dragX;
    private int dragY;
    private boolean mouseDown;
    private int scroll;
    private boolean create;
    private Element currentElement;
    private final GuiHudDesigner hudDesigner;
    private int x;
    private int y;

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getRealHeight() {
        return this.realHeight;
    }

    public final boolean getCreate() {
        return this.create;
    }

    public final void setCreate(boolean <set-?>) {
        this.create = <set-?>;
    }

    public final void drawPanel(int mouseX, int mouseY, int wheel) {
        this.drag(mouseX, mouseY);
        if (Intrinsics.areEqual(this.currentElement, this.hudDesigner.getSelectedElement()) ^ true) {
            this.scroll = 0;
        }

        this.currentElement = this.hudDesigner.getSelectedElement();
        int currMouseY = mouseY;
        boolean shouldScroll = this.realHeight > 200;

        if (shouldScroll) {
            GL11.glPushMatrix();
            RenderUtils.makeScissorBox((float) this.x, (float) this.y + 1.0F, (float) this.x + (float) this.width, (float) this.y + 200.0F);
            GL11.glEnable(3089);
            if (this.y + 200 < mouseY) {
                currMouseY = -1;
            }

            if (mouseX >= this.x && mouseX <= this.x + this.width && currMouseY >= this.y && currMouseY <= this.y + 200 && Mouse.hasWheel()) {
                if (wheel < 0 && -this.scroll + 205 <= this.realHeight) {
                    this.scroll -= 12;
                } else if (wheel > 0) {
                    this.scroll += 12;
                    if (this.scroll > 0) {
                        this.scroll = 0;
                    }
                }
            }
        }

        RenderUtils.drawRect(this.x, this.y + 12, this.x + this.width, this.y + this.realHeight, (new Color(27, 34, 40)).getRGB());
        if (this.create) {
            this.drawCreate(mouseX, currMouseY);
        } else if (this.currentElement != null) {
            this.drawEditor(mouseX, currMouseY);
        } else {
            this.drawSelection(mouseX, currMouseY);
        }

        if (shouldScroll) {
            RenderUtils.drawRect(this.x + this.width - 5, this.y + 15, this.x + this.width - 2, this.y + 197, (new Color(41, 41, 41)).getRGB());
            float v = (float) 197 * ((float) (-this.scroll) / ((float) this.realHeight - 170.0F));

            RenderUtils.drawRect((float) (this.x + this.width) - 5.0F, (float) (this.y + 15) + v, (float) (this.x + this.width) - 2.0F, (float) (this.y + 20) + v, (new Color(37, 126, 255)).getRGB());
            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }

        this.mouseDown = Mouse.isButtonDown(0);
    }

    private final void drawCreate(int mouseX, int mouseY) {
        this.height = 15 + this.scroll;
        this.realHeight = 15;
        this.width = 90;
        Class[] aclass = HUD.Companion.getElements();
        int i = aclass.length;

        IFontRenderer ifontrenderer;
        float f;
        float f1;
        Color color;

        for (int j = 0; j < i; ++j) {
            Class element = aclass[j];
            ElementInfo elementinfo = (ElementInfo) element.getAnnotation(ElementInfo.class);

            if (elementinfo != null) {
                ElementInfo info = elementinfo;

                if (info.single()) {
                    Iterable name = (Iterable) LiquidBounce.INSTANCE.getHud().getElements();
                    boolean stringWidth = false;
                    boolean flag;

                    if (name instanceof Collection && ((Collection) name).isEmpty()) {
                        flag = false;
                    } else {
                        Iterator e = name.iterator();

                        while (true) {
                            if (!e.hasNext()) {
                                flag = false;
                                break;
                            }

                            Object element$iv = e.next();
                            Element it = (Element) element$iv;
                            boolean $i$a$-any-EditorPanel$drawCreate$1 = false;

                            if (Intrinsics.areEqual(it.getClass(), element)) {
                                flag = true;
                                break;
                            }
                        }
                    }

                    if (flag) {
                        continue;
                    }
                }

                String s = info.name();

                ifontrenderer = Fonts.font35;
                f = (float) this.x + 2.0F;
                f1 = (float) this.y + (float) this.height;
                color = Color.WHITE;
                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                ifontrenderer.drawString(s, f, f1, color.getRGB());
                int k = Fonts.font35.getStringWidth(s);

                if (this.width < k + 8) {
                    this.width = k + 8;
                }

                if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + this.height && mouseY <= this.y + this.height + 10) {
                    try {
                        Element element = (Element) element.newInstance();

                        if (element.createElement()) {
                            HUD hud = LiquidBounce.INSTANCE.getHud();

                            Intrinsics.checkExpressionValueIsNotNull(element, "newElement");
                            hud.addElement(element);
                        }
                    } catch (InstantiationException instantiationexception) {
                        instantiationexception.printStackTrace();
                    } catch (IllegalAccessException illegalaccessexception) {
                        illegalaccessexception.printStackTrace();
                    }

                    this.create = false;
                }

                this.height += 10;
                this.realHeight += 10;
            }
        }

        int l = this.x;
        int i1 = this.y;
        int j1 = this.x + this.width;
        int k1 = this.y + 12;

        color = ClickGUI.generateColor();
        Intrinsics.checkExpressionValueIsNotNull(color, "ClickGUI.generateColor()");
        RenderUtils.drawRect(l, i1, j1, k1, color.getRGB());
        ifontrenderer = Fonts.font35;
        f = (float) this.x + 2.0F;
        f1 = (float) this.y + 3.5F;
        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        ifontrenderer.drawString("§lCreate element", f, f1, color.getRGB());
    }

    private final void drawSelection(int mouseX, int mouseY) {
        this.height = 15 + this.scroll;
        this.realHeight = 15;
        this.width = 120;
        IFontRenderer ifontrenderer = Fonts.font35;
        float f = (float) this.x + 2.0F;
        float f1 = (float) this.y + (float) this.height;
        Color color = Color.WHITE;

        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        ifontrenderer.drawString("§lCreate element", f, f1, color.getRGB());
        if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + this.height && mouseY <= this.y + this.height + 10) {
            this.create = true;
        }

        this.height += 10;
        this.realHeight += 10;
        ifontrenderer = Fonts.font35;
        f = (float) this.x + (float) 2;
        f1 = (float) this.y + (float) this.height;
        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        ifontrenderer.drawString("§lReset", f, f1, color.getRGB());
        if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + this.height && mouseY <= this.y + this.height + 10) {
            LiquidBounce.INSTANCE.setHud(HUD.Companion.createDefault());
        }

        this.height += 15;
        this.realHeight += 15;
        ifontrenderer = Fonts.font35;
        f = (float) this.x + 2.0F;
        f1 = (float) this.y + (float) this.height;
        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        ifontrenderer.drawString("§lAvailable Elements", f, f1, color.getRGB());
        this.height += 10;
        this.realHeight += 10;

        int i;
        int j;

        for (Iterator iterator = LiquidBounce.INSTANCE.getHud().getElements().iterator(); iterator.hasNext(); this.realHeight += 10) {
            Element element = (Element) iterator.next();

            ifontrenderer = Fonts.font35;
            String s = element.getName();

            i = this.x + 2;
            j = this.y + this.height;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s, i, j, color.getRGB());
            int stringWidth = Fonts.font35.getStringWidth(element.getName());

            if (this.width < stringWidth + 8) {
                this.width = stringWidth + 8;
            }

            if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + this.height && mouseY <= this.y + this.height + 10) {
                this.hudDesigner.setSelectedElement(element);
            }

            this.height += 10;
        }

        int k = this.x;
        int l = this.y;

        i = this.x + this.width;
        j = this.y + 12;
        color = ClickGUI.generateColor();
        Intrinsics.checkExpressionValueIsNotNull(color, "ClickGUI.generateColor()");
        RenderUtils.drawRect(k, l, i, j, color.getRGB());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ifontrenderer = Fonts.font35;
        f = (float) this.x + 2.0F;
        f1 = (float) this.y + 3.5F;
        color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
        ifontrenderer.drawString("§lEditor", f, f1, color.getRGB());
    }

    private final void drawEditor(int mouseX, int mouseY) {
        this.height = this.scroll + 15;
        this.realHeight = 15;
        int prevWidth = this.width;

        this.width = 100;
        Element element = this.currentElement;

        if (this.currentElement != null) {
            Element element = element;
            IFontRenderer ifontrenderer = Fonts.font35;
            StringBuilder stringbuilder = (new StringBuilder()).append("X: ");
            String deleteWidth = "%.2f";
            Object[] currIndex = new Object[] { Double.valueOf(element.getRenderX())};
            StringBuilder stringbuilder1 = stringbuilder;
            IFontRenderer ifontrenderer1 = ifontrenderer;
            boolean y = false;
            String s = String.format(deleteWidth, Arrays.copyOf(currIndex, currIndex.length));

            Intrinsics.checkExpressionValueIsNotNull(s, "java.lang.String.format(this, *args)");
            String s1 = s;

            stringbuilder = stringbuilder1.append(s1).append(" (");
            deleteWidth = "%.2f";
            currIndex = new Object[] { Double.valueOf(element.getX())};
            stringbuilder1 = stringbuilder;
            y = false;
            s = String.format(deleteWidth, Arrays.copyOf(currIndex, currIndex.length));
            Intrinsics.checkExpressionValueIsNotNull(s, "java.lang.String.format(this, *args)");
            s1 = s;
            String s2 = stringbuilder1.append(s1).append(')').toString();
            int i = this.x + 2;
            int j = this.y + this.height;
            Color color = Color.WHITE;

            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer1.drawString(s2, i, j, color.getRGB());
            this.height += 10;
            this.realHeight += 10;
            ifontrenderer = Fonts.font35;
            stringbuilder = (new StringBuilder()).append("Y: ");
            deleteWidth = "%.2f";
            currIndex = new Object[] { Double.valueOf(element.getRenderY())};
            stringbuilder1 = stringbuilder;
            ifontrenderer1 = ifontrenderer;
            y = false;
            s = String.format(deleteWidth, Arrays.copyOf(currIndex, currIndex.length));
            Intrinsics.checkExpressionValueIsNotNull(s, "java.lang.String.format(this, *args)");
            s1 = s;
            stringbuilder = stringbuilder1.append(s1).append(" (");
            deleteWidth = "%.2f";
            currIndex = new Object[] { Double.valueOf(element.getY())};
            stringbuilder1 = stringbuilder;
            y = false;
            s = String.format(deleteWidth, Arrays.copyOf(currIndex, currIndex.length));
            Intrinsics.checkExpressionValueIsNotNull(s, "java.lang.String.format(this, *args)");
            s1 = s;
            s2 = stringbuilder1.append(s1).append(')').toString();
            i = this.x + 2;
            j = this.y + this.height;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer1.drawString(s2, i, j, color.getRGB());
            this.height += 10;
            this.realHeight += 10;
            ifontrenderer = Fonts.font35;
            stringbuilder = (new StringBuilder()).append("Scale: ");
            deleteWidth = "%.2f";
            currIndex = new Object[] { Float.valueOf(element.getScale())};
            stringbuilder1 = stringbuilder;
            ifontrenderer1 = ifontrenderer;
            y = false;
            s = String.format(deleteWidth, Arrays.copyOf(currIndex, currIndex.length));
            Intrinsics.checkExpressionValueIsNotNull(s, "java.lang.String.format(this, *args)");
            s1 = s;
            s2 = stringbuilder1.append(s1).toString();
            i = this.x + 2;
            j = this.y + this.height;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer1.drawString(s2, i, j, color.getRGB());
            this.height += 10;
            this.realHeight += 10;
            ifontrenderer = Fonts.font35;
            i = this.x + 2;
            j = this.y + this.height;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString("H:", i, j, color.getRGB());
            ifontrenderer = Fonts.font35;
            s2 = element.getSide().getHorizontal().getSideName();
            i = this.x + 12;
            j = this.y + this.height;
            color = Color.GRAY;
            Intrinsics.checkExpressionValueIsNotNull(Color.GRAY, "Color.GRAY");
            ifontrenderer.drawString(s2, i, j, color.getRGB());
            int k;
            double d0;
            IClassProvider iclassprovider;
            double d1;
            IMinecraft iminecraft;

            if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + this.height && mouseY <= this.y + this.height + 10) {
                Side.Horizontal[] aside_horizontal = Side.Horizontal.values();

                k = ArraysKt.indexOf(aside_horizontal, element.getSide().getHorizontal());
                d0 = element.getRenderX();
                element.getSide().setHorizontal(aside_horizontal[k + 1 >= aside_horizontal.length ? 0 : k + 1]);
                switch (EditorPanel$WhenMappings.$EnumSwitchMapping$0[element.getSide().getHorizontal().ordinal()]) {
                case 1:
                    d1 = d0;
                    break;

                case 2:
                    iclassprovider = MinecraftInstance.classProvider;
                    iminecraft = MinecraftInstance.mc;
                    Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
                    d1 = (double) (iclassprovider.createScaledResolution(iminecraft).getScaledWidth() / 2) - d0;
                    break;

                case 3:
                    iclassprovider = MinecraftInstance.classProvider;
                    iminecraft = MinecraftInstance.mc;
                    Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
                    d1 = (double) iclassprovider.createScaledResolution(iminecraft).getScaledWidth() - d0;
                    break;

                default:
                    throw new NoWhenBranchMatchedException();
                }

                element.setX(d1);
            }

            this.height += 10;
            this.realHeight += 10;
            ifontrenderer = Fonts.font35;
            i = this.x + 2;
            j = this.y + this.height;
            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString("V:", i, j, color.getRGB());
            ifontrenderer = Fonts.font35;
            s2 = element.getSide().getVertical().getSideName();
            i = this.x + 12;
            j = this.y + this.height;
            color = Color.GRAY;
            Intrinsics.checkExpressionValueIsNotNull(Color.GRAY, "Color.GRAY");
            ifontrenderer.drawString(s2, i, j, color.getRGB());
            if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + this.height && mouseY <= this.y + this.height + 10) {
                Side.Vertical[] aside_vertical = Side.Vertical.values();

                k = ArraysKt.indexOf(aside_vertical, element.getSide().getVertical());
                d0 = element.getRenderY();
                element.getSide().setVertical(aside_vertical[k + 1 >= aside_vertical.length ? 0 : k + 1]);
                switch (EditorPanel$WhenMappings.$EnumSwitchMapping$1[element.getSide().getVertical().ordinal()]) {
                case 1:
                    d1 = d0;
                    break;

                case 2:
                    iclassprovider = MinecraftInstance.classProvider;
                    iminecraft = MinecraftInstance.mc;
                    Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
                    d1 = (double) (iclassprovider.createScaledResolution(iminecraft).getScaledHeight() / 2) - d0;
                    break;

                case 3:
                    iclassprovider = MinecraftInstance.classProvider;
                    iminecraft = MinecraftInstance.mc;
                    Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
                    d1 = (double) iclassprovider.createScaledResolution(iminecraft).getScaledHeight() - d0;
                    break;

                default:
                    throw new NoWhenBranchMatchedException();
                }

                element.setY(d1);
            }

            this.height += 10;
            this.realHeight += 10;
            Iterator iterator = element.getValues().iterator();

            while (iterator.hasNext()) {
                Value value = (Value) iterator.next();
                int l;
                int i1;

                if (value instanceof BoolValue) {
                    ifontrenderer = Fonts.font35;
                    s2 = value.getName();
                    i = this.x + 2;
                    j = this.y + this.height;
                    if (((Boolean) ((BoolValue) value).get()).booleanValue()) {
                        color = Color.WHITE;
                        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                        i1 = color.getRGB();
                    } else {
                        color = Color.GRAY;
                        Intrinsics.checkExpressionValueIsNotNull(Color.GRAY, "Color.GRAY");
                        i1 = color.getRGB();
                    }

                    ifontrenderer.drawString(s2, i, j, i1);
                    l = Fonts.font35.getStringWidth(value.getName());
                    if (this.width < l + 8) {
                        this.width = l + 8;
                    }

                    if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + this.height && mouseY <= this.y + this.height + 10) {
                        ((BoolValue) value).set(Boolean.valueOf(!((Boolean) ((BoolValue) value).get()).booleanValue()));
                    }

                    this.height += 10;
                    this.realHeight += 10;
                } else {
                    boolean $i$f$clamp_float;
                    String s3;
                    String s4;
                    int j1;
                    float f;
                    float f1;
                    float f2;
                    float f3;
                    float f4;

                    if (value instanceof FloatValue) {
                        float f5 = ((Number) ((FloatValue) value).get()).floatValue();
                        float f6 = ((FloatValue) value).getMinimum();
                        float f7 = ((FloatValue) value).getMaximum();
                        StringBuilder stringbuilder2 = (new StringBuilder()).append(value.getName()).append(": §c");

                        s4 = "%.2f";
                        Object[] aobject = new Object[] { Float.valueOf(f5)};
                        StringBuilder stringbuilder3 = stringbuilder2;
                        boolean flag = false;

                        s = String.format(s4, Arrays.copyOf(aobject, aobject.length));
                        Intrinsics.checkExpressionValueIsNotNull(s, "java.lang.String.format(this, *args)");
                        String s5 = s;

                        s3 = stringbuilder3.append(s5).toString();
                        ifontrenderer = Fonts.font35;
                        i = this.x + 2;
                        j = this.y + this.height;
                        color = Color.WHITE;
                        Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                        ifontrenderer.drawString(s3, i, j, color.getRGB());
                        j1 = Fonts.font35.getStringWidth(s3);
                        if (this.width < j1 + 8) {
                            this.width = j1 + 8;
                        }

                        RenderUtils.drawRect((float) this.x + 8.0F, (float) (this.y + this.height) + 12.0F, (float) (this.x + prevWidth) - 8.0F, (float) (this.y + this.height) + 13.0F, Color.WHITE);
                        f = (float) this.x + ((float) prevWidth - 18.0F) * (f5 - f6) / (f7 - f6);
                        RenderUtils.drawRect(8.0F + f, (float) (this.y + this.height) + 9.0F, f + 11.0F, (float) (this.y + this.height) + 15.0F, (new Color(37, 126, 255)).getRGB());
                        if (mouseX >= this.x + 8 && mouseX <= this.x + prevWidth && mouseY >= this.y + this.height + 9 && mouseY <= this.y + this.height + 15 && Mouse.isButtonDown(0)) {
                            f2 = ((float) (mouseX - this.x) - 8.0F) / ((float) prevWidth - 18.0F);
                            f3 = 0.0F;
                            f4 = 1.0F;
                            $i$f$clamp_float = false;
                            f1 = f2 < f3 ? f3 : (f2 > f4 ? f4 : f2);
                            ((FloatValue) value).set((Object) Float.valueOf(f6 + (f7 - f6) * f1));
                        }

                        this.height += 20;
                        this.realHeight += 20;
                    } else {
                        int stringWidth;
                        int k1;

                        if (value instanceof IntegerValue) {
                            l = ((Number) ((IntegerValue) value).get()).intValue();
                            k1 = ((IntegerValue) value).getMinimum();
                            stringWidth = ((IntegerValue) value).getMaximum();
                            s3 = value.getName() + ": §c" + l;
                            ifontrenderer = Fonts.font35;
                            i = this.x + 2;
                            j = this.y + this.height;
                            color = Color.WHITE;
                            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                            ifontrenderer.drawString(s3, i, j, color.getRGB());
                            j1 = Fonts.font35.getStringWidth(s3);
                            if (this.width < j1 + 8) {
                                this.width = j1 + 8;
                            }

                            RenderUtils.drawRect((float) this.x + 8.0F, (float) (this.y + this.height) + 12.0F, (float) (this.x + prevWidth) - 8.0F, (float) (this.y + this.height) + 13.0F, Color.WHITE);
                            f = (float) this.x + ((float) prevWidth - 18.0F) * (float) (l - k1) / (float) (stringWidth - k1);
                            RenderUtils.drawRect(8.0F + f, (float) (this.y + this.height) + 9.0F, f + 11.0F, (float) (this.y + this.height) + 15.0F, (new Color(37, 126, 255)).getRGB());
                            if (mouseX >= this.x + 8 && mouseX <= this.x + prevWidth && mouseY >= this.y + this.height + 9 && mouseY <= this.y + this.height + 15 && Mouse.isButtonDown(0)) {
                                f2 = ((float) (mouseX - this.x) - 8.0F) / ((float) prevWidth - 18.0F);
                                f3 = 0.0F;
                                f4 = 1.0F;
                                $i$f$clamp_float = false;
                                f1 = f2 < f3 ? f3 : (f2 > f4 ? f4 : f2);
                                ((IntegerValue) value).set((Object) Integer.valueOf((int) ((float) k1 + (float) (stringWidth - k1) * f1)));
                            }

                            this.height += 20;
                            this.realHeight += 20;
                        } else if (value instanceof ListValue) {
                            ifontrenderer = Fonts.font35;
                            s2 = value.getName();
                            i = this.x + 2;
                            j = this.y + this.height;
                            color = Color.WHITE;
                            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                            ifontrenderer.drawString(s2, i, j, color.getRGB());
                            this.height += 10;
                            this.realHeight += 10;
                            String[] astring = ((ListValue) value).getValues();
                            int l1 = astring.length;

                            for (k1 = 0; k1 < l1; ++k1) {
                                String s6 = astring[k1];

                                s4 = "§c> §r" + s6;
                                ifontrenderer = Fonts.font35;
                                i = this.x + 2;
                                j = this.y + this.height;
                                if (Intrinsics.areEqual(s6, (String) ((ListValue) value).get())) {
                                    color = Color.WHITE;
                                    Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                                    i1 = color.getRGB();
                                } else {
                                    color = Color.GRAY;
                                    Intrinsics.checkExpressionValueIsNotNull(Color.GRAY, "Color.GRAY");
                                    i1 = color.getRGB();
                                }

                                ifontrenderer.drawString(s4, i, j, i1);
                                int i2 = Fonts.font35.getStringWidth(s4);

                                if (this.width < i2 + 8) {
                                    this.width = i2 + 8;
                                }

                                if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + this.height && mouseY <= this.y + this.height + 10) {
                                    ((ListValue) value).set(s6);
                                }

                                this.height += 10;
                                this.realHeight += 10;
                            }
                        } else if (value instanceof FontValue) {
                            IFontRenderer fontRenderer = (IFontRenderer) ((FontValue) value).get();
                            String text = fontRenderer.isGameFontRenderer() ? "Font: " + fontRenderer.getGameFontRenderer().getDefaultFont().getFont().getName() + " - " + fontRenderer.getGameFontRenderer().getDefaultFont().getFont().getSize() : (Intrinsics.areEqual(fontRenderer, Fonts.minecraftFont) ? "Font: Minecraft" : "Font: Unknown");

                            ifontrenderer = Fonts.font35;
                            i = this.x + 2;
                            j = this.y + this.height;
                            color = Color.WHITE;
                            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                            ifontrenderer.drawString(text, i, j, color.getRGB());
                            stringWidth = Fonts.font35.getStringWidth(text);
                            if (this.width < stringWidth + 8) {
                                this.width = stringWidth + 8;
                            }

                            if (Mouse.isButtonDown(0) && !this.mouseDown && mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + this.height && mouseY <= this.y + this.height + 10) {
                                List fonts = Fonts.getFonts();

                                Intrinsics.checkExpressionValueIsNotNull(fonts, "fonts");
                                Iterable $this$forEachIndexed$iv = (Iterable) fonts;
                                boolean $i$f$forEachIndexed = false;
                                int index$iv = 0;
                                Iterator num$iv = $this$forEachIndexed$iv.iterator();

                                while (num$iv.hasNext()) {
                                    Object item$iv = num$iv.next();
                                    int max$iv = index$iv++;

                                    $i$f$clamp_float = false;
                                    if (max$iv < 0) {
                                        CollectionsKt.throwIndexOverflow();
                                    }

                                    IFontRenderer font = (IFontRenderer) item$iv;
                                    boolean $i$a$-forEachIndexed-EditorPanel$drawEditor$1 = false;

                                    if (Intrinsics.areEqual(font, fontRenderer)) {
                                        FontValue fontvalue = (FontValue) value;
                                        Object object = fonts.get(max$iv + 1 >= fonts.size() ? 0 : max$iv + 1);

                                        Intrinsics.checkExpressionValueIsNotNull(object, "fonts[if (index + 1 >= f…s.size) 0 else index + 1]");
                                        fontvalue.set(object);
                                    }
                                }
                            }

                            this.height += 10;
                            this.realHeight += 10;
                        }
                    }
                }
            }

            int j2 = this.x;
            int k2 = this.y;

            i = this.x + this.width;
            j = this.y + 12;
            color = ClickGUI.generateColor();
            Intrinsics.checkExpressionValueIsNotNull(color, "ClickGUI.generateColor()");
            RenderUtils.drawRect(j2, k2, i, j, color.getRGB());
            ifontrenderer = Fonts.font35;
            s2 = "§l" + element.getName();
            float f8 = (float) this.x + 2.0F;
            float f9 = (float) this.y + 3.5F;

            color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(s2, f8, f9, color.getRGB());
            if (!element.getInfo().force()) {
                float f10 = (float) (this.x + this.width - Fonts.font35.getStringWidth("§lDelete")) - 2.0F;

                ifontrenderer = Fonts.font35;
                f9 = (float) this.y + 3.5F;
                color = Color.WHITE;
                Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
                ifontrenderer.drawString("§lDelete", f10, f9, color.getRGB());
                if (Mouse.isButtonDown(0) && !this.mouseDown && (float) mouseX >= f10 && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 10) {
                    LiquidBounce.INSTANCE.getHud().removeElement(element);
                }
            }

        }
    }

    private final void drag(int mouseX, int mouseY) {
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 12 && Mouse.isButtonDown(0) && !this.mouseDown) {
            this.drag = true;
            this.dragX = mouseX - this.x;
            this.dragY = mouseY - this.y;
        }

        if (Mouse.isButtonDown(0) && this.drag) {
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        } else {
            this.drag = false;
        }

    }

    public final int getX() {
        return this.x;
    }

    public final void setX(int <set-?>) {
        this.x = <set-?>;
    }

    public final int getY() {
        return this.y;
    }

    public final void setY(int <set-?>) {
        this.y = <set-?>;
    }

    public EditorPanel(@NotNull GuiHudDesigner hudDesigner, int x, int y) {
        Intrinsics.checkParameterIsNotNull(hudDesigner, "hudDesigner");
        super();
        this.hudDesigner = hudDesigner;
        this.x = x;
        this.y = y;
        this.width = 80;
        this.height = 20;
        this.realHeight = 20;
    }
}
