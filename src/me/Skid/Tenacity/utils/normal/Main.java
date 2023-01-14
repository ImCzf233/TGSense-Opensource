package me.Skid.Tenacity.utils.normal;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleManager;

public class Main {

    public static int categoryCount;
    public static boolean reloadModules;
    public static float allowedClickGuiHeight = 300.0F;

    public static List getModulesInCategory(ModuleCategory c, ModuleManager moduleManager) {
        return (List) moduleManager.getModules().stream().filter((m) -> {
            return m.getCategory() == c;
        }).collect(Collectors.toList());
    }
}
