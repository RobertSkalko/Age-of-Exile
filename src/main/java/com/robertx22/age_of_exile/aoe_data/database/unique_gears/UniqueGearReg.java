package com.robertx22.age_of_exile.aoe_data.database.unique_gears;

import com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.UniqueArmors;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.UniqueJewelry;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.UniqueWeapons;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class UniqueGearReg implements ISlashRegistryInit {
    @Override
    public void registerAll() {

        new UniqueArmors().registerAll();
        new UniqueJewelry().registerAll();
        new UniqueWeapons().registerAll();
    }
}
