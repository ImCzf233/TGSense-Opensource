package me.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0005H\u0007J\u0006\u0010\t\u001a\u00020\u0006R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"},
    d2 = { "Lme/utils/ClassUtils;", "", "()V", "cachedClasses", "", "", "", "hasClass", "className", "hasForge", "LiquidBounce"}
)
public final class ClassUtils {

    private static final Map cachedClasses;
    public static final ClassUtils INSTANCE;

    @JvmStatic
    public static final boolean hasClass(@NotNull String className) {
        Intrinsics.checkParameterIsNotNull(className, "className");
        boolean flag;

        if (ClassUtils.cachedClasses.containsKey(className)) {
            Object object = ClassUtils.cachedClasses.get(className);

            if (object == null) {
                Intrinsics.throwNpe();
            }

            flag = ((Boolean) object).booleanValue();
        } else {
            boolean flag1;

            try {
                Class.forName(className);
                ClassUtils.cachedClasses.put(className, Boolean.valueOf(true));
                flag1 = true;
            } catch (ClassNotFoundException classnotfoundexception) {
                ClassUtils.cachedClasses.put(className, Boolean.valueOf(false));
                flag1 = false;
            }

            flag = flag1;
        }

        return flag;
    }

    public final boolean hasForge() {
        return hasClass("net.minecraftforge.common.MinecraftForge");
    }

    static {
        ClassUtils classutils = new ClassUtils();

        INSTANCE = classutils;
        boolean flag = false;

        cachedClasses = (Map) (new LinkedHashMap());
    }
}
