package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.database.data.spells.TestSpell;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.library_of_exile.registry.ExileRegistryContainer;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

public class SpellContainer extends ExileRegistryContainer<Spell> {
    public SpellContainer(ExileRegistryType type, Spell emptyDefault) {
        super(type, emptyDefault);

    }

    @Override
    public boolean isRegistered(String id) {
        if (id.equals(TestSpell.USE_THIS_EXACT_ID)) {
            return true;
        }
        return super.isRegistered(id);
    }

    @Override
    public Spell get(String guid) {

        if (guid.equals(TestSpell.USE_THIS_EXACT_ID)) {
            return TestSpell.get();
        }
        return super.get(guid);
    }
}
