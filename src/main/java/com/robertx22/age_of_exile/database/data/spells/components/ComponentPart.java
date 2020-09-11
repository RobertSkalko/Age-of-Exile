package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.actions.ExilePotionAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleInRadiusAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICMainTooltip;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.*;

public class ComponentPart {

    public List<MapHolder> targets = new ArrayList<>();
    public List<MapHolder> acts = new ArrayList<>();
    public List<MapHolder> ifs = new ArrayList<>();

    List<ComponentPart> per_entity_hit = null;

    public ComponentPart addPerEntityHit(ComponentPart add) {
        if (per_entity_hit == null) {
            per_entity_hit = new ArrayList<>();
        }
        this.per_entity_hit
            .add(add);
        return this;
    }

    public ComponentPart onTick(Double ticks) {
        ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return this;
    }

    public void addActivationRequirement(EntityActivation act) {
        if (act == EntityActivation.ON_EXPIRE) {
            this.addCondition(EffectCondition.ON_ENTITY_EXPIRE.create());
        }
        if (act == EntityActivation.ON_HIT) {
            this.addCondition(EffectCondition.ON_HIT.create());
        }
        if (act == EntityActivation.ON_CAST) {
            this.addCondition(EffectCondition.ON_CAST.create());
        }
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

        if (per_entity_hit != null) {
            per_entity_hit.forEach(x -> x.validate());
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

        if (per_entity_hit != null) {

            for (LivingEntity en : list) {
                SpellCtx chainedCtx = SpellCtx.onEntityHit(ctx, en);

                for (ComponentPart onEn : per_entity_hit) {

                    List<LivingEntity> single = Arrays.asList(en);

                    for (MapHolder part : onEn.ifs) {
                        EffectCondition condition = EffectCondition.MAP.get(part.type);
                        if (!condition.canActivate(chainedCtx, part)) {
                            return;
                        }
                    }
                    for (MapHolder part : onEn.acts) {
                        SpellAction action = SpellAction.MAP.get(part.type);
                        action.tryActivate(single, chainedCtx, part);
                    }

                }

            }

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

    public ComponentPart requiresSpellMod(SpellModifier mod) {
        return addCondition(EffectCondition.CASTER_HAS_SPELL_MOD.create(mod));
    }

    public ComponentPart addCondition(MapHolder map) {
        this.ifs.add(map);
        return this;
    }

    public List<MutableText> GetTooltipString(TooltipInfo info, AttachedSpell spell) {
        List<MutableText> list = new ArrayList<>();

        MutableText text = new LiteralText("");

        String firstLetter = "*";
        boolean isCondition = false;

        for (MapHolder part : acts) {
            SpellAction handler = SpellAction.MAP.get(part.type);
            if (handler instanceof ICMainTooltip) {
                ICMainTooltip line = (ICMainTooltip) handler;
                list.addAll(line.getLines(spell, part));
            }
        }

        for (MapHolder part : ifs) {
            EffectCondition handler = EffectCondition.MAP.get(part.type);
            if (handler instanceof ICMainTooltip) {
                ICMainTooltip line = (ICMainTooltip) handler;
                list.addAll(line.getLines(spell, part));
                isCondition = true;
            }
        }
        if (isCondition) {
            firstLetter = "-";
        }
        boolean hasAction = false;

        for (MapHolder part : ifs) {
            EffectCondition handler = EffectCondition.MAP.get(part.type);

            if (handler instanceof ICTextTooltip) {
                ICTextTooltip ictext = (ICTextTooltip) handler;
                text.append(ictext.getText(info, part));
            }
        }

        for (MapHolder part : acts) {
            SpellAction handler = SpellAction.MAP.get(part.type);

            if (handler instanceof ICTextTooltip) {
                ICTextTooltip ictext = (ICTextTooltip) handler;
                text.append(ictext.getText(info, part));
                hasAction = true;
            }
        }

        for (MapHolder part : targets) {
            BaseTargetSelector handler = BaseTargetSelector.MAP.get(part.type);

            if (handler instanceof ICTextTooltip) {
                ICTextTooltip ictext = (ICTextTooltip) handler;
                text.append(ictext.getText(info, part));
            }
        }

        if (hasAction) {
            list.add(text);
        }

        return list;
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

        public static ComponentPart restoreManaInRadius(ValueCalculationData calc, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.RESTORE_MANA.create(calc));
            c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ENEMIES));
            return c;
        }

        public static ComponentPart restoreMagicShieldInRadius(ValueCalculationData calc, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.RESTORE_MAGIC_SHIELD.create(calc));
            c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ENEMIES));
            return c;
        }

        public static ComponentPart restoreMagicShieldToCaster(ValueCalculationData calc) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.RESTORE_MAGIC_SHIELD.create(calc));
            c.targets.add(BaseTargetSelector.CASTER.create());
            return c;
        }

        public static ComponentPart onTickDamageInAoe(Double ticks, ValueCalculationData calc, Elements ele, Double radius) {
            ComponentPart c = damageInAoe(calc, ele, radius);
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart damageInAoe(ValueCalculationData calc, Elements ele, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.DEAL_DAMAGE.create(calc, ele));
            c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ENEMIES));
            return c;
        }

        public static ComponentPart damageInFront(ValueCalculationData calc, Elements ele, Double distance, Double width) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.DEAL_DAMAGE.create(calc, ele));
            c.targets.add(BaseTargetSelector.IN_FRONT.create(distance, width, EntityFinder.EntityPredicate.ENEMIES));
            return c;
        }

        public static ComponentPart onTickHealInAoe(Double ticks, ValueCalculationData calc, Double radius) {
            ComponentPart c = healInAoe(calc, radius);
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart healInAoe(ValueCalculationData calc, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.RESTORE_HEALTH.create(calc));
            c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ALLIES));
            return c;
        }

        public static ComponentPart healCaster(ValueCalculationData calc) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.RESTORE_HEALTH.create(calc));
            c.targets.add(BaseTargetSelector.CASTER.create());
            return c;
        }

        public static ComponentPart pushCaster(DashUtils.Way way, DashUtils.Strength str) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PUSH.create((double) str.num, way));
            c.targets.add(BaseTargetSelector.CASTER.create());
            return c;
        }

        public static ComponentPart onTickAction(Double ticks, MapHolder action) {
            ComponentPart c = new ComponentPart();
            c.acts.add(action);
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

        public static ComponentPart aoeParticles(DefaultParticleType particle, Double count, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius));
            return c;
        }

        public static ComponentPart tickCloudParticle(Double ticks, DefaultParticleType particle, Double count, Double radius) {
            return tickCloudParticle(ticks, particle, count, radius, 2.5D);
        }

        public static ComponentPart tickCloudParticle(Double ticks, DefaultParticleType particle, Double count, Double radius, Double randomY) {
            ComponentPart c = cloudParticles(particle, count, radius, randomY);
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart onTickCleanseInRadius(Double ticks, StatusEffect effect, Double radius) {
            ComponentPart c = cleanseInRadius(effect, radius);
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart cleanseInRadius(StatusEffect effect, Double radius) {
            ComponentPart c = new ComponentPart();
            c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ALLIES));
            c.acts.add(SpellAction.POTION.create(effect, ExilePotionAction.GiveOrTake.REMOVE_STACKS));
            return c;
        }

        public static ComponentPart cloudParticles(DefaultParticleType particle, Double count, Double radius, Double randomY) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
                .put(MapField.Y_RANDOM, randomY));
            return c;
        }

        public static ComponentPart tickGroundParticle(Double ticks, DefaultParticleType particle, Double count, Double radius, Double randomY) {
            ComponentPart c = groundParticles(particle, count, radius, randomY);
            c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

        public static ComponentPart groundEdgeParticles(DefaultParticleType particle, Double count, Double radius, Double randomY) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE.name())
                .put(MapField.Y_RANDOM, randomY)
                .put(MapField.HEIGHT, 0.5D)
                .put(MapField.PARTICLE_SHAPE, ParticleInRadiusAction.Shape.HORIZONTAL_CIRCLE_EDGE.name()));
            return c;
        }

        public static ComponentPart groundParticles(DefaultParticleType particle, Double count, Double radius, Double randomY) {
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

        public static ComponentPart swordSweepParticles() {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.SWORD_SWEEP_PARTICLES.create());
            return c;
        }

        public static ComponentPart giveSelfExileEffect(BasePotionEffect effect) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.EXILE_POTION.create(effect, ExilePotionAction.GiveOrTake.GIVE_STACKS));
            c.targets.add(BaseTargetSelector.CASTER.create());
            return c;
        }

        public static ComponentPart giveToAlliesInRadius(BasePotionEffect effect, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.EXILE_POTION.create(effect, ExilePotionAction.GiveOrTake.GIVE_STACKS));
            c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ALLIES));
            return c;
        }

        public static ComponentPart addExileEffectToEnemiesInAoe(BasePotionEffect effect, Double radius) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.EXILE_POTION.create(effect, ExilePotionAction.GiveOrTake.GIVE_STACKS));
            c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ENEMIES));
            return c;
        }

        public static ComponentPart addExileEffectToEnemiesInFront(BasePotionEffect effect, Double distance, Double width) {
            ComponentPart c = new ComponentPart();
            c.acts.add(SpellAction.EXILE_POTION.create(effect, ExilePotionAction.GiveOrTake.GIVE_STACKS));
            c.targets.add(BaseTargetSelector.IN_FRONT.create(distance, width, EntityFinder.EntityPredicate.ENEMIES));
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
