package com.robertx22.age_of_exile.aoe_data.database.unique_gears.monster_uniques;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class MonsterUniques implements ExileRegistryInit {

    @Override
    public void registerAll() {
        new JewelryMIs().registerAll();
    }
}
