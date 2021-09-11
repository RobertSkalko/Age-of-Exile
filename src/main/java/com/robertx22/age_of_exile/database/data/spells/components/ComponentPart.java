package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.entity_predicates.SpellEntityPredicate;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;

import java.util.*;
import java.util.stream.Collectors;

public class ComponentPart {

    public List<MapHolder> targets = new ArrayList<>();
    public List<MapHolder> acts = new ArrayList<>();
    public List<MapHolder> ifs = new ArrayList<>();
    public List<MapHolder> en_preds = new ArrayList<>();

    List<ComponentPart> per_entity_hit = null;

    public ComponentPart addPerEntityHit(ComponentPart add) {
        if (per_entity_hit == null) {
            per_entity_hit = new ArrayList<>();
        }
        this.per_entity_hit
            .add(add);
        return this;
    }

    public ComponentPart enemiesInRadius(Double radius) {
        targets.add(TargetSelector.AOE.enemiesInRadius(radius));
        return this;
    }

    public ComponentPart alliesInRadius(Double radius) {
        targets.add(TargetSelector.AOE.alliesInRadius(radius));
        return this;
    }

    public ComponentPart onTick(Double ticks) {
        ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return this;
    }

    public ComponentPart setTarget(MapHolder target) {
        targets.add(target);
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
            List<LivingEntity> selected = selector.get(ctx, ctx.caster, ctx.target, ctx.pos, part);

            for (MapHolder entityPredicate : en_preds) {
                SpellEntityPredicate pred = SpellEntityPredicate.MAP.get(part.type);

                if (pred != null) {
                    selected = selected.stream()
                        .filter(x -> pred.is(ctx, x, entityPredicate))
                        .collect(Collectors.toList());
                }
            }
            list.addAll(selected);
        }

        for (MapHolder part : acts) {
            SpellAction action = SpellAction.MAP.get(part.type);
            if (action == null) {
                System.out.print(part.type + " action is null");
            } else {
                action.tryActivate(list, ctx, part);
            }
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

    public ComponentPart addCondition(MapHolder map) {
        this.ifs.add(map);
        return this;
    }

    public ComponentPart addEntityPredicate(MapHolder map) {
        this.en_preds.add(map);
        return this;
    }

}
