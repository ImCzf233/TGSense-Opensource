package net.ccbluex.liquidbounce.utils.login;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.Base64;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\b\u001a\u00020\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0006H\u0007¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/login/LoginUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "login", "Lnet/ccbluex/liquidbounce/utils/login/LoginUtils$LoginResult;", "username", "", "password", "loginCracked", "", "loginSessionId", "sessionId", "LoginResult", "LiquidBounce"}
)
public final class LoginUtils extends MinecraftInstance {

    public static final LoginUtils INSTANCE;

    @JvmStatic
    @NotNull
    public static final LoginUtils.LoginResult login(@Nullable String username, @Nullable String password) {
        UserAuthentication userauthentication = (new YggdrasilAuthenticationService(Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);

        if (userauthentication == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");
        } else {
            YggdrasilUserAuthentication userAuthentication = (YggdrasilUserAuthentication) userauthentication;

            userAuthentication.setUsername(username);
            userAuthentication.setPassword(password);

            LoginUtils.LoginResult loginutils_loginresult;

            try {
                userAuthentication.logIn();
                IMinecraft iminecraft = MinecraftInstance.mc;
                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                GameProfile gameprofile = userAuthentication.getSelectedProfile();

                Intrinsics.checkExpressionValueIsNotNull(gameprofile, "userAuthentication.selectedProfile");
                String s = gameprofile.getName();

                Intrinsics.checkExpressionValueIsNotNull(s, "userAuthentication.selectedProfile.name");
                GameProfile gameprofile1 = userAuthentication.getSelectedProfile();

                Intrinsics.checkExpressionValueIsNotNull(gameprofile1, "userAuthentication.selectedProfile");
                String s1 = gameprofile1.getId().toString();

                Intrinsics.checkExpressionValueIsNotNull(s1, "userAuthentication.selectedProfile.id.toString()");
                String s2 = userAuthentication.getAuthenticatedToken();

                Intrinsics.checkExpressionValueIsNotNull(s2, "userAuthentication.authenticatedToken");
                iminecraft.setSession(iclassprovider.createSession(s, s1, s2, "mojang"));
                LiquidBounce.INSTANCE.getEventManager().callEvent((Event) (new SessionEvent()));
                loginutils_loginresult = LoginUtils.LoginResult.LOGGED;
            } catch (AuthenticationUnavailableException authenticationunavailableexception) {
                loginutils_loginresult = LoginUtils.LoginResult.NO_CONTACT;
            } catch (AuthenticationException authenticationexception) {
                String s3 = authenticationexception.getMessage();

                if (s3 == null) {
                    Intrinsics.throwNpe();
                }

                String message = s3;

                loginutils_loginresult = StringsKt.contains((CharSequence) message, (CharSequence) "invalid username or password.", true) ? LoginUtils.LoginResult.INVALID_ACCOUNT_DATA : (StringsKt.contains((CharSequence) message, (CharSequence) "account migrated", true) ? LoginUtils.LoginResult.MIGRATED : LoginUtils.LoginResult.NO_CONTACT);
            } catch (NullPointerException nullpointerexception) {
                loginutils_loginresult = LoginUtils.LoginResult.WRONG_PASSWORD;
            }

            return loginutils_loginresult;
        }
    }

    @JvmStatic
    public static final void loginCracked(@Nullable String username) {
        IMinecraft iminecraft = MinecraftInstance.mc;
        IClassProvider iclassprovider = MinecraftInstance.classProvider;

        if (username == null) {
            Intrinsics.throwNpe();
        }

        iminecraft.setSession(iclassprovider.createSession(username, UserUtils.INSTANCE.getUUID(username), "-", "legacy"));
        LiquidBounce.INSTANCE.getEventManager().callEvent((Event) (new SessionEvent()));
    }

    @JvmStatic
    @NotNull
    public static final LoginUtils.LoginResult loginSessionId(@NotNull String sessionId) {
        Intrinsics.checkParameterIsNotNull(sessionId, "sessionId");

        String sessionObject1;

        try {
            byte[] abyte = Base64.getDecoder().decode((String) StringsKt.split$default((CharSequence) sessionId, new String[] { "."}, false, 0, 6, (Object) null).get(1));

            Intrinsics.checkExpressionValueIsNotNull(abyte, "Base64.getDecoder().deco�?(sessionId.split(\".\")[1])");
            byte[] sessionObject = abyte;
            Charset uuid = Charsets.UTF_8;
            boolean accessToken = false;

            sessionObject1 = new String(sessionObject, uuid);
        } catch (Exception exception) {
            return LoginUtils.LoginResult.FAILED_PARSE_TOKEN;
        }

        String decodedSessionData = sessionObject1;

        JsonObject uuid1;
        JsonElement jsonelement;

        try {
            jsonelement = (new JsonParser()).parse(decodedSessionData);
            Intrinsics.checkExpressionValueIsNotNull(jsonelement, "JsonParser().parse(decodedSessionData)");
            uuid1 = jsonelement.getAsJsonObject();
        } catch (Exception exception1) {
            return LoginUtils.LoginResult.FAILED_PARSE_TOKEN;
        }

        JsonObject sessionObject2 = uuid1;

        jsonelement = uuid1.get("spr");
        Intrinsics.checkExpressionValueIsNotNull(jsonelement, "sessionObject.get(\"spr\")");
        String uuid2 = jsonelement.getAsString();

        jsonelement = sessionObject2.get("yggt");
        Intrinsics.checkExpressionValueIsNotNull(jsonelement, "sessionObject.get(\"yggt\")");
        String accessToken1 = jsonelement.getAsString();
        UserUtils userutils = UserUtils.INSTANCE;

        Intrinsics.checkExpressionValueIsNotNull(accessToken1, "accessToken");
        if (!userutils.isValidToken(accessToken1)) {
            return LoginUtils.LoginResult.INVALID_ACCOUNT_DATA;
        } else {
            userutils = UserUtils.INSTANCE;
            Intrinsics.checkExpressionValueIsNotNull(uuid2, "uuid");
            String s = userutils.getUsername(uuid2);

            if (s != null) {
                String username = s;

                MinecraftInstance.mc.setSession(MinecraftInstance.classProvider.createSession(username, uuid2, accessToken1, "mojang"));
                LiquidBounce.INSTANCE.getEventManager().callEvent((Event) (new SessionEvent()));
                return LoginUtils.LoginResult.LOGGED;
            } else {
                return LoginUtils.LoginResult.INVALID_ACCOUNT_DATA;
            }
        }
    }

    static {
        LoginUtils loginutils = new LoginUtils();

        INSTANCE = loginutils;
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"},
        d2 = { "Lnet/ccbluex/liquidbounce/utils/login/LoginUtils$LoginResult;", "", "(Ljava/lang/String;I)V", "WRONG_PASSWORD", "NO_CONTACT", "INVALID_ACCOUNT_DATA", "MIGRATED", "LOGGED", "FAILED_PARSE_TOKEN", "LiquidBounce"}
    )
    public static enum LoginResult {

        WRONG_PASSWORD, NO_CONTACT, INVALID_ACCOUNT_DATA, MIGRATED, LOGGED, FAILED_PARSE_TOKEN;
    }
}
