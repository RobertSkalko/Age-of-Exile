package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class Tiers implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        for (int i = 0; i < 100; i++) {
            new Tier(i).addToSerializables();
        }
    }
}
