package com.robertx22.age_of_exile.mobs.ai;

import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class SpellAttackGoal<T extends HostileEntity> extends Goal {
    private final T actor;
    private double speed = 1;
    private final float squaredRange;
    private int targetSeeingTicker;
    private boolean movingToLeft;
    private boolean backward;
    private int combatTicks = -1;

    List<EntitySavedSpellData> spells = new ArrayList<>();

    public SpellAttackGoal addOtherSpell(String spell) {

        EntitySavedSpellData data = EntitySavedSpellData.create(Load.Unit(actor)
            .getLevel(), actor, Database.Spells()
            .get(spell));

        spells.add(data);

        return this;

    }

    public SpellAttackGoal(String spell, T actor, float range) {
        this.actor = actor;
        this.squaredRange = range * range;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        addOtherSpell(spell);
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

            Load.Unit(actor)
                .getCooldowns()
                .onTicksPass(1);

            spells.forEach(x -> tryCast(x));

        }
    }

    public void tryCast(EntitySavedSpellData data) {

        if (Load.Unit(actor)
            .getCooldowns()
            .isOnCooldown(data.getSpell()
                .GUID())) {
            return;
        }

        if (!Load.spells(actor)
            .getCastingData()
            .isCasting()) {
            Load.spells(actor)
                .getCastingData()
                .setToCast(data.getSpell(), actor);
        }

        Load.spells(actor)
            .getCastingData()
            .onTimePass(actor, Load.spells(actor), 1);

    }

}
