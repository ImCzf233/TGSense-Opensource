package me.Skid.utils.particles;

import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;

public class Particle {

    private final ParticleTimer removeTimer = new ParticleTimer();
    public final Vec3 position;
    private final Vec3 delta;

    public Particle(Vec3 position) {
        this.position = position;
        this.delta = new Vec3((Math.random() * 2.5D - 1.25D) * 0.01D, (Math.random() * 0.5D - 0.2D) * 0.01D, (Math.random() * 2.5D - 1.25D) * 0.01D);
        this.removeTimer.reset();
    }

    public Particle(Vec3 position, Vec3 velocity) {
        this.position = position;
        this.delta = new Vec3(velocity.xCoord * 0.01D, velocity.yCoord * 0.01D, velocity.zCoord * 0.01D);
        this.removeTimer.reset();
    }

    public void update() {
        IBlock block1 = PlayerParticles.getBlock(this.position.xCoord, this.position.yCoord, this.position.zCoord + this.delta.zCoord);

        if (!(block1 instanceof BlockAir) && !(block1 instanceof BlockBush) && !(block1 instanceof BlockLiquid)) {
            this.delta.zCoord *= -0.8D;
        }

        IBlock block2 = PlayerParticles.getBlock(this.position.xCoord, this.position.yCoord + this.delta.yCoord, this.position.zCoord);

        if (!(block2 instanceof BlockAir) && !(block2 instanceof BlockBush) && !(block2 instanceof BlockLiquid)) {
            this.delta.xCoord *= 0.9990000128746033D;
            this.delta.zCoord *= 0.9990000128746033D;
            this.delta.yCoord *= -0.6D;
        }

        IBlock block3 = PlayerParticles.getBlock(this.position.xCoord + this.delta.xCoord, this.position.yCoord, this.position.zCoord);

        if (!(block3 instanceof BlockAir) && !(block3 instanceof BlockBush) && !(block3 instanceof BlockLiquid)) {
            this.delta.xCoord *= -0.8D;
        }

        this.updateWithoutPhysics();
    }

    public void updateWithoutPhysics() {
        this.position.xCoord += this.delta.xCoord;
        this.position.yCoord += this.delta.yCoord;
        this.position.zCoord += this.delta.zCoord;
        this.delta.xCoord /= 0.9999970197677612D;
        this.delta.yCoord -= 1.7E-6D;
        this.delta.zCoord /= 0.9999970197677612D;
    }
}
