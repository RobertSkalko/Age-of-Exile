package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.cast_actions.CastAction;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class TestSpells {

    public static Spell TEST_SPELL = Spell.Builder.of("test")
        .addOnCast(ComponentPart.Builder.justAction(CastAction.PROJECTILE.create(5, 1, ModRegistry.ENTITIES.POISON_BALL)))
        .addEffect(ActivationTypeData.createOnCast(), ComponentPart.Builder.damage(ValueCalculationData.base(5), Elements.Nature))
        .build();
}
