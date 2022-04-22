package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.database.data.spells.TestSpell;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class TestSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        TestSpell.get()
            .addToSerializables();

    }
}
