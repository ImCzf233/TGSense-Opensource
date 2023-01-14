package net.ccbluex.liquidinstruction;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import javax.swing.JFrame;
import javax.swing.JLabel;
import kotlin.Metadata;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 2,
    d1 = { "\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a\u0006\u0010\u0000\u001a\u00020\u0001¨\u0006\u0002"},
    d2 = { "main", "", "LiquidBounce"}
)
public final class LiquidInstructionKt {

    public static final void main() {
        JFrame frame = new JFrame("LiquidBounce | Installation");

        frame.setDefaultCloseOperation(3);
        frame.setLayout((LayoutManager) (new BorderLayout()));
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        InputStream inputstream = LiquidBounce.class.getResourceAsStream("/instructions.html");

        Intrinsics.checkExpressionValueIsNotNull(inputstream, "LiquidBounce::class.java…eam(\"/instructions.html\")");
        InputStream inputstream1 = inputstream;
        Charset charset = Charsets.UTF_8;
        boolean flag = false;
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream1, charset);
        String s = TextStreamsKt.readText((Reader) inputstreamreader);
        String s1 = LiquidBounce.INSTANCE.getClass().getClassLoader().getResource("assets").toString();

        Intrinsics.checkExpressionValueIsNotNull(s1, "LiquidBounce.javaClass.c…urce(\"assets\").toString()");
        String s2 = StringsKt.replace$default(s, "{assets}", s1, false, 4, (Object) null);
        JLabel label = new JLabel(s2);

        frame.add((Component) label, "Center");
        frame.pack();
        frame.setLocationRelativeTo((Component) null);
        frame.setVisible(true);
    }

    public static void main(String[] astring) {
        main();
    }
}
