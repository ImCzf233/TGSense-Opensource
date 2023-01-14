package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.ccbluex.liquidbounce.injection.backend.ISPacketEncryptionRequestKt;
import net.ccbluex.liquidbounce.injection.backend.NetworkManagerImplKt;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import net.minecraft.util.CryptManager;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.vitox.mcleaks.MCLeaks;
import net.vitox.mcleaks.Session;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({ NetHandlerLoginClient.class})
public class MixinNetHandlerLoginClient {

    @Shadow
    @Final
    private NetworkManager networkManager;

    @Inject(
        method = { "handleEncryptionRequest"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void handleEncryptionRequest(SPacketEncryptionRequest packetIn, CallbackInfo callbackInfo) {
        if (MCLeaks.isAltActive()) {
            SecretKey secretkey = CryptManager.createNewSharedKey();
            String s = packetIn.getServerId();
            PublicKey publickey = packetIn.getPublicKey();
            String s1 = (new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey))).toString(16);
            Session session = MCLeaks.getSession();
            String server = ((InetSocketAddress) this.networkManager.getRemoteAddress()).getHostName() + ":" + ((InetSocketAddress) this.networkManager.getRemoteAddress()).getPort();

            try {
                label45: {
                    String e = "{\"session\":\"" + session.getToken() + "\",\"mcname\":\"" + session.getUsername() + "\",\"serverhash\":\"" + s1 + "\",\"server\":\"" + server + "\"}";
                    HttpURLConnection connection = (HttpURLConnection) (new URL("https://auth.mcleaks.net/v1/joinserver")).openConnection();

                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                    connection.setDoOutput(true);
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

                    outputStream.write(e.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                    outputStream.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder outputBuilder = new StringBuilder();

                    String line;

                    while ((line = reader.readLine()) != null) {
                        outputBuilder.append(line);
                    }

                    reader.close();
                    JsonElement jsonElement = (JsonElement) (new Gson()).fromJson(outputBuilder.toString(), JsonElement.class);

                    if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("success")) {
                        if (jsonElement.getAsJsonObject().get("success").getAsBoolean()) {
                            break label45;
                        }

                        String errorMessage = "Received success=false from MCLeaks API";

                        if (jsonElement.getAsJsonObject().has("errorMessage")) {
                            errorMessage = jsonElement.getAsJsonObject().get("errorMessage").getAsString();
                        }

                        this.networkManager.closeChannel(new TextComponentString(errorMessage));
                        callbackInfo.cancel();
                        return;
                    }

                    this.networkManager.closeChannel(new TextComponentString("Invalid response from MCLeaks API"));
                    callbackInfo.cancel();
                    return;
                }
            } catch (Exception exception) {
                this.networkManager.closeChannel(new TextComponentString("Error whilst contacting MCLeaks API: " + exception.toString()));
                callbackInfo.cancel();
                return;
            }

            ClientUtils.sendEncryption(NetworkManagerImplKt.wrap(this.networkManager), secretkey, publickey, ISPacketEncryptionRequestKt.wrap(packetIn));
            callbackInfo.cancel();
        }

    }
}
