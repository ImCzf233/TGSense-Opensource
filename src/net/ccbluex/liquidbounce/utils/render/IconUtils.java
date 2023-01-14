package net.ccbluex.liquidbounce.utils.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class IconUtils {

    public static ByteBuffer[] getFavicon() {
        return null;
    }

    private static ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
        if (imageStream == null) {
            return null;
        } else {
            BufferedImage bufferedImage = ImageIO.read(imageStream);
            int[] rgb = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), (int[]) null, 0, bufferedImage.getWidth());
            ByteBuffer byteBuffer = ByteBuffer.allocate(4 * rgb.length);
            int[] aint = rgb;
            int i = rgb.length;

            for (int j = 0; j < i; ++j) {
                int i = aint[j];

                byteBuffer.putInt(i << 8 | i >> 24 & 255);
            }

            byteBuffer.flip();
            return byteBuffer;
        }
    }
}
