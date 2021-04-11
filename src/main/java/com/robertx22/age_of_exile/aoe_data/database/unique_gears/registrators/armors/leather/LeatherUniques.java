package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.leather;

import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class LeatherUniques implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new BoneArmor().registerAll();

    }
}
