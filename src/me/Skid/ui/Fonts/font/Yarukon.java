package me.Skid.ui.Fonts.font;

public class Yarukon {

    public static Yarukon INSTANCE;
    public char[] chars = new char['\uffff'];
    public char[] ascii_chars = new char[256];

    public Yarukon() {
        Yarukon.INSTANCE = this;

        int i;

        for (i = 0; i < this.chars.length; ++i) {
            this.chars[i] = (char) i;
        }

        for (i = 0; i < this.ascii_chars.length; ++i) {
            this.ascii_chars[i] = (char) i;
        }

    }
}
