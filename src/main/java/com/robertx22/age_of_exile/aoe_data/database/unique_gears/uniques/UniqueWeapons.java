package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques;

import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapons.*;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class UniqueWeapons implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new UniqueGloves().registerAll();
        new UniqueSwords().registerAll();
        new UniqueHammers().registerAll();
        new UniqueDaggers().registerAll();
        new UniqueStaffs().registerAll();

    }
}
