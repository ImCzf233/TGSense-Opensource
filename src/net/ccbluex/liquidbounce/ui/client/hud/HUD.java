package net.ccbluex.liquidbounce.ui.client.hud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import me.Skid.ui.elements.ArmorHud;
import me.Skid.ui.elements.CPURender;
import me.Skid.ui.elements.HealthHud;
import me.Skid.ui.elements.HotBar;
import me.Skid.ui.elements.HurtTimeHud;
import me.Skid.ui.elements.KeyBinds;
import me.Skid.ui.elements.KeyStrokes;
import me.Skid.ui.elements.LBplusTarget;
import me.Skid.ui.elements.MemoryHud;
import me.Skid.ui.elements.MemoryUi;
import me.Skid.ui.elements.TimerHud;
import me.Skid.ui.elements.WorldInfo;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Armor;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Arraylist;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Arraylist2;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Effects;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.GameInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Image;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Inventory;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Model;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NewEffects;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notifications;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Radar;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.ScoreboardElement;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.SessionInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.SessionInformation;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Text;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Text2;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Text3;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0000\n\u0002\u0010\b\n\u0002\b\r\b\u0016\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\nJ\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001e\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0017J\u0016\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u0017J\u0006\u0010\u001d\u001a\u00020\u0012J\u000e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\nJ\u000e\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u000fJ\u0006\u0010\"\u001a\u00020\u0012R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\b\b\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007¨\u0006$"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "elements", "", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "getElements", "()Ljava/util/List;", "elements$1", "notifications", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "getNotifications", "addElement", "element", "addNotification", "", "notification", "clearElements", "", "handleKey", "c", "", "keyCode", "", "handleMouseClick", "mouseX", "mouseY", "button", "handleMouseMove", "handleMouseReleased", "removeElement", "removeNotification", "render", "designer", "update", "Companion", "LiquidBounce"}
)
public class HUD extends MinecraftInstance {

    @NotNull
    private final List elements$1;
    @NotNull
    private final List notifications;
    @NotNull
    private static final Class[] elements = new Class[] { Armor.class, Arraylist.class, Effects.class, SessionInformation.class, Image.class, Model.class, NewEffects.class, Notifications.class, ScoreboardElement.class, KeyBinds.class, LBplusTarget.class, GameInfo.class, Radar.class, Text.class, Text2.class, Text3.class, HealthHud.class, MemoryUi.class, KeyStrokes.class, WorldInfo.class, ArmorHud.class, Arraylist2.class, TimerHud.class, HurtTimeHud.class, MemoryHud.class, CPURender.class, HotBar.class, SessionInfo.class, Inventory.class};
    public static final HUD.Companion Companion = new HUD.Companion((DefaultConstructorMarker) null);

    @NotNull
    public final List getElements() {
        return this.elements$1;
    }

    @NotNull
    public final List getNotifications() {
        return this.notifications;
    }

    public final void render(boolean designer) {
        Iterable $this$forEach$iv = (Iterable) this.elements$1;
        boolean $i$f$forEach = false;
        boolean element$iv = false;
        Comparator it = (Comparator) (new HUD$render$$inlined$sortedBy$1());

        $this$forEach$iv = (Iterable) CollectionsKt.sortedWith($this$forEach$iv, it);
        $i$f$forEach = false;

        for (Iterator iterator = $this$forEach$iv.iterator(); iterator.hasNext(); GL11.glPopMatrix()) {
            Object element$iv1 = iterator.next();
            Element it1 = (Element) element$iv1;
            boolean $i$a$-forEach-HUD$render$2 = false;

            GL11.glPushMatrix();
            if (!it1.getInfo().disableScale()) {
                GL11.glScalef(it1.getScale(), it1.getScale(), it1.getScale());
            }

            GL11.glTranslated(it1.getRenderX(), it1.getRenderY(), 0.0D);

            try {
                it1.setBorder(it1.drawElement());
                if (designer) {
                    Border border = it1.getBorder();

                    if (border != null) {
                        border.draw();
                    }
                }
            } catch (Exception exception) {
                ClientUtils.getLogger().error("Something went wrong while drawing " + it1.getName() + " element in HUD.", (Throwable) exception);
            }
        }

    }

