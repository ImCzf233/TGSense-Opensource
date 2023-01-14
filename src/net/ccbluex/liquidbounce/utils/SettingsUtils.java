package net.ccbluex.liquidbounce.utils;

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
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.misc.Spammer;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/SettingsUtils;", "", "()V", "executeScript", "", "script", "", "generateScript", "values", "", "binds", "states", "LiquidBounce"}
)
public final class SettingsUtils {

    public static final SettingsUtils INSTANCE;

    public final void executeScript(@NotNull String script) {
        Intrinsics.checkParameterIsNotNull(script, "script");
        Iterable $this$forEachIndexed$iv = (Iterable) StringsKt.lines((CharSequence) script);
        boolean $i$f$forEachIndexed = false;
        Collection destination$iv$iv = (Collection) (new ArrayList());
        boolean item$iv = false;
        Iterator iterator = $this$forEachIndexed$iv.iterator();

        boolean $i$a$-forEachIndexed-SettingsUtils$executeScript$2;

        while (iterator.hasNext()) {
            Object element$iv$iv = iterator.next();
            String it = (String) element$iv$iv;
            boolean s = false;
            CharSequence index = (CharSequence) it;

            $i$a$-forEachIndexed-SettingsUtils$executeScript$2 = false;
            if (index.length() > 0 && !StringsKt.startsWith$default((CharSequence) it, '#', false, 2, (Object) null)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }

        $this$forEachIndexed$iv = (Iterable) ((List) destination$iv$iv);
        $i$f$forEachIndexed = false;
        int index$iv = 0;
        Iterator iterator1 = $this$forEachIndexed$iv.iterator();

        while (iterator1.hasNext()) {
            Object object = iterator1.next();
            int i = index$iv++;
            boolean flag = false;

            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }

            String s = (String) object;

            $i$a$-forEachIndexed-SettingsUtils$executeScript$2 = false;
            Collection $this$toTypedArray$iv = (Collection) StringsKt.split$default((CharSequence) s, new String[] { " "}, false, 0, 6, (Object) null);
            boolean moduleName = false;
            Object[] aobject = $this$toTypedArray$iv.toArray(new String[0]);

            if (aobject == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }

            String[] args = (String[]) aobject;

            if (args.length <= 1) {
                ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §cSyntax error at line \'" + i + "\' in setting script.\n§8§lLine: §7" + s);
            } else {
                label157: {
                    String s1 = args[0];
                    String valueName;
                    String s2;
                    String s3;

                    switch (s1.hashCode()) {
                    case -840716943:
                        if (s1.equals("unchat")) {
                            s3 = StringUtils.toCompleteString(args, 1);
                            Intrinsics.checkExpressionValueIsNotNull(s3, "StringUtils.toCompleteString(args, 1)");
                            ClientUtils.displayChatMessage(ColorUtils.translateAlternateColorCodes(s3));
                            continue;
                        }
                        break;

                    case -634337326:
                        if (s1.equals("targetPlayer")) {
                            break label157;
                        }
                        break;

                    case 3052376:
                        if (s1.equals("chat")) {
                            StringBuilder stringbuilder = (new StringBuilder()).append("§7[§3§lAutoSettings§7] §e");
                            String s4 = StringUtils.toCompleteString(args, 1);

                            Intrinsics.checkExpressionValueIsNotNull(s4, "StringUtils.toCompleteString(args, 1)");
                            ClientUtils.displayChatMessage(stringbuilder.append(ColorUtils.translateAlternateColorCodes(s4)).toString());
                            continue;
                        }
                        break;

                    case 3327206:
                        if (s1.equals("load")) {
                            s2 = StringUtils.toCompleteString(args, 1);
                            Intrinsics.checkExpressionValueIsNotNull(s2, "urlRaw");
                            if (StringsKt.startsWith$default(s2, "http", false, 2, (Object) null)) {
                                s3 = s2;
                            } else {
                                StringBuilder stringbuilder1 = (new StringBuilder()).append("https://cloud.liquidbounce.net/LiquidBounce/settings/");
                                boolean module = false;

                                s3 = s2.toLowerCase();
                                Intrinsics.checkExpressionValueIsNotNull(s3, "(this as java.lang.String).toLowerCase()");
                                String s5 = s3;

                                s3 = stringbuilder1.append(s5).toString();
                            }

                            valueName = s3;

                            try {
                                ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §7Loading settings from §a§l" + valueName + "§7...");
                                SettingsUtils settingsutils = SettingsUtils.INSTANCE;

                                Intrinsics.checkExpressionValueIsNotNull(valueName, "url");
                                settingsutils.executeScript(HttpUtils.get(valueName));
                                ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §7Loaded settings from §a§l" + valueName + "§7.");
                            } catch (Exception exception) {
                                ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §7Failed to load settings from §a§l" + valueName + "§7.");
                            }
                            continue;
                        }
                        break;

                    case 283156764:
                        if (s1.equals("targetInvisible")) {
                            EntityUtils.targetInvisible = StringsKt.equals(args[1], "true", true);
                            ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §a§l" + args[0] + "§7 set to §c§l" + EntityUtils.targetInvisible + "§7.");
                            continue;
                        }
                        break;

                    case 486125973:
                        if (s1.equals("targetDead")) {
                            EntityUtils.targetDead = StringsKt.equals(args[1], "true", true);
                            ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §a§l" + args[0] + "§7 set to §c§l" + EntityUtils.targetDead + "§7.");
                            continue;
                        }
                        break;

                    case 486403748:
                        if (s1.equals("targetMobs")) {
                            EntityUtils.targetMobs = StringsKt.equals(args[1], "true", true);
                            ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §a§l" + args[0] + "§7 set to §c§l" + EntityUtils.targetMobs + "§7.");
                            continue;
                        }
                        break;

                    case 1447011110:
                        if (s1.equals("targetAnimals")) {
                            EntityUtils.targetAnimals = StringsKt.equals(args[1], "true", true);
                            ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §a§l" + args[0] + "§7 set to §c§l" + EntityUtils.targetAnimals + "§7.");
                            continue;
                        }
                        break;

                    case 1810379489:
                        if (s1.equals("targetPlayers")) {
                            break label157;
                        }
                    }

                    if (args.length != 3) {
                        ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §cSyntax error at line \'" + i + "\' in setting script.\n§8§lLine: §7" + s);
                        continue;
                    }

                    s2 = args[0];
                    valueName = args[1];
                    String value = args[2];
                    Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(s2);

                    if (module == null) {
                        ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §cModule §a§l" + s2 + "§c was not found!");
                        continue;
                    }

                    if (StringsKt.equals(valueName, "toggle", true)) {
                        module.setState(StringsKt.equals(value, "true", true));
                        ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §a§l" + module.getName() + " §7was toggled §c§l" + (module.getState() ? "on" : "off") + "§7.");
                        continue;
                    }

                    if (StringsKt.equals(valueName, "bind", true)) {
                        module.setKeyBind(Keyboard.getKeyIndex(value));
                        ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §a§l" + module.getName() + " §7was bound to §c§l" + Keyboard.getKeyName(module.getKeyBind()) + "§7.");
                        continue;
                    }

                    Value moduleValue = module.getValue(valueName);

                    if (moduleValue == null) {
                        ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §cValue §a§l" + valueName + "§c don\'t found in module §a§l" + s2 + "§c.");
                        continue;
                    }

                    try {
                        boolean flag1;

                        if (moduleValue instanceof BoolValue) {
                            BoolValue boolvalue = (BoolValue) moduleValue;

                            flag1 = false;
                            boolean flag2 = Boolean.parseBoolean(value);

                            boolvalue.changeValue(Boolean.valueOf(flag2));
                        } else if (moduleValue instanceof FloatValue) {
                            FloatValue floatvalue = (FloatValue) moduleValue;

                            flag1 = false;
                            float f = Float.parseFloat(value);

                            floatvalue.changeValue(Float.valueOf(f));
                        } else if (moduleValue instanceof IntegerValue) {
                            IntegerValue integervalue = (IntegerValue) moduleValue;

                            flag1 = false;
                            int j = Integer.parseInt(value);

                            integervalue.changeValue(Integer.valueOf(j));
                        } else if (moduleValue instanceof TextValue) {
                            ((TextValue) moduleValue).changeValue(value);
                        } else if (moduleValue instanceof ListValue) {
                            ((ListValue) moduleValue).changeValue(value);
                        }

                        ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §a§l" + module.getName() + "§7 value §8§l" + moduleValue.getName() + "§7 set to §c§l" + value + "§7.");
                    } catch (Exception exception1) {
                        ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §a§l" + exception1.getClass().getName() + "§7(" + exception1.getMessage() + ") §cAn Exception occurred while setting §a§l" + value + "§c to §a§l" + moduleValue.getName() + "§c in §a§l" + module.getName() + "§c.");
                    }
                    continue;
                }

                EntityUtils.targetPlayer = StringsKt.equals(args[1], "true", true);
                ClientUtils.displayChatMessage("§7[§3§lAutoSettings§7] §a§l" + args[0] + "§7 set to §c§l" + EntityUtils.targetPlayer + "§7.");
            }
        }

        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
    }

