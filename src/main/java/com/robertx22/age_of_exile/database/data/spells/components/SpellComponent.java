package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.activated_on.ActivatedOn;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.reg.SpellActions;
import com.robertx22.age_of_exile.database.data.spells.components.reg.SpellConditions;
import com.robertx22.age_of_exile.database.data.spells.components.reg.SpellTargetSelectors;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import net.minecraft.entity.LivingEntity;

import java.util.*;

public class SpellComponent {

    private List<ComponentPart> target_selectors = new ArrayList<>();
    private List<ComponentPart> actions = new ArrayList<>();
    private List<ComponentPart> conditions = new ArrayList<>();

    // this is different so its efficient to check. Otherwise ontick checkers would lag like hell.
    ActivatedOn.ActivationType activationType;
    HashMap<MapField, Object> activationData;

    public void tryActivate(ActivatedOn.ActivationType type, SpellCtx ctx) {

        if (this.activationType != type) {
            return;
        }

        for (ComponentPart part : conditions) {
            EffectCondition condition = SpellConditions.MAP.get(part.type);
            if (!condition.canActivate(ctx, part.map)) {
                return;
            }
        }

        Set<LivingEntity> list = new HashSet<>();

        for (ComponentPart part : target_selectors) {
            BaseTargetSelector selector = SpellTargetSelectors.MAP.get(part.type);
            list.addAll(selector.get(ctx.caster, ctx.target, ctx.pos, part.map));
        }

        for (ComponentPart part : actions) {
            SpellAction action = SpellActions.MAP.get(part.type);
            action.tryActivate(list, ctx, part.map);
        }

    }

}
