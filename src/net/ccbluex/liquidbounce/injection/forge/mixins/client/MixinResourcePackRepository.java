package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.resources.ResourcePackRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ ResourcePackRepository.class})
public class MixinResourcePackRepository {

    @Shadow
    @Final
    private static Logger LOGGER;
    @Shadow
    @Final
    private File dirServerResourcepacks;

    @Overwrite
    private void deleteOldServerResourcesPacks() {
        try {
            ArrayList e = Lists.newArrayList(FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, (IOFileFilter) null));

            e.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            int count = 0;
            Iterator iterator = e.iterator();

            while (iterator.hasNext()) {
                File resourcePackFile = (File) iterator.next();

                if (count++ >= 10) {
                    MixinResourcePackRepository.LOGGER.info("Deleting old server resource pack " + resourcePackFile.getName());

                    try {
                        resourcePackFile.delete();
                    } catch (Throwable throwable) {
                        ;
                    }
                }
            }
        } catch (Throwable throwable1) {
            throwable1.printStackTrace();
        }

    }
}
