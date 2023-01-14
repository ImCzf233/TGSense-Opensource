package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector3d;

public final class PathUtils extends MinecraftInstance {

    public static List findBlinkPath(double tpX, double tpY, double tpZ) {
        ArrayList positions = new ArrayList();
        double curX = PathUtils.mc.getThePlayer().getPosX();
        double curY = PathUtils.mc.getThePlayer().getPosY();
        double curZ = PathUtils.mc.getThePlayer().getPosZ();
        double distance = Math.abs(curX - tpX) + Math.abs(curY - tpY) + Math.abs(curZ - tpZ);

        for (int count = 0; distance > 0.0D; ++count) {
            distance = Math.abs(curX - tpX) + Math.abs(curY - tpY) + Math.abs(curZ - tpZ);
            double diffX = curX - tpX;
            double diffY = curY - tpY;
            double diffZ = curZ - tpZ;
            double offset = (count & 1) == 0 ? 0.4D : 0.1D;
            double minX = Math.min(Math.abs(diffX), offset);

            if (diffX < 0.0D) {
                curX += minX;
            }

            if (diffX > 0.0D) {
                curX -= minX;
            }

            double minY = Math.min(Math.abs(diffY), 0.25D);

            if (diffY < 0.0D) {
                curY += minY;
            }

            if (diffY > 0.0D) {
                curY -= minY;
            }

            double minZ = Math.min(Math.abs(diffZ), offset);

            if (diffZ < 0.0D) {
                curZ += minZ;
            }

            if (diffZ > 0.0D) {
                curZ -= minZ;
            }

            positions.add(new Vector3d(curX, curY, curZ));
        }

        return positions;
    }

    public static List findPath(double tpX, double tpY, double tpZ, double offset) {
        ArrayList positions = new ArrayList();
        double steps = Math.ceil(getDistance(PathUtils.mc.getThePlayer().getPosX(), PathUtils.mc.getThePlayer().getPosY(), PathUtils.mc.getThePlayer().getPosZ(), tpX, tpY, tpZ) / offset);
        double dX = tpX - PathUtils.mc.getThePlayer().getPosX();
        double dY = tpY - PathUtils.mc.getThePlayer().getPosY();
        double dZ = tpZ - PathUtils.mc.getThePlayer().getPosZ();

        for (double d = 1.0D; d <= steps; ++d) {
            positions.add(new Vector3d(PathUtils.mc.getThePlayer().getPosX() + dX * d / steps, PathUtils.mc.getThePlayer().getPosY() + dY * d / steps, PathUtils.mc.getThePlayer().getPosZ() + dZ * d / steps));
        }

        return positions;
    }

    private static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double xDiff = x1 - x2;
        double yDiff = y1 - y2;
        double zDiff = z1 - z2;

        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }
}
