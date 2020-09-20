package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.*;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class BaseGearsRegister implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new BaseClothArmors().registerAll();
        new BaseLeatherArmors().registerAll();
        new BasePlateArmors().registerAll();

        new BaseGearOffHands().registerAll();
        new BaseGearWeapons().registerAll();
        new BaseGearJewelry().registerAll();

    }
}
