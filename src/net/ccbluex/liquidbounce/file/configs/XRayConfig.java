package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;

public class XRayConfig extends FileConfig {

    public XRayConfig(File file) {
        super(file);
    }

    protected void loadConfig() throws IOException {
        XRay xRay = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);

        if (xRay == null) {
            ClientUtils.getLogger().error("[FileManager] Failed to find xray module.");
        } else {
            JsonArray jsonArray = (new JsonParser()).parse(new BufferedReader(new FileReader(this.getFile()))).getAsJsonArray();

            xRay.getXrayBlocks().clear();
            Iterator iterator = jsonArray.iterator();

            while (iterator.hasNext()) {
                JsonElement jsonElement = (JsonElement) iterator.next();

                try {
                    IBlock throwable = LiquidBounce.wrapper.getFunctions().getBlockFromName(jsonElement.getAsString());

                    if (xRay.getXrayBlocks().contains(throwable)) {
                        ClientUtils.getLogger().error("[FileManager] Skipped xray block \'" + throwable.getRegistryName() + "\' because the block is already added.");
                    } else {
                        xRay.getXrayBlocks().add(throwable);
                    }
                } catch (Throwable throwable) {
                    ClientUtils.getLogger().error("[FileManager] Failed to add block to xray.", throwable);
                }
            }

        }
    }

    protected void saveConfig() throws IOException {
        XRay xRay = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);

        if (xRay == null) {
            ClientUtils.getLogger().error("[FileManager] Failed to find xray module.");
        } else {
            JsonArray jsonArray = new JsonArray();
            Iterator printWriter = xRay.getXrayBlocks().iterator();

            while (printWriter.hasNext()) {
                IBlock block = (IBlock) printWriter.next();

                jsonArray.add(FileManager.PRETTY_GSON.toJsonTree(Integer.valueOf(LiquidBounce.wrapper.getFunctions().getIdFromBlock(block))));
            }

            PrintWriter printWriter1 = new PrintWriter(new FileWriter(this.getFile()));

            printWriter1.println(FileManager.PRETTY_GSON.toJson(jsonArray));
            printWriter1.close();
        }
    }
}
