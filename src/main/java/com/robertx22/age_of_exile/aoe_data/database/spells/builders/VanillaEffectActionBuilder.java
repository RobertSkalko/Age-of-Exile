package com.robertx22.age_of_exile.aoe_data.database.spells.builders;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.potion.Effect;

public class VanillaEffectActionBuilder extends BaseEffectActionBuilder<VanillaEffectActionBuilder> {

    public Effect effect;

    public VanillaEffectActionBuilder(Effect effect) {
        this.effect = effect;
    }

    @Override
    public ComponentPart build() {
        ComponentPart c = new ComponentPart();

        c.acts.add(SpellAction.POTION.createGive(effect, this.seconds * 20D));

        if (giveToSelfOnly) {
            c.targets.add(BaseTargetSelector.CASTER.create());
        } else {
            c.targets.add(BaseTargetSelector.AOE.create((double) radius,
                EntityFinder.SelectionType.RADIUS,
                this.targetSelector));
        }

        return c;
    }
}
