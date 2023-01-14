package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;

public class ModulesConfig extends FileConfig {

    public ModulesConfig(File file) {
        super(file);
    }

    protected void loadConfig() throws IOException {
        JsonElement jsonElement = (new JsonParser()).parse(new BufferedReader(new FileReader(this.getFile())));

        if (!(jsonElement instanceof JsonNull)) {
            Iterator entryIterator = jsonElement.getAsJsonObject().entrySet().iterator();

            while (entryIterator.hasNext()) {
                Entry entry = (Entry) entryIterator.next();
                Module module = LiquidBounce.moduleManager.getModule((String) entry.getKey());

                if (module != null) {
                    JsonObject jsonModule = (JsonObject) entry.getValue();

                    module.setState(jsonModule.get("State").getAsBoolean());
                    module.setKeyBind(jsonModule.get("KeyBind").getAsInt());
                    if (jsonModule.has("Array")) {
                        module.setArray(jsonModule.get("Array").getAsBoolean());
                    }
                }
            }

        }
    }

    protected void saveConfig() throws IOException {
        JsonObject jsonObject = new JsonObject();
        Iterator printWriter = LiquidBounce.moduleManager.getModules().iterator();

        while (printWriter.hasNext()) {
            Module module = (Module) printWriter.next();
            JsonObject jsonMod = new JsonObject();

            jsonMod.addProperty("State", Boolean.valueOf(module.getState()));
            jsonMod.addProperty("KeyBind", Integer.valueOf(module.getKeyBind()));
            jsonMod.addProperty("Array", Boolean.valueOf(module.getArray()));
            jsonObject.add(module.getName(), jsonMod);
        }

        PrintWriter printWriter1 = new PrintWriter(new FileWriter(this.getFile()));

        printWriter1.println(FileManager.PRETTY_GSON.toJson(jsonObject));
        printWriter1.close();
    }
}
