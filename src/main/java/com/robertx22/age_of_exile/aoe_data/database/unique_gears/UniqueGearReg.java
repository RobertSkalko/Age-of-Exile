package com.robertx22.age_of_exile.aoe_data.database.unique_gears;

import com.robertx22.age_of_exile.aoe_data.database.unique_gears.fabled.FabledUniques;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.UniqueArmors;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.UniqueJewelry;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.UniqueWeapons;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class UniqueGearReg implements ExileRegistryInit {
    @Override
    public void registerAll() {

        new FabledUniques().registerAll();

        new UniqueArmors().registerAll();
        new UniqueJewelry().registerAll();
        new UniqueWeapons().registerAll();

    }
}
