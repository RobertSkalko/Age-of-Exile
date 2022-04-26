package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleInRadiusAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
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

    public static ComponentPart justAction(MapHolder data) {
        ComponentPart c = new ComponentPart();
        c.acts.add(data);
        return c;
    }

    public static ComponentPart removeSelfEffect(Effect effect) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.POTION.createRemove(effect));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart removeNegativeEffectInAoe(double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.POTION.removeNegative(1D));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        return c;
    }

    public static class Restore {

        public static class Health {
            public static ComponentPart aoe(ValueCalculation calc, Double radius) {
                ComponentPart c = new ComponentPart();
                c.acts.add(SpellAction.RESTORE_HEALTH.create(calc));
                c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
                return c;
            }

            public static ComponentPart caster(ValueCalculation calc) {
                ComponentPart c = new ComponentPart();
                c.acts.add(SpellAction.RESTORE_HEALTH.create(calc));
                c.targets.add(BaseTargetSelector.CASTER.create());
                return c;
            }
        }

        public static class Mana {

            public static ComponentPart aoe(ValueCalculation calc, Double radius) {
                ComponentPart c = new ComponentPart();
                c.acts.add(SpellAction.RESTORE_MANA.create(calc));
                c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
                return c;
            }

            public static ComponentPart caster(ValueCalculation calc) {
                ComponentPart c = new ComponentPart();
                c.acts.add(SpellAction.RESTORE_MANA.create(calc));
                c.targets.add(BaseTargetSelector.CASTER.create());
                return c;
            }
        }

    }

    public static class Particles {

        public static ComponentPart tickAoe(Double ticks, BasicParticleType particle, Double count, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius));
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart aoe(BasicParticleType particle, Double count, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius));
            return c;
        }

        public static ComponentPart tickCloud(Double ticks, BasicParticleType particle, Double count, Double radius) {
            return tickCloud(ticks, particle, count, radius, 2.5D);
        }

        public static ComponentPart tickCloud(Double ticks, BasicParticleType particle, Double count, Double radius, Double randomY) {
            ComponentPart c = cloud(particle, count, radius, randomY);
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart cloud(BasicParticleType particle, Double count, Double radius, Double randomY) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
                .put(MapField.Y_RANDOM, randomY));
            return c;
        }

        public static ComponentPart groundEdge(BasicParticleType particle, Double count, Double radius, Double randomY) {
            return groundEdge(particle, count, radius, randomY, ParticleMotion.None);
        }

        public static ComponentPart groundEdge(BasicParticleType particle, Double count, Double radius, Double randomY, ParticleMotion motion) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
                .put(MapField.Y_RANDOM, randomY)
                .put(MapField.HEIGHT, 0.5D)
                .put(MapField.MOTION, motion.name())
                .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE_EDGE.name()));
            return c;
        }

        public static ComponentPart ground(BasicParticleType particle, Double count, Double radius, Double randomY) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
                .put(MapField.Y_RANDOM, randomY)
                .put(MapField.HEIGHT, 0.5D));
            return c;
        }

        public static ComponentPart swordSweep() {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.SWORD_SWEEP_PARTICLES.create());
            return c;
        }
    }

    public static class Damage {

        public static ComponentPart of(ValueCalculation calc) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.DEAL_DAMAGE.create(calc));
            c.targets.add(BaseTargetSelector.TARGET.create());
            return c;
        }

        public static ComponentPart dotOnTick(String effect, ValueCalculation calc) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.DEAL_DAMAGE.create(calc)
                .put(MapField.EXILE_POTION_ID, effect)
                .put(MapField.DMG_EFFECT_TYPE, AttackType.dot.name()));

            c.targets.add(BaseTargetSelector.TARGET.create());
            return c;
        }

        public static ComponentPart aoe(ValueCalculation calc, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.DEAL_DAMAGE.create(calc));
            c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies));
            return c;
        }

        public static ComponentPart inFront(ValueCalculation calc, Double distance, Double width) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.DEAL_DAMAGE.create(calc));
            c.targets.add(BaseTargetSelector.IN_FRONT.create(distance, width, AllyOrEnemy.enemies));
            return c;
        }

    }

    public static class Sound {

        public static ComponentPart play(SoundEvent sound, Double volume, Double pitch) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PLAY_SOUND.create(sound, volume, pitch));
            return c;
        }

        public static ComponentPart play(SoundEvent sound) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PLAY_SOUND.create(sound, 1D, 1D));
            return c;
        }

        public static ComponentPart play(SavedSound sound) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PLAY_SOUND.create(sound.sound, (double) sound.volume, (double) sound.pitch));
            return c;
        }

        public static ComponentPart playSoundPerTarget(SoundEvent sound, Double volume, Double pitch) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PLAY_SOUND_PER_TARGET.create(sound, volume, pitch));
            return c;
        }
    }

}
