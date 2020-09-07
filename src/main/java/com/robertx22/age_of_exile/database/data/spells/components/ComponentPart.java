package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DefaultParticleType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComponentPart {

    public List<MapHolder> target_selectors = new ArrayList<>();
    public List<MapHolder> actions = new ArrayList<>();
    public List<MapHolder> conditions = new ArrayList<>();

    public void tryActivate(SpellCtx ctx) {

        for (MapHolder part : conditions) {
            EffectCondition condition = EffectCondition.MAP.get(part.type);
            if (!condition.canActivate(ctx, part)) {
                return;
            }
        }

        Set<LivingEntity> list = new HashSet<>();

        for (MapHolder part : target_selectors) {
            BaseTargetSelector selector = BaseTargetSelector.MAP.get(part.type);
            list.addAll(selector.get(ctx.caster, ctx.target, ctx.pos, part));
        }

        for (MapHolder part : actions) {
            SpellAction action = SpellAction.MAP.get(part.type);
            action.tryActivate(list, ctx, part);
        }

    }

    public static class Builder {

        public static ComponentPart damage(ValueCalculationData calc, Elements ele) {
            ComponentPart c = new ComponentPart();
            c.actions.add(SpellAction.DEAL_DAMAGE.create(calc, ele));
            c.target_selectors.add(BaseTargetSelector.TARGET.create());
            return c;
        }

        public static ComponentPart justAction(MapHolder data) {
            ComponentPart c = new ComponentPart();
            c.actions.add(data);
            return c;
        }

        public static ComponentPart particleOnTick(Double ticks, DefaultParticleType particle, Double count, Double radius) {
            ComponentPart c = new ComponentPart();
            c.actions.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius));
            c.conditions.add(EffectCondition.EVERY_X_TICKS.create(ticks));
            return c;
        }

    }

}
