package net.ccbluex.liquidbounce.features.special;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence.Builder;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0006\u0010\u0016\u001a\u00020\u0015J\u0006\u0010\u0017\u001a\u00020\u0015J\u0006\u0010\u0018\u001a\u00020\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/special/ClientRichPresence;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "appID", "", "assets", "", "", "ipcClient", "Lcom/jagrosh/discordipc/IPCClient;", "running", "", "showRichPresenceValue", "getShowRichPresenceValue", "()Z", "setShowRichPresenceValue", "(Z)V", "timestamp", "Ljava/time/OffsetDateTime;", "kotlin.jvm.PlatformType", "loadConfiguration", "", "setup", "shutdown", "update", "LiquidBounce"}
)
public final class ClientRichPresence extends MinecraftInstance {

    private boolean showRichPresenceValue = true;
    private IPCClient ipcClient;
    private long appID;
    private final Map assets;
    private final OffsetDateTime timestamp;
    private boolean running;

    public final boolean getShowRichPresenceValue() {
        return this.showRichPresenceValue;
    }

    public final void setShowRichPresenceValue(boolean <set-?>) {
        this.showRichPresenceValue = <set-?>;
    }

    public final void setup() {
        try {
            this.running = true;
            this.loadConfiguration();
            this.ipcClient = new IPCClient(this.appID);
            IPCClient ipcclient = this.ipcClient;

            if (this.ipcClient != null) {
                ipcclient.setListener((IPCListener) (new IPCListener() {
                    public void onReady(@Nullable IPCClient client) {
                        ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) (new Function0(0) {
                            public final void invoke() {
                                while (ClientRichPresence.this.running) {
                                    ClientRichPresence.this.update();

                                    try {
                                        Thread.sleep(1000L);
                                    } catch (InterruptedException interruptedexception) {
                                        ;
                                    }
                                }

                            }
                        }), 31, (Object) null);
                    }

                    public void onClose(@Nullable IPCClient client, @Nullable JSONObject json) {
                        ClientRichPresence.this.running = false;
                    }
                }));
            }

            ipcclient = this.ipcClient;
            if (this.ipcClient != null) {
                ipcclient.connect(new DiscordBuild[0]);
            }
        } catch (Throwable throwable) {
            ClientUtils.getLogger().error("Failed to setup Discord RPC.", throwable);
        }

    }

    public final void update() {
        Builder builder = new Builder();

        builder.setStartTimestamp(this.timestamp);
        if (this.assets.containsKey("logo")) {
            builder.setLargeImage((String) this.assets.get("logo"), "MC 1.12.2 - TGSense b230116");
        }

        if (MinecraftInstance.mc.getThePlayer() != null) {
            IServerData serverData = MinecraftInstance.mc.getCurrentServerData();

            builder.setDetails("Server: " + (!MinecraftInstance.mc.isIntegratedServerRunning() && serverData != null ? serverData.getServerIP() : "Singleplayer"));
            StringBuilder stringbuilder = (new StringBuilder()).append("Enabled ");
            Iterable $this$count$iv = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();
            StringBuilder stringbuilder1 = stringbuilder;
            boolean $i$f$count = false;
            int i;

            if ($this$count$iv instanceof Collection && ((Collection) $this$count$iv).isEmpty()) {
                i = 0;
            } else {
                int count$iv = 0;
                Iterator iterator = $this$count$iv.iterator();

                while (iterator.hasNext()) {
                    Object element$iv = iterator.next();
                    Module it = (Module) element$iv;
                    boolean $i$a$-count-ClientRichPresence$update$1 = false;

                    if (it.getState()) {
                        ++count$iv;
                        $i$a$-count-ClientRichPresence$update$1 = false;
                        if (count$iv < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }

                i = count$iv;
            }

            int j = i;

            builder.setState(stringbuilder1.append(j).append(" of ").append(LiquidBounce.INSTANCE.getModuleManager().getModules().size()).append(" modules").toString());
        }

        if ((this.ipcClient != null ? this.ipcClient.getStatus() : null) == PipeStatus.CONNECTED) {
            IPCClient ipcclient = this.ipcClient;

            if (this.ipcClient != null) {
                ipcclient.sendRichPresence(builder.build());
            }
        }

    }

    public final void shutdown() {
        if ((this.ipcClient != null ? this.ipcClient.getStatus() : null) == PipeStatus.CONNECTED) {
            try {
                IPCClient ipcclient = this.ipcClient;

                if (this.ipcClient != null) {
                    ipcclient.close();
                }
            } catch (Throwable throwable) {
                ClientUtils.getLogger().error("Failed to close Discord RPC.", throwable);
            }

        }
    }

    private final void loadConfiguration() {
        JsonElement json = (new JsonParser()).parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/discord.json"));

        if (json instanceof JsonObject) {
            if (((JsonObject) json).has("appID")) {
                JsonElement jsonelement = ((JsonObject) json).get("appID");

                Intrinsics.checkExpressionValueIsNotNull(jsonelement, "json.get(\"appID\")");
                this.appID = jsonelement.getAsLong();
            }

            JsonElement jsonelement1 = ((JsonObject) json).get("assets");

            Intrinsics.checkExpressionValueIsNotNull(jsonelement1, "json.get(\"assets\")");
            Iterator iterator = jsonelement1.getAsJsonObject().entrySet().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();
                boolean flag = false;
                String key = (String) entry.getKey();

                flag = false;
                JsonElement value = (JsonElement) entry.getValue();
                Map map = this.assets;

                Intrinsics.checkExpressionValueIsNotNull(key, "key");
                Intrinsics.checkExpressionValueIsNotNull(value, "value");
                String s = value.getAsString();

                Intrinsics.checkExpressionValueIsNotNull(s, "value.asString");
                map.put(key, s);
            }

        }
    }

    public ClientRichPresence() {
        boolean flag = false;
        Map map = (Map) (new LinkedHashMap());

        this.assets = map;
        this.timestamp = OffsetDateTime.now();
    }
}
