package net.ccbluex.liquidbounce.utils;

public class CPSCounter {

    private static final int MAX_CPS = 50;
    private static final RollingArrayLongBuffer[] TIMESTAMP_BUFFERS = new RollingArrayLongBuffer[CPSCounter.MouseButton.values().length];

    public static void registerClick(CPSCounter.MouseButton button) {
        CPSCounter.TIMESTAMP_BUFFERS[button.getIndex()].add(System.currentTimeMillis());
    }

    public static int getCPS(CPSCounter.MouseButton button) {
        return CPSCounter.TIMESTAMP_BUFFERS[button.getIndex()].getTimestampsSince(System.currentTimeMillis() - 1000L);
    }

    static {
        for (int i = 0; i < CPSCounter.TIMESTAMP_BUFFERS.length; ++i) {
            CPSCounter.TIMESTAMP_BUFFERS[i] = new RollingArrayLongBuffer(50);
        }

    }

    public static enum MouseButton {

        LEFT(0), MIDDLE(1), RIGHT(2);

        private int index;

        private MouseButton(int index) {
            this.index = index;
        }

        private int getIndex() {
            return this.index;
        }
    }
}
