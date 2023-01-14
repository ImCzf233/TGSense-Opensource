package me.Skid.utils.particles;

import net.vitox.ParticleGenerator;

public final class ParticleUtils {

    private static final ParticleGenerator particleGenerator = new ParticleGenerator(100);

    public static void drawParticles(int mouseX, int mouseY) {
        ParticleUtils.particleGenerator.draw(mouseX, mouseY);
    }
}
