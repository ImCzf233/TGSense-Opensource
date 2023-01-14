package net.vitox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.vitox.particle.util.RenderUtils;

@SideOnly(Side.CLIENT)
public class ParticleGenerator {

    private final List particles = new ArrayList();
    private final int amount;
    private int prevWidth;
    private int prevHeight;

    public ParticleGenerator(int amount) {
        this.amount = amount;
    }

    public void draw(int mouseX, int mouseY) {
        if (this.particles.isEmpty() || this.prevWidth != Minecraft.getMinecraft().displayWidth || this.prevHeight != Minecraft.getMinecraft().displayHeight) {
            this.particles.clear();
            this.create();
        }

        this.prevWidth = Minecraft.getMinecraft().displayWidth;
        this.prevHeight = Minecraft.getMinecraft().displayHeight;

        Particle particle;

        for (Iterator iterator = this.particles.iterator(); iterator.hasNext(); RenderUtils.drawCircle(particle.getX(), particle.getY(), particle.size, -1)) {
            particle = (Particle) iterator.next();
            particle.fall();
            particle.interpolation();
            byte range = 50;
            boolean mouseOver = (float) mouseX >= particle.x - (float) range && (float) mouseY >= particle.y - (float) range && (float) mouseX <= particle.x + (float) range && (float) mouseY <= particle.y + (float) range;

            if (mouseOver) {
                this.particles.stream().filter((part) -> {
                    return part.getX() > particle.getX() && part.getX() - particle.getX() < (float) range && particle.getX() - part.getX() < (float) range && (part.getY() > particle.getY() && part.getY() - particle.getY() < (float) range || particle.getY() > part.getY() && particle.getY() - part.getY() < (float) range);
                }).forEach((connectable) -> {
                    particle.connect(connectable.getX(), connectable.getY());
                });
            }
        }

    }

    private void create() {
        Random random = new Random();

        for (int i = 0; i < this.amount; ++i) {
            this.particles.add(new Particle(random.nextInt(Minecraft.getMinecraft().displayWidth), random.nextInt(Minecraft.getMinecraft().displayHeight)));
        }

    }
}
