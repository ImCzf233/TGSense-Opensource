package net.vitox.mcleaks;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MCLeaks {

    private static Session session;
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static final Gson gson = new Gson();

    public static boolean isAltActive() {
        return MCLeaks.session != null;
    }

    public static Session getSession() {
        return MCLeaks.session;
    }

    public static void refresh(Session session) {
        MCLeaks.session = session;
    }

    public static void remove() {
        MCLeaks.session = null;
    }

    public static void redeem(String token, Callback callback) {
        MCLeaks.EXECUTOR_SERVICE.execute(() -> {
            URLConnection connection = preparePostRequest("{\"token\":\"" + token + "\"}");

            if (connection == null) {
                callback.done("An error occurred! [R1]");
            } else {
                Object o = getResult(connection);

                if (o instanceof String) {
                    callback.done(o);
                } else {
                    JsonObject jsonObject = (JsonObject) o;

                    if (jsonObject != null) {
                        if (jsonObject.has("mcname") && jsonObject.has("session")) {
                            callback.done(new RedeemResponse(jsonObject.get("mcname").getAsString(), jsonObject.get("session").getAsString()));
                        } else {
                            callback.done("An error occurred! [R2]");
                        }
                    }
                }
            }
        });
    }

    private static URLConnection preparePostRequest(String body) {
        try {
            HttpsURLConnection e = (HttpsURLConnection) (new URL("https://auth.mcleaks.net/v1/redeem")).openConnection();

            e.setConnectTimeout(10000);
            e.setReadTimeout(10000);
            e.setRequestMethod("POST");
            e.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201");
            e.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(e.getOutputStream());

            dataOutputStream.write(body.getBytes(StandardCharsets.UTF_8));
            dataOutputStream.flush();
            dataOutputStream.close();
            return e;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static Object getResult(URLConnection urlConnection) {
        try {
            BufferedReader e = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line;

            while ((line = e.readLine()) != null) {
                stringBuilder.append(line);
            }

            e.close();
            JsonElement jsonElement = (JsonElement) MCLeaks.gson.fromJson(stringBuilder.toString(), JsonElement.class);

            return jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("success") ? (!jsonElement.getAsJsonObject().get("success").getAsBoolean() ? (jsonElement.getAsJsonObject().has("errorMessage") ? jsonElement.getAsJsonObject().get("errorMessage").getAsString() : "An error occurred! [G4]") : (!jsonElement.getAsJsonObject().has("result") ? "An error occurred! [G3]" : (jsonElement.getAsJsonObject().get("result").isJsonObject() ? jsonElement.getAsJsonObject().get("result").getAsJsonObject() : null))) : "An error occurred! [G1]";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "An error occurred! [G2]";
        }
    }
}
