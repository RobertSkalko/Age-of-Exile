package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.IHasSpellEffect;

public class SpellHealEffect extends HealEffect implements IHasSpellEffect {

    public Spell spell;

    public SpellHealEffect(ResourcesData.Context data) {

        super(data);

        this.spell = Database.Spells()
            .get(data.spell);
    }

    @Override
    public Spell getSpell() {
        return this.spell;
    }
}
