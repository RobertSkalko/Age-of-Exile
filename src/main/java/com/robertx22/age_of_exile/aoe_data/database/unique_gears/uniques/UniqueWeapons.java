package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques;

import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapons.UniqueStaffs;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapons.UniqueSwords;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class UniqueWeapons implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new UniqueSwords().registerAll();
        new UniqueStaffs().registerAll();

    }
}
