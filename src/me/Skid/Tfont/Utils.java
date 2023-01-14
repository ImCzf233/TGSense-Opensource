package me.Skid.Tfont;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;

public class Utils {

    public static boolean fuck = true;
    private static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isContainerEmpty(Container container) {
        int i = 0;

        for (int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                return false;
            }
        }

        return true;
    }

    public static Minecraft getMinecraft() {
        return Utils.mc;
    }

    public static String getMD5(String input) {
        StringBuilder res = new StringBuilder();

        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");

            algorithm.reset();
            algorithm.update(input.getBytes());
            byte[] arrby = algorithm.digest();
            byte[] abyte = arrby;
            int i = arrby.length;

            for (int j = 0; j < i; ++j) {
                byte aMd5 = abyte[j];
                String tmp = Integer.toHexString(255 & aMd5);

                if (tmp.length() == 1) {
                    res.append("0").append(tmp);
                } else {
                    res.append(tmp);
                }
            }
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            ;
        }

        return res.toString();
    }

    public static int add(int number, int add, int max) {
        return number + add > max ? max : number + add;
    }

    public static int remove(int number, int remove, int min) {
        return number - remove < min ? min : number - remove;
    }
}
