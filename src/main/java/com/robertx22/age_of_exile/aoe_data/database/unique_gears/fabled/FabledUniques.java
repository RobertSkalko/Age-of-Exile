package com.robertx22.age_of_exile.aoe_data.database.unique_gears.fabled;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class FabledUniques implements ExileRegistryInit {

    @Override
    public void registerAll() {
        new FabledJewelry().registerAll();
        new FabledArmor().registerAll();

    }
}
