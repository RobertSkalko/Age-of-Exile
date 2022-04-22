package com.robertx22.age_of_exile.aoe_data.database.spells.builders;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;

public class ExileEffectActionBuilder extends BaseEffectActionBuilder<ExileEffectActionBuilder> {
    EffectCtx effect;

    public ExileEffectActionBuilder(EffectCtx effect) {
        this.effect = effect;
    }

    @Override
    public ComponentPart build() {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.EXILE_EFFECT.create(effect.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, seconds * 20D));
        c.targets.add(BaseTargetSelector.AOE.create((double) radius, EntityFinder.SelectionType.RADIUS, targetSelector));
        return c;
    }
}
