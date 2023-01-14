package net.ccbluex.liquidbounce.ui.client.hud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\n\u001a\u00020\u0006J\u0006\u0010\u000b\u001a\u00020\u0003R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/ui/client/hud/Config;", "", "config", "", "(Ljava/lang/String;)V", "hud", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;)V", "jsonArray", "Lcom/google/gson/JsonArray;", "toHUD", "toJson", "LiquidBounce"}
)
public final class Config {

    private JsonArray jsonArray;

    @NotNull
    public final String toJson() {
        String s = (new GsonBuilder()).setPrettyPrinting().create().toJson((JsonElement) this.jsonArray);

        Intrinsics.checkExpressionValueIsNotNull(s, "GsonBuilder().setPrettyP…reate().toJson(jsonArray)");
        return s;
    }

    @NotNull
    public final HUD toHUD() {
        HUD hud = new HUD();

        try {
            Iterator iterator = this.jsonArray.iterator();

            Element it;

            while (iterator.hasNext()) {
                JsonElement e = (JsonElement) iterator.next();

                try {
                    if (e instanceof JsonObject && ((JsonObject) e).has("Type")) {
                        JsonElement jsonelement = ((JsonObject) e).get("Type");

                        Intrinsics.checkExpressionValueIsNotNull(jsonelement, "jsonObject[\"Type\"]");
                        String e1 = jsonelement.getAsString();
                        Class[] $i$f$none = HUD.Companion.getElements();
                        int i = $i$f$none.length;

                        for (int $this$none$iv = 0; $this$none$iv < i; ++$this$none$iv) {
                            Class elementClass = $i$f$none[$this$none$iv];
                            String element$iv = ((ElementInfo) elementClass.getAnnotation(ElementInfo.class)).name();

                            if (Intrinsics.areEqual(element$iv, e1)) {
                                it = (Element) elementClass.newInstance();
                                JsonElement jsonelement1 = ((JsonObject) e).get("X");

                                Intrinsics.checkExpressionValueIsNotNull(jsonelement1, "jsonObject[\"X\"]");
                                it.setX((double) jsonelement1.getAsInt());
                                jsonelement1 = ((JsonObject) e).get("Y");
                                Intrinsics.checkExpressionValueIsNotNull(jsonelement1, "jsonObject[\"Y\"]");
                                it.setY((double) jsonelement1.getAsInt());
                                jsonelement1 = ((JsonObject) e).get("Scale");
                                Intrinsics.checkExpressionValueIsNotNull(jsonelement1, "jsonObject[\"Scale\"]");
                                it.setScale(jsonelement1.getAsFloat());
                                Side side = new Side;
                                Side.Horizontal.Companion side_horizontal_companion = Side.Horizontal.Companion;
                                JsonElement jsonelement2 = ((JsonObject) e).get("HorizontalFacing");

                                Intrinsics.checkExpressionValueIsNotNull(jsonelement2, "jsonObject[\"HorizontalFacing\"]");
                                String s = jsonelement2.getAsString();

                                Intrinsics.checkExpressionValueIsNotNull(s, "jsonObject[\"HorizontalFacing\"].asString");
                                Side.Horizontal side_horizontal = side_horizontal_companion.getByName(s);

                                if (side_horizontal == null) {
                                    Intrinsics.throwNpe();
                                }

                                Side.Vertical.Companion side_vertical_companion = Side.Vertical.Companion;
                                JsonElement jsonelement3 = ((JsonObject) e).get("VerticalFacing");

                                Intrinsics.checkExpressionValueIsNotNull(jsonelement3, "jsonObject[\"VerticalFacing\"]");
                                String s1 = jsonelement3.getAsString();

                                Intrinsics.checkExpressionValueIsNotNull(s1, "jsonObject[\"VerticalFacing\"].asString");
                                Side.Vertical side_vertical = side_vertical_companion.getByName(s1);

                                if (side_vertical == null) {
                                    Intrinsics.throwNpe();
                                }

                                side.<init>(side_horizontal, side_vertical);
                                it.setSide(side);
                                Iterator iterator1 = it.getValues().iterator();

                                while (iterator1.hasNext()) {
                                    Value $i$a$-none-Config$toHUD$2 = (Value) iterator1.next();

                                    if (((JsonObject) e).has($i$a$-none-Config$toHUD$2.getName())) {
                                        jsonelement1 = ((JsonObject) e).get($i$a$-none-Config$toHUD$2.getName());
                                        Intrinsics.checkExpressionValueIsNotNull(jsonelement1, "jsonObject[value.name]");
                                        $i$a$-none-Config$toHUD$2.fromJson(jsonelement1);
                                    }
                                }

                                if (((JsonObject) e).has("font")) {
                                    Iterable iterable = (Iterable) it.getValues();
                                    boolean flag = false;
                                    boolean flag1 = false;
                                    Iterator iterator2 = iterable.iterator();

                                    Object object;

                                    while (true) {
                                        if (iterator2.hasNext()) {
                                            Object object1 = iterator2.next();
                                            Value it1 = (Value) object1;
                                            boolean $i$a$-find-Config$toHUD$1 = false;

                                            if (!(it1 instanceof FontValue)) {
                                                continue;
                                            }

                                            object = object1;
                                            break;
                                        }

                                        object = null;
                                        break;
                                    }

                                    Value value = (Value) object;

                                    if ((Value) object != null) {
                                        jsonelement1 = ((JsonObject) e).get("font");
                                        Intrinsics.checkExpressionValueIsNotNull(jsonelement1, "jsonObject[\"font\"]");
                                        value.fromJson(jsonelement1);
                                    }
                                }

                                Intrinsics.checkExpressionValueIsNotNull(it, "element");
                                hud.addElement(it);
                                break;
                            }
                        }
                    }
                } catch (Exception exception) {
                    ClientUtils.getLogger().error("Error while loading custom hud element from config.", (Throwable) exception);
                }
            }

            Class[] aclass = HUD.Companion.getElements();
            int j = aclass.length;

            for (int k = 0; k < j; ++k) {
                Class oclass = aclass[k];

                if (((ElementInfo) oclass.getAnnotation(ElementInfo.class)).force()) {
                    Iterable iterable1 = (Iterable) hud.getElements();
                    boolean flag2 = false;
                    boolean flag3;

                    if (iterable1 instanceof Collection && ((Collection) iterable1).isEmpty()) {
                        flag3 = true;
                    } else {
                        Iterator iterator3 = iterable1.iterator();

                        while (true) {
                            if (!iterator3.hasNext()) {
                                flag3 = true;
                                break;
                            }

                            Object object2 = iterator3.next();

                            it = (Element) object2;
                            boolean flag4 = false;

                            if (Intrinsics.areEqual(it.getClass(), oclass)) {
                                flag3 = false;
                                break;
                            }
                        }
                    }

                    if (flag3) {
                        Object object3 = oclass.newInstance();

                        Intrinsics.checkExpressionValueIsNotNull(object3, "elementClass.newInstance()");
                        hud.addElement((Element) object3);
                    }
                }
            }

            return hud;
        } catch (Exception exception1) {
            ClientUtils.getLogger().error("Error while loading custom hud config.", (Throwable) exception1);
            return HUD.Companion.createDefault();
        }
    }

