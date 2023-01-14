package net.ccbluex.liquidbounce.utils.misc;

import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\nJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0007J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u000fJ\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\fJ\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\fH\u0007¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/misc/RandomUtils;", "", "()V", "nextBoolean", "", "nextDouble", "", "startInclusive", "endInclusive", "nextFloat", "", "nextInt", "", "endExclusive", "random", "", "length", "chars", "", "randomNumber", "randomString", "LiquidBounce"}
)
public final class RandomUtils {

    public static final RandomUtils INSTANCE;

    public final boolean nextBoolean() {
        return (new Random()).nextBoolean();
    }

    @JvmStatic
    public static final int nextInt(int startInclusive, int endExclusive) {
        return endExclusive - startInclusive <= 0 ? startInclusive : startInclusive + (new Random()).nextInt(endExclusive - startInclusive);
    }

    public final double nextDouble(double startInclusive, double endInclusive) {
        return startInclusive != endInclusive && endInclusive - startInclusive > 0.0D ? startInclusive + (endInclusive - startInclusive) * Math.random() : startInclusive;
    }

    public final float nextFloat(float startInclusive, float endInclusive) {
        return startInclusive != endInclusive && endInclusive - startInclusive > 0.0F ? (float) ((double) startInclusive + (double) (endInclusive - startInclusive) * Math.random()) : startInclusive;
    }

    @NotNull
    public final String randomNumber(int length) {
        return this.random(length, "123456789");
    }

    @JvmStatic
    @NotNull
    public static final String randomString(int length) {
        return RandomUtils.INSTANCE.random(length, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }

    @NotNull
    public final String random(int length, @NotNull String chars) {
        Intrinsics.checkParameterIsNotNull(chars, "chars");
        boolean flag = false;
        char[] achar = chars.toCharArray();

        Intrinsics.checkExpressionValueIsNotNull(achar, "(this as java.lang.String).toCharArray()");
        char[] achar1 = achar;

        return this.random(length, achar1);
    }

    @NotNull
    public final String random(int length, @NotNull char[] chars) {
        Intrinsics.checkParameterIsNotNull(chars, "chars");
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;

        for (int i = length; i < i; ++i) {
            stringBuilder.append(chars[(new Random()).nextInt(chars.length)]);
        }

        String s = stringBuilder.toString();

        Intrinsics.checkExpressionValueIsNotNull(s, "stringBuilder.toString()");
        return s;
    }

    static {
        RandomUtils randomutils = new RandomUtils();

        INSTANCE = randomutils;
    }
}
