package com.robertx22.age_of_exile.mobs.ai;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.saveclasses.item_classes.SkillGemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.SkillGem;
import com.robertx22.age_of_exile.vanilla_mc.packets.particles.ParticleEnum;
import com.robertx22.age_of_exile.vanilla_mc.packets.particles.ParticlePacketData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class SpellAttackGoal<T extends HostileEntity> extends Goal {
    private final T actor;
    private final double speed;
    private int attackInterval;
    private final float squaredRange;
    private int cooldown = -1;
    private int targetSeeingTicker;
    private boolean movingToLeft;
    private boolean backward;
    private int combatTicks = -1;

    SkillGemData skillgem;

    public SpellAttackGoal(BaseSpell spell, T actor, double speed, int attackInterval, float range) {
        this.actor = actor;
        this.speed = speed;
        this.attackInterval = attackInterval;
        this.squaredRange = range * range;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));

        int spelllvl = MathHelper.clamp(Load.Unit(actor)
            .getLevel() / 2, 1, Integer.MAX_VALUE);
        // smaller lvl spells cus spells hit like a truck because of mana cost
        skillgem = SkillGem.Load(new SkillGemBlueprint(spelllvl).createStack());
        skillgem.spell_id = spell
            .GUID();

    }

    public void setAttackInterval(int attackInterval) {
        this.attackInterval = attackInterval;
    }

    @Override
    public boolean canStart() {
        return this.actor.getTarget() != null;
    }

    public boolean shouldContinue() {
        return (this.canStart() || !this.actor.getNavigation()
            .isIdle());
    }

    public void start() {
        super.start();
        this.actor.setAttacking(true);
    }

    public void stop() {
        super.stop();
        this.actor.setAttacking(false);
        this.targetSeeingTicker = 0;
        this.cooldown = -1;
        this.actor.clearActiveItem();
    }

    public void tick() {
        LivingEntity livingEntity = this.actor.getTarget();
        if (livingEntity != null) {
            double d = this.actor.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            boolean bl = this.actor.getVisibilityCache()
                .canSee(livingEntity);
            boolean bl2 = this.targetSeeingTicker > 0;
            if (bl != bl2) {
                this.targetSeeingTicker = 0;
            }

            if (bl) {
                ++this.targetSeeingTicker;
            } else {
                --this.targetSeeingTicker;
            }

            if (d <= (double) this.squaredRange && this.targetSeeingTicker >= 20) {
                this.actor.getNavigation()
                    .stop();
                ++this.combatTicks;
            } else {
                this.actor.getNavigation()
                    .startMovingTo(livingEntity, this.speed);
                this.combatTicks = -1;
            }

            if (this.combatTicks >= 20) {
                if ((double) this.actor.getRandom()
                    .nextFloat() < 0.3D) {
                    this.movingToLeft = !this.movingToLeft;
                }

                if ((double) this.actor.getRandom()
                    .nextFloat() < 0.3D) {
                    this.backward = !this.backward;
                }

                this.combatTicks = 0;
            }

            if (this.combatTicks > -1) {
                if (d > (double) (this.squaredRange * 0.75F)) {
                    this.backward = false;
                } else if (d < (double) (this.squaredRange * 0.25F)) {
                    this.backward = true;
                }

                this.actor.getMoveControl()
                    .strafeTo(this.backward ? -0.5F : 0.5F, this.movingToLeft ? 0.5F : -0.5F);
                this.actor.lookAtEntity(livingEntity, 30.0F, 30.0F);
            } else {
                this.actor.getLookControl()
                    .lookAt(livingEntity, 30.0F, 30.0F);
            }

            if (cooldown < 1) {
                this.skillgem.getSpell()
                    .cast(new SpellCastContext(actor, 0, skillgem));

                this.cooldown = this.attackInterval;
            } else {
                cooldown--;

                if (cooldown % 5 == 0) {
                    if (!this.actor.world.isClient) {
                        ParticleEnum.sendToClients(
                            actor.getBlockPos(), actor.world, new ParticlePacketData(actor.getPos(), ParticleEnum.AOE).radius(1)
                                .motion(new Vec3d(0, 0, 0))
                                .type(ParticleTypes.WITCH)
                                .amount(8));
                    }
                }

            }

        }
    }

}
