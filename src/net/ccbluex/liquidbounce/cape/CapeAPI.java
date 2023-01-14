package net.ccbluex.liquidbounce.cape;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IThreadDownloadImageData;
import net.ccbluex.liquidbounce.api.minecraft.client.render.WIImageBuffer;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.IAbstractTexture;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/cape/CapeAPI;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "capeService", "Lnet/ccbluex/liquidbounce/cape/CapeService;", "hasCapeService", "", "loadCape", "Lnet/ccbluex/liquidbounce/cape/CapeInfo;", "uuid", "Ljava/util/UUID;", "registerCapeService", "", "LiquidBounce"}
)
public final class CapeAPI extends MinecraftInstance {

    private static CapeService capeService;
    public static final CapeAPI INSTANCE;

    public final void registerCapeService() {
        JsonElement jsonelement = (new JsonParser()).parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/capes.json"));

        Intrinsics.checkExpressionValueIsNotNull(jsonelement, "JsonParser()\n           …IENT_CLOUD}/capes.json\"))");
        JsonObject jsonObject = jsonelement.getAsJsonObject();

        jsonelement = jsonObject.get("serviceType");
        Intrinsics.checkExpressionValueIsNotNull(jsonelement, "jsonObject.get(\"serviceType\")");
        String serviceType = jsonelement.getAsString();

        Intrinsics.checkExpressionValueIsNotNull(serviceType, "serviceType");
        boolean users = false;

        if (serviceType == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String s = serviceType.toLowerCase();

            Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
            String s1 = s;

            switch (s1.hashCode()) {
            case 96794:
                if (s1.equals("api")) {
                    jsonelement = jsonObject.get("api");
                    Intrinsics.checkExpressionValueIsNotNull(jsonelement, "jsonObject.get(\"api\")");
                    jsonelement = jsonelement.getAsJsonObject().get("url");
                    Intrinsics.checkExpressionValueIsNotNull(jsonelement, "jsonObject.get(\"api\").asJsonObject.get(\"url\")");
                    String users2 = jsonelement.getAsString();

                    Intrinsics.checkExpressionValueIsNotNull(users2, "url");
                    CapeAPI.capeService = (CapeService) (new ServiceAPI(users2));
                    ClientUtils.getLogger().info("Registered " + users2 + " as \'" + serviceType + "\' service type.");
                }
                break;

            case 3322014:
                if (s1.equals("list")) {
                    HashMap users1 = new HashMap();

                    jsonelement = jsonObject.get("users");
                    Intrinsics.checkExpressionValueIsNotNull(jsonelement, "jsonObject.get(\"users\")");
                    Iterator iterator = jsonelement.getAsJsonObject().entrySet().iterator();

                    while (iterator.hasNext()) {
                        Entry entry = (Entry) iterator.next();
                        boolean flag = false;
                        String key = (String) entry.getKey();

                        flag = false;
                        JsonElement value = (JsonElement) entry.getValue();
                        Map map = (Map) users1;

                        Intrinsics.checkExpressionValueIsNotNull(key, "key");
                        Intrinsics.checkExpressionValueIsNotNull(value, "value");
                        String s2 = value.getAsString();

                        Intrinsics.checkExpressionValueIsNotNull(s2, "value.asString");
                        map.put(key, s2);
                        ClientUtils.getLogger().info("Loaded user cape for \'" + key + "\'.");
                    }

                    CapeAPI.capeService = (CapeService) (new ServiceList((Map) users1));
                    ClientUtils.getLogger().info("Registered \'" + serviceType + "\' service type.");
                }
            }

            ClientUtils.getLogger().info("Loaded.");
        }
    }

    @Nullable
    public final CapeInfo loadCape(@NotNull UUID uuid) {
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        CapeService capeservice = CapeAPI.capeService;

        if (CapeAPI.capeService != null) {
            String s = capeservice.getCape(uuid);

            if (s != null) {
                String url = s;
                IClassProvider iclassprovider = LiquidBounce.INSTANCE.getWrapper().getClassProvider();
                String capeInfo = "capes/%s.png";
                Object[] threadDownloadImageData = new Object[] { uuid.toString()};
                IClassProvider iclassprovider1 = iclassprovider;
                boolean flag = false;

                s = String.format(capeInfo, Arrays.copyOf(threadDownloadImageData, threadDownloadImageData.length));
                Intrinsics.checkExpressionValueIsNotNull(s, "java.lang.String.format(this, *args)");
                String s1 = s;
                IResourceLocation resourceLocation = iclassprovider1.createResourceLocation(s1);
                final CapeInfo capeInfo1 = new CapeInfo(resourceLocation, false, 2, (DefaultConstructorMarker) null);
                IThreadDownloadImageData threadDownloadImageData1 = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createThreadDownloadImageData((File) null, url, (IResourceLocation) null, (WIImageBuffer) (new WIImageBuffer() {
                    @Nullable
                    public BufferedImage parseUserSkin(@Nullable BufferedImage image) {
                        return image;
                    }

                    public void skinAvailable() {
                        capeInfo1.setCapeAvailable(true);
                    }
                }));

                MinecraftInstance.mc.getTextureManager().loadTexture(resourceLocation, (IAbstractTexture) threadDownloadImageData1);
                return capeInfo1;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public final boolean hasCapeService() {
        return CapeAPI.capeService != null;
    }

    static {
        CapeAPI capeapi = new CapeAPI();

        INSTANCE = capeapi;
    }
}
