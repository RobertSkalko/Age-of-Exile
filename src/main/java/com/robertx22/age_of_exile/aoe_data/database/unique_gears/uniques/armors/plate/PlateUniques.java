package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armors.plate;

import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class PlateUniques implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new OakArmor().registerAll();
        new DarkCrystalArmor().registerAll();

    }
}