    public final void update() {
        Iterator iterator = this.elements$1.iterator();

        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();

            element.updateElement();
        }

    }

    public final void handleMouseClick(int mouseX, int mouseY, int button) {
        Iterator iterator = this.elements$1.iterator();

        Element element;

        while (iterator.hasNext()) {
            element = (Element) iterator.next();
            element.handleMouseClick((double) ((float) mouseX / element.getScale()) - element.getRenderX(), (double) ((float) mouseY / element.getScale()) - element.getRenderY(), button);
        }

        if (button == 0) {
            iterator = CollectionsKt.reversed((Iterable) this.elements$1).iterator();

            while (iterator.hasNext()) {
                element = (Element) iterator.next();
                if (element.isInBorder((double) ((float) mouseX / element.getScale()) - element.getRenderX(), (double) ((float) mouseY / element.getScale()) - element.getRenderY())) {
                    element.setDrag(true);
                    this.elements$1.remove(element);
                    this.elements$1.add(element);
                    break;
                }
            }
        }

    }

    public final void handleMouseReleased() {
        Iterator iterator = this.elements$1.iterator();

        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();

            element.setDrag(false);
        }

    }

    public final void handleMouseMove(int mouseX, int mouseY) {
        if (MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.mc.getCurrentScreen())) {
            IClassProvider iclassprovider = MinecraftInstance.classProvider;
            IMinecraft iminecraft = MinecraftInstance.mc;

            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
            IScaledResolution scaledResolution = iclassprovider.createScaledResolution(iminecraft);
            Iterator iterator = this.elements$1.iterator();

            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                float scaledX = (float) mouseX / element.getScale();
                float scaledY = (float) mouseY / element.getScale();
                float prevMouseX = element.getPrevMouseX();
                float prevMouseY = element.getPrevMouseY();

                element.setPrevMouseX(scaledX);
                element.setPrevMouseY(scaledY);
                if (element.getDrag()) {
                    float moveX = scaledX - prevMouseX;
                    float moveY = scaledY - prevMouseY;

                    if (moveX != 0.0F || moveY != 0.0F) {
                        Border border = element.getBorder();

                        if (border != null) {
                            Border border = border;
                            float minY = border.getX();
                            float maxX = border.getX2();
                            boolean maxY = false;
                            float minX = Math.min(minY, maxX) + (float) 1;

                            maxX = border.getY();
                            float maxY1 = border.getY2();
                            boolean width = false;

                            minY = Math.min(maxX, maxY1) + (float) 1;
                            maxY1 = border.getX();
                            float width1 = border.getX2();
                            boolean height = false;

                            maxX = Math.max(maxY1, width1) - (float) 1;
                            width1 = border.getY();
                            float height1 = border.getY2();
                            boolean flag = false;

                            maxY1 = Math.max(width1, height1) - (float) 1;
                            width1 = (float) scaledResolution.getScaledWidth() / element.getScale();
                            height1 = (float) scaledResolution.getScaledHeight() / element.getScale();
                            if ((element.getRenderX() + (double) minX + (double) moveX >= 0.0D || moveX > (float) 0) && (element.getRenderX() + (double) maxX + (double) moveX <= (double) width1 || moveX < (float) 0)) {
                                element.setRenderX((double) moveX);
                            }

                            if ((element.getRenderY() + (double) minY + (double) moveY >= 0.0D || moveY > (float) 0) && (element.getRenderY() + (double) maxY1 + (double) moveY <= (double) height1 || moveY < (float) 0)) {
                                element.setRenderY((double) moveY);
                            }
                        }
                    }
                }
            }

        }
    }

    public final void handleKey(char c, int keyCode) {
        Iterator iterator = this.elements$1.iterator();

        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();

            element.handleKey(c, keyCode);
        }

    }

    @NotNull
    public final HUD addElement(@NotNull Element element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        this.elements$1.add(element);
        element.updateElement();
        return this;
    }

    @NotNull
    public final HUD removeElement(@NotNull Element element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        element.destroyElement();
        this.elements$1.remove(element);
        return this;
    }

    public final void clearElements() {
        Iterator iterator = this.elements$1.iterator();

        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();

            element.destroyElement();
        }

        this.elements$1.clear();
    }

    public final boolean addNotification(@NotNull Notification notification) {
        Intrinsics.checkParameterIsNotNull(notification, "notification");
        Iterable $this$any$iv = (Iterable) this.elements$1;
        boolean $i$f$any = false;
        boolean flag;

        if ($this$any$iv instanceof Collection && ((Collection) $this$any$iv).isEmpty()) {
            flag = false;
        } else {
            Iterator iterator = $this$any$iv.iterator();

            while (true) {
                if (iterator.hasNext()) {
                    Object element$iv = iterator.next();
                    Element it = (Element) element$iv;
                    boolean $i$a$-any-HUD$addNotification$1 = false;

                    if (!(it instanceof Notifications)) {
                        continue;
                    }

                    flag = true;
                    break;
                }

                flag = false;
                break;
            }
        }

        return flag && this.notifications.add(notification);
    }

    public final boolean removeNotification(@NotNull Notification notification) {
        Intrinsics.checkParameterIsNotNull(notification, "notification");
        return this.notifications.remove(notification);
    }

    public HUD() {
        boolean flag = false;
        List list = (List) (new ArrayList());

        this.elements$1 = list;
        flag = false;
        list = (List) (new ArrayList());
        this.notifications = list;
    }

    @JvmStatic
    @NotNull
    public static final HUD createDefault() {
        return HUD.Companion.createDefault();
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0007R!\u0010\u0003\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\f"},
        d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD$Companion;", "", "()V", "elements", "", "Ljava/lang/Class;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "getElements", "()[Ljava/lang/Class;", "[Ljava/lang/Class;", "createDefault", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final Class[] getElements() {
            return HUD.elements;
        }

        @JvmStatic
        @NotNull
        public final HUD createDefault() {
            return (new HUD()).addElement((Element) (new ScoreboardElement(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null))).addElement((Element) (new Notifications(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null))).addElement((Element) (new Effects(0.0D, 0.0D, 0.0F, (Side) null, 15, (DefaultConstructorMarker) null))).addElement((Element) (new NewEffects()));
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
