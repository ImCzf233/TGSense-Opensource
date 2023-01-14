package net.ccbluex.liquidbounce.script.remapper.injection.transformers.handlers;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.script.remapper.Remapper;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u001c\u0010\n\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007¨\u0006\u000b"},
    d2 = { "Lnet/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler;", "", "()V", "addMember", "", "clazz", "Ljava/lang/Class;", "name", "accessibleObject", "Ljava/lang/reflect/AccessibleObject;", "setPropertyGetter", "LiquidBounce"}
)
public final class AbstractJavaLinkerHandler {

    public static final AbstractJavaLinkerHandler INSTANCE;

    @JvmStatic
    @NotNull
    public static final String addMember(@NotNull Class clazz, @NotNull String name, @NotNull AccessibleObject accessibleObject) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(accessibleObject, "accessibleObject");
        if (!(accessibleObject instanceof Method)) {
            return name;
        } else {
            Class oclass;

            for (Class currentClass = clazz; Intrinsics.areEqual(currentClass.getName(), "java.lang.Object") ^ true; currentClass = oclass) {
                Remapper remapper = Remapper.INSTANCE;
                String s = Type.getMethodDescriptor((Method) accessibleObject);

                Intrinsics.checkExpressionValueIsNotNull(s, "Type.getMethodDescriptor(accessibleObject)");
                String remapped = remapper.remapMethod(currentClass, name, s);

                if (Intrinsics.areEqual(remapped, name) ^ true) {
                    return remapped;
                }

                if (currentClass.getSuperclass() == null) {
                    break;
                }

                oclass = currentClass.getSuperclass();
                Intrinsics.checkExpressionValueIsNotNull(oclass, "currentClass.superclass");
            }

            return name;
        }
    }

    @JvmStatic
    @NotNull
    public static final String addMember(@NotNull Class clazz, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");

        Class oclass;

        for (Class currentClass = clazz; Intrinsics.areEqual(currentClass.getName(), "java.lang.Object") ^ true; currentClass = oclass) {
            String remapped = Remapper.INSTANCE.remapField(currentClass, name);

            if (Intrinsics.areEqual(remapped, name) ^ true) {
                return remapped;
            }

            if (currentClass.getSuperclass() == null) {
                break;
            }

            oclass = currentClass.getSuperclass();
            Intrinsics.checkExpressionValueIsNotNull(oclass, "currentClass.superclass");
        }

        return name;
    }

    @JvmStatic
    @NotNull
    public static final String setPropertyGetter(@NotNull Class clazz, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");

        Class oclass;

        for (Class currentClass = clazz; Intrinsics.areEqual(currentClass.getName(), "java.lang.Object") ^ true; currentClass = oclass) {
            String remapped = Remapper.INSTANCE.remapField(currentClass, name);

            if (Intrinsics.areEqual(remapped, name) ^ true) {
                return remapped;
            }

            if (currentClass.getSuperclass() == null) {
                break;
            }

            oclass = currentClass.getSuperclass();
            Intrinsics.checkExpressionValueIsNotNull(oclass, "currentClass.superclass");
        }

        return name;
    }

    static {
        AbstractJavaLinkerHandler abstractjavalinkerhandler = new AbstractJavaLinkerHandler();

        INSTANCE = abstractjavalinkerhandler;
    }
}
