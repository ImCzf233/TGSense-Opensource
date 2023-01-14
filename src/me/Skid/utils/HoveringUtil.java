package me.Skid.utils;

public class HoveringUtil {

    public static boolean isHovering(float xPos, float yPos, float width, float height, int mouseX, int mouseY) {
        return (float) mouseX >= xPos && (float) mouseY >= yPos && (float) mouseX < xPos + width && (float) mouseY < yPos + height;
    }
}
