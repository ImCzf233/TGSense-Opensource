package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\b\u0002\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¢\u0006\u0002\u0010\u0007J\u001b\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016¢\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016¢\u0006\u0002\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001b\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/ModuleCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "module", "Lnet/ccbluex/liquidbounce/features/module/Module;", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "(Lnet/ccbluex/liquidbounce/features/module/Module;Ljava/util/List;)V", "getModule", "()Lnet/ccbluex/liquidbounce/features/module/Module;", "getValues", "()Ljava/util/List;", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class ModuleCommand extends Command {

    @NotNull
    private final Module module;
    @NotNull
    private final List values;

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        Iterable moduleName = (Iterable) this.values;
        boolean value = false;
        Collection id = (Collection) (new ArrayList());
        boolean $i$f$filterTo = false;
        Iterator exception = moduleName.iterator();

        boolean $i$a$-filter-ModuleCommand$execute$valueNames$1;

        while (exception.hasNext()) {
            Object tmpId = exception.next();
            Value it = (Value) tmpId;

            $i$a$-filter-ModuleCommand$execute$valueNames$1 = false;
            if (!(it instanceof FontValue)) {
                id.add(tmpId);
            }
        }

        String valueNames = CollectionsKt.joinToString$default((Iterable) ((List) id), (CharSequence) "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null.INSTANCE, 30, (Object) null);
        String value1 = this.module.getName();
        boolean e = false;

        if (value1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s = value1.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
            String moduleName1 = s;

            if (args.length < 2) {
                this.chatSyntax(this.values.size() == 1 ? moduleName1 + ' ' + valueNames + " <value>" : moduleName1 + " <" + valueNames + '>');
            } else {
                Value value2 = this.module.getValue(args[1]);

                if (value2 == null) {
                    this.chatSyntax(moduleName1 + " <" + valueNames + '>');
                } else {
                    if (value2 instanceof BoolValue) {
                        e = !((Boolean) ((BoolValue) value2).get()).booleanValue();
                        ((BoolValue) value2).set(Boolean.valueOf(e));
                        this.chat("§7" + this.module.getName() + " §8" + args[1] + "§7 was toggled " + (e ? "§8on§7" : "§8off§7."));
                        this.playEdit();
                    } else {
                        String s1;
                        boolean id2;
                        StringBuilder stringbuilder;
                        StringBuilder stringbuilder1;

                        if (args.length < 3) {
                            String e1;

                            if (!(value2 instanceof IntegerValue) && !(value2 instanceof FloatValue) && !(value2 instanceof TextValue)) {
                                if (value2 instanceof ListValue) {
                                    stringbuilder1 = (new StringBuilder()).append(moduleName1).append(' ');
                                    e1 = args[1];
                                    stringbuilder = stringbuilder1;
                                    id2 = false;
                                    if (e1 == null) {
                                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                    }

                                    s = e1.toLowerCase();
                                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                                    s1 = s;
                                    stringbuilder1 = stringbuilder.append(s1).append(" <");
                                    e1 = ArraysKt.joinToString$default(((ListValue) value2).getValues(), (CharSequence) "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
                                    stringbuilder = stringbuilder1;
                                    id2 = false;
                                    if (e1 == null) {
                                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                    }

                                    s = e1.toLowerCase();
                                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                                    s1 = s;
                                    this.chatSyntax(stringbuilder.append(s1).append('>').toString());
                                }
                            } else {
                                stringbuilder1 = (new StringBuilder()).append(moduleName1).append(' ');
                                e1 = args[1];
                                stringbuilder = stringbuilder1;
                                id2 = false;
                                if (e1 == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                }

                                s = e1.toLowerCase();
                                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                                s1 = s;
                                this.chatSyntax(stringbuilder.append(s1).append(" <value>").toString());
                            }

                            return;
                        }

                        try {
                            if (value2 instanceof BlockValue) {
                                id2 = false;

                                int $i$f$filterTo1;
                                String $i$f$filterTo2;
                                boolean exception1;

                                try {
                                    $i$f$filterTo2 = args[2];
                                    exception1 = false;
                                    $i$f$filterTo1 = Integer.parseInt($i$f$filterTo2);
                                } catch (NumberFormatException numberformatexception) {
                                    IBlock iblock = MinecraftInstance.functions.getBlockFromName(args[2]);
                                    Integer integer;

                                    if (iblock != null) {
                                        IBlock it1 = iblock;

                                        $i$a$-filter-ModuleCommand$execute$valueNames$1 = false;
                                        boolean flag = false;
                                        boolean $i$a$-let-ModuleCommand$execute$tmpId$1 = false;

                                        integer = Integer.valueOf(MinecraftInstance.functions.getIdFromBlock(it1));
                                    } else {
                                        integer = null;
                                    }

                                    Integer tmpId1 = integer;

                                    if (tmpId1 == null || tmpId1.intValue() <= 0) {
                                        this.chat("§7Block §8" + args[2] + "§7 does not exist!");
                                        return;
                                    }

                                    $i$f$filterTo1 = tmpId1.intValue();
                                }

                                int id3 = $i$f$filterTo1;

                                ((BlockValue) value2).set((Object) Integer.valueOf($i$f$filterTo1));
                                stringbuilder1 = (new StringBuilder()).append("§7").append(this.module.getName()).append(" §8");
                                $i$f$filterTo2 = args[1];
                                stringbuilder = stringbuilder1;
                                exception1 = false;
                                if ($i$f$filterTo2 == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                }

                                s = $i$f$filterTo2.toLowerCase();
                                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                                s1 = s;
                                this.chat(stringbuilder.append(s1).append("§7 was set to §8").append(BlockUtils.getBlockName(id3)).append("§7.").toString());
                                this.playEdit();
                                return;
                            }

                            String id1;

                            if (value2 instanceof IntegerValue) {
                                IntegerValue integervalue = (IntegerValue) value2;

                                id1 = args[2];
                                IntegerValue integervalue1 = integervalue;

                                $i$f$filterTo = false;
                                int i = Integer.parseInt(id1);

                                integervalue1.set((Object) Integer.valueOf(i));
                            } else if (value2 instanceof FloatValue) {
                                FloatValue floatvalue = (FloatValue) value2;

                                id1 = args[2];
                                FloatValue floatvalue1 = floatvalue;

                                $i$f$filterTo = false;
                                float f = Float.parseFloat(id1);

                                floatvalue1.set((Object) Float.valueOf(f));
                            } else if (value2 instanceof ListValue) {
                                if (!((ListValue) value2).contains(args[2])) {
                                    stringbuilder1 = (new StringBuilder()).append(moduleName1).append(' ');
                                    id1 = args[1];
                                    stringbuilder = stringbuilder1;
                                    $i$f$filterTo = false;
                                    if (id1 == null) {
                                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                    }

                                    s = id1.toLowerCase();
                                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                                    s1 = s;
                                    stringbuilder1 = stringbuilder.append(s1).append(" <");
                                    id1 = ArraysKt.joinToString$default(((ListValue) value2).getValues(), (CharSequence) "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
                                    stringbuilder = stringbuilder1;
                                    $i$f$filterTo = false;
                                    if (id1 == null) {
                                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                    }

                                    s = id1.toLowerCase();
                                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                                    s1 = s;
                                    this.chatSyntax(stringbuilder.append(s1).append('>').toString());
                                    return;
                                }

                                ((ListValue) value2).set(args[2]);
                            } else if (value2 instanceof TextValue) {
                                TextValue textvalue = (TextValue) value2;
                                String s2 = StringUtils.toCompleteString(args, 2);

                                Intrinsics.checkExpressionValueIsNotNull(s2, "StringUtils.toCompleteString(args, 2)");
                                textvalue.set(s2);
                            }

                            this.chat("§7" + this.module.getName() + " §8" + args[1] + "§7 was set to §8" + value2.get() + "§7.");
                            this.playEdit();
                        } catch (NumberFormatException numberformatexception1) {
                            this.chat("§8" + args[2] + "§7 cannot be converted to number!");
                        }
                    }

                }
            }
        }
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean $this$forEach$iv = false;

        if (args.length == 0) {
            return CollectionsKt.emptyList();
        } else {
            boolean $i$f$filter;
            String s;
            String s1;
            List list;

            switch (args.length) {
            case 1:
                Iterable iterable = (Iterable) this.values;

                $this$forEach$iv = false;
                Collection collection = (Collection) (new ArrayList());
                boolean flag = false;
                Iterator iterator = iterable.iterator();

                Object object;
                Value value;

                while (iterator.hasNext()) {
                    object = iterator.next();
                    value = (Value) object;
                    $i$f$filter = false;
                    if (!(value instanceof FontValue) && StringsKt.startsWith(value.getName(), args[0], true)) {
                        collection.add(object);
                    }
                }

                iterable = (Iterable) ((List) collection);
                $this$forEach$iv = false;
                collection = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10)));
                flag = false;
                iterator = iterable.iterator();

                while (iterator.hasNext()) {
                    object = iterator.next();
                    value = (Value) object;
                    $i$f$filter = false;
                    String s2 = value.getName();
                    boolean flag1 = false;

                    if (s2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s1 = s2.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                    s = s1;
                    collection.add(s);
                }

                list = (List) collection;
                break;

            case 2:
                Value $this$map$iv = this.module.getValue(args[0]);
                boolean $i$f$forEach;
                boolean $i$f$filterTo;
                Iterable iterable1;

                if ($this$map$iv instanceof BlockValue) {
                    iterable1 = (Iterable) MinecraftInstance.functions.getItemRegistryKeys();
                    $i$f$forEach = false;
                    Collection collection1 = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable1, 10)));
                    boolean flag2 = false;
                    Iterator iterator1 = iterable1.iterator();

                    boolean $this$filterTo$iv$iv1;
                    Object object1;

                    while (iterator1.hasNext()) {
                        object1 = iterator1.next();
                        IResourceLocation iresourcelocation = (IResourceLocation) object1;

                        $this$filterTo$iv$iv1 = false;
                        String s3 = iresourcelocation.getResourcePath();

                        $i$f$filterTo = false;
                        if (s3 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        s1 = s3.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                        s = s1;
                        collection1.add(s);
                    }

                    iterable1 = (Iterable) ((List) collection1);
                    $i$f$forEach = false;
                    collection1 = (Collection) (new ArrayList());
                    flag2 = false;
                    iterator1 = iterable1.iterator();

                    while (iterator1.hasNext()) {
                        object1 = iterator1.next();
                        String s4 = (String) object1;

                        $this$filterTo$iv$iv1 = false;
                        if (StringsKt.startsWith(s4, args[1], true)) {
                            collection1.add(object1);
                        }
                    }

                    return (List) collection1;
                }

                if ($this$map$iv instanceof ListValue) {
                    iterable1 = (Iterable) this.values;
                    $i$f$forEach = false;
                    Iterator $this$filterTo$iv$iv = iterable1.iterator();

                    Value value;

                    do {
                        if (!$this$filterTo$iv$iv.hasNext()) {
                            return CollectionsKt.emptyList();
                        }

                        Object element$iv = $this$filterTo$iv$iv.next();

                        value = (Value) element$iv;
                        boolean $i$a$-forEach-ModuleCommand$tabComplete$5 = false;
                    } while (!StringsKt.equals(value.getName(), args[0], true) || !(value instanceof ListValue));

                    String[] $this$filter$iv = ((ListValue) value).getValues();

                    $i$f$filter = false;
                    Collection destination$iv$iv = (Collection) (new ArrayList());

                    $i$f$filterTo = false;
                    String[] astring = $this$filter$iv;
                    int i = $this$filter$iv.length;

                    for (int j = 0; j < i; ++j) {
                        String element$iv$iv = astring[j];
                        boolean $i$a$-filter-ModuleCommand$tabComplete$5$1 = false;

                        if (StringsKt.startsWith(element$iv$iv, args[1], true)) {
                            destination$iv$iv.add(element$iv$iv);
                        }
                    }

                    return (List) destination$iv$iv;
                }

                list = CollectionsKt.emptyList();
                break;

            default:
                list = CollectionsKt.emptyList();
            }

            return list;
        }
    }

    @NotNull
    public final Module getModule() {
        return this.module;
    }

    @NotNull
    public final List getValues() {
        return this.values;
    }

    public ModuleCommand(@NotNull Module module, @NotNull List values) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        Intrinsics.checkParameterIsNotNull(values, "values");
        String s = module.getName();
        boolean flag = false;

        if (s == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s1 = s.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
            String s2 = s1;

            super(s2, new String[0]);
            this.module = module;
            this.values = values;
            if (this.values.isEmpty()) {
                throw (Throwable) (new IllegalArgumentException("Values are empty!"));
            }
        }
    }

    public ModuleCommand(Module module, List list, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 2) != 0) {
            list = module.getValues();
        }

        this(module, list);
    }
}
