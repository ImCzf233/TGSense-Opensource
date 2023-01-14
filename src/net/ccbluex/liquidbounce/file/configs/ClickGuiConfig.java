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
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.ui.client.clickgui.Panel;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.utils.ClientUtils;

public class ClickGuiConfig extends FileConfig {

    public ClickGuiConfig(File file) {
        super(file);
    }

    protected void loadConfig() throws IOException {
        JsonElement jsonElement = (new JsonParser()).parse(new BufferedReader(new FileReader(this.getFile())));

        if (!(jsonElement instanceof JsonNull)) {
            JsonObject jsonObject = (JsonObject) jsonElement;
            Iterator iterator = LiquidBounce.clickGui.panels.iterator();

            while (iterator.hasNext()) {
                Panel panel = (Panel) iterator.next();

                if (jsonObject.has(panel.getName())) {
                    try {
                        JsonObject e = jsonObject.getAsJsonObject(panel.getName());

                        panel.setOpen(e.get("open").getAsBoolean());
                        panel.setVisible(e.get("visible").getAsBoolean());
                        panel.setX(e.get("posX").getAsInt());
                        panel.setY(e.get("posY").getAsInt());
                        Iterator iterator1 = panel.getElements().iterator();

                        while (iterator1.hasNext()) {
                            Element element = (Element) iterator1.next();

                            if (element instanceof ModuleElement) {
                                ModuleElement moduleElement = (ModuleElement) element;

                                if (e.has(moduleElement.getModule().getName())) {
                                    try {
                                        JsonObject e1 = e.getAsJsonObject(moduleElement.getModule().getName());

                                        moduleElement.setShowSettings(e1.get("Settings").getAsBoolean());
                                    } catch (Exception exception) {
                                        ClientUtils.getLogger().error("Error while loading clickgui module element with the name \'" + moduleElement.getModule().getName() + "\' (Panel Name: " + panel.getName() + ").", exception);
                                    }
                                }
                            }
                        }
                    } catch (Exception exception1) {
                        ClientUtils.getLogger().error("Error while loading clickgui panel with the name \'" + panel.getName() + "\'.", exception1);
                    }
                }
            }

        }
    }

    protected void saveConfig() throws IOException {
        JsonObject jsonObject = new JsonObject();
        Iterator printWriter = LiquidBounce.clickGui.panels.iterator();

        while (printWriter.hasNext()) {
            Panel panel = (Panel) printWriter.next();
            JsonObject panelObject = new JsonObject();

            panelObject.addProperty("open", Boolean.valueOf(panel.getOpen()));
            panelObject.addProperty("visible", Boolean.valueOf(panel.isVisible()));
            panelObject.addProperty("posX", Integer.valueOf(panel.getX()));
            panelObject.addProperty("posY", Integer.valueOf(panel.getY()));
            Iterator iterator = panel.getElements().iterator();

            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();

                if (element instanceof ModuleElement) {
                    ModuleElement moduleElement = (ModuleElement) element;
                    JsonObject elementObject = new JsonObject();

                    elementObject.addProperty("Settings", Boolean.valueOf(moduleElement.isShowSettings()));
                    panelObject.add(moduleElement.getModule().getName(), elementObject);
                }
            }

            jsonObject.add(panel.getName(), panelObject);
        }

        PrintWriter printWriter1 = new PrintWriter(new FileWriter(this.getFile()));

        printWriter1.println(FileManager.PRETTY_GSON.toJson(jsonObject));
        printWriter1.close();
    }
}