    @NotNull
    public final String generateScript(boolean values, boolean binds, boolean states) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterable $this$forEach$iv = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();
        boolean $i$f$forEach = false;
        Collection element$iv = (Collection) (new ArrayList());
        boolean it = false;
        Iterator $i$a$-forEach-SettingsUtils$generateScript$2 = $this$forEach$iv.iterator();

        while ($i$a$-forEach-SettingsUtils$generateScript$2.hasNext()) {
            Object $this$forEach$iv1 = $i$a$-forEach-SettingsUtils$generateScript$2.next();
            Module $i$f$forEach1 = (Module) $this$forEach$iv1;
            boolean $i$a$-filter-SettingsUtils$generateScript$1 = false;

            if ($i$f$forEach1.getCategory() != ModuleCategory.RENDER && !($i$f$forEach1 instanceof NameProtect) && !($i$f$forEach1 instanceof Spammer)) {
                element$iv.add($this$forEach$iv1);
            }
        }

        $this$forEach$iv = (Iterable) ((List) element$iv);
        $i$f$forEach = false;
        Iterator $this$filterTo$iv$iv = $this$forEach$iv.iterator();

        while ($this$filterTo$iv$iv.hasNext()) {
            Object element$iv2 = $this$filterTo$iv$iv.next();
            Module it1 = (Module) element$iv2;
            boolean $i$a$-forEach-SettingsUtils$generateScript$21 = false;

            if (values) {
                Iterable $this$forEach$iv2 = (Iterable) it1.getValues();
                boolean $i$f$forEach2 = false;
                Iterator $i$a$-filter-SettingsUtils$generateScript$11 = $this$forEach$iv2.iterator();

                while ($i$a$-filter-SettingsUtils$generateScript$11.hasNext()) {
                    Object element$iv1 = $i$a$-filter-SettingsUtils$generateScript$11.next();
                    Value value = (Value) element$iv1;
                    boolean $i$a$-forEach-SettingsUtils$generateScript$2$1 = false;

                    stringBuilder.append(it1.getName()).append(" ").append(value.getName()).append(" ").append(value.get()).append("\n");
                }
            }

            if (states) {
                stringBuilder.append(it1.getName()).append(" toggle ").append(it1.getState()).append("\n");
            }

            if (binds) {
                stringBuilder.append(it1.getName()).append(" bind ").append(Keyboard.getKeyName(it1.getKeyBind())).append("\n");
            }
        }

        String s = stringBuilder.toString();

        Intrinsics.checkExpressionValueIsNotNull(s, "stringBuilder.toString()");
        return s;
    }

    static {
        SettingsUtils settingsutils = new SettingsUtils();

        INSTANCE = settingsutils;
    }
}
