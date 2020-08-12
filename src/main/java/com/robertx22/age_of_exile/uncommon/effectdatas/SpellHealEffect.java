package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IHasSpellEffect;

public class SpellHealEffect extends HealEffect implements IHasSpellEffect {

    public BaseSpell spell;

    public SpellHealEffect(ResourcesData.Context data) {

        super(data);

        this.spell = data.spell;
    }

    @Override
    public BaseSpell getSpell() {
        return this.spell;
    }
}