    public Config(@NotNull String config) {
        Intrinsics.checkParameterIsNotNull(config, "config");
        super();
        this.jsonArray = new JsonArray();
        Object object = (new Gson()).fromJson(config, JsonArray.class);

        Intrinsics.checkExpressionValueIsNotNull(object, "Gson().fromJson(config, JsonArray::class.java)");
        this.jsonArray = (JsonArray) object;
    }

    public Config(@NotNull HUD hud) {
        Intrinsics.checkParameterIsNotNull(hud, "hud");
        super();
        this.jsonArray = new JsonArray();
        Iterator iterator = hud.getElements().iterator();

        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            JsonObject elementObject = new JsonObject();

            elementObject.addProperty("Type", element.getName());
            elementObject.addProperty("X", (Number) Double.valueOf(element.getX()));
            elementObject.addProperty("Y", (Number) Double.valueOf(element.getY()));
            elementObject.addProperty("Scale", (Number) Float.valueOf(element.getScale()));
            elementObject.addProperty("HorizontalFacing", element.getSide().getHorizontal().getSideName());
            elementObject.addProperty("VerticalFacing", element.getSide().getVertical().getSideName());
            Iterator iterator1 = element.getValues().iterator();

            while (iterator1.hasNext()) {
                Value value = (Value) iterator1.next();

                elementObject.add(value.getName(), value.toJson());
            }

            this.jsonArray.add((JsonElement) elementObject);
        }

    }
}
