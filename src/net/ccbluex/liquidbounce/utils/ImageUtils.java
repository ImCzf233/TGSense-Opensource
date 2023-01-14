package net.ccbluex.liquidbounce.utils;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.ByteBuffer;

public class ImageUtils {

    public static ByteBuffer readImageToBuffer(BufferedImage bufferedImage) {
        int[] rgbArray = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), (int[]) null, 0, bufferedImage.getWidth());
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * rgbArray.length);
        int[] aint = rgbArray;
        int i = rgbArray.length;

        for (int j = 0; j < i; ++j) {
            int rgb = aint[j];

            byteBuffer.putInt(rgb << 8 | rgb >> 24 & 255);
        }

        byteBuffer.flip();
        return byteBuffer;
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        BufferedImage buffImg = new BufferedImage(width, height, 6);

        buffImg.getGraphics().drawImage(image.getScaledInstance(width, height, 4), 0, 0, (ImageObserver) null);
        return buffImg;
    }
}
