package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.cloth;

import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class ClothUniques implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new VoidArmor().registerAll();
        new BlazeArmor().registerAll();
        new SlimeArmor().registerAll();

    }
}
