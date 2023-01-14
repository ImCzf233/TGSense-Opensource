package me.Skid.Modules.Render;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import me.Skid.utils.particles.EvictingList;
import me.Skid.utils.particles.Particle;
import me.Skid.utils.particles.ParticleTimer;
import me.Skid.utils.particles.Vec3;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;

@ModuleInfo(
    name = "Particles",
    description = "sb.",
    category = ModuleCategory.RENDER
)
public final class Particles extends Module {

    private final IntegerValue amount = new IntegerValue("Amount", 10, 1, 20);
    private final BoolValue physics = new BoolValue("Physics", true);
    private final List particles = new EvictingList(100);
    private final ParticleTimer timer = new ParticleTimer();
    private IEntityLivingBase target;

    @EventTarget
    public void onAttack(AttackEvent event) {
        this.target = LiquidBounce.combatManager.getTarget();
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        if (this.target != null && this.target.getHurtTime() >= 9 && Particles.mc.getThePlayer().getDistance(this.target.getPosX(), this.target.getPosY(), this.target.getPosZ()) < 10.0D) {
            for (int i = 0; i < ((Integer) this.amount.get()).intValue(); ++i) {
                this.particles.add(new Particle(new Vec3(this.target.getPosX() + (Math.random() - 0.5D) * 0.5D, this.target.getPosY() + Math.random() * 1.0D + 0.5D, this.target.getPosZ() + (Math.random() - 0.5D) * 0.5D)));
            }

            this.target = null;
        }

    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        if (!this.particles.isEmpty()) {
            for (int i = 0; (double) i <= (double) this.timer.getElapsedTime() / 1.0E11D; ++i) {
                if (((Boolean) this.physics.get()).booleanValue()) {
                    this.particles.forEach(Particle::update);
                } else {
                    this.particles.forEach(Particle::updateWithoutPhysics);
                }
            }

            this.particles.removeIf((particle) -> {
                return Particles.mc.getThePlayer().getDistance(particle.position.xCoord, particle.position.yCoord, particle.position.zCoord) > 500.0D;
            });
            this.timer.reset();
            RenderUtils.renderParticles(this.particles);
        }
    }
}
