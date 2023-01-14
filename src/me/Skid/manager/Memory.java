package me.Skid.manager;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.UpdateEvent;

public class Memory implements Listenable {

    public static float maxMemorySize = 0.0F;
    public static float usedMemorySize = 0.0F;

    public boolean handleEvents() {
        return true;
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

        Memory.maxMemorySize = (float) memoryUsage.getMax() / 1048576.0F;
        Memory.usedMemorySize = (float) memoryUsage.getUsed() / 1048576.0F;
    }

    public static float getMemory() {
        return Memory.maxMemorySize / Memory.usedMemorySize;
    }
}
