package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.activated_on.ActivatedOn;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class TestSpells {

    public static Spell TEST_SPELL = Spell.Builder.of("test")
        .addEffect(ActivatedOn.Activation.ON_CAST, ComponentPart.Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(5D, 1D, ModRegistry.ENTITIES.POISON_BALL, 200D)))
        .addEffect(ActivatedOn.Activation.ON_HIT, ComponentPart.Builder.damage(ValueCalculationData.base(500), Elements.Nature))
        .build();
}
