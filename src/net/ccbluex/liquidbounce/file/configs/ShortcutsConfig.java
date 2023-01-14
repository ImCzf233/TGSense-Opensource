package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.shortcuts.Shortcut;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0014J\b\u0010\u0007\u001a\u00020\u0006H\u0014¨\u0006\b"},
    d2 = { "Lnet/ccbluex/liquidbounce/file/configs/ShortcutsConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "loadConfig", "", "saveConfig", "LiquidBounce"}
)
public final class ShortcutsConfig extends FileConfig {

    protected void loadConfig() {
        JsonParser jsonparser = new JsonParser();
        File file = this.getFile();

        Intrinsics.checkExpressionValueIsNotNull(file, "file");
        JsonElement jsonElement = jsonparser.parse(FilesKt.readText$default(file, (Charset) null, 1, (Object) null));

        if (jsonElement instanceof JsonArray) {
            Iterator iterator = ((JsonArray) jsonElement).iterator();

            while (iterator.hasNext()) {
                JsonElement shortcutJson = (JsonElement) iterator.next();

                if (shortcutJson instanceof JsonObject) {
                    JsonElement jsonelement = ((JsonObject) shortcutJson).get("name");

                    if (jsonelement != null) {
                        String s = jsonelement.getAsString();

                        if (s != null) {
                            String name = s;

                            jsonelement = ((JsonObject) shortcutJson).get("script");
                            if (jsonelement != null) {
                                JsonArray jsonarray = jsonelement.getAsJsonArray();

                                if (jsonarray != null) {
                                    JsonArray scriptJson = jsonarray;
                                    boolean scriptCommand = false;
                                    List script = (List) (new ArrayList());
                                    Iterator iterator1 = scriptJson.iterator();

                                    while (iterator1.hasNext()) {
                                        JsonElement scriptCommand1 = (JsonElement) iterator1.next();

                                        if (scriptCommand1 instanceof JsonObject) {
                                            jsonelement = ((JsonObject) scriptCommand1).get("name");
                                            if (jsonelement != null) {
                                                s = jsonelement.getAsString();
                                                if (s != null) {
                                                    String commandName = s;

                                                    jsonelement = ((JsonObject) scriptCommand1).get("arguments");
                                                    if (jsonelement != null) {
                                                        jsonarray = jsonelement.getAsJsonArray();
                                                        if (jsonarray != null) {
                                                            JsonArray arguments = jsonarray;
                                                            Command command = LiquidBounce.INSTANCE.getCommandManager().getCommand(commandName);

                                                            if (command != null) {
                                                                Command command = command;
                                                                Iterable $this$toTypedArray$iv = (Iterable) arguments;
                                                                boolean $i$f$toTypedArray = false;
                                                                Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$toTypedArray$iv, 10)));
                                                                boolean $i$f$mapTo = false;
                                                                Iterator iterator2 = $this$toTypedArray$iv.iterator();

                                                                while (iterator2.hasNext()) {
                                                                    Object item$iv$iv = iterator2.next();
                                                                    JsonElement it = (JsonElement) item$iv$iv;
                                                                    boolean $i$a$-map-ShortcutsConfig$loadConfig$1 = false;

                                                                    Intrinsics.checkExpressionValueIsNotNull(it, "it");
                                                                    String s1 = it.getAsString();

                                                                    destination$iv$iv.add(s1);
                                                                }

                                                                List list = (List) destination$iv$iv;
                                                                Collection $this$toTypedArray$iv1 = (Collection) list;

                                                                $i$f$toTypedArray = false;
                                                                Object[] aobject = $this$toTypedArray$iv1.toArray(new String[0]);

                                                                if (aobject == null) {
                                                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                                                                }

                                                                Object[] aobject1 = aobject;

                                                                script.add(new Pair(command, aobject1));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    LiquidBounce.INSTANCE.getCommandManager().registerCommand((Command) (new Shortcut(name, script)));
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    protected void saveConfig() {
        JsonArray jsonArray = new JsonArray();
        Iterator iterator = LiquidBounce.INSTANCE.getCommandManager().getCommands().iterator();

        while (iterator.hasNext()) {
            Command command = (Command) iterator.next();

            if (command instanceof Shortcut) {
                JsonObject jsonCommand = new JsonObject();

                jsonCommand.addProperty("name", command.getCommand());
                JsonArray scriptArray = new JsonArray();
                Iterator iterator1 = ((Shortcut) command).getScript().iterator();

                while (iterator1.hasNext()) {
                    Pair pair = (Pair) iterator1.next();
                    JsonObject pairObject = new JsonObject();

                    pairObject.addProperty("name", ((Command) pair.getFirst()).getCommand());
                    JsonArray argumentsObject = new JsonArray();
                    String[] astring = (String[]) pair.getSecond();
                    int i = astring.length;

                    for (int j = 0; j < i; ++j) {
                        String argument = astring[j];

                        argumentsObject.add(argument);
                    }

                    pairObject.add("arguments", (JsonElement) argumentsObject);
                    scriptArray.add((JsonElement) pairObject);
                }

                jsonCommand.add("script", (JsonElement) scriptArray);
                jsonArray.add((JsonElement) jsonCommand);
            }
        }

        File file = this.getFile();

        Intrinsics.checkExpressionValueIsNotNull(file, "file");
        String s = FileManager.PRETTY_GSON.toJson((JsonElement) jsonArray);

        Intrinsics.checkExpressionValueIsNotNull(s, "FileManager.PRETTY_GSON.toJson(jsonArray)");
        FilesKt.writeText$default(file, s, (Charset) null, 2, (Object) null);
    }

    public ShortcutsConfig(@NotNull File file) {
        Intrinsics.checkParameterIsNotNull(file, "file");
        super(file);
    }
}
