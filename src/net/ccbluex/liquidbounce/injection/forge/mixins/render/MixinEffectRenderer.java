package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Queue;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEmitter;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({ ParticleManager.class})
public abstract class MixinEffectRenderer {

    @Shadow
    @Final
    private final Queue particleEmitters = Queues.newArrayDeque();
    @Shadow
    @Final
    private Queue queue;
    @Shadow
    @Final
    private ArrayDeque[][] fxLayers;

    @Shadow
    protected abstract void updateEffectLayer(int i);

    @Overwrite
    public void updateEffects() {
        try {
            for (int particle = 0; particle < 4; ++particle) {
                this.updateEffectLayer(particle);
            }

            if (!this.particleEmitters.isEmpty()) {
                ArrayList arraylist = Lists.newArrayList();
                Iterator j = this.particleEmitters.iterator();

                while (j.hasNext()) {
                    ParticleEmitter k = (ParticleEmitter) j.next();

                    k.onUpdate();
                    if (!k.isAlive()) {
                        arraylist.add(k);
                    }
                }

                this.particleEmitters.removeAll(arraylist);
            }

            if (!this.queue.isEmpty()) {
                for (Particle particle = (Particle) this.queue.poll(); particle != null; particle = (Particle) this.queue.poll()) {
                    int i = particle.getFXLayer();
                    int j = particle.shouldDisableDepth() ? 0 : 1;

                    if (this.fxLayers[i][j].size() >= 16384) {
                        this.fxLayers[i][j].removeFirst();
                    }

                    this.fxLayers[i][j].add(particle);
                }
            }
        } catch (ConcurrentModificationException concurrentmodificationexception) {
            ;
        }

    }
}
