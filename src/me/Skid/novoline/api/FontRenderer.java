package me.Skid.novoline.api;

public interface FontRenderer {

    float drawString(CharSequence charsequence, float f, float f1, int i, boolean flag);

    float drawString(CharSequence charsequence, double d0, double d1, int i, boolean flag);

    String trimStringToWidth(CharSequence charsequence, int i, boolean flag);

    int stringWidth(CharSequence charsequence);

    float charWidth(char c0);

    String getName();

    int getHeight();

    boolean isAntiAlias();

    boolean isFractionalMetrics();

    default float drawString(CharSequence text, float x, float y, int color) {
        return this.drawString(text, x, y, color, false);
    }

    default float drawString(CharSequence text, int x, int y, int color) {
        return this.drawString(text, (float) x, (float) y, color, false);
    }

    default String trimStringToWidth(CharSequence text, int width) {
        return this.trimStringToWidth(text, width, false);
    }

    default float drawCenteredString(CharSequence text, float x, float y, int color, boolean dropShadow) {
        return this.drawString(text, x - (float) this.stringWidth(text) / 2.0F, y, color, dropShadow);
    }

    default float getMiddleOfBox(float boxHeight) {
        return boxHeight / 2.0F - (float) this.getHeight() / 2.0F;
    }

    default float drawCenteredString(CharSequence text, float x, float y, int color) {
        return this.drawCenteredString(text, x, y, color, false);
    }
}
