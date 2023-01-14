package net.ccbluex.liquidbounce.script.remapper;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010\r\u001a\u00020\fH\u0002J\u001a\u0010\u000e\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u0005J\"\u0010\u0012\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005RR\u0010\u0003\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000RR\u0010\u0007\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/script/remapper/Remapper;", "", "()V", "fields", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "methods", "srgFile", "Ljava/io/File;", "srgName", "loadSrg", "", "parseSrg", "remapField", "clazz", "Ljava/lang/Class;", "name", "remapMethod", "desc", "LiquidBounce"}
)
public final class Remapper {

    private static final String srgName = "stable_22";
    private static final File srgFile;
    private static final HashMap fields;
    private static final HashMap methods;
    public static final Remapper INSTANCE;

    public final void loadSrg() {
        if (!Remapper.srgFile.exists()) {
            Remapper.srgFile.createNewFile();
            ClientUtils.getLogger().info("[Remapper] Downloading stable_22 srg...");
            HttpUtils.download("https://cloud.liquidbounce.net/LiquidBounce/srgs/mcp-stable_22.srg", Remapper.srgFile);
            ClientUtils.getLogger().info("[Remapper] Downloaded stable_22.");
        }

        ClientUtils.getLogger().info("[Remapper] Loading srg...");
        this.parseSrg();
        ClientUtils.getLogger().info("[Remapper] Loaded srg.");
    }

    private final void parseSrg() {
        Iterable $this$forEach$iv = (Iterable) FilesKt.readLines$default(Remapper.srgFile, (Charset) null, 1, (Object) null);
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv.iterator();

        while (iterator.hasNext()) {
            Object element$iv = iterator.next();
            String it = (String) element$iv;
            boolean $i$a$-forEach-Remapper$parseSrg$1 = false;
            List args = StringsKt.split$default((CharSequence) it, new String[] { " "}, false, 0, 6, (Object) null);
            String name;
            String desc;
            String className;
            boolean flag;
            String srg;
            boolean flag1;
            boolean flag2;
            Map map;
            HashMap hashmap;
            String methodName1;
            int i;
            Object object;
            String s;

            if (StringsKt.startsWith$default(it, "FD:", false, 2, (Object) null)) {
                name = (String) args.get(1);
                desc = (String) args.get(2);
                byte methodName = 0;
                int methodSrg = StringsKt.lastIndexOf$default((CharSequence) name, '/', 0, false, 6, (Object) null);

                flag = false;
                if (name == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                s = name.substring(methodName, methodSrg);
                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                srg = StringsKt.replace$default(s, '/', '.', false, 4, (Object) null);
                methodSrg = StringsKt.lastIndexOf$default((CharSequence) name, '/', 0, false, 6, (Object) null) + 1;
                flag = false;
                if (name == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                s = name.substring(methodSrg);
                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).substring(startIndex)");
                className = s;
                i = StringsKt.lastIndexOf$default((CharSequence) desc, '/', 0, false, 6, (Object) null) + 1;
                flag1 = false;
                if (desc == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                s = desc.substring(i);
                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).substring(startIndex)");
                methodName1 = s;
                Map methodSrg1 = (Map) Remapper.fields;

                flag = false;
                flag2 = false;
                if (!methodSrg1.containsKey(srg)) {
                    map = (Map) Remapper.fields;
                    boolean methodSrg2 = false;

                    hashmap = new HashMap();
                    map.put(srg, hashmap);
                }

                object = Remapper.fields.get(srg);
                if (object == null) {
                    Intrinsics.throwNpe();
                }

                Intrinsics.checkExpressionValueIsNotNull(object, "fields[className]!!");
                ((Map) object).put(methodName1, className);
            } else if (StringsKt.startsWith$default(it, "MD:", false, 2, (Object) null)) {
                name = (String) args.get(1);
                desc = (String) args.get(2);
                srg = (String) args.get(3);
                byte methodSrg3 = 0;

                i = StringsKt.lastIndexOf$default((CharSequence) name, '/', 0, false, 6, (Object) null);
                flag1 = false;
                if (name == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                s = name.substring(methodSrg3, i);
                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                className = StringsKt.replace$default(s, '/', '.', false, 4, (Object) null);
                i = StringsKt.lastIndexOf$default((CharSequence) name, '/', 0, false, 6, (Object) null) + 1;
                flag1 = false;
                if (name == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                s = name.substring(i);
                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).substring(startIndex)");
                methodName1 = s;
                int j = StringsKt.lastIndexOf$default((CharSequence) srg, '/', 0, false, 6, (Object) null) + 1;

                flag2 = false;
                if (srg == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                s = srg.substring(j);
                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).substring(startIndex)");
                String methodSrg4 = s;
                Map map1 = (Map) Remapper.methods;

                flag1 = false;
                boolean flag3 = false;

                if (!map1.containsKey(className)) {
                    map = (Map) Remapper.methods;
                    flag = false;
                    hashmap = new HashMap();
                    map.put(className, hashmap);
                }

                object = Remapper.methods.get(className);
                if (object == null) {
                    Intrinsics.throwNpe();
                }

                Intrinsics.checkExpressionValueIsNotNull(object, "methods[className]!!");
                ((Map) object).put(methodSrg4 + desc, methodName1);
            }
        }

    }

    @NotNull
    public final String remapField(@NotNull Class clazz, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");
        if (!Remapper.fields.containsKey(clazz.getName())) {
            return name;
        } else {
            Object object = Remapper.fields.get(clazz.getName());

            if (object == null) {
                Intrinsics.throwNpe();
            }

            object = ((HashMap) object).getOrDefault(name, name);
            Intrinsics.checkExpressionValueIsNotNull(object, "fields[clazz.name]!!.getOrDefault(name, name)");
            return (String) object;
        }
    }

    @NotNull
    public final String remapMethod(@NotNull Class clazz, @NotNull String name, @NotNull String desc) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(desc, "desc");
        if (!Remapper.methods.containsKey(clazz.getName())) {
            return name;
        } else {
            Object object = Remapper.methods.get(clazz.getName());

            if (object == null) {
                Intrinsics.throwNpe();
            }

            object = ((HashMap) object).getOrDefault(name + desc, name);
            Intrinsics.checkExpressionValueIsNotNull(object, "methods[clazz.name]!!.ge…efault(name + desc, name)");
            return (String) object;
        }
    }

    static {
        Remapper remapper = new Remapper();

        INSTANCE = remapper;
        srgFile = new File(LiquidBounce.INSTANCE.getFileManager().dir, "mcp-stable_22.srg");
        boolean flag = false;

        fields = new HashMap();
        flag = false;
        methods = new HashMap();
    }
}
