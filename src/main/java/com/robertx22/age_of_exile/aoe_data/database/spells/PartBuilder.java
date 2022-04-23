package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction.GiveOrTake;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleInRadiusAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.utils.SavedSound;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.util.SoundEvent;

public class PartBuilder {

    public static ComponentPart empty() {
        ComponentPart c = new ComponentPart();
        return c;
    }

    public static ComponentPart damage(ValueCalculation calc) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc));
        c.targets.add(BaseTargetSelector.TARGET.create());
        return c;
    }

    public static ComponentPart dotDamageOnTick(String effect, ValueCalculation calc) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc)
            .put(MapField.EXILE_POTION_ID, effect)
            .put(MapField.DMG_EFFECT_TYPE, AttackType.dot.name()));

        c.targets.add(BaseTargetSelector.TARGET.create());
        return c;
    }

    public static ComponentPart exileEffect(String effect, Double duration) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.EXILE_EFFECT.create(effect, GiveOrTake.GIVE_STACKS, duration));
        c.targets.add(BaseTargetSelector.TARGET.create());
        return c;
    }

    public static ComponentPart restoreManaInRadius(ValueCalculation calc, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.RESTORE_MANA.create(calc));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        return c;
    }

    public static ComponentPart restoreManaToCaster(ValueCalculation calc) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.RESTORE_MANA.create(calc));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart onTickDamageInAoe(Double ticks, ValueCalculation calc, Double radius) {
        ComponentPart c = damageInAoe(calc, radius);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart damageInAoe(ValueCalculation calc, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies));
        return c;
    }

    public static ComponentPart damageInFront(ValueCalculation calc, Double distance, Double width) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc));
        c.targets.add(BaseTargetSelector.IN_FRONT.create(distance, width, AllyOrEnemy.enemies));
        return c;
    }

    public static ComponentPart onTickRaycast(Double ticks, ValueCalculation calc, Double distance) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc));
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        c.targets.add(BaseTargetSelector.RAY_CAST.create(distance, AllyOrEnemy.enemies));
        return c;
    }

    public static ComponentPart healInAoe(ValueCalculation calc, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.RESTORE_HEALTH.create(calc));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        return c;
    }

    public static ComponentPart healCaster(ValueCalculation calc) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.RESTORE_HEALTH.create(calc));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart pushCaster(DashUtils.Way way, Double str) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PUSH.create(str, way));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart pushCaster(DashUtils.Way way, DashUtils.Strength str) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PUSH.create((double) str.num, way));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart justAction(MapHolder data) {
        ComponentPart c = new ComponentPart();
        c.acts.add(data);
        return c;
    }

    public static ComponentPart particleOnTick(Double ticks, BasicParticleType particle, Double count, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius));
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart aoeParticles(BasicParticleType particle, Double count, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius));
        return c;
    }

    public static ComponentPart tickCloudParticle(Double ticks, BasicParticleType particle, Double count, Double radius) {
        return tickCloudParticle(ticks, particle, count, radius, 2.5D);
    }

    public static ComponentPart tickCloudParticle(Double ticks, BasicParticleType particle, Double count, Double radius, Double randomY) {
        ComponentPart c = cloudParticles(particle, count, radius, randomY);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart onTickCleanseInRadius(Double ticks, Effect effect, Double radius) {
        ComponentPart c = cleanseInRadius(effect, radius);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart onTickRemoveNegativeEffectInRadius(Double ticks, Double radius) {
        ComponentPart c = removeNegativeEffectInRadius(radius);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart removeNegativeEffectInRadius(Double radius) {
        ComponentPart c = new ComponentPart();
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        c.acts.add(SpellAction.POTION.removeNegative(1D));
        return c;
    }

    public static ComponentPart cleanseInRadius(Effect effect, Double radius) {
        ComponentPart c = new ComponentPart();
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        c.acts.add(SpellAction.POTION.createRemove(effect));
        return c;
    }

    public static ComponentPart cloudParticles(BasicParticleType particle, Double count, Double radius, Double randomY) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
            .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
            .put(MapField.Y_RANDOM, randomY));
        return c;
    }

    public static ComponentPart tickGroundParticle(Double ticks, BasicParticleType particle, Double count, Double radius, Double randomY) {
        ComponentPart c = groundParticles(particle, count, radius, randomY);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;

    }

    public static ComponentPart cancelSpell() {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.CANCEL_CAST.create());
        return c;
    }

    public static ComponentPart groundEdgeParticles(BasicParticleType particle, Double count, Double radius, Double randomY) {
        return groundEdgeParticles(particle, count, radius, randomY, ParticleMotion.None);
    }

    public static class Particle {

        MapHolder map;

        public static Particle builder(BasicParticleType particle, Double count, Double radius) {
            Particle p = new Particle();
            p.map = SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius);
            p.map.put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.CIRCLE.name());
            return p;
        }

        public <T> Particle set(MapField<T> field, T t) {
            map.put(field, t);
            return this;
        }

        public ComponentPart build() {
            ComponentPart p = new ComponentPart();
            p.acts.add(map);
            return p;
        }

    }

    public static ComponentPart nova(BasicParticleType particle, Double count, Double radius, Double motionMulti) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
            .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
            .put(MapField.Y_RANDOM, 0.2D)
            .put(MapField.HEIGHT, 0.5D)
            .put(MapField.MOTION, ParticleMotion.OutwardMotion.name())
            .put(MapField.MOTION_MULTI, motionMulti)
            .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE_EDGE.name()));
        return c;
    }

    public static ComponentPart groundEdgeParticles(BasicParticleType particle, Double count, Double radius, Double randomY, ParticleMotion motion) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
            .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
            .put(MapField.Y_RANDOM, randomY)
            .put(MapField.HEIGHT, 0.5D)
            .put(MapField.MOTION, motion.name())
            .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE_EDGE.name()));
        return c;
    }

    public static ComponentPart groundParticles(BasicParticleType particle, Double count, Double radius, Double randomY) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
            .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
            .put(MapField.Y_RANDOM, randomY)
            .put(MapField.HEIGHT, 0.5D));
        return c;
    }

    public static ComponentPart particleOnTick(Double ticks, MapHolder map) {
        ComponentPart c = new ComponentPart();
        c.acts.add(map);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart playSound(SoundEvent sound, Double volume, Double pitch) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PLAY_SOUND.create(sound, volume, pitch));
        return c;
    }

    public static ComponentPart playSound(SavedSound sound) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PLAY_SOUND.create(sound.sound, (double) sound.volume, (double) sound.pitch));
        return c;
    }

    public static ComponentPart swordSweepParticles() {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.SWORD_SWEEP_PARTICLES.create());
        return c;
    }

    public static ComponentPart removeSelfEffect(Effect effect) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.POTION.createRemove(effect));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart playSoundPerTarget(SoundEvent sound, Double volume, Double pitch) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PLAY_SOUND_PER_TARGET.create(sound, volume, pitch));
        return c;
    }

}
