package net.ccbluex.liquidbounce.features.command.shortcuts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.PrimitiveIterator.OfInt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\n\u0010\n\u001a\u00060\u000bj\u0002`\fH\u0002J\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u000e2\u0006\u0010\u0010\u001a\u00020\u000fJ\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/shortcuts/ShortcutParser;", "", "()V", "SEPARATOR", "", "finishLiteral", "", "tokens", "", "Lnet/ccbluex/liquidbounce/features/command/shortcuts/Token;", "tokenBuf", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "parse", "", "", "script", "tokenize", "LiquidBounce"}
)
public final class ShortcutParser {

    private static final int SEPARATOR;
    public static final ShortcutParser INSTANCE;

    @NotNull
    public final List parse(@NotNull String script) {
        Intrinsics.checkParameterIsNotNull(script, "script");
        List tokens = this.tokenize(script);
        boolean tmpStatement = false;
        List parsed = (List) (new ArrayList());
        boolean token = false;
        List tmpStatement1 = (List) (new ArrayList());
        Iterator iterator = tokens.iterator();

        while (iterator.hasNext()) {
            Token token1 = (Token) iterator.next();
            Collection collection;
            boolean flag;

            if (token1 instanceof Literal) {
                collection = (Collection) tmpStatement1;
                String s = ((Literal) token1).getLiteral();

                flag = false;
                collection.add(s);
            } else if (token1 instanceof StatementEnd) {
                collection = (Collection) parsed;
                List list = CollectionsKt.toList((Iterable) tmpStatement1);

                flag = false;
                collection.add(list);
                tmpStatement1.clear();
            }
        }

        Collection token2 = (Collection) tmpStatement1;
        boolean flag1 = false;

        if (!token2.isEmpty()) {
            throw (Throwable) (new IllegalArgumentException("Unexpected end of statement!"));
        } else {
            return parsed;
        }
    }

    private final List tokenize(String script) {
        boolean tokenBuf = false;
        List tokens = (List) (new ArrayList());
        StringBuilder tokenBuf1 = new StringBuilder();
        OfInt ofint = script.codePoints().iterator();

        while (ofint.hasNext()) {
            Integer code = ofint.next();

            Intrinsics.checkExpressionValueIsNotNull(code, "code");
            if (Character.isWhitespace(code.intValue())) {
                this.finishLiteral(tokens, tokenBuf1);
            } else {
                int i = ShortcutParser.SEPARATOR;

                if (code.intValue() == i) {
                    this.finishLiteral(tokens, tokenBuf1);
                    Collection collection = (Collection) tokens;
                    StatementEnd statementend = new StatementEnd();
                    boolean flag = false;

                    collection.add(statementend);
                } else {
                    tokenBuf1.appendCodePoint(code.intValue());
                }
            }
        }

        CharSequence code1 = (CharSequence) tokenBuf1;
        boolean flag1 = false;

        if (code1.length() > 0) {
            throw (Throwable) (new IllegalArgumentException("Unexpected end of literal!"));
        } else {
            return tokens;
        }
    }

    private final void finishLiteral(List tokens, StringBuilder tokenBuf) {
        CharSequence charsequence = (CharSequence) tokenBuf;
        boolean flag = false;

        if (charsequence.length() > 0) {
            Collection collection = (Collection) tokens;
            String s = tokenBuf.toString();

            Intrinsics.checkExpressionValueIsNotNull(s, "tokenBuf.toString()");
            Literal literal = new Literal(s);
            boolean flag1 = false;

            collection.add(literal);
            StringsKt.clear(tokenBuf);
        }

    }

    static {
        ShortcutParser shortcutparser = new ShortcutParser();

        INSTANCE = shortcutparser;
        String s = ";";
        byte b0 = 0;
        boolean flag = false;

        SEPARATOR = s.codePointAt(b0);
    }
}
