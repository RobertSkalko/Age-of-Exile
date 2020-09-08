package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.actions.ParticleInRadiusAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComponentPart {

    public List<MapHolder> targets = new ArrayList<>();
    public List<MapHolder> acts = new ArrayList<>();
    public List<MapHolder> ifs = new ArrayList<>();

    private List<ComponentPart> chained = null;

    public ComponentPart addChained(ComponentPart add) {
        if (chained == null) {
            chained = new ArrayList<>();
        }
        this.chained.add(add);
        return this;
    }

    public void validate() {
        for (MapHolder part : ifs) {
            EffectCondition condition = EffectCondition.MAP.get(part.type);
            condition.validate(part);
        }
        for (MapHolder part : targets) {
            BaseTargetSelector selector = BaseTargetSelector.MAP.get(part.type);
            selector.validate(part);
        }
        for (MapHolder part : acts) {
            SpellAction action = SpellAction.MAP.get(part.type);
            action.validate(part);
        }

        if (chained != null) {
            chained.forEach(x -> x.validate());
        }
    }

    public void tryActivate(SpellCtx ctx) {

        for (MapHolder part : ifs) {
            EffectCondition condition = EffectCondition.MAP.get(part.type);
            if (!condition.canActivate(ctx, part)) {
                return;
            }
        }

        Set<LivingEntity> list = new HashSet<>();

        for (MapHolder part : targets) {
            BaseTargetSelector selector = BaseTargetSelector.MAP.get(part.type);
            list.addAll(selector.get(ctx.caster, ctx.target, ctx.pos, part));
        }

        for (MapHolder part : acts) {
            SpellAction action = SpellAction.MAP.get(part.type);
            action.tryActivate(list, ctx, part);
        }

        if (chained != null) {
            chained.forEach(x -> x.tryActivate(ctx));
        }
    }

    public ComponentPart addTarget(MapHolder map) {
        this.targets.add(map);
        return this;
    }

    public ComponentPart addActions(MapHolder map) {
        this.acts.add(map);
        return this;
    }

    public ComponentPart addCondition(MapHolder map) {
        this.ifs.add(map);
        return this;
    }

    public static class Builder {

        public static ComponentPart empty() {
            ComponentPart c = new ComponentPart();
            return c;
        }

        public static ComponentPart damage(ValueCalculationData calc, Elements ele) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.DEAL_DAMAGE.create(calc, ele));
            c.targets.add(BaseTargetSelector.TARGET.create());
            return c;
        }

        public static ComponentPart onTickDamageInAoe(Double ticks, ValueCalculationData calc, Elements ele, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.DEAL_DAMAGE.create(calc, ele));
            c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ENEMIES));
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart onTickEmpty(Double ticks) {
            ComponentPart c = new ComponentPart();
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart justAction(MapHolder data) {
            ComponentPart c = new ComponentPart();
            c.acts.add(data);
            return c;
        }

        public static ComponentPart particleOnTick(Double ticks, DefaultParticleType particle, Double count, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius));
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart cloudParticle(Double ticks, DefaultParticleType particle, Double count, Double radius) {
            return cloudParticle(ticks, particle, count, radius, 2.5D);
        }

        public static ComponentPart cloudParticle(Double ticks, DefaultParticleType particle, Double count, Double radius, Double randomY) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
                .put(MapField.Y_RANDOM, randomY));
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart groundParticle(Double ticks, DefaultParticleType particle, Double count, Double radius, Double randomY) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
                .put(MapField.Y_RANDOM, randomY)
                .put(MapField.HEIGHT, 0.5D));
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
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

        public static ComponentPart playSoundPerTarget(SoundEvent sound, Double volume, Double pitch) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PLAY_SOUND_PER_TARGET.create(sound, volume, pitch));
            return c;
        }

        public static ComponentPart playSoundEveryTicks(Double ticks, SoundEvent sound, Double volume, Double pitch) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PLAY_SOUND.create(sound, volume, pitch));
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));

            return c;
        }

    }

}
