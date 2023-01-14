package net.ccbluex.liquidbounce.utils.login;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/login/UserUtils;", "", "()V", "getUUID", "", "username", "getUsername", "uuid", "isValidToken", "", "token", "isValidTokenOffline", "LiquidBounce"}
)
public final class UserUtils {

    public static final UserUtils INSTANCE;

    public final boolean isValidTokenOffline(@NotNull String token) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        return token.length() >= 32;
    }

    public final boolean isValidToken(@NotNull String token) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        CloseableHttpClient client = HttpClients.createDefault();
        BasicHeader[] headers = new BasicHeader[] { new BasicHeader("Content-Type", "application/json")};
        HttpPost request = new HttpPost("https://authserver.mojang.com/validate");

        request.setHeaders((Header[]) headers);
        JsonObject body = new JsonObject();

        body.addProperty("accessToken", token);
        request.setEntity((HttpEntity) (new StringEntity((new Gson()).toJson((JsonElement) body))));
        CloseableHttpResponse response = client.execute((HttpUriRequest) request);

        Intrinsics.checkExpressionValueIsNotNull(response, "response");
        StatusLine statusline = response.getStatusLine();

        Intrinsics.checkExpressionValueIsNotNull(statusline, "response.statusLine");
        return statusline.getStatusCode() == 204;
    }

    @Nullable
    public final String getUsername(@NotNull String uuid) {
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://api.mojang.com/user/profiles/" + uuid + "/names");
        CloseableHttpResponse response = client.execute((HttpUriRequest) request);

        Intrinsics.checkExpressionValueIsNotNull(response, "response");
        StatusLine statusline = response.getStatusLine();

        Intrinsics.checkExpressionValueIsNotNull(statusline, "response.statusLine");
        if (statusline.getStatusCode() != 200) {
            return null;
        } else {
            JsonElement jsonelement = (new JsonParser()).parse(EntityUtils.toString(response.getEntity()));

            Intrinsics.checkExpressionValueIsNotNull(jsonelement, "JsonParser().parse(Entit…oString(response.entity))");
            JsonArray names = jsonelement.getAsJsonArray();

            jsonelement = names.get(names.size() - 1);
            Intrinsics.checkExpressionValueIsNotNull(jsonelement, "names.get(names.size() - 1)");
            jsonelement = jsonelement.getAsJsonObject().get("name");
            Intrinsics.checkExpressionValueIsNotNull(jsonelement, "names.get(names.size() -�?.asJsonObject.get(\"name\")");
            return jsonelement.getAsString();
        }
    }

    @NotNull
    public final String getUUID(@NotNull String username) {
        Intrinsics.checkParameterIsNotNull(username, "username");

        try {
            URLConnection urlconnection = (new URL("https://api.mojang.com/users/profiles/minecraft/" + username)).openConnection();

            if (urlconnection == null) {
                throw new TypeCastException("null cannot be cast to non-null type javax.net.ssl.HttpsURLConnection");
            } else {
                HttpsURLConnection httpConnection = (HttpsURLConnection) urlconnection;

                httpConnection.setConnectTimeout(2000);
                httpConnection.setReadTimeout(2000);
                httpConnection.setRequestMethod("GET");
                httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                HttpURLConnection.setFollowRedirects(true);
                httpConnection.setDoOutput(true);
                if (httpConnection.getResponseCode() != 200) {
                    return "";
                } else {
                    Closeable closeable = (Closeable) (new InputStreamReader(httpConnection.getInputStream()));
                    boolean flag = false;
                    Throwable throwable = (Throwable) null;

                    String s;

                    try {
                        InputStreamReader it = (InputStreamReader) closeable;
                        boolean $i$a$-use-UserUtils$getUUID$1 = false;
                        JsonElement jsonElement = (new JsonParser()).parse((Reader) it);

                        Intrinsics.checkExpressionValueIsNotNull(jsonElement, "jsonElement");
                        if (!jsonElement.isJsonObject()) {
                            Unit it1 = Unit.INSTANCE;

                            return "";
                        }

                        JsonElement jsonelement = jsonElement.getAsJsonObject().get("id");

                        Intrinsics.checkExpressionValueIsNotNull(jsonelement, "jsonElement.asJsonObject.get(\"id\")");
                        String s1 = jsonelement.getAsString();

                        Intrinsics.checkExpressionValueIsNotNull(s1, "jsonElement.asJsonObject.get(\"id\").asString");
                        s = s1;
                    } catch (Throwable throwable1) {
                        throwable = throwable1;
                        throw throwable1;
                    } finally {
                        CloseableKt.closeFinally(closeable, throwable);
                    }

                    return s;
                }
            }
        } catch (Throwable throwable2) {
            return "";
        }
    }

    static {
        UserUtils userutils = new UserUtils();

        INSTANCE = userutils;
    }
}
