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
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.AutoReconnect;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.ui.client.GuiBackground;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDonatorCape;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.value.Value;

public class ValuesConfig extends FileConfig {

    public ValuesConfig(File file) {
        super(file);
    }

    protected void loadConfig() throws IOException {
        JsonElement jsonElement = (new JsonParser()).parse(new BufferedReader(new FileReader(this.getFile())));

        if (!(jsonElement instanceof JsonNull)) {
            JsonObject jsonObject = (JsonObject) jsonElement;
            Iterator iterator = jsonObject.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();

                if (((String) entry.getKey()).equalsIgnoreCase("CommandPrefix")) {
                    LiquidBounce.commandManager.setPrefix(((JsonElement) entry.getValue()).getAsCharacter());
                } else if (((String) entry.getKey()).equalsIgnoreCase("ShowRichPresence")) {
                    LiquidBounce.clientRichPresence.setShowRichPresenceValue(((JsonElement) entry.getValue()).getAsBoolean());
                } else {
                    JsonObject module1;

                    if (((String) entry.getKey()).equalsIgnoreCase("targets")) {
                        module1 = (JsonObject) entry.getValue();
                        if (module1.has("TargetPlayer")) {
                            EntityUtils.targetPlayer = module1.get("TargetPlayer").getAsBoolean();
                        }

                        if (module1.has("TargetMobs")) {
                            EntityUtils.targetMobs = module1.get("TargetMobs").getAsBoolean();
                        }

                        if (module1.has("TargetAnimals")) {
                            EntityUtils.targetAnimals = module1.get("TargetAnimals").getAsBoolean();
                        }

                        if (module1.has("TargetInvisible")) {
                            EntityUtils.targetInvisible = module1.get("TargetInvisible").getAsBoolean();
                        }

                        if (module1.has("TargetDead")) {
                            EntityUtils.targetDead = module1.get("TargetDead").getAsBoolean();
                        }
                    } else if (((String) entry.getKey()).equalsIgnoreCase("features")) {
                        module1 = (JsonObject) entry.getValue();
                        if (module1.has("AntiForge")) {
                            AntiForge.enabled = module1.get("AntiForge").getAsBoolean();
                        }

                        if (module1.has("AntiForgeFML")) {
                            AntiForge.blockFML = module1.get("AntiForgeFML").getAsBoolean();
                        }

                        if (module1.has("AntiForgeProxy")) {
                            AntiForge.blockProxyPacket = module1.get("AntiForgeProxy").getAsBoolean();
                        }

                        if (module1.has("AntiForgePayloads")) {
                            AntiForge.blockPayloadPackets = module1.get("AntiForgePayloads").getAsBoolean();
                        }

                        if (module1.has("BungeeSpoof")) {
                            BungeeCordSpoof.enabled = module1.get("BungeeSpoof").getAsBoolean();
                        }

                        if (module1.has("AutoReconnectDelay")) {
                            AutoReconnect.INSTANCE.setDelay(module1.get("AutoReconnectDelay").getAsInt());
                        }
                    } else if (((String) entry.getKey()).equalsIgnoreCase("thealtening")) {
                        module1 = (JsonObject) entry.getValue();
                        if (module1.has("API-Key")) {
                            GuiTheAltening.Companion.setApiKey(module1.get("API-Key").getAsString());
                        }
                    } else if (((String) entry.getKey()).equalsIgnoreCase("liquidchat")) {
                        module1 = (JsonObject) entry.getValue();
                        if (module1.has("token")) {
                            LiquidChat.Companion.setJwtToken(module1.get("token").getAsString());
                        }
                    } else if (((String) entry.getKey()).equalsIgnoreCase("DonatorCape")) {
                        module1 = (JsonObject) entry.getValue();
                        if (module1.has("TransferCode")) {
                            GuiDonatorCape.Companion.setTransferCode(module1.get("TransferCode").getAsString());
                        }

                        if (module1.has("CapeEnabled")) {
                            GuiDonatorCape.Companion.setCapeEnabled(module1.get("CapeEnabled").getAsBoolean());
                        }
                    } else if (((String) entry.getKey()).equalsIgnoreCase("Background")) {
                        module1 = (JsonObject) entry.getValue();
                        if (module1.has("Enabled")) {
                            GuiBackground.Companion.setEnabled(module1.get("Enabled").getAsBoolean());
                        }

                        if (module1.has("Particles")) {
                            GuiBackground.Companion.setParticles(module1.get("Particles").getAsBoolean());
                        }
                    } else {
                        Module module = LiquidBounce.moduleManager.getModule((String) entry.getKey());

                        if (module != null) {
                            JsonObject jsonModule = (JsonObject) entry.getValue();
                            Iterator iterator = module.getValues().iterator();

                            while (iterator.hasNext()) {
                                Value moduleValue = (Value) iterator.next();
                                JsonElement element = jsonModule.get(moduleValue.getName());

                                if (element != null) {
                                    moduleValue.fromJson(element);
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    protected void saveConfig() throws IOException {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("CommandPrefix", Character.valueOf(LiquidBounce.commandManager.getPrefix()));
        jsonObject.addProperty("ShowRichPresence", Boolean.valueOf(LiquidBounce.clientRichPresence.getShowRichPresenceValue()));
        JsonObject jsonTargets = new JsonObject();

        jsonTargets.addProperty("TargetPlayer", Boolean.valueOf(EntityUtils.targetPlayer));
        jsonTargets.addProperty("TargetMobs", Boolean.valueOf(EntityUtils.targetMobs));
        jsonTargets.addProperty("TargetAnimals", Boolean.valueOf(EntityUtils.targetAnimals));
        jsonTargets.addProperty("TargetInvisible", Boolean.valueOf(EntityUtils.targetInvisible));
        jsonTargets.addProperty("TargetDead", Boolean.valueOf(EntityUtils.targetDead));
        jsonObject.add("targets", jsonTargets);
        JsonObject jsonFeatures = new JsonObject();

        jsonFeatures.addProperty("AntiForge", Boolean.valueOf(AntiForge.enabled));
        jsonFeatures.addProperty("AntiForgeFML", Boolean.valueOf(AntiForge.blockFML));
        jsonFeatures.addProperty("AntiForgeProxy", Boolean.valueOf(AntiForge.blockProxyPacket));
        jsonFeatures.addProperty("AntiForgePayloads", Boolean.valueOf(AntiForge.blockPayloadPackets));
        jsonFeatures.addProperty("BungeeSpoof", Boolean.valueOf(BungeeCordSpoof.enabled));
        jsonFeatures.addProperty("AutoReconnectDelay", Integer.valueOf(AutoReconnect.INSTANCE.getDelay()));
        jsonObject.add("features", jsonFeatures);
        JsonObject theAlteningObject = new JsonObject();

        theAlteningObject.addProperty("API-Key", GuiTheAltening.Companion.getApiKey());
        jsonObject.add("thealtening", theAlteningObject);
        JsonObject liquidChatObject = new JsonObject();

        liquidChatObject.addProperty("token", LiquidChat.Companion.getJwtToken());
        jsonObject.add("liquidchat", liquidChatObject);
        JsonObject capeObject = new JsonObject();

        capeObject.addProperty("TransferCode", GuiDonatorCape.Companion.getTransferCode());
        capeObject.addProperty("CapeEnabled", Boolean.valueOf(GuiDonatorCape.Companion.getCapeEnabled()));
        jsonObject.add("DonatorCape", capeObject);
        JsonObject backgroundObject = new JsonObject();

        backgroundObject.addProperty("Enabled", Boolean.valueOf(GuiBackground.Companion.getEnabled()));
        backgroundObject.addProperty("Particles", Boolean.valueOf(GuiBackground.Companion.getParticles()));
        jsonObject.add("Background", backgroundObject);
        LiquidBounce.moduleManager.getModules().stream().filter((module) -> {
            return !module.getValues().isEmpty();
        }).forEach((module) -> {
            JsonObject jsonModule = new JsonObject();

            module.getValues().forEach((value) -> {
                jsonModule.add(value.getName(), value.toJson());
            });
            jsonObject.add(module.getName(), jsonModule);
        });
        PrintWriter printWriter = new PrintWriter(new FileWriter(this.getFile()));

        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonObject));
        printWriter.close();
    }
}
