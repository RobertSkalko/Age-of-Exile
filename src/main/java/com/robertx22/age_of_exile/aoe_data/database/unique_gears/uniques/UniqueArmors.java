package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques;

import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armors.cloth.ClothUniques;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armors.leather.LeatherUniques;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armors.plate.PlateUniques;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class UniqueArmors implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new PlateUniques().registerAll();
        new ClothUniques().registerAll();
        new LeatherUniques().registerAll();

    }
}
