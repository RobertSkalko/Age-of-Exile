package com.robertx22.age_of_exile.database.all_keys.base;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;

public class SpellKey extends DataKey<Spell> {
    public SpellKey(String id) {
        super(id);
    }

    @Override
    public Spell getFromRegistry() {
        return ExileDB.Spells()
            .get(id);
    }

    @Override
    public Spell getFromDataGen() {
        return ExileDB.Spells()
            .getFromSerializables(id);
    }

    @Override
    public Class getDataClass() {
        return Spell.class;
    }
}
