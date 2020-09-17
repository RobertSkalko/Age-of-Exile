package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.spells.DatapackSpells;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class Spells implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        DatapackSpells.init();
    }
}
