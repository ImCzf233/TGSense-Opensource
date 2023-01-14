package net.ccbluex.liquidbounce.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006J\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u000eR(\u0010\u0003\u001a\u001c\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/event/EventManager;", "", "()V", "registry", "Ljava/util/HashMap;", "Ljava/lang/Class;", "Lnet/ccbluex/liquidbounce/event/Event;", "", "Lnet/ccbluex/liquidbounce/event/EventHook;", "callEvent", "", "event", "registerListener", "listener", "Lnet/ccbluex/liquidbounce/event/Listenable;", "unregisterListener", "listenable", "LiquidBounce"}
)
public final class EventManager {

    private final HashMap registry = new HashMap();

    public final void registerListener(@NotNull Listenable listener) {
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        Method[] amethod = listener.getClass().getDeclaredMethods();
        int i = amethod.length;

        for (int j = 0; j < i; ++j) {
            Method method = amethod[j];

            if (method.isAnnotationPresent(EventTarget.class)) {
                Intrinsics.checkExpressionValueIsNotNull(method, "method");
                if (method.getParameterTypes().length == 1) {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }

                    Class oclass = method.getParameterTypes()[0];

                    if (oclass == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<out net.ccbluex.liquidbounce.event.Event>");
                    }

                    Class eventClass = oclass;
                    EventTarget eventTarget = (EventTarget) method.getAnnotation(EventTarget.class);
                    Object object = this.registry.getOrDefault(eventClass, new ArrayList());

                    Intrinsics.checkExpressionValueIsNotNull(object, "registry.getOrDefault(eventClass, ArrayList())");
                    List invokableEventTargets = (List) object;

                    Intrinsics.checkExpressionValueIsNotNull(eventTarget, "eventTarget");
                    invokableEventTargets.add(new EventHook(listener, method, eventTarget));
                    ((Map) this.registry).put(eventClass, invokableEventTargets);
                }
            }
        }

    }

    public final void unregisterListener(@NotNull final Listenable listenable) {
        Intrinsics.checkParameterIsNotNull(listenable, "listenable");
        Map key = (Map) this.registry;
        boolean targets = false;
        Iterator iterator = key.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            boolean flag = false;
            Class key1 = (Class) entry.getKey();

            flag = false;
            List targets1 = (List) entry.getValue();

            targets1.removeIf((Predicate) (new Predicate() {
                public final boolean test(@NotNull EventHook it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    return Intrinsics.areEqual(it.getEventClass(), listenable);
                }
            }));
            ((Map) this.registry).put(key1, targets1);
        }

    }

    public final void callEvent(@NotNull Event event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        List list = (List) this.registry.get(event.getClass());

        if (list != null) {
            Intrinsics.checkExpressionValueIsNotNull(list, "registry[event.javaClass] ?: return");
            List targets = list;
            Iterator iterator = targets.iterator();

            while (iterator.hasNext()) {
                EventHook invokableEventTarget = (EventHook) iterator.next();

                try {
                    if (invokableEventTarget.getEventClass().handleEvents() || invokableEventTarget.isIgnoreCondition()) {
                        invokableEventTarget.getMethod().invoke(invokableEventTarget.getEventClass(), new Object[] { event});
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

        }
    }
}
