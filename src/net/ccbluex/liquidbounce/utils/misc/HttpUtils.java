package net.ccbluex.liquidbounce.utils.misc;

import com.google.common.io.ByteStreams;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J\"\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u0004H\u0002J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u0004J\"\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/misc/HttpUtils;", "", "()V", "DEFAULT_AGENT", "", "download", "", "url", "file", "Ljava/io/File;", "get", "make", "Ljava/net/HttpURLConnection;", "method", "agent", "request", "requestStream", "Ljava/io/InputStream;", "LiquidBounce"}
)
public final class HttpUtils {

    private static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
    public static final HttpUtils INSTANCE;

    private final HttpURLConnection make(String url, String method, String agent) {
        URLConnection urlconnection = (new URL(url)).openConnection();

        if (urlconnection == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.net.HttpURLConnection");
        } else {
            HttpURLConnection httpConnection = (HttpURLConnection) urlconnection;

            httpConnection.setRequestMethod(method);
            httpConnection.setConnectTimeout(2000);
            httpConnection.setReadTimeout(10000);
            httpConnection.setRequestProperty("User-Agent", agent);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setDoOutput(true);
            return httpConnection;
        }
    }

    static HttpURLConnection make$default(HttpUtils httputils, String s, String s1, String s2, int i, Object object) {
        if ((i & 4) != 0) {
            s2 = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
        }

        return httputils.make(s, s1, s2);
    }

    @NotNull
    public final String request(@NotNull String url, @NotNull String method, @NotNull String agent) throws IOException {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(method, "method");
        Intrinsics.checkParameterIsNotNull(agent, "agent");
        HttpURLConnection connection = this.make(url, method, agent);
        InputStream inputstream = connection.getInputStream();

        Intrinsics.checkExpressionValueIsNotNull(inputstream, "connection.inputStream");
        InputStream inputstream1 = inputstream;
        Charset charset = Charsets.UTF_8;
        boolean flag = false;

        return TextStreamsKt.readText((Reader) (new InputStreamReader(inputstream1, charset)));
    }

    public static String request$default(HttpUtils httputils, String s, String s1, String s2, int i, Object object) throws IOException {
        if ((i & 4) != 0) {
            s2 = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
        }

        return httputils.request(s, s1, s2);
    }

    @Nullable
    public final InputStream requestStream(@NotNull String url, @NotNull String method, @NotNull String agent) throws IOException {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(method, "method");
        Intrinsics.checkParameterIsNotNull(agent, "agent");
        HttpURLConnection connection = this.make(url, method, agent);

        return connection.getInputStream();
    }

    public static InputStream requestStream$default(HttpUtils httputils, String s, String s1, String s2, int i, Object object) throws IOException {
        if ((i & 4) != 0) {
            s2 = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
        }

        return httputils.requestStream(s, s1, s2);
    }

    @JvmStatic
    @NotNull
    public static final String get(@NotNull String url) throws IOException {
        Intrinsics.checkParameterIsNotNull(url, "url");
        return request$default(HttpUtils.INSTANCE, url, "GET", (String) null, 4, (Object) null);
    }

    @JvmStatic
    public static final long download(@NotNull String url, @NotNull File file) throws IOException {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(file, "file");
        Closeable closeable = (Closeable) (new FileOutputStream(file));
        boolean flag = false;
        Throwable throwable = (Throwable) null;

        long it1;

        try {
            FileOutputStream it = (FileOutputStream) closeable;
            boolean $i$a$-use-HttpUtils$download$1 = false;

            it1 = ByteStreams.copy(make$default(HttpUtils.INSTANCE, url, "GET", (String) null, 4, (Object) null).getInputStream(), (OutputStream) it);
        } catch (Throwable throwable1) {
            throwable = throwable1;
            throw throwable1;
        } finally {
            CloseableKt.closeFinally(closeable, throwable);
        }

        return it1;
    }

    static {
        HttpUtils httputils = new HttpUtils();

        INSTANCE = httputils;
        HttpURLConnection.setFollowRedirects(true);
    }
}
