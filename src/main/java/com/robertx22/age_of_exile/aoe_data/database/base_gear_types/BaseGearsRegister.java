package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.*;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class BaseGearsRegister implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new BaseClothArmors().registerAll();
        new BaseLeatherArmors().registerAll();
        new BasePlateArmors().registerAll();

        new BaseGearOffHands().registerAll();
        new BaseGearWeapons().registerAll();
        new BaseGearJewelry().registerAll();

        new BaseToolsAdder().registerAll();

    }
}
